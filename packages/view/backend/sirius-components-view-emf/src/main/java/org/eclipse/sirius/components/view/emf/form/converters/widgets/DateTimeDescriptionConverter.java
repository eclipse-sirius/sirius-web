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

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter.VARIABLE_MANAGER;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.forms.DateTimeStyle;
import org.eclipse.sirius.components.forms.DateTimeType;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.DateTimeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.DateTimeStyleProvider;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert date time descriptions.
 *
 * @author sbegaudeau
 */
@Service
public class DateTimeDescriptionConverter implements IWidgetDescriptionConverter {

    private final IIdentityService identityService;

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public DateTimeDescriptionConverter(IIdentityService identityService, IReadOnlyObjectPredicate readOnlyObjectPredicate, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.DateTimeDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.DateTimeDescription viewDateTimeDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewDateTimeDescription);

            BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValue);
                childVariableManager.put(VARIABLE_MANAGER, variableManager);

                var result = this.operationExecutor.execute(interpreter, childVariableManager, viewDateTimeDescription.getBody());
                if (result.status() == OperationExecutionStatus.FAILURE) {
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
                        .filter(style -> interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
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

            var dateTimeDescription = DateTimeDescription.newDateTimeDescription(descriptionId)
                    .idProvider(new WidgetIdProvider())
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.identityService))
                    .labelProvider(new StringValueProvider(interpreter, viewDateTimeDescription.getLabelExpression()))
                    .isReadOnlyProvider(new ReadOnlyValueProvider(this.readOnlyObjectPredicate, interpreter, viewDateTimeDescription.getIsEnabledExpression()))
                    .type(DateTimeType.valueOf(viewDateTimeDescription.getType().getLiteral()))
                    .stringValueProvider(new StringValueProvider(interpreter, viewDateTimeDescription.getStringValueExpression()))
                    .newValueHandler(newValueHandler)
                    .styleProvider(styleProvider)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewDateTimeDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewDateTimeDescription.getHelpExpression()))
                    .build();

            return Optional.of(dateTimeDescription);
        }
        return Optional.empty();
    }
}
