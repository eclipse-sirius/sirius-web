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

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import org.eclipse.sirius.components.diagrams.layout.api.experimental.Rectangle;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;

/**
 * Resolves overlaps.
 *
 * @author pcdavid
 */
public class OverlapHandler {
    public Rectangle findNearestFreeLocation(Rectangle rectangle, Collection<Rectangle> obstacles) {
        var largestOverlappingObstacle = obstacles.stream()
                .filter(obstacle -> !obstacle.intersection(rectangle).isEmpty())
                .max(Comparator.comparing(obstacle -> obstacle.intersection(rectangle).area()));
        if (largestOverlappingObstacle.isPresent()) {
            var obstacle = largestOverlappingObstacle.get();
            var shift = this.getVectorToShift(rectangle, obstacle);
            return rectangle.translate(shift.x(), shift.y());
        } else {
            return rectangle;
        }
    }

    private Position getVectorToShift(Rectangle rectangle, Rectangle obstacle) {
        // How much would be need to move rectangle in each direction to avoid the obstacle?
        double leftShift = Math.abs(obstacle.topLeft().x() - rectangle.topRight().x());
        double rightShift = Math.abs(obstacle.topRight().x() - rectangle.topLeft().x());
        double upShift = Math.abs(obstacle.topLeft().y() - rectangle.bottomLeft().y());
        double downShift = Math.abs(obstacle.bottomLeft().y() - rectangle.topLeft().y());

        // Choose the direction with the smallest non-null distance
        double minShift = Arrays.stream(new double[] { leftShift, rightShift, upShift, downShift })
                .filter(shift -> shift > 0)
                .min()
                .orElse(0.0);

        Position result;
        if (minShift == rightShift) {
            result = new Position(rightShift, 0);
        } else if (minShift == leftShift) {
            result = new Position(-leftShift, 0);
        } else if (minShift == downShift) {
            result = new Position(0, downShift);
        } else {
            result = new Position(0, -upShift);
        }
        return result;
    }
}
