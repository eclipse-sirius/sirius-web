/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.charts.barchart.components;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The style of a BarChart.
 *
 * @author fbarbin
 */
@Immutable
public final class BarChartStyle {

    private String barsColor;

    private int fontSize;

    private boolean italic;

    private boolean bold;

    private boolean underline;

    private boolean strikeThrough;

    private BarChartStyle() {
        // prevent instantiation
    }

    public String getBarsColor() {
        return this.barsColor;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public boolean isBold() {
        return this.bold;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    public boolean isStrikeThrough() {
        return this.strikeThrough;
    }

    public static Builder newBarChartStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'barsColor: {1}, fontSize: {2}, italic: {3}, bold: {4}, underline: {5}, strikeThrough: {6}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.barsColor, this.fontSize, this.italic, this.bold, this.underline, this.strikeThrough);
    }

    /**
     * Builder used to create the BarChart style.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String barsColor;

        private int fontSize;

        private boolean italic;

        private boolean bold;

        private boolean underline;

        private boolean strikeThrough;

        public Builder barsColor(String barsColor) {
            this.barsColor = Objects.requireNonNull(barsColor);
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

        public Builder underline(boolean underline) {
            this.underline = underline;
            return this;
        }

        public Builder strikeThrough(boolean strikeThrough) {
            this.strikeThrough = strikeThrough;
            return this;
        }

        public BarChartStyle build() {
            BarChartStyle barChartStyle = new BarChartStyle();
            barChartStyle.barsColor = this.barsColor;
            barChartStyle.fontSize = this.fontSize;
            barChartStyle.italic = this.italic;
            barChartStyle.bold = this.bold;
            barChartStyle.underline = this.underline;
            barChartStyle.strikeThrough = this.strikeThrough;
            return barChartStyle;
        }
    }

}
