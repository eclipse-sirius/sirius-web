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
package org.eclipse.sirius.web.application.project.listeners;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.application.project.services.CopySemanticDataCause;
import org.eclipse.sirius.web.application.project.services.InitializeProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectContentImportParticipant;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Transactional listener in charge of importing project content when a new project is created with some initial content.
 *
 * @author Arthur Daussy
 */
@Service
public class ProjectContentImporter {

    private final List<IProjectContentImportParticipant> contentImportParticipants;

    public ProjectContentImporter(List<IProjectContentImportParticipant> contentImportParticipants) {
        this.contentImportParticipants = Objects.requireNonNull(contentImportParticipants);
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onProjectSemanticDataCreatedEvent(ProjectSemanticDataCreatedEvent event) {
        if (event.causedBy() instanceof SemanticDataCreatedEvent semanticDataCreatedEvent
                && semanticDataCreatedEvent.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent
                && projectCreatedEvent.causedBy() instanceof InitializeProjectInput initializeProjectInput) {
            this.contentImportParticipants.stream()
                    .filter(participant -> participant.canHandle(event))
                    .forEach(participant -> participant.handle(event, initializeProjectInput.projectContent()));
        }
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onSemanticDataUpdatedEvent(SemanticDataUpdatedEvent event) {
        if (event.causedBy() instanceof CopySemanticDataCause copySemanticDataCause
                && copySemanticDataCause.causedBy() instanceof InitializeProjectInput initializeProjectInput) {
            this.contentImportParticipants.stream()
                    .filter(participant -> participant.canHandle(event))
                    .forEach(participant -> participant.handle(event, initializeProjectInput.projectContent()));
        }
    }
}
