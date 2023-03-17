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
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

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

    private static final AffineTransform AFFINE_TRANSFORM_AUTO_WRAP = new AffineTransform();

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(AFFINE_TRANSFORM, true, true);

    private static final int SPACE_FOR_ICON = 20;

    private static String fontName;

    static {
        // Frontend does not render the font the same way than the backend (it appears bigger on the frontend).
        // So, apply ratio to fill this difference.
        AFFINE_TRANSFORM_AUTO_WRAP.scale(1.09, 1.09);
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

    public TextBounds computeAutoWrapBounds(LabelStyle labelStyle, String text, double containerMaxWidth) {
        if (text == null || text.isEmpty()) {
            return new TextBounds(Size.of(0, 0), Position.at(0, 0));
        }
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        Font font = this.getFont(labelStyle).deriveFont(AFFINE_TRANSFORM_AUTO_WRAP);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        float fontSize = font.getSize2D();
        float backendRatio = fontSize / 10;
        float lineHeight = fm.getHeight() + backendRatio;

        AtomicReference<Float> height = new AtomicReference<>();
        height.set(0f);
        float maxWidth = 0;
        if (containerMaxWidth < 0) {
            maxWidth = 1000;
        } else {
            maxWidth = (float) containerMaxWidth;
        }
        boolean isIcon = !labelStyle.getIconURL().isEmpty();
        if (isIcon) {
            maxWidth = Math.max(SPACE_FOR_ICON, maxWidth - SPACE_FOR_ICON);
        }
        final float breakWidth = maxWidth;

        Stream<String> lines = text.lines();
        lines.forEach(line -> {
            height.set(height.get() + lineHeight);
            int textWidth = fm.stringWidth(line);
            if (textWidth > breakWidth) {
                String[] words = line.split("((?=\\t| )|(?<=\\t| ))");
                String currentLine = "";
                for (String word : words) {
                    int lineWidth = fm.stringWidth(currentLine + word);
                    if (lineWidth >= breakWidth) {
                        currentLine = word;
                        height.set(height.get() + lineHeight);
                    } else {
                        currentLine += word;
                    }
                }
            }
        });

        g2d.dispose();

        Position alignment = Position.at(0, 0);
        Size size = Size.of(containerMaxWidth, height.get());

        return new TextBounds(size, alignment);
    }

    public long computeAutoWrapLines(LabelStyle labelStyle, String text, double containerMaxWidth) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        Font font = this.getFont(labelStyle).deriveFont(AFFINE_TRANSFORM_AUTO_WRAP);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        float fontSize = font.getSize2D();
        float backendRatio = fontSize / 10;
        float lineHeight = fm.getHeight() + backendRatio;

        AtomicReference<Float> height = new AtomicReference<>();
        height.set(0f);
        float maxWidth = 0;
        if (containerMaxWidth < 0) {
            maxWidth = 1000;
        } else {
            maxWidth = (float) containerMaxWidth;
        }
        boolean isIcon = !labelStyle.getIconURL().isEmpty();
        if (isIcon) {
            maxWidth = Math.max(SPACE_FOR_ICON, maxWidth - SPACE_FOR_ICON);
        }
        final float breakWidth = maxWidth;

        AtomicLong nbLines = new AtomicLong();
        Stream<String> lines = text.lines();
        lines.forEach(line -> {
            nbLines.incrementAndGet();
            height.set(height.get() + lineHeight);
            int textWidth = fm.stringWidth(line);
            if (textWidth > breakWidth) {
                String[] words = line.split("((?=\\t| )|(?<=\\t| ))");
                String currentLine = "";
                for (String word : words) {
                    int lineWidth = fm.stringWidth(currentLine + word);
                    if (lineWidth >= breakWidth) {
                        currentLine = word;
                        height.set(height.get() + lineHeight);
                        nbLines.incrementAndGet();
                    } else {
                        currentLine += word;
                    }
                }
            }
        });

        g2d.dispose();
        return nbLines.get();
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
