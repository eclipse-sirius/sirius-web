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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.piechart.PieChart;
import org.eclipse.sirius.components.charts.piechart.PieChartDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The props of the pie-chart component.
 *
 * @author fbarbin
 */
public class PieChartComponentProps implements IProps {

    private final VariableManager variableManager;

    private final PieChartDescription pieChartDescription;

    private final Optional<PieChart> previousPieChart;

    public PieChartComponentProps(VariableManager variableManager, PieChartDescription pieChartDescription, Optional<PieChart> previousPieChart) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.pieChartDescription = Objects.requireNonNull(pieChartDescription);
        this.previousPieChart = Objects.requireNonNull(previousPieChart);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public PieChartDescription getPieChartDescription() {
        return this.pieChartDescription;
    }

    public Optional<PieChart> getPreviousPieChart() {
        return this.previousPieChart;
    }

}
