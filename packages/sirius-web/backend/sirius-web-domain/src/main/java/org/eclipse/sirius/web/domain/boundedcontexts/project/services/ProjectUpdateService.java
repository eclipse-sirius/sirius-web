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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectNameValidator;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectUpdateService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to update projects.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectUpdateService implements IProjectUpdateService {

    private final IProjectRepository projectRepository;

    private final IProjectNameValidator projectNameValidator;

    private final IMessageService messageService;

    public ProjectUpdateService(IProjectRepository projectRepository, IProjectNameValidator projectNameValidator, IMessageService messageService) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.projectNameValidator = Objects.requireNonNull(projectNameValidator);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> renameProject(ICause cause, String projectId, String newName) {
        IResult<Void> result = null;

        var optionalProject = this.projectRepository.findById(projectId);
        var projectName = this.projectNameValidator.sanitize(newName);
        if (!this.projectNameValidator.isValid(projectName)) {
            result = new Failure<>(this.messageService.invalidName());
        } else if (optionalProject.isEmpty()) {
            result = new Failure<>(this.messageService.notFound());
        } else {
            var project = optionalProject.get();
            project.updateName(cause, newName);

            this.projectRepository.save(project);
            result = new Success<>(null);
        }

        return result;
    }

    @Override
    public IResult<Void> addNatures(ICause cause, String projectId, List<String> natureNames) {
        IResult<Void> result = null;

        var optionalProject = this.projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            result = new Failure<>(this.messageService.notFound());
        } else {
            var project = optionalProject.get();
            project.addNatures(cause, natureNames);

            this.projectRepository.save(project);
            result = new Success<>(null);
        }

        return result;
    }

    @Override
    public IResult<Void> removeNatures(ICause cause, String projectId, List<String> natureNames) {
        IResult<Void> result = null;

        var optionalProject = this.projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            result = new Failure<>(this.messageService.notFound());
        } else {
            var project = optionalProject.get();
            project.removeNatures(cause, natureNames);

            this.projectRepository.save(project);
            result = new Success<>(null);
        }

        return result;
    }
}
