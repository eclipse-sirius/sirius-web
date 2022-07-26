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
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
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
     * For elk, all labels have a padding in four directions (top, right, bottom and left). By default the padding value
     * is 5 on each direction, so to have the real size of a node label we must add the
     * {@link LayoutOptionValues#DEFAULT_ELK_NODE_LABELS_PADDING} twice to the node label width and the node label
     * height.
     */
    private static final double DEFAULT_NODE_LABELS_PADDING = 2 * LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;

    /**
     * Provides the new position of the given node. If the node position does not need to be updated, the current
     * position is returned.
     *
     * @param optionalDiagramElementEvent
     *            The event currently taken into account
     * @param node
     *            the node for which we want to retrieve the new position.
     * @return the new {@link Position} if updated or the current one.
     */
    public Position getPosition(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData node) {
        Position position = node.getPosition();
        if (NodeType.NODE_LIST_ITEM.equals(node.getNodeType())) {
            int nodeListItemIndex = node.getParent().getChildrenNodes().indexOf(node);
            Optional<Position> nodeListItemPosition = this.getNodeListItemPosition(node, nodeListItemIndex);
            if (nodeListItemPosition.isPresent()) {
                position = nodeListItemPosition.get();
            }
        } else if (optionalDiagramElementEvent.isPresent()) {
            IDiagramEvent diagramEvent = optionalDiagramElementEvent.get();
            Optional<Position> eventRelativePosition = this.getEventRelativePosition(diagramEvent, node);
            if (eventRelativePosition.isPresent()) {
                position = eventRelativePosition.get();
            }
        }
        return position;
    }

    private Optional<Position> getNodeListItemPosition(NodeLayoutData node, int nodeListItemIndex) {
        Optional<Position> nodeListItemPosition = Optional.empty();
        if (nodeListItemIndex > 0) {
            NodeLayoutData previousNodeListItem = node.getParent().getChildrenNodes().get(nodeListItemIndex - 1);
            Position lastPosition = previousNodeListItem.getPosition();
            Size lastSize = previousNodeListItem.getSize();
            double y = lastPosition.getY() + lastSize.getHeight() + LayoutOptionValues.NODE_LIST_ELK_NODE_NODE_GAP;
            nodeListItemPosition = Optional.of(Position.at(0, y));
        } else if (nodeListItemIndex == 0) {
            // We are positioning the first element during this layout
            if (node.getParent() instanceof NodeLayoutData) {
                NodeLayoutData parentLayoutData = (NodeLayoutData) node.getParent();
                LabelLayoutData parentLabelLayoutData = parentLayoutData.getLabel();
                // @formatter:off
                double posY = parentLabelLayoutData.getTextBounds().getSize().getHeight()
                        + LayoutOptionValues.NODE_LIST_ELK_PADDING_TOP
                        + LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;
                // @formatter:on
                nodeListItemPosition = Optional.of(Position.at(0, posY));
            }
        }
        return nodeListItemPosition;
    }

    private Optional<Position> getEventRelativePosition(IDiagramEvent diagramElementEvent, NodeLayoutData node) {
        Optional<Position> optionalPosition = Optional.empty();
        if (diagramElementEvent instanceof MoveEvent) {
            optionalPosition = this.getPositionFromMoveEvent(node, (MoveEvent) diagramElementEvent);
        } else if (diagramElementEvent instanceof ResizeEvent) {
            optionalPosition = this.getPositionFromResizeEvent(node, (ResizeEvent) diagramElementEvent);
        } else if (diagramElementEvent instanceof SinglePositionEvent) {
            optionalPosition = this.getPositionFromSinglePositionEvent(node, (SinglePositionEvent) diagramElementEvent);
        }
        return optionalPosition;
    }

    private Optional<Position> getPositionFromSinglePositionEvent(NodeLayoutData node, SinglePositionEvent diagramElementEvent) {
        Optional<Position> optionalPosition = Optional.empty();
        if (Position.UNDEFINED.equals(node.getPosition())) {
            // @formatter:off
             Position nodePosition = this.getPositionRelativeToSibling(node)
                    .orElse(this.getPositionRelativeToParent(node, diagramElementEvent));
             optionalPosition = Optional.of(nodePosition);
            // @formatter:on
        }
        return optionalPosition;
    }

    private Position getPositionRelativeToParent(NodeLayoutData node, SinglePositionEvent diagramElementEvent) {
        Position position;
        IContainerLayoutData parent = node.getParent();
        Position eventPosition = diagramElementEvent.getPosition();
        if (Position.UNDEFINED.equals(parent.getPosition())) {
            position = this.getDefaultPosition(node);
        } else {
            if (parent instanceof DiagramLayoutData) {
                position = eventPosition;
            } else if (this.isEventPositionInNodeBounds(parent, eventPosition)) {
                Position parentPosition = parent.getPosition();
                double xPosition = eventPosition.getX() - parentPosition.getX();
                double yPosition = eventPosition.getY() - parentPosition.getY();
                position = Position.at(xPosition, yPosition);
            } else {
                position = this.getDefaultPosition(node);
            }
        }
        return position;
    }

    private Optional<Position> getPositionRelativeToSibling(NodeLayoutData node) {
        Optional<NodeLayoutData> lastPositionedSiblingOptional = this.getLastPositionedSibling(node);
        if (lastPositionedSiblingOptional.isPresent()) {
            NodeLayoutData lastPositionedSibling = lastPositionedSiblingOptional.get();
            Position lastPosition = lastPositionedSibling.getPosition();
            Size lastSize = lastPositionedSibling.getSize();
            double x = lastPosition.getX();
            double y = lastPosition.getY() + lastSize.getHeight() + IncrementalLayoutEngine.NODES_GAP;
            return Optional.of(Position.at(x, y));
        }
        return Optional.empty();
    }

    private Optional<NodeLayoutData> getLastPositionedSibling(NodeLayoutData node) {
        // return the last sibling who has a fixed position.
        // this only works because we know that the nodes are positioned
        // in the order of parent.getChildrenNodes

        // @formatter:off
        return node.getParent().getChildrenNodes().stream()
            .filter(child -> child.isPinned() && !child.equals(node))
            .reduce((first, second) -> second);
        // @formatter:on
    }

    private Optional<Position> getPositionFromMoveEvent(NodeLayoutData node, MoveEvent moveEvent) {
        if (moveEvent.getNodeId().equals(node.getId())) {
            return Optional.of(moveEvent.getNewPosition());
        }
        return Optional.empty();
    }

    private Optional<Position> getPositionFromResizeEvent(NodeLayoutData node, ResizeEvent resizeEvent) {
        Optional<Position> optionalPosition = Optional.empty();
        // The current node is directly resized
        if (resizeEvent.getNodeId().equals(node.getId())) {
            Position oldPosition = node.getPosition();
            double newX = oldPosition.getX() - resizeEvent.getPositionDelta().getX();
            double newY = oldPosition.getY() - resizeEvent.getPositionDelta().getY();
            optionalPosition = Optional.of(Position.at(newX, newY));

        }
        // The current node is a direct child of a resized container.
        else if (resizeEvent.getNodeId().toString().equals(node.getParent().getId().toString())) {
            // The parent has been resized so the relative new position may have to be changed to keep the same
            // absolute coordinates
            Position oldPosition = node.getPosition();
            double newX = oldPosition.getX() + resizeEvent.getPositionDelta().getX();
            double newY = oldPosition.getY() + resizeEvent.getPositionDelta().getY();
            optionalPosition = Optional.of(Position.at(newX, newY));
        }
        return optionalPosition;
    }

    private boolean isEventPositionInNodeBounds(IContainerLayoutData node, Position eventPosition) {
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

    /**
     * The default position of a node is on the top left of its container, with correct label and side paddings.
     *
     * @param node
     *            the node to position
     * @return the default position of the node
     */
    public Position getDefaultPosition(NodeLayoutData node) {
        IContainerLayoutData parent = node.getParent();
        double defaultYPosition = LayoutOptionValues.DEFAULT_ELK_PADDING;
        double labelPaddings = 0;
        if (this.isLabelOfType(parent, "inside")) { //$NON-NLS-1$
            double parentLabelHeight = ((NodeLayoutData) parent).getLabel().getTextBounds().getSize().getHeight();
            labelPaddings += parentLabelHeight + DEFAULT_NODE_LABELS_PADDING;
        }
        if (this.isLabelOfType(node, "outside")) { //$NON-NLS-1$
            double nodeLabelHeight = node.getLabel().getTextBounds().getSize().getHeight();
            labelPaddings += nodeLabelHeight + DEFAULT_NODE_LABELS_PADDING;
        }
        double yPosition = Math.max(labelPaddings, defaultYPosition);

        return Position.at(LayoutOptionValues.DEFAULT_ELK_PADDING, yPosition);
    }

    private boolean isLabelOfType(IContainerLayoutData container, String labelType) {
        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            LabelLayoutData label = nodeLayoutData.getLabel();
            if (label != null) {
                return label.getLabelType().contains(labelType);
            }
        }
        return false;
    }

}
