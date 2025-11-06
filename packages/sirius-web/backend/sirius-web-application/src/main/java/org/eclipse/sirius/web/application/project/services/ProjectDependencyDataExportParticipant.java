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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipOutputStream;

import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.project.services.api.IProjectExportParticipant;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to export the dependency data of a project.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectDependencyDataExportParticipant implements IProjectExportParticipant {

    private final ISemanticDataSearchService semanticDataSearchService;

    public ProjectDependencyDataExportParticipant(ISemanticDataSearchService semanticDataSearchService) {
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
    }

    @Override
    public Map<String, Object> exportData(Project project, String editingContextId, ZipOutputStream outputStream) {
        var semanticDataDependencies = new UUIDParser().parse(editingContextId)
                .flatMap(this.semanticDataSearchService::findById)
                .map(SemanticData::getDependencies)
                .orElse(List.of());

        var dependencies = semanticDataDependencies.stream()
                .map(SemanticDataDependency::dependencySemanticDataId)
                .map(AggregateReference::getId)
                .map(Objects::toString)
                .toList();

        return Map.of("dependencies", dependencies);
    }
}
