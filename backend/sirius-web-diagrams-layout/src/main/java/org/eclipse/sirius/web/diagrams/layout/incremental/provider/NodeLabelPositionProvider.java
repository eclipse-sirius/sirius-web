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

import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * Provides the position to apply to a Node Label.
 *
 * @author wpiers
 */
public class NodeLabelPositionProvider {

    /** The Spacing between a label and the element to describe. */
    private static final int LABEL_Y_SPACING = 5;

    public Position getPosition(NodeLayoutData node, LabelLayoutData label) {
        if (NodeType.NODE_LIST_ITEM.equals(node.getNodeType())) {
            return Position.at(LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT, 0);
        }

        double x = (node.getSize().getWidth() - label.getTextBounds().getSize().getWidth()) / 2;
        double y = 0;
        if (NodeType.NODE_IMAGE.equals(node.getNodeType())) {
            y = -(label.getTextBounds().getSize().getHeight() + LABEL_Y_SPACING);
        } else if (NodeType.NODE_RECTANGLE.equals(node.getNodeType())) {
            y = LABEL_Y_SPACING;
        }
        return Position.at(x, y);
    }

}
