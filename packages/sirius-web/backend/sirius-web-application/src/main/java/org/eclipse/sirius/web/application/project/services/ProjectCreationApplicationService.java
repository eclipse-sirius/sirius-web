/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.api.ICreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectCreationApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectMapper;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectCreationService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service used to create projects.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectCreationApplicationService implements IProjectCreationApplicationService {

    private final IProjectCreationService projectCreationService;

    private final List<IProjectTemplateProvider> projectTemplateProviders;

    private final IProjectMapper projectMapper;

    private final IMessageService messageService;

    public ProjectCreationApplicationService(IProjectCreationService projectCreationService, List<IProjectTemplateProvider> projectTemplateProviders, IProjectMapper projectMapper, IMessageService messageService) {
        this.projectCreationService = Objects.requireNonNull(projectCreationService);
        this.projectTemplateProviders = Objects.requireNonNull(projectTemplateProviders);
        this.projectMapper = Objects.requireNonNull(projectMapper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    @Transactional
    public IPayload createProject(ICreateProjectInput input) {
        IPayload payload = null;

        var optionalProjectTemplate = this.projectTemplateProviders.stream()
                .map(IProjectTemplateProvider::getProjectTemplates)
                .flatMap(Collection::stream)
                .filter(projectTemplate -> projectTemplate.id().equals(input.templateId()))
                .findFirst();

        if (optionalProjectTemplate.isPresent()) {
            var projectTemplate = optionalProjectTemplate.get();
            var natures = projectTemplate.natures().stream()
                    .map(ProjectTemplateNature::id)
                    .toList();
            var result = this.projectCreationService.createProject(input, input.name(), natures);
            if (result instanceof Failure<Project> failure) {
                payload = new ErrorPayload(input.id(), failure.message());
            } else if (result instanceof Success<Project> success) {
                payload = new CreateProjectSuccessPayload(input.id(), this.projectMapper.toDTO(success.data()));
            }
        } else {
            payload = new ErrorPayload(input.id(), this.messageService.notFound());
        }

        return payload;
    }
}
