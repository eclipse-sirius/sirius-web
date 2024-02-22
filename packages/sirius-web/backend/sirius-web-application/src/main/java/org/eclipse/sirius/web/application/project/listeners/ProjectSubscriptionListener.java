/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.project.listeners;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.application.project.dto.ProjectRenamedEventPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectSubscriptions;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectNameUpdatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to listen to project events in order to provide events for the project subscriptions.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectSubscriptionListener {

    private final IProjectSubscriptions projectSubscriptions;

    public ProjectSubscriptionListener(IProjectSubscriptions projectSubscriptions) {
        this.projectSubscriptions = Objects.requireNonNull(projectSubscriptions);
    }

    @TransactionalEventListener
    public void onProjectNameUpdatedEvent(ProjectNameUpdatedEvent event) {
        var payload = new ProjectRenamedEventPayload(UUID.randomUUID(), event.project().getId(), event.project().getName());
        this.projectSubscriptions.emit(event.project().getId(), payload);
    }

    @TransactionalEventListener
    public void onProjectDeletedEvent(ProjectDeletedEvent event) {
        this.projectSubscriptions.dispose(event.project().getId());
    }
}
