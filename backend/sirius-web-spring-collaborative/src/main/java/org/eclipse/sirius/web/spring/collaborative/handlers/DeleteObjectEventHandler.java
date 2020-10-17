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

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.services.api.dto.IProjectInput;
import org.eclipse.sirius.web.services.api.objects.DeleteObjectInput;
import org.eclipse.sirius.web.services.api.objects.DeleteObjectSuccessPayload;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Handler used to delete an object.
 *
 * @author sbegaudeau
 */
@Service
public class DeleteObjectEventHandler implements IProjectEventHandler {

    private final Logger logger = LoggerFactory.getLogger(DeleteObjectEventHandler.class);

    private final IObjectService objectService;

    private final IEditService editService;

    private final ICollaborativeMessageService messageService;

    public DeleteObjectEventHandler(IObjectService objectService, IEditService editService, ICollaborativeMessageService messageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IProjectInput projectInput) {
        return projectInput instanceof DeleteObjectInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IProjectInput projectInput, Context context) {
        if (projectInput instanceof DeleteObjectInput) {
            DeleteObjectInput input = (DeleteObjectInput) projectInput;

            Optional<Object> optionalObject = this.objectService.getObject(editingContext, input.getObjectId());
            if (optionalObject.isPresent()) {
                Object object = optionalObject.get();
                this.editService.delete(object);

                // FIXME Find the document in which the object is located
                return new EventHandlerResponse(true, representation -> true, new DeleteObjectSuccessPayload(null));
            } else {
                this.logger.warn(MessageFormat.format("The object with the id {0} does not exist", input.getObjectId())); //$NON-NLS-1$
            }
        }

        String message = this.messageService.invalidInput(projectInput.getClass().getSimpleName(), DeleteObjectInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

}
