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

    public Position getPosition(NodeLayoutData node, LabelLayoutData label) {
        double x = (node.getSize().getWidth() - label.getTextBounds().getSize().getWidth()) / 2;
        double y = 0;

        switch (node.getNodeType()) {
        case NodeType.NODE_LIST_ITEM:
            x = LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT;
            break;
        case NodeType.NODE_LIST:
        case NodeType.NODE_RECTANGLE:
            y = LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;
            break;
        case NodeType.NODE_IMAGE:
            y = -(label.getTextBounds().getSize().getHeight() + LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING);
            break;
        default:
            x = (node.getSize().getWidth() - label.getTextBounds().getSize().getWidth()) / 2;
            y = 0;
            break;
        }

        return Position.at(x, y);
    }

}
