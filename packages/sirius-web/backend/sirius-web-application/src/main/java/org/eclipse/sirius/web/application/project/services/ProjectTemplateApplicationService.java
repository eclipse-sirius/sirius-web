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
package org.eclipse.sirius.web.application.project.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.eclipse.sirius.web.application.project.dto.CreateProjectFromTemplateInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.ProjectTemplateContext;
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

    private final ICapabilityEvaluator capabilityEvaluator;

    private final IMessageService messageService;

    public ProjectTemplateApplicationService(List<IProjectTemplateProvider> projectTemplateProviders, IProjectApplicationService projectApplicationService, ITemplateBasedProjectInitializer templateBasedProjectInitializer,
                                             List<ICapabilityVoter> capabilityVoters, ICapabilityEvaluator capabilityEvaluator, IMessageService messageService) {
        this.projectTemplateProviders = Objects.requireNonNull(projectTemplateProviders);
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
        this.templateBasedProjectInitializer = Objects.requireNonNull(templateBasedProjectInitializer);
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public Page<ProjectTemplateDTO> findAll(Pageable pageable, String context) {
        return switch (context) {
            case ProjectTemplateContext.PROJECT_BROWSER -> this.handleProjectBrowser(pageable);
            case ProjectTemplateContext.PROJECT_TEMPLATE_MODAL -> this.handleProjectTemplateModal(pageable);
            default -> this.handleProjectTemplateModal(pageable);
        };
    }

    private List<ProjectTemplate> getProjectTemplatesSortedByName() {
        return this.projectTemplateProviders.stream()
                .map(IProjectTemplateProvider::getProjectTemplates)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(ProjectTemplate::label))
                .toList();
    }

    private Page<ProjectTemplateDTO> handleProjectBrowser(Pageable pageable) {
        var projectTemplates = this.getProjectTemplatesSortedByName();

        List<ProjectTemplate> siriusWebProjectTemplate = new ArrayList<>();
        siriusWebProjectTemplate.add(new ProjectTemplate("create-project", "", "", List.of()));
        this.getUploadProject().ifPresent(siriusWebProjectTemplate::add);
        this.getBrowseAllProjectTemplates().ifPresent(siriusWebProjectTemplate::add);

        int startIndex = (int) pageable.getOffset() * pageable.getPageSize();
        int endIndex = Math.min(((int) pageable.getOffset() + 1) * pageable.getPageSize(), projectTemplates.size() + siriusWebProjectTemplate.size());
        var projectTemplateDTOs = Stream.concat(projectTemplates.subList(startIndex, endIndex - siriusWebProjectTemplate.size()).stream(), siriusWebProjectTemplate.stream())
                .map(projectTemplate -> new ProjectTemplateDTO(projectTemplate.id(), projectTemplate.label(), projectTemplate.imageURL(), projectTemplate.natures().stream().map(ProjectTemplateNature::id).toList()))
                .toList();

        return new PageImpl<>(projectTemplateDTOs, pageable, projectTemplates.size());
    }

    private Page<ProjectTemplateDTO> handleProjectTemplateModal(Pageable pageable) {
        var projectTemplates = this.getProjectTemplatesSortedByName();

        int startIndex = (int) pageable.getOffset() * pageable.getPageSize();
        int endIndex = Math.min(((int) pageable.getOffset() + 1) * pageable.getPageSize(), projectTemplates.size());
        var projectTemplateDTOs = projectTemplates.subList(startIndex, endIndex).stream()
                .map(projectTemplate -> new ProjectTemplateDTO(projectTemplate.id(), projectTemplate.label(), projectTemplate.imageURL(), projectTemplate.natures().stream().map(ProjectTemplateNature::id).toList()))
                .toList();

        return new PageImpl<>(projectTemplateDTOs, pageable, projectTemplates.size());
    }

    private Optional<ProjectTemplate> getUploadProject() {
        Optional<ProjectTemplate> result = Optional.empty();
        var canCreate = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT, null, SiriusWebCapabilities.Project.UPLOAD);
        if (canCreate) {
            result = Optional.of(new ProjectTemplate("upload-project", "", "", List.of()));
        }
        return  result;
    }

    private Optional<ProjectTemplate> getBrowseAllProjectTemplates() {
        Optional<ProjectTemplate> result = Optional.empty();
        var aTemplateExists = this.projectTemplateProviders.stream().mapToLong(providers -> providers.getProjectTemplates().size()).sum() > 0;
        if (aTemplateExists) {
            result = Optional.of(new ProjectTemplate("browse-all-project-templates", "", "", List.of()));
        }
        return  result;
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
            var projectCreationPayload = this.projectApplicationService.createProject(new CreateProjectInput(input.id(), input.name(), input.natures(), List.of()));
            if (projectCreationPayload instanceof CreateProjectSuccessPayload createProjectSuccessPayload) {
                var projectId = createProjectSuccessPayload.project().id();
                payload = this.templateBasedProjectInitializer.initializeProjectFromTemplate(input, projectId, input.templateId());
            } else {
                payload = projectCreationPayload;
            }
        } else {
            payload = new ErrorPayload(input.id(), this.messageService.notFound());
        }

        return payload;
    }
}
