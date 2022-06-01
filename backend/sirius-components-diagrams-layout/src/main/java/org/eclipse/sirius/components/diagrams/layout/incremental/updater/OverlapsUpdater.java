/*******************************************************************************
 * Copyright (c) 2021, 2022 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.diagrams.layout.incremental.updater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.api.Geometry;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.EdgeRoutingPointsProvider;

/**
 * An algorithm dedicated to solve overlaps issues. Any node in a given container might be moved to avoid overlaps,
 * except those that have been moved by an user.
 *
 * @author wpiers
 */
public class OverlapsUpdater {

    /**
     * The maximal iteration limit to find overlapping nodes. If after this limit we still have overlap, we stop the
     * algorithm.
     */
    private static final int MAX_OVERLAP_ITERATION = 100;

    private Set<NodeLayoutData> fixedNodes = new HashSet<>();

    private final EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

    public void update(IContainerLayoutData container) {
        Optional<DiagramLayoutData> optionalRoot = this.getRoot(container);
        Collection<NodeLayoutData[]> overlaps = this.findAllOverlaps(container);
        int iteration = 0;
        while (!overlaps.isEmpty() && iteration <= MAX_OVERLAP_ITERATION) {
            for (NodeLayoutData[] overlap : overlaps) {
                NodeLayoutData fixedNode = overlap[0];
                NodeLayoutData nodeToMove = overlap[1];
                Position newPosition = this.computeNewPosition(fixedNode, nodeToMove);
                this.updateNodePosition(nodeToMove, newPosition, optionalRoot);
                this.fixedNodes.add(nodeToMove);
            }
            overlaps = this.findAllOverlaps(container);
            iteration++;
        }
        if (container instanceof NodeLayoutData) {
            this.update(((NodeLayoutData) container).getParent());
        }
    }

    /**
     * Updates the node position and all "contained" edges routing points.
     *
     * @param node
     *            The node to update
     * @param newPosition
     *            The new position of the node
     * @param optionalRoot
     *            The possible diagram layout data
     */
    private void updateNodePosition(NodeLayoutData node, Position newPosition, Optional<DiagramLayoutData> optionalRoot) {
        Position previousPosition = node.getPosition();
        node.setPosition(newPosition);
        node.setChanged(true);

        if (optionalRoot.isPresent()) {
            DiagramLayoutData root = optionalRoot.get();
            Position delta = Position.at(newPosition.getX() - previousPosition.getX(), newPosition.getY() - previousPosition.getY());
            List<NodeLayoutData> movedNodes = this.getChildren(node);

            for (EdgeLayoutData edgeLayoutData : root.getEdges()) {
                if (movedNodes.contains(edgeLayoutData.getSource()) && movedNodes.contains(edgeLayoutData.getTarget())) {
                    MoveEvent moveEvent = new MoveEvent(node.getId(), newPosition);
                    List<Position> updatedRoutingPoints = this.edgeRoutingPointsProvider.getRoutingPoints(Optional.of(moveEvent), Optional.of(delta), edgeLayoutData);
                    edgeLayoutData.setRoutingPoints(updatedRoutingPoints);
                }
            }
        }
    }

    private Optional<DiagramLayoutData> getRoot(IContainerLayoutData container) {
        Optional<DiagramLayoutData> optionalRoot = Optional.empty();

        if (container instanceof DiagramLayoutData) {
            optionalRoot = Optional.of((DiagramLayoutData) container);
        }

        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeContainer = (NodeLayoutData) container;
            optionalRoot = this.getRoot(nodeContainer.getParent());
        }

        return optionalRoot;
    }

    private List<NodeLayoutData> getChildren(NodeLayoutData nodeLayoutData) {
        List<NodeLayoutData> allChildrenNode = new ArrayList<>(nodeLayoutData.getChildrenNodes());

        for (NodeLayoutData child : nodeLayoutData.getChildrenNodes()) {
            allChildrenNode.addAll(this.getChildren(child));
        }

        return allChildrenNode;
    }

    private Collection<NodeLayoutData[]> findAllOverlaps(IContainerLayoutData container) {
        List<NodeLayoutData[]> overlaps = new ArrayList<>();
        for (NodeLayoutData node : container.getChildrenNodes()) {
            overlaps.addAll(this.findOverlaps(node));
        }
        return overlaps;
    }

    private Collection<NodeLayoutData[]> findOverlaps(NodeLayoutData node) {
        List<NodeLayoutData[]> overlaps = new ArrayList<>();
        for (NodeLayoutData sibling : node.getParent().getChildrenNodes()) {
            // we only consider solvable overlaps
            if (!sibling.equals(node) && !sibling.isPinned() && !this.fixedNodes.contains(sibling)) {
                Position siblingPosition = sibling.getPosition();
                Size siblingSize = sibling.getSize();
                // @formatter:off
                if (node.getPosition().getX() < siblingPosition.getX() + siblingSize.getWidth() &&
                        node.getPosition().getX() + node.getSize().getWidth() > siblingPosition.getX() &&
                        node.getPosition().getY() < siblingPosition.getY() + siblingSize.getHeight() &&
                        node.getPosition().getY() + node.getSize().getHeight() > siblingPosition.getY()) {
                    overlaps.add(new NodeLayoutData[] {node, sibling});
                }
                // @formatter:on
            }
        }
        return overlaps;
    }

    private Position computeNewPosition(NodeLayoutData node1, NodeLayoutData node2) {
        Position center1 = this.getCenter(node1);
        Position center2 = this.getCenter(node2);

        // We compute the minimal distance between the two nodes centers
        double distance = IncrementalLayoutEngine.NODES_GAP;
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

    private Position translate(Position source, Position target, double distance, Position start) {
        double factor = Math.sqrt(Math.pow(source.getX() - target.getX(), 2.0) + Math.pow(source.getY() - target.getY(), 2.0));
        if (factor == 0.0d) {
            factor = 1.0d;
        }
        double x = start.getX() + (target.getX() - source.getX()) / factor * distance;
        double y = start.getY() + (target.getY() - source.getY()) / factor * distance;
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

}
