/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.elk.graph.ElkBendPoint;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkEdgeSection;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.springframework.stereotype.Service;

/**
 * This class is used to use both a non-layouted immutable diagram and the result of the computation of the layout to
 * produce a brand new immutable diagram with the proper layout information.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class ELKLayoutedDiagramProvider {

    public Diagram getLayoutedDiagram(Diagram diagram, ElkNode elkDiagram, Map<String, ElkGraphElement> id2ElkGraphElements) {
        Size size = Size.of(elkDiagram.getWidth(), elkDiagram.getHeight());
        Position position = Position.at(elkDiagram.getX(), elkDiagram.getY());

        // @formatter:off
        List<Node> nodes = this.getLayoutedNodes(diagram.getNodes(), id2ElkGraphElements);
        List<Edge> edges = this.getLayoutedEdges(diagram.getEdges(), id2ElkGraphElements);

        return Diagram.newDiagram(diagram)
                .position(position)
                .size(size)
                .nodes(nodes)
                .edges(edges)
                .build();
        // @formatter:on
    }

    private List<Node> getLayoutedNodes(List<Node> nodes, Map<String, ElkGraphElement> id2ElkGraphElements) {
        // @formatter:off
        return nodes.stream().flatMap(node -> {
            return Optional.ofNullable(id2ElkGraphElements.get(node.getId().toString()))
                    .filter(ElkConnectableShape.class::isInstance)
                    .map(ElkConnectableShape.class::cast)
                    .map(elkNode -> this.getLayoutedNode(node, elkNode, id2ElkGraphElements))
                    .stream();
        }).collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    private Node getLayoutedNode(Node node, ElkConnectableShape elkConnectableShape, Map<String, ElkGraphElement> id2ElkGraphElements) {
        Size size = Size.of(elkConnectableShape.getWidth(), elkConnectableShape.getHeight());
        Position position = Position.at(elkConnectableShape.getX(), elkConnectableShape.getY());

        double xOffSet = 0;
        if (!node.isBorderNode()) {
            // The label is positioned at the center of the node and the front-end will apply a "'text-anchor':
            // 'middle'" property.
            xOffSet = node.getLabel().getSize().getWidth() / 2;
        }
        Label label = this.getLayoutedLabel(node.getLabel(), id2ElkGraphElements, xOffSet, 0);

        List<Node> childNodes = this.getLayoutedNodes(node.getChildNodes(), id2ElkGraphElements);
        List<Node> borderNodes = this.getLayoutedNodes(node.getBorderNodes(), id2ElkGraphElements);
        // @formatter:off
        return Node.newNode(node)
                .label(label)
                .size(size)
                .position(position)
                .childNodes(childNodes)
                .borderNodes(borderNodes)
                .build();
        // @formatter:on
    }

    private List<Edge> getLayoutedEdges(List<Edge> edges, Map<String, ElkGraphElement> id2ElkGraphElements) {
        // @formatter:off
        return edges.stream().flatMap(edge -> {
            return Optional.ofNullable(id2ElkGraphElements.get(edge.getId().toString()))
                    .filter(ElkEdge.class::isInstance)
                    .map(ElkEdge.class::cast)
                    .map(elkEdge -> this.getLayoutedEdge(edge, elkEdge, id2ElkGraphElements))
                    .stream();
        }).collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    private Edge getLayoutedEdge(Edge edge, ElkEdge elkEdge, Map<String, ElkGraphElement> id2ElkGraphElements) {
        List<Position> routingPoints = new ArrayList<>();

        ElkNode containingNode = elkEdge.getContainingNode();
        double xOffset = 0;
        double yOffset = 0;
        if (containingNode != null) {
            xOffset = containingNode.getX();
            yOffset = containingNode.getY();
            ElkNode parent = containingNode.getParent();
            while (parent instanceof ElkNode) {
                xOffset += parent.getX();
                yOffset += parent.getY();
                parent = parent.getParent();
            }
        }

        if (!elkEdge.getSections().isEmpty()) {
            ElkEdgeSection section = elkEdge.getSections().get(0);

            Position startPosition = Position.at(xOffset + section.getStartX(), yOffset + section.getStartY());
            routingPoints.add(startPosition);

            for (ElkBendPoint bendPoint : section.getBendPoints()) {
                Position position = Position.at(xOffset + bendPoint.getX(), yOffset + bendPoint.getY());
                routingPoints.add(position);
            }

            Position endPosition = Position.at(xOffset + section.getEndX(), yOffset + section.getEndY());
            routingPoints.add(endPosition);
        }

        Label beginLabel = edge.getBeginLabel();
        if (beginLabel != null) {
            beginLabel = this.getLayoutedLabel(beginLabel, id2ElkGraphElements, xOffset, yOffset);
        }
        Label centerLabel = edge.getCenterLabel();
        if (centerLabel != null) {
            centerLabel = this.getLayoutedLabel(centerLabel, id2ElkGraphElements, xOffset, yOffset);
        }
        Label endLabel = edge.getEndLabel();
        if (endLabel != null) {
            endLabel = this.getLayoutedLabel(endLabel, id2ElkGraphElements, xOffset, yOffset);
        }

        // @formatter:off
        return Edge.newEdge(edge)
                .beginLabel(beginLabel)
                .centerLabel(centerLabel)
                .endLabel(endLabel)
                .routingPoints(routingPoints)
                .build();
        // @formatter:on
    }

    private Label getLayoutedLabel(Label label, Map<String, ElkGraphElement> id2ElkGraphElements, double xOffset, double yOffset) {
        Label layoutedLabel = label;
        var optionalElkBeginLabel = Optional.of(id2ElkGraphElements.get(label.getId().toString())).filter(ElkLabel.class::isInstance).map(ElkLabel.class::cast);
        if (optionalElkBeginLabel.isPresent()) {
            ElkLabel elkLabel = optionalElkBeginLabel.get();

            Size size = Size.of(elkLabel.getWidth(), elkLabel.getHeight());

            Position position = Position.at(xOffset + elkLabel.getX(), yOffset + elkLabel.getY());

            // @formatter:off
            Position alignment = elkLabel.eAdapters().stream()
                    .findFirst()
                    .filter(AlignmentHolder.class::isInstance)
                    .map(AlignmentHolder.class::cast)
                    .map(AlignmentHolder::getAlignment)
                    .orElse(Position.UNDEFINED);

            layoutedLabel = Label.newLabel(label)
                    .size(size)
                    .position(position)
                    .alignment(alignment)
                    .build();
            // @formatter:on
        }
        return layoutedLabel;
    }
}
