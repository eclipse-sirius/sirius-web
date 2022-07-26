/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import org.eclipse.sirius.components.diagrams.Ratio;
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

        Label label = this.getLayoutedLabel(node.getLabel(), id2ElkGraphElements, 0, 0);

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
        double sourceAnchorRatioX = 0.5;
        double sourceAnchorRatioY = 0.5;
        double targetAnchorRatioX = 0.5;
        double targetAnchorRatioY = 0.5;
        Ratio sourceAnchorRatio = Ratio.of(sourceAnchorRatioX, sourceAnchorRatioY);
        Ratio targetAnchorRatio = Ratio.of(targetAnchorRatioX, targetAnchorRatioY);

        if (!elkEdge.getSections().isEmpty()) {
            ElkEdgeSection section = elkEdge.getSections().get(0);

            Optional<ElkNode> optionalSource = elkEdge.getSources().stream().filter(ElkNode.class::isInstance).map(ElkNode.class::cast).findFirst();
            Optional<ElkNode> optionalTarget = elkEdge.getTargets().stream().filter(ElkNode.class::isInstance).map(ElkNode.class::cast).findFirst();
            if (optionalSource.isPresent() && optionalTarget.isPresent()) {

                ElkNode sourceNode = optionalSource.get();
                ElkNode targetNode = optionalTarget.get();

                sourceAnchorRatio = this.getSectionRatio(sourceNode, section.getStartX() + xOffset, section.getStartY() + yOffset);
                targetAnchorRatio = this.getSectionRatio(targetNode, section.getEndX() + xOffset, section.getEndY() + yOffset);
            }

            for (ElkBendPoint bendPoint : section.getBendPoints()) {
                Position position = Position.at(xOffset + bendPoint.getX(), yOffset + bendPoint.getY());
                routingPoints.add(position);
            }
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
                .sourceAnchorRelativePosition(sourceAnchorRatio)
                .targetAnchorRelativePosition(targetAnchorRatio)
                .build();
        // @formatter:on
    }

    private Ratio getSectionRatio(ElkNode node, double sectionX, double sectionY) {
        double sourceAnchorRatioX;
        double sourceAnchorRatioY;
        Position nodeAbsolutePosition = this.getAbsolutePosition(node);
        if (sectionX == nodeAbsolutePosition.getX()) {
            sourceAnchorRatioX = 0.5;
        } else if (sectionX == nodeAbsolutePosition.getX() + node.getWidth()) {
            sourceAnchorRatioX = 0.5;
        } else {
            sourceAnchorRatioX = (sectionX - nodeAbsolutePosition.getX()) / node.getWidth();
        }

        if (sectionY == nodeAbsolutePosition.getY()) {
            sourceAnchorRatioY = 0.5;
        } else if (sectionY == nodeAbsolutePosition.getY() + node.getHeight()) {
            sourceAnchorRatioY = 0.5;
        } else {
            sourceAnchorRatioY = (sectionY - nodeAbsolutePosition.getY()) / node.getHeight();
        }

        return Ratio.of(sourceAnchorRatioX, sourceAnchorRatioY);
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

    private Position getAbsolutePosition(ElkNode node) {
        ElkNode currentNode = node;
        Position absolutePosition = Position.at(node.getX(), node.getY());
        while (currentNode.getParent() != null) {
            currentNode = currentNode.getParent();
            // @formatter:off
            absolutePosition = Position.newPosition()
                    .x(absolutePosition.getX() + currentNode.getX())
                    .y(absolutePosition.getY() + currentNode.getY())
                    .build();
            // @formatter:on
        }
        return absolutePosition;
    }
}
