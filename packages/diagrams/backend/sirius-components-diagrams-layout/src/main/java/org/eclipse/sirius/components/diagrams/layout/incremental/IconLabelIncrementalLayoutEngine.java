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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.Size.Builder;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeLabelSizeProvider;

/**
 * The incremental layout engine to layout icon label nodes.
 *
 * @author gcoutable
 */
public class IconLabelIncrementalLayoutEngine implements INodeIncrementalLayoutEngine {

    private final NodeLabelSizeProvider nodeLabelSizeProvider;

    private final List<ICustomNodeLabelPositionProvider> customLabelPositionProviders;

    public IconLabelIncrementalLayoutEngine(List<ICustomNodeLabelPositionProvider> customLabelPositionProviders) {
        this.nodeLabelSizeProvider = new NodeLabelSizeProvider();
        this.customLabelPositionProviders = Objects.requireNonNull(customLabelPositionProviders);
    }

    @Override
    public NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        // Positions the label inside the node icon label if the icon label is just created.
        ElkPadding nodeLabelPadding = this.nodeLabelSizeProvider.getLabelPadding(nodeLayoutData, layoutConfigurator);
        if (nodeLayoutData.getLabel().getPosition().equals(Position.UNDEFINED)) {
            nodeLayoutData.getLabel().setPosition(Position.at(nodeLabelPadding.left, nodeLabelPadding.top));
        }

        Size labelSize = this.nodeLabelSizeProvider.getLabelWithPaddingSize(nodeLayoutData, layoutConfigurator);
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
        NodeLabelPositionProvider nodeLabelPositionProvider = new NodeLabelPositionProvider(layoutConfigurator);

        Builder newNodeSizeBuilder = Size.newSize().width(maxWidth);
        double newNodeHeight = this.nodeLabelSizeProvider.getLabelWithPaddingSize(nodeLayoutData, layoutConfigurator).getHeight();

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

        // recompute the label
        if (nodeLayoutData.getLabel() != null) {
            // @formatter:off
            Position nodeLabelPosition = this.customLabelPositionProviders.stream()
                    .map(customLabelPositionProvider -> customLabelPositionProvider.getLabelPosition(layoutConfigurator, nodeLayoutData.getLabel().getTextBounds().getSize(), nodeLayoutData.getSize(),
                            nodeLayoutData.getNodeType(), nodeLayoutData.getStyle()))
                    .flatMap(Optional::stream)
                    .findFirst()
                    .orElseGet(() -> nodeLabelPositionProvider.getPosition(nodeLayoutData, nodeLayoutData.getLabel(), List.of()));
            // @formatter:on
            nodeLayoutData.getLabel().setPosition(nodeLabelPosition);
        }

        return nodeLayoutData;
    }

    @Override
    public double getNodeWidth(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        return this.nodeLabelSizeProvider.getLabelWithPaddingSize(nodeLayoutData, layoutConfigurator).getWidth();
    }

    @Override
    public double getNodeMinimalWidth(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        return this.nodeLabelSizeProvider.getLabelWithPaddingSize(node, layoutConfigurator).getWidth();
    }
}
