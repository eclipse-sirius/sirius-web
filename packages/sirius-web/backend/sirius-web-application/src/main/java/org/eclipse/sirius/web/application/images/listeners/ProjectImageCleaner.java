/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.application.images.listeners;

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services.api.IProjectImageDeletionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to delete a project image.
 *
 * @author ntinsalhi
 */
@Service
public class ProjectImageCleaner {

    private final IProjectImageDeletionService projectImageDeletionService;

    public ProjectImageCleaner(IProjectImageDeletionService projectImageDeletionService) {
        this.projectImageDeletionService = Objects.requireNonNull(projectImageDeletionService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onProjectDeletedEvent(ProjectDeletedEvent event) {
        var projectId = event.project().getId();
        this.projectImageDeletionService.deleteAllProjectImages(event, projectId);
    }
}