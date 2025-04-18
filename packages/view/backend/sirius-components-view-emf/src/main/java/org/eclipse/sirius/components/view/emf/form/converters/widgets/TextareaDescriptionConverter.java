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
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.TextareaStyleProvider;
import org.eclipse.sirius.components.view.emf.form.converters.NewValueHandler;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.form.TextAreaDescription;
import org.eclipse.sirius.components.view.form.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert textareas.
 *
 * @author sbegaudeau
 */
@Service
public class TextareaDescriptionConverter implements IWidgetDescriptionConverter {

    private final IObjectService objectService;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public TextareaDescriptionConverter(IObjectService objectService, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof TextAreaDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof TextAreaDescription viewTextAreaDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewTextAreaDescription);

            Function<VariableManager, TextareaStyle> styleProvider = variableManager -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                var effectiveStyle = viewTextAreaDescription.getConditionalStyles().stream()
                        .filter(style -> interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
                                .asBoolean()
                                .orElse(Boolean.FALSE))
                        .map(TextareaDescriptionStyle.class::cast)
                        .findFirst()
                        .orElseGet(viewTextAreaDescription::getStyle);
                if (effectiveStyle == null) {
                    return null;
                }
                return new TextareaStyleProvider(effectiveStyle).apply(childVariableManager);
            };

            var textareaDescription = TextareaDescription.newTextareaDescription(descriptionId)
                    .idProvider(new WidgetIdProvider())
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                    .labelProvider(new StringValueProvider(interpreter, viewTextAreaDescription.getLabelExpression()))
                    .isReadOnlyProvider(new ReadOnlyValueProvider(interpreter, viewTextAreaDescription.getIsEnabledExpression()))
                    .valueProvider(new StringValueProvider(interpreter, viewTextAreaDescription.getValueExpression()))
                    .newValueHandler(new NewValueHandler<>(interpreter, this.operationExecutor, this.feedbackMessageService, viewTextAreaDescription.getBody()))
                    .styleProvider(styleProvider)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewTextAreaDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewTextAreaDescription.getHelpExpression()))
                    .build();

            return Optional.of(textareaDescription);
        }
        return Optional.empty();
    }
}
