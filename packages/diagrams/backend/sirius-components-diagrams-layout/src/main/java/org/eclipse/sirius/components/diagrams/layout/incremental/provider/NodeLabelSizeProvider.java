/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * Provides the size of a node label.
 *
 * @author gcoutable
 */
public class NodeLabelSizeProvider {

    /**
     * Returns the size of the given node label according to the layout configuration on which we added the label
     * padding if the label is inside the node. If the label is outside the node, returns the label size only.
     *
     * <p>
     * The label padding will be taken into account regarding its placement inside the node. For example: the label
     * padding top will be added to the size returned only if the label is placed on top. So a label placed in the
     * center of its node will not have any padding.
     * </p>
     *
     * @param node
     *            The node holding the label we want the size
     * @param layoutConfigurator
     *            The layout configuration
     * @return the size of the label on which the label padding has been added according to the configuration.
     */
    public Size getLabelWithPaddingSize(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        IPropertyHolder nodeProperties = layoutConfigurator.configureByType(node.getNodeType());
        EnumSet<NodeLabelPlacement> placement = nodeProperties.getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
        Size labelSize = node.getLabel().getTextBounds().getSize();

        if (placement.contains(NodeLabelPlacement.OUTSIDE)) {
            return labelSize;
        }

        ElkPadding labelPadding = this.getLabelPadding(node, layoutConfigurator);
        double paddingTop = 0;
        double paddingBottom = 0;
        double paddingLeft = 0;
        double paddingRight = 0;

        if (placement.contains(NodeLabelPlacement.V_TOP)) {
            paddingTop = labelPadding.getTop();
        }

        if (placement.contains(NodeLabelPlacement.V_BOTTOM)) {
            paddingBottom = labelPadding.getBottom();
        }

        if (placement.contains(NodeLabelPlacement.H_LEFT)) {
            paddingLeft = labelPadding.getLeft();
        }

        if (placement.contains(NodeLabelPlacement.H_RIGHT)) {
            paddingRight = labelPadding.getRight();
        }

        // @formatter:off
        return Size.newSize()
                .width(paddingLeft + labelSize.getWidth() + paddingRight)
                .height(paddingTop + labelSize.getHeight() + paddingBottom)
                .build();
        // @formatter:on
    }

    public ElkPadding getLabelPadding(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        ElkPadding padding = null;

        IPropertyHolder iconLabelProperties = layoutConfigurator.configureByType(node.getNodeType());
        if (iconLabelProperties.hasProperty(CoreOptions.SPACING_INDIVIDUAL)) {
            IPropertyHolder individualSpacings = iconLabelProperties.getProperty(CoreOptions.SPACING_INDIVIDUAL);
            if (individualSpacings.hasProperty(CoreOptions.NODE_LABELS_PADDING)) {
                padding = individualSpacings.getProperty(CoreOptions.NODE_LABELS_PADDING);
            }
        }

        if (padding == null && node.getParent() instanceof NodeLayoutData) {
            NodeLayoutData parent = (NodeLayoutData) node.getParent();
            padding = layoutConfigurator.configureByType(parent.getNodeType()).getProperty(CoreOptions.NODE_LABELS_PADDING);
        }

        if (padding == null) {
            padding = CoreOptions.NODE_LABELS_PADDING.getDefault();
        }

        return padding;
    }

}
