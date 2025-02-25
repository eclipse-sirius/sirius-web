/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form.converters;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle;
import org.eclipse.sirius.components.charts.barchart.descriptions.BarChartDescription;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.BarChartStyleProvider;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;

/**
 * Used to convert bar chart widgets.
 *
 * @author sbegaudeau
 */
public class BarChartDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    public BarChartDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IFormIdProvider widgetIdProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    public ChartWidgetDescription convert(org.eclipse.sirius.components.view.form.BarChartDescription viewBarChartDescription) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewBarChartDescription);

        String keysExpression = viewBarChartDescription.getKeysExpression();
        String valuesExpression = viewBarChartDescription.getValuesExpression();
        Function<VariableManager, BarChartStyle> styleProvider = new BarChartStyleProvider(this.interpreter, viewBarChartDescription);

        var barChartDescription = BarChartDescription.newBarChartDescription(descriptionId)
                .label(viewBarChartDescription.getName())
                .labelProvider(new StringValueProvider(this.interpreter, viewBarChartDescription.getLabelExpression()))
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .keysProvider(new MultiValueProvider(this.interpreter, keysExpression, String.class))
                .valuesProvider(new MultiValueProvider(this.interpreter, valuesExpression, Number.class))
                .styleProvider(styleProvider)
                .width(viewBarChartDescription.getWidth())
                .height(viewBarChartDescription.getHeight())
                .yAxisLabelProvider(new StringValueProvider(this.interpreter, viewBarChartDescription.getYAxisLabelExpression()))
                .build();

        return ChartWidgetDescription.newChartWidgetDescription(descriptionId)
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .labelProvider(new StringValueProvider(this.interpreter, viewBarChartDescription.getLabelExpression()))
                .idProvider(new WidgetIdProvider())
                .chartDescription(barChartDescription)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewBarChartDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewBarChartDescription.getHelpExpression()))
                .build();
    }
}
