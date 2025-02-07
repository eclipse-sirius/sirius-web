/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.repositories.IProjectSemanticDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve the project semantic data.
 *
 * @author mcharfadi
 */
@Service
public class ProjectSemanticDataSearchService implements IProjectSemanticDataSearchService {

    private final IProjectSemanticDataRepository projectSemanticDataRepository;

    public ProjectSemanticDataSearchService(IProjectSemanticDataRepository projectSemanticDataRepository) {
        this.projectSemanticDataRepository = Objects.requireNonNull(projectSemanticDataRepository);
    }

    @Override
    public Optional<ProjectSemanticData> findByProjectId(AggregateReference<Project, String> project) {
        return this.findByProjectIdAndName(project, "main");
    }

    @Override
    public List<ProjectSemanticData> findAllByProjectId(AggregateReference<Project, String> project) {
        return this.projectSemanticDataRepository.findAllByProjectId(project.getId());
    }

    @Override
    public Optional<ProjectSemanticData> findByProjectIdAndName(AggregateReference<Project, String> project, String name) {
        return this.projectSemanticDataRepository.findByProjectIdAndName(project.getId(), name);
    }

    @Override
    public Optional<ProjectSemanticData> findBySemanticDataId(AggregateReference<Project, UUID> semanticData) {
        return this.projectSemanticDataRepository.findBySemanticDataId(semanticData.getId());
    }
}
