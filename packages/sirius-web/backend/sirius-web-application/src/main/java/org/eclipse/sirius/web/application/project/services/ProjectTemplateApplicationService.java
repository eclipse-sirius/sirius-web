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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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

    public ProjectTemplateApplicationService(List<IProjectTemplateProvider> projectTemplateProviders) {
        this.projectTemplateProviders = Objects.requireNonNull(projectTemplateProviders);
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
}
