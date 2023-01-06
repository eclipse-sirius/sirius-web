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
package org.eclipse.sirius.web.services.projects;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.RenameProjectInput;
import org.eclipse.sirius.web.services.api.projects.RenameProjectSuccessPayload;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to rename a project.
 *
 * @author fbarbin
 */
@Service
public class RenameProjectEventHandler implements IEditingContextEventHandler {

    private final IServicesMessageService messageService;

    private final IProjectService projectService;

    public RenameProjectEventHandler(IServicesMessageService messageService, IProjectService projectService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof RenameProjectInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), RenameProjectInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof RenameProjectInput renameProjectInput) {
            Optional<Project> optionalProject = this.projectService.renameProject(renameProjectInput.projectId(), renameProjectInput.newName());
            if (optionalProject.isPresent()) {
                payload = new RenameProjectSuccessPayload(input.id(), optionalProject.get());
                changeDescription = new ChangeDescription(ChangeKind.PROJECT_RENAMING, editingContext.getId(), input);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
