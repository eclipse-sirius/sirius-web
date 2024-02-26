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

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.dto.CreateProjectFromTemplateInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.ProjectTemplateDTO;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ITemplateBasedProjectInitializer;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Used to interact with project templates.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectTemplateApplicationService implements IProjectTemplateApplicationService {

    private final List<IProjectTemplateProvider> projectTemplateProviders;

    private final IProjectApplicationService projectApplicationService;

    private final ITemplateBasedProjectInitializer templateBasedProjectInitializer;

    private final IMessageService messageService;

    public ProjectTemplateApplicationService(List<IProjectTemplateProvider> projectTemplateProviders, IProjectApplicationService projectApplicationService, ITemplateBasedProjectInitializer templateBasedProjectInitializer, IMessageService messageService) {
        this.projectTemplateProviders = Objects.requireNonNull(projectTemplateProviders);
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
        this.templateBasedProjectInitializer = Objects.requireNonNull(templateBasedProjectInitializer);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public Page<ProjectTemplateDTO> findAll(Pageable pageable) {
        var projectTemplates = this.projectTemplateProviders.stream()
                .map(IProjectTemplateProvider::getProjectTemplates)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(ProjectTemplate::label))
                .toList();

        int startIndex = (int) pageable.getOffset() * pageable.getPageSize();
        int endIndex = Math.min(((int) pageable.getOffset() + 1) * pageable.getPageSize(), projectTemplates.size());
        var projectTemplateDTOs = projectTemplates.subList(startIndex, endIndex).stream()
                .map(projectTemplate -> new ProjectTemplateDTO(projectTemplate.id(), projectTemplate.label(), projectTemplate.imageURL()))
                .toList();

        return new PageImpl<>(projectTemplateDTOs, pageable, projectTemplates.size());
    }


    @Override
    public IPayload createProjectFromTemplate(CreateProjectFromTemplateInput input) {
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

            var projectCreationPayload = this.projectApplicationService.createProject(new CreateProjectInput(input.id(), projectTemplate.label(), natures));
            if (projectCreationPayload instanceof CreateProjectSuccessPayload createProjectSuccessPayload) {
                payload = this.templateBasedProjectInitializer.initializeProjectFromTemplate(input, createProjectSuccessPayload.project().id(), input.templateId());
            } else {
                payload = projectCreationPayload;
            }
        } else {
            payload = new ErrorPayload(input.id(), this.messageService.notFound());
        }

        return payload;
    }
}
