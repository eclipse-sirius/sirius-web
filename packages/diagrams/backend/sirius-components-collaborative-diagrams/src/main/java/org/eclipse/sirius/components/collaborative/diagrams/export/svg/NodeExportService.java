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
package org.eclipse.sirius.components.collaborative.diagrams.export.svg;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.springframework.stereotype.Service;

/**
 * Used to export a {@link Node} to SVG.
 *
 * @author rpage
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class NodeExportService {
    private final DiagramElementExportService elementExport;

    public NodeExportService(DiagramElementExportService elementExport) {
        this.elementExport = Objects.requireNonNull(elementExport);
    }

    public StringBuilder export(Node node, Map<String, NodeAndContainerId> id2NodeHierarchy) {
        StringBuilder nodeExport = new StringBuilder();
        INodeStyle style = node.getStyle();

        if (style instanceof ImageNodeStyle) {
            nodeExport.append(this.exportImage(node, (ImageNodeStyle) style, id2NodeHierarchy));
        } else if (style instanceof IconLabelNodeStyle) {
            nodeExport.append(this.exportIconLabel(node, (IconLabelNodeStyle) style, id2NodeHierarchy));
        } else if (style instanceof RectangularNodeStyle) {
            nodeExport.append(this.exportRectangle(node, (RectangularNodeStyle) style, id2NodeHierarchy));
        }

        return nodeExport;
    }

    private StringBuilder exportChildren(Node node, Map<String, NodeAndContainerId> id2NodeHierarchy) {
        String nodeId = node.getId();
        StringBuilder childrenExport = new StringBuilder();
        // @formatter:off
        Stream.concat(node.getBorderNodes().stream(),
                      node.getChildNodes().stream())
            .forEach(elt -> {
                id2NodeHierarchy.put(elt.getId(), new NodeAndContainerId(nodeId, elt));
                childrenExport.append(this.export(elt, id2NodeHierarchy));
            });
        // @formatter:on
        return childrenExport;
    }

    private StringBuilder exportImage(Node node, ImageNodeStyle style, Map<String, NodeAndContainerId> id2NodeHierarchy) {
        StringBuilder imageExport = new StringBuilder();
        Size size = node.getSize();
        Label label = node.getLabel();

        imageExport.append(this.elementExport.exportGNodeElement(node));
        // adapt the size and position to show the full width of the border
        Size imageSize = Size.of(node.getSize().getWidth() + style.getBorderSize(), node.getSize().getHeight() + style.getBorderSize());
        Position position = Position.at(-style.getBorderSize() / 2., -style.getBorderSize() / 2.);
        imageExport.append(this.elementExport.exportRectangleElement(imageSize, position, Optional.of(style.getBorderRadius()), Optional.empty(), Optional.of(style.getBorderColor()),
                Optional.of(style.getBorderSize()), Optional.of(style.getBorderStyle())));
        imageExport.append(this.elementExport.exportImageElement(style.getImageURL(), 0, 0, Optional.of(size)));
        imageExport.append(this.elementExport.exportLabel(label));
        imageExport.append(this.exportChildren(node, id2NodeHierarchy));

        return imageExport.append("</g>"); //$NON-NLS-1$
    }

    private StringBuilder exportHeaderSeparator(Node node, Label label, RectangularNodeStyle nodeStyle) {
        StringBuilder headerExport = new StringBuilder();
        // The label y position indicates the padding top, we suppose the same padding is applied to the bottom.
        double headerLabelPadding = label.getPosition().getY();
        double y = label.getSize().getHeight() + 2 * headerLabelPadding;

        headerExport.append("<line "); //$NON-NLS-1$
        headerExport.append("x1=\"0\" "); //$NON-NLS-1$
        headerExport.append("y1=\"" + y + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
        headerExport.append("x2=\"" + node.getSize().getWidth() + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
        headerExport.append("y2=\"" + y + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
        headerExport.append("style=\""); //$NON-NLS-1$
        headerExport.append("stroke: " + nodeStyle.getBorderColor() + "; "); //$NON-NLS-1$ //$NON-NLS-2$
        headerExport.append("stroke-width: " + nodeStyle.getBorderSize() + "; "); //$NON-NLS-1$ //$NON-NLS-2$
        headerExport.append("\" "); //$NON-NLS-1$

        return headerExport.append("/>"); //$NON-NLS-1$
    }

    private StringBuilder exportIconLabel(Node node, IconLabelNodeStyle style, Map<String, NodeAndContainerId> id2NodeHierarchy) {
        StringBuilder rectangleExport = new StringBuilder();

        rectangleExport.append(this.elementExport.exportGNodeElement(node));
        rectangleExport
                .append(this.elementExport.exportRectangleElement(node.getSize(), Optional.empty(), Optional.of(style.getBackgroundColor()), Optional.empty(), Optional.empty(), Optional.empty()));
        rectangleExport.append(this.elementExport.exportLabel(node.getLabel()));
        rectangleExport.append(this.exportChildren(node, id2NodeHierarchy));

        return rectangleExport.append("</g>"); //$NON-NLS-1$
    }

    private StringBuilder exportRectangle(Node node, RectangularNodeStyle style, Map<String, NodeAndContainerId> id2NodeHierarchy) {
        StringBuilder rectangleExport = new StringBuilder();

        rectangleExport.append(this.elementExport.exportGNodeElement(node));
        rectangleExport.append(this.elementExport.exportRectangleElement(node.getSize(), Optional.of(style.getBorderRadius()), Optional.of(style.getColor()), Optional.of(style.getBorderColor()),
                Optional.of(style.getBorderSize()), Optional.of(style.getBorderStyle())));
        rectangleExport.append(this.elementExport.exportLabel(node.getLabel()));
        if (style.isWithHeader()) {
            rectangleExport.append(this.exportHeaderSeparator(node, node.getLabel(), style));
        }
        rectangleExport.append(this.exportChildren(node, id2NodeHierarchy));

        return rectangleExport.append("</g>"); //$NON-NLS-1$
    }
}
