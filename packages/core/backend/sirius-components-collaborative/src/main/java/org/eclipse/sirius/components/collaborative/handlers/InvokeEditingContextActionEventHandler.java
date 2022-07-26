/*******************************************************************************
 * Copyright (c) 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.collaborative.handlers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextActionHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.InvokeEditingContextActionInput;
import org.eclipse.sirius.components.collaborative.dto.InvokeEditingContextActionSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to invoke an EditingContext action.
 *
 * @author rpage
 */
@Service
public class InvokeEditingContextActionEventHandler implements IEditingContextEventHandler {
    private final Logger logger = LoggerFactory.getLogger(InvokeEditingContextActionEventHandler.class);

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    private final List<IEditingContextActionHandler> editingContextActionHandlers;

    public InvokeEditingContextActionEventHandler(List<IEditingContextActionHandler> editingContextActionHandlers, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.editingContextActionHandlers = Objects.requireNonNull(editingContextActionHandlers);

        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof InvokeEditingContextActionInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), InvokeEditingContextActionInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof InvokeEditingContextActionInput) {
            InvokeEditingContextActionInput invokeEditingContextActionInput = (InvokeEditingContextActionInput) input;

            // @formatter:off
            IStatus status = this.editingContextActionHandlers.stream()
                .filter(handler -> handler.canHandle(editingContext, invokeEditingContextActionInput.getActionId()))
                .findFirst()
                .map(handler -> handler.handle(editingContext, invokeEditingContextActionInput.getActionId()))
                .orElse(new Failure("No handler could be found for action with id " + invokeEditingContextActionInput.getActionId())); //$NON-NLS-1$
            // @formatter:on

            if (status instanceof Success) {
                payload = new InvokeEditingContextActionSuccessPayload(invokeEditingContextActionInput.getId());
                changeDescription = new ChangeDescription(((Success) status).getChangeKind(), editingContext.getId(), input);
            } else if (status instanceof Failure) {
                this.logger.warn("The action with id {} could not be executed", invokeEditingContextActionInput.getActionId()); //$NON-NLS-1$
                payload = new ErrorPayload(input.getId(), ((Failure) status).getMessage());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
