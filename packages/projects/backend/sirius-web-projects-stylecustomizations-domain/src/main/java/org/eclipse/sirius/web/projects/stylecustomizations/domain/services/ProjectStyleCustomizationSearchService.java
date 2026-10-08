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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.projects.stylecustomizations.domain.ProjectStyleCustomization;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.repositories.IProjectStyleCustomizationRepository;
import org.eclipse.sirius.web.projects.domain.Project;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve project style customizations.
 *
 * @author gcoutable
 */
@Service
public class ProjectStyleCustomizationSearchService implements IProjectStyleCustomizationSearchService {

    private final IProjectStyleCustomizationRepository projectStyleCustomizationRepository;

    public ProjectStyleCustomizationSearchService(IProjectStyleCustomizationRepository projectStyleCustomizationRepository) {
        this.projectStyleCustomizationRepository = Objects.requireNonNull(projectStyleCustomizationRepository);
    }

    @Override
    public boolean existsByProjectIdAndStyleCustomizationDescriptionId(AggregateReference<Project, String> projectReference, String styleCustomizationDescriptionId) {
        return this.projectStyleCustomizationRepository.existsByProjectIdAndStyleCustomizationDescriptionId(projectReference.getId(), styleCustomizationDescriptionId);
    }

    @Override
    public List<ProjectStyleCustomization> findAllByProjectId(AggregateReference<Project, String> projectReference) {
        return this.projectStyleCustomizationRepository.findAllByProjectId(projectReference.getId());
    }
}
