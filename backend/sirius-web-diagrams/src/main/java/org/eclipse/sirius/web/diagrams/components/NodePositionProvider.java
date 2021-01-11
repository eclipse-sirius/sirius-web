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
package org.eclipse.sirius.web.diagrams.components;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;

/**
 * Provides the Position to apply to a new node.
 *
 * @author fbarbin
 */
public class NodePositionProvider {

    private static final int NEXT_POSITION_DELTA = 30;

    private Optional<Position> startingPosition;

    private Position lastPosition;

    private Size lastSize;

    private Map<UUID, Position> movedElementIdToNewPositionMap;

    /**
     * Default constructor.
     *
     * @param startingPosition
     *            the coordinates of the new element starting position.
     * @param movedElementIdToNewPositionMap
     *            a map containing the new position of diagram elements (identified by their UUID)
     */
    public NodePositionProvider(Optional<Position> startingPosition, Map<UUID, Position> movedElementIdToNewPositionMap) {
        this.startingPosition = startingPosition;
        this.movedElementIdToNewPositionMap = Map.copyOf(movedElementIdToNewPositionMap);
    }

    /**
     * Computes the position of a given node.
     *
     * @param nodeId
     *            the node id
     * @param optionalPreviousNode
     *            the optional previous version of the node
     * @param optionalPreviousParentElement
     *            the optional previous parent of the node
     * @param nodeSizeProvider
     *            the node size provider
     * @param style
     *            the node style
     * @return the new position of the node
     */
    public Position getPosition(UUID nodeId, Optional<Node> optionalPreviousNode, Optional<Object> optionalPreviousParentElement, NodeSizeProvider nodeSizeProvider, INodeStyle style) {
        Position position;
        if (this.movedElementIdToNewPositionMap.containsKey(nodeId)) {
            // The node has been moved
            position = this.movedElementIdToNewPositionMap.get(nodeId);
        } else if (optionalPreviousNode.isPresent()) {
            // The node already has a valid position
            position = optionalPreviousNode.get().getPosition();
        } else if (this.startingPosition.isPresent() && this.lastPosition == null) {
            // The node has been created by a tool and has a fixed position
            Size newSize = nodeSizeProvider.getSize(style, List.of());
            // we shift the position according to the node size, so the center of the node matches the mouse position
            // @formatter:off
            position = Position.newPosition()
              .x(this.startingPosition.get().getX() - newSize.getWidth() / 2)
              .y(this.startingPosition.get().getY() - newSize.getHeight() / 2)
              .build();
            // @formatter:on
        } else {
            // The node has been created along with others, by a tool or a refresh
            Size newSize = nodeSizeProvider.getSize(style, List.of());
            position = this.getNextPosition(optionalPreviousParentElement, newSize);
        }
        return position;
    }

    /**
     * Finds an empty position to create an element.
     *
     * @param previousParentElement
     *            the parent element
     * @param newSize
     *            the size of the element to create
     * @return the new position
     */
    private Position getNextPosition(Optional<Object> previousParentElement, Size newSize) {
        Position newPosition;
        if (this.lastPosition == null) {
            newPosition = this.startingPosition.orElse(Position.newPosition().x(0).y(0).build());
        } else {
            // @formatter:off
            newPosition = Position.newPosition()
              .x(this.lastPosition.getX())
              .y(this.lastPosition.getY() + this.lastSize.getHeight() + NEXT_POSITION_DELTA)
              .build();
            // @formatter:on
        }

        // Shift the new position if necessary, according to existing elements
        if (previousParentElement.isPresent()) {
            Object parent = previousParentElement.get();
            Collection<Node> siblings;
            if (parent instanceof Diagram) {
                siblings = ((Diagram) parent).getNodes();
            } else if (parent instanceof Node) {
                siblings = ((Node) parent).getChildNodes();
            } else {
                siblings = Collections.emptyList();
            }
            if (!siblings.isEmpty()) {
                Node element = this.findOverlappingElement(siblings, newSize, newPosition);
                while (element != null) {
                    // @formatter:off
                    newPosition = Position.newPosition()
                      .x(newPosition.getX())
                      .y(element.getPosition().getY() + element.getSize().getHeight() + NEXT_POSITION_DELTA)
                      .build();
                    // @formatter:on
                    element = this.findOverlappingElement(siblings, newSize, newPosition);
                }
            }
        }
        this.lastPosition = newPosition;
        this.lastSize = newSize;
        return newPosition;
    }

    /**
     * Find a an element overlapping the given position & size in the given siblings.
     *
     * @param siblings
     *            the existing elements
     * @param newSize
     *            the new element size
     * @param newPosition
     *            the new element position
     * @return the existing sibling overlapping the given position & size
     */
    private Node findOverlappingElement(Collection<Node> siblings, Size newSize, Position newPosition) {
        for (Node sibling : siblings) {
            Position siblingPosition = sibling.getPosition();
            Size siblingSize = sibling.getSize();
            // @formatter:off
            if (newPosition.getX() < siblingPosition.getX() + siblingSize.getWidth() &&
                newPosition.getX() + newSize.getWidth() > siblingPosition.getX() &&
                newPosition.getY() < siblingPosition.getY() + siblingSize.getHeight() &&
                newPosition.getY() + newSize.getHeight() > siblingPosition.getY()) {
                return sibling;
            }
            // @formatter:on
        }
        return null;
    }

}
