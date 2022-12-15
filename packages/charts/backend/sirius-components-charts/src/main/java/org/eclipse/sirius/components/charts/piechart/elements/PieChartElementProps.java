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
package org.eclipse.sirius.components.charts.piechart.elements;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the pie-chart element.
 *
 * @author fbarbin
 */
@Immutable
public final class PieChartElementProps implements IProps {

    public static final String TYPE = "PieChart";

    private String id;

    private String label;

    private String descriptionId;

    private List<Number> values;

    private List<String> keys;

    private PieChartStyle style;

    private PieChartElementProps() {
        // prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public List<Number> getValues() {
        return this.values;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public PieChartStyle getStyle() {
        return this.style;
    }

    public static Builder newPieChartElementProps(String id) {
        return new Builder(id);
    }

    /**
     * The Builder used to create a new PieChartElementProps.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String descriptionId;

        private List<Number> values;

        private List<String> keys;

        private PieChartStyle style;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder values(List<Number> values) {
            this.values = Objects.requireNonNull(values);
            return this;
        }

        public Builder keys(List<String> keys) {
            this.keys = Objects.requireNonNull(keys);
            return this;
        }

        public Builder style(PieChartStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public PieChartElementProps build() {
            PieChartElementProps pieChartElementProps = new PieChartElementProps();
            pieChartElementProps.id = Objects.requireNonNull(this.id);
            pieChartElementProps.label = Objects.requireNonNull(this.label);
            pieChartElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            pieChartElementProps.values = Objects.requireNonNull(this.values);
            pieChartElementProps.keys = Objects.requireNonNull(this.keys);
            pieChartElementProps.style = this.style; // Optional on purpose
            return pieChartElementProps;
        }
    }
}
