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
package org.eclipse.sirius.components.charts.barchart.descriptions;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle;
import org.eclipse.sirius.components.charts.descriptions.IChartDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Description of the bar chart representation.
 *
 * @author fbarbin
 */
@Immutable
public final class BarChartDescription implements IChartDescription {

    private String id;

    private String label;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<Number>> valuesProvider;

    private Function<VariableManager, List<String>> keysProvider;

    private Function<VariableManager, BarChartStyle> styleProvider;

    private int width;

    private int height;

    private BarChartDescription() {
        // prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<Number>> getValuesProvider() {
        return this.valuesProvider;
    }

    public Function<VariableManager, List<String>> getKeysProvider() {
        return this.keysProvider;
    }

    public Function<VariableManager, BarChartStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public static Builder newBarChartDescription(String id) {
        return new Builder(id);
    }

    /**
     * The Builder used to create the BarChartDescription.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<Number>> valuesProvider;

        private Function<VariableManager, List<String>> keysProvider;

        private Function<VariableManager, BarChartStyle> styleProvider = variableManager -> null;

        private int width;

        private int height;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder valuesProvider(Function<VariableManager, List<Number>> valuesProvider) {
            this.valuesProvider = Objects.requireNonNull(valuesProvider);
            return this;
        }

        public Builder keysProvider(Function<VariableManager, List<String>> keysProvider) {
            this.keysProvider = Objects.requireNonNull(keysProvider);
            return this;
        }

        public Builder styleProvider(Function<VariableManager, BarChartStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
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

        public BarChartDescription build() {
            BarChartDescription barChartDescription = new BarChartDescription();
            barChartDescription.id = Objects.requireNonNull(this.id);
            barChartDescription.label = Objects.requireNonNull(this.label);
            barChartDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            barChartDescription.valuesProvider = Objects.requireNonNull(this.valuesProvider);
            barChartDescription.keysProvider = Objects.requireNonNull(this.keysProvider);
            barChartDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            barChartDescription.width = this.width;
            barChartDescription.height = this.height;
            return barChartDescription;
        }
    }
}
