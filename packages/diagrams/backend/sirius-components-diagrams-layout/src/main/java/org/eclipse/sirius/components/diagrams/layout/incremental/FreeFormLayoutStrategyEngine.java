/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.api.Geometry;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ChildLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ChildrenAreaLaidOutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * The layout strategy engine that layout the children of the given node freely.
 *
 * @author gcoutable
 */
public class FreeFormLayoutStrategyEngine implements ILayoutStrategyEngine {

    private final ChildNodeIncrementalLayoutEngineHandler childNodeIncrementalLayoutEngine;

    public FreeFormLayoutStrategyEngine(ChildNodeIncrementalLayoutEngineHandler childNodeIncrementalLayoutEngine) {
        this.childNodeIncrementalLayoutEngine = Objects.requireNonNull(childNodeIncrementalLayoutEngine);
    }

    @Override
    public ChildrenAreaLaidOutData layoutChildrenArea(ChildrenAreaLayoutContext childrenAreaLayoutContext, ISiriusWebLayoutConfigurator layoutConfigurator) {

        // 1- Do the layout of children
        List<NodeLayoutData> childrenLayoutData = new ArrayList<>();
        Map<String, ChildLayoutData> nodeIdToChildLayoutData = new HashMap<>();
        for (ChildLayoutData child : childrenAreaLayoutContext.getChildrenLayoutData()) {
            nodeIdToChildLayoutData.put(child.getId(), child);
            this.childNodeIncrementalLayoutEngine.layout(childrenAreaLayoutContext.getOptionalDiagramEvent(), child, layoutConfigurator, Optional.empty()).ifPresent(childrenLayoutData::add);
        }

        // 2- Update position regarding diagram events
        childrenAreaLayoutContext.getOptionalDiagramEvent().ifPresent(event -> this.handleDiagramEvent(event, childrenAreaLayoutContext));

        // 3- Handle children of node that is being created
        if (childrenAreaLayoutContext.isParentBeingCreated()) {
            childrenLayoutData.forEach(node -> {
                if (Position.UNDEFINED.equals(node.getPosition())) {
                    // If a child does not have its position initialized
                    node.setPosition(Position.at(0, 0));
                }
            });
        }

        // 4- Update position regarding overlaps (children node position can be negative)
        double topLeftX = 0;
        double topLeftY = 0;
        double bottomRightX = 0;
        double bottomRightY = 0;
        List<NodeLayoutData> childrenWithoutOverlap = this.updateOverlap(childrenAreaLayoutContext, childrenLayoutData);
        for (NodeLayoutData laidOutNode : childrenWithoutOverlap) {
            Position laidOutNodePosition = laidOutNode.getPosition();
            Size laidOutNodeSize = laidOutNode.getSize();
            topLeftX = Math.min(topLeftX, laidOutNodePosition.getX());
            topLeftY = Math.min(topLeftY, laidOutNodePosition.getY());
            bottomRightX = Math.max(bottomRightX, laidOutNodePosition.getX() + laidOutNodeSize.getWidth());
            bottomRightY = Math.max(bottomRightY, laidOutNodePosition.getY() + laidOutNodeSize.getHeight());
        }

        double childrenAreaWidth = bottomRightX - topLeftX;
        double childrenAreaHeight = bottomRightY - topLeftY;

        if (childrenAreaLayoutContext.getOptionalChildrenAreaWidth().isPresent()) {
            childrenAreaWidth = childrenAreaLayoutContext.getOptionalChildrenAreaWidth().get();
        }

        // 5- Update the position of child nodes and update the children area size and position accordingly (children
        // node are positive, the position of children area can be negative)
        ChildrenAreaLaidOutData childrenAreaLayoutData = this.createChildrenAreaLayoutData(childrenWithoutOverlap, nodeIdToChildLayoutData, Size.of(childrenAreaWidth, childrenAreaHeight));

        // 6- Return the updated children area layout data
        return childrenAreaLayoutData;
    }

    private ChildrenAreaLaidOutData createChildrenAreaLayoutData(List<NodeLayoutData> childrenWithoutOverlap, Map<String, ChildLayoutData> nodeIdToChildLayoutData, Size childrenAreaSize) {
        double minDeltaX = 0;
        double minDeltaY = 0;

        for (NodeLayoutData nodeLayoutData : childrenWithoutOverlap) {
            Position positionInArea = nodeLayoutData.getPosition();
            minDeltaX = Math.min(minDeltaX, positionInArea.getX());
            minDeltaY = Math.min(minDeltaY, positionInArea.getY());
        }

        // @formatter:off
        return ChildrenAreaLaidOutData.newChildrenAreaLaidOutData()
                .deltaPosition(Position.at(minDeltaX, minDeltaY))
                .size(childrenAreaSize)
                .nodeIdToChildLayoutData(nodeIdToChildLayoutData)
                .build();
        // @formatter:on
    }

    private List<NodeLayoutData> updateOverlap(ChildrenAreaLayoutContext childrenAreaLayoutContext, List<NodeLayoutData> childrenLayoutData) {

        Set<NodeLayoutData> fixedNodes = new HashSet<>();
        double distance = childrenAreaLayoutContext.getNodeMargin();
        List<NodeLayoutData[]> overlaps = this.findAllOverlaps(childrenLayoutData, fixedNodes, distance);
        int iteration = 0;
        while (!overlaps.isEmpty() && iteration <= 100) {
            for (NodeLayoutData[] overlap : overlaps) {
                NodeLayoutData fixedNode = overlap[0];
                NodeLayoutData nodeToMove = overlap[1];
                Position newPosition = this.computeNewPosition(fixedNode, nodeToMove, distance);
                this.updateNodePosition(nodeToMove, newPosition);
                fixedNodes.add(nodeToMove);
            }
            fixedNodes.clear();
            overlaps = this.findAllOverlaps(childrenLayoutData, fixedNodes, distance);
            iteration++;
        }
        return childrenLayoutData;
    }

    private List<NodeLayoutData[]> findAllOverlaps(List<NodeLayoutData> childrenLayoutData, Set<NodeLayoutData> fixedNodes, double distance) {
        List<NodeLayoutData[]> overlaps = new ArrayList<>();
        for (NodeLayoutData node : childrenLayoutData) {
            overlaps.addAll(this.findOverlaps(node, childrenLayoutData, fixedNodes, distance));
        }
        return overlaps;
    }

    private List<NodeLayoutData[]> findOverlaps(NodeLayoutData node, List<NodeLayoutData> siblings, Set<NodeLayoutData> fixedNodes, double distance) {
        List<NodeLayoutData[]> overlaps = new ArrayList<>();
        for (NodeLayoutData sibling : siblings) {
            // we only consider solvable overlaps
            if (!sibling.equals(node) && !sibling.isPinned() && !fixedNodes.contains(sibling)) {
                Size siblingRoundedSize = sibling.getSize();
                Size nodeRoundedSize = this.getRoundedSize(node.getSize());
                Position siblingRoundedPosition = this.getRoundedPosition(sibling.getPosition());
                Position nodeRoundedPosition = this.getRoundedPosition(node.getPosition());

                // @formatter:off
                if (nodeRoundedPosition.getX() < siblingRoundedPosition.getX() + siblingRoundedSize.getWidth() + distance &&
                        nodeRoundedPosition.getX() + nodeRoundedSize.getWidth() + distance > siblingRoundedPosition.getX() &&
                        nodeRoundedPosition.getY() < siblingRoundedPosition.getY() + siblingRoundedSize.getHeight() + distance &&
                        nodeRoundedPosition.getY() + nodeRoundedSize.getHeight() + distance > siblingRoundedPosition.getY()) {
                    overlaps.add(new NodeLayoutData[] {node, sibling});
                }
                // @formatter:on
            }
        }
        return overlaps;
    }

    private Position computeNewPosition(NodeLayoutData node1, NodeLayoutData node2, double gap) {
        double distance = gap;
        Position center1 = this.getCenter(node1);
        Position center2 = this.getCenter(node2);

        // We compute the minimal distance between the two nodes centers
        distance += this.computeDistanceToBorder(center1, center2, node1);
        distance += this.computeDistanceToBorder(center2, center1, node2);

        // We translate the center according to the distance
        Position newCenter = this.translate(center1, center2, distance, center1);

        // We find the new position according to the new center
        double x = newCenter.getX() - node2.getSize().getWidth() / 2;
        double y = newCenter.getY() - node2.getSize().getHeight() / 2;

        return Position.newPosition().x(x).y(y).build();
    }

    private Position getCenter(NodeLayoutData node) {
        double x = node.getPosition().getX() + node.getSize().getWidth() / 2;
        double y = node.getPosition().getY() + node.getSize().getHeight() / 2;
        return Position.newPosition().x(x).y(y).build();
    }

    /**
     * Computes the shortest distance between the center1 point and the given node bounds intersection with the segment
     * (center1, center2).
     *
     * @param center1
     *            the first node center.
     * @param center2
     *            the second node center.
     * @param node
     *            the node used for computing the intersection.
     * @return the shortest distance.
     */
    private double computeDistanceToBorder(Position center1, Position center2, NodeLayoutData node) {
        Geometry geometry = new Geometry();
        Optional<Position> optionalIntersection = geometry.getIntersection(center2, center1, this.getBounds(node));
        if (optionalIntersection.isPresent()) {
            return geometry.computeDistance(center1, optionalIntersection.get());
        } else {
            return 0;
        }
    }

    private Bounds getBounds(NodeLayoutData nodeLayoutData) {
        // @formatter:off
        return Bounds.newBounds()
                .position(nodeLayoutData.getPosition())
                .size(nodeLayoutData.getSize())
                .build();
        // @formatter:on
    }

    private Position translate(Position source, Position target, double distance, Position start) {
        double factor = Math.sqrt(Math.pow(source.getX() - target.getX(), 2.0) + Math.pow(source.getY() - target.getY(), 2.0));
        if (factor == 0.0d) {
            factor = 1.0d;
        }
        double x = start.getX() + (target.getX() - source.getX()) / factor * distance;
        double y = start.getY() + (target.getY() - source.getY()) / factor * distance;
        return Position.newPosition().x(x).y(y).build();
    }

    private void updateNodePosition(NodeLayoutData node, Position newPosition) {
        node.setPosition(newPosition);
        node.setChanged(true);
    }

    private void handleDiagramEvent(IDiagramEvent event, ChildrenAreaLayoutContext childrenAreaLayoutContext) {
        List<ChildLayoutData> childrenLayoutData = childrenAreaLayoutContext.getChildrenLayoutData();
        if (event instanceof MoveEvent moveEvent) {
            for (NodeLayoutData laidOutNode : childrenLayoutData) {
                if (moveEvent.nodeId().equals(laidOutNode.getId())) {
                    this.updateNodePosition(laidOutNode, moveEvent.newPosition());
                    laidOutNode.setPinned(true);
                }
            }
        } else if (event instanceof SinglePositionEvent singlePositionEvent) {
            for (NodeLayoutData laidOutNode : childrenLayoutData) {
                if (laidOutNode.getPosition().equals(Position.UNDEFINED)) {
                    Position eventPosition = singlePositionEvent.position();
                    Position childrenAreaAbsolutePosition = childrenAreaLayoutContext.getAbsolutePosition();
                    double xPosition = Math.max(eventPosition.getX() - childrenAreaAbsolutePosition.getX(), 0);
                    double yPosition = Math.max(eventPosition.getY() - childrenAreaAbsolutePosition.getY(), 0);
                    this.updateNodePosition(laidOutNode, Position.at(xPosition, yPosition));
                    laidOutNode.setPinned(true);
                }
            }
        } else if (event instanceof ResizeEvent resizeEvent) {
            Position positionDelta = resizeEvent.positionDelta();
            double deltaX = positionDelta.getX();
            double deltaY = positionDelta.getY();
            if (resizeEvent.nodeId().equals(childrenAreaLayoutContext.getParentId())) {
                for (NodeLayoutData laidOutNode : childrenLayoutData) {
                    // No matter how the parent size is increasing or decreasing, we don't want the position of any
                    // node to be negative
                    double newPosX = Math.max(laidOutNode.getPosition().getX() + deltaX, 0);
                    double newPosY = Math.max(laidOutNode.getPosition().getY() + deltaY, 0);
                    this.updateNodePosition(laidOutNode, Position.at(newPosX, newPosY));
                }
            } else {
                for (NodeLayoutData laidOutNode : childrenLayoutData) {
                    if (resizeEvent.nodeId().equals(laidOutNode.getId())) {
                        this.updateNodePosition(laidOutNode, Position.at(laidOutNode.getPosition().getX() - deltaX, laidOutNode.getPosition().getY() - deltaY));
                        laidOutNode.setPinned(true);
                    }
                }
            }
        }
    }

    private Position getRoundedPosition(Position position) {
        BigDecimal roundedX = BigDecimal.valueOf(position.getX()).setScale(4, RoundingMode.HALF_UP);
        BigDecimal roundedY = BigDecimal.valueOf(position.getY()).setScale(4, RoundingMode.HALF_UP);
        return Position.at(roundedX.doubleValue(), roundedY.doubleValue());
    }

    private Size getRoundedSize(Size size) {
        BigDecimal roundedWidth = BigDecimal.valueOf(size.getWidth()).setScale(4, RoundingMode.HALF_UP);
        BigDecimal roundedHeight = BigDecimal.valueOf(size.getHeight()).setScale(4, RoundingMode.HALF_UP);
        return Size.of(roundedWidth.doubleValue(), roundedHeight.doubleValue());
    }
}
