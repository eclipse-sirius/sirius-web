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
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.export.api.IImageRegistry;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.springframework.stereotype.Service;

/**
 * Contains methods used when exporting diagram elements.
 *
 * @author rpage
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramElementExportService {
    private final IImageRegistry imageRegistry;

    public DiagramElementExportService(IImageRegistry imageRegistry) {
        this.imageRegistry = Objects.requireNonNull(imageRegistry);
    }

    public StringBuilder exportLabel(Label label) {
        StringBuilder labelExport = new StringBuilder();
        Position position = label.getPosition();
        Position alignment = label.getAlignment();
        LabelStyle style = label.getStyle();

        labelExport.append("<g "); //$NON-NLS-1$
        labelExport.append("transform=\""); //$NON-NLS-1$
        labelExport.append("translate(" + position.getX() + ", " + position.getY() + ") "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        labelExport.append("translate(" + alignment.getX() + ", " + alignment.getY() + ")\""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        labelExport.append(">"); //$NON-NLS-1$

        if (!(style.getIconURL().isEmpty())) {
            labelExport.append(this.exportImageElement(style.getIconURL(), -20, -12, Optional.empty()));
        }

        labelExport.append(this.exportTextElement(label.getText(), label.getType(), style));

        return labelExport.append("</g>"); //$NON-NLS-1$
    }

    public StringBuilder exportImageElement(String imageURL, int x, int y, Optional<Size> size) {
        StringBuilder imageExport = new StringBuilder();
        UUID symbolId = this.imageRegistry.registerImage(imageURL);
        if (symbolId != null) {
            imageExport.append("<use "); //$NON-NLS-1$
            imageExport.append("xlink:href=\"#" + symbolId + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
            imageExport.append("x=\"" + x + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
            imageExport.append("y=\"" + y + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
            size.ifPresent(it -> imageExport.append(this.addSizeParam(it)));
            imageExport.append("/>"); //$NON-NLS-1$
        }
        return imageExport;
    }

    /**
     * Export the g element containing the position of a diagram element.
     *
     * @param node
     *            The diagram element
     * @return The g element containing its position
     */
    public StringBuilder exportGNodeElement(Node node) {
        StringBuilder gExport = new StringBuilder();

        gExport.append("<g "); //$NON-NLS-1$
        gExport.append("transform=\"translate(" + node.getPosition().getX() + ", " + node.getPosition().getY() + ")\" "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        return gExport.append(">"); //$NON-NLS-1$
    }

    public StringBuilder exportRectangleElement(Size size, Optional<Integer> borderRadius, Optional<String> color, Optional<String> borderColor, Optional<Integer> borderSize,
            Optional<LineStyle> borderStyle) {
        return this.exportRectangleElement(size, Position.at(0, 0), borderRadius, color, borderColor, borderSize, borderStyle);
    }

    public StringBuilder exportRectangleElement(Size size, Position position, Optional<Integer> borderRadius, Optional<String> color, Optional<String> borderColor, Optional<Integer> borderSize,
            Optional<LineStyle> borderStyle) {
        StringBuilder rectangle = new StringBuilder();

        rectangle.append("<rect "); //$NON-NLS-1$
        rectangle.append("x=\"" + position.getX() + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
        rectangle.append("y=\"" + position.getY() + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
        borderRadius.ifPresent(radius -> rectangle.append("rx=\"" + radius + "\" ")); //$NON-NLS-1$ //$NON-NLS-2$
        rectangle.append(this.addSizeParam(size));
        rectangle.append(this.exportRectangleStyleParam(color, borderColor, borderSize, borderStyle));

        return rectangle.append("/> "); //$NON-NLS-1$
    }

    private StringBuilder addSizeParam(Size size) {
        StringBuilder sizeParam = new StringBuilder();
        sizeParam.append("width=\"" + size.getWidth() + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
        return sizeParam.append("height=\"" + size.getHeight() + "\" "); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private StringBuilder exportTextElement(String text, String type, LabelStyle labelStyle) {
        StringBuilder textExport = new StringBuilder();

        textExport.append("<text "); //$NON-NLS-1$
        textExport.append("style=\""); //$NON-NLS-1$
        textExport.append("fill: " + labelStyle.getColor() + "; "); //$NON-NLS-1$ //$NON-NLS-2$
        textExport.append(this.exportFont(labelStyle));
        textExport.append("\">"); //$NON-NLS-1$

        String[] lines = text.split("\\n", -1); //$NON-NLS-1$
        if (lines.length == 1) {
            textExport.append(text);
        } else {
            textExport.append("<tspan x=\"0\">" + lines[0] + "</tspan>"); //$NON-NLS-1$//$NON-NLS-2$
            double fontSize = labelStyle.getFontSize();
            for (int i = 1; i < lines.length; i++) {
                if (lines[i].isEmpty()) {
                    // avoid tspan to be ignored if there is only a line return
                    lines[i] = " "; //$NON-NLS-1$
                }
                textExport.append("<tspan x=\"0\" dy=\"" + fontSize + "\">" + lines[i] + "</tspan>"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
            }
        }

        return textExport.append("</text>"); //$NON-NLS-1$
    }

    private StringBuilder exportFont(LabelStyle labelStyle) {
        StringBuilder textExport = new StringBuilder();

        textExport.append("font-size: " + labelStyle.getFontSize() + "px; "); //$NON-NLS-1$ //$NON-NLS-2$
        textExport.append("font-family: Arial, Helvetica, sans-serif; "); //$NON-NLS-1$

        textExport.append("font-weight: "); //$NON-NLS-1$
        if (labelStyle.isBold()) {
            textExport.append("bold"); //$NON-NLS-1$
        } else {
            textExport.append("normal"); //$NON-NLS-1$
        }
        textExport.append("; "); //$NON-NLS-1$

        textExport.append("font-style: "); //$NON-NLS-1$
        if (labelStyle.isItalic()) {
            textExport.append("italic"); //$NON-NLS-1$
        } else {
            textExport.append("normal"); //$NON-NLS-1$
        }
        textExport.append("; "); //$NON-NLS-1$

        textExport.append("text-decoration: "); //$NON-NLS-1$
        if (labelStyle.isUnderline() || labelStyle.isStrikeThrough()) {
            if (labelStyle.isUnderline()) {
                textExport.append("underline "); //$NON-NLS-1$
            }
            if (labelStyle.isStrikeThrough()) {
                textExport.append("line-through"); //$NON-NLS-1$
            }
        } else {
            textExport.append("none"); //$NON-NLS-1$
        }
        return textExport.append(";"); //$NON-NLS-1$
    }

    private StringBuilder exportRectangleStyleParam(Optional<String> color, Optional<String> borderColor, Optional<Integer> borderSize, Optional<LineStyle> borderStyle) {
        StringBuilder styleExport = new StringBuilder();

        styleExport.append("style=\""); //$NON-NLS-1$
        if (color.isPresent()) {
            styleExport.append("fill: " + color.get() + "; "); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            styleExport.append("fill-opacity: " + 0 + "; "); //$NON-NLS-1$ //$NON-NLS-2$
        }
        borderColor.ifPresent(it -> styleExport.append("stroke: " + it + "; ")); //$NON-NLS-1$ //$NON-NLS-2$
        borderSize.ifPresent(it -> styleExport.append("stroke-width: " + it + "px; ")); //$NON-NLS-1$ //$NON-NLS-2$
        borderStyle.ifPresent(style -> styleExport.append(this.exportBorderStyle(style)));

        return styleExport.append("\""); //$NON-NLS-1$
    }

    private StringBuilder exportBorderStyle(LineStyle borderStyle) {
        StringBuilder style = new StringBuilder();
        switch (borderStyle.toString()) {
        case "Dash": //$NON-NLS-1$
            style.append(" stroke-dasharray: 4, 4;"); //$NON-NLS-1$
            break;
        case "Dot": //$NON-NLS-1$
            style.append(" stroke-dasharray: 2, 2;"); //$NON-NLS-1$
            break;
        case "Dash_Dot": //$NON-NLS-1$
            style.append(" stroke-dasharray: 2, 4, 2;"); //$NON-NLS-1$
            break;
        default:
            break;
        }
        return style;
    }
}
