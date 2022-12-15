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
package org.eclipse.sirius.components.charts.piechart;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.charts.IChart;
import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle;

/**
 * Root concept of the pie-chart representation.
 *
 * @author fbarbin
 */
@Immutable
public final class PieChart implements IChart {
    public static final String KIND = "PieChart";

    private String id;

    private String descriptionId;

    private String label;

    private String kind;

    private List<PieChartEntry> entries;

    private PieChartStyle style;

    private PieChart() {
        // prevent instantiation
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

    public List<PieChartEntry> getEntries() {
        return this.entries;
    }

    public PieChartStyle getStyle() {
        return this.style;
    }

    public static Builder newPieChart(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, label: {3}, kind: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.label, this.kind);
    }

    /**
     * The builder of the pie-chart.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String descriptionId;

        private String label;

        private String kind = KIND;

        private List<PieChartEntry> entries;

        private PieChartStyle style;

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

        public Builder entries(List<PieChartEntry> entries) {
            this.entries = new ArrayList<>(Objects.requireNonNull(entries));
            return this;
        }

        public Builder style(PieChartStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public PieChart build() {
            PieChart pieChart = new PieChart();
            pieChart.id = Objects.requireNonNull(this.id);
            pieChart.label = Objects.requireNonNull(this.label);
            pieChart.descriptionId = Objects.requireNonNull(this.descriptionId);
            pieChart.kind = Objects.requireNonNull(this.kind);
            pieChart.entries = Objects.requireNonNull(this.entries);
            pieChart.style = this.style; // Optional on purpose
            return pieChart;
        }
    }

}
