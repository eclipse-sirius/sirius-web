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
package org.eclipse.sirius.web.domain.boundedcontexts.project.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectCreationService;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to create new projects.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectCreationService implements IProjectCreationService {

    private final IProjectRepository projectRepository;

    private final IMessageService messageService;

    public ProjectCreationService(IProjectRepository projectRepository, IMessageService messageService) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Project> createProject(String name, List<String> natures) {
        var projectName = this.sanitize(name);

        IResult<Project> result = null;

        if (!isValid(projectName)) {
            result = new Failure<>(this.messageService.invalidName());
        } else {
            var project = Project.newProject()
                    .name(name)
                    .natures(natures)
                    .build();
            this.projectRepository.save(project);

            result = new Success<>(project);
        }

        return result;
    }

    private String sanitize(String name) {
        return name.trim();
    }

    private boolean isValid(String name) {
        return !name.isEmpty() && name.length() <= 1024;
    }
}
