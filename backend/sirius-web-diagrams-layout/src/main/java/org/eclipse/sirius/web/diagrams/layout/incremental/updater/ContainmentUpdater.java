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
package org.eclipse.sirius.web.diagrams.layout.incremental.updater;

import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IConnectable;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * An algorithm dedicated to recompute the size & position of a container, according to its children. The container
 * might be moved if some of its children are outside.
 *
 * @author wpiers
 */
public class ContainmentUpdater {

    public void update(IContainerLayoutData container) {
        if (!container.getChildrenNodes().isEmpty()) {
            if (container instanceof NodeLayoutData) {
                // We do not change the position of diagrams as it disturbs the feedback
                this.updateTopLeft(container);
            }
            this.updateBottomRight(container);
        }
    }

    private void updateTopLeft(IContainerLayoutData container) {
        double minX = 0;
        double minY = 0;
        for (NodeLayoutData child : container.getChildrenNodes()) {
            minX = Math.min(minX, child.getPosition().getX());
            minY = Math.min(minY, child.getPosition().getY());
        }
        double shiftX = 0;
        double shiftY = 0;
        if (minX < 0) {
            shiftX = -minX;
        }
        if (minY < 0) {
            shiftY = -minY;
        }
        if (shiftX > 0 || shiftY > 0) {
            this.shift(container, shiftX, shiftY);
        }
    }

    private void updateBottomRight(IContainerLayoutData container) {
        double width = container.getSize().getWidth();
        double height = container.getSize().getHeight();
        double maxWidth = width;
        double maxHeight = height;
        for (NodeLayoutData child : container.getChildrenNodes()) {
            maxWidth = Math.max(maxWidth, child.getPosition().getX() + child.getSize().getWidth());
            maxHeight = Math.max(maxHeight, child.getPosition().getY() + child.getSize().getHeight());
        }
        if (maxWidth > width || maxHeight > height) {
            container.setSize(Size.of(maxWidth, maxHeight));
            if (container instanceof IConnectable) {
                ((IConnectable) container).setChanged(true);
            }
        }
    }

    private void shift(IContainerLayoutData container, double shiftX, double shiftY) {
        double x = container.getPosition().getX() - shiftX;
        double y = container.getPosition().getY() - shiftY;
        double width = container.getSize().getWidth() + shiftX;
        double height = container.getSize().getHeight() + shiftY;
        container.setPosition(Position.at(x, y));
        container.setSize(Size.of(width, height));
        if (container instanceof IConnectable) {
            ((IConnectable) container).setChanged(true);
        }
        for (NodeLayoutData child : container.getChildrenNodes()) {
            double childX = child.getPosition().getX() + shiftX;
            double childY = child.getPosition().getY() + shiftY;
            child.setPosition(Position.at(childX, childY));
        }
    }

}
