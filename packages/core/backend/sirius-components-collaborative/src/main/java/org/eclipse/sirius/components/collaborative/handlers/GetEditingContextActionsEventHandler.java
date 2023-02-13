/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import org.eclipse.sirius.components.collaborative.api.IEditingContextActionProvider;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.EditingContextAction;
import org.eclipse.sirius.components.collaborative.dto.GetEditingContextActionsInput;
import org.eclipse.sirius.components.collaborative.dto.GetEditingContextActionsSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to access to the EditingContext Actions.
 *
 * @author rpage
 */
@Service
public class GetEditingContextActionsEventHandler implements IEditingContextEventHandler {
    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    private final List<IEditingContextActionProvider> editingContextActionProviders;

    public GetEditingContextActionsEventHandler(ICollaborativeMessageService messageService, List<IEditingContextActionProvider> editingContextActionProviders, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.editingContextActionProviders = Objects.requireNonNull(editingContextActionProviders);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetEditingContextActionsInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetEditingContextActionsInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof GetEditingContextActionsInput) {
            GetEditingContextActionsInput editingContextActionsInput = (GetEditingContextActionsInput) input;

            // @formatter:off
            List<EditingContextAction> editingContextActions = this.editingContextActionProviders.stream()
                .flatMap(provider -> provider.getEditingContextAction(editingContext).stream())
                .sorted((a1, a2) -> a1.getLabel().compareTo(a2.getLabel()))
                .toList();
            // @formatter:on

            payload = new GetEditingContextActionsSuccessPayload(editingContextActionsInput.id(), editingContextActions);
            changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
