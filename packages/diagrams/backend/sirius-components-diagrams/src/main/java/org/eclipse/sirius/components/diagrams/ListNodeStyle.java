/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
 * The list container node style.
 *
 * @author gcoutable
 */
@Immutable
public final class ListNodeStyle implements INodeStyle {

    private String color;

    private String borderColor;

    private int borderSize;

    private int borderRadius;

    private LineStyle borderStyle;

    private ListNodeStyle() {
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

    public static Builder newListNodeStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'color: {1}, borderColor: {2}, borderSize: {3}, borderRadius: {4}, borderStyle: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.color, this.borderColor, this.borderSize, this.borderRadius, this.borderStyle);
    }

    /**
     * The builder used to create the list container node style.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String color;

        private String borderColor;

        private int borderSize;

        private int borderRadius;

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

        public Builder borderRadius(int borderRadius) {
            this.borderRadius = borderRadius;
            return this;
        }

        public Builder borderStyle(LineStyle borderStyle) {
            this.borderStyle = Objects.requireNonNull(borderStyle);
            return this;
        }

        public ListNodeStyle build() {
            ListNodeStyle nodeStyleDescription = new ListNodeStyle();
            nodeStyleDescription.color = Objects.requireNonNull(this.color);
            nodeStyleDescription.borderColor = Objects.requireNonNull(this.borderColor);
            nodeStyleDescription.borderSize = this.borderSize;
            nodeStyleDescription.borderRadius = this.borderRadius;
            nodeStyleDescription.borderStyle = Objects.requireNonNull(this.borderStyle);
            return nodeStyleDescription;
        }

    }

}
