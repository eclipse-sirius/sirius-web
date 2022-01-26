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

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.ListItemNodeStyle;
import org.eclipse.sirius.components.diagrams.ListNodeStyle;
import org.eclipse.sirius.components.diagrams.Node;
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

    public StringBuilder export(Node node) {
        StringBuilder nodeExport = new StringBuilder();
        INodeStyle style = node.getStyle();

        if (style instanceof ImageNodeStyle) {
            nodeExport.append(this.exportImage(node, (ImageNodeStyle) style));
        } else if (style instanceof ListNodeStyle) {
            nodeExport.append(this.exportList(node, (ListNodeStyle) style));
        } else if (style instanceof ListItemNodeStyle) {
            nodeExport.append(this.exportListItem(node, (ListItemNodeStyle) style));
        } else if (style instanceof RectangularNodeStyle) {
            nodeExport.append(this.exportRectangle(node, (RectangularNodeStyle) style));
        }

        return nodeExport;
    }

    private StringBuilder exportChildren(Node node) {
        StringBuilder childrenExport = new StringBuilder();
        // @formatter:off
        Stream.concat(node.getBorderNodes().stream(),
                      node.getChildNodes().stream())
            .forEach(elt -> childrenExport.append(this.export(elt)));
        // @formatter:on
        return childrenExport;
    }

    private StringBuilder exportImage(Node node, ImageNodeStyle style) {
        StringBuilder imageExport = new StringBuilder();
        Size size = node.getSize();
        Label label = node.getLabel();

        imageExport.append(this.elementExport.exportGNodeElement(node));
        imageExport.append(this.elementExport.exportImageElement(style.getImageURL(), 0, 0, Optional.of(size)));
        imageExport.append(this.elementExport.exportLabel(label));
        imageExport.append(this.exportChildren(node));

        return imageExport.append("</g>"); //$NON-NLS-1$
    }

    private StringBuilder exportList(Node node, ListNodeStyle style) {
        StringBuilder listExport = new StringBuilder();

        listExport.append(this.elementExport.exportGNodeElement(node));
        listExport.append(this.elementExport.exportRectangleElement(node.getSize(), Optional.of(style.getBorderRadius()), Optional.of(style.getColor()), Optional.of(style.getBorderColor()),
                Optional.of(style.getBorderSize()), Optional.of(style.getBorderStyle())));
        listExport.append(this.elementExport.exportLabel(node.getLabel()));
        listExport.append(this.exportHeaderSeparator(node, node.getLabel(), style));
        listExport.append(this.exportChildren(node));

        return listExport.append("</g>"); //$NON-NLS-1$
    }

    private StringBuilder exportHeaderSeparator(Node node, Label label, ListNodeStyle nodeStyle) {
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

    private StringBuilder exportListItem(Node node, ListItemNodeStyle style) {
        StringBuilder rectangleExport = new StringBuilder();

        rectangleExport.append(this.elementExport.exportGNodeElement(node));
        rectangleExport
                .append(this.elementExport.exportRectangleElement(node.getSize(), Optional.empty(), Optional.of(style.getBackgroundColor()), Optional.empty(), Optional.empty(), Optional.empty()));
        rectangleExport.append(this.elementExport.exportLabel(node.getLabel()));
        rectangleExport.append(this.exportChildren(node));

        return rectangleExport.append("</g>"); //$NON-NLS-1$
    }

    private StringBuilder exportRectangle(Node node, RectangularNodeStyle style) {
        StringBuilder rectangleExport = new StringBuilder();

        rectangleExport.append(this.elementExport.exportGNodeElement(node));
        rectangleExport.append(this.elementExport.exportRectangleElement(node.getSize(), Optional.of(style.getBorderRadius()), Optional.of(style.getColor()), Optional.of(style.getBorderColor()),
                Optional.of(style.getBorderSize()), Optional.of(style.getBorderStyle())));
        rectangleExport.append(this.elementExport.exportLabel(node.getLabel()));
        rectangleExport.append(this.exportChildren(node));

        return rectangleExport.append("</g>"); //$NON-NLS-1$
    }
}
