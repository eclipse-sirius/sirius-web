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
package org.eclipse.sirius.web.application.project.services;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to convert project ids into editing context id and vice versa.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectEditingContextService implements IProjectEditingContextService {

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    public ProjectEditingContextService(IProjectSemanticDataSearchService projectSemanticDataSearchService) {
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
    }

    @Override
    public Optional<String> getEditingContextId(String projectId) {
        return this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(projectId))
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .map(UUID::toString);
    }

    @Override
    public Optional<String> getEditingContextId(String projectId, String name) {
        return this.projectSemanticDataSearchService.findByProjectIdAndName(AggregateReference.to(projectId), name)
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .map(UUID::toString);
    }

    @Override
    public Optional<String> getProjectId(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .flatMap(semanticDataId -> this.projectSemanticDataSearchService.findBySemanticDataId(AggregateReference.to(semanticDataId)))
                .map(ProjectSemanticData::getProject)
                .map(AggregateReference::getId);
    }
}
