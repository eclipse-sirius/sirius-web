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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * Label style with nullable fields to store customizations.
 *
 * @author nvannier
 */
@Immutable
public final class LabelCustomizedStyle {

    private String color;

    private Integer fontSize;

    private Boolean bold;

    private Boolean italic;

    private Boolean underline;

    private Boolean strikeThrough;

    private List<String> iconURL;

    private String background;

    private String borderColor;

    private Integer borderSize;

    private Integer borderRadius;

    private LineStyle borderStyle;

    private String maxWidth;

    private LabelCustomizedStyle() {
        // Prevent instantiation
    }

    public static LabelCustomizedStyle.Builder newLabelCustomizedStyle() {
        return new Builder();
    }

    public static LabelCustomizedStyle.Builder newLabelCustomizedStyle(LabelCustomizedStyle source) {
        return new Builder(source);
    }


    public Integer getFontSize() {
        return this.fontSize;
    }

    public Boolean isBold() {
        return this.bold;
    }

    public Boolean isItalic() {
        return this.italic;
    }

    public Boolean isUnderline() {
        return this.underline;
    }

    public Boolean isStrikeThrough() {
        return this.strikeThrough;
    }

    public String getColor() {
        return this.color;
    }

    public List<String> getIconURL() {
        return this.iconURL;
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

    public String getMaxWidth() {
        return this.maxWidth;
    }

    /**
     * Builder for a label customized style.
     *
     * @author nvannier
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String color;

        private Integer fontSize;

        private Boolean bold;

        private Boolean italic;

        private Boolean underline;

        private Boolean strikeThrough;

        private List<String> iconURL;

        private String background;

        private String borderColor;

        private Integer borderSize;

        private Integer borderRadius;

        private LineStyle borderStyle;

        private String maxWidth;

        private Builder() {
        }

        private Builder(LabelCustomizedStyle source) {
            this.color = source.getColor();
            this.fontSize = source.getFontSize();
            this.bold = source.isBold();
            this.italic = source.isItalic();
            this.underline = source.isUnderline();
            this.strikeThrough = source.isStrikeThrough();
            this.iconURL = source.getIconURL();
            this.background = source.getBackground();
            this.borderColor = source.getBorderColor();
            this.borderSize = source.getBorderSize();
            this.borderRadius = source.getBorderRadius();
            this.borderStyle = source.getBorderStyle();
            this.maxWidth = source.getMaxWidth();
        }

        public Builder color(String color) {
            this.color = Objects.requireNonNull(color);
            return this;
        }

        public Builder fontSize(Integer fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder bold(Boolean bold) {
            this.bold = bold;
            return this;
        }

        public Builder italic(Boolean italic) {
            this.italic = italic;
            return this;
        }

        public Builder underline(Boolean underline) {
            this.underline = underline;
            return this;
        }

        public Builder strikeThrough(Boolean strikeThrough) {
            this.strikeThrough = strikeThrough;
            return this;
        }

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder background(String background) {
            this.background = Objects.requireNonNull(background);
            return this;
        }

        public Builder borderColor(String borderColor) {
            this.borderColor = Objects.requireNonNull(borderColor);
            return this;
        }

        public Builder borderSize(Integer borderSize) {
            this.borderSize = borderSize;
            return this;
        }

        public Builder borderRadius(Integer borderRadius) {
            this.borderRadius = borderRadius;
            return this;
        }

        public Builder borderStyle(LineStyle borderStyle) {
            this.borderStyle = Objects.requireNonNull(borderStyle);
            return this;
        }

        public Builder maxWidth(String maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public LabelCustomizedStyle build() {
            LabelCustomizedStyle labelCustomizedStyle = new LabelCustomizedStyle();
            labelCustomizedStyle.color = this.color;
            labelCustomizedStyle.fontSize = this.fontSize;
            labelCustomizedStyle.bold = this.bold;
            labelCustomizedStyle.italic = this.italic;
            labelCustomizedStyle.strikeThrough = this.strikeThrough;
            labelCustomizedStyle.underline = this.underline;
            labelCustomizedStyle.iconURL = this.iconURL;
            labelCustomizedStyle.background = this.background;
            labelCustomizedStyle.borderColor = this.borderColor;
            labelCustomizedStyle.borderSize = this.borderSize;
            labelCustomizedStyle.borderRadius = this.borderRadius;
            labelCustomizedStyle.borderStyle = this.borderStyle;
            labelCustomizedStyle.maxWidth = this.maxWidth;

            return labelCustomizedStyle;
        }
    }
}
