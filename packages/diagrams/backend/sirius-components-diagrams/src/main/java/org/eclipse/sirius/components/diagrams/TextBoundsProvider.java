/*******************************************************************************
 * Copyright (c) 2021, 2023 THALES GLOBAL SERVICES.
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
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

/**
 * Provides the TextBounds of a given text applied to a LabelStyle.
 *
 * @author wpiers
 */
public class TextBoundsProvider {

    /**
     * Font used in backend & frontend to compute and draw diagram labels.
     */
    private static final String DEFAULT_LABEL_FONT_NAME = "Arial";

    private static final String FALLBACK_LABEL_FONT_NAME = "Liberation Sans";

    private static final AffineTransform AFFINE_TRANSFORM = new AffineTransform();

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(AFFINE_TRANSFORM, true, true);

    private static final int SPACE_FOR_ICON = 20;

    private static String fontName;

    static {
        // Frontend does not render the font the same way than the backend (it appears bigger on the frontend).
        // Furthermore, text overflow break rules are not the same between frontend and backend.
        // So, apply upscales to fill these differences.
        AFFINE_TRANSFORM.scale(1.05, 1.50);
    }

    /**
     * Computes the text bounds for a label with the given text.<br>
     * The text bounds take into account the line return contained in text.
     *
     * @param labelStyle
     *            the label style
     * @param text
     *            the text
     * @param maxWidth
     *            the maximum width to take into account (the text will be split after this value)
     * @return the text bounds
     */
    public TextBounds computeBounds(LabelStyle labelStyle, String text) {
        Rectangle2D labelBounds = null;

        Font font = this.getFont(labelStyle);
        String[] lines = text.split("\\n", -1);
        if (lines.length == 0) {
            labelBounds = font.getStringBounds("", FONT_RENDER_CONTEXT);
        } else {
            labelBounds = font.getStringBounds(lines[0], FONT_RENDER_CONTEXT);

            if (lines.length > 1) {
                for (int i = 1; i < lines.length; i++) {
                    String line = lines[i];

                    Rectangle2D lineBounds = font.getStringBounds(line, FONT_RENDER_CONTEXT);
                    // shift the rectangle under the previous line
                    lineBounds.setFrame(lineBounds.getX(), lineBounds.getY() + labelBounds.getHeight(), lineBounds.getWidth(), lineBounds.getHeight());

                    labelBounds = labelBounds.createUnion(lineBounds);
                }
            }
        }

        boolean isIcon = !labelStyle.getIconURL().isEmpty();
        Position alignment = this.getAlignment(labelBounds, isIcon, true);
        Size size = this.getSize(labelBounds, isIcon);

        return new TextBounds(size, alignment);
    }

    public TextBounds computeAutoWrapBounds(LabelStyle labelStyle, String text, double maxWidth) {
        Rectangle2D labelBounds = new Rectangle();
        if (text == null || text.isEmpty()) {
            labelBounds.setFrame(0, 0, 0, 0);
        } else {
            Font font = this.getFont(labelStyle).deriveFont(AFFINE_TRANSFORM);
            AttributedString attributedString = new AttributedString(text);
            attributedString.addAttribute(TextAttribute.FONT, font);
            AttributedCharacterIterator paragraph = attributedString.getIterator();
            int paragraphStart = paragraph.getBeginIndex();
            int paragraphEnd = paragraph.getEndIndex();
            LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, FONT_RENDER_CONTEXT);

            // Set break width to width of maxWidth.
            float breakWidth;
            // maxWidth may be negative in case of a diagram/node creation. In this case we set a break width value
            // to a very big value.
            if (maxWidth < 0) {
                breakWidth = 10000;
            } else {
                breakWidth = (float) maxWidth;
            }
            float width = 0;
            float height = 0;
            // Set position to the index of the first character in the paragraph.
            lineMeasurer.setPosition(paragraphStart);

            while (lineMeasurer.getPosition() < paragraphEnd) {
                TextLayout layout = lineMeasurer.nextLayout(breakWidth);
                // Move y-coordinate by the ascent of the layout.
                height += layout.getAscent();
                // Move y-coordinate in preparation for next layout.
                height += layout.getDescent() + layout.getLeading();
                Rectangle2D layoutBounds = layout.getBounds();
                width = (float) Math.max(width, layoutBounds.getWidth());
            }
            labelBounds.setFrame(0, 0, width, height);
        }

        boolean isIcon = !labelStyle.getIconURL().isEmpty();
        Position alignment = this.getAlignment(labelBounds, isIcon, false);
        Size size = this.getSize(labelBounds, isIcon);

        return new TextBounds(size, alignment);
    }

    private Position getAlignment(Rectangle2D labelBounds, boolean isIcon, boolean applySprottyCorrection) {
        Position alignment = null;
        if (labelBounds != null && applySprottyCorrection) {
            double iconWidth = 0;
            if (isIcon) {
                iconWidth = SPACE_FOR_ICON;
            }
            // Sprotty needs the inverse of the x and y for the alignment, so it's "0 - x" and "0 - y" on purpose
            alignment = Position.at(0 - labelBounds.getX() + iconWidth, 0 - labelBounds.getY());
        } else {
            alignment = Position.at(0, 0);
        }
        return alignment;
    }

    private Size getSize(Rectangle2D labelBounds, boolean isIcon) {
        double height = labelBounds.getHeight();
        double width = labelBounds.getWidth();
        double iconWidth = 0;
        double iconHeight = 0;
        if (isIcon) {
            iconWidth = SPACE_FOR_ICON;
            if (height < iconWidth / 2) {
                iconHeight = iconWidth / 2;
            }
        }
        return Size.of(width + iconWidth, height + iconHeight);
    }

    private Font getFont(LabelStyle labelStyle) {
        int fontStyle = Font.PLAIN;
        if (labelStyle.isBold()) {
            fontStyle = fontStyle | Font.BOLD;
        }
        if (labelStyle.isItalic()) {
            fontStyle = fontStyle | Font.ITALIC;
        }
        return new Font(this.getFontName(), fontStyle, labelStyle.getFontSize());
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
