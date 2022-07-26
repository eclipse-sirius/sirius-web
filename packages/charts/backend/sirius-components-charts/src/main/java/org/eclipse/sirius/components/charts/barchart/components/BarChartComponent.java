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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.charts.barchart.BarChart;
import org.eclipse.sirius.components.charts.barchart.BarChartDescription;
import org.eclipse.sirius.components.charts.barchart.elements.BarChartElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the bar-chart representation.
 *
 * @author fbarbin
 */
public class BarChartComponent implements IComponent {

    private final BarChartComponentProps props;

    public BarChartComponent(BarChartComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        BarChartDescription barChartDescription = this.props.getBarChartDescription();
        Optional<BarChart> optionalPreviousBarChart = this.props.getPreviousBarChart();

        String id = optionalPreviousBarChart.map(BarChart::getId).orElseGet(() -> UUID.randomUUID().toString());
        String label = barChartDescription.getLabelProvider().apply(variableManager);
        List<Number> values = barChartDescription.getValuesProvider().apply(variableManager);
        List<String> keys = barChartDescription.getKeysProvider().apply(variableManager);

        // @formatter:off
        BarChartElementProps barChartElementProps = BarChartElementProps.newBarChartElementProps(id)
                .label(label)
                .descriptionId(barChartDescription.getId())
                .values(values)
                .keys(keys)
                .build();
        // @formatter:on

        return new Element(BarChartElementProps.TYPE, barChartElementProps);
    }

}
