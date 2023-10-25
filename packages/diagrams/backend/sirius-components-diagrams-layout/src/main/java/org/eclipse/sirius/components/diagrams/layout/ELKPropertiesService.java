/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.EnumSet;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.PortLabelPlacement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.springframework.stereotype.Service;

/**
 * Get the label type and other properties from the ELK configuration.
 *
 * @author arichard
 */
@Service
public class ELKPropertiesService {

    public double getMaxPadding(Node node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        double maxPadding = 0d;
        ElkPadding nodeLabelsPadding = layoutConfigurator.configureByType(node.getType()).getProperty(CoreOptions.NODE_LABELS_PADDING);
        double leftPadding = nodeLabelsPadding.getLeft();
        double rightPadding = nodeLabelsPadding.getRight();
        double topPadding = nodeLabelsPadding.getTop();
        double bottomPadding = nodeLabelsPadding.getBottom();
        maxPadding = Double.max(Double.max(leftPadding, rightPadding), Double.max(topPadding, bottomPadding));
        return maxPadding;
    }

    public String getNodeLabelType(Node node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        String labelType = "label:";
        String nodeType = node.getType();
        EnumSet<NodeLabelPlacement> nodeLabelPlacementSet = layoutConfigurator.configureByType(nodeType).getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
        if (nodeLabelPlacementSet.contains(NodeLabelPlacement.OUTSIDE)) {
            labelType += "outside";
            if (NodeType.NODE_IMAGE.equals(nodeType)) {
                labelType += "-center";
            }
        } else if (NodeType.NODE_RECTANGLE.equals(nodeType) && !this.hasHeader(node)) {
            labelType += "inside";
            String verticalAlignment = this.getVerticalAlignment(nodeLabelPlacementSet);
            labelType += verticalAlignment;

            String horizontalAlignment = this.getHorizontalAlignment(nodeLabelPlacementSet);
            labelType += horizontalAlignment;
        } else {
            labelType += "inside-center";
        }

        return labelType;
    }

    public String getBorderNodeLabelType(Node node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        String labelType = "label:";
        String nodeType = node.getType();
        EnumSet<PortLabelPlacement> portLabelPlacementSet = layoutConfigurator.configureByType(nodeType).getProperty(CoreOptions.PORT_LABELS_PLACEMENT);
        if (portLabelPlacementSet.contains(PortLabelPlacement.OUTSIDE)) {
            labelType += "outside";
        } else {
            labelType += "inside";
        }

        return labelType;
    }

    public boolean hasHeader(Node node) {
        return node.getInsideLabel().isIsHeader();
    }

    private String getVerticalAlignment(EnumSet<NodeLabelPlacement> nodeLabelPlacementSet) {
        String verticalAlignment = "-v_top";
        if (nodeLabelPlacementSet.contains(NodeLabelPlacement.V_CENTER)) {
            verticalAlignment = "-v_center";
        } else if (nodeLabelPlacementSet.contains(NodeLabelPlacement.V_BOTTOM)) {
            verticalAlignment = "-v_bottom";
        }
        return verticalAlignment;
    }

    private String getHorizontalAlignment(EnumSet<NodeLabelPlacement> nodeLabelPlacementSet) {
        String horizontalAlignment = "-h_center";
        if (nodeLabelPlacementSet.contains(NodeLabelPlacement.H_LEFT)) {
            horizontalAlignment = "-h_left";
        } else if (nodeLabelPlacementSet.contains(NodeLabelPlacement.H_RIGHT)) {
            horizontalAlignment = "-h_right";
        }
        return horizontalAlignment;
    }
}
