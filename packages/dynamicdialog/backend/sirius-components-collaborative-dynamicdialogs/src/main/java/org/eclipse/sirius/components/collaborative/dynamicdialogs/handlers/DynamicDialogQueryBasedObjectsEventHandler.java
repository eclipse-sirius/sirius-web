/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import org.eclipse.sirius.components.collaborative.dto.QueryBasedStringInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogQueryBasedObjectsInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.services.api.IDynamicDialogService;
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
 * Handler used to execute query based Objects for dynamic dialog.
 *
 * @author lfasani
 */
@Service
public class DynamicDialogQueryBasedObjectsEventHandler implements IEditingContextEventHandler {

    private final ICollaborativeMessageService messageService;

    private final IDynamicDialogService dynamicDialogService;

    private final Counter counter;

    public DynamicDialogQueryBasedObjectsEventHandler(ICollaborativeMessageService messageService, MeterRegistry meterRegistry, IDynamicDialogService dynamicDialogService) {
        this.messageService = Objects.requireNonNull(messageService);

        this.dynamicDialogService = Objects.requireNonNull(dynamicDialogService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof DynamicDialogQueryBasedObjectsInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), QueryBasedStringInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (input instanceof DynamicDialogQueryBasedObjectsInput dialogQueryBasedObjectsInput) {
            payload = this.dynamicDialogService.getQueryBasedObjects(editingContext, dialogQueryBasedObjectsInput);
        }

        payloadSink.tryEmitValue(payload);

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
