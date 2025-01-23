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

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverterSwitch.VARIABLE_MANAGER;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.RadioStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.RadioDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.RadioStyleProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.form.RadioDescriptionStyle;

/**
 * Used to convert radio widgets.
 *
 * @author sbegaudeau
 */
public class RadioDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public RadioDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IEditService editService, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    public RadioDescription convert(org.eclipse.sirius.components.view.form.RadioDescription viewRadioDescription) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewRadioDescription);

        Function<VariableManager, RadioStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewRadioDescription.getConditionalStyles().stream()
                    .filter(style -> this.interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
                            .asBoolean()
                            .orElse(Boolean.FALSE))
                    .map(RadioDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewRadioDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new RadioStyleProvider(effectiveStyle).apply(variableManager);
        };

        return RadioDescription.newRadioDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .labelProvider(new StringValueProvider(this.interpreter, viewRadioDescription.getLabelExpression()))
                .isReadOnlyProvider(new ReadOnlyValueProvider(this.interpreter, viewRadioDescription.getIsEnabledExpression()))
                .optionsProvider(new MultiValueProvider(this.interpreter, viewRadioDescription.getCandidatesExpression()))
                .optionIdProvider(new OptionIdProvider(this.objectService))
                .optionLabelProvider(new StringValueProvider(this.interpreter, viewRadioDescription.getCandidateLabelExpression()))
                .optionSelectedProvider(new OptionSelectedProvider(this.interpreter, viewRadioDescription.getValueExpression()))
                .newValueHandler(new SelectNewValueHandler(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, viewRadioDescription.getBody()))
                .styleProvider(styleProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewRadioDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewRadioDescription.getHelpExpression()))
                .build();
    }
}
