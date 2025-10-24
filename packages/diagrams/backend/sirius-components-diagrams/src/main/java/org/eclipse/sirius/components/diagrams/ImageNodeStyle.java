/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
 * The image node style.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Immutable
public final class ImageNodeStyle implements INodeStyle {

    private String imageURL;

    private int scalingFactor;

    private String borderColor;

    private int borderSize;

    private int borderRadius;

    private LineStyle borderStyle;

    private boolean positionDependentRotation;

    private ILayoutStrategy childrenLayoutStrategy;

    private ImageNodeStyle() {
        // Prevent instantiation
    }

    public static ImageNodeStyle.Builder newImageNodeStyle(ImageNodeStyle sourceImageNodeStyle) {
        return new ImageNodeStyle.Builder(sourceImageNodeStyle);
    }

    public static Builder newImageNodeStyle() {
        return new Builder();
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public int getScalingFactor() {
        return this.scalingFactor;
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

    public boolean isPositionDependentRotation() {
        return this.positionDependentRotation;
    }

    @Override
    public ILayoutStrategy getChildrenLayoutStrategy() {
        return this.childrenLayoutStrategy;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'imageURL: {1}, border: '{' size: {2}, color: {3}, style: {4} '}''}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.imageURL, this.borderSize, this.borderColor, this.borderStyle);
    }

    /**
     * The builder used to create the image node style description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String imageURL;

        private int scalingFactor;

        private String borderColor = "black";

        private int borderSize;

        private int borderRadius;

        private LineStyle borderStyle = LineStyle.Solid;

        private boolean positionDependentRotation;

        private ILayoutStrategy childrenLayoutStrategy;

        private Builder() {
            // Prevent instantiation
        }

        private Builder(ImageNodeStyle sourceImageNodeStyle) {
            this.borderColor = sourceImageNodeStyle.getBorderColor();
            this.borderSize = sourceImageNodeStyle.getBorderSize();
            this.borderRadius = sourceImageNodeStyle.getBorderRadius();
            this.borderStyle = sourceImageNodeStyle.getBorderStyle();
            this.scalingFactor = sourceImageNodeStyle.getScalingFactor();
            this.imageURL = sourceImageNodeStyle.getImageURL();
            this.positionDependentRotation = sourceImageNodeStyle.isPositionDependentRotation();
            this.childrenLayoutStrategy = sourceImageNodeStyle.getChildrenLayoutStrategy();
        }

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder scalingFactor(int scalingFactor) {
            this.scalingFactor = scalingFactor;
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

        public Builder positionDependentRotation(boolean positionDependentRotation) {
            this.positionDependentRotation = positionDependentRotation;
            return this;
        }

        public Builder childrenLayoutStrategy(ILayoutStrategy childrenLayoutStrategy) {
            this.childrenLayoutStrategy = Objects.requireNonNull(childrenLayoutStrategy);
            return this;
        }

        public ImageNodeStyle build() {
            ImageNodeStyle style = new ImageNodeStyle();
            style.imageURL = Objects.requireNonNull(this.imageURL);
            style.scalingFactor = Objects.requireNonNull(this.scalingFactor);
            style.borderColor = Objects.requireNonNull(this.borderColor);
            style.borderSize = this.borderSize;
            style.borderRadius = this.borderRadius;
            style.borderStyle = Objects.requireNonNull(this.borderStyle);
            style.positionDependentRotation = this.positionDependentRotation;
            style.childrenLayoutStrategy = Objects.requireNonNull(this.childrenLayoutStrategy);
            return style;
        }
    }
}
