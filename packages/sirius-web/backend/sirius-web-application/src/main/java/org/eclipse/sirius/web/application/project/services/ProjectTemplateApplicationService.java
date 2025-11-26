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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.application.project.dto.ProjectTemplateContext;
import org.eclipse.sirius.web.application.project.dto.ProjectTemplateDTO;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
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

    private final ICapabilityEvaluator capabilityEvaluator;

    public ProjectTemplateApplicationService(List<IProjectTemplateProvider> projectTemplateProviders, ICapabilityEvaluator capabilityEvaluator) {
        this.projectTemplateProviders = Objects.requireNonNull(projectTemplateProviders);
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
    }

    @Override
    public Page<ProjectTemplateDTO> findAll(Pageable pageable, String context) {
        return switch (context) {
            case ProjectTemplateContext.PROJECT_BROWSER -> this.handleProjectBrowser(pageable);
            case ProjectTemplateContext.PROJECT_TEMPLATE_MODAL -> this.handleProjectTemplateModal(pageable);
            default -> this.handleProjectTemplateModal(pageable);
        };
    }

    @Override
    public List<ProjectTemplateDTO> findAll() {
        return this.getProjectTemplatesSortedByName().stream()
                .map(this::toDTO)
                .toList();
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
        var optionalBlankProjectTemplate = projectTemplates.stream()
                .filter(projectTemplate -> projectTemplate.id().equals(BlankProjectTemplateProvider.BLANK_PROJECT_TEMPLATE_ID))
                .findFirst();

        List<ProjectTemplate> siriusWebProjectTemplate = new ArrayList<>();
        if (optionalBlankProjectTemplate.isPresent()) {
            var blankProjectTemplate = optionalBlankProjectTemplate.get();

            var projectTemplatesWithoutBlank = projectTemplates.stream()
                    .filter(projectTemplate -> !projectTemplate.id().equals(BlankProjectTemplateProvider.BLANK_PROJECT_TEMPLATE_ID))
                    .toList();
            projectTemplates = new ArrayList<>();
            projectTemplates.addAll(projectTemplatesWithoutBlank);
            siriusWebProjectTemplate.add(blankProjectTemplate);
        }

        this.getUploadProject().ifPresent(siriusWebProjectTemplate::add);
        this.getBrowseAllProjectTemplates().ifPresent(siriusWebProjectTemplate::add);

        int startIndex = (int) pageable.getOffset() * pageable.getPageSize();
        int endIndex = Math.min(((int) pageable.getOffset() + 1) * pageable.getPageSize(), projectTemplates.size() + siriusWebProjectTemplate.size());
        var projectTemplateDTOs = Stream.concat(projectTemplates.subList(startIndex, endIndex - siriusWebProjectTemplate.size()).stream(), siriusWebProjectTemplate.stream())
                .map(projectTemplate -> this.toDTO(projectTemplate))
                .toList();

        return new PageImpl<>(projectTemplateDTOs, pageable, projectTemplates.size());
    }

    private Page<ProjectTemplateDTO> handleProjectTemplateModal(Pageable pageable) {
        var projectTemplates = this.getProjectTemplatesSortedByName();

        int startIndex = (int) pageable.getOffset() * pageable.getPageSize();
        int endIndex = Math.min(((int) pageable.getOffset() + 1) * pageable.getPageSize(), projectTemplates.size());
        var projectTemplateDTOs = projectTemplates.subList(startIndex, endIndex).stream()
                .map(projectTemplate -> this.toDTO(projectTemplate))
                .toList();

        return new PageImpl<>(projectTemplateDTOs, pageable, projectTemplates.size());
    }

    private ProjectTemplateDTO toDTO(ProjectTemplate projectTemplate) {
        return new ProjectTemplateDTO(projectTemplate.id(), projectTemplate.label(), projectTemplate.imageURL());
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
}
