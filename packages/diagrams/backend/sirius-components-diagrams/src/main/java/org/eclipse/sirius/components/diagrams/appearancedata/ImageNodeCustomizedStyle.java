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
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * The image node style with nullable fields to store customizations.
 *
 * @author nvannier
 */
@Immutable
public final class ImageNodeCustomizedStyle implements INodeCustomizedStyle {

    private String imageURL;

    private Integer scalingFactor;

    private String borderColor;

    private Integer borderSize;

    private Integer borderRadius;

    private LineStyle borderStyle;

    private Boolean positionDependentRotation;

    private ImageNodeCustomizedStyle() {
        // Prevent instantiation
    }

    public static Builder newImageNodeCustomizedStyle() {
        return new Builder();
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public Integer getScalingFactor() {
        return this.scalingFactor;
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

    public Boolean isPositionDependentRotation() {
        return this.positionDependentRotation;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'imageURL: {1}', border: '{' size: {2}, color: {3}, style: {4} '}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.imageURL, this.borderSize, this.borderColor, this.borderStyle);
    }

    @Override
    public INodeStyle mergeInto(INodeStyle style) {
        if (style instanceof ImageNodeStyle imageNodeStyle) {
            return ImageNodeStyle.newImageNodeStyle()
                    .imageURL(Optional.ofNullable(imageURL).orElse(imageNodeStyle.getImageURL()))
                    .scalingFactor(Optional.ofNullable(scalingFactor).orElse(imageNodeStyle.getScalingFactor()))
                    .borderColor(Optional.ofNullable(borderColor).orElse(imageNodeStyle.getBorderColor()))
                    .borderSize(Optional.ofNullable(borderSize).orElse(imageNodeStyle.getBorderSize()))
                    .borderRadius(Optional.ofNullable(borderRadius).orElse(imageNodeStyle.getBorderRadius()))
                    .borderStyle(Optional.ofNullable(borderStyle).orElse(imageNodeStyle.getBorderStyle()))
                    .build();
        } else {
            return style;
        }
    }

    /**
     * Builder for an image node customized style.
     *
     * @author nvannier
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String imageURL;

        private Integer scalingFactor;

        private String borderColor;

        private Integer borderSize;

        private Integer borderRadius;

        private LineStyle borderStyle;

        private Boolean positionDependentRotation;

        private Builder() {
            // Prevent instantiation
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

        public ImageNodeCustomizedStyle build() {
            ImageNodeCustomizedStyle style = new ImageNodeCustomizedStyle();
            style.imageURL = this.imageURL;
            style.scalingFactor = this.scalingFactor;
            style.borderColor = this.borderColor;
            style.borderSize = this.borderSize;
            style.borderRadius = this.borderRadius;
            style.borderStyle = this.borderStyle;
            style.positionDependentRotation = this.positionDependentRotation;
            return style;
        }
    }
}
