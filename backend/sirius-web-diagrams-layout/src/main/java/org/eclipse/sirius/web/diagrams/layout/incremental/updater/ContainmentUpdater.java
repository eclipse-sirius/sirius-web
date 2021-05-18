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
package org.eclipse.sirius.web.diagrams.layout.incremental.updater;

import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IConnectable;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;

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
                switch (nodeLayoutData.getNodeType()) {
                case NodeType.NODE_LIST:
                    // No need to change the position of a Node List as contained list items are fixed.
                    this.updateContentPosition(nodeLayoutData);
                    this.updateBottomRight(nodeLayoutData);
                    break;
                case NodeType.NODE_LIST_ITEM:
                    // Update the size of a list item regarding the size of its label.
                    this.updateNodeListItem(nodeLayoutData);
                    break;
                default:
                    // We do not change the position of diagrams as it disturbs the feedback
                    this.updateTopLeft(nodeLayoutData);
                    this.updateBottomRight(nodeLayoutData);
                    break;
                }
            } else {
                this.updateBottomRight(container);
            }
        }
    }

    private boolean shouldUpdate(IContainerLayoutData container) {
        boolean shouldUpdate = true;

        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            shouldUpdate = !nodeLayoutData.isResizedByUser();
        }

        shouldUpdate = shouldUpdate && (!container.getChildrenNodes().isEmpty() || this.isLabelContained(container));

        return shouldUpdate;
    }

    private boolean isLabelContained(IContainerLayoutData container) {
        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            return nodeLayoutData.getLabel().getLabelType().contains("inside"); //$NON-NLS-1$
        }

        return false;
    }

    private void updateContentPosition(NodeLayoutData nodeLayoutData) {
        double nextYChildPosition = nodeLayoutData.getLabel().getTextBounds().getSize().getHeight() + LayoutOptionValues.NODE_LIST_ELK_PADDING_TOP + LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;
        for (NodeLayoutData childLayoutData : nodeLayoutData.getChildrenNodes()) {
            double currentYChildPosition = childLayoutData.getPosition().getY();
            if (currentYChildPosition != nextYChildPosition) {
                childLayoutData.setPosition(Position.at(childLayoutData.getPosition().getX(), nextYChildPosition));
            }
            nextYChildPosition = childLayoutData.getPosition().getY() + childLayoutData.getLabel().getTextBounds().getSize().getHeight() + LayoutOptionValues.NODE_LIST_ELK_NODE_NODE_GAP;
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

    private void updateNodeListItem(NodeLayoutData nodeLayoutData) {
        Size labelSize = nodeLayoutData.getLabel().getTextBounds().getSize();
        double width = labelSize.getWidth() + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_RIGHT + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT;
        double height = labelSize.getHeight() + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_TOP + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_BOTTOM;

        Size nodeSize = nodeLayoutData.getSize();
        if (width != nodeSize.getWidth() || height != nodeSize.getHeight()) {
            nodeLayoutData.setSize(Size.of(width, height));
            if (nodeLayoutData instanceof IConnectable) {
                ((IConnectable) nodeLayoutData).setChanged(true);
            }
        }
    }

    private void updateBottomRight(IContainerLayoutData container) {
        double width = container.getSize().getWidth();
        double height = container.getSize().getHeight();
        double maxWidth = LayoutOptionValues.MIN_WIDTH_CONSTRAINT;
        double maxHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;

        if (this.isLabelContained(container)) {
            LabelLayoutData label = ((NodeLayoutData) container).getLabel();
            maxWidth = Math.max(maxWidth, label.getTextBounds().getSize().getWidth() + this.getNodeLabelPaddingWidth(container));
            maxHeight = Math.max(maxHeight, label.getTextBounds().getSize().getHeight() + this.getNodeLabelPaddingHeight(container));
        }

        for (NodeLayoutData child : container.getChildrenNodes()) {
            maxWidth = Math.max(maxWidth, child.getPosition().getX() + child.getSize().getWidth() + this.getPaddingWidth(container));
            maxHeight = Math.max(maxHeight, child.getPosition().getY() + child.getSize().getHeight() + this.getPaddingHeight(container));
        }

        if (maxWidth != width || maxHeight != height) {
            container.setSize(Size.of(maxWidth, maxHeight));
            if (container instanceof IConnectable) {
                ((IConnectable) container).setChanged(true);
            }
        }

        if (container instanceof NodeLayoutData && NodeType.NODE_LIST.equals(((NodeLayoutData) container).getNodeType())) {
            for (NodeLayoutData child : container.getChildrenNodes()) {
                child.setSize(Size.of(container.getSize().getWidth(), child.getSize().getHeight()));
                if (child instanceof IConnectable) {
                    ((IConnectable) container).setChanged(true);
                }
            }
        }

    }

    private double getNodeLabelPaddingHeight(IContainerLayoutData container) {
        return 0;
    }

    private double getNodeLabelPaddingWidth(IContainerLayoutData container) {
        double nodeLabelPaddingWidth = 0;
        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            if (NodeType.NODE_LIST.equals(nodeLayoutData.getNodeType())) {
                nodeLabelPaddingWidth = DEFAULT_NODE_LABELS_PADDING;
            } else if (NodeType.NODE_LIST_ITEM.equals(nodeLayoutData.getNodeType())) {
                nodeLabelPaddingWidth = NODE_LIST_NODE_LABELS_PADDING_WIDTH;
            }
        }
        return nodeLabelPaddingWidth;
    }

    private double getPaddingHeight(IContainerLayoutData container) {
        double nodeLabelPaddingHeight = 0;
        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            if (NodeType.NODE_LIST.equals(nodeLayoutData.getNodeType())) {
                nodeLabelPaddingHeight = LayoutOptionValues.DEFAULT_ELK_PADDING;
            } else if (NodeType.NODE_LIST_ITEM.equals(nodeLayoutData.getNodeType())) {
                nodeLabelPaddingHeight = NODE_LIST_NODE_LABELS_PADDING_HEIGHT;
            }
        }
        return nodeLabelPaddingHeight;
    }

    private double getPaddingWidth(IContainerLayoutData container) {
        if (container instanceof NodeLayoutData) {
            NodeLayoutData nodeLayoutData = (NodeLayoutData) container;
            if (NodeType.NODE_LIST.equals(nodeLayoutData.getNodeType())) {
                return NODE_LIST_PADDING_WIDTH;
            }
        }
        return 0;
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
