/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.forms.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.barchart.components.BarChartComponent;
import org.eclipse.sirius.components.charts.barchart.components.BarChartComponentProps;
import org.eclipse.sirius.components.charts.barchart.descriptions.BarChartDescription;
import org.eclipse.sirius.components.charts.descriptions.IChartDescription;
import org.eclipse.sirius.components.charts.piechart.PieChartDescription;
import org.eclipse.sirius.components.charts.piechart.components.PieChartComponent;
import org.eclipse.sirius.components.charts.piechart.components.PieChartComponentProps;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.forms.elements.ChartWidgetElementProps;
import org.eclipse.sirius.components.forms.elements.ChartWidgetElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to create the chart widget.
 *
 * @author fbarbin
 */
public class ChartWidgetComponent implements IComponent {

    private final ChartWidgetComponentProps props;

    public ChartWidgetComponent(ChartWidgetComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ChartWidgetDescription chartWidgetDescription = this.props.getChartWidgetDescription();

        String id = chartWidgetDescription.getIdProvider().apply(variableManager);
        String label = chartWidgetDescription.getLabelProvider().apply(variableManager);
        String iconURL = chartWidgetDescription.getIconURLProvider().apply(variableManager);
        IChartDescription chartDescription = chartWidgetDescription.getChartDescription();

        List<Element> children = new ArrayList<>();
        if (chartDescription instanceof BarChartDescription) {
            children.add(new Element(BarChartComponent.class, new BarChartComponentProps(variableManager, (BarChartDescription) chartDescription, Optional.empty())));
        } else if (chartDescription instanceof PieChartDescription) {
            children.add(new Element(PieChartComponent.class, new PieChartComponentProps(variableManager, (PieChartDescription) chartDescription, Optional.empty())));
        }
        children.add(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(chartWidgetDescription, variableManager)));

        // @formatter:off
        Builder chartWidgetElementPropsBuilder = ChartWidgetElementProps.newChartWidgetElementProps(id)
                .label(label)
                .children(children);
        // @formatter:on
        if (iconURL != null) {
            chartWidgetElementPropsBuilder.iconURL(iconURL);
        }
        if (chartWidgetDescription.getHelpTextProvider() != null) {
            chartWidgetElementPropsBuilder.helpTextProvider(() -> chartWidgetDescription.getHelpTextProvider().apply(variableManager));
        }
        return new Element(ChartWidgetElementProps.TYPE, chartWidgetElementPropsBuilder.build());
    }

}
