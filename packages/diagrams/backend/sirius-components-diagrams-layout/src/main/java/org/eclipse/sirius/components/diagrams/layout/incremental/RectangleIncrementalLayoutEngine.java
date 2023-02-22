/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.events.UpdateCollapsingStateEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ChildLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ChildrenAreaLaidOutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeLabelPositionProvider;

/**
 * The incremental layout engine to layout rectangle nodes.
 *
 * @author gcoutable
 */
public class RectangleIncrementalLayoutEngine implements INodeIncrementalLayoutEngine {

    private final ChildLayoutStrategyEngineHandler childLayoutStrategyEngineHandler;

    private final IBorderNodeLayoutEngine borderNodeLayoutEngine;

    private final List<ICustomNodeLabelPositionProvider> customLabelPositionProviders;

    public RectangleIncrementalLayoutEngine(ChildLayoutStrategyEngineHandler childLayoutStrategyEngineHandler, IBorderNodeLayoutEngine borderNodeLayoutEngine,
            List<ICustomNodeLabelPositionProvider> customLabelPositionProviders) {
        this.childLayoutStrategyEngineHandler = Objects.requireNonNull(childLayoutStrategyEngineHandler);
        this.borderNodeLayoutEngine = Objects.requireNonNull(borderNodeLayoutEngine);
        this.customLabelPositionProviders = Objects.requireNonNull(customLabelPositionProviders);
    }

    @Override
    public NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator, Optional<Double> optionalMaxWidth) {
        Bounds initialNodeBounds = Bounds.newBounds().position(nodeLayoutData.getPosition()).size(nodeLayoutData.getSize()).build();

        // 1- Prepare to layout the children area : create the context and remove from all children positions the
        // padding top and padding left (label included)
        NodeContext nodeContext = new NodeContext(nodeLayoutData, layoutConfigurator, optionalDiagramEvent);

        // 2- Layout the children area
        Optional<ChildrenAreaLaidOutData> optionalChildrenAreaLayoutData = this.handleChildren(optionalDiagramEvent, nodeContext, layoutConfigurator, optionalMaxWidth);

        // 3- Use the children area layout data to update the node layout data
        if (optionalChildrenAreaLayoutData.isPresent()) {
            ChildrenAreaLaidOutData childrenAreaLayoutData = optionalChildrenAreaLayoutData.get();
            this.processChildrenAreaLayoutResult(childrenAreaLayoutData, nodeContext);
            this.updateChildrenSize(childrenAreaLayoutData, nodeContext);
        }

        Size childrenAreaSize = optionalChildrenAreaLayoutData.map(ChildrenAreaLaidOutData::getSize).orElse(Size.of(0, 0));
        double newNodeWidth = optionalMaxWidth.orElseGet(() -> this.getNodeWidth(optionalDiagramEvent, nodeContext, childrenAreaSize.getWidth()));
        double newNodeHeight = this.getNodeHeight(optionalDiagramEvent, nodeContext, childrenAreaSize.getHeight());
        Size newNodeSize;
        if (optionalDiagramEvent.isEmpty() || !(optionalDiagramEvent.get() instanceof UpdateCollapsingStateEvent)) {
            newNodeSize = Size.of(Math.max(newNodeWidth, initialNodeBounds.getSize().getWidth()), Math.max(newNodeHeight, initialNodeBounds.getSize().getHeight()));
        } else {
            newNodeSize = Size.of(newNodeWidth, newNodeHeight);
        }

        if (!this.getRoundedSize(nodeLayoutData.getSize()).equals(this.getRoundedSize(newNodeSize))) {
            nodeLayoutData.setSize(newNodeSize);
            nodeLayoutData.setChanged(true);
            nodeLayoutData.setResizedByUser(false);
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

        // 4- Returns the updated node layout data
        return nodeLayoutData;
    }

    @Override
    public double getNodeWidth(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        NodeContext nodeContext = new NodeContext(nodeLayoutData, layoutConfigurator, optionalDiagramEvent);
        Optional<ChildrenAreaLaidOutData> optionalChildrenAreaLayoutData = this.handleChildren(optionalDiagramEvent, nodeContext, layoutConfigurator, Optional.empty());
        Size childrenAreaSize = optionalChildrenAreaLayoutData.map(ChildrenAreaLaidOutData::getSize).orElse(Size.of(0, 0));
        return this.getNodeWidth(optionalDiagramEvent, nodeContext, childrenAreaSize.getWidth());
    }

    private Optional<ChildrenAreaLaidOutData> handleChildren(Optional<IDiagramEvent> optionalDiagramEvent, NodeContext nodeContext, ISiriusWebLayoutConfigurator layoutConfigurator,
            Optional<Double> optionalMaxWidth) {
        Optional<ChildrenAreaLaidOutData> optionalChildrenAreaLayoutData = Optional.empty();
        if (nodeContext.areChildrenConsidered()) {
            // @formatter:off
            List<ChildLayoutData> childrenLayoutData = nodeContext.getChildrenToLayout().stream()
                    .filter(nodeData -> !nodeData.isExcludedFromLayoutComputation())
                    .map(nodeData -> this.createFromNodeLayoutData(nodeData, nodeContext.getYOffset(), nodeContext.getXOffset()))
                    .toList();

            Size parentMinimalSize = Size.newSize()
                    .width(this.getNodeMinimalWidthConsideringChildren(nodeContext, 0, true))
                    .height(this.getNodeMinimalHeightConsideringChildren(nodeContext, 0))
                    .build();

            Position nodeAbsolutePosition = nodeContext.getNode().getAbsolutePosition();
            ChildrenAreaLayoutContext.Builder childrenAreaLayoutContextBuilder = ChildrenAreaLayoutContext.newChildrenAreaLayoutContext(nodeContext.getNode().getId())
                    .parentSize(nodeContext.getNode().getSize())
                    .parentMinimalSize(parentMinimalSize)
                    .parentResizedByUser(nodeContext.getNode().isResizedByUser())
                    .childrenLayoutData(childrenLayoutData)
                    .childrenLayoutStrategy(nodeContext.getOptionalChildrenLayoutStrategy().get())
                    .parentBeingCreated(nodeContext.isNodeBeingCreated())
                    .parentBeingExpanded(nodeContext.isNodeBeingExpanded())
                    .nodeMargin(nodeContext.getChildrenNodeMargin())
                    .absolutePosition(Position.at(nodeAbsolutePosition.getX() + nodeContext.getXOffset(), nodeAbsolutePosition.getY() + nodeContext.getYOffset()));
            // @formatter:on

            optionalDiagramEvent.map(event -> this.adaptDiagramEvent(event, nodeContext)).ifPresent(childrenAreaLayoutContextBuilder::optionalDiagramEvent);
            optionalMaxWidth.map(maxWidth -> this.computeChildrenAreaWidth(maxWidth, nodeContext)).ifPresent(childrenAreaLayoutContextBuilder::optionalChildrenAreaWidth);

            optionalChildrenAreaLayoutData = this.childLayoutStrategyEngineHandler.layoutChildrenArea(childrenAreaLayoutContextBuilder.build(), layoutConfigurator);
        }
        return optionalChildrenAreaLayoutData;
    }

    /**
     * Adapts to the children area the diagram event if needed.
     *
     * <ul>
     * <li>The move event <code>newPosition</code> will be adapted to the children area if it concerns a direct child
     * (the position of this event is relative to the parent)</li>
     * <li>The single position event will be transmitted to the children only if the current node is already positioned
     * in order to be handled later. Returns <code>null</code> if the current node is not positioned, meaning the event
     * is meant to be used to position the current node (not its children).</li>
     * <li>The resize event will be transmitted to the children layout engine that will handle it if needed.</li>
     * </ul>
     *
     * @param diagramEvent
     *            The diagram event to adapt to the children area
     * @param nodeContext
     *            the node context
     * @return the adapted diagram event, or <code>null</code> when children will not need to handle the event.
     */
    private IDiagramEvent adaptDiagramEvent(IDiagramEvent diagramEvent, NodeContext nodeContext) {
        IDiagramEvent adaptedDiagramEvent = diagramEvent;
        if (adaptedDiagramEvent instanceof MoveEvent moveEvent) {
            for (NodeLayoutData childNode : nodeContext.getChildrenToLayout()) {
                if (moveEvent.nodeId().equals(childNode.getId())) {
                    Position newPositionInParentNode = moveEvent.newPosition();
                    adaptedDiagramEvent = new MoveEvent(moveEvent.nodeId(),
                            Position.at(newPositionInParentNode.getX() - nodeContext.getXOffset(), newPositionInParentNode.getY() - nodeContext.getYOffset()));
                }
            }
        } else if (adaptedDiagramEvent instanceof SinglePositionEvent) {
            if (Position.UNDEFINED.equals(nodeContext.getNode().getPosition())) {
                // Since the single position event is not associated to a specific node, it is handled by all
                // unpositioned nodes. Most of the time it matches a single node creation. If the node creation also
                // creates a child, we don't want the single position event to be used to also position the child, and
                // thus, we don't give the event to children.
                adaptedDiagramEvent = null;
            }
        }
        return adaptedDiagramEvent;
    }

    private double computeChildrenAreaWidth(double maxWidth, NodeContext nodeContext) {
        double childrenAreaWidth = maxWidth;

        if (nodeContext.areChildrenConsidered()) {
            double labelWidth = nodeContext.getNode().getLabel().getTextBounds().getSize().getWidth();
            Set<NodeLabelPlacement> labelPlacements = nodeContext.getLabelPlacements();
            if (labelPlacements.contains(NodeLabelPlacement.H_LEFT)) {
                childrenAreaWidth = childrenAreaWidth - (nodeContext.getNodeLabelPadding().left + labelWidth + nodeContext.getNodePadding().left + nodeContext.getNodePadding().right);
            } else if (labelPlacements.contains(NodeLabelPlacement.H_RIGHT)) {
                childrenAreaWidth = childrenAreaWidth - (nodeContext.getNodePadding().left + nodeContext.getNodePadding().right + nodeContext.getNodeLabelPadding().right);
            } else {
                // The label is center horizontally, we don't use node label padding to define the size of the children
                // area.
                childrenAreaWidth = childrenAreaWidth - (nodeContext.getNodePadding().left + nodeContext.getNodePadding().right);
            }
        } else {
            // The node does not have a label or it is not inside
            childrenAreaWidth = childrenAreaWidth - (nodeContext.getNodePadding().left + nodeContext.getNodePadding().right);
        }

        return childrenAreaWidth;
    }

    /**
     * Remove the padding top and padding left from child node to make child node relative to the children area.
     *
     * @param childNode
     *            The child node
     * @param paddingTop
     *            The padding top to remove to make children relative to the children area
     * @param paddingLeft
     *            The padding left to remove to make children relative to the children area
     * @return The child layout data positioned relatively to the children area
     */
    private ChildLayoutData createFromNodeLayoutData(NodeLayoutData childNode, double paddingTop, double paddingLeft) {
        Position positionInParent = childNode.getPosition();

        // @formatter:off
        Position positionInChildArea = Position.newPosition()
                .x(positionInParent.getX() - paddingLeft)
                .y(positionInParent.getY() - paddingTop)
                .build();
        // @formatter:on

        // @formatter:off
        ChildLayoutData.Builder childLayoutDataBuilder = ChildLayoutData.newChildLayoutData(childNode)
                .paddingLeft(paddingLeft)
                .paddingTop(paddingTop);
        // @formatter:on

        // FIXME: Massive workaround to prevent "unpositioned" node to be "positioned" because their (-1, -1) position
        // whould have become positionInChildArea.
        // This workaround could be removed once the position of node will have become optional globally.
        // There are many _FIXME: Massive workaround (undefined position)_ around the code.
        if (!Position.UNDEFINED.equals(childNode.getPosition())) {
            childLayoutDataBuilder.position(positionInChildArea);
        }

        return childLayoutDataBuilder.build();
    }

    private double getNodeWidth(Optional<IDiagramEvent> optionalDiagramEvent, NodeContext nodeContext, double childrenAreaWidth) {
        // @formatter:off
        Optional<Double> resizedWidth = this.getNodeSizeFromEvent(optionalDiagramEvent, nodeContext)
                .map(Size::getWidth)
                .or(() -> Optional.of(nodeContext.getNode()).filter(NodeLayoutData::isResizedByUser).map(NodeLayoutData::getSize).map(Size::getWidth));
        // @formatter:on

        double nodeWidth = this.getNodeMinimalWidthConsideringChildren(nodeContext, childrenAreaWidth, resizedWidth.map(width -> false).orElse(true));

        if (resizedWidth.isPresent()) {
            if (resizedWidth.get() > nodeWidth) {
                nodeWidth = resizedWidth.get();
            }
        }
        return nodeWidth;
    }

    private double getNodeHeight(Optional<IDiagramEvent> optionalDiagramEvent, NodeContext nodeContext, double childrenAreaHeight) {
        double nodeHeight = this.getNodeMinimalHeightConsideringChildren(nodeContext, childrenAreaHeight);

        // @formatter:off
        Optional<Double> optionalHeight = this.getNodeSizeFromEvent(optionalDiagramEvent, nodeContext)
                .map(Size::getHeight)
                .or(() -> Optional.of(nodeContext.getNode()).filter(NodeLayoutData::isResizedByUser).map(NodeLayoutData::getSize).map(Size::getHeight));
        // @formatter:on

        if (optionalHeight.isPresent()) {
            if (optionalHeight.get() > nodeHeight) {
                nodeHeight = optionalHeight.get();
            }
        }

        return nodeHeight;
    }

    private Optional<Size> getNodeSizeFromEvent(Optional<IDiagramEvent> optionalDiagramEvent, NodeContext nodeContext) {
        Optional<Size> size = Optional.empty();
        if (optionalDiagramEvent.isPresent()) {
            if (optionalDiagramEvent.get() instanceof ResizeEvent resizeEvent) {
                if (resizeEvent.nodeId().equals(nodeContext.getNode().getId())) {
                    size = Optional.of(resizeEvent.newSize());
                    nodeContext.getNode().setResizedByUser(true);
                }
            }
        }
        return size;
    }

    private double getNodeMinimalWidthConsideringChildren(NodeContext nodeContext, double childrenAreaWidth, boolean takeLabelWidthIntoAccount) {
        double newNodeWidth = LayoutOptionValues.MIN_WIDTH_CONSTRAINT;
        if (nodeContext.isMinimumSizeConstrained()) {
            KVector minSize = nodeContext.getNodeProperty(CoreOptions.NODE_SIZE_MINIMUM);
            newNodeWidth = Math.max(newNodeWidth, minSize.x);
        }

        double labelOnlyWidth = 0;
        double labelAndChildrenWidth = 0;
        Set<NodeLabelPlacement> labelPlacements = nodeContext.getLabelPlacements();
        double labelWidth = 0;
        if (nodeContext.hasLabel() && nodeContext.isNodeLabelInside() && takeLabelWidthIntoAccount) {
            labelWidth = nodeContext.getNode().getLabel().getTextBounds().getSize().getWidth();
        }
        if (labelPlacements.contains(NodeLabelPlacement.H_CENTER)) {
            labelAndChildrenWidth = nodeContext.getNodePadding().left + childrenAreaWidth + nodeContext.getNodePadding().right;
            labelOnlyWidth = nodeContext.getNodeLabelPadding().left + labelWidth + nodeContext.getNodeLabelPadding().right;
        }
        if (labelPlacements.contains(NodeLabelPlacement.H_LEFT)) {
            labelAndChildrenWidth = nodeContext.getNodeLabelPadding().left + labelWidth + nodeContext.getNodePadding().left + childrenAreaWidth + nodeContext.getNodePadding().right;
            labelOnlyWidth = nodeContext.getNodeLabelPadding().left + labelWidth + nodeContext.getNodeLabelPadding().right;
        }
        if (labelPlacements.contains(NodeLabelPlacement.H_RIGHT)) {
            labelAndChildrenWidth = nodeContext.getNodePadding().left + childrenAreaWidth + nodeContext.getNodePadding().right + labelWidth + nodeContext.getNodeLabelPadding().right;
            labelOnlyWidth = nodeContext.getNodeLabelPadding().left + labelWidth + nodeContext.getNodeLabelPadding().right;
        }

        if (!nodeContext.hasLabel() || labelPlacements.contains(NodeLabelPlacement.OUTSIDE)) {
            labelAndChildrenWidth = nodeContext.getNodePadding().left + childrenAreaWidth + nodeContext.getNodePadding().right;
        }

        double widthToConsider = Math.max(labelOnlyWidth, labelAndChildrenWidth);
        if (widthToConsider > newNodeWidth) {
            newNodeWidth = widthToConsider;
        }

        return newNodeWidth;
    }

    private double getNodeMinimalHeightConsideringChildren(NodeContext nodeContext, double childrenAreaHeight) {
        double newNodeHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;
        if (nodeContext.isMinimumSizeConstrained()) {
            KVector minSize = nodeContext.getNodeProperty(CoreOptions.NODE_SIZE_MINIMUM);
            newNodeHeight = Math.max(newNodeHeight, minSize.y);
        }

        double labelOnlyHeight = 0;
        double labelAndChildrenHeight = 0;
        Set<NodeLabelPlacement> labelPlacements = nodeContext.getLabelPlacements();
        double labelHeight = 0d;
        if (nodeContext.hasLabel() && nodeContext.isNodeLabelInside()) {
            labelHeight = nodeContext.getNode().getLabel().getTextBounds().getSize().getHeight();
        }
        if (labelPlacements.contains(NodeLabelPlacement.V_CENTER)) {
            labelAndChildrenHeight = nodeContext.getNodePadding().top + childrenAreaHeight + nodeContext.getNodePadding().bottom;
            labelOnlyHeight = nodeContext.getNodeLabelPadding().top + labelHeight + nodeContext.getNodeLabelPadding().bottom;
        }
        if (labelPlacements.contains(NodeLabelPlacement.V_TOP)) {
            labelAndChildrenHeight = nodeContext.getNodeLabelPadding().top + labelHeight + nodeContext.getHeaderPadding() + nodeContext.getNodePadding().top + childrenAreaHeight
                    + nodeContext.getNodePadding().bottom;
            labelOnlyHeight = nodeContext.getNodeLabelPadding().top + labelHeight + nodeContext.getNodeLabelPadding().bottom;
        }
        if (labelPlacements.contains(NodeLabelPlacement.V_BOTTOM)) {
            labelAndChildrenHeight = nodeContext.getNodePadding().top + childrenAreaHeight + nodeContext.getNodePadding().bottom + labelHeight + nodeContext.getNodeLabelPadding().bottom;
            labelOnlyHeight = nodeContext.getNodeLabelPadding().top + labelHeight + nodeContext.getNodeLabelPadding().bottom;
        }
        if (!nodeContext.hasLabel() || labelPlacements.contains(NodeLabelPlacement.OUTSIDE)) {
            labelAndChildrenHeight = nodeContext.getNodePadding().top + childrenAreaHeight + nodeContext.getNodePadding().bottom;
        }

        double heightToConsider = Math.max(labelOnlyHeight, labelAndChildrenHeight);
        if (heightToConsider > newNodeHeight) {
            newNodeHeight = heightToConsider;
        }

        return newNodeHeight;
    }

    /**
     * Updates the node and its children node position.
     *
     * <p>
     * It applies the xOffset and yOffset to all children and regarding the delta position, updates the node position.
     * </p>
     *
     * @param childrenAreaLayoutData
     *            The data resulting from the layout of children
     * @param nodeLayoutData
     *            The node layout data
     * @param xOffset
     *            The abscissa where the parent want to put the children area
     * @param yOffset
     *            The ordinal where the parent want to put the children area
     * @return The updated NodeLayoutData
     */
    private NodeLayoutData processChildrenAreaLayoutResult(ChildrenAreaLaidOutData childrenAreaLayoutData, NodeContext nodeContext) {
        Position deltaPosition = childrenAreaLayoutData.getDeltaPosition();
        Map<String, ChildLayoutData> nodeIdToChildLayoutData = childrenAreaLayoutData.getNodeIdToChildLayoutData();
        for (NodeLayoutData child : nodeContext.getChildrenToLayout()) {
            ChildLayoutData childLayoutData = nodeIdToChildLayoutData.get(child.getId());
            // @formatter:off
            Position newChildPosition = Position.newPosition()
                    .x(childLayoutData.getPosition().getX() + nodeContext.getXOffset() - deltaPosition.getX())
                    .y(childLayoutData.getPosition().getY() + nodeContext.getYOffset() - deltaPosition.getY())
                    .build();
            // @formatter:on
            if (!child.getPosition().equals(newChildPosition)) {
                child.setPosition(newChildPosition);
                child.setChanged(true);
                child.setPinned(childLayoutData.isPinned());
            }
        }

        // @formatter:off
        Position newNodePosition = Position.newPosition()
                .x(deltaPosition.getX() + nodeContext.getNode().getPosition().getX())
                .y(deltaPosition.getY() + nodeContext.getNode().getPosition().getY())
                .build();
        // @formatter:on

        // Update the position of the node if it has one. This will be used by the children layout engine parent to
        // check if the parent node has to increase its size because this child has been moved out the children area on
        // top or left. In fact, instead of updating the node position here, we should return a data structure that will
        // be used by the children parent layout engine to update the position of this node because it should be the
        // matter of the parent to position its children, not a child to position itself.
        if (!Position.UNDEFINED.equals(nodeContext.getNode().getPosition()) && !nodeContext.getNode().getPosition().equals(newNodePosition)) {
            nodeContext.getNode().setPosition(newNodePosition);
            nodeContext.getNode().setChanged(true);
            for (NodeLayoutData child : nodeContext.getChildrenToLayout()) {
                child.setChanged(true);
            }
        }

        return nodeContext.getNode();
    }

    private void updateChildrenSize(ChildrenAreaLaidOutData childrenAreaLayoutData, NodeContext nodeContext) {
        Map<String, ChildLayoutData> nodeIdToChildLayoutData = childrenAreaLayoutData.getNodeIdToChildLayoutData();
        for (NodeLayoutData child : nodeContext.getChildrenToLayout()) {
            ChildLayoutData childLayoutData = nodeIdToChildLayoutData.get(child.getId());
            Size childNewSize = childLayoutData.getSize();
            if (!this.getRoundedSize(childNewSize).equals(this.getRoundedSize(child.getSize()))) {
                child.setSize(childNewSize);
                child.setChanged(true);
            }
            child.setResizedByUser(childLayoutData.isResizedByUser());
        }
    }

    /**
     * Round size to 1/1000 of a pixel. It is needed when an image has a width or height with a very big decimal part
     * (e.g: 140.0004672837)
     *
     * @param size
     *            the {@link Size} to round.
     * @return the rounded size.
     */
    private Size getRoundedSize(Size size) {
        BigDecimal roundedWidth = BigDecimal.valueOf(size.getWidth()).setScale(4, RoundingMode.HALF_UP);
        BigDecimal roundedHeight = BigDecimal.valueOf(size.getHeight()).setScale(4, RoundingMode.HALF_UP);
        return Size.of(roundedWidth.doubleValue(), roundedHeight.doubleValue());
    }
}
