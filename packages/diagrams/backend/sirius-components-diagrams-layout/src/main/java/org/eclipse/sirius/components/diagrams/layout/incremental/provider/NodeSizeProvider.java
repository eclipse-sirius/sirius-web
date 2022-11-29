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
package org.eclipse.sirius.components.diagrams.layout.incremental.provider;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.springframework.stereotype.Service;

/**
 * Provides the minimal size of a Node.
 *
 * @author fbarbin
 * @author wpiers
 */
@Service
public class NodeSizeProvider {

    /**
     * For ELK, a node with an image style is an image within a node. The padding between a node and its content is 12
     * on each side, so to obtain the same result we have to add 12*2 to the width & height.
     */
    private static final double ELK_SIZE_DIFF = 2 * LayoutOptionValues.DEFAULT_ELK_PADDING;

    private static final int DEFAULT_WIDTH = 150;

    private static final int DEFAULT_HEIGHT = 70;

    private final ImageSizeProvider imageSizeProvider;

    public NodeSizeProvider(ImageSizeProvider imageSizeProvider) {
        this.imageSizeProvider = Objects.requireNonNull(imageSizeProvider);
    }

    /**
     * Provides the new {@link Size} of the given node. If the node size is no need to be updated, the current size is
     * returned. If the provided {@link NodeLayoutData} is resized by the {@link ResizeEvent}, we mark it as
     * resizedByUser.
     *
     * @param optionalDiagramElementEvent
     *            The event which is currently taken into account
     * @param node
     *            The {@link NodeLayoutData} for which we want to retrieve the new size.
     * @param elementId2ElkElement
     *            The map of element id to elk element. Used to retrieve elk properties of a node
     * @return the new {@link Size} if updated or the current one.
     */
    public Size getSize(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData node, Map<String, ElkGraphElement> elementId2ElkElement) {
        Size size;
        if (this.isRelevantResizeEvent(optionalDiagramElementEvent, node)) {
            node.setResizedByUser(true);
            // @formatter:off
            size = optionalDiagramElementEvent.filter(ResizeEvent.class::isInstance)
                    .map(ResizeEvent.class::cast)
                    .map(ResizeEvent::getNewSize)
                    .orElse(Size.UNDEFINED);
            // @formatter:on

        } else if (this.isAlreadySized(node)) {
            size = node.getSize();
        } else {
            size = this.getInitialSize(node, elementId2ElkElement);
        }
        return size;
    }

    private boolean isRelevantResizeEvent(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData node) {
        // @formatter:off
        return optionalDiagramElementEvent.filter(ResizeEvent.class::isInstance)
                .map(ResizeEvent.class::cast)
                .filter(event -> event.getNodeId().equals(node.getId()))
                .isPresent();
        // @formatter:on
    }

    private Size getInitialSize(NodeLayoutData node, Map<String, ElkGraphElement> elementId2ElkElement) {
        double width = DEFAULT_WIDTH;
        double height = DEFAULT_HEIGHT;
        IPropertyHolder nodeProperties = elementId2ElkElement.get(node.getId());
        KVector nodeSizeMinimum = nodeProperties.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
        if (nodeSizeMinimum.equals(new KVector()) && node.getChildrenLayoutStrategy() != null) {
            nodeSizeMinimum = nodeProperties.getProperty(CoreOptions.NODE_SIZE_MINIMUM);
        }
        if (nodeSizeMinimum != null) {
            width = nodeSizeMinimum.x;
            height = nodeSizeMinimum.y;
        }
        INodeStyle style = node.getStyle();
        if (style instanceof ImageNodeStyle) {
            Size imageSize = new ImageNodeStyleSizeProvider(this.imageSizeProvider).getSize((ImageNodeStyle) style);
            width = imageSize.getWidth();
            height = imageSize.getHeight();
            if (!node.isBorderNode()) {
                width += ELK_SIZE_DIFF;
                height += ELK_SIZE_DIFF;
            }
        } else if (NodeType.NODE_ICON_LABEL.equals(node.getNodeType())) {
            Size nodeItemLabelSize = node.getLabel().getTextBounds().getSize();
            width = nodeItemLabelSize.getWidth() + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_RIGHT + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT;
            height = nodeItemLabelSize.getHeight() + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_TOP + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_BOTTOM;
        }
        return Size.of(width, height);
    }

    private boolean isAlreadySized(NodeLayoutData node) {
        Size size = node.getSize();
        INodeStyle style = node.getStyle();
        boolean isSizeUndefined = size.getWidth() == -1 || size.getHeight() == -1;
        // If the node is an Image node Style, we recompute the size since the image scale factor may have changed.
        return node.isResizedByUser() || (!isSizeUndefined && !(style instanceof ImageNodeStyle));
    }
}
