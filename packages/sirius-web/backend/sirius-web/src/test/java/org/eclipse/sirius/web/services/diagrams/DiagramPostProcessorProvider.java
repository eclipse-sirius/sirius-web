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

package org.eclipse.sirius.web.services.diagrams;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramPostProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.events.ArrangeAllEvent;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to provide a IDiagramPostProcessor to customize diagrams.
 *
 * @author lfasani
 */
@Service
@Conditional(OnStudioTests.class)
public class DiagramPostProcessorProvider implements IDiagramPostProcessor {

    public static final String DIAGRAM_WITH_POST_PROCESSOR_NAME = "DiagramUsingIDiagramPostProcessor";
    public static final String TEXT_AFTER_ARRANGE_ALL = "textAfterArrangeAll";
    public static final String TEXT_AFTER_REFRESH = "textAfterRefresh";

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public DiagramPostProcessorProvider(IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.representationMetadataSearchService = representationMetadataSearchService;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext) {
        var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
        if (optionalSemanticDataId.isPresent()) {
            var semanticDataId = optionalSemanticDataId.get();
            return this.representationMetadataSearchService.findMetadataById(AggregateReference.to(semanticDataId), UUID.fromString(diagramContext.diagram().getId()))
                    .map(representationMetadata -> representationMetadata.getLabel())
                    .filter(label -> DIAGRAM_WITH_POST_PROCESSOR_NAME.equals(label))
                    .isPresent();
        }
        return false;
    }

    @Override
    public Optional<Diagram> postProcess(IEditingContext editingContext, DiagramContext diagramContext) {
        String nodeLabel = diagramContext.diagramEvents().stream()
                .filter(ArrangeAllEvent.class::isInstance)
                .findAny()
                .map(de -> TEXT_AFTER_ARRANGE_ALL)
                .orElse(TEXT_AFTER_REFRESH);

        return Optional.of(Diagram.newDiagram(diagramContext.diagram())
                .nodes(List.of(Node.newNode(diagramContext.diagram().getNodes().get(0))
                        .insideLabel(InsideLabel.newInsideLabel(diagramContext.diagram().getNodes().get(0).getInsideLabel())
                                .text(nodeLabel)
                                .build())
                        .build()))
                .edges(List.of())
                .build());
    }
}
