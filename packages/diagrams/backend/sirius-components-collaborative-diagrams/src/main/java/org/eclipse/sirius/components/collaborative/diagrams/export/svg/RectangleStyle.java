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

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * Class style for a SVG rectangle.
 *
 * @author tgiraudet
 */
@Immutable
public final class RectangleStyle {

    private int borderRadius;

    private String color;

    private String borderColor;

    private int borderSize;

    private LineStyle borderStyle;

    private float opacity;

    private RectangleStyle() {
        // private constructor
    }

    public int getBorderRadius() {
        return this.borderRadius;
    }

    public String getColor() {
        return this.color;
    }

    public String getBorderColor() {
        return this.borderColor;
    }

    public int getBorderSize() {
        return this.borderSize;
    }

    public LineStyle getBorderStyle() {
        return this.borderStyle;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public static Builder newRectangleStyle() {
        return new Builder();
    }

    /**
     * The builder used to create a rectangle style.
     *
     * @author tgiraudet
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private int borderRadius;

        private String color;

        private String borderColor;

        private int borderSize;

        private LineStyle borderStyle;

        private float opacity;

        private Builder() {
            // private constructor
        }

        public Builder borderRadius(int borderRadius) {
            this.borderRadius = Objects.requireNonNull(borderRadius);
            return this;
        }

        public Builder color(String color) {
            this.color = Objects.requireNonNull(color);
            return this;
        }

        public Builder borderColor(String borderColor) {
            this.borderColor = Objects.requireNonNull(borderColor);
            return this;
        }

        public Builder borderSize(int borderSize) {
            this.borderSize = Objects.requireNonNull(borderSize);
            return this;
        }

        public Builder borderStyle(LineStyle borderStyle) {
            this.borderStyle = Objects.requireNonNull(borderStyle);
            return this;
        }

        public Builder opacity(float opacity) {
            this.opacity = opacity;
            return this;
        }

        public RectangleStyle build() {
            RectangleStyle style = new RectangleStyle();

            style.borderRadius = Objects.requireNonNull(this.borderRadius);
            style.color = Objects.requireNonNull(this.color);
            style.borderColor = Objects.requireNonNull(this.borderColor);
            style.borderSize = Objects.requireNonNull(this.borderSize);
            style.borderStyle = Objects.requireNonNull(this.borderStyle);
            style.opacity = this.opacity;

            return style;
        }
    }
}
