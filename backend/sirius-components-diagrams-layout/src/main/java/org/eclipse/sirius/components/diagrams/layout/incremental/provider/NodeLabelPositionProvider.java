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

import java.util.EnumSet;
import java.util.Objects;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * Provides the position to apply to a Node Label.
 *
 * @author wpiers
 */
public class NodeLabelPositionProvider {

    private final ISiriusWebLayoutConfigurator layoutConfigurator;

    public NodeLabelPositionProvider(ISiriusWebLayoutConfigurator layoutConfigurator) {
        this.layoutConfigurator = Objects.requireNonNull(layoutConfigurator);
    }

    public Position getPosition(NodeLayoutData node, LabelLayoutData label) {
        double x = 0d;
        double y = 0d;

        switch (node.getNodeType()) {
        case NodeType.NODE_LIST_ITEM:
            ElkPadding nodeLabelsPadding = this.layoutConfigurator.configureByType(NodeType.NODE_LIST_ITEM).getProperty(CoreOptions.NODE_LABELS_PADDING);
            if (nodeLabelsPadding != null) {
                x = nodeLabelsPadding.getLeft();
            }
            break;
        default:
            x = this.getHorizontalPosition(node, label);
            y = this.getVerticalPosition(node, label);
            break;
        }

        return Position.at(x, y);
    }

    private double getHorizontalPosition(NodeLayoutData node, LabelLayoutData label) {
        double x = 0d;
        EnumSet<NodeLabelPlacement> nodeLabelPlacementSet = this.layoutConfigurator.configureByType(node.getNodeType()).getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
        ElkPadding nodeLabelsPadding = this.layoutConfigurator.configureByType(node.getNodeType()).getProperty(CoreOptions.NODE_LABELS_PADDING);
        boolean outside = this.isOutside(nodeLabelPlacementSet);
        double leftPadding = 0d;
        double rightPadding = 0d;
        if (nodeLabelsPadding == null) {
            nodeLabelsPadding = CoreOptions.NODE_LABELS_PADDING.getDefault();
        }
        leftPadding = nodeLabelsPadding.getLeft();
        rightPadding = nodeLabelsPadding.getRight();
        for (NodeLabelPlacement nodeLabelPlacement : nodeLabelPlacementSet) {
            switch (nodeLabelPlacement) {
            case H_LEFT:
                if (outside) {
                    x = -(label.getTextBounds().getSize().getWidth() + leftPadding);
                } else {
                    x = leftPadding;
                }
                break;
            case H_CENTER:
                x = (node.getSize().getWidth() - label.getTextBounds().getSize().getWidth()) / 2;
                break;
            case H_RIGHT:
                if (outside) {
                    x = node.getSize().getWidth() + rightPadding;
                } else {
                    x = node.getSize().getWidth() - label.getTextBounds().getSize().getWidth() - rightPadding;
                }
                break;
            default:
                break;
            }
        }
        return x;
    }

    private double getVerticalPosition(NodeLayoutData node, LabelLayoutData label) {
        double y = 0d;
        EnumSet<NodeLabelPlacement> nodeLabelPlacementSet = this.layoutConfigurator.configureByType(node.getNodeType()).getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
        ElkPadding nodeLabelsPadding = this.layoutConfigurator.configureByType(node.getNodeType()).getProperty(CoreOptions.NODE_LABELS_PADDING);
        boolean outside = this.isOutside(nodeLabelPlacementSet);
        double topPadding = 0d;
        double bottomPadding = 0d;
        if (nodeLabelsPadding == null) {
            nodeLabelsPadding = CoreOptions.NODE_LABELS_PADDING.getDefault();
        }
        topPadding = nodeLabelsPadding.getTop();
        bottomPadding = nodeLabelsPadding.getBottom();
        for (NodeLabelPlacement nodeLabelPlacement : nodeLabelPlacementSet) {
            switch (nodeLabelPlacement) {
            case V_TOP:
                if (outside) {
                    y = -(label.getTextBounds().getSize().getHeight()) - topPadding;
                } else {
                    y = topPadding;
                }
                break;
            case V_CENTER:
                y = (node.getSize().getHeight() - label.getTextBounds().getSize().getHeight()) / 2;
                break;
            case V_BOTTOM:
                if (outside) {
                    y = -(label.getTextBounds().getSize().getHeight());
                } else {
                    y = node.getSize().getHeight() - label.getTextBounds().getSize().getHeight() - bottomPadding;
                }
                break;
            default:
                break;
            }
        }
        return y;
    }

    private boolean isOutside(EnumSet<NodeLabelPlacement> nodeLabelPlacementSet) {
        return nodeLabelPlacementSet.stream().anyMatch(NodeLabelPlacement.OUTSIDE::equals);
    }
}
