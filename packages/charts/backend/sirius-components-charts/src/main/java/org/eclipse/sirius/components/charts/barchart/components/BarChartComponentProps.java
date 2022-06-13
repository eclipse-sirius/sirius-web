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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.barchart.BarChart;
import org.eclipse.sirius.components.charts.barchart.descriptions.BarChartDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The props of the bar-chart component.
 *
 * @author fbarbin
 */
public class BarChartComponentProps implements IProps {

    private final VariableManager variableManager;

    private final BarChartDescription barChartDescription;

    private final Optional<BarChart> previousBarChart;

    public BarChartComponentProps(VariableManager variableManager, BarChartDescription barChartDescription, Optional<BarChart> previousBarChart) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.barChartDescription = Objects.requireNonNull(barChartDescription);
        this.previousBarChart = Objects.requireNonNull(previousBarChart);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public BarChartDescription getBarChartDescription() {
        return this.barChartDescription;
    }

    public Optional<BarChart> getPreviousBarChart() {
        return this.previousBarChart;
    }

}
