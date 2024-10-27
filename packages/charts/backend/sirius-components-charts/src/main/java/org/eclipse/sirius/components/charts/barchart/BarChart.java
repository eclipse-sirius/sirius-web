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
    public static final String KIND = "BarChart";

    private String id;

    private String descriptionId;

    private String kind;

    private String targetObjectId;

    private List<BarChartEntry> entries;

    private BarChartStyle style;

    private int width;

    private int height;

    private String yAxisLabel;

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
    public String getKind() {
        return this.kind;
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public List<BarChartEntry> getEntries() {
        return this.entries;
    }

    public BarChartStyle getStyle() {
        return this.style;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getYAxisLabel() {
        return this.yAxisLabel;
    }

    public static Builder newBarChart(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, kind: {3}, width: {4}, height: {5}, yAxisLabel:{6}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.kind, this.width, this.height, this.yAxisLabel);
    }

    /**
     * The builder of the bar-chart.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String descriptionId;

        private String kind = KIND;

        private String targetObjectId;

        private List<BarChartEntry> entries;

        private BarChartStyle style;

        private int width;

        private int height;

        private String yAxisLabel;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
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

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder yAxisLabel(String yAxisLabel) {
            this.yAxisLabel = Objects.requireNonNull(yAxisLabel);
            return this;
        }

        public BarChart build() {
            BarChart barChart = new BarChart();
            barChart.id = Objects.requireNonNull(this.id);
            barChart.descriptionId = Objects.requireNonNull(this.descriptionId);
            barChart.kind = Objects.requireNonNull(this.kind);
            barChart.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            barChart.entries = Objects.requireNonNull(this.entries);
            barChart.style = this.style; // Optional on purpose
            barChart.width = this.width;
            barChart.height = this.height;
            barChart.yAxisLabel = this.yAxisLabel; // Optional on purpose
            return barChart;
        }
    }
}
