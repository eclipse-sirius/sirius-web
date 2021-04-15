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
package org.eclipse.sirius.web.diagrams.layout.incremental.provider;

import java.util.Objects;

import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;
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
     * For ELK, a node with an image style is an image within a node. The margin between a node and its content is 12 on
     * each side, so to obtain the same result we have to add 12*2 to the width & height.
     */
    private static final int ELK_SIZE_DIFF = 24;

    private static final int DEFAULT_WIDTH = 150;

    private static final int DEFAULT_HEIGHT = 70;

    private final ImageSizeProvider imageSizeProvider;

    public NodeSizeProvider(ImageSizeProvider imageSizeProvider) {
        this.imageSizeProvider = Objects.requireNonNull(imageSizeProvider);
    }

    /**
     * Provides the new {@link Size} of the given node. If the node size is no need to be updated, the current size is
     * returned.
     *
     * @param node
     *            the node for which we want to retrieve the new size.
     * @return the new {@link Size} if updated or the current one.
     */
    public Size getSize(NodeLayoutData node) {
        Size size;
        if (this.isAlreadySized(node)) {
            size = node.getSize();
        } else {
            size = this.getInitialSize(node);
        }
        return size;
    }

    private Size getInitialSize(NodeLayoutData node) {
        double width = DEFAULT_WIDTH;
        double height = DEFAULT_HEIGHT;
        INodeStyle style = node.getStyle();
        if (style instanceof ImageNodeStyle) {
            Size imageSize = new ImageNodeStyleSizeProvider(this.imageSizeProvider).getSize((ImageNodeStyle) style);
            width = imageSize.getWidth() + ELK_SIZE_DIFF;
            height = imageSize.getHeight() + ELK_SIZE_DIFF;
        }
        return Size.of(width, height);
    }

    private boolean isAlreadySized(NodeLayoutData node) {
        Size size = node.getSize();
        INodeStyle style = node.getStyle();
        // If the node is an Image node Style, we recompute the size since the image scale factor may have changed.
        return size.getWidth() != -1 && size.getHeight() != -1 && !(style instanceof ImageNodeStyle);
    }

}
