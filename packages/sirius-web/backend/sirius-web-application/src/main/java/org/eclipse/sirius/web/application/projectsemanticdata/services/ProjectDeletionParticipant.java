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
package org.eclipse.sirius.web.application.projectsemanticdata.services;

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletionRequestedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataDeletionService;
import org.springframework.context.event.EventListener;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to delete the semantic data of a project.
 *
 * @technical-debt This event listener is not connected to the lifecycle of the application using the regular approach,
 * for this reason, it should not be considered as a good example. It will need to be updated when the small hack used
 * during the deletion of a project will be deleted.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectDeletionParticipant {

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final ISemanticDataDeletionService semanticDataDeletionService;

    public ProjectDeletionParticipant(IProjectSemanticDataSearchService projectSemanticDataSearchService, ISemanticDataDeletionService semanticDataDeletionService) {
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.semanticDataDeletionService = Objects.requireNonNull(semanticDataDeletionService);
    }

    @EventListener
    public void onProjectDeletionRequestedEvent(ProjectDeletionRequestedEvent event) {
        var allSemanticDataIds = this.projectSemanticDataSearchService.findAllByProjectId(AggregateReference.to(event.project().getId())).stream()
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .toList();
        this.semanticDataDeletionService.deleteAllById(allSemanticDataIds);
    }
}
