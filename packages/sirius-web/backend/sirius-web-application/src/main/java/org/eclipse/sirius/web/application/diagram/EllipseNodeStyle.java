/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.diagram;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * The ellipse node style.
 *
 * @author frouene
 */
@Immutable
public final class EllipseNodeStyle implements INodeStyle {

    private String color;

    private String borderColor;

    private int borderSize;

    private LineStyle borderStyle;

    private EllipseNodeStyle() {
        // Prevent instantiation
    }

    public static Builder newEllipseNodeStyle() {
        return new Builder();
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

    @Override
    public String toString() {
        String pattern = "{0} '{'color: {1}, border: '{' color: {2}, size: {3}, style: {4} '}''}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.color, this.borderColor, this.borderSize, this.borderStyle);
    }

    /**
     * The builder used to create the ellipse node style.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String color;

        private String borderColor;

        private int borderSize;

        private LineStyle borderStyle;

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

        public Builder borderStyle(LineStyle borderStyle) {
            this.borderStyle = Objects.requireNonNull(borderStyle);
            return this;
        }


        public EllipseNodeStyle build() {
            EllipseNodeStyle nodeStyleDescription = new EllipseNodeStyle();
            nodeStyleDescription.color = Objects.requireNonNull(this.color);
            nodeStyleDescription.borderColor = Objects.requireNonNull(this.borderColor);
            nodeStyleDescription.borderSize = this.borderSize;
            nodeStyleDescription.borderStyle = Objects.requireNonNull(this.borderStyle);
            return nodeStyleDescription;
        }
    }
}
