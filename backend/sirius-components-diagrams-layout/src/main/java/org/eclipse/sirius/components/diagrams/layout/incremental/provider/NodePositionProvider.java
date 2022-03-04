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
package org.eclipse.sirius.components.diagrams.layout.incremental.provider;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

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
    public Position getPosition(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData node) {
        Position position = node.getPosition();

        Optional<Position> optionalPosition = this.getSpecificNodePositionFromEvent(optionalDiagramElementEvent, node);
        if (optionalPosition.isPresent() && !NodeType.NODE_LIST_ITEM.equals(node.getNodeType())) {
            position = optionalPosition.get();
        } else if (!this.isAlreadyPositioned(node) || NodeType.NODE_LIST_ITEM.equals(node.getNodeType())) {
            Optional<Position> optionalStartingPosition = this.getOptionalStartingPositionFromEvent(optionalDiagramElementEvent);
            if (optionalStartingPosition.isPresent() && this.last == null && !NodeType.NODE_LIST_ITEM.equals(node.getNodeType())) {
                Position parentPosition = node.getParent().getAbsolutePosition();
                double eventX = optionalStartingPosition.get().getX();
                double eventY = optionalStartingPosition.get().getY();
                if (this.isEventPositionInParentBounds(node.getParent(), Position.at(eventX, eventY))) {
                    // The node has been created by a tool and has a fixed position
                    double xPosition = eventX - parentPosition.getX();
                    double yPosition = eventY - parentPosition.getY();
                    position = Position.at(xPosition, yPosition);
                    this.last = node;
                } else {
                    // The new node should appear in the parent, without moving the parent unless necessary
                    position = Position.at(10, 10);
                    this.last = node;
                }
            } else {
                // The node has been created along with others, by a tool or a refresh
                position = this.getNewPosition(node);
            }
        }
        return position;
    }

    private boolean isEventPositionInParentBounds(IContainerLayoutData node, Position eventPosition) {
        Position topLeft = node.getPosition();
        double topLeftX = topLeft.getX();
        double topLeftY = topLeft.getY();
        double eventX = eventPosition.getX();
        double eventY = eventPosition.getY();

        if (topLeftX > eventX || topLeftY > eventY) {
            return false;
        }

        Size nodeSize = node.getSize();
        double bottomRightX = topLeftX + nodeSize.getWidth();
        double bottomRightY = topLeftY + nodeSize.getHeight();

        return (bottomRightX < topLeftX || bottomRightX > eventX) && (bottomRightY < topLeftY || bottomRightY > eventY);
    }

    private Optional<Position> getOptionalStartingPositionFromEvent(Optional<IDiagramEvent> optionalDiagramElementEvent) {
        // @formatter:off
        return optionalDiagramElementEvent.filter(SinglePositionEvent.class::isInstance)
                .map(SinglePositionEvent.class::cast)
                .map(SinglePositionEvent::getPosition);
        // @formatter:on
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
            if (maxBottom == null) {
                newPosition = this.getNewChildPosition(node);
            } else {
                double newY = maxBottom + this.getNodeGap(node);
                newPosition = Position.at(0, newY);
            }
        } else {
            Position lastPosition = this.last.getPosition();
            Size lastSize = this.last.getSize();
            double x = lastPosition.getX();
            double y = lastPosition.getY() + lastSize.getHeight() + this.getNodeGap(node);
            newPosition = Position.at(x, y);
        }
        this.last = node;
        return newPosition;
    }

    private Optional<Position> getSpecificNodePositionFromEvent(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData node) {
        Position position = null;
        if (optionalDiagramElementEvent.isPresent()) {
            IDiagramEvent diagramElementEvent = optionalDiagramElementEvent.get();
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
     * Returns the position of the child that have been created.
     *
     * <p>
     * If the node being positioned is a {@link NodeType#NODE_LIST_ITEM},
     * </p>
     *
     * @param node
     *            the being positioned
     * @return the position of the child that have been created
     */
    private Position getNewChildPosition(NodeLayoutData node) {
        double posX = 0;
        double posY = 0;
        if (NodeType.NODE_LIST_ITEM.equals(node.getNodeType()) && node.getParent() instanceof NodeLayoutData && NodeType.NODE_LIST.equals(((NodeLayoutData) node.getParent()).getNodeType())) {
            NodeLayoutData parentLayoutData = (NodeLayoutData) node.getParent();
            LabelLayoutData parentLabelLayoutData = parentLayoutData.getLabel();
            posY = parentLabelLayoutData.getPosition().getY() + parentLabelLayoutData.getTextBounds().getSize().getHeight() + LayoutOptionValues.NODE_LIST_ELK_PADDING_TOP
                    + LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;
        }
        return Position.at(posX, posY);
    }

    private double getNodeGap(NodeLayoutData node) {
        if (NodeType.NODE_LIST_ITEM.equals(node.getNodeType())) {
            return LayoutOptionValues.NODE_LIST_ELK_NODE_NODE_GAP;
        }
        return IncrementalLayoutEngine.NODES_GAP;
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
