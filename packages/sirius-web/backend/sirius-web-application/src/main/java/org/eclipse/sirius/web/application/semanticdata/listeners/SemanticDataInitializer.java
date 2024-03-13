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
package org.eclipse.sirius.web.application.semanticdata.listeners;

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataCreationService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Service used to create the semantic data of a project.
 *
 * @author sbegaudeau
 */
@Service
public class SemanticDataInitializer {

    private final ISemanticDataCreationService semanticDataCreationService;

    public SemanticDataInitializer(ISemanticDataCreationService semanticDataCreationService) {
        this.semanticDataCreationService = Objects.requireNonNull(semanticDataCreationService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onProjectCreatedEvent(ProjectCreatedEvent event) {
        this.semanticDataCreationService.initialize(event, AggregateReference.to(event.project().getId()));
    }
}
