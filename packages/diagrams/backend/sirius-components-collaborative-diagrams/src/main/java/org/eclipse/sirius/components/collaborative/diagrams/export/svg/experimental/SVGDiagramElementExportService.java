/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.export.svg.experimental;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.export.api.IImageRegistry;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * Used to export diagram element parts to SVG.
 *
 * @author gcoutable
 */
public class SVGDiagramElementExportService {

    private final IImageRegistry imageRegistry;

    public SVGDiagramElementExportService(IImageRegistry imageRegistry) {
        this.imageRegistry = Objects.requireNonNull(imageRegistry);
    }

    public String exportLabelAsText(Label label, float opacity, DiagramLayoutData diagramLayoutData) {
        LabelLayoutData labelLayoutData = diagramLayoutData.labelLayoutData().get(label.getId());
        Position position = labelLayoutData.position();
        Position alignment = labelLayoutData.alignment();
        LabelStyle style = label.getStyle();

        return """
                <g transform="translate(#positionX, #positionY) translate(#alignmentX, #alignmentY)" style="opacity:#opacity;">
                  #imageElement#textElement
                </g>
                """
                .replace("#positionX", Double.toString(position.x()))
                .replace("#positionY", Double.toString(position.y()))
                .replace("#alignmentX", Double.toString(alignment.x()))
                .replace("#alignmentY", Double.toString(alignment.y()))
                .replace("#positionX", Float.toString(opacity))
                .replace("#imageElement", this.exportImageElement(style.getIconURL(), -20, -12, Optional.empty(), 1))
                .replace("#textElement", this.exportTextElement(label.getText(), label.getType(), style))
                ;
    }

    public String exportImageElement(String imageURL, int x, int y, Optional<Size> size, float nodeOpacity) {
        UUID symbolId = this.imageRegistry.registerImage(imageURL);
        if (symbolId == null) {
            return "";
        }

        return """
                <use xlink:href="##symbolId" x="#abscissa" y="#ordinal" #sizeParameters style="opacity:#opacity;/>"
                """
                .replace("#symbolId", symbolId.toString())
                .replace("#abscissa", Integer.toString(x))
                .replace("#ordinal", Integer.toString(y))
                .replace("#sizeParameters", this.exportSizeParameters(size))
                .replace("#opacity", Float.toString(nodeOpacity))
                ;
    }

    private String exportTextElement(String text, String type, LabelStyle labelStyle) {

        return null;
    }

    private String exportSizeParameters(Optional<Size> optionalSize) {
        if (optionalSize.isEmpty()) {
            return "";
        }
        Size size = optionalSize.get();
        return """
                width="#width" height="#height"
                """
                .replace("#width", Double.toString(size.width()))
                .replace("#height", Double.toString(size.height()));
    }
}
