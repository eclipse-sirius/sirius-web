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
package org.eclipse.sirius.web.diagrams.layout.incremental.updater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.incremental.IncrementalLayoutEngine;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;

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

    public void update(IContainerLayoutData container) {
        Collection<NodeLayoutData[]> overlaps = this.findAllOverlaps(container);
        int iteration = 0;
        while (!overlaps.isEmpty() && iteration <= MAX_OVERLAP_ITERATION) {
            for (NodeLayoutData[] overlap : overlaps) {
                NodeLayoutData fixedNode = overlap[0];
                NodeLayoutData nodeToMove = overlap[1];
                nodeToMove.setPosition(this.computeNewPosition(fixedNode, nodeToMove));
                nodeToMove.setChanged(true);
                this.fixedNodes.add(nodeToMove);
            }
            overlaps = this.findAllOverlaps(container);
            iteration++;
        }
        if (container instanceof NodeLayoutData) {
            this.update(((NodeLayoutData) container).getParent());
        }
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
        double x = start.getX() + (target.getX() - source.getX()) / factor * distance;
        double y = start.getY() + (target.getY() - source.getY()) / factor * distance;
        return Position.newPosition().x(x).y(y).build();
    }

    private double computeDistance(Position source, Position target) {
        double ac = Math.abs(target.getY() - source.getY());
        double cb = Math.abs(target.getX() - source.getX());
        return Math.hypot(ac, cb);
    }

    /**
     * Computes the distance between the center
     *
     * @param center1
     * @param center2
     * @param node
     * @return
     */
    private double computeDistanceToBorder(Position center1, Position center2, NodeLayoutData node) {
        Position intersection = this.getIntersection(center1, center2, node, center2);
        return this.computeDistance(center1, intersection);
    }

    /**
     * Finds the closest intersection from a given point, along a given line, with a rectangle.
     *
     * @param lineA
     *            the line first point
     * @param lineB
     *            the line second point
     * @param closest
     *            the closest point to the intersection
     * @return the intersection
     */
    private Position getIntersection(Position lineA, Position lineB, NodeLayoutData node, Position closest) {
        Position topLeft = node.getPosition();
        Position topRight = Position.newPosition().x(node.getPosition().getX() + node.getSize().getWidth()).y(node.getPosition().getY()).build();
        Position bottomLeft = Position.newPosition().x(node.getPosition().getX()).y(node.getPosition().getY() + node.getSize().getHeight()).build();
        Position bottomRight = Position.newPosition().x(node.getPosition().getX() + node.getSize().getWidth()).y(node.getPosition().getY() + node.getSize().getHeight()).build();

        Position topIntersection = this.getIntersection(lineA, lineB, topLeft, topRight);
        Position bottomIntersection = this.getIntersection(lineA, lineB, bottomLeft, bottomRight);
        Position leftIntersection = this.getIntersection(lineA, lineB, topLeft, bottomLeft);
        Position rightIntersection = this.getIntersection(lineA, lineB, topRight, bottomRight);

        Position minDistancePosition = this.getMinDistancePosition(closest, topIntersection, bottomIntersection, leftIntersection, rightIntersection);
        return minDistancePosition;

    }

    /**
     * Returns the closest point from the source.
     *
     * @param source
     *            the source point
     * @param candidates
     *            the target candidates
     * @return the closest point
     */
    private Position getMinDistancePosition(Position source, Position... candidates) {
        Position minPosition = null;
        Double minDistance = null;
        for (Position intersection : candidates) {
            if (intersection != null) {
                double tmp = this.computeDistance(source, intersection);
                if (minDistance == null) {
                    minDistance = tmp;
                    minPosition = intersection;
                } else if (tmp < minDistance) {
                    minDistance = tmp;
                    minPosition = intersection;
                }
            }
        }
        return minPosition;
    }

    /**
     * Returns the intersection between a line and a segment.
     *
     * @param lineA
     *            the line first point
     * @param lineB
     *            the line second point
     * @param segmentSource
     *            the segment source
     * @param segmentTarget
     *            the segment target
     * @return the intersection or <null>
     */
    private Position getIntersection(Position lineA, Position lineB, Position segmentSource, Position segmentTarget) {
        double x1 = lineA.getX();
        double y1 = lineA.getY();
        double x2 = lineB.getX();
        double y2 = lineB.getY();

        double x3 = segmentSource.getX();
        double y3 = segmentSource.getY();
        double x4 = segmentTarget.getX();
        double y4 = segmentTarget.getY();

        Position p = null;
        double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (d != 0) {
            double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
            double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;
            if (x3 == x4 && yi <= y4 && yi >= y3) {
                p = Position.newPosition().x(xi).y(yi).build();
            }
            if (y3 == y4 && xi <= x4 && xi >= x3) {
                p = Position.newPosition().x(xi).y(yi).build();
            }
        }
        return p;
    }

}
