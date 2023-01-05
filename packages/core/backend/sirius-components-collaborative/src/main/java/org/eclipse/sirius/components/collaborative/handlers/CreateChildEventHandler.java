/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.collaborative.dto.CreateChildSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to create a new child.
 *
 * @author sbegaudeau
 */
@Service
public class CreateChildEventHandler implements IEditingContextEventHandler {

    private final IObjectService objectService;

    private final IEditService editService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public CreateChildEventHandler(IObjectService objectService, IEditService editService, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof CreateChildInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), CreateChildInput.class.getSimpleName());
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        IPayload payload = null;

        if (input instanceof CreateChildInput) {
            CreateChildInput createChildInput = (CreateChildInput) input;
            String parentObjectId = createChildInput.objectId();
            String childCreationDescriptionId = createChildInput.childCreationDescriptionId();

            Optional<Object> createdChildOptional = this.objectService.getObject(editingContext, parentObjectId).flatMap(parent -> {
                return this.editService.createChild(editingContext, parent, childCreationDescriptionId);
            });

            if (createdChildOptional.isPresent()) {
                payload = new CreateChildSuccessPayload(input.id(), createdChildOptional.get());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
            } else {
                message = this.messageService.objectCreationFailed();
            }
        }

        if (payload == null) {
            payload = new ErrorPayload(input.id(), message);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
