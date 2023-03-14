/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

package org.eclipse.sirius.components.diagrams.layout;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.layout.api.IDiagramLayoutConfigurationProvider;
import org.eclipse.sirius.components.diagrams.layout.api.configuration.DiagramLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.configuration.EdgeLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.configuration.IParentLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.configuration.NodeLayoutConfiguration;
import org.springframework.stereotype.Service;

/**
 * Used to provide the diagram layout configuration.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramLayoutConfigurationProvider implements IDiagramLayoutConfigurationProvider {

    private IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public DiagramLayoutConfigurationProvider(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public DiagramLayoutConfiguration getDiagramLayoutConfiguration(IEditingContext editingContext, Diagram diagram) {
        var diagramDescriptionLabel = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .map(DiagramDescription::getLabel)
                .orElse("<unknown description>");
        var displayName = "[diagram] " + diagramDescriptionLabel + "::" + diagram.getLabel();

        var diagramLayoutConfiguration = DiagramLayoutConfiguration.newDiagramLayoutConfiguration(diagram.getId())
                .displayName(displayName)
                .previousDiagramLayoutData(diagram.getLayoutData())
                .build();

        diagram.getNodes().stream()
                .map(node -> this.convertNode(diagramLayoutConfiguration, node))
                .forEach(diagramLayoutConfiguration.getChildNodeLayoutConfigurations()::add);

        diagram.getEdges().stream()
                .map(edge -> this.convertEdge(diagramLayoutConfiguration, edge))
                .forEach(diagramLayoutConfiguration.getEdgeLayoutConfigurations()::add);

        return diagramLayoutConfiguration;
    }

    private NodeLayoutConfiguration convertNode(IParentLayoutConfiguration parentLayoutConfiguration, Node node) {
        String containmentKind = "child node";
        if (node.isBorderNode()) {
            containmentKind = "border node";
        }
        var nodeLabel = Optional.ofNullable(node.getLabel()).map(Label::getText).orElse("<no label>");
        var displayName = parentLayoutConfiguration.getDisplayName() + " > [" + containmentKind + "] " + nodeLabel;

        var nodeLayoutConfiguration = NodeLayoutConfiguration.newNodeLayoutConfiguration(node.getId())
                .displayName(displayName)
                .parentLayoutConfiguration(parentLayoutConfiguration)
                .build();

        node.getChildNodes().stream()
                .map(childNode -> this.convertNode(nodeLayoutConfiguration, childNode))
                .forEach(nodeLayoutConfiguration.getChildNodeLayoutConfigurations()::add);

        node.getBorderNodes().stream()
                .map(borderNode -> this.convertNode(nodeLayoutConfiguration, borderNode))
                .forEach(nodeLayoutConfiguration.getBorderNodeLayoutConfigurations()::add);

        return nodeLayoutConfiguration;
    }

    private EdgeLayoutConfiguration convertEdge(DiagramLayoutConfiguration diagramLayoutConfiguration, Edge edge) {
        var edgeLabel = Optional.ofNullable(edge.getCenterLabel()).map(Label::getText).orElse("<no label>");
        var displayName = diagramLayoutConfiguration.getDisplayName() + " > [edge] " + edgeLabel;

        var edgeLayoutConfiguration = EdgeLayoutConfiguration.newEdgeLayoutConfiguration(edge.getId())
                .displayName(displayName)
                .parentLayoutConfiguration(diagramLayoutConfiguration)
                .build();

        return edgeLayoutConfiguration;
    }
}
