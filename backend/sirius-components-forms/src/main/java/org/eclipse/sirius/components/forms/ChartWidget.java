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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.charts.IChart;
import org.eclipse.sirius.components.forms.validation.Diagnostic;

/**
 * The Chart widget.
 *
 * @author fbarbin
 */
@Immutable
public final class ChartWidget extends AbstractWidget {
    private String label;

    private IChart chart;

    private ChartWidget() {
        // Prevent instantiation
    }

    public String getLabel() {
        return this.label;
    }

    public IChart getChart() {
        return this.chart;
    }

    public static Builder newChartWidget(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label);
    }

    /**
     * The builder used to create the Chart Widget.
     *
     * @author ldelaigue
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private final String id;

        private String label;

        private IChart chart;

        private List<Diagnostic> diagnostics;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder chart(IChart chart) {
            this.chart = Objects.requireNonNull(chart);
            return this;
        }

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public ChartWidget build() {
            ChartWidget link = new ChartWidget();
            link.id = Objects.requireNonNull(this.id);
            link.label = Objects.requireNonNull(this.label);
            link.chart = Objects.requireNonNull(this.chart);
            link.diagnostics = Objects.requireNonNull(this.diagnostics);
            return link;
        }
    }
}
