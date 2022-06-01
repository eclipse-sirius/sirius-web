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
package org.eclipse.sirius.components.charts.barchart.elements;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the bar-chart element.
 *
 * @author fbarbin
 */
public final class BarChartElementProps implements IProps {

    public static final String TYPE = "BarChart"; //$NON-NLS-1$

    private String id;

    private String descriptionId;

    private String label;

    private List<Number> values;

    private List<String> keys;

    private BarChartElementProps() {
        // prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public String getLabel() {
        return this.label;
    }

    public List<Number> getValues() {
        return this.values;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public static Builder newBarChartElementProps(String id) {
        return new Builder(id);
    }

    /**
     * The Builder used to create a new BarChartElementProps.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String descriptionId;

        private String label;

        private List<Number> values;

        private List<String> keys;

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

        public Builder values(List<Number> values) {
            this.values = Objects.requireNonNull(values);
            return this;
        }

        public Builder keys(List<String> keys) {
            this.keys = Objects.requireNonNull(keys);
            return this;
        }

        public BarChartElementProps build() {
            BarChartElementProps barChartElementProps = new BarChartElementProps();
            barChartElementProps.id = Objects.requireNonNull(this.id);
            barChartElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            barChartElementProps.label = Objects.requireNonNull(this.label);
            barChartElementProps.values = Objects.requireNonNull(this.values);
            barChartElementProps.keys = Objects.requireNonNull(this.keys);
            return barChartElementProps;
        }
    }
}
