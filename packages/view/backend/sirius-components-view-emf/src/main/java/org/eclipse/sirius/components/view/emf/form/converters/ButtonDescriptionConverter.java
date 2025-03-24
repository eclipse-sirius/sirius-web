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

import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.ButtonStyleProvider;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.form.ButtonDescriptionStyle;

/**
 * Used to convert button widgets.
 *
 * @author sbegaudeau
 */
public class ButtonDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public ButtonDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    public ButtonDescription convert(org.eclipse.sirius.components.view.form.ButtonDescription viewButtonDescription) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewButtonDescription);

        Function<VariableManager, ButtonStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewButtonDescription.getConditionalStyles().stream()
                    .filter(style -> this.interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
                            .asBoolean()
                            .orElse(Boolean.FALSE))
                    .map(ButtonDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewButtonDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ButtonStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        return ButtonDescription.newButtonDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .labelProvider(new StringValueProvider(this.interpreter, viewButtonDescription.getLabelExpression()))
                .isReadOnlyProvider(new ReadOnlyValueProvider(this.interpreter, viewButtonDescription.getIsEnabledExpression()))
                .buttonLabelProvider(new StringValueProvider(this.interpreter, viewButtonDescription.getButtonLabelExpression()))
                .imageURLProvider(new StringValueProvider(this.interpreter, viewButtonDescription.getImageExpression()))
                .pushButtonHandler(new ButtonPushHandler(this.interpreter, this.operationExecutor, this.feedbackMessageService, viewButtonDescription.getBody()))
                .styleProvider(styleProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewButtonDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewButtonDescription.getHelpExpression()))
                .build();
    }
}
