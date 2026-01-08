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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.eclipse.sirius.web.view.fork.dto.CreateForkedStudioInput;
import org.eclipse.sirius.web.view.fork.services.api.IForkedViewSemanticDataInitializer;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Service used to insert needed document in a forked project.
 *
 * @author mcharfadi
 */
@Service
public class ForkedStudioSemanticDataCreationService {

    private final IIdentityService identityService;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IURLParser urlParser;

    private final IEditingContextSearchService editingContextSearchService;

    private final IForkedViewSemanticDataInitializer forkedViewSemanticDataInitializer;

    public ForkedStudioSemanticDataCreationService(IIdentityService identityService, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IRepresentationMetadataSearchService representationMetadataSearchService, IURLParser urlParser, IEditingContextSearchService editingContextSearchService, IForkedViewSemanticDataInitializer forkedViewSemanticDataInitializer) {
        this.identityService = Objects.requireNonNull(identityService);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.forkedViewSemanticDataInitializer = Objects.requireNonNull(forkedViewSemanticDataInitializer);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataCreatedEvent(SemanticDataCreatedEvent semanticDataCreatedEvent) {
        if (semanticDataCreatedEvent.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent
                && projectCreatedEvent.causedBy() instanceof CreateForkedStudioInput createdForkedStudioInput) {
            AggregateReference<Project, String> projectId = AggregateReference.to(projectCreatedEvent.project().getId());
            var editingContextId = createdForkedStudioInput.editingContextId();
            var representationId = createdForkedStudioInput.representationId();

            this.updateDocument(projectCreatedEvent, projectId, editingContextId, representationId);
        }
    }

    private void updateDocument(ICause cause, AggregateReference<Project, String> projectId, String editingContextId, String representationId) {
        var optionalRepresentationMetadata = new UUIDParser().parse(editingContextId)
                .flatMap(semanticDataId -> representationMetadataSearchService.findMetadataById(AggregateReference.to(semanticDataId), UUID.fromString(representationId)));
        var optionalEditingContext = editingContextSearchService.findById(editingContextId);

        if (optionalRepresentationMetadata.isPresent() && optionalEditingContext.isPresent()) {
            var representationMetadata = optionalRepresentationMetadata.get();
            var editingContext = optionalEditingContext.get();

            var representationDescriptionId = representationMetadata.getDescriptionId();
            var optionalSourceId = this.getSourceId(representationDescriptionId);
            var optionalSourceElementId = this.getSourceElementId(representationDescriptionId);
            var newSourceElementId = UUID.randomUUID().toString();

            if (optionalSourceId.isPresent() && optionalSourceElementId.isPresent()) {
                var sourceId = optionalSourceId.get();
                var sourceElementId = optionalSourceElementId.get();

                var optionalRepresentationDescription = this.viewRepresentationDescriptionSearchService.findViewsBySourceId(editingContext, sourceId).stream()
                        .flatMap(view -> view.getDescriptions().stream())
                        .filter(description -> identityService.getId(description).equals(sourceElementId))
                        .findFirst();

                if (optionalRepresentationDescription.isPresent()) {
                    var representationDescription = optionalRepresentationDescription.get();

                    this.forkedViewSemanticDataInitializer.initialize(cause, projectId, representationDescription, representationDescriptionId, sourceId, sourceElementId, newSourceElementId);
                }
            }
        }
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }
}
