/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The style of a Select.
 *
 * @author arichard
 */
@Immutable
public final class SelectStyle extends AbstractFontStyle {

    private String backgroundColor;

    private String foregroundColor;

    private boolean showIcon;

    private WidgetGridLayout widgetGridLayout;

    private SelectStyle() {
        // Prevent instantiation
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public String getForegroundColor() {
        return this.foregroundColor;
    }

    public boolean isShowIcon() {
        return this.showIcon;
    }

    public WidgetGridLayout getWidgetGridLayout() {
        return this.widgetGridLayout;
    }

    public static Builder newSelectStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'backgroundColor: {1}, foregroundColor: {2}, fontSize: {3}, italic: {4}, bold: {5}, underline: {6}, strikeThrough: {7}, isShowIcon: {8'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.backgroundColor, this.foregroundColor, this.fontSize, this.italic, this.bold, this.underline, this.strikeThrough, this.showIcon);
    }

    /**
     * Builder used to create the Select style.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String backgroundColor;

        private String foregroundColor;

        private int fontSize;

        private boolean italic;

        private boolean bold;

        private boolean underline;

        private boolean strikeThrough;

        private boolean showIcon;

        private WidgetGridLayout widgetGridLayout;

        private Builder() {
        }

        public Builder backgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder foregroundColor(String foregroundColor) {
            this.foregroundColor = foregroundColor;
            return this;
        }

        public Builder fontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder italic(boolean italic) {
            this.italic = italic;
            return this;
        }

        public Builder bold(boolean bold) {
            this.bold = bold;
            return this;
        }

        public Builder showIcon(boolean showIcon) {
            this.showIcon = showIcon;
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

        public Builder widgetGridLayout(WidgetGridLayout widgetGridLayout) {
            this.widgetGridLayout = Objects.requireNonNull(widgetGridLayout);
            return this;
        }

        public SelectStyle build() {
            SelectStyle selectStyle = new SelectStyle();
            selectStyle.backgroundColor = this.backgroundColor;
            selectStyle.foregroundColor = this.foregroundColor;
            selectStyle.fontSize = this.fontSize;
            selectStyle.italic = this.italic;
            selectStyle.bold = this.bold;
            selectStyle.underline = this.underline;
            selectStyle.strikeThrough = this.strikeThrough;
            selectStyle.showIcon = this.showIcon;
            selectStyle.widgetGridLayout = this.widgetGridLayout;
            return selectStyle;
        }

    }
}
