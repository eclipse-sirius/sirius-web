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

import java.util.Optional;

import org.eclipse.sirius.web.diagrams.CreationEvent;
import org.eclipse.sirius.web.diagrams.IDiagramElementEvent;
import org.eclipse.sirius.web.diagrams.MoveEvent;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.ResizeEvent;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.incremental.IncrementalLayoutEngine;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * Provides the position to apply to a Node.
 *
 * @author wpiers
 */
public class NodePositionProvider {

    /**
     * The last node for which we computed a position. Used when computing several new positions during one refresh.
     */
    private NodeLayoutData last;

    /**
     * Provides the new position of the given node. If the node position is no need to be updated, the current position
     * is returned.
     *
     * @param optionalDiagramElementEvent
     *            The event currently taken into account
     * @param node
     *            the node for which we want to retrieve the new position.
     * @return the new {@link Position} if updated or the current one.
     */
    public Position getPosition(Optional<IDiagramElementEvent> optionalDiagramElementEvent, NodeLayoutData node) {
        Position position = node.getPosition();

        Optional<Position> optionalPosition = this.getSpecificNodePositionFromEvent(optionalDiagramElementEvent, node);
        if (optionalPosition.isPresent()) {
            position = optionalPosition.get();

        } else if (!this.isAlreadyPositioned(node)) {
            Optional<Position> optionalStartingPosition = this.getOptionalStartingPositionFromEvent(optionalDiagramElementEvent);
            if (optionalStartingPosition.isPresent() && this.last == null) {
                // The node has been created by a tool and has a fixed position
                Position parentPosition = node.getParent().getAbsolutePosition();
                double xPosition = optionalStartingPosition.get().getX() - parentPosition.getX();
                double yPosition = optionalStartingPosition.get().getY() - parentPosition.getY();
                position = Position.at(xPosition, yPosition);
                this.last = node;
            } else {
                // The node has been created along with others, by a tool or a refresh
                position = this.getNewPosition(node);
            }
        }
        return position;
    }

    private Optional<Position> getOptionalStartingPositionFromEvent(Optional<IDiagramElementEvent> optionalDiagramElementEvent) {
        Position position = null;
        if (optionalDiagramElementEvent.isPresent()) {
            IDiagramElementEvent diagramElementEvent = optionalDiagramElementEvent.get();
            if (diagramElementEvent instanceof CreationEvent) {
                position = ((CreationEvent) diagramElementEvent).getStartingPosition();
            }
        }
        return Optional.ofNullable(position);
    }

    private boolean isAlreadyPositioned(NodeLayoutData node) {
        Position position = node.getPosition();
        return position.getX() != -1 && position.getY() != -1;
    }

    private Position getNewPosition(NodeLayoutData node) {
        Position newPosition;
        if (this.last == null) {
            // We are positioning the first element during this layout
            Double maxBottom = this.findMaxBottom(node.getParent());
            // there is no other located node so we start at 0,0.
            if (maxBottom == null) {
                newPosition = Position.at(0, 0);
            } else {
                newPosition = Position.at(0, maxBottom + IncrementalLayoutEngine.NODES_GAP);
            }
        } else {
            Position lastPosition = this.last.getPosition();
            Size lastSize = this.last.getSize();
            double x = lastPosition.getX();
            double y = lastPosition.getY() + lastSize.getHeight() + IncrementalLayoutEngine.NODES_GAP;
            newPosition = Position.at(x, y);
        }
        this.last = node;
        return newPosition;
    }

    private Optional<Position> getSpecificNodePositionFromEvent(Optional<IDiagramElementEvent> optionalDiagramElementEvent, NodeLayoutData node) {
        Position position = null;
        if (optionalDiagramElementEvent.isPresent()) {
            IDiagramElementEvent diagramElementEvent = optionalDiagramElementEvent.get();
            if (diagramElementEvent instanceof MoveEvent) {
                position = this.getPossiblePositionFromMoveEvent(node, (MoveEvent) diagramElementEvent);
            } else if (diagramElementEvent instanceof ResizeEvent) {
                position = this.getPossiblePositionFromResizeEvent(node, (ResizeEvent) diagramElementEvent);
            }
        }
        return Optional.ofNullable(position);
    }

    private Position getPossiblePositionFromMoveEvent(NodeLayoutData node, MoveEvent moveEvent) {
        if (moveEvent.getNodeId().equals(node.getId())) {
            return moveEvent.getNewPosition();
        }
        return null;
    }

    private Position getPossiblePositionFromResizeEvent(NodeLayoutData node, ResizeEvent resizeEvent) {
        Position position = null;
        // The current node is directly resized
        if (resizeEvent.getNodeId().equals(node.getId())) {
            Position oldPosition = node.getPosition();
            double newX = oldPosition.getX() - resizeEvent.getPositionDelta().getX();
            double newY = oldPosition.getY() - resizeEvent.getPositionDelta().getY();
            position = Position.at(newX, newY);

        }
        // The current node is a direct child of a resized container.
        else if (resizeEvent.getNodeId().toString().equals(node.getParent().getId().toString())) {
            // The parent has been resized so the relative new position may have to be changed to keep the same
            // absolute coordinates
            Position oldPosition = node.getPosition();
            double newX = oldPosition.getX() + resizeEvent.getPositionDelta().getX();
            double newY = oldPosition.getY() + resizeEvent.getPositionDelta().getY();
            position = Position.at(newX, newY);
        }
        return position;
    }

    /**
     * Provides the maximal bottom value within the given parent children.
     *
     * @param parent
     *            the node parent.
     * @return the maximal bottom value or null if no already positioned child has been found.
     */
    private Double findMaxBottom(IContainerLayoutData parent) {
        Double bottom = null;
        for (NodeLayoutData node : parent.getChildrenNodes()) {
            Position nodePosition = node.getPosition();
            // If the node has not been located, we skip it from the calculation
            if (!this.isUndefined(nodePosition)) {
                double nodeBottom = nodePosition.getY() + node.getSize().getHeight();
                if (bottom == null) {
                    bottom = nodeBottom;
                } else {
                    bottom = Math.max(bottom, nodeBottom);
                }
            }
        }
        return bottom;
    }

    private boolean isUndefined(Position position) {
        return position.getX() == -1 || position.getY() == -1;
    }

    public void reset() {
        this.last = null;
    }
}
