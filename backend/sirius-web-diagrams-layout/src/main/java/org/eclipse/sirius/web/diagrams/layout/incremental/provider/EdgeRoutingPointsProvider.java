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
package org.eclipse.sirius.web.diagrams.layout.incremental.provider;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Ratio;
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
        if (edge.getSource().equals(edge.getTarget())) {
            positions = this.getRoutingPointsToMyself(edge.getSource().getPosition(), edge.getSource().getSize().getWidth());
        } else {
            Bounds sourceBounds = this.getAbsoluteBounds(edge.getSource());
            Bounds targetBounds = this.getAbsoluteBounds(edge.getTarget());

            this.supportOldDiagramWithExistingEdge(edge);
            Position sourceAbsolutePosition = this.toAbsolutePosition(edge.getSourceAnchorRelativePosition(), sourceBounds);
            Position targetAbsolutePosition = this.toAbsolutePosition(edge.getTargetAnchorRelativePosition(), targetBounds);

            Geometry geometry = new Geometry();
            Optional<Position> optionalSourceIntersection = geometry.getIntersection(targetAbsolutePosition, sourceAbsolutePosition, sourceBounds);
            Optional<Position> optionalTargetIntersection = geometry.getIntersection(sourceAbsolutePosition, targetAbsolutePosition, targetBounds);
            if (optionalSourceIntersection.isPresent() && optionalTargetIntersection.isPresent()) {
                positions = List.of(optionalSourceIntersection.get(), optionalTargetIntersection.get());
            }
        }
        return positions;
    }

    private Position toAbsolutePosition(Ratio anchorRelativePosition, Bounds sourceBounds) {
        double edgeAbsoluteX = sourceBounds.getPosition().getX() + sourceBounds.getSize().getWidth() * anchorRelativePosition.getX();
        double edgeAbsoluteY = sourceBounds.getPosition().getY() + sourceBounds.getSize().getHeight() * anchorRelativePosition.getY();
        return Position.at(edgeAbsoluteX, edgeAbsoluteY);
    }

    /**
     * Used to keep old diagram containing edges working. It affects the position proportion of the node center, and
     * thus, old edges are still overlapping but at least the diagram is loading.
     */
    private void supportOldDiagramWithExistingEdge(EdgeLayoutData edge) {
        if (edge.getSourceAnchorRelativePosition() == null || Ratio.UNDEFINED.equals(edge.getSourceAnchorRelativePosition())) {
            edge.setSourceAnchorRelativePosition(Ratio.of(0.5, 0.5));
        }
        if (edge.getTargetAnchorRelativePosition() == null || Ratio.UNDEFINED.equals(edge.getTargetAnchorRelativePosition())) {
            edge.setTargetAnchorRelativePosition(Ratio.of(0.5, 0.5));
        }
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
}
