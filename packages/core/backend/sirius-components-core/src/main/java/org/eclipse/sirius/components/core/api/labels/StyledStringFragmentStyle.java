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
package org.eclipse.sirius.components.core.api.labels;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Fragment's style.
 *
 * @author mcharfadi
 */
@Immutable
public final class StyledStringFragmentStyle {
    private String font;

    private String backgroundColor;

    private String foregroundColor;

    private String strikeoutColor;

    private String underlineColor;

    private String borderColor;

    private boolean struckOut;

    private boolean bold;

    private boolean italic;

    private UnderLineStyle underlineStyle;

    private BorderStyle borderStyle;

    private  StyledStringFragmentStyle() {
        //prevent instantiation
    }

    public String getFont() {
        return this.font;
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public String getForegroundColor() {
        return this.foregroundColor;
    }

    public String getStrikeoutColor() {
        return this.strikeoutColor;
    }

    public String getUnderlineColor() {
        return this.underlineColor;
    }

    public String getBorderColor() {
        return this.borderColor;
    }

    public boolean isStruckOut() {
        return this.struckOut;
    }

    public boolean isBold() {
        return this.bold;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public static Builder newStyledStringFragmentStyle() {
        return new Builder();
    }

    public static Builder newDefaultStyledStringFragmentStyle() {
        return new Builder()
                .font("")
                .backgroundColor("")
                .foregroundColor("")
                .strikeoutColor("")
                .underlineColor("")
                .borderColor("")
                .struckOut(false)
                .bold(false)
                .italic(false)
                .underlineStyle(UnderLineStyle.NONE)
                .borderStyle(BorderStyle.NONE);
    }

    public UnderLineStyle getUnderlineStyle() {
        return this.underlineStyle;
    }

    public BorderStyle getBorderStyle() {
        return this.borderStyle;
    }

    /**
     * The builder used to create the StyledStringFragmentStyle object.
     *
     * @author mcharfadi
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String font;

        private String backgroundColor;

        private String foregroundColor;

        private String strikeoutColor;

        private String underlineColor;

        private String borderColor;

        private boolean struckOut;

        private boolean bold;

        private boolean italic;

        private UnderLineStyle underlineStyle;

        private BorderStyle borderStyle;

        private Builder() {
            //Default constructor
        }

        public Builder font(String font) {
            this.font = Objects.requireNonNull(font);
            return this;
        }

        public Builder backgroundColor(String backgroundColor) {
            this.backgroundColor = Objects.requireNonNull(backgroundColor);
            return this;
        }

        public Builder foregroundColor(String foregroundColor) {
            this.foregroundColor = Objects.requireNonNull(foregroundColor);
            return this;
        }

        public Builder strikeoutColor(String strikeoutColor) {
            this.strikeoutColor = Objects.requireNonNull(strikeoutColor);
            return this;
        }

        public Builder underlineColor(String underlineColor) {
            this.underlineColor = Objects.requireNonNull(underlineColor);
            return this;
        }

        public Builder borderColor(String borderColor) {
            this.borderColor = Objects.requireNonNull(borderColor);
            return this;
        }

        public Builder struckOut(boolean struckOut) {
            this.struckOut = struckOut;
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

        public Builder underlineStyle(UnderLineStyle underlineStyle) {
            this.underlineStyle = Objects.requireNonNull(underlineStyle);
            return this;
        }

        public Builder borderStyle(BorderStyle borderStyle) {
            this.borderStyle = Objects.requireNonNull(borderStyle);
            return this;
        }

        public StyledStringFragmentStyle build() {
            StyledStringFragmentStyle styledStringFragmentStyle = new StyledStringFragmentStyle();
            styledStringFragmentStyle.font = Objects.requireNonNull(this.font);
            styledStringFragmentStyle.backgroundColor = Objects.requireNonNull(this.backgroundColor);
            styledStringFragmentStyle.foregroundColor = Objects.requireNonNull(this.foregroundColor);
            styledStringFragmentStyle.strikeoutColor = Objects.requireNonNull(this.strikeoutColor);
            styledStringFragmentStyle.underlineColor = Objects.requireNonNull(this.underlineColor);
            styledStringFragmentStyle.borderColor = Objects.requireNonNull(this.borderColor);
            styledStringFragmentStyle.struckOut = Objects.requireNonNull(this.struckOut);
            styledStringFragmentStyle.underlineStyle = Objects.requireNonNull(this.underlineStyle);
            styledStringFragmentStyle.borderStyle = Objects.requireNonNull(this.borderStyle);
            styledStringFragmentStyle.bold = Objects.requireNonNull(this.bold);
            styledStringFragmentStyle.italic = Objects.requireNonNull(this.italic);
            return styledStringFragmentStyle;
        }


    }
}
