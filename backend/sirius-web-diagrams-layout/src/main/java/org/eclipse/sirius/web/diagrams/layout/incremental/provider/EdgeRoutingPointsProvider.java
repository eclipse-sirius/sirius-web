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
        Bounds sourceBounds = this.getAbsoluteBounds(edge.getSource());
        Bounds targetBounds = this.getAbsoluteBounds(edge.getTarget());
        Position sourceAbsolutePosition = this.getCenter(sourceBounds);
        Position targetAbsolutePosition = this.getCenter(targetBounds);
        Geometry geometry = new Geometry();
        Optional<Position> optionalSourceIntersection = geometry.getIntersection(targetAbsolutePosition, sourceAbsolutePosition, sourceBounds);
        Optional<Position> optionalTargetIntersection = geometry.getIntersection(sourceAbsolutePosition, targetAbsolutePosition, targetBounds);
        if (optionalSourceIntersection.isPresent() && optionalTargetIntersection.isPresent()) {
            return List.of(optionalSourceIntersection.get(), optionalTargetIntersection.get());
        }
        return List.of();
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
