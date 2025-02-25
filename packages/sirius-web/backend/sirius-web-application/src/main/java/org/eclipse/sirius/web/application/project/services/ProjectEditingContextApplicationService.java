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

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to interact with editing contexts.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectEditingContextApplicationService implements IProjectEditingContextApplicationService {

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    public ProjectEditingContextApplicationService(IProjectSemanticDataSearchService projectSemanticDataSearchService) {
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
    }
    
    @Override
    @Transactional(readOnly = true)
    public String getCurrentEditingContextId(String projectId) {
        return this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(projectId))
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .map(UUID::toString)
                .orElse("");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> getProjectId(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .flatMap(semanticDataId -> this.projectSemanticDataSearchService.findBySemanticDataId(AggregateReference.to(semanticDataId)))
                .map(ProjectSemanticData::getProject)
                .map(AggregateReference::getId);
    }
}
