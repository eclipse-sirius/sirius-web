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
package org.eclipse.sirius.components.diagrams.layout.incremental.provider;

import java.util.List;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.EdgeLayoutData;

/**
 * Provides the routing points to apply to an Edge.
 *
 * @author wpiers
 */
public class EdgeRoutingPointsProvider {

    public List<Position> getRoutingPoints(EdgeLayoutData edge) {
        List<Position> positions = List.of();
        this.supportOldDiagramWithExistingEdge(edge);
        if (edge.getSource().equals(edge.getTarget())) {
            positions = this.getRoutingPointsToMyself(edge.getSource().getPosition(), edge.getSource().getSize().getWidth());
        }
        return positions;
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
        Position sourceAnchor = Position.at(nodePosition.getX() + size / 3, nodePosition.getY());
        Position firstRoutingPoint = Position.at(sourceAnchor.getX(), sourceAnchor.getY() - 10);
        Position secondRoutingPoint = Position.at(sourceAnchor.getX() + size / 3, firstRoutingPoint.getY());
        return List.of(firstRoutingPoint, secondRoutingPoint);
    }

}
