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
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.SliderDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.IntValueProvider;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;

/**
 * Used to convert slider descriptions.
 *
 * @author sbegaudeau
 */
public class SliderDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public SliderDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IEditService editService, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    public SliderDescription convert(org.eclipse.sirius.components.view.form.SliderDescription viewSliderDescription) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewSliderDescription);

        Function<VariableManager, Integer> minValueProvider = new IntValueProvider(this.interpreter, viewSliderDescription.getMinValueExpression());
        Function<VariableManager, Integer> maxValueProvider = new IntValueProvider(this.interpreter, viewSliderDescription.getMaxValueExpression());
        Function<VariableManager, Integer> currentValueProvider = new IntValueProvider(this.interpreter, viewSliderDescription.getCurrentValueExpression());
        Function<VariableManager, IStatus> newValueHandler = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(viewSliderDescription.getBody(), childVariableManager);
            if (optionalVariableManager.isEmpty()) {
                List<Message> errorMessages = new ArrayList<>();
                errorMessages.add(new Message("Something went wrong while handling the widget operations execution.", MessageLevel.ERROR));
                errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
                return new Failure(errorMessages);
            }
            return new Success(this.feedbackMessageService.getFeedbackMessages());
        };

        return SliderDescription.newSliderDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .labelProvider(new StringValueProvider(this.interpreter, viewSliderDescription.getLabelExpression()))
                .isReadOnlyProvider(new ReadOnlyValueProvider(this.interpreter, viewSliderDescription.getIsEnabledExpression()))
                .minValueProvider(minValueProvider)
                .maxValueProvider(maxValueProvider)
                .currentValueProvider(currentValueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewSliderDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewSliderDescription.getHelpExpression()))
                .build();
    }
}
