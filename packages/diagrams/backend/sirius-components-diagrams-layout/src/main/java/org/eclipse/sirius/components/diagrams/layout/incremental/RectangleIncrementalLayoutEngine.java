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
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.Size.Builder;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeLabelSizeProvider;

/**
 * The incremental layout engine to layout rectangle nodes.
 *
 * @author gcoutable
 */
public class RectangleIncrementalLayoutEngine implements INodeIncrementalLayoutEngine {

    private final ChildLayoutStrategyEngineHandler childLayoutStrategyEngineHandler;

    private final IBorderNodeLayoutEngine borderNodeLayoutEngine;

    private final List<ICustomNodeLabelPositionProvider> customLabelPositionProviders;

    private final NodeLabelSizeProvider nodeLabelSizeProvider;

    public RectangleIncrementalLayoutEngine(ChildLayoutStrategyEngineHandler childLayoutStrategyEngineHandler, IBorderNodeLayoutEngine borderNodeLayoutEngine,
            List<ICustomNodeLabelPositionProvider> customLabelPositionProviders) {
        this.childLayoutStrategyEngineHandler = Objects.requireNonNull(childLayoutStrategyEngineHandler);
        this.borderNodeLayoutEngine = Objects.requireNonNull(borderNodeLayoutEngine);
        this.customLabelPositionProviders = Objects.requireNonNull(customLabelPositionProviders);
        this.nodeLabelSizeProvider = new NodeLabelSizeProvider();
    }

    @Override
    public double getNodeMinimalWidth(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        IPropertyHolder nodeTypePropertyHolder = layoutConfigurator.configureByType(node.getNodeType());
        double nodeMinimalWidth = 0;
        if (this.isNodeLabelInside(node, layoutConfigurator)) {
            ElkPadding padding = nodeTypePropertyHolder.getProperty(CoreOptions.PADDING);
            nodeMinimalWidth = node.getLabel().getTextBounds().getSize().getWidth() + padding.right + padding.left;
        }

        if (nodeTypePropertyHolder.hasProperty(CoreOptions.NODE_SIZE_CONSTRAINTS) && nodeTypePropertyHolder.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS).contains(SizeConstraint.MINIMUM_SIZE)) {
            KVector childMinSize = nodeTypePropertyHolder.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
            if (nodeMinimalWidth < childMinSize.x) {
                nodeMinimalWidth = childMinSize.x;
            }
        }

        return nodeMinimalWidth;
    }

    @Override
    public NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        NodeLabelPositionProvider nodeLabelPositionProvider = new NodeLabelPositionProvider(layoutConfigurator);
        Bounds initialNodeBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();

        Size childrenAreaSize = this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, layoutConfigurator).orElseGet(() -> Size.of(0, 0));

        IPropertyHolder childrenLayoutStrategyPropertyHolder = layoutConfigurator.configureByChildrenLayoutStrategy(nodeLayoutData.getChildrenLayoutStrategy().getClass());
        ElkPadding elkPadding = childrenLayoutStrategyPropertyHolder.getProperty(CoreOptions.PADDING);
        double xOffset = elkPadding.left;
        double yOffset = elkPadding.top + this.handleHeader(nodeLayoutData);

        this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, layoutConfigurator, childrenAreaSize.getWidth());

        // Update node size
        Builder newNodeSizeBuilder = Size.newSize().width(elkPadding.left + childrenAreaSize.getWidth() + elkPadding.right);
        double newNodeHeight = elkPadding.top + childrenAreaSize.getHeight() + elkPadding.bottom;

        newNodeHeight += this.handleHeader(nodeLayoutData);

        if (this.shouldConsiderNodeLabel(nodeLayoutData, layoutConfigurator)) {
            Size labelWithPaddingSize = this.nodeLabelSizeProvider.getLabelWithPaddingSize(nodeLayoutData, layoutConfigurator);
            newNodeHeight += labelWithPaddingSize.getHeight();
            yOffset += labelWithPaddingSize.getHeight();
        }

        IPropertyHolder nodePropertyHolder = layoutConfigurator.configureByType(nodeLayoutData.getNodeType());
        EnumSet<SizeConstraint> sizeConstraints = nodePropertyHolder.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS);
        if (sizeConstraints.contains(SizeConstraint.MINIMUM_SIZE)) {
            KVector minimumSize = nodePropertyHolder.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
            if (newNodeHeight < minimumSize.y) {
                newNodeHeight = minimumSize.y;
            }
        }

        if (newNodeHeight < LayoutOptionValues.MIN_HEIGHT_CONSTRAINT) {
            newNodeHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;
        }

        newNodeSizeBuilder.height(newNodeHeight);
        Size newNodeSize = newNodeSizeBuilder.build();

        if (!nodeLayoutData.getSize().equals(newNodeSize)) {
            nodeLayoutData.setSize(newNodeSize);
            nodeLayoutData.setChanged(true);
        }

        // Update children position
        for (NodeLayoutData child : nodeLayoutData.getChildrenNodes()) {
            // @formatter:off
            Position updatedChildNodePosition = Position.newPosition()
                    .x(child.getPosition().getX() + xOffset)
                    .y(child.getPosition().getY() + yOffset)
                    .build();
            // @formatter:on
            if (!updatedChildNodePosition.equals(child.getPosition())) {
                child.setPosition(updatedChildNodePosition);
                child.setChanged(true);
            }
        }

        // update the border node once the current node bounds are updated
        Bounds newBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();
        List<BorderNodesOnSide> borderNodesOnSide = this.borderNodeLayoutEngine.layoutBorderNodes(optionalDiagramEvent, nodeLayoutData.getBorderNodes(), initialNodeBounds, newBounds,
                layoutConfigurator);

        // recompute the label
        if (nodeLayoutData.getLabel() != null) {
            // @formatter:off
            Position nodeLabelPosition = this.customLabelPositionProviders.stream()
                    .map(customLabelPositionProvider -> customLabelPositionProvider.getLabelPosition(layoutConfigurator, nodeLayoutData.getLabel().getTextBounds().getSize(), nodeLayoutData.getSize(),
                            nodeLayoutData.getNodeType(), nodeLayoutData.getStyle()))
                    .flatMap(Optional::stream)
                    .findFirst()
                    .orElseGet(() -> nodeLabelPositionProvider.getPosition(nodeLayoutData, nodeLayoutData.getLabel(), borderNodesOnSide));
            // @formatter:on
            nodeLayoutData.getLabel().setPosition(nodeLabelPosition);
        }

        return nodeLayoutData;
    }

    @Override
    public NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator, double maxWidth) {
        NodeLabelPositionProvider nodeLabelPositionProvider = new NodeLabelPositionProvider(layoutConfigurator);
        Bounds initialNodeBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();

        Size childrenAreaSize = this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, layoutConfigurator, maxWidth).orElseGet(() -> Size.of(0, 0));

        IPropertyHolder childrenLayoutStrategyPropertyHolder = layoutConfigurator.configureByChildrenLayoutStrategy(nodeLayoutData.getChildrenLayoutStrategy().getClass());
        ElkPadding elkPadding = childrenLayoutStrategyPropertyHolder.getProperty(CoreOptions.PADDING);
        double xOffset = elkPadding.left;
        double yOffset = elkPadding.top + this.handleHeader(nodeLayoutData);

        Builder newNodeSizeBuilder = Size.newSize().width(maxWidth);
        double newNodeHeight = elkPadding.top + childrenAreaSize.getHeight() + elkPadding.bottom;

        if (this.shouldConsiderNodeLabel(nodeLayoutData, layoutConfigurator)) {
            Size labelWithPaddingSize = this.nodeLabelSizeProvider.getLabelWithPaddingSize(nodeLayoutData, layoutConfigurator);
            newNodeHeight += labelWithPaddingSize.getHeight();
            yOffset += labelWithPaddingSize.getHeight();
        }

        IPropertyHolder nodePropertyHolder = layoutConfigurator.configureByType(nodeLayoutData.getNodeType());
        EnumSet<SizeConstraint> sizeConstraints = nodePropertyHolder.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS);
        if (sizeConstraints.contains(SizeConstraint.MINIMUM_SIZE)) {
            KVector minimumSize = nodePropertyHolder.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
            if (newNodeHeight < minimumSize.y) {
                newNodeHeight = minimumSize.y;
            }
        }

        if (newNodeHeight < LayoutOptionValues.MIN_HEIGHT_CONSTRAINT) {
            newNodeHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;
        }

        newNodeSizeBuilder.height(newNodeHeight);
        Size newNodeSize = newNodeSizeBuilder.build();

        if (!nodeLayoutData.getSize().equals(newNodeSize)) {
            nodeLayoutData.setSize(newNodeSize);
            nodeLayoutData.setChanged(true);
        }

        // Update children position
        for (NodeLayoutData child : nodeLayoutData.getChildrenNodes()) {
            // @formatter:off
            Position updatedChildNodePosition = Position.newPosition()
                    .x(child.getPosition().getX() + xOffset)
                    .y(child.getPosition().getY() + yOffset)
                    .build();
            // @formatter:on
            if (!updatedChildNodePosition.equals(child.getPosition())) {
                child.setPosition(updatedChildNodePosition);
                child.setChanged(true);
            }
        }

        // update the border node once the current node bounds are updated
        Bounds newBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();
        List<BorderNodesOnSide> borderNodesOnSide = this.borderNodeLayoutEngine.layoutBorderNodes(optionalDiagramEvent, nodeLayoutData.getBorderNodes(), initialNodeBounds, newBounds,
                layoutConfigurator);

        // recompute the label
        if (nodeLayoutData.getLabel() != null) {
            // @formatter:off
            Position nodeLabelPosition = this.customLabelPositionProviders.stream()
                    .map(customLabelPositionProvider -> customLabelPositionProvider.getLabelPosition(layoutConfigurator, nodeLayoutData.getLabel().getTextBounds().getSize(), nodeLayoutData.getSize(),
                            nodeLayoutData.getNodeType(), nodeLayoutData.getStyle()))
                    .flatMap(Optional::stream)
                    .findFirst()
                    .orElseGet(() -> nodeLabelPositionProvider.getPosition(nodeLayoutData, nodeLayoutData.getLabel(), borderNodesOnSide));
            // @formatter:on
            nodeLayoutData.getLabel().setPosition(nodeLabelPosition);
        }

        return nodeLayoutData;
    }

    /**
     * Adds a padding top if the node has a header.
     *
     * NOTE: We will be able to remove that once the incremental layout will inherit from Elk properties directly
     * instead of the sirius layout configuration.
     *
     * @param nodeLayoutData
     *            The node layout data with the header of not
     * @return The padding top to add to the node size and as a padding for the first children
     */
    private double handleHeader(NodeLayoutData nodeLayoutData) {
        INodeStyle nodeStyle = nodeLayoutData.getStyle();
        if (nodeStyle instanceof RectangularNodeStyle) {
            RectangularNodeStyle rectangularNodeStyle = (RectangularNodeStyle) nodeStyle;
            if (rectangularNodeStyle.isWithHeader()) {
                return rectangularNodeStyle.getBorderSize() + 5;
            }
        }
        return 0;
    }

    @Override
    public double getNodeWidth(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        IPropertyHolder childrenLayoutStrategyPropertyHolder = layoutConfigurator.configureByChildrenLayoutStrategy(nodeLayoutData.getChildrenLayoutStrategy().getClass());
        ElkPadding elkPadding = childrenLayoutStrategyPropertyHolder.getProperty(CoreOptions.PADDING);

        Size childrenAreaSize = this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, layoutConfigurator).orElseGet(() -> Size.of(0, 0));

        return elkPadding.left + childrenAreaSize.getWidth() + elkPadding.right;
    }

    private boolean shouldConsiderNodeLabel(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        boolean shouldConsiderNodeLabel = true;
        LabelLayoutData label = node.getLabel();

        shouldConsiderNodeLabel = label.getTextBounds().getSize().getWidth() != 0;
        shouldConsiderNodeLabel = shouldConsiderNodeLabel && this.isNodeLabelInside(node, layoutConfigurator);

        return shouldConsiderNodeLabel;
    }

    private boolean isNodeLabelInside(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        IPropertyHolder nodeTypePropertyHolder = layoutConfigurator.configureByType(node.getNodeType());
        EnumSet<NodeLabelPlacement> nodeLabelPlacement = nodeTypePropertyHolder.getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
        return nodeLabelPlacement.contains(NodeLabelPlacement.INSIDE);
    }
}
