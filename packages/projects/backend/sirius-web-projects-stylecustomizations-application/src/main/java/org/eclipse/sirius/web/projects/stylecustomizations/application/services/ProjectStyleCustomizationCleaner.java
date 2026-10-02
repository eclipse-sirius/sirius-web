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
package org.eclipse.sirius.web.projects.stylecustomizations.application.services;

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletedEvent;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationDeletionService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to delete project style customizations when the project they are associated with is deleted.
 *
 * @author gcoutable
 */
@Service
public class ProjectStyleCustomizationCleaner {

    private final IProjectStyleCustomizationDeletionService projectStyleCustomizationDeletionService;

    public ProjectStyleCustomizationCleaner(IProjectStyleCustomizationDeletionService projectStyleCustomizationDeletionService) {
        this.projectStyleCustomizationDeletionService = Objects.requireNonNull(projectStyleCustomizationDeletionService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onProjectDeletedEvent(ProjectDeletedEvent projectDeletedEvent) {
        // TODO: ajouter un test d'integration
        var projectId = projectDeletedEvent.project().getId();
        this.projectStyleCustomizationDeletionService.deleteProjectStyleCustomizationsByProjectId(projectDeletedEvent, AggregateReference.to(projectId));
    }
}
