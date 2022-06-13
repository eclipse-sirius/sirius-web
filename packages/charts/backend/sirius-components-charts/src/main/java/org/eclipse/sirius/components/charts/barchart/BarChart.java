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
package org.eclipse.sirius.components.charts.barchart;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.charts.IChart;
import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle;

/**
 * Root concept of the bar-chart representation.
 *
 * @author fbarbin
 */
@Immutable
public final class BarChart implements IChart {
    public static final String KIND = "BarChart"; //$NON-NLS-1$

    private String id;

    private String descriptionId;

    private String label;

    private String kind;

    private List<BarChartEntry> entries;

    private BarChartStyle style;

    private BarChart() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    public List<BarChartEntry> getEntries() {
        return this.entries;
    }

    public BarChartStyle getStyle() {
        return this.style;
    }

    public static Builder newBarChart(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, label: {3}, kind: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.label, this.kind);
    }

    /**
     * The builder of the bar-chart element props.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String descriptionId;

        private String label;

        private String kind = KIND;

        private List<BarChartEntry> entries;

        private BarChartStyle style;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder entries(List<BarChartEntry> entries) {
            this.entries = new ArrayList<>(entries);
            return this;
        }

        public Builder style(BarChartStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public BarChart build() {
            BarChart barChart = new BarChart();
            barChart.id = Objects.requireNonNull(this.id);
            barChart.descriptionId = Objects.requireNonNull(this.descriptionId);
            barChart.label = Objects.requireNonNull(this.label);
            barChart.kind = Objects.requireNonNull(this.kind);
            barChart.entries = Objects.requireNonNull(this.entries);
            barChart.style = this.style;
            return barChart;
        }
    }
}
