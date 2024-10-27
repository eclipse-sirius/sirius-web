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
package org.eclipse.sirius.components.charts.barchart.components;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.charts.barchart.BarChart;
import org.eclipse.sirius.components.charts.barchart.descriptions.BarChartDescription;
import org.eclipse.sirius.components.charts.barchart.elements.BarChartElementProps;
import org.eclipse.sirius.components.charts.barchart.elements.BarChartElementProps.Builder;
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
        String targetObjectId = barChartDescription.getTargetObjectIdProvider().apply(variableManager);
        List<Number> values = barChartDescription.getValuesProvider().apply(variableManager);
        List<String> keys = barChartDescription.getKeysProvider().apply(variableManager);
        String yAxisLabel = barChartDescription.getYAxisLabelProvider().apply(variableManager);
        BarChartStyle barChartStyle = barChartDescription.getStyleProvider().apply(variableManager);

        Builder builder = BarChartElementProps.newBarChartElementProps(id)
                .descriptionId(barChartDescription.getId())
                .targetObjectId(targetObjectId)
                .values(values)
                .width(barChartDescription.getWidth())
                .height(barChartDescription.getHeight())
                .keys(keys);

        if (barChartStyle != null) {
            builder.style(barChartStyle);
        }
        if (yAxisLabel != null) {
            builder.yAxisLabel(yAxisLabel);
        }
        BarChartElementProps barChartElementProps = builder.build();
        return new Element(BarChartElementProps.TYPE, barChartElementProps);
    }

}
