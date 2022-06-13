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
package org.eclipse.sirius.components.charts.piechart.components;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The style of a PieChart.
 *
 * @author fbarbin
 */
@Immutable
public final class PieChartStyle {

    private List<String> colors;

    private int strokeWidth;

    private String strokeColor;

    private int fontSize;

    private boolean italic;

    private boolean bold;

    private boolean underline;

    private boolean strikeThrough;

    private PieChartStyle() {
        // prevent instantiation
    }

    public List<String> getColors() {
        return this.colors;
    }

    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    public String getStrokeColor() {
        return this.strokeColor;
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

    public static Builder newPieChartStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'colors: {1}, strokeWidth: {2}, strokeColor: {3}, fontSize: {4}, italic: {5}, bold: {6}, underline: {7}, strikeThrough: {8}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.colors, this.strokeWidth, this.strokeColor, this.fontSize, this.italic, this.bold, this.underline,
                this.strikeThrough);
    }

    /**
     * Builder used to create the BarChart style.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private List<String> colors = new ArrayList<>();

        private int strokeWidth;

        private String strokeColor;

        private int fontSize;

        private boolean italic;

        private boolean bold;

        private boolean underline;

        private boolean strikeThrough;

        public Builder colors(List<String> colors) {
            this.colors = new ArrayList<>(Objects.requireNonNull(colors));
            return this;
        }

        public Builder strokeWidth(int strokeWidth) {
            this.strokeWidth = Objects.requireNonNull(strokeWidth);
            return this;
        }

        public Builder strokeColor(String strokeColor) {
            this.strokeColor = Objects.requireNonNull(strokeColor);
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

        public PieChartStyle build() {
            PieChartStyle pieChartStyle = new PieChartStyle();
            // All style attributes are optional.
            pieChartStyle.colors = this.colors;
            pieChartStyle.strokeWidth = this.strokeWidth;
            pieChartStyle.strokeColor = this.strokeColor;
            pieChartStyle.fontSize = this.fontSize;
            pieChartStyle.italic = this.italic;
            pieChartStyle.bold = this.bold;
            pieChartStyle.underline = this.underline;
            pieChartStyle.strikeThrough = this.strikeThrough;
            return pieChartStyle;
        }
    }

}
