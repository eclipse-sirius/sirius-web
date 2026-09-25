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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.components.EdgeAppearance;
import org.eclipse.sirius.components.diagrams.components.NodeAppearance;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.EditAppearanceEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.INodeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.IEdgeAppearanceChange;
import org.eclipse.sirius.components.diagrams.renderer.IDiagramAppearanceHandler;
import org.eclipse.sirius.components.diagrams.renderer.IEdgeAppearanceHandler;
import org.eclipse.sirius.components.diagrams.renderer.INodeAppearanceHandler;
import org.eclipse.sirius.components.diagrams.renderer.api.INodeStyleCustomizer;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Used to handle diagram graphical element appearance.
 *
 * @author gcoutable
 */
@Service
public class DiagramAppearanceHandler implements IDiagramAppearanceHandler {

    private final Set<INodeStyleCustomizer> nodeStyleCustomizers;

    private final List<INodeAppearanceHandler> nodeAppearanceHandlers;

    private final List<IEdgeAppearanceHandler> edgeAppearanceHandlers;

    public DiagramAppearanceHandler(Set<INodeStyleCustomizer> nodeStyleCustomizers, List<INodeAppearanceHandler> nodeAppearanceHandlers, List<IEdgeAppearanceHandler> edgeAppearanceHandlers) {
        this.nodeStyleCustomizers = Objects.requireNonNull(nodeStyleCustomizers);
        this.nodeAppearanceHandlers = Objects.requireNonNull(nodeAppearanceHandlers);
        this.edgeAppearanceHandlers = Objects.requireNonNull(edgeAppearanceHandlers);
    }

    @Override
    public NodeAppearance getNodeAppearance(VariableManager variableManager, DiagramDescription diagramDescription, NodeDescription nodeDescription, List<IDiagramEvent> diagramEvents, String nodeId, Optional<Node> optionalPreviousNode) {
        Optional<NodeAppearance> optionalPreviousAppearance = optionalPreviousNode.map(previousNode ->
                new NodeAppearance(previousNode.getStyle(), previousNode.getCustomizedStyleProperties())
        );

        INodeStyle providedStyle = nodeDescription.getStyleProvider().apply(variableManager);

        for (var nodeStyleCustomizer: this.nodeStyleCustomizers) {
            providedStyle = nodeStyleCustomizer.customize(variableManager, diagramDescription, nodeDescription, providedStyle);
        }

        INodeStyle customizedStyle = providedStyle;

        List<INodeAppearanceChange> appearanceChanges = diagramEvents.stream()
                .filter(EditAppearanceEvent.class::isInstance)
                .map(EditAppearanceEvent.class::cast)
                .flatMap(appearanceEvent -> appearanceEvent.changes().stream())
                .filter(INodeAppearanceChange.class::isInstance)
                .map(INodeAppearanceChange.class::cast)
                .filter(appearanceChange -> Objects.equals(nodeId, appearanceChange.nodeId()))
                .toList();

        return this.nodeAppearanceHandlers.stream()
                .filter(handler -> handler.canHandle(customizedStyle))
                .findFirst()
                .map(handler -> handler.handle(customizedStyle, appearanceChanges, optionalPreviousAppearance))
                .orElse(new NodeAppearance(customizedStyle, new LinkedHashSet<>()));
    }

    @Override
    public EdgeAppearance getEdgeAppearance(VariableManager variableManager, EdgeDescription edgeDescription, List<IDiagramEvent> diagramEvents, String edgeId, Optional<Edge> optionalPreviousEdge) {
        Optional<EdgeAppearance> optionalPreviousAppearance = optionalPreviousEdge.map(previousEdge ->
                new EdgeAppearance(previousEdge.getStyle(), previousEdge.getCustomizedStyleProperties())
        );

        EdgeStyle providedStyle = edgeDescription.getStyleProvider().apply(variableManager);

        List<IEdgeAppearanceChange> appearanceChanges = diagramEvents.stream()
                .filter(EditAppearanceEvent.class::isInstance)
                .map(EditAppearanceEvent.class::cast)
                .flatMap(appearanceEvent -> appearanceEvent.changes().stream())
                .filter(IEdgeAppearanceChange.class::isInstance)
                .map(IEdgeAppearanceChange.class::cast)
                .filter(appearanceChange -> Objects.equals(edgeId, appearanceChange.edgeId()))
                .toList();

        return this.edgeAppearanceHandlers.stream()
                .filter(handler -> handler.canHandle(providedStyle))
                .findFirst()
                .map(handler -> handler.handle(providedStyle, appearanceChanges, optionalPreviousAppearance))
                .orElse(new EdgeAppearance(providedStyle, new LinkedHashSet<>()));
    }
}
