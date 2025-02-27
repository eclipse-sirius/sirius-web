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

import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Service used to initialize the project semantic data.
 *
 * @author mcharfadi
 */
@Service
public class ProjectSemanticDataInitializer {

    private final IProjectSemanticDataCreationService projectSemanticDataCreationService;

    public ProjectSemanticDataInitializer(IProjectSemanticDataCreationService projectSemanticDataCreationService) {
        this.projectSemanticDataCreationService = Objects.requireNonNull(projectSemanticDataCreationService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataCreatedEvent(SemanticDataCreatedEvent event) {
        if (event.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent) {
            this.projectSemanticDataCreationService.create(event, AggregateReference.to(projectCreatedEvent.project().getId()), AggregateReference.to(event.semanticData().getId()), "main");
        }
    }
}
