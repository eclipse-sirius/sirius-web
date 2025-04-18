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

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter.VARIABLE_MANAGER;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.MultiSelectStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.MultiSelectDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.MultiSelectStyleProvider;
import org.eclipse.sirius.components.view.emf.form.converters.MultiSelectNewValueHandler;
import org.eclipse.sirius.components.view.emf.form.converters.MultiSelectValuesProvider;
import org.eclipse.sirius.components.view.emf.form.converters.MultiValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.OptionIconURLsProvider;
import org.eclipse.sirius.components.view.emf.form.converters.OptionIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert multiselect widgets.
 *
 * @author sbegaudeau
 */
@Service
public class MultiSelectDescriptionConverter implements IWidgetDescriptionConverter {

    private final IObjectService objectService;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public MultiSelectDescriptionConverter(IObjectService objectService, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.MultiSelectDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.MultiSelectDescription viewMultiSelectDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewMultiSelectDescription);

            Function<VariableManager, MultiSelectStyle> styleProvider = variableManager -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                var effectiveStyle = viewMultiSelectDescription.getConditionalStyles().stream()
                        .filter(style -> interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
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

            var multiSelectDescription = MultiSelectDescription.newMultiSelectDescription(descriptionId)
                    .idProvider(new WidgetIdProvider())
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                    .labelProvider(new StringValueProvider(interpreter, viewMultiSelectDescription.getLabelExpression()))
                    .isReadOnlyProvider(new ReadOnlyValueProvider(interpreter, viewMultiSelectDescription.getIsEnabledExpression()))
                    .valuesProvider(new MultiSelectValuesProvider(interpreter, this.objectService, viewMultiSelectDescription.getValueExpression()))
                    .optionsProvider(new MultiValueProvider(interpreter, viewMultiSelectDescription.getCandidatesExpression(), Object.class))
                    .optionIdProvider(new OptionIdProvider(this.objectService))
                    .optionLabelProvider(new StringValueProvider(interpreter, viewMultiSelectDescription.getCandidateLabelExpression()))
                    .optionIconURLProvider(new OptionIconURLsProvider(this.objectService))
                    .newValuesHandler(new MultiSelectNewValueHandler(interpreter, this.objectService, this.operationExecutor, this.feedbackMessageService, viewMultiSelectDescription.getBody()))
                    .styleProvider(styleProvider)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewMultiSelectDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewMultiSelectDescription.getHelpExpression()))
                    .build();

            return Optional.of(multiSelectDescription);
        }
        return Optional.empty();
    }
}
