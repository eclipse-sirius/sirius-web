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
package org.eclipse.sirius.web.diagrams.layout.incremental.provider;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.utils.Bounds;
import org.eclipse.sirius.web.diagrams.layout.incremental.utils.Geometry;

/**
 * Provides the routing points to apply to an Edge.
 *
 * @author wpiers
 */
public class EdgeRoutingPointsProvider {

    public List<Position> getRoutingPoints(EdgeLayoutData edge) {
        List<Position> positions = List.of();
        Bounds sourceBounds = this.getAbsoluteBounds(edge.getSource());
        Bounds targetBounds = this.getAbsoluteBounds(edge.getTarget());
        Position sourceAbsolutePosition = this.getCenter(sourceBounds);
        Position targetAbsolutePosition = this.getCenter(targetBounds);
        Geometry geometry = new Geometry();
        Optional<Position> optionalSourceIntersection = geometry.getIntersection(targetAbsolutePosition, sourceAbsolutePosition, sourceBounds);
        Optional<Position> optionalTargetIntersection = geometry.getIntersection(sourceAbsolutePosition, targetAbsolutePosition, targetBounds);
        if (optionalSourceIntersection.isPresent() && optionalTargetIntersection.isPresent()) {
            positions = List.of(optionalSourceIntersection.get(), optionalTargetIntersection.get());
        } else if (edge.getSource().equals(edge.getTarget())) {
            positions = this.getRoutingPointsToMyself(edge.getSource().getPosition(), edge.getSource().getSize().getWidth());
        }
        return positions;
    }

    private List<Position> getRoutingPointsToMyself(Position nodePosition, double size) {
        Position p1 = Position.at(nodePosition.getX() + size / 3, nodePosition.getY());
        Position p2 = Position.at(p1.getX(), p1.getY() - 10);
        Position p3 = Position.at(p1.getX() + size / 3, p2.getY());
        Position p4 = Position.at(p3.getX(), p1.getY());
        return List.of(p1, p2, p3, p4);
    }

    private Bounds getAbsoluteBounds(NodeLayoutData nodeLayoutData) {
        // @formatter:off
            return Bounds.newBounds()
                    .position(nodeLayoutData.getAbsolutePosition())
                    .size(nodeLayoutData.getSize())
                    .build();
            // @formatter:on
    }

    private Position getCenter(Bounds bounds) {
        // @formatter:off
        return Position.newPosition()
                .x(bounds.getPosition().getX() + (bounds.getSize().getWidth() / 2))
                .y(bounds.getPosition().getY() + (bounds.getSize().getHeight() / 2))
                .build();
        // @formatter:on

    }

}
