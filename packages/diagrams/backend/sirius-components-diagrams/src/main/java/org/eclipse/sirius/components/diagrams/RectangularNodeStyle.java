/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
 * The rectangular node style.
 *
 * @author hmarchadour
 */
@Immutable
public final class RectangularNodeStyle implements INodeStyle {
    private String color;

    private String borderColor;

    private int borderSize;

    private int borderRadius;

    private LineStyle borderStyle;

    private boolean withHeader;

    private RectangularNodeStyle() {
        // Prevent instantiation
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

    public int getBorderRadius() {
        return this.borderRadius;
    }

    public LineStyle getBorderStyle() {
        return this.borderStyle;
    }

    public boolean isWithHeader() {
        return this.withHeader;
    }

    public static Builder newRectangularNodeStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'color: {1}, border: '{' color: {2}, size: {3}, radius: {4}, style: {5} '}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.color, this.borderColor, this.borderSize, this.borderRadius, this.borderStyle);
    }

    /**
     * The builder used to create the rectangular node style.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String color;

        private String borderColor;

        private int borderSize;

        private int borderRadius;

        private LineStyle borderStyle;

        private boolean withHeader;

        private Builder() {
            // Prevent instantiation
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

        public Builder withHeader(boolean withHeader) {
            this.withHeader = Objects.requireNonNull(withHeader);
            return this;
        }

        public RectangularNodeStyle build() {
            RectangularNodeStyle nodeStyleDescription = new RectangularNodeStyle();
            nodeStyleDescription.color = Objects.requireNonNull(this.color);
            nodeStyleDescription.borderColor = Objects.requireNonNull(this.borderColor);
            nodeStyleDescription.borderSize = this.borderSize;
            nodeStyleDescription.borderRadius = this.borderRadius;
            nodeStyleDescription.borderStyle = Objects.requireNonNull(this.borderStyle);
            nodeStyleDescription.withHeader = Objects.requireNonNull(this.withHeader);
            return nodeStyleDescription;
        }
    }
}
