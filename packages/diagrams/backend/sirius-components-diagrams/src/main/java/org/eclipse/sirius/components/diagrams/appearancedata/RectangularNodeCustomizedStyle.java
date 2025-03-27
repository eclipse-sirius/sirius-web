/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.appearancedata;

import java.text.MessageFormat;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;

/**
 * The rectangular node style with nullable fields to store customizations.
 *
 * @author nvannier
 */
@Immutable
public final class RectangularNodeCustomizedStyle implements INodeCustomizedStyle {

    private String background;

    private String borderColor;

    private Integer borderSize;

    private Integer borderRadius;

    private LineStyle borderStyle;

    private RectangularNodeCustomizedStyle() {
        // Prevent instantiation
    }

    public static Builder newRectangularNodeCustomizedStyle() {
        return new Builder();
    }

    public static Builder newRectangularNodeCustomizedStyle(RectangularNodeCustomizedStyle source) {
        return new Builder(source);
    }


    public String getBackground() {
        return this.background;
    }

    public String getBorderColor() {
        return this.borderColor;
    }

    public Integer getBorderSize() {
        return this.borderSize;
    }

    public Integer getBorderRadius() {
        return this.borderRadius;
    }

    public LineStyle getBorderStyle() {
        return this.borderStyle;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'color: {1}, border: '{' background: {2}, size: {3}, radius: {4}, style: {5} '}''}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.background, this.borderColor, this.borderSize, this.borderRadius, this.borderStyle);
    }

    @Override
    public INodeStyle mergeInto(INodeStyle style) {
        if (style instanceof RectangularNodeStyle rectangularNodeStyle) {
            return RectangularNodeStyle.newRectangularNodeStyle()
                    .background(Optional.ofNullable(background).orElse(rectangularNodeStyle.getBackground()))
                    .borderColor(Optional.ofNullable(borderColor).orElse(rectangularNodeStyle.getBorderColor()))
                    .borderSize(Optional.ofNullable(borderSize).orElse(rectangularNodeStyle.getBorderSize()))
                    .borderRadius(Optional.ofNullable(borderRadius).orElse(rectangularNodeStyle.getBorderRadius()))
                    .borderStyle(Optional.ofNullable(borderStyle).orElse(rectangularNodeStyle.getBorderStyle()))
                    .build();
        } else {
            return style;
        }
    }

    /**
     * Builder for a rectangular node customized style.
     *
     * @author nvannier
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String background;

        private String borderColor;

        private Integer borderSize;

        private Integer borderRadius;

        private LineStyle borderStyle;

        private Builder() {
            // Prevent instantiation
        }

        private Builder(RectangularNodeCustomizedStyle source) {
            this.background = source.getBackground();
            this.borderColor = source.getBorderColor();
            this.borderSize = source.getBorderSize();
            this.borderRadius = source.getBorderRadius();
            this.borderStyle = source.getBorderStyle();
        }

        public Builder background(String background) {
            this.background = background;
            return this;
        }

        public Builder borderColor(String borderColor) {
            this.borderColor = borderColor;
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
            this.borderStyle = borderStyle;
            return this;
        }

        public RectangularNodeCustomizedStyle build() {
            RectangularNodeCustomizedStyle nodeCustomizedStyle = new RectangularNodeCustomizedStyle();
            nodeCustomizedStyle.background = this.background;
            nodeCustomizedStyle.borderColor = this.borderColor;
            nodeCustomizedStyle.borderSize = this.borderSize;
            nodeCustomizedStyle.borderRadius = this.borderRadius;
            nodeCustomizedStyle.borderStyle = this.borderStyle;
            return nodeCustomizedStyle;
        }
    }
}
