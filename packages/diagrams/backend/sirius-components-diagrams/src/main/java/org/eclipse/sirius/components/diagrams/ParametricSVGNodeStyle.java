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
package org.eclipse.sirius.components.diagrams;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The SVG node style.
 *
 * @author lfasani
 */
@Immutable
public final class ParametricSVGNodeStyle implements INodeStyle {
    private String svgURL;

    private String backgroundColor;

    private String borderColor;

    private int borderSize;

    private int borderRadius;

    private LineStyle borderStyle;

    private ParametricSVGNodeStyle() {
        // Prevent instantiation
    }

    public String getSvgURL() {
        return this.svgURL;
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public String getBorderColor() {
        return this.borderColor;
    }

    public int getBorderSize() {
        return this.borderSize;
    }

    public int getBorderRadius() {
        return this.borderSize;
    }

    public LineStyle getBorderStyle() {
        return this.borderStyle;
    }

    public static Builder newParametricSVGNodeStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'backgroundcolor: {1}', border: '{' size: {2}, radius: {3}, color: {4}, style: {5} '}', svg: {6}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.backgroundColor, this.borderSize, this.borderRadius, this.borderColor, this.borderStyle, this.svgURL);
    }

    /**
     * The builder used to create the svg node style description.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String svgURL;

        private String backgroundColor = "white"; //$NON-NLS-1$

        private String borderColor = "black"; //$NON-NLS-1$

        private int borderSize;

        private int borderRadius;

        private LineStyle borderStyle = LineStyle.Solid;

        private Builder() {
            // Prevent instantiation
        }

        public Builder svgURL(String svgURL) {
            this.svgURL = Objects.requireNonNull(svgURL);
            return this;
        }

        public Builder backgroundColor(String backgroundColor) {
            this.backgroundColor = Objects.requireNonNull(backgroundColor);
            return this;
        }

        public Builder borderColor(String borderColor) {
            this.borderColor = Objects.requireNonNull(borderColor);
            return this;
        }

        public Builder borderSize(int borderSize) {
            this.borderSize = borderSize;
            return this;
        }

        public Builder borderRadius(int borderRadius) {
            this.borderRadius = borderRadius;
            return this;
        }

        public Builder borderStyle(LineStyle borderStyle) {
            this.borderStyle = Objects.requireNonNull(borderStyle);
            return this;
        }

        public ParametricSVGNodeStyle build() {
            ParametricSVGNodeStyle style = new ParametricSVGNodeStyle();
            style.svgURL = Objects.requireNonNull(this.svgURL);
            style.backgroundColor = Objects.requireNonNull(this.backgroundColor);
            style.borderColor = Objects.requireNonNull(this.borderColor);
            style.borderSize = this.borderSize;
            style.borderRadius = this.borderRadius;
            style.borderStyle = Objects.requireNonNull(this.borderStyle);
            return style;
        }
    }
}
