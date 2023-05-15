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
package org.eclipse.sirius.components.diagrams.layout.experimental;

import java.util.Optional;
import java.util.OptionalDouble;

import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * Handles the layout of an InsideLabelBox.
 *
 * @author pcdavid
 */
public class InsideLabelLayoutEngine {

    public InsideLabelBox layout(Label label, InsideLabelLayoutConfiguration rules, OptionalDouble maxBoxWidth) {
        Optional<Size> optionalIconSize = rules.getIconSize();
        OptionalDouble maxTextWidth = maxBoxWidth;
        if (maxTextWidth.isPresent()) {
            maxTextWidth = OptionalDouble.of(maxBoxWidth.getAsDouble() - rules.getPadding().width() - rules.getBorder().width());
            if (optionalIconSize.isPresent()) {
                maxTextWidth = OptionalDouble.of(maxTextWidth.getAsDouble() - rules.getGap() - optionalIconSize.get().width());
            }
        }
        TextBox textBox = new TextLayoutEngine().layout(label, maxTextWidth);
        Size textSize = textBox.size();

        // The full content area contains the icon and text
        double contentAreaWidth = textSize.width();
        double contentAreaHeight = textSize.height();
        if (optionalIconSize.isPresent()) {
            contentAreaWidth += optionalIconSize.get().width() + rules.getGap();
            contentAreaHeight = Math.max(contentAreaHeight, optionalIconSize.get().height());
        }
        // We now know the room needed for the actual content
        Rectangle contentArea = new Rectangle(0, 0, contentAreaWidth, contentAreaHeight);
        // The whole label needs more, to account for the border and padding
        Offsets internalOffsets = rules.getBorder().combine(rules.getPadding());
        Rectangle labelArea = contentArea.resize(contentArea.width() + internalOffsets.width(), contentArea.height() + internalOffsets.height());
        // Place the content area inside the whole label area according to the anchoring rules
        Rectangle availableArea = labelArea.shrink(internalOffsets);
        Anchor anchor = rules.getAnchor();
        Position contentAreaPosition = anchor.apply(availableArea, new Size(contentAreaWidth, contentAreaHeight));
        // Fine-tune the position of the icon and text inside the content area
        Optional<Rectangle> iconArea;
        Rectangle textArea;
        if (optionalIconSize.isPresent()) {
            Size iconSize = optionalIconSize.get();
            double textOffset = iconSize.width() + rules.getGap();
            if (iconSize.height() > textSize.height()) {
                // The icon is taller that the text, place the icon in the top-left and center the label vertically
                iconArea = Optional.of(new Rectangle(contentAreaPosition, iconSize));
                textArea = new Rectangle(contentAreaPosition.translate(textOffset, (contentAreaHeight - textSize.height()) / 2.0), textSize);
            } else {
                // The text is taller, center the icon vertically and align the label to the top
                iconArea = Optional.of(new Rectangle(contentAreaPosition.translate(0.0, (contentAreaHeight - iconSize.height()) / 2.0), iconSize));
                textArea = new Rectangle(contentAreaPosition.translate(textOffset, 0.0), textSize);
            }
        } else {
            iconArea = Optional.empty();
            textArea = new Rectangle(contentAreaPosition, new Size(contentAreaWidth, contentAreaHeight));
        }
        return new InsideLabelBox(labelArea, iconArea, textArea);
    }
}
