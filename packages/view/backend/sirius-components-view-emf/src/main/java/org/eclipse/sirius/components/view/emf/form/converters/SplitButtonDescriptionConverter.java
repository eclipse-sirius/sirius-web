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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.SplitButtonDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverterSwitch;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;

/**
 * Used to convert the split button.
 *
 * @author sbegaudeau
 */
public class SplitButtonDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    private final ViewFormDescriptionConverterSwitch viewFormDescriptionConverterSwitch;

    public SplitButtonDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IFormIdProvider widgetIdProvider, ViewFormDescriptionConverterSwitch viewFormDescriptionConverterSwitch) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.viewFormDescriptionConverterSwitch = Objects.requireNonNull(viewFormDescriptionConverterSwitch);
    }

    public SplitButtonDescription convert(org.eclipse.sirius.components.view.form.SplitButtonDescription viewSplitButtonDescription) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewSplitButtonDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();

        List<ButtonDescription> actions = viewSplitButtonDescription.getActions().stream()
                .map(this.viewFormDescriptionConverterSwitch::doSwitch)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(ButtonDescription.class::isInstance)
                .map(ButtonDescription.class::cast).toList();

        return SplitButtonDescription.newSplitButtonDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .labelProvider(new StringValueProvider(this.interpreter, viewSplitButtonDescription.getLabelExpression()))
                .isReadOnlyProvider(new ReadOnlyValueProvider(this.interpreter, viewSplitButtonDescription.getIsEnabledExpression()))
                .actions(actions)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewSplitButtonDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewSplitButtonDescription.getHelpExpression()))
                .build();
    }
}
