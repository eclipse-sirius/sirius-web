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
package org.eclipse.sirius.components.view.emf.form.converters.widgets;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.charts.piechart.PieChartDescription;
import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.PieChartStyleProvider;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.MultiValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert pie chart widgets.
 *
 * @author sbegaudeau
 */
@Service
public class PieChartDescriptionConverter implements IWidgetDescriptionConverter {

    private final IIdentityService identityService;

    private final IFormIdProvider widgetIdProvider;

    public PieChartDescriptionConverter(IIdentityService identityService, IFormIdProvider widgetIdProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.PieChartDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.PieChartDescription viewPieChartDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewPieChartDescription);

            String keysExpression = viewPieChartDescription.getKeysExpression();
            String valuesExpression = viewPieChartDescription.getValuesExpression();

            Function<VariableManager, PieChartStyle> styleProvider = new PieChartStyleProvider(interpreter, viewPieChartDescription);
            var pieChartDescription =  PieChartDescription.newPieChartDescription(descriptionId)
                    .label(viewPieChartDescription.getName())
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.identityService))
                    .keysProvider(new MultiValueProvider<>(interpreter, keysExpression, String.class))
                    .valuesProvider(new MultiValueProvider<>(interpreter, valuesExpression, Number.class))
                    .styleProvider(styleProvider)
                    .build();

            var chartWidgetDescription = ChartWidgetDescription.newChartWidgetDescription(descriptionId)
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.identityService))
                    .labelProvider(new StringValueProvider(interpreter, viewPieChartDescription.getLabelExpression()))
                    .idProvider(new WidgetIdProvider())
                    .chartDescription(pieChartDescription)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewPieChartDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewPieChartDescription.getHelpExpression()))
                    .build();

            return Optional.of(chartWidgetDescription);
        }
        return Optional.empty();
    }
}
