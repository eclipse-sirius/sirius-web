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
package org.eclipse.sirius.web.application.studio.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.studio.services.representations.api.IDomainDiagramDescriptionProvider;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to create the representation of a newly created studio project.
 *
 * @author sbegaudeau
 */
@Service
public class StudioRepresentationInitializer {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IDomainDiagramDescriptionProvider domainDiagramDescriptionProvider;

    public StudioRepresentationInitializer(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramCreationService diagramCreationService, IRepresentationMetadataPersistenceService representationMetadataPersistenceService, IRepresentationPersistenceService representationPersistenceService, IDomainDiagramDescriptionProvider domainDiagramDescriptionProvider) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.representationMetadataPersistenceService = Objects.requireNonNull(representationMetadataPersistenceService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.domainDiagramDescriptionProvider = Objects.requireNonNull(domainDiagramDescriptionProvider);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataUpdatedEvent(SemanticDataUpdatedEvent semanticDataUpdatedEvent) {
        if (semanticDataUpdatedEvent.causedBy() instanceof StudioTemplateInitialization studioTemplateInitialization) {
            var editingContext = studioTemplateInitialization.editingContext();
            var domainResource = studioTemplateInitialization.domainResource();

            var optionalDomainDiagramDescription = this.findDomainDiagramDescription(editingContext);
            if (optionalDomainDiagramDescription.isPresent()) {
                DiagramDescription domainDiagramDescription = optionalDomainDiagramDescription.get();
                Object semanticTarget = domainResource.getContents().get(0);

                var variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, semanticTarget);
                variableManager.put(DiagramDescription.LABEL, domainDiagramDescription.getLabel());
                String label = domainDiagramDescription.getLabelProvider().apply(variableManager);
                List<String> iconURLs = domainDiagramDescription.getIconURLsProvider().apply(variableManager);

                Diagram diagram = this.diagramCreationService.create(editingContext, domainDiagramDescription, semanticTarget);
                var representationMetadata = RepresentationMetadata.newRepresentationMetadata(diagram.getId())
                        .kind(diagram.getKind())
                        .label(label)
                        .descriptionId(diagram.getDescriptionId())
                        .iconURLs(iconURLs)
                        .build();

                this.representationMetadataPersistenceService.save(semanticDataUpdatedEvent, editingContext, representationMetadata, diagram.getTargetObjectId());
                this.representationPersistenceService.save(semanticDataUpdatedEvent, editingContext, diagram);
            }
        }
    }

    private Optional<DiagramDescription> findDomainDiagramDescription(IEditingContext editingContext) {
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .filter(diagramDescription -> diagramDescription.getId().equals(this.domainDiagramDescriptionProvider.getDescriptionId()))
                .findFirst();
    }
}
