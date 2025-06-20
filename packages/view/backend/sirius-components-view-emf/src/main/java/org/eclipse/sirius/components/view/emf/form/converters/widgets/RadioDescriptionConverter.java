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
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.forms.RadioStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.RadioDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.RadioStyleProvider;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.MultiValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.OptionIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.OptionSelectedProvider;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.SelectNewValueHandler;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.form.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert radio widgets.
 *
 * @author sbegaudeau
 */
@Service
public class RadioDescriptionConverter implements IWidgetDescriptionConverter {

    private final IIdentityService identityService;

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final IObjectSearchService objectSearchService;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public RadioDescriptionConverter(IIdentityService identityService, IReadOnlyObjectPredicate readOnlyObjectPredicate, IObjectSearchService objectSearchService, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.RadioDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.RadioDescription viewRadioDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewRadioDescription);

            Function<VariableManager, RadioStyle> styleProvider = variableManager -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                var effectiveStyle = viewRadioDescription.getConditionalStyles().stream()
                        .filter(style -> interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
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

            var radioDescription = RadioDescription.newRadioDescription(descriptionId)
                    .idProvider(new WidgetIdProvider())
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.identityService))
                    .labelProvider(new StringValueProvider(interpreter, viewRadioDescription.getLabelExpression()))
                    .isReadOnlyProvider(new ReadOnlyValueProvider(this.readOnlyObjectPredicate, interpreter, viewRadioDescription.getIsEnabledExpression()))
                    .optionsProvider(new MultiValueProvider(interpreter, viewRadioDescription.getCandidatesExpression(), Object.class))
                    .optionIdProvider(new OptionIdProvider(this.identityService))
                    .optionLabelProvider(new StringValueProvider(interpreter, viewRadioDescription.getCandidateLabelExpression()))
                    .optionSelectedProvider(new OptionSelectedProvider(interpreter, viewRadioDescription.getValueExpression()))
                    .newValueHandler(new SelectNewValueHandler(interpreter, this.objectSearchService, this.operationExecutor, this.feedbackMessageService, viewRadioDescription.getBody()))
                    .styleProvider(styleProvider)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewRadioDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewRadioDescription.getHelpExpression()))
                    .build();

            return Optional.of(radioDescription);
        }
        return Optional.empty();
    }
}
