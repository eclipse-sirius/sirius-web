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
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.document.CreateRootObjectInput;
import org.eclipse.sirius.web.services.api.document.CreateRootObjectSuccessPayload;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.services.api.dto.IProjectInput;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.web.trees.Tree;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handler used to create a new root object.
 *
 * @author lfasani
 */
@Service
public class CreateRootObjectEventHandler implements IProjectEventHandler {

    private final IEditService editService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public CreateRootObjectEventHandler(IEditService editService, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.editService = Objects.requireNonNull(editService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IProjectInput projectInput) {
        return projectInput instanceof CreateRootObjectInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IProjectInput projectInput, Context context) {
        this.counter.increment();

        if (projectInput instanceof CreateRootObjectInput) {
            CreateRootObjectInput input = (CreateRootObjectInput) projectInput;
            UUID documentId = input.getDocumentId();
            String rootObjectCreationDescriptionId = input.getRootObjectCreationDescriptionId();
            String namespaceId = input.getNamespaceId();

            var optionalObject = this.editService.createRootObject(editingContext, documentId, namespaceId, rootObjectCreationDescriptionId);

            if (optionalObject.isPresent()) {
                return new EventHandlerResponse(true, Tree.class::isInstance, new CreateRootObjectSuccessPayload(optionalObject.get()));
            }
        }

        String message = this.messageService.invalidInput(projectInput.getClass().getSimpleName(), CreateRootObjectInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

}
