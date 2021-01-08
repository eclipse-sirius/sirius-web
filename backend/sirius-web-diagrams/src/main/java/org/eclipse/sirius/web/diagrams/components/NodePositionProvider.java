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
import java.util.Optional;

import org.eclipse.sirius.web.diagrams.Diagram;
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

    private Position startingPosition;

    private Position lastPosition;

    private Size lastSize;

    /**
     * Default constructor.
     *
     * @param x
     *            the x coordinate of the new element starting position.
     * @param y
     *            the y coordinate of the new element starting position.
     */
    public NodePositionProvider(double x, double y) {
        // @formatter:off
        this.startingPosition = Position.newPosition()
          .x(x)
          .y(y)
          .build();
        // @formatter:on
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
    public Position getNextPosition(Optional<Object> previousParentElement, Size newSize) {
        Position newPosition;
        if (this.lastPosition == null) {
            newPosition = this.startingPosition;
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
