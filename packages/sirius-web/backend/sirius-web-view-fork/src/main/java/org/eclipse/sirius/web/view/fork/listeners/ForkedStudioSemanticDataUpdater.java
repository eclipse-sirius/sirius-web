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
package org.eclipse.sirius.web.view.fork.listeners;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.eclipse.sirius.web.view.fork.dto.CreateForkedStudioInput;
import org.eclipse.sirius.web.view.fork.dto.ForkSemanticDataUpdatedEvent;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Service used to update the representation_metadata description id after the fork of a view model.
 *
 * @author mcharfadi
 */
@Service
public class ForkedStudioSemanticDataUpdater {

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationMetadataUpdateService representationMetadataUpdateService;

    public ForkedStudioSemanticDataUpdater(IProjectSemanticDataSearchService projectSemanticDataSearchService, IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationMetadataUpdateService representationMetadataUpdateService) {
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationMetadataUpdateService = Objects.requireNonNull(representationMetadataUpdateService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataUpdatedEvent(SemanticDataUpdatedEvent semanticDataUpdatedEvent) {
        if (semanticDataUpdatedEvent.causedBy() instanceof ForkSemanticDataUpdatedEvent forkSemanticDataUpdatedEvent
                && forkSemanticDataUpdatedEvent.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent
                && projectCreatedEvent.causedBy() instanceof CreateForkedStudioInput createdForkedStudioInput) {
            var representationId = createdForkedStudioInput.representationId();
            var newDescriptionId = this.getNewRepresentationDescriptionId(forkSemanticDataUpdatedEvent);

            var optionalSemanticDataId = new UUIDParser().parse(createdForkedStudioInput.editingContextId());
            if (optionalSemanticDataId.isPresent()) {
                var semanticDataId = optionalSemanticDataId.get();
                var representationMetadata = representationMetadataSearchService.findMetadataById(AggregateReference.to(semanticDataId), UUID.fromString(representationId));
                representationMetadata.ifPresent(metadata -> this.representationMetadataUpdateService.updateDescriptionId(semanticDataUpdatedEvent, AggregateReference.to(semanticDataId), metadata.getRepresentationMetadataId(), newDescriptionId));
            }
        }
    }

    private String getNewRepresentationDescriptionId(ForkSemanticDataUpdatedEvent forkSemanticDataUpdatedEvent) {
        var previousDescriptionId = forkSemanticDataUpdatedEvent.previousDescriptionId();
        var previousSourceId = forkSemanticDataUpdatedEvent.previousSourceId();
        var previousSourceElementId = forkSemanticDataUpdatedEvent.previousSourceElementId();
        var newSourceElementId = forkSemanticDataUpdatedEvent.newSourceElementId();
        var newSourceId = forkSemanticDataUpdatedEvent.newSourceId();
        return previousDescriptionId
                .replace(previousSourceId, newSourceId)
                .replace(previousSourceElementId, newSourceElementId);
    }

}
