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

import org.eclipse.sirius.web.diagrams.Position;
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
     * The position where to create a node without existing position. Used to support the creation tool.
     */
    private Position startingPosition;

    /**
     * The last node for which we computed a position. Used when computing several new positions during one refresh.
     */
    private NodeLayoutData last;

    /**
     * Default constructor.
     *
     * @param startingPosition
     *            the position from which new nodes should be created (following a tool execution for instance). Can be
     *            null.
     */
    public NodePositionProvider(Position startingPosition) {
        this.startingPosition = startingPosition;
    }

    /**
     * Default constructor.
     */
    public NodePositionProvider() {
        this(null);
    }

    public Position getPosition(NodeLayoutData node) {
        Position position;
        if (this.startingPosition != null && this.last == null) {
            // The node has been created by a tool and has a fixed position
            Position parentPosition = node.getParent().getAbsolutePosition();
            double xPosition = this.startingPosition.getX() - parentPosition.getX();
            double yPosition = this.startingPosition.getY() - parentPosition.getY();
            position = Position.at(xPosition, yPosition);
            this.last = node;
        } else {
            // The node has been created along with others, by a tool or a refresh
            position = this.getNewPosition(node);
        }
        return position;
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
}
