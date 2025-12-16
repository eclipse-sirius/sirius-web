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
package org.eclipse.sirius.web.application.representation.listeners;

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataDeletionService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataDeletedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to delete a representation metadata.
 *
 * @author ntinsalhi
 */
@Service
public class RepresentationMetadataCleaner {

    private final IRepresentationMetadataDeletionService representationMetadataDeletionService;

    public RepresentationMetadataCleaner(IRepresentationMetadataDeletionService representationMetadataDeletionService) {
        this.representationMetadataDeletionService = Objects.requireNonNull(representationMetadataDeletionService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataDeletedEvent(SemanticDataDeletedEvent event) {
        var semanticDataId = event.semanticData().getId();
        this.representationMetadataDeletionService.deleteRepresentationMetadata(event, semanticDataId);
    }
}