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
package org.eclipse.sirius.web.view.fork.listeners;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationMetadataUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.eclipse.sirius.web.view.fork.dto.CreateForkedStudioInput;
import org.eclipse.sirius.web.view.fork.dto.ForkSemanticDataUpdatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Service used to update the representation_content ids after the fork of a view model.
 *
 * @author mcharfadi
 */
@Service
public class ForkedStudioRepresentationMetadataUpdater {

    private final IRepresentationContentSearchService representationContentSearchService;

    private final IRepresentationContentUpdateService representationContentUpdateService;

    public ForkedStudioRepresentationMetadataUpdater(IRepresentationContentSearchService representationContentSearchService, IRepresentationContentUpdateService representationContentUpdateService) {
        this.representationContentSearchService = Objects.requireNonNull(representationContentSearchService);
        this.representationContentUpdateService = Objects.requireNonNull(representationContentUpdateService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataUpdatedEvent(RepresentationMetadataUpdatedEvent representationMetadataUpdatedEvent) {
        if (representationMetadataUpdatedEvent.causedBy() instanceof  SemanticDataUpdatedEvent semanticDataUpdatedEvent
                && semanticDataUpdatedEvent.causedBy() instanceof ForkSemanticDataUpdatedEvent forkSemanticDataUpdatedEvent
                && forkSemanticDataUpdatedEvent.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent
                && projectCreatedEvent.causedBy() instanceof CreateForkedStudioInput createdForkedStudioInput) {
            var representationId = createdForkedStudioInput.representationId();
            var previousDescriptionId = forkSemanticDataUpdatedEvent.previousDescriptionId();
            var previousSourceId = forkSemanticDataUpdatedEvent.previousSourceId();
            var previousSourceElementId = forkSemanticDataUpdatedEvent.previousSourceElementId();
            var newSourceElementId = forkSemanticDataUpdatedEvent.newSourceElementId();
            var newSourceId = forkSemanticDataUpdatedEvent.newSourceId();
            var newDescriptionId = previousDescriptionId
                    .replace(previousSourceId, newSourceId)
                    .replace(previousSourceElementId, newSourceElementId);

            var representationContent = this.representationContentSearchService.findContentById(UUID.fromString(representationId));
            if (representationContent.isPresent()) {
                //  Update the descriptionId of the current representation
                var newContent = representationContent.get().getContent()
                        .replace(previousDescriptionId, newDescriptionId)
                        .replace(previousSourceId, newSourceId);

                this.representationContentUpdateService.updateContentByRepresentationId(representationMetadataUpdatedEvent, UUID.fromString(representationId), newContent);
            }
        }
    }
}
