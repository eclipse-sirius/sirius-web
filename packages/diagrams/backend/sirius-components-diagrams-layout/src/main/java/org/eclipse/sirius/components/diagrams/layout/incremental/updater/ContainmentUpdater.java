/*******************************************************************************
 * Copyright (c) 2021, 2022 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.diagrams.layout.incremental.updater;

import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IConnectable;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * An algorithm dedicated to recompute the size & position of a container, according to its children. The container
 * might be moved if some of its children are outside.
 *
 * @author wpiers
 */
public class ContainmentUpdater {

    /**
     * For elk, all labels have a padding in four directions (top, right, bottom and left). By default the padding value
     * is 5 on each direction, so to have the real size of a node label we must add the
     * {@link LayoutOptionValues#DEFAULT_ELK_NODE_LABELS_PADDING} twice to the node label width and the node label
     * height.
     */
    private static final double DEFAULT_NODE_LABELS_PADDING = 2 * LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;

    /**
     * For elk, all labels have a padding in four directions (top, right, bottom and left). In our elk configuration the
     * default value has been overridden for NodeList, so to have the real width of a node list item label we must add
     * {@link LayoutOptionValues#NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT} and
     * {@link LayoutOptionValues#NODE_LIST_ELK_NODE_LABELS_PADDING_RIGHT} to the node list item label width.
     */
    private static final double NODE_LIST_NODE_LABELS_PADDING_WIDTH = LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_RIGHT;

    /**
     * For elk, all labels have a padding in four directions (top, right, bottom and left). In our elk configuration the
     * default value has been overridden for NodeList, so to have the real height of a node list item label we must add
     * {@link LayoutOptionValues#NODE_LIST_ELK_NODE_LABELS_PADDING_TOP} and
     * {@link LayoutOptionValues#NODE_LIST_ELK_NODE_LABELS_PADDING_BOTTOM} to the node list item label height.
     */
    private static final double NODE_LIST_NODE_LABELS_PADDING_HEIGHT = LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_TOP + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_BOTTOM;

    /**
     * For elk, all nodes have a padding in four directions (top, right, bottom and left) to place the node content. In
     * our elk configuration the default value for the NodeList padding left has been overridden, so to have the real
     * width of a node we must add the {@link LayoutOptionValues#NODE_LIST_ELK_PADDING_LEFT} and the
     * {@link LayoutOptionValues#DEFAULT_ELK_PADDING} to the node content width.
     */
    private static final double NODE_LIST_PADDING_WIDTH = LayoutOptionValues.NODE_LIST_ELK_PADDING_LEFT + LayoutOptionValues.NODE_LIST_ELK_PADDING_RIGHT;

    public ContainmentUpdater() {
    }

    public void update(IContainerLayoutData container) {
        if (this.shouldUpdate(container)) {
            if (container instanceof NodeLayoutData) {
                NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
                if (nodeLayoutData.getChildrenLayoutStrategy() instanceof ListLayoutStrategy) {
                    // No need to change the position of a Node List as contained list items are fixed.
                    this.updateNodeListContentSize(nodeLayoutData);
                    this.updateBottomRight(nodeLayoutData);
                } else {
                    // We do not change the position of diagrams as it disturbs the feedback
                    this.updateTopLeft(nodeLayoutData);
                    this.updateBottomRight(nodeLayoutData);
                }
            } else {
                this.updateBottomRight(container);
            }
        }
    }

    private boolean shouldUpdate(IContainerLayoutData container) {
        boolean shouldUpdate = true;
        boolean isLabelContained = this.isLabelContained(container);
        if (!(isLabelContained && this.isLabelLargerThanNode(container))) {
            if (container instanceof NodeLayoutData) {
                NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
                shouldUpdate = !(nodeLayoutData.isResizedByUser() && this.hasEveryChildWithinItsBounds(nodeLayoutData));
            }

            shouldUpdate = shouldUpdate && (!container.getChildrenNodes().isEmpty() || isLabelContained);
        }

        return shouldUpdate;
    }

    private boolean isLabelLargerThanNode(IContainerLayoutData container) {
        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            double labelWidth = nodeLayoutData.getLabel().getTextBounds().getSize().getWidth() + DEFAULT_NODE_LABELS_PADDING;
            double nodeWidth = nodeLayoutData.getSize().getWidth();
            return labelWidth > nodeWidth;
        }
        return false;
    }

    private boolean hasEveryChildWithinItsBounds(NodeLayoutData nodeLayoutData) {
        return nodeLayoutData.getChildrenNodes().stream().allMatch(child -> this.isChildWithinParentBounds(nodeLayoutData, child));
    }

    private boolean isChildWithinParentBounds(NodeLayoutData parent, NodeLayoutData child) {
        Position parentPosition = parent.getPosition();
        Size parentSize = parent.getSize();

        Position childPosition = child.getPosition();
        Size childSize = child.getSize();

        double parentX = parentPosition.getX();
        double parentY = parentPosition.getY();
        double parentWidth = parentSize.getWidth();
        double parentHeight = parentSize.getHeight();

        double childX = parentX + childPosition.getX();
        double childY = parentY + childPosition.getY();
        double childWidth = childSize.getWidth();
        double childHeight = childSize.getHeight();

        if (childX < parentX || childY < parentY) {
            return false;
        }

        boolean top = this.compareDimensions(parentX, parentWidth, childX, childWidth);
        boolean side = this.compareDimensions(parentY, parentHeight, childY, childHeight);

        return top && side;
    }

    private boolean compareDimensions(double parentStart, double parentDimension, double childStart, double childDimension) {
        double parentEnd = parentStart + parentDimension;
        double childEnd = childStart + childDimension;
        if (childEnd <= childStart) {
            return !(parentEnd >= parentStart || childEnd > parentEnd);
        } else {
            return !(parentEnd >= parentStart && childEnd > parentEnd);
        }
    }

    private boolean isLabelContained(IContainerLayoutData container) {
        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            return nodeLayoutData.getLabel().getLabelType().contains("inside"); //$NON-NLS-1$
        }

        return false;
    }

    /**
     * Uses the wider label between the node list label and all its node list item labels to update the width of every
     * list item
     *
     * @param nodeLayoutData
     *            The layout data of the node list
     */
    private void updateNodeListContentSize(NodeLayoutData nodeLayoutData) {
        Size nodeListLabelSize = nodeLayoutData.getLabel().getTextBounds().getSize();
        Size widerLabel = Size.of(nodeListLabelSize.getWidth() + LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING * 2, nodeListLabelSize.getHeight());
        for (NodeLayoutData nodeListItemLayoutData : nodeLayoutData.getChildrenNodes()) {
            Size labelSize = nodeListItemLayoutData.getLabel().getTextBounds().getSize();
            double labelWidth = labelSize.getWidth() + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_RIGHT + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT;
            if (widerLabel.getWidth() < labelWidth) {
                widerLabel = Size.of(labelWidth, -1);
            }
        }

        for (NodeLayoutData nodeListItemLayoutData : nodeLayoutData.getChildrenNodes()) {
            double childNodeWidth = nodeListItemLayoutData.getSize().getWidth();
            if (childNodeWidth != widerLabel.getWidth()) {
                nodeListItemLayoutData.setSize(Size.of(widerLabel.getWidth(), nodeListItemLayoutData.getSize().getHeight()));
                nodeListItemLayoutData.setChanged(true);
            }
        }
    }

    private void updateTopLeft(IContainerLayoutData container) {
        double minX = 0;
        double minY = 0;
        for (NodeLayoutData child : container.getChildrenNodes()) {
            minX = Math.min(minX, child.getPosition().getX());
            minY = Math.min(minY, child.getPosition().getY());
        }
        double shiftX = 0;
        double shiftY = 0;
        if (minX < 0) {
            shiftX = -minX;
        }
        if (minY < 0) {
            shiftY = -minY;
        }
        if (shiftX > 0 || shiftY > 0) {
            this.shift(container, shiftX, shiftY);
        }
    }

    private void updateBottomRight(IContainerLayoutData container) {
        Size contentSize = this.computeContentSize(container);

        // For "normal" containers, we only resize if they are too small to hold their contents.
        // Lists however are always resized to match exactly their content, even it is means making them smaller.
        boolean isNodeListContainer = container instanceof NodeLayoutData && ((NodeLayoutData) container).getChildrenLayoutStrategy() instanceof ListLayoutStrategy;
        boolean isTooSmallForContent = container.getSize().getWidth() < contentSize.getWidth() || container.getSize().getHeight() < contentSize.getHeight();
        boolean shouldResize = isTooSmallForContent || (isNodeListContainer && !container.getSize().equals(contentSize));
        if (shouldResize) {
            container.setSize(contentSize);
            if (container instanceof IConnectable) {
                ((IConnectable) container).setChanged(true);
            }
        }
    }

    /**
     * Determines the "natural" size needed for the container to hold all its content (label and children), with
     * appropriate padding.
     */
    private Size computeContentSize(IContainerLayoutData container) {
        double contentWidth = LayoutOptionValues.MIN_WIDTH_CONSTRAINT;
        double contentHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;

        if (this.isLabelContained(container)) {
            LabelLayoutData label = ((NodeLayoutData) container).getLabel();
            Size labelSize = label.getTextBounds().getSize();
            contentWidth = Math.max(contentWidth, labelSize.getWidth() + this.getNodeLabelPaddingWidth(container));
            contentHeight = Math.max(contentHeight, labelSize.getHeight() + this.getNodeLabelPaddingHeight(container));
        }

        for (NodeLayoutData child : container.getChildrenNodes()) {
            contentWidth = Math.max(contentWidth, child.getPosition().getX() + child.getSize().getWidth() + this.getPaddingWidth(container));
            contentHeight = Math.max(contentHeight, child.getPosition().getY() + child.getSize().getHeight() + this.getPaddingHeight(container));
        }

        return Size.of(contentWidth, contentHeight);
    }

    private double getNodeLabelPaddingHeight(IContainerLayoutData container) {
        return DEFAULT_NODE_LABELS_PADDING;
    }

    private double getNodeLabelPaddingWidth(IContainerLayoutData container) {
        double nodeLabelPaddingWidth = DEFAULT_NODE_LABELS_PADDING;
        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            switch (nodeLayoutData.getNodeType()) {
            case NodeType.NODE_ICON_LABEL:
                nodeLabelPaddingWidth = NODE_LIST_NODE_LABELS_PADDING_WIDTH;
                break;
            case NodeType.NODE_RECTANGLE:
            default:
                nodeLabelPaddingWidth = DEFAULT_NODE_LABELS_PADDING;
                break;
            }
        }
        return nodeLabelPaddingWidth;
    }

    private double getPaddingHeight(IContainerLayoutData container) {
        double nodeLabelPaddingHeight = LayoutOptionValues.DEFAULT_ELK_PADDING;
        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            if (nodeLayoutData.getChildrenLayoutStrategy() instanceof ListLayoutStrategy) {
                nodeLabelPaddingHeight = LayoutOptionValues.DEFAULT_ELK_PADDING;
            } else if (NodeType.NODE_ICON_LABEL.equals(nodeLayoutData.getNodeType())) {
                nodeLabelPaddingHeight = NODE_LIST_NODE_LABELS_PADDING_HEIGHT;
            }
        }
        return nodeLabelPaddingHeight;
    }

    private double getPaddingWidth(IContainerLayoutData container) {
        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            if (nodeLayoutData.getChildrenLayoutStrategy() instanceof ListLayoutStrategy) {
                return NODE_LIST_PADDING_WIDTH;
            }
        }
        return LayoutOptionValues.DEFAULT_ELK_PADDING;
    }

    private void shift(IContainerLayoutData container, double shiftX, double shiftY) {
        double x = container.getPosition().getX() - shiftX;
        double y = container.getPosition().getY() - shiftY;
        double width = container.getSize().getWidth() + shiftX;
        double height = container.getSize().getHeight() + shiftY;
        container.setPosition(Position.at(x, y));
        container.setSize(Size.of(width, height));
        if (container instanceof IConnectable) {
            ((IConnectable) container).setChanged(true);
        }
        for (NodeLayoutData child : container.getChildrenNodes()) {
            double childX = child.getPosition().getX() + shiftX;
            double childY = child.getPosition().getY() + shiftY;
            child.setPosition(Position.at(childX, childY));
        }
    }

}
