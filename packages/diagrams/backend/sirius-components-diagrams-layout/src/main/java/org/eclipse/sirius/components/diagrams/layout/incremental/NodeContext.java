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

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.elk.graph.properties.MapPropertyHolder;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.events.UpdateCollapsingStateEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeLabelSizeProvider;

/**
 * Hold some context for the current node.
 *
 * @author gcoutable
 */
public class NodeContext {
    private final NodeLayoutData node;

    private final boolean areChildrenConsidered;

    private final boolean hasLabel;

    private final boolean isNodeLabelInside;

    private final ElkPadding nodePadding;

    private final ElkPadding nodeLabelPadding;

    private final double headerPadding;

    private final double childrenNodeMargin;

    private final Set<SizeConstraint> sizeConstraints;

    private final double xOffset;

    private final double yOffset;

    private final boolean isNodeBeingExpanded;

    private final Set<NodeLabelPlacement> labelPlacements;

    private final IPropertyHolder nodeTypePropertyHolder;

    private final Optional<ILayoutStrategy> optionalChildrenLayoutStrategy;

    private final boolean hasHeader;

    private final NodeLabelSizeProvider nodeLabelSizeProvider = new NodeLabelSizeProvider();

    private final Optional<IPropertyHolder> optionalChildrenLayoutStrategyPropertyHolder;

    public NodeContext(NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator, Optional<IDiagramEvent> optionalDiagramEvent) {
        this.node = nodeLayoutData;
        this.optionalChildrenLayoutStrategy = Optional.ofNullable(this.node.getChildrenLayoutStrategy());
        this.nodeTypePropertyHolder = new MapPropertyHolder().copyProperties(layoutConfigurator.configureByType(this.node.getNodeType()));
        // @formatter:off
        this.optionalChildrenLayoutStrategyPropertyHolder = this.optionalChildrenLayoutStrategy
                .map(ILayoutStrategy::getClass)
                .map(layoutConfigurator::configureByChildrenLayoutStrategy)
                .map(new MapPropertyHolder()::copyProperties);
        // @formatter:on

        this.areChildrenConsidered = this.shouldConsiderChildren();
        this.hasHeader = this.hasHeader();
        this.hasLabel = this.shouldConsiderNodeLabel();
        this.isNodeLabelInside = this.isLabelInside();
        ElkPadding padding = new ElkPadding(0);
        if (this.areChildrenConsidered && this.optionalChildrenLayoutStrategyPropertyHolder.isPresent()) {
            padding = this.optionalChildrenLayoutStrategyPropertyHolder.get().getProperty(CoreOptions.PADDING);
        }
        this.nodePadding = padding.clone();

        ElkPadding labelPadding = new ElkPadding(0);
        if (this.hasLabel && this.isNodeLabelInside) {
            labelPadding = this.nodeLabelSizeProvider.getLabelPadding(this.node, layoutConfigurator);
        }
        this.nodeLabelPadding = labelPadding.clone();

        this.headerPadding = this.headerPadding();
        this.sizeConstraints = this.nodeTypePropertyHolder.getProperty(CoreOptions.NODE_SIZE_CONSTRAINTS);

        double gapBetweenNode = 0;
        if (this.optionalChildrenLayoutStrategyPropertyHolder.isPresent()) {
            gapBetweenNode = this.optionalChildrenLayoutStrategyPropertyHolder.get().getProperty(CoreOptions.SPACING_NODE_NODE);
        }
        this.childrenNodeMargin = gapBetweenNode;

        double childrenXOffset = 0;
        double childrenYOffset = 0;
        EnumSet<NodeLabelPlacement> nodeLabelPlacements = NodeLabelPlacement.fixed();
        if (this.hasLabel) {
            nodeLabelPlacements = this.getNodeProperty(CoreOptions.NODE_LABELS_PLACEMENT);
        }

        if (this.shouldConsiderChildren()) {
            childrenYOffset = this.nodePadding.top;
            childrenXOffset = this.nodePadding.left;

            if (this.isNodeLabelInside) {
                Size labelSize = this.node.getLabel().getTextBounds().getSize();
                if (nodeLabelPlacements.contains(NodeLabelPlacement.V_TOP)) {
                    childrenYOffset = this.nodeLabelPadding.top + labelSize.getHeight() + this.nodePadding.top + this.headerPadding;
                }

                if (nodeLabelPlacements.contains(NodeLabelPlacement.H_LEFT)) {
                    childrenXOffset = this.nodeLabelPadding.left + labelSize.getWidth() + this.nodePadding.left;
                }
            }

        }
        this.xOffset = childrenXOffset;
        this.yOffset = childrenYOffset;
        this.labelPlacements = nodeLabelPlacements;

        boolean nodeIsBeingExpanded = false;
        if (optionalDiagramEvent.isPresent() && optionalDiagramEvent.get() instanceof UpdateCollapsingStateEvent updateCollapsingStateEvent) {
            if (this.node.getId().equals(updateCollapsingStateEvent.diagramElementId())) {
                nodeIsBeingExpanded = CollapsingState.EXPANDED.equals(updateCollapsingStateEvent.collapsingState());
            }
        }
        this.isNodeBeingExpanded = nodeIsBeingExpanded;
    }

    public NodeLayoutData getNode() {
        return this.node;
    }

    public boolean areChildrenConsidered() {
        return this.areChildrenConsidered;
    }

    public boolean hasLabel() {
        return this.hasLabel;
    }

    public boolean isNodeLabelInside() {
        return this.isNodeLabelInside;
    }

    public Set<NodeLabelPlacement> getLabelPlacements() {
        return this.labelPlacements;
    }

    public ElkPadding getNodePadding() {
        return this.nodePadding;
    }

    public ElkPadding getNodeLabelPadding() {
        return this.nodeLabelPadding;
    }

    public double getHeaderPadding() {
        return this.headerPadding;
    }

    public double getChildrenNodeMargin() {
        return this.childrenNodeMargin;
    }

    public double getXOffset() {
        return this.xOffset;
    }

    public double getYOffset() {
        return this.yOffset;
    }

    public boolean isMinimumSizeConstrained() {
        return this.sizeConstraints.contains(SizeConstraint.MINIMUM_SIZE);
    }

    public <T> T getNodeProperty(IProperty<T> property) {
        T value = null;
        if (this.nodeTypePropertyHolder.hasProperty(property)) {
            value = this.nodeTypePropertyHolder.getProperty(property);
        }

        if (value == null && this.optionalChildrenLayoutStrategyPropertyHolder.isPresent() && this.optionalChildrenLayoutStrategyPropertyHolder.get().hasProperty(property)) {
            value = this.optionalChildrenLayoutStrategyPropertyHolder.get().getProperty(property);
        }

        if (value == null) {
            value = property.getDefault();
        }

        return value;
    }

    public Optional<ILayoutStrategy> getOptionalChildrenLayoutStrategy() {
        return this.optionalChildrenLayoutStrategy;
    }

    public List<NodeLayoutData> getChildrenToLayout() {
        // @formatter:off
        return this.node.getChildrenNodes().stream()
                .filter(nodeData -> !nodeData.isExcludedFromLayoutComputation())
                .toList();
        // @formatter:on
    }

    /**
     * Returns <code>true</code> whether the node has a children layout strategy, has children and at least one of them
     * is visible (not hidden), <code>false</code> otherwise.
     *
     * @return <code>true</code> whether the node has a children layout strategy, has children and at least one of them
     *         is visible (not hidden), <code>false</code> otherwise.
     */
    private boolean shouldConsiderChildren() {
        return this.optionalChildrenLayoutStrategy.isPresent() && !this.node.getChildrenNodes().isEmpty()
                && this.node.getChildrenNodes().stream().anyMatch(nodeData -> !nodeData.isExcludedFromLayoutComputation());
    }

    private boolean shouldConsiderNodeLabel() {
        return this.node.getLabel().getTextBounds().getSize().getWidth() != 0 || this.hasHeader;
    }

    private boolean isLabelInside() {
        Set<NodeLabelPlacement> nodeLabelPlacement = this.nodeTypePropertyHolder.getProperty(CoreOptions.NODE_LABELS_PLACEMENT);
        return nodeLabelPlacement.contains(NodeLabelPlacement.INSIDE);
    }

    private boolean hasHeader() {
        INodeStyle nodeStyle = this.node.getStyle();
        if (nodeStyle instanceof RectangularNodeStyle rectangularNodeStyle) {
            return rectangularNodeStyle.isWithHeader();
        }
        return false;
    }

    /**
     * Adds a padding top if the node has a header.
     *
     * <p>
     * NOTE: We will be able to remove that once the incremental layout will inherit from Elk properties directly
     * instead of the sirius layout configuration.
     * </p>
     *
     * @param nodeLayoutData
     *            The node layout data with the header of not
     * @return The padding top to add to the node size and as a padding for the first children
     */
    private double headerPadding() {
        INodeStyle nodeStyle = this.node.getStyle();
        if (nodeStyle instanceof RectangularNodeStyle rectangularNodeStyle) {
            if (rectangularNodeStyle.isWithHeader()) {
                return rectangularNodeStyle.getBorderSize() + 5;
            }
        }
        return 0;
    }

    /**
     * Returns <code>true</code> whether the current node is being created. Its position and size are undefined.
     *
     * NOTE: Since the data structure of NodeLayoutData is mutable, the value of this method for the same node could
     * change during the layout. It should be used only to determine if a {@link SinglePositionEvent} should be given to
     * the children of this node.
     *
     * @return <code>true</code> whether the current node is being created, <code>false</code> otherwise
     */
    public boolean isNodeBeingCreated() {
        return Position.UNDEFINED.equals(this.node.getPosition()) && Size.UNDEFINED.equals(this.node.getSize());
    }

    public boolean isNodeBeingExpanded() {
        return this.isNodeBeingExpanded;
    }

}
