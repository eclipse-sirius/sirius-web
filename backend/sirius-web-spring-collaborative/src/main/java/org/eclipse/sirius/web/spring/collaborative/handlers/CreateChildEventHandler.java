/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.handlers;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.services.api.objects.CreateChildInput;
import org.eclipse.sirius.web.services.api.objects.CreateChildSuccessPayload;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

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
    public boolean canHandle(IInput input) {
        return input instanceof CreateChildInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), CreateChildInput.class.getSimpleName());
        if (input instanceof CreateChildInput) {
            CreateChildInput createChildInput = (CreateChildInput) input;
            String parentObjectId = createChildInput.getObjectId();
            String childCreationDescriptionId = createChildInput.getChildCreationDescriptionId();

            Optional<Object> createdChildOptional = this.objectService.getObject(editingContext, parentObjectId).flatMap(parent -> {
                return this.editService.createChild(editingContext, parent, childCreationDescriptionId);
            });

            if (createdChildOptional.isPresent()) {
                return new EventHandlerResponse(true, representation -> true, new CreateChildSuccessPayload(createdChildOptional.get()));
            } else {
                message = this.messageService.objectCreationFailed();
            }
        }

        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }
}
