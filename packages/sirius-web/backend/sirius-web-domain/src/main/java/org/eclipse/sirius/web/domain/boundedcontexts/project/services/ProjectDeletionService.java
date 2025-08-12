/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.project.services;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletionRequestedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectDeletionService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Used to delete projects.
 *
 * @technical-debt This service needs to fire manually some event to give the opportunity to other services to be
 * synchronized with the deletion of a project. This is done thanks to a small hack relying on the application event
 * publisher that will need to be deleted in the future. Do not rely on this.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectDeletionService implements IProjectDeletionService {

    private final IProjectRepository projectRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final IMessageService messageService;

    public ProjectDeletionService(IProjectRepository projectRepository, ApplicationEventPublisher applicationEventPublisher, IMessageService messageService) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.applicationEventPublisher = Objects.requireNonNull(applicationEventPublisher);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> deleteProject(ICause cause, String projectId) {
        IResult<Void> result = null;

        var optionalProject = this.projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            var project = optionalProject.get();
            project.dispose(cause);

            this.applicationEventPublisher.publishEvent(new ProjectDeletionRequestedEvent(UUID.randomUUID(), Instant.now(), cause, project));

            this.projectRepository.delete(project);
            result = new Success<>(null);
        } else {
            result = new Failure<>(this.messageService.notFound());
        }

        return result;
    }
}
