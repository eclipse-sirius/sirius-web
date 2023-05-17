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

package org.eclipse.sirius.components.diagrams.layout.experimental;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ParametricSVGNodeStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.DiagramLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.EdgeLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.FontStyle;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.IDiagramLayoutConfigurationProvider;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.IParentLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.IconLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.LabelLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.NodeLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.NodeLayoutStrategy;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.Offsets;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.springframework.stereotype.Service;

/**
 * Used to provide the diagram layout configuration.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramLayoutConfigurationProvider implements IDiagramLayoutConfigurationProvider {

    @Override
    public DiagramLayoutConfiguration getDiagramLayoutConfiguration(Diagram diagram, DiagramLayoutData previousLayoutData, Optional<IDiagramEvent> optionalDiagramEvent) {
        var builder = DiagramLayoutConfiguration.newDiagramLayoutConfiguration(diagram.getId())
                .displayName(String.format("[diagram] %s", diagram.getLabel()))
                .previousLayoutData(previousLayoutData);

        optionalDiagramEvent.ifPresent(builder::diagramEvent);

        var diagramLayoutConfiguration = builder.build();

        diagram.getNodes().stream()
            .map(node -> this.convertNode(diagramLayoutConfiguration, node))
            .forEach(diagramLayoutConfiguration.childNodeLayoutConfigurations()::add);

        diagram.getEdges().stream()
            .map(edge -> this.convertEdge(diagramLayoutConfiguration, edge))
            .forEach(diagramLayoutConfiguration.edgeLayoutConfigurations()::add);

        for (var configuration : diagramLayoutConfiguration.childNodeLayoutConfigurations()) {
            diagramLayoutConfiguration.nodeLayoutConfigurationsById().put(configuration.id(), configuration);
            this.indexNodeLayoutConfigurations(diagramLayoutConfiguration.nodeLayoutConfigurationsById(), configuration);
        }
        for (var configuration: diagramLayoutConfiguration.edgeLayoutConfigurations()) {
            diagramLayoutConfiguration.edgeLayoutConfigurationsById().put(configuration.id(), configuration);
        }

        return diagramLayoutConfiguration;
    }

    private void indexNodeLayoutConfigurations(Map<String, NodeLayoutConfiguration> nodeLayoutConfigurationsById, NodeLayoutConfiguration nodeLayoutConfiguration) {
        for (var configuration : nodeLayoutConfiguration.childNodeLayoutConfigurations()) {
            nodeLayoutConfigurationsById.put(configuration.id(), configuration);
            this.indexNodeLayoutConfigurations(nodeLayoutConfigurationsById, configuration);
        }
        for (var configuration : nodeLayoutConfiguration.borderNodeLayoutConfigurations()) {
            nodeLayoutConfigurationsById.put(configuration.id(), configuration);
            this.indexNodeLayoutConfigurations(nodeLayoutConfigurationsById, configuration);
        }
    }

    private NodeLayoutConfiguration convertNode(IParentLayoutConfiguration parentLayoutConfiguration, Node node) {
        String containmentKind = "child";
        if (node.isBorderNode()) {
            containmentKind = "border";
        }
        var nodeLabel = Optional.ofNullable(node.getLabel()).map(Label::getText).orElse("nolabel");
        var displayName = String.format("%s > [%s node] %s", parentLayoutConfiguration.displayName(), containmentKind, nodeLabel);

        var builder = NodeLayoutConfiguration.newNodeLayoutConfiguration(node.getId())
                .displayName(displayName)
                .parentLayoutConfiguration(parentLayoutConfiguration)
                .border(this.getBorderSize(node.getStyle()))
                .padding(Offsets.of(12.0))
                .margin(Offsets.of(25.0))
                .minimumSize(new Size(150, 70))
                .layoutStrategy(this.getLayoutStrategy(node));

        Optional.ofNullable(node.getLabel()).map(this::convertLabel).ifPresent(builder::labelLayoutConfiguration);

        var nodeLayoutConfiguration = builder.build();

        node.getChildNodes().stream()
            .map(childNode -> this.convertNode(nodeLayoutConfiguration, childNode))
            .forEach(nodeLayoutConfiguration.childNodeLayoutConfigurations()::add);

        node.getBorderNodes().stream()
            .map(borderNode -> this.convertNode(nodeLayoutConfiguration, borderNode))
            .forEach(nodeLayoutConfiguration.borderNodeLayoutConfigurations()::add);

        return nodeLayoutConfiguration;
    }

    private Offsets getBorderSize(INodeStyle style) {
        Offsets border = Offsets.empty();
        if (style instanceof RectangularNodeStyle rectangularNodeStyle) {
            border = Offsets.of(rectangularNodeStyle.getBorderSize());
        } else if (style instanceof ImageNodeStyle imageNodeStyle) {
            border = Offsets.of(imageNodeStyle.getBorderSize());
        } else if (style instanceof ParametricSVGNodeStyle parametricSvgNodeStyle) {
            border = Offsets.of(parametricSvgNodeStyle.getBorderSize());
        }
        return border;
    }

    private NodeLayoutStrategy getLayoutStrategy(Node node) {
        if (node.getChildrenLayoutStrategy() != null && Objects.equals(node.getChildrenLayoutStrategy().getKind() , ListLayoutStrategy.KIND)) {
            return NodeLayoutStrategy.LIST;
        } else {
            return NodeLayoutStrategy.FREEFORM;
        }
    }

    private EdgeLayoutConfiguration convertEdge(DiagramLayoutConfiguration diagramLayoutConfiguration, Edge edge) {
        var edgeLabel = Optional.ofNullable(edge.getCenterLabel())
                .map(Label::getText)
                .orElse("<no label>");

        var builder = EdgeLayoutConfiguration.newEdgeLayoutConfiguration(edge.getId())
                .displayName(String.format("%s > [edge] %s", diagramLayoutConfiguration.displayName(), edgeLabel))
                .parentLayoutConfiguration(diagramLayoutConfiguration)
                .width(edge.getStyle().getSize());

        Optional.ofNullable(edge.getBeginLabel()).ifPresent(label -> builder.beginLabelLayoutConfiguration(this.convertLabel(label)));
        Optional.ofNullable(edge.getCenterLabel()).ifPresent(label -> builder.centerLabelLayoutConfiguration(this.convertLabel(label)));
        Optional.ofNullable(edge.getEndLabel()).ifPresent(label -> builder.endLabelLayoutConfiguration(this.convertLabel(label)));

        return builder.build();
    }

    public LabelLayoutConfiguration convertLabel(Label label) {
        var labelStyle = label.getStyle();
        var fontStyle = new FontStyle(labelStyle.isBold(), labelStyle.isItalic(), labelStyle.isUnderline(), labelStyle.isStrikeThrough());
        return LabelLayoutConfiguration.newLabelLayoutConfiguration(label.getId())
                .text(label.getText())
                .fontSize(labelStyle.getFontSize())
                .fontStyle(fontStyle)
                .border(Offsets.empty())
                .padding(Offsets.of(5.0))
                .margin(Offsets.of(5.0))
                .iconLayoutConfiguration(new IconLayoutConfiguration(new Size(16, 16), 10.0))
                .build();
    }
}
