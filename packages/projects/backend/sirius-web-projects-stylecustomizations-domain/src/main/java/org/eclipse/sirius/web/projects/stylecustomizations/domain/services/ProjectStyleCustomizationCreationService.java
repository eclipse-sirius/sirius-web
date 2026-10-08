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
package org.eclipse.sirius.web.projects.stylecustomizations.domain.services;

import java.util.Objects;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.core.domain.results.IResult;
import org.eclipse.sirius.web.core.domain.results.Success;
import org.eclipse.sirius.web.projects.domain.Project;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.ProjectStyleCustomization;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.repositories.IProjectStyleCustomizationRepository;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationCreationService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to create project style customizations.
 *
 * @author gcoutable
 */
@Service
public class ProjectStyleCustomizationCreationService implements IProjectStyleCustomizationCreationService {

    private final IProjectStyleCustomizationRepository projectStyleCustomizationRepository;

    public ProjectStyleCustomizationCreationService(IProjectStyleCustomizationRepository projectStyleCustomizationRepository) {
        this.projectStyleCustomizationRepository = Objects.requireNonNull(projectStyleCustomizationRepository);
    }

    @Override
    public IResult<ProjectStyleCustomization> createProjectStyleCustomization(ICause cause, AggregateReference<Project, String> project, String styleCustomizationDescriptionId) {
        var projectStyleCustomization = ProjectStyleCustomization.newProjectStyleCustomization()
                .project(project)
                .styleCustomizationDescriptionId(styleCustomizationDescriptionId)
                .build(cause);
        this.projectStyleCustomizationRepository.save(projectStyleCustomization);

        return new Success<>(projectStyleCustomization);
    }
}
