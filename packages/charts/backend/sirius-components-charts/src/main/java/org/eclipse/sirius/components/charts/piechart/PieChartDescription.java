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
package org.eclipse.sirius.components.charts.piechart;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.charts.descriptions.IChartDescription;
import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Description of the pie-chart representation.
 *
 * @author fbarbin
 */
@Immutable
public final class PieChartDescription implements IChartDescription {

    private String id;

    private String label;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, List<Number>> valuesProvider;

    private Function<VariableManager, List<String>> keysProvider;

    private Function<VariableManager, PieChartStyle> styleProvider;

    private PieChartDescription() {
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

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, List<Number>> getValuesProvider() {
        return this.valuesProvider;
    }

    public Function<VariableManager, List<String>> getKeysProvider() {
        return this.keysProvider;
    }

    public Function<VariableManager, PieChartStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public static Builder newPieChartDescription(String id) {
        return new Builder(id);
    }

    /**
     * The Builder used to create the PieChartDescription.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, List<Number>> valuesProvider;

        private Function<VariableManager, List<String>> keysProvider;

        private Function<VariableManager, PieChartStyle> styleProvider;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
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

        public Builder styleProvider(Function<VariableManager, PieChartStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public PieChartDescription build() {
            PieChartDescription pieChartDescription = new PieChartDescription();
            pieChartDescription.id = Objects.requireNonNull(this.id);
            pieChartDescription.label = Objects.requireNonNull(this.label);
            pieChartDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            pieChartDescription.valuesProvider = Objects.requireNonNull(this.valuesProvider);
            pieChartDescription.keysProvider = Objects.requireNonNull(this.keysProvider);
            pieChartDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            return pieChartDescription;
        }
    }
}
