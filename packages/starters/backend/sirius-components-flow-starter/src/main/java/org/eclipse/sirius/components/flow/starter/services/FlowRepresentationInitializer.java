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
package org.eclipse.sirius.components.flow.starter.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to initialize the representations of a project created with the flow template.
 *
 * @author sbegaudeau
 */
@Service
public class FlowRepresentationInitializer {

    private static final String TOPOGRAPHY_VIEW_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=7256a8d7-581c-30c7-88ee-c5c95822f3d7";

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    private final IRepresentationPersistenceService representationPersistenceService;

    public FlowRepresentationInitializer(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramCreationService diagramCreationService, IRepresentationMetadataPersistenceService representationMetadataPersistenceService, IRepresentationPersistenceService representationPersistenceService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.representationMetadataPersistenceService = Objects.requireNonNull(representationMetadataPersistenceService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataUpdatedEvent(SemanticDataUpdatedEvent semanticDataUpdatedEvent) {
        if (semanticDataUpdatedEvent.causedBy() instanceof FlowTemplateInitialization flowTemplateInitialization) {
            var editingContext = flowTemplateInitialization.editingContext();

            var optionalResource = editingContext.getDomain().getResourceSet().getResources().stream().findFirst();

            if (flowTemplateInitialization.templateId().equals(FlowProjectTemplatesProvider.FLOW_TEMPLATE_ID)) {
                var optionalTopographyDiagram = this.representationDescriptionSearchService.findById(editingContext, TOPOGRAPHY_VIEW_DESCRIPTION_ID)
                        .filter(DiagramDescription.class::isInstance)
                        .map(DiagramDescription.class::cast);

                if (optionalResource.isPresent() && optionalTopographyDiagram.isPresent()) {
                    var resource = optionalResource.get();
                    DiagramDescription topographyDiagramDescription = optionalTopographyDiagram.get();
                    Object semanticTarget = resource.getContents().get(0);

                    var variableManager = new VariableManager();
                    variableManager.put(VariableManager.SELF, semanticTarget);
                    variableManager.put(DiagramDescription.LABEL, topographyDiagramDescription.getLabel());
                    String label = topographyDiagramDescription.getLabelProvider().apply(variableManager);
                    List<String> iconURLs = topographyDiagramDescription.getIconURLsProvider().apply(variableManager);

                    Diagram diagram = this.diagramCreationService.create(editingContext, topographyDiagramDescription, semanticTarget);
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
    }
}
