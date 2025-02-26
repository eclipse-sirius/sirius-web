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
package org.eclipse.sirius.web.application.studio.services;

import java.util.Objects;

import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to test if an editing context is capable of supporting a studio.
 *
 * @author sbegaudeau
 */
@Service
public class StudioCapableEditingContextPredicate implements IStudioCapableEditingContextPredicate {

    private final IProjectSearchService projectSearchService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final ISemanticDataSearchService semanticDataSearchService;

    public StudioCapableEditingContextPredicate(IProjectSearchService projectSearchService, IProjectSemanticDataSearchService projectSemanticDataSearchService, ISemanticDataSearchService semanticDataSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
    }

    @Override
    public boolean test(String editingContextId) {
        var isStudioProject = new UUIDParser().parse(editingContextId)
                .flatMap(semanticDataId -> this.projectSemanticDataSearchService.findBySemanticDataId(AggregateReference.to(semanticDataId)))
                .map(ProjectSemanticData::getProject)
                .map(AggregateReference::getId)
                .flatMap(this.projectSearchService::findById)
                .filter(this::isStudio)
                .isPresent();

        var isStudioSemanticData = new UUIDParser().parse(editingContextId)
                .flatMap(this.semanticDataSearchService::findById)
                .filter(semanticData -> semanticData.getDomains().stream()
                        .anyMatch(semanticDataDomain -> semanticDataDomain.uri().equals(ViewPackage.eNS_URI) || semanticDataDomain.uri().equals(DomainPackage.eNS_URI)))
                .isPresent();

        return isStudioProject || isStudioSemanticData;
    }

    private boolean isStudio(Project project) {
        return project.getNatures().stream()
                .map(Nature::name)
                .anyMatch(StudioProjectTemplateProvider.STUDIO_NATURE::equals);
    }
}
