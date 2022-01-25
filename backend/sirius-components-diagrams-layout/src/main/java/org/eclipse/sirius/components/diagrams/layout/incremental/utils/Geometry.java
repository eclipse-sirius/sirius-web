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
package org.eclipse.sirius.components.diagrams.layout.incremental.utils;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Position;

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

}
