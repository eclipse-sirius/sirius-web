/*******************************************************************************
 * Copyright (c) 2023 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.collaborative.dynamicdialogs.handlers;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.ApplyDynamicDialogInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.services.api.IDynamicDialogService;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle the apply on dynamic dialog.
 *
 * @author lfasani
 */
@Service
public class ApplyDynamicDialogEventHandler implements IEditingContextEventHandler {

    private final IDynamicDialogService dynamicDialogService;
    private final ICollaborativeMessageService messageService;
    private final Counter counter;

    public ApplyDynamicDialogEventHandler(IDynamicDialogService dynamicDialogService, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.dynamicDialogService = Objects.requireNonNull(dynamicDialogService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }


    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof ApplyDynamicDialogInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), ApplyDynamicDialogInput.class.getSimpleName());
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        IPayload payload = null;

        if (input instanceof ApplyDynamicDialogInput applyDynamicDialogInput) {

            payload =  this.dynamicDialogService.applyDialog(editingContext, applyDynamicDialogInput);

            if (payload instanceof SuccessPayload) {
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
            }
        }

        if (payload == null) {
            payload = new ErrorPayload(input.id(), message);
        }


        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
