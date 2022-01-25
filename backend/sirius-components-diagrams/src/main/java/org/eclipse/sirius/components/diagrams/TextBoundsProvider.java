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
package org.eclipse.sirius.components.diagrams;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Provides the TextBounds of a given text applied to a LabelStyle.
 *
 * @author wpiers
 */
public class TextBoundsProvider {

    /**
     * Font used in backend & frontend to compute and draw diagram labels.
     */
    private static final String DEFAULT_LABEL_FONT_NAME = "Arial"; //$NON-NLS-1$

    private static final String FALLBACK_LABEL_FONT_NAME = "Liberation Sans"; //$NON-NLS-1$

    private static final AffineTransform AFFINE_TRANSFORM = new AffineTransform();

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(AFFINE_TRANSFORM, true, true);

    private static final int SPACE_FOR_ICON = 20;

    private static String fontName;

    /**
     * Computes the text bounds for a label with the given text.
     *
     * @param labelStyle
     *            the label style
     * @param text
     *            the text
     * @return the text bounds
     */
    public TextBounds computeBounds(LabelStyle labelStyle, String text) {
        int fontStyle = Font.PLAIN;
        if (labelStyle.isBold()) {
            fontStyle = fontStyle | Font.BOLD;
        }
        if (labelStyle.isItalic()) {
            fontStyle = fontStyle | Font.ITALIC;
        }
        Font font = new Font(this.getFontName(), fontStyle, labelStyle.getFontSize());
        Rectangle2D stringBounds = font.getStringBounds(text, FONT_RENDER_CONTEXT);
        double width = stringBounds.getWidth();
        double height = stringBounds.getHeight();

        double iconWidth = 0;
        double iconHeight = 0;
        if (!labelStyle.getIconURL().isEmpty()) {
            iconWidth = SPACE_FOR_ICON;
            if (height < iconWidth / 2) {
                iconHeight = iconWidth / 2;
            }
        }

        Size size = Size.of(width + iconWidth, height + iconHeight);

        // Sprotty needs the inverse of the x and y for the alignment, so it's "0 - x" and "0 - y" on purpose
        Position alignment = Position.at(0 - stringBounds.getX() + iconWidth, 0 - stringBounds.getY());

        return new TextBounds(size, alignment);
    }

    private String getFontName() {
        if (fontName == null) {
            if (this.isDefaultFontAvailable()) {
                fontName = DEFAULT_LABEL_FONT_NAME;
            } else {
                fontName = FALLBACK_LABEL_FONT_NAME;
            }
        }
        return fontName;
    }

    private boolean isDefaultFontAvailable() {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (String currentFont : e.getAvailableFontFamilyNames()) {
            if (DEFAULT_LABEL_FONT_NAME.equals(currentFont)) {
                return true;
            }
        }
        return false;
    }
}
