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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.SplitButtonDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert the split button.
 *
 * @author sbegaudeau
 */
@Service
public class SplitButtonDescriptionConverter implements IWidgetDescriptionConverter {

    private final IIdentityService identityService;

    private final IFormIdProvider widgetIdProvider;

    private final List<IWidgetDescriptionConverter> widgetDescriptionConverters;

    public SplitButtonDescriptionConverter(IIdentityService identityService, IFormIdProvider widgetIdProvider, List<IWidgetDescriptionConverter> widgetDescriptionConverters) {
        this.identityService = Objects.requireNonNull(identityService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.widgetDescriptionConverters = Objects.requireNonNull(widgetDescriptionConverters);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.SplitButtonDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.SplitButtonDescription viewSplitButtonDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewSplitButtonDescription);
            WidgetIdProvider idProvider = new WidgetIdProvider();

            List<ButtonDescription> actions = viewSplitButtonDescription.getActions().stream()
                    .map(buttonDescription  -> this.widgetDescriptionConverters.stream()
                            .filter(converter -> converter.canConvert(buttonDescription))
                            .findFirst()
                            .flatMap(converter -> converter.convert(buttonDescription, interpreter)))
                    .flatMap(Optional::stream)
                    .filter(ButtonDescription.class::isInstance)
                    .map(ButtonDescription.class::cast)
                    .toList();

            var splitButtonDescription = SplitButtonDescription.newSplitButtonDescription(descriptionId)
                    .idProvider(idProvider)
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.identityService))
                    .labelProvider(new StringValueProvider(interpreter, viewSplitButtonDescription.getLabelExpression()))
                    .isReadOnlyProvider(new ReadOnlyValueProvider(interpreter, viewSplitButtonDescription.getIsEnabledExpression()))
                    .actions(actions)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewSplitButtonDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewSplitButtonDescription.getHelpExpression()))
                    .build();

            return Optional.of(splitButtonDescription);
        }
        return Optional.empty();
    }
}
