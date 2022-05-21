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
package org.eclipse.sirius.components.forms.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.descriptions.IChartDescription;
import org.eclipse.sirius.components.charts.hierarchy.components.HierarchyComponent;
import org.eclipse.sirius.components.charts.hierarchy.components.HierarchyComponentProps;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.forms.elements.ChartWidgetElementProps;
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
        IChartDescription chartDescription = chartWidgetDescription.getChartDescription();

        List<Element> children = new ArrayList<>();
        if (chartDescription instanceof HierarchyDescription) {
            children.add(new Element(HierarchyComponent.class, new HierarchyComponentProps(variableManager, (HierarchyDescription) chartDescription, Optional.empty())));
        }
        children.add(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(chartWidgetDescription, variableManager)));

        // @formatter:off
        ChartWidgetElementProps chartWidgetElementProps = ChartWidgetElementProps.newChartWidgetElementProps(id)
                .label(label)
                .children(children)
                .build();
        // @formatter:on
        return new Element(ChartWidgetElementProps.TYPE, chartWidgetElementProps);
    }

}
