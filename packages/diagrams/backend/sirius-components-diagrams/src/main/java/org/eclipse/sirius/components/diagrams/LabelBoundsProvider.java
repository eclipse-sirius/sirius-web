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
package org.eclipse.sirius.components.diagrams;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Provides the TextBounds of a given text applied to a LabelStyle.
 * @author mcharfadi
 */
public class LabelBoundsProvider {
    private static final String DEFAULT_LABEL_FONT_NAME = "Arial";
    private static final String FALLBACK_LABEL_FONT_NAME = "Liberation Sans";
    private static final AffineTransform AFFINE_TRANSFORM = new AffineTransform();
    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(AFFINE_TRANSFORM, true, true);
    private static String fontName;

    public static Size getLabelBounds(LabelStyle labelStyle, String text) {
        Font font = getFont(labelStyle);

        String maxGlyphHeight = "Éç";
        Double maxFontHeight = font.getStringBounds(maxGlyphHeight, FONT_RENDER_CONTEXT).getHeight();
        Double totalHeight = maxFontHeight * text.lines().count();

        String longestLine = text.lines().max(Comparator.comparingInt(String::length)).get();
        Double maxWidthValue = font.getStringBounds(longestLine, FONT_RENDER_CONTEXT).getWidth();

        return Size.of(Math.round(maxWidthValue), Math.round(totalHeight));
    }

    private static int round(double d) {
        double dAbs = Math.abs(d);
        int i = (int) dAbs;
        if (d < 0) {
            return -(i + 1);
        }
        else {
            return i + 1;
        }
    }

    public static Size getLabelBoundsWrapped(LabelStyle labelStyle, String text, double containerMaxWidth) {
        Font font = getFont(labelStyle);
        String maxGlyphHeight = "Éç";
        Double maxFontHeight = font.getStringBounds(maxGlyphHeight, FONT_RENDER_CONTEXT).getHeight();

        float maxWidth = 0;
        if (containerMaxWidth < 0) {
            maxWidth = 1000;
        } else {
            maxWidth = (float) containerMaxWidth;
        }

        Stream<String> lines = text.lines();
        int nbTotalLignes = 0;
        for (String line : lines.toList()) {
            Double textWidth = font.getStringBounds(line, FONT_RENDER_CONTEXT).getWidth();
            Double nbLigne = textWidth / maxWidth;
            nbTotalLignes += round(nbLigne);
        }

        Double totalHeight = (maxFontHeight * nbTotalLignes) -  maxFontHeight;
        return Size.of(containerMaxWidth, Math.round(totalHeight));
    }

    private static Font getFont(LabelStyle labelStyle) {
        int fontStyle = Font.PLAIN;
        if (labelStyle.isBold()) {
            fontStyle = fontStyle | Font.BOLD;
        }
        if (labelStyle.isItalic()) {
            fontStyle = fontStyle | Font.ITALIC;
        }
        return new Font(getFontName(), fontStyle, labelStyle.getFontSize());
    }

    private static String getFontName() {
        if (fontName == null) {
            if (isDefaultFontAvailable()) {
                fontName = DEFAULT_LABEL_FONT_NAME;
            } else {
                fontName = FALLBACK_LABEL_FONT_NAME;
            }
        }
        return fontName;
    }

    private static boolean isDefaultFontAvailable() {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (String currentFont : e.getAvailableFontFamilyNames()) {
            if (DEFAULT_LABEL_FONT_NAME.equals(currentFont)) {
                return true;
            }
        }
        return false;
    }
}
