/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * A label style.
 *
 * @author hmarchadour
 */
@Immutable
public final class LabelStyle {

    private String color;

    private int fontSize;

    private boolean bold;

    private boolean italic;

    private boolean underline;

    private boolean strikeThrough;

    private List<String> iconURL;

    private String background;

    private String borderColor;

    private int borderSize;

    private int borderRadius;

    private LineStyle borderStyle;

    private LabelStyle() {
        // Prevent instantiation
    }

    public static Builder newLabelStyle() {
        return new Builder();
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public boolean isBold() {
        return this.bold;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    public boolean isStrikeThrough() {
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

    public int getBorderSize() {
        return this.borderSize;
    }

    public int getBorderRadius() {
        return this.borderRadius;
    }

    public LineStyle getBorderStyle() {
        return this.borderStyle;
    }

    /**
     * The builder used to create the label style.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String color;

        private int fontSize;

        private boolean bold;

        private boolean italic;

        private boolean underline;

        private boolean strikeThrough;

        private List<String> iconURL;

        private String background;

        private String borderColor;

        private int borderSize;

        private int borderRadius;

        private LineStyle borderStyle;

        private Builder() {
        }

        public Builder color(String color) {
            this.color = Objects.requireNonNull(color);
            return this;
        }

        public Builder fontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder bold(boolean bold) {
            this.bold = bold;
            return this;
        }

        public Builder italic(boolean italic) {
            this.italic = italic;
            return this;
        }

        public Builder underline(boolean underline) {
            this.underline = underline;
            return this;
        }

        public Builder strikeThrough(boolean strikeThrough) {
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

        public LabelStyle build() {
            LabelStyle labelDescription = new LabelStyle();
            labelDescription.color = Objects.requireNonNull(this.color);
            labelDescription.fontSize = this.fontSize;
            labelDescription.bold = this.bold;
            labelDescription.italic = this.italic;
            labelDescription.strikeThrough = this.strikeThrough;
            labelDescription.underline = this.underline;
            labelDescription.iconURL = Objects.requireNonNull(this.iconURL);
            labelDescription.background = Objects.requireNonNull(this.background);
            labelDescription.borderColor = Objects.requireNonNull(this.borderColor);
            labelDescription.borderSize = this.borderSize;
            labelDescription.borderRadius = this.borderRadius;
            labelDescription.borderStyle = Objects.requireNonNull(this.borderStyle);
            return labelDescription;
        }
    }
}
