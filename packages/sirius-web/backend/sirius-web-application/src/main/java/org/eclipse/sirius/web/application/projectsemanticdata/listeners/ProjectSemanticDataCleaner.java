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
package org.eclipse.sirius.web.application.projectsemanticdata.listeners;

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataDeletionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Service used to delete the project semantic data.
 *
 * @author ntinsalhi
 */
@Service
public class ProjectSemanticDataCleaner {

    private final IProjectSemanticDataDeletionService projectSemanticDataDeletionService;

    public ProjectSemanticDataCleaner(IProjectSemanticDataDeletionService projectSemanticDataDeletionService) {
        this.projectSemanticDataDeletionService = Objects.requireNonNull(projectSemanticDataDeletionService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onProjectDeletedEvent(ProjectDeletedEvent event) {
        this.projectSemanticDataDeletionService.deleteProjectSemanticData(event, event.project().getId());
    }
}
