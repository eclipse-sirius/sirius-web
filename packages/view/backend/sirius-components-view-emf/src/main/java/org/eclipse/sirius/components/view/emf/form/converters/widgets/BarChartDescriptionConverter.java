/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form.converters.widgets;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle;
import org.eclipse.sirius.components.charts.barchart.descriptions.BarChartDescription;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.BarChartStyleProvider;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.MultiValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert bar chart widgets.
 *
 * @author sbegaudeau
 */
@Service
public class BarChartDescriptionConverter implements IWidgetDescriptionConverter {

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    public BarChartDescriptionConverter(IObjectService objectService, IFormIdProvider widgetIdProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.BarChartDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.BarChartDescription viewBarChartDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewBarChartDescription);

            String keysExpression = viewBarChartDescription.getKeysExpression();
            String valuesExpression = viewBarChartDescription.getValuesExpression();
            Function<VariableManager, BarChartStyle> styleProvider = new BarChartStyleProvider(interpreter, viewBarChartDescription);

            var barChartDescription = BarChartDescription.newBarChartDescription(descriptionId)
                    .label(viewBarChartDescription.getName())
                    .labelProvider(new StringValueProvider(interpreter, viewBarChartDescription.getLabelExpression()))
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                    .keysProvider(new MultiValueProvider(interpreter, keysExpression, String.class))
                    .valuesProvider(new MultiValueProvider(interpreter, valuesExpression, Number.class))
                    .styleProvider(styleProvider)
                    .width(viewBarChartDescription.getWidth())
                    .height(viewBarChartDescription.getHeight())
                    .yAxisLabelProvider(new StringValueProvider(interpreter, viewBarChartDescription.getYAxisLabelExpression()))
                    .build();

            var chartWidgetDescription = ChartWidgetDescription.newChartWidgetDescription(descriptionId)
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                    .labelProvider(new StringValueProvider(interpreter, viewBarChartDescription.getLabelExpression()))
                    .idProvider(new WidgetIdProvider())
                    .chartDescription(barChartDescription)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewBarChartDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewBarChartDescription.getHelpExpression()))
                    .build();

            return Optional.of(chartWidgetDescription);
        }
        return Optional.empty();
    }
}
