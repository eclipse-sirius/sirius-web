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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * Provides the routing points to apply to an Edge.
 *
 * @author wpiers
 */
public class EdgeRoutingPointsProvider {

    public List<Position> getRoutingPoints(EdgeLayoutData edge) {
        List<Position> routingPoints = new ArrayList<>();
        routingPoints.add(this.getCenter(edge.getSource()));
        routingPoints.add(this.getCenter(edge.getTarget()));
        return routingPoints;
    }

    private Position getCenter(NodeLayoutData nodeLayoutData) {
        Position absoluteNodePosition = nodeLayoutData.getAbsolutePosition();
        Size nodeSize = nodeLayoutData.getSize();
        // @formatter:off
        return Position.newPosition()
                .x(absoluteNodePosition.getX() + (nodeSize.getWidth() / 2))
                .y(absoluteNodePosition.getY() + (nodeSize.getHeight() / 2))
                .build();
        // @formatter:on
    }

}
