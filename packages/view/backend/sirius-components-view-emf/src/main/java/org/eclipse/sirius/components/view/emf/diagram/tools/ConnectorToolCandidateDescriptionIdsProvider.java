/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolCandidateDescriptionIdsProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.diagram.ToolFinder;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IEdgeToolConverter;
import org.springframework.stereotype.Service;

/**
 * Provides the connector tool candidates available from a diagram element described by a View model.
 *
 * @author mcharfadi
 */
@Service
public class ConnectorToolCandidateDescriptionIdsProvider implements IConnectorToolCandidateDescriptionIdsProvider {

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IEdgeToolConverter edgeToolConverter;

    public ConnectorToolCandidateDescriptionIdsProvider(IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IDiagramDescriptionService diagramDescriptionService, IEdgeToolConverter edgeToolConverter) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.edgeToolConverter = Objects.requireNonNull(edgeToolConverter);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, String diagramElementId) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public List<String> getConnectorToolCandidateDescriptionIds(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, Object diagramElement) {
        return this.computeConnectorToolsCandidates(editingContext, diagramDescription, diagramElement);
    }

    private List<String> computeConnectorToolsCandidates(IEditingContext editingContext, DiagramDescription diagramDescription, Object diagramElement) {
        var optionalDiagramElementDescription = this.findDiagramElementDescription(diagramDescription, diagramElement);
        var optionalViewDiagramElementDescription = this.findViewDiagramElementDescription(editingContext, diagramElement);

        if (optionalDiagramElementDescription.isPresent() && optionalViewDiagramElementDescription.isPresent()) {
            var toolFinder = new ToolFinder();
            var diagramElementDescription = optionalDiagramElementDescription.get();
            return toolFinder.findEdgeTools(optionalViewDiagramElementDescription.get()).stream()
                    .flatMap(edgeTool -> this.edgeToolConverter.getConnectorToolsCandidates(edgeTool, diagramDescription, diagramElementDescription).stream())
                    .flatMap(singleClickOnTwoDiagramElementsCandidate -> singleClickOnTwoDiagramElementsCandidate.targets().stream())
                    .map(IDiagramElementDescription::getId)
                    .toList();
        }
        return List.of();
    }

    private Optional<IDiagramElementDescription> findDiagramElementDescription(DiagramDescription diagramDescription, Object diagramElement) {
        Optional<IDiagramElementDescription> optionalDiagramElementDescription = Optional.empty();
        if (diagramElement instanceof Node node) {
            optionalDiagramElementDescription = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, node.getDescriptionId()).map(IDiagramElementDescription.class::cast);
        } else if (diagramElement instanceof Edge edge) {
            optionalDiagramElementDescription = this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, edge.getDescriptionId()).map(IDiagramElementDescription.class::cast);
        }
        return optionalDiagramElementDescription;
    }

    private Optional<DiagramElementDescription> findViewDiagramElementDescription(IEditingContext editingContext, Object diagramElement) {
        Optional<DiagramElementDescription> optionalDiagramElementDescription = Optional.empty();
        if (diagramElement instanceof Node node) {
            optionalDiagramElementDescription = this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, node.getDescriptionId()).map(DiagramElementDescription.class::cast);
        } else if (diagramElement instanceof Edge edge) {
            optionalDiagramElementDescription = this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, edge.getDescriptionId()).map(DiagramElementDescription.class::cast);
        }
        return optionalDiagramElementDescription;
    }
}
