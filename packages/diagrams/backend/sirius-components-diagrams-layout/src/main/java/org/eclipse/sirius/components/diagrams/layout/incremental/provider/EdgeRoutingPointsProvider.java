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
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.UpdateEdgeRoutingPointsEvent;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * Provides the routing points to apply to an Edge.
 *
 * @author wpiers
 */
public class EdgeRoutingPointsProvider {

    public List<Position> getRoutingPoints(Optional<IDiagramEvent> optionalDiagramElementEvent, Optional<Position> optionalDelta, EdgeLayoutData edge) {
        List<Position> positions = List.of();
        this.supportOldDiagramWithExistingEdge(edge);
        if (optionalDiagramElementEvent.filter(UpdateEdgeRoutingPointsEvent.class::isInstance).isPresent()) {
            UpdateEdgeRoutingPointsEvent updateEdgeRoutingPointsEvent = optionalDiagramElementEvent.map(UpdateEdgeRoutingPointsEvent.class::cast).get();
            positions = updateEdgeRoutingPointsEvent.getRoutingPoints();
        } else {
            if (!edge.getRoutingPoints().isEmpty()) {
                positions = edge.getRoutingPoints();
            }

            if (optionalDelta.isPresent() && this.isContainedInMovedElement(optionalDiagramElementEvent, edge.getSource())
                    && this.isContainedInMovedElement(optionalDiagramElementEvent, edge.getTarget())) {
                Position delta = optionalDelta.get();
                positions = positions.stream().map(position -> {
                    return Position.at(position.getX() + delta.getX(), position.getY() + delta.getY());
                }).collect(Collectors.toList());
            }

            if (edge.getSource().equals(edge.getTarget()) && (positions.isEmpty() || this.hasBeenMoved(edge.getSource(), optionalDiagramElementEvent))) {
                positions = this.getRoutingPointsToMyself(edge.getSource().getPosition(), edge.getSource().getSize().getWidth());
            }
        }
        return positions;
    }

    private boolean hasBeenMoved(NodeLayoutData node, Optional<IDiagramEvent> optionalDiagramElementEvent) {
        // @formatter:off
        return optionalDiagramElementEvent.filter(MoveEvent.class::isInstance)
                .map(MoveEvent.class::cast)
                .map(MoveEvent::getNodeId)
                .filter(node.getId()::equals)
                .isPresent();
        // @formatter:on
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

    private boolean isContainedInMovedElement(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData node) {
        // @formatter:off
        return optionalDiagramElementEvent.filter(MoveEvent.class::isInstance)
                .map(MoveEvent.class::cast)
                .map(MoveEvent::getNodeId)
                .filter(nodeId -> this.isContainedIn(node, nodeId))
                .isPresent();
        // @formatter:on
    }

    private boolean isContainedIn(NodeLayoutData node, String nodeId) {
        boolean result = false;
        if (nodeId.equals(node.getId())) {
            result = true;
        } else {
            IContainerLayoutData parent = node.getParent();
            if (parent instanceof NodeLayoutData) {
                result = this.isContainedIn((NodeLayoutData) parent, nodeId);
            }
        }
        return result;
    }
}
