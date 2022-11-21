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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.charts.piechart.PieChart;
import org.eclipse.sirius.components.charts.piechart.PieChartDescription;
import org.eclipse.sirius.components.charts.piechart.elements.PieChartElementProps;
import org.eclipse.sirius.components.charts.piechart.elements.PieChartElementProps.Builder;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the pie-chart representation.
 *
 * @author fbarbin
 */
public class PieChartComponent implements IComponent {

    private final PieChartComponentProps props;

    public PieChartComponent(PieChartComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        PieChartDescription pieChartDescription = this.props.getPieChartDescription();
        Optional<PieChart> optionalPreviousPieChart = this.props.getPreviousPieChart();

        String id = optionalPreviousPieChart.map(PieChart::getId).orElseGet(() -> UUID.randomUUID().toString());
        String label = Optional.ofNullable(pieChartDescription.getLabel()).orElse(""); //$NON-NLS-1$
        List<Number> values = pieChartDescription.getValuesProvider().apply(variableManager);
        List<String> keys = pieChartDescription.getKeysProvider().apply(variableManager);
        PieChartStyle pieChartStyle = pieChartDescription.getStyleProvider().apply(variableManager);

        // @formatter:off
        Builder builder = PieChartElementProps.newPieChartElementProps(id)
                .label(label)
                .descriptionId(pieChartDescription.getId())
                .values(values)
                .keys(keys);
        // @formatter:on
        if (pieChartStyle != null) {
            builder.style(pieChartStyle);
        }
        PieChartElementProps pieChartElementProps = builder.build();
        return new Element(PieChartElementProps.TYPE, pieChartElementProps);
    }

}
