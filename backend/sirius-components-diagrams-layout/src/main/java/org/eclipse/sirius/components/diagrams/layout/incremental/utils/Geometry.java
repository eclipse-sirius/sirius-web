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
package org.eclipse.sirius.components.diagrams.layout.incremental.utils;

import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;

/**
 * A Geometry Helper.
 *
 * @author fbarbin
 */
public final class Geometry {
    /**
     * Computes intersection points between the given rectangle (Bounds) and the segment(source,target), then returns
     * the closest one from the source point.
     *
     * @param source
     *            the source point of the segment.
     * @param target
     *            the target point of the segment.
     * @param nodeBounds
     *            the node bounds.
     * @return the closest intersection from the source point.
     */
    public Optional<Position> getIntersection(Position source, Position target, Bounds nodeBounds) {
        //@formatter:off
        Position topLeft = nodeBounds.getPosition();
        Position topRight = Position.newPosition()
                .x(topLeft.getX() + nodeBounds.getSize().getWidth())
                .y(topLeft.getY())
                .build();
        Position bottomLeft = Position.newPosition()
                .x(topLeft.getX())
                .y(topLeft.getY() + nodeBounds.getSize().getHeight())
                .build();
        Position bottomRight = Position.newPosition()
                .x(topLeft.getX() + nodeBounds.getSize().getWidth())
                .y(topLeft.getY() + nodeBounds.getSize().getHeight())
                .build();
        //@formatter:on

        Position topIntersection = this.getIntersection(source, target, topLeft, topRight);
        Position bottomIntersection = this.getIntersection(source, target, bottomLeft, bottomRight);
        Position leftIntersection = this.getIntersection(source, target, topLeft, bottomLeft);
        Position rightIntersection = this.getIntersection(source, target, topRight, bottomRight);

        return this.getMinDistancePosition(source, topIntersection, bottomIntersection, leftIntersection, rightIntersection);
    }

    private Optional<Position> getMinDistancePosition(Position source, Position... intersections) {
        Position minPosition = null;
        Double minDistance = null;
        for (Position intersection : intersections) {
            if (intersection != null) {
                double tmp = this.computeDistance(source, intersection);
                if (minDistance == null || tmp < minDistance) {
                    minDistance = tmp;
                    minPosition = intersection;
                }
            }
        }
        return Optional.ofNullable(minPosition);
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
     * @return the intersection point or null if not found.
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

    public double computeDistance(Position source, Position target) {
        double ac = Math.abs(target.getY() - source.getY());
        double cb = Math.abs(target.getX() - source.getX());
        return Math.hypot(ac, cb);
    }

    /**
     * Returns the distance between the point p and the segment made of p1 and p2.
     *
     * @param p
     *            the point
     * @param p1
     *            the first point of the segment
     * @param p2
     *            the second point of the segment
     */
    public Position closestPointOnSegment(Position p, Position p1, Position p2) {
        double p1pVectordx = p.getX() - p1.getX();
        double p1pVectordy = p.getY() - p1.getY();
        double p2p1Vectordx = p2.getX() - p1.getX();
        double p2p1Vectordy = p2.getY() - p1.getY();

        double dot = p1pVectordx * p2p1Vectordx + p1pVectordy * p2p1Vectordy;
        double lengthSquare = p2p1Vectordx * p2p1Vectordx + p2p1Vectordy * p2p1Vectordy;
        // param is the projection distance from p1 on the segment. This is the fraction of the segment that p is
        // closest to.
        double param = -1;
        if (lengthSquare != 0) {
            // if the line has 0 length
            param = dot / lengthSquare;
        }

        Position nearestPoint = null;

        if (param < 0) {
            // The point is not "in front of" the segment. It is "away" on the p1 side
            nearestPoint = Position.at(p1.getX(), p1.getY());
        } else if (param > 1) {
            // The point is not "in front of" the segment. It is "away" on the p1 side
            nearestPoint = Position.at(p2.getX(), p2.getY());
        } else {
            // The point is "in front of" the segment
            // get the projection of p on the segment
            nearestPoint = Position.at(p1.getX() + param * p2p1Vectordx, p1.getY() + param * p2p1Vectordy);
        }

        return nearestPoint;
    }

    /**
     * Gets the closest point on the parentRectangleSize from the given borderNodeRectangle rectangle.
     *
     * @return the position of the point (given in the parentRectangleSize upper right corner coordinates system) and
     *         the side on the parent rectangle.
     */
    public PointOnRectangleInfo snapBorderNodeOnRectangle(Bounds borderNodeRectangle, Size parentRectangleSize, double portOffset) {
        Position borderNodePosition = borderNodeRectangle.getPosition();
        Size borderNodeSize = borderNodeRectangle.getSize();
        Position borderNodeCenterPosition = Position.at(borderNodePosition.getX() + borderNodeSize.getWidth() / 2, borderNodePosition.getY() + borderNodeSize.getHeight() / 2);

        Position parentUpperLeftCorner = Position.at(0, 0);
        Position parentUpperRightCorner = Position.at(parentRectangleSize.getWidth(), 0);
        Position parentBottomRightCorner = Position.at(parentRectangleSize.getWidth(), parentRectangleSize.getHeight());
        Position parentBottomLeftCorner = Position.at(0, parentRectangleSize.getHeight());

        EnumMap<RectangleSide, Position> closestPointOnSide = new EnumMap<>(RectangleSide.class);
        closestPointOnSide.put(RectangleSide.NORTH, this.closestPointOnSegment(borderNodeCenterPosition, parentUpperLeftCorner, parentUpperRightCorner));
        closestPointOnSide.put(RectangleSide.EAST, this.closestPointOnSegment(borderNodeCenterPosition, parentUpperRightCorner, parentBottomRightCorner));
        closestPointOnSide.put(RectangleSide.SOUTH, this.closestPointOnSegment(borderNodeCenterPosition, parentBottomRightCorner, parentBottomLeftCorner));
        closestPointOnSide.put(RectangleSide.WEST, this.closestPointOnSegment(borderNodeCenterPosition, parentBottomLeftCorner, parentUpperLeftCorner));

        double currentClosestDistance = Double.MAX_VALUE;
        RectangleSide currentClosestSide = RectangleSide.NORTH;
        Position curentCenterPositionOnSide = borderNodeCenterPosition;
        for (Entry<RectangleSide, Position> entry : closestPointOnSide.entrySet()) {
            RectangleSide side = entry.getKey();
            Position positionOnSide = entry.getValue();

            double distance = this.computeDistance(borderNodeCenterPosition, positionOnSide);
            if (currentClosestDistance >= distance) {
                boolean updateTheClosestSide = true;
                if (currentClosestDistance == distance) {
                    // Manage the case when borderNodeCenterPosition is outside the rectangle, near a corner of the
                    // parent rectangle, to get the best side
                    if (RectangleSide.NORTH.equals(side) || RectangleSide.SOUTH.equals(side)) {
                        updateTheClosestSide = Math.abs(borderNodeCenterPosition.getY() - positionOnSide.getY()) > Math.abs(borderNodeCenterPosition.getX() - positionOnSide.getX());
                    } else if (RectangleSide.EAST.equals(side) || RectangleSide.WEST.equals(side)) {
                        updateTheClosestSide = Math.abs(borderNodeCenterPosition.getY() - positionOnSide.getY()) < Math.abs(borderNodeCenterPosition.getX() - positionOnSide.getX());
                    }
                }

                if (updateTheClosestSide) {
                    currentClosestDistance = distance;
                    currentClosestSide = side;
                    curentCenterPositionOnSide = positionOnSide;
                }
            }
        }

        // Get the new upper right corner position of the border node according to the side and the offset
        Position newBorderNodePosition = Position.at(0, 0);
        if (RectangleSide.NORTH.equals(currentClosestSide)) {
            newBorderNodePosition = Position.at(curentCenterPositionOnSide.getX() - borderNodeSize.getWidth() / 2, curentCenterPositionOnSide.getY() - borderNodeSize.getHeight() - portOffset);
        } else if (RectangleSide.SOUTH.equals(currentClosestSide)) {
            newBorderNodePosition = Position.at(curentCenterPositionOnSide.getX() - borderNodeSize.getWidth() / 2, curentCenterPositionOnSide.getY() + portOffset);
        } else if (RectangleSide.WEST.equals(currentClosestSide)) {
            newBorderNodePosition = Position.at(curentCenterPositionOnSide.getX() - borderNodeSize.getWidth() - portOffset, curentCenterPositionOnSide.getY() - borderNodeSize.getHeight() / 2);
        } else if (RectangleSide.EAST.equals(currentClosestSide)) {
            newBorderNodePosition = Position.at(curentCenterPositionOnSide.getX() + portOffset, curentCenterPositionOnSide.getY() - borderNodeSize.getHeight() / 2);
        }

        return new PointOnRectangleInfo(newBorderNodePosition, currentClosestSide);
    }

}
