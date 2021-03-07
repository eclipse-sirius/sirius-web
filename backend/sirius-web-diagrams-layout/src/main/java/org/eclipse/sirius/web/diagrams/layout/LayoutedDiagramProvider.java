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
package org.eclipse.sirius.web.diagrams.layout;

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
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.springframework.stereotype.Service;

/**
 * This class is used to use both a non-layouted immutable diagram and the result of the computation of the layout to
 * produce a brand new immutable diagram with the proper layout information.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class LayoutedDiagramProvider {

    public Diagram getLayoutedDiagram(Diagram diagram, ElkNode elkDiagram, Map<String, ElkGraphElement> id2ElkGraphElements) {
        // @formatter:off
        Size size = Size.newSize()
                .width(elkDiagram.getWidth())
                .height(elkDiagram.getHeight())
                .build();

        Position position = Position.newPosition()
                .x(elkDiagram.getX())
                .y(elkDiagram.getY())
                .build();

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
        // @formatter:off
        Size size = Size.newSize()
                .width(elkConnectableShape.getWidth())
                .height(elkConnectableShape.getHeight())
                .build();

        Position position = Position.newPosition()
                .x(elkConnectableShape.getX())
                .y(elkConnectableShape.getY())
                .build();

        Label label = this.getLayoutedLabel(node.getLabel(), id2ElkGraphElements, 0, 0);

        List<Node> childNodes = this.getLayoutedNodes(node.getChildNodes(), id2ElkGraphElements);
        List<Node> borderNodes = this.getLayoutedNodes(node.getBorderNodes(), id2ElkGraphElements);
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

            // @formatter:off
            Position startPosition = Position.newPosition()
                    .x(xOffset + section.getStartX())
                    .y(yOffset + section.getStartY())
                    .build();
            routingPoints.add(startPosition);

            for (ElkBendPoint bendPoint : section.getBendPoints()) {
                Position position = Position.newPosition()
                        .x(xOffset + bendPoint.getX())
                        .y(yOffset + bendPoint.getY())
                        .build();
                routingPoints.add(position);
            }

            Position endPosition = Position.newPosition()
                    .x(xOffset + section.getEndX())
                    .y(yOffset + section.getEndY())
                    .build();
            routingPoints.add(endPosition);
            // @formatter:on
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
        var optionalElkBeginLabel = Optional.of(id2ElkGraphElements.get(label.getId())).filter(ElkLabel.class::isInstance).map(ElkLabel.class::cast);
        if (optionalElkBeginLabel.isPresent()) {
            ElkLabel elkLabel = optionalElkBeginLabel.get();

            // @formatter:off
            Size size = Size.newSize()
                    .width(elkLabel.getWidth())
                    .height(elkLabel.getHeight())
                    .build();

            Position position = Position.newPosition()
                    .x(xOffset + elkLabel.getX())
                    .y(yOffset + elkLabel.getY())
                    .build();

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
