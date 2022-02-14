/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.events;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.Position;

/**
 * The diagram event used to update routing points of an edge in the next diagram refresh.
 *
 * @author gcoutable
 */
public class UpdateEdgeRoutingPointsEvent implements IDiagramEvent {

    private final String edgeId;

    private final List<Position> routingPoints;

    public UpdateEdgeRoutingPointsEvent(String edgeId, List<Position> routingPoints) {
        this.edgeId = Objects.requireNonNull(edgeId);
        this.routingPoints = Objects.requireNonNull(routingPoints);
    }

    public String getEdgeId() {
        return this.edgeId;
    }

    public List<Position> getRoutingPoints() {
        return this.routingPoints;
    }
}
