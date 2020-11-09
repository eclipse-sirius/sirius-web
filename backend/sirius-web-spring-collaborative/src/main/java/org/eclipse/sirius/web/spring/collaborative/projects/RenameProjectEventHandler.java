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
package org.eclipse.sirius.web.spring.collaborative.projects;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.services.api.dto.IProjectInput;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.RenameProjectInput;
import org.eclipse.sirius.web.services.api.projects.RenameProjectSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.springframework.stereotype.Service;

/**
 * Handler used to rename a project.
 *
 * @author fbarbin
 */
@Service
public class RenameProjectEventHandler implements IProjectEventHandler {

    private final ICollaborativeMessageService messageService;

    private final IProjectService projectService;

    public RenameProjectEventHandler(ICollaborativeMessageService messageService, IProjectService projectService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public boolean canHandle(IProjectInput projectInput) {
        return projectInput instanceof RenameProjectInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IProjectInput projectInput, Context context) {
        if (projectInput instanceof RenameProjectInput) {
            RenameProjectInput input = (RenameProjectInput) projectInput;
            Optional<Project> optionalProject = this.projectService.renameProject(input.getProjectId(), input.getNewName());
            if (optionalProject.isPresent()) {
                RenameProjectSuccessPayload payload = new RenameProjectSuccessPayload(optionalProject.get());
                return new EventHandlerResponse(false, representation -> false, payload);
            }
        }
        String message = this.messageService.invalidInput(projectInput.getClass().getSimpleName(), RenameProjectInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

}
