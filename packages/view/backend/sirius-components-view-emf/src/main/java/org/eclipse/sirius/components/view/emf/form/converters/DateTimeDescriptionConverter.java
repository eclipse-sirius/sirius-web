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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.DateTimeStyle;
import org.eclipse.sirius.components.forms.DateTimeType;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.DateTimeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.emf.form.DateTimeStyleProvider;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;

/**
 * Used to convert date time descriptions.
 *
 * @author sbegaudeau
 */
public class DateTimeDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public DateTimeDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IEditService editService, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    public DateTimeDescription convert(org.eclipse.sirius.components.view.form.DateTimeDescription viewDateTimeDescription) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewDateTimeDescription);

        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValue);
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(viewDateTimeDescription.getBody(), childVariableManager);
            if (optionalVariableManager.isEmpty()) {
                List<Message> errorMessages = new ArrayList<>();
                errorMessages.add(new Message("Something went wrong while handling the widget operations execution.", MessageLevel.ERROR));
                errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
                return new Failure(errorMessages);
            }
            return new Success(this.feedbackMessageService.getFeedbackMessages());
        };

        Function<VariableManager, DateTimeStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewDateTimeDescription.getConditionalStyles().stream()
                    .filter(style -> this.interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
                            .asBoolean()
                            .orElse(Boolean.FALSE))
                    .map(DateTimeDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewDateTimeDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new DateTimeStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        return DateTimeDescription.newDateTimeDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .labelProvider(new StringValueProvider(this.interpreter, viewDateTimeDescription.getLabelExpression()))
                .isReadOnlyProvider(new ReadOnlyValueProvider(this.interpreter, viewDateTimeDescription.getIsEnabledExpression()))
                .type(DateTimeType.valueOf(viewDateTimeDescription.getType().getLiteral()))
                .stringValueProvider(new StringValueProvider(this.interpreter, viewDateTimeDescription.getStringValueExpression()))
                .newValueHandler(newValueHandler)
                .styleProvider(styleProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewDateTimeDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewDateTimeDescription.getHelpExpression()))
                .build();
    }
}
