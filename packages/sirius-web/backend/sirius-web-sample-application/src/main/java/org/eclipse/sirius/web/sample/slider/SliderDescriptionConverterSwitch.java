/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.sample.slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.web.customwidgets.SliderDescription;
import org.eclipse.sirius.web.customwidgets.util.CustomwidgetsSwitch;

/**
 * Converts a modeled Slider Description into its API equivalent.
 *
 * @author pcdavid
 */
public class SliderDescriptionConverterSwitch extends CustomwidgetsSwitch<AbstractWidgetDescription> {

    private final AQLInterpreter interpreter;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    public SliderDescriptionConverterSwitch(AQLInterpreter interpreter, IEditService editService, IFeedbackMessageService feedbackMessageService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    @Override
    public AbstractWidgetDescription caseSliderDescription(SliderDescription viewSliderDescription) {
        String descriptionId = this.getDescriptionId(viewSliderDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewSliderDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewSliderDescription.getIsEnabledExpression());
        Function<VariableManager, Integer> minValueProvider = this.getIntValueProvider(viewSliderDescription.getMinValueExpression());
        Function<VariableManager, Integer> maxValueProvider = this.getIntValueProvider(viewSliderDescription.getMaxValueExpression());
        Function<VariableManager, Integer> currentValueProvider = this.getIntValueProvider(viewSliderDescription.getCurrentValueExpression());
        Function<VariableManager, IStatus> newValueHandler = this.getOperationsHandler(viewSliderDescription.getBody());

        var builder = org.eclipse.sirius.web.sample.slider.SliderDescription.newSliderDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .minValueProvider(minValueProvider)
                .maxValueProvider(maxValueProvider)
                .currentValueProvider(currentValueProvider)
                .newValueHandler(newValueHandler);
        if (viewSliderDescription.getHelpExpression() != null && !viewSliderDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewSliderDescription.getHelpExpression()));
        }
        return builder.build();
    }

    private Function<VariableManager, Integer> getIntValueProvider(String intValueExpression) {
        String safeValueExpression = Optional.ofNullable(intValueExpression).orElse("");
        return variableManager -> {
            if (!safeValueExpression.isBlank()) {
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression);
                return result.asInt().orElse(0);
            }
            return 0;
        };
    }

    private Function<VariableManager, IStatus> getOperationsHandler(List<Operation> operations) {
        return variableManager -> {
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
            if (optionalVariableManager.isEmpty()) {
                List<Message> errorMessages = new ArrayList<>();
                errorMessages.add(new Message("Something went wrong while handling the widget operations execution.", MessageLevel.ERROR));
                errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
                return new Failure(errorMessages);
            } else {
                return new Success(this.feedbackMessageService.getFeedbackMessages());
            }
        };
    }

    private StringValueProvider getStringValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return new StringValueProvider(this.interpreter, safeValueExpression);
    }

    private String getDescriptionId(EObject description) {
        String descriptionURI = EcoreUtil.getURI(description).toString();
        return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    }

    private Function<VariableManager, Boolean> getReadOnlyValueProvider(String expression) {
        return variableManager -> {
            if (expression != null && !expression.isBlank()) {
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), expression);
                return result.asBoolean().map(value -> !value).orElse(Boolean.FALSE);
            }
            return Boolean.FALSE;
        };
    }
}
