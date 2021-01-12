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

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.services.api.objects.RenameObjectInput;
import org.eclipse.sirius.web.services.api.objects.RenameObjectSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.IProjectInput;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handler used to rename an object.
 *
 * @author arichard
 */
@Service
public class RenameObjectEventHandler implements IProjectEventHandler {

    private final ICollaborativeMessageService messageService;

    private final IObjectService objectService;

    private final IEditService editService;

    private final Counter counter;

    public RenameObjectEventHandler(ICollaborativeMessageService messageService, IObjectService objectService, IEditService editService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IProjectInput projectInput) {
        return projectInput instanceof RenameObjectInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IProjectInput projectInput, Context context) {
        this.counter.increment();

        if (projectInput instanceof RenameObjectInput) {
            RenameObjectInput input = (RenameObjectInput) projectInput;
            String objectId = input.getObjectId();
            String newName = input.getNewName();
            var optionalObject = this.objectService.getObject(editingContext, objectId);
            if (optionalObject.isPresent()) {
                Object object = optionalObject.get();
                var optionalLabelField = this.objectService.getLabelField(object);
                if (optionalLabelField.isPresent()) {
                    String labelField = optionalLabelField.get();
                    this.editService.editLabel(object, labelField, newName);
                    return new EventHandlerResponse(true, representation -> true, new RenameObjectSuccessPayload(objectId, newName));
                }
            }
        }
        String message = this.messageService.invalidInput(projectInput.getClass().getSimpleName(), RenameObjectInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

}
