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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import java.util.EnumSet;
import java.util.Optional;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.Size.Builder;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * The incremental layout engine to layout icon label nodes.
 *
 * @author gcoutable
 */
public class IconLabelIncrementalLayoutEngine implements INodeIncrementalLayoutEngine {

    @Override
    public NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        // Positions the label inside the node icon label if the icon label is just created.
        ElkPadding nodeLabelPadding = layoutConfigurator.configureByType(NodeType.NODE_ICON_LABEL).getProperty(CoreOptions.NODE_LABELS_PADDING);
        if (nodeLayoutData.getLabel().getPosition().equals(Position.UNDEFINED)) {
            nodeLayoutData.getLabel().setPosition(Position.at(nodeLabelPadding.left, nodeLabelPadding.top));
        }

        Size labelSize = this.getLabelWithPaddingSize(nodeLayoutData, layoutConfigurator);
        Builder newNodeSizeBuilder = Size.newSize().width(labelSize.getWidth());
        double newNodeHeight = labelSize.getHeight();

        IPropertyHolder nodePropertyHolder = layoutConfigurator.configureByType(nodeLayoutData.getNodeType());
        EnumSet<SizeConstraint> sizeConstraints = nodePropertyHolder.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS);
        if (sizeConstraints.contains(SizeConstraint.MINIMUM_SIZE)) {
            KVector minimumSize = nodePropertyHolder.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
            if (newNodeHeight < minimumSize.y) {
                newNodeHeight = minimumSize.y;
            }
        }

        newNodeSizeBuilder.height(newNodeHeight);
        Size newNodeSize = newNodeSizeBuilder.build();

        if (!nodeLayoutData.getSize().equals(newNodeSize)) {
            nodeLayoutData.setSize(newNodeSize);
            nodeLayoutData.setChanged(true);
        }

        return nodeLayoutData;
    }

    @Override
    public NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator, double maxWidth) {
        // Positions the label inside the node icon label if the icon label is just created.
        ElkPadding nodeLabelPadding = layoutConfigurator.configureByType(NodeType.NODE_ICON_LABEL).getProperty(CoreOptions.NODE_LABELS_PADDING);
        if (nodeLayoutData.getLabel().getPosition().equals(Position.UNDEFINED)) {
            // The position of the label could be different if the regarding the ELK property
            // CoreOptions.NODE_LABELS_PLACEMENT
            nodeLayoutData.getLabel().setPosition(Position.at(nodeLabelPadding.left, nodeLabelPadding.top));
        }

        Builder newNodeSizeBuilder = Size.newSize().width(maxWidth);
        double newNodeHeight = this.getLabelWithPaddingSize(nodeLayoutData, layoutConfigurator).getHeight();

        IPropertyHolder nodePropertyHolder = layoutConfigurator.configureByType(nodeLayoutData.getNodeType());
        EnumSet<SizeConstraint> sizeConstraints = nodePropertyHolder.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS);
        if (sizeConstraints.contains(SizeConstraint.MINIMUM_SIZE)) {
            KVector minimumSize = nodePropertyHolder.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
            if (newNodeHeight < minimumSize.y) {
                newNodeHeight = minimumSize.y;
            }
        }

        newNodeSizeBuilder.height(newNodeHeight);
        Size newNodeSize = newNodeSizeBuilder.build();

        if (!nodeLayoutData.getSize().equals(newNodeSize)) {
            nodeLayoutData.setSize(newNodeSize);
            nodeLayoutData.setChanged(true);
        }

        return nodeLayoutData;
    }

    @Override
    public double getNodeWidth(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        return this.getLabelWithPaddingSize(nodeLayoutData, layoutConfigurator).getWidth();
    }

    private Size getLabelWithPaddingSize(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        IPropertyHolder nodeTypePropertyHolder = layoutConfigurator.configureByType(node.getNodeType());
        ElkPadding labelPadding = nodeTypePropertyHolder.getProperty(CoreOptions.NODE_LABELS_PADDING);
        LabelLayoutData label = node.getLabel();
        return Size.of(labelPadding.left + label.getTextBounds().getSize().getWidth() + labelPadding.right, labelPadding.top + label.getTextBounds().getSize().getHeight() + labelPadding.bottom);
    }

    @Override
    public double getNodeMinimalWidth(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        return this.getLabelWithPaddingSize(node, layoutConfigurator).getWidth();
    }
}
