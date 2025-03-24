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
import org.eclipse.sirius.components.forms.MultiSelectStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.MultiSelectDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.MultiSelectStyleProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle;

/**
 * Used to convert multiselect widgets.
 *
 * @author sbegaudeau
 */
public class MultiSelectDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public MultiSelectDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    public MultiSelectDescription convert(org.eclipse.sirius.components.view.form.MultiSelectDescription viewMultiSelectDescription) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewMultiSelectDescription);

        Function<VariableManager, MultiSelectStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewMultiSelectDescription.getConditionalStyles().stream()
                    .filter(style -> this.interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
                            .asBoolean()
                            .orElse(Boolean.FALSE))
                    .map(MultiSelectDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewMultiSelectDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new MultiSelectStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        return MultiSelectDescription.newMultiSelectDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .labelProvider(new StringValueProvider(this.interpreter, viewMultiSelectDescription.getLabelExpression()))
                .isReadOnlyProvider(new ReadOnlyValueProvider(this.interpreter, viewMultiSelectDescription.getIsEnabledExpression()))
                .valuesProvider(new MultiSelectValuesProvider(this.interpreter, this.objectService, viewMultiSelectDescription.getValueExpression()))
                .optionsProvider(new MultiValueProvider(this.interpreter, viewMultiSelectDescription.getCandidatesExpression(), Object.class))
                .optionIdProvider(new OptionIdProvider(this.objectService))
                .optionLabelProvider(new StringValueProvider(this.interpreter, viewMultiSelectDescription.getCandidateLabelExpression()))
                .optionIconURLProvider(new OptionIconURLsProvider(this.objectService))
                .newValuesHandler(new MultiSelectNewValueHandler(this.interpreter, this.objectService, this.operationExecutor, this.feedbackMessageService, viewMultiSelectDescription.getBody()))
                .styleProvider(styleProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewMultiSelectDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewMultiSelectDescription.getHelpExpression()))
                .build();
    }
}
