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
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
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
        IPropertyHolder nodeTypePropertyHolder = layoutConfigurator.configureByType(nodeLayoutData.getNodeType());
        Bounds initialNodeBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();

        ElkPadding nodePadding = new ElkPadding(0);
        if (this.shouldConsiderChildren(nodeLayoutData)) {
            IPropertyHolder childrenLayoutStrategyPropertyHolder = layoutConfigurator.configureByChildrenLayoutStrategy(nodeLayoutData.getChildrenLayoutStrategy().getClass());
            nodePadding = childrenLayoutStrategyPropertyHolder.getProperty(CoreOptions.PADDING);
        }

        ElkPadding nodeLabelPadding = new ElkPadding(0);
        if (this.hasLabel(nodeLayoutData) && this.isNodeLabelInside(nodeLayoutData, layoutConfigurator)) {
            nodeLabelPadding = this.nodeLabelSizeProvider.getLabelPadding(nodeLayoutData, layoutConfigurator);
        }

        double xOffset = 0;
        double yOffset = 0;
        Size childrenAreaSize = Size.of(0, 0);
        if (this.shouldConsiderChildren(nodeLayoutData)) {
            xOffset = nodePadding.left;
            yOffset = nodePadding.top;

            if (this.hasLabel(nodeLayoutData) && this.isNodeLabelInside(nodeLayoutData, layoutConfigurator)) {
                EnumSet<NodeLabelPlacement> labelPlacements = nodeTypePropertyHolder.getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
                Size labelSize = nodeLayoutData.getLabel().getTextBounds().getSize();

                if (labelPlacements.contains(NodeLabelPlacement.V_TOP)) {
                    yOffset = nodeLabelPadding.top + labelSize.getHeight() + nodePadding.top + this.handleHeader(nodeLayoutData);
                }

                if (labelPlacements.contains(NodeLabelPlacement.H_LEFT)) {
                    xOffset = nodeLabelPadding.left + labelSize.getWidth() + nodePadding.left;
                }
            }

            childrenAreaSize = this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, layoutConfigurator).orElseGet(() -> Size.of(0, 0));
            this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, layoutConfigurator, childrenAreaSize.getWidth());
        }

        Size newNodeSize = this.getNodeSize(nodeLayoutData, layoutConfigurator, nodeTypePropertyHolder, nodePadding, nodeLabelPadding, childrenAreaSize);
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
            NodeLabelPositionProvider nodeLabelPositionProvider = new NodeLabelPositionProvider(layoutConfigurator);
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
        IPropertyHolder nodeTypePropertyHolder = layoutConfigurator.configureByType(nodeLayoutData.getNodeType());
        Bounds initialNodeBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();

        ElkPadding nodePadding = new ElkPadding(0);
        if (this.shouldConsiderChildren(nodeLayoutData)) {
            IPropertyHolder childrenLayoutStrategyPropertyHolder = layoutConfigurator.configureByChildrenLayoutStrategy(nodeLayoutData.getChildrenLayoutStrategy().getClass());
            nodePadding = childrenLayoutStrategyPropertyHolder.getProperty(CoreOptions.PADDING);
        }

        ElkPadding nodeLabelPadding = new ElkPadding(0);
        if (this.hasLabel(nodeLayoutData) && this.isNodeLabelInside(nodeLayoutData, layoutConfigurator)) {
            nodeLabelPadding = this.nodeLabelSizeProvider.getLabelPadding(nodeLayoutData, layoutConfigurator);
        }

        double xOffset = 0;
        double yOffset = 0;
        Size childrenAreaSize = Size.of(maxWidth, 0);
        if (this.shouldConsiderChildren(nodeLayoutData)) {
            xOffset = nodePadding.left;
            yOffset = nodePadding.top;

            if (this.hasLabel(nodeLayoutData) && this.isNodeLabelInside(nodeLayoutData, layoutConfigurator)) {
                EnumSet<NodeLabelPlacement> labelPlacements = nodeTypePropertyHolder.getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
                Size labelSize = nodeLayoutData.getLabel().getTextBounds().getSize();

                if (labelPlacements.contains(NodeLabelPlacement.V_TOP)) {
                    yOffset = nodeLabelPadding.top + labelSize.getHeight() + nodePadding.top + this.handleHeader(nodeLayoutData);
                }

                if (labelPlacements.contains(NodeLabelPlacement.H_LEFT)) {
                    xOffset = nodeLabelPadding.left + labelSize.getWidth() + nodePadding.left;
                }
            }

            childrenAreaSize = this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, layoutConfigurator, maxWidth).orElseGet(() -> Size.of(0, 0));
        }

        Size newNodeSize = this.getNodeSize(nodeLayoutData, layoutConfigurator, nodeTypePropertyHolder, nodePadding, nodeLabelPadding, childrenAreaSize);
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
            NodeLabelPositionProvider nodeLabelPositionProvider = new NodeLabelPositionProvider(layoutConfigurator);
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
    public double getNodeWidth(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        IPropertyHolder childrenLayoutStrategyPropertyHolder = layoutConfigurator.configureByChildrenLayoutStrategy(nodeLayoutData.getChildrenLayoutStrategy().getClass());
        ElkPadding elkPadding = childrenLayoutStrategyPropertyHolder.getProperty(CoreOptions.PADDING);

        Size childrenAreaSize = this.childLayoutStrategyEngineHandler.layoutChildren(optionalDiagramEvent, nodeLayoutData, layoutConfigurator).orElseGet(() -> Size.of(0, 0));

        return elkPadding.left + childrenAreaSize.getWidth() + elkPadding.right;
    }

    private boolean shouldConsiderChildren(NodeLayoutData node) {
        return node.getChildrenLayoutStrategy() != null && !node.getChildrenNodes().isEmpty();
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

    private boolean hasHeader(NodeLayoutData nodeLayoutData) {
        INodeStyle nodeStyle = nodeLayoutData.getStyle();
        if (nodeStyle instanceof RectangularNodeStyle) {
            RectangularNodeStyle rectangularNodeStyle = (RectangularNodeStyle) nodeStyle;
            return rectangularNodeStyle.isWithHeader();
        }
        return false;
    }

    private Size getNodeSize(NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator, IPropertyHolder nodeTypePropertyHolder, ElkPadding nodePadding,
            ElkPadding nodeLabelPadding, Size childrenAreaSize) {
        double newNodeWidth = LayoutOptionValues.MIN_WIDTH_CONSTRAINT;
        double newNodeHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;
        if (nodeTypePropertyHolder.hasProperty(CoreOptions.NODE_SIZE_CONSTRAINTS) && nodeTypePropertyHolder.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS).contains(SizeConstraint.MINIMUM_SIZE)) {
            KVector minSize = nodeTypePropertyHolder.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
            newNodeWidth = minSize.x;
            newNodeHeight = minSize.y;
        }

        double labelOnlyHeight = 0;
        double labelAndChildrenHeight = 0;
        double labelOnlyWidth = 0;
        double labelAndChildrenWidth = 0;
        EnumSet<NodeLabelPlacement> labelPlacements = nodeTypePropertyHolder.getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
        Size labelSize = Size.of(0, 0);
        if (this.hasLabel(nodeLayoutData) && this.isNodeLabelInside(nodeLayoutData, layoutConfigurator)) {
            labelSize = nodeLayoutData.getLabel().getTextBounds().getSize();
        }
        if (labelPlacements.contains(NodeLabelPlacement.V_CENTER)) {
            labelAndChildrenHeight = nodePadding.top + childrenAreaSize.getHeight() + nodePadding.bottom;
            labelOnlyHeight = nodeLabelPadding.top + labelSize.getHeight() + nodeLabelPadding.bottom;
        }
        if (labelPlacements.contains(NodeLabelPlacement.V_TOP)) {
            labelAndChildrenHeight = nodeLabelPadding.top + labelSize.getHeight() + this.handleHeader(nodeLayoutData) + nodePadding.top + childrenAreaSize.getHeight() + nodePadding.bottom;
            labelOnlyHeight = nodeLabelPadding.top + labelSize.getHeight() + nodeLabelPadding.bottom;
        }
        if (labelPlacements.contains(NodeLabelPlacement.V_BOTTOM)) {
            labelAndChildrenHeight = nodePadding.top + childrenAreaSize.getHeight() + nodePadding.bottom + labelSize.getHeight() + nodeLabelPadding.bottom;
            labelOnlyHeight = nodeLabelPadding.top + labelSize.getHeight() + nodeLabelPadding.bottom;
        }
        if (labelPlacements.contains(NodeLabelPlacement.H_CENTER)) {
            labelAndChildrenWidth = nodePadding.left + childrenAreaSize.getWidth() + nodePadding.right;
            labelOnlyWidth = nodeLabelPadding.left + labelSize.getWidth() + nodeLabelPadding.right;
        }
        if (labelPlacements.contains(NodeLabelPlacement.H_LEFT)) {
            labelAndChildrenWidth = nodeLabelPadding.left + labelSize.getWidth() + nodePadding.left + childrenAreaSize.getWidth() + nodePadding.right;
            labelOnlyWidth = nodeLabelPadding.left + labelSize.getWidth() + nodeLabelPadding.right;
        }
        if (labelPlacements.contains(NodeLabelPlacement.H_RIGHT)) {
            labelAndChildrenWidth = nodePadding.left + childrenAreaSize.getWidth() + nodePadding.right + labelSize.getWidth() + nodeLabelPadding.right;
            labelOnlyWidth = nodeLabelPadding.left + labelSize.getWidth() + nodeLabelPadding.right;
        }

        if (labelPlacements.contains(NodeLabelPlacement.OUTSIDE)) {
            labelAndChildrenHeight = nodePadding.top + childrenAreaSize.getHeight() + nodePadding.bottom;
            labelAndChildrenWidth = nodePadding.left + childrenAreaSize.getWidth() + nodePadding.right;
        }

        double heightToConsider = Math.max(labelOnlyHeight, labelAndChildrenHeight);
        double widthToConsider = Math.max(labelOnlyWidth, labelAndChildrenWidth);
        if (heightToConsider > newNodeHeight) {
            newNodeHeight = heightToConsider;
        }
        if (widthToConsider > newNodeWidth) {
            newNodeWidth = widthToConsider;
        }

        return Size.of(newNodeWidth, newNodeHeight);
    }

    private boolean hasLabel(NodeLayoutData node) {
        return node.getLabel().getTextBounds().getSize().getWidth() != 0 || this.hasHeader(node);
    }

    private boolean isNodeLabelInside(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        IPropertyHolder nodeTypePropertyHolder = layoutConfigurator.configureByType(node.getNodeType());
        EnumSet<NodeLabelPlacement> nodeLabelPlacement = nodeTypePropertyHolder.getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
        return nodeLabelPlacement.contains(NodeLabelPlacement.INSIDE);
    }
}
