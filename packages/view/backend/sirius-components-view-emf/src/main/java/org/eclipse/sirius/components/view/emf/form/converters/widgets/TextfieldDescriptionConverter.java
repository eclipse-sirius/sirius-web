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

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.forms.TextfieldStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.TextfieldStyleProvider;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.NewValueHandler;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert textfields.
 *
 * @author sbegaudeau
 */
@Service
public class TextfieldDescriptionConverter implements IWidgetDescriptionConverter {

    private final IIdentityService identityService;

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public TextfieldDescriptionConverter(IIdentityService identityService, IReadOnlyObjectPredicate readOnlyObjectPredicate, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.TextfieldDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.TextfieldDescription viewTextfieldDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewTextfieldDescription);

            Function<VariableManager, TextfieldStyle> styleProvider = variableManager -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                var effectiveStyle = viewTextfieldDescription.getConditionalStyles().stream()
                        .filter(style -> interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
                                .asBoolean()
                                .orElse(Boolean.FALSE))
                        .map(TextfieldDescriptionStyle.class::cast)
                        .findFirst()
                        .orElseGet(viewTextfieldDescription::getStyle);
                if (effectiveStyle == null) {
                    return null;
                }
                return new TextfieldStyleProvider(effectiveStyle).apply(childVariableManager);
            };

            var textfieldDescription = TextfieldDescription.newTextfieldDescription(descriptionId)
                    .idProvider(new WidgetIdProvider())
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.identityService))
                    .labelProvider(new StringValueProvider(interpreter, viewTextfieldDescription.getLabelExpression()))
                    .isReadOnlyProvider(new ReadOnlyValueProvider(this.readOnlyObjectPredicate, interpreter, viewTextfieldDescription.getIsEnabledExpression()))
                    .valueProvider(new StringValueProvider(interpreter, viewTextfieldDescription.getValueExpression()))
                    .newValueHandler(new NewValueHandler<>(interpreter, this.operationExecutor, this.feedbackMessageService, viewTextfieldDescription.getBody()))
                    .styleProvider(styleProvider)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewTextfieldDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewTextfieldDescription.getHelpExpression()))
                    .build();

            return Optional.of(textfieldDescription);
        }
        return Optional.empty();
    }

}
