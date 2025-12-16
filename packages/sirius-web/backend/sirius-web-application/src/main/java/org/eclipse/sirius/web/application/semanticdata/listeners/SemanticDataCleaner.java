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
package org.eclipse.sirius.web.application.semanticdata.listeners;

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataDeletionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Service used to delete the semantic data of a project.
 *
 * @author ntinsalhi
 */
@Service
public class SemanticDataCleaner {

    private final ISemanticDataDeletionService semanticDataDeletionService;

    public SemanticDataCleaner(ISemanticDataDeletionService semanticDataDeletionService) {
        this.semanticDataDeletionService = Objects.requireNonNull(semanticDataDeletionService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataDeletedEvent(ProjectSemanticDataDeletedEvent event) {
        var semanticDataId = event.projectSemanticData().getSemanticData().getId();
        this.semanticDataDeletionService.deleteSemanticData(event, semanticDataId);
    }
}
