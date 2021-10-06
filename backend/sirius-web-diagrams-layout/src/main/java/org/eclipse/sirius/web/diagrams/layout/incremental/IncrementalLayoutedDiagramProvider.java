/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.layout.incremental;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.diagrams.CustomizableProperties;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.ILayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;
import org.springframework.stereotype.Service;

/**
 * This class is used to include layout data in an existing diagram by producing a brand new immutable diagram with the
 * proper layout information.
 *
 * @author wpiers
 */
@Service
public class IncrementalLayoutedDiagramProvider {

    public Diagram getLayoutedDiagram(Diagram diagram, DiagramLayoutData diagramLayoutData, Map<String, ILayoutData> id2LayoutData) {
        List<Node> nodes = this.getLayoutedNodes(diagram.getNodes(), id2LayoutData);
        List<Edge> edges = this.getLayoutedEdges(diagram.getEdges(), id2LayoutData);

        // @formatter:off
        return Diagram.newDiagram(diagram)
                .position(diagramLayoutData.getPosition())
                .size(diagramLayoutData.getSize())
                .nodes(nodes)
                .edges(edges)
                .build();
        // @formatter:on
    }

    private List<Node> getLayoutedNodes(List<Node> nodes, Map<String, ILayoutData> id2LayoutData) {
        // @formatter:off
        return nodes.stream().flatMap(node -> {
            return Optional.ofNullable(id2LayoutData.get(node.getId()))
                    .filter(NodeLayoutData.class::isInstance)
                    .map(NodeLayoutData.class::cast)
                    .map(nodeLayoutData -> this.getLayoutedNode(node, nodeLayoutData, id2LayoutData))
                    .stream();
        }).collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    private Node getLayoutedNode(Node node, NodeLayoutData nodeLayoutData, Map<String, ILayoutData> id2LayoutData) {
        Label label = this.getLayoutedLabel(node.getLabel(), id2LayoutData);

        List<Node> childNodes = this.getLayoutedNodes(node.getChildNodes(), id2LayoutData);
        List<Node> borderNodes = this.getLayoutedNodes(node.getBorderNodes(), id2LayoutData);

        Set<CustomizableProperties> customizableProperties = new HashSet<>(node.getCustomizedProperties());
        if (nodeLayoutData.isResizedByUser()) {
            customizableProperties.add(CustomizableProperties.Size);
        }
        // @formatter:off
        return Node.newNode(node)
                .label(label)
                .size(nodeLayoutData.getSize())
                .position(nodeLayoutData.getPosition())
                .childNodes(childNodes)
                .borderNodes(borderNodes)
                .customizedProperties(customizableProperties)
                .build();
        // @formatter:on
    }

    private List<Edge> getLayoutedEdges(List<Edge> edges, Map<String, ILayoutData> id2LayoutData) {
        // @formatter:off
        return edges.stream().flatMap(edge -> {
            return Optional.ofNullable(id2LayoutData.get(edge.getId()))
                    .filter(EdgeLayoutData.class::isInstance)
                    .map(EdgeLayoutData.class::cast)
                    .map(edgeLayoutData -> this.getLayoutedEdge(edge, edgeLayoutData, id2LayoutData))
                    .stream();
        }).collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    private Edge getLayoutedEdge(Edge edge, EdgeLayoutData edgeLayoutData, Map<String, ILayoutData> id2LayoutData) {
        Label beginLabel = edge.getBeginLabel();
        if (beginLabel != null) {
            beginLabel = this.getLayoutedLabel(beginLabel, id2LayoutData);
        }
        Label centerLabel = edge.getCenterLabel();
        if (centerLabel != null) {
            centerLabel = this.getLayoutedLabel(centerLabel, id2LayoutData);
        }
        Label endLabel = edge.getEndLabel();
        if (endLabel != null) {
            endLabel = this.getLayoutedLabel(endLabel, id2LayoutData);
        }

        // @formatter:off
        return Edge.newEdge(edge)
                .beginLabel(beginLabel)
                .centerLabel(centerLabel)
                .endLabel(endLabel)
                .routingPoints(edgeLayoutData.getRoutingPoints())
                .build();
        // @formatter:on
    }

    private Label getLayoutedLabel(Label label, Map<String, ILayoutData> id2LayoutData) {
        Label layoutedLabel = label;
        var optionalLabelLayoutData = Optional.of(id2LayoutData.get(label.getId())).filter(LabelLayoutData.class::isInstance).map(LabelLayoutData.class::cast);
        if (optionalLabelLayoutData.isPresent()) {
            LabelLayoutData labelLayoutData = optionalLabelLayoutData.get();

            // @formatter:off
            layoutedLabel = Label.newLabel(label)
                    .size(labelLayoutData.getTextBounds().getSize())
                    .position(labelLayoutData.getPosition())
                    .alignment(labelLayoutData.getTextBounds().getAlignment())
                    .build();
            // @formatter:on
        }
        return layoutedLabel;
    }
}
