/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.project.services;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.eclipse.sirius.web.application.project.dto.RenameProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectDeletionService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectUpdateService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application services used to manipulate projects.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectApplicationService implements IProjectApplicationService {
    private final IProjectSearchService projectSearchService;

    private final IProjectCreationService projectCreationService;

    private final IProjectUpdateService projectUpdateService;

    private final IProjectDeletionService projectDeletionService;

    private final IProjectMapper projectMapper;

    public ProjectApplicationService(IProjectSearchService projectSearchService, IProjectCreationService projectCreationService, IProjectUpdateService projectUpdateService, IProjectDeletionService projectDeletionService, IProjectMapper projectMapper) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectCreationService = Objects.requireNonNull(projectCreationService);
        this.projectUpdateService = Objects.requireNonNull(projectUpdateService);
        this.projectDeletionService = Objects.requireNonNull(projectDeletionService);
        this.projectMapper = Objects.requireNonNull(projectMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDTO> findById(UUID projectId) {
        return this.projectSearchService.findById(projectId).map(this.projectMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return this.projectSearchService.findAll(pageable).map(this.projectMapper::toDTO);
    }

    @Override
    @Transactional
    public IPayload createProject(CreateProjectInput input) {
        var result = this.projectCreationService.createProject(input, input.name(), input.natures());

        IPayload payload = null;
        if (result instanceof Failure<Project> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Project> success) {
            payload = new CreateProjectSuccessPayload(input.id(), this.projectMapper.toDTO(success.data()));
        }
        return payload;
    }

    @Override
    @Transactional
    public IPayload renameProject(RenameProjectInput input) {
        var result = this.projectUpdateService.renameProject(input.projectId(), input.newName());

        IPayload payload = null;
        if (result instanceof Failure<Void> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Void>) {
            payload = new RenameProjectSuccessPayload(input.id());
        }
        return payload;
    }

    @Override
    @Transactional
    public IPayload deleteProject(DeleteProjectInput input) {
        var result = this.projectDeletionService.deleteProject(input.projectId());

        IPayload payload = null;
        if (result instanceof Failure<Void> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Void>) {
            payload = new SuccessPayload(input.id());
        }
        return payload;
    }
}
