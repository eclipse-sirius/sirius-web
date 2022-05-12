/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.emf.view.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.utils.BooleanValueProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.view.OperationInterpreter;
import org.eclipse.sirius.components.forms.components.RadioComponent;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.MultiSelectDescription;
import org.eclipse.sirius.components.forms.description.RadioDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription.Builder;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.TextAreaDescription;
import org.eclipse.sirius.components.view.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.util.ViewSwitch;

/**
 * A switch to dispatch View Form Widget Descriptions conversion.
 *
 * @author fbarbin
 */
public class ViewFormDescriptionConverterSwitch extends ViewSwitch<AbstractWidgetDescription> {

    private final AQLInterpreter interpreter;

    private final IEditService editService;

    private final IObjectService objectService;

    public ViewFormDescriptionConverterSwitch(AQLInterpreter interpreter, IEditService editService, IObjectService objectService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public AbstractWidgetDescription caseTextfieldDescription(org.eclipse.sirius.components.view.TextfieldDescription viewTextfieldDescription) {
        String descriptionId = this.getDescriptionId(viewTextfieldDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewTextfieldDescription.getLabelExpression());
        StringValueProvider valueProvider = this.getStringValueProvider(viewTextfieldDescription.getValueExpression());
        BiFunction<VariableManager, String, IStatus> newValueHandler = this.getNewValueHandler(viewTextfieldDescription.getBody());
        TextfieldDescriptionStyle textfieldDescriptionStyle = viewTextfieldDescription.getStyle();

        // @formatter:off
        Builder textfieldBuilder = TextfieldDescription.newTextfieldDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> ""); //$NON-NLS-1$
        // @formatter:on

        if (textfieldDescriptionStyle != null) {
            TextfieldStyleProvider styleProvider = new TextfieldStyleProvider(textfieldDescriptionStyle);
            textfieldBuilder.styleProvider(styleProvider);
        }

        return textfieldBuilder.build();
    }

    @Override
    public AbstractWidgetDescription caseCheckboxDescription(org.eclipse.sirius.components.view.CheckboxDescription viewCheckboxDescription) {
        String descriptionId = this.getDescriptionId(viewCheckboxDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewCheckboxDescription.getLabelExpression());
        String valueExpression = Optional.ofNullable(viewCheckboxDescription.getValueExpression()).orElse(""); //$NON-NLS-1$
        BooleanValueProvider valueProvider = new BooleanValueProvider(this.interpreter, valueExpression);
        BiFunction<VariableManager, Boolean, IStatus> newValueHandler = this.getNewValueHandler(viewCheckboxDescription.getBody());

        // @formatter:off
        return CheckboxDescription.newCheckboxDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseSelectDescription(org.eclipse.sirius.components.view.SelectDescription viewSelectDescription) {
        String descriptionId = this.getDescriptionId(viewSelectDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewSelectDescription.getLabelExpression());
        Function<VariableManager, String> valueProvider = this.getSelectValueProvider(viewSelectDescription.getValueExpression());
        Function<VariableManager, String> optionIdProvider = this.getOptionIdProvider();
        StringValueProvider optionLabelProvider = this.getStringValueProvider(viewSelectDescription.getCandidateLabelExpression());
        Function<VariableManager, List<?>> optionsProvider = variableManager -> {
            String candidateExpression = viewSelectDescription.getCandidatesExpression();
            return this.interpreter.evaluateExpression(variableManager.getVariables(), candidateExpression).asObjects().orElse(new ArrayList<>());
        };
        BiFunction<VariableManager, String, IStatus> selectNewValueHandler = this.getSelectNewValueHandler(viewSelectDescription.getBody());
        // @formatter:off
        return SelectDescription.newSelectDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .valueProvider(valueProvider)
                .optionIdProvider(optionIdProvider)
                .optionLabelProvider(optionLabelProvider)
                .optionsProvider(optionsProvider)
                .newValueHandler(selectNewValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseTextAreaDescription(TextAreaDescription textAreaDescription) {
        String descriptionId = this.getDescriptionId(textAreaDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(textAreaDescription.getLabelExpression());
        StringValueProvider valueProvider = this.getStringValueProvider(textAreaDescription.getValueExpression());
        BiFunction<VariableManager, String, IStatus> newValueHandler = this.getNewValueHandler(textAreaDescription.getBody());

        // @formatter:off
        return TextareaDescription.newTextareaDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseMultiSelectDescription(org.eclipse.sirius.components.view.MultiSelectDescription multiSelectDescription) {
        String descriptionId = this.getDescriptionId(multiSelectDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(multiSelectDescription.getLabelExpression());
        Function<VariableManager, List<String>> valuesProvider = this.getMultiSelectValuesProvider(multiSelectDescription.getValueExpression());
        Function<VariableManager, String> optionIdProvider = this.getOptionIdProvider();
        StringValueProvider optionLabelProvider = this.getStringValueProvider(multiSelectDescription.getCandidateLabelExpression());
        Function<VariableManager, List<?>> optionsProvider = variableManager -> {
            String candidateExpression = multiSelectDescription.getCandidatesExpression();
            return this.interpreter.evaluateExpression(variableManager.getVariables(), candidateExpression).asObjects().orElse(new ArrayList<>());
        };
        BiFunction<VariableManager, List<String>, IStatus> multiSelectNewValueHandler = this.getMultiSelectNewValuesHandler(multiSelectDescription.getBody());
        // @formatter:off
        return MultiSelectDescription.newMultiSelectDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .valuesProvider(valuesProvider)
                .optionIdProvider(optionIdProvider)
                .optionLabelProvider(optionLabelProvider)
                .optionsProvider(optionsProvider)
                .newValuesHandler(multiSelectNewValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseRadioDescription(org.eclipse.sirius.components.view.RadioDescription radioDescription) {
        String descriptionId = this.getDescriptionId(radioDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(radioDescription.getLabelExpression());
        Function<VariableManager, String> optionIdProvider = this.getOptionIdProvider();
        StringValueProvider optionLabelProvider = this.getStringValueProvider(radioDescription.getCandidateLabelExpression());

        Function<VariableManager, Boolean> optionSelectedProvider = variableManager -> {
            Optional<Object> optionalResult = this.interpreter.evaluateExpression(variableManager.getVariables(), radioDescription.getValueExpression()).asObject();
            Object candidate = variableManager.getVariables().get(RadioComponent.CANDIDATE_VARIABLE);
            return optionalResult.map(candidate::equals).orElse(Boolean.FALSE);
        };

        Function<VariableManager, List<?>> optionsProvider = variableManager -> {
            Optional<List<Object>> optional = this.interpreter.evaluateExpression(variableManager.getVariables(), radioDescription.getCandidatesExpression()).asObjects();
            return optional.orElse(Collections.emptyList());
        };

        BiFunction<VariableManager, String, IStatus> newValueHandler = this.getSelectNewValueHandler(radioDescription.getBody());
        // @formatter:off
        return RadioDescription.newRadioDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .optionIdProvider(optionIdProvider)
                .optionLabelProvider(optionLabelProvider)
                .optionSelectedProvider(optionSelectedProvider)
                .optionsProvider(optionsProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .build();
        // @formatter:on

    }

    private Function<VariableManager, String> getOptionIdProvider() {
        return variableManager -> {
            Object candidate = variableManager.getVariables().get(SelectComponent.CANDIDATE_VARIABLE);
            return this.objectService.getId(candidate);
        };
    }

    private BiFunction<VariableManager, List<String>, IStatus> getMultiSelectNewValuesHandler(List<Operation> operations) {
        return (variableManager, newValue) -> {
            IStatus status = new Failure("An error occured while handling the new selected values."); //$NON-NLS-1$
            Optional<IEditingContext> optionalEditingDomain = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
            if (optionalEditingDomain.isPresent()) {
                IEditingContext editingContext = optionalEditingDomain.get();
                // @formatter:off
                List<Object> newValuesObjects = newValue.stream()
                        .map(currentValue -> this.objectService.getObject(editingContext, currentValue))
                        .flatMap(Optional::stream)
                        .collect(Collectors.toList());
                // @formatter:on
                variableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValuesObjects);
                OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
                Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
                if (optionalVariableManager.isEmpty()) {
                    status = new Failure("Something went wrong while handling the MultiSelect widget new values."); //$NON-NLS-1$
                } else {
                    status = new Success();
                }
            }
            return status;
        };
    }

    private Function<VariableManager, List<String>> getMultiSelectValuesProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse(""); //$NON-NLS-1$
        return variableManager -> {
            List<String> values = new ArrayList<>();
            if (!safeValueExpression.isBlank()) {
                Optional<List<Object>> optionalResult = this.interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression).asObjects();
                if (optionalResult.isPresent()) {
                    values = optionalResult.get().stream().map(this.objectService::getId).collect(Collectors.toList());
                }
            }
            return values;
        };
    }

    private StringValueProvider getStringValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse(""); //$NON-NLS-1$
        return new StringValueProvider(this.interpreter, safeValueExpression);
    }

    private Function<VariableManager, String> getSelectValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse(""); //$NON-NLS-1$
        return variableManager -> {
            if (!safeValueExpression.isBlank()) {
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression);
                return result.asObject().map(this.objectService::getId).orElse(""); //$NON-NLS-1$
            }
            return ""; //$NON-NLS-1$
        };
    }

    private <T> BiFunction<VariableManager, T, IStatus> getNewValueHandler(List<Operation> operations) {
        return (variableManager, newValue) -> {
            variableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValue);
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
            if (optionalVariableManager.isEmpty()) {
                return new Failure("Something went wrong while handling the widget new value."); //$NON-NLS-1$
            } else {
                return new Success();
            }
        };
    }

    private BiFunction<VariableManager, String, IStatus> getSelectNewValueHandler(List<Operation> operations) {
        return (variableManager, newValue) -> {
            IStatus status = new Failure("An error occured while handling the new selected value."); //$NON-NLS-1$
            Optional<Object> optionalNewValueObject = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                    .flatMap(editingContext -> this.objectService.getObject(editingContext, newValue));
            if (optionalNewValueObject.isPresent()) {
                variableManager.put(ViewFormDescriptionConverter.NEW_VALUE, optionalNewValueObject.get());
                OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
                Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
                if (optionalVariableManager.isEmpty()) {
                    status = new Failure("Something went wrong while handling the Select widget new value."); //$NON-NLS-1$
                } else {
                    status = new Success();
                }
            }
            return status;
        };
    }

    private String getDescriptionId(EObject description) {
        String descriptionURI = EcoreUtil.getURI(description).toString();
        return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    }
}
