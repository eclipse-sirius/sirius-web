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
package org.eclipse.sirius.components.view.emf.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.charts.barchart.BarChartDescription;
import org.eclipse.sirius.components.charts.descriptions.IChartDescription;
import org.eclipse.sirius.components.charts.piechart.PieChartDescription;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.utils.BooleanValueProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.CheckboxStyle;
import org.eclipse.sirius.components.forms.FlexDirection;
import org.eclipse.sirius.components.forms.MultiSelectStyle;
import org.eclipse.sirius.components.forms.RadioStyle;
import org.eclipse.sirius.components.forms.SelectStyle;
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.forms.TextfieldStyle;
import org.eclipse.sirius.components.forms.components.RadioComponent;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.FlexboxContainerDescription;
import org.eclipse.sirius.components.forms.description.MultiSelectDescription;
import org.eclipse.sirius.components.forms.description.RadioDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.TextAreaDescription;
import org.eclipse.sirius.components.view.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
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
        Function<VariableManager, TextfieldStyle> styleProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = viewTextfieldDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), variableManager))
                    .map(TextfieldDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewTextfieldDescription::getStyle);
            // @formatter:on
            if (effectiveStyle == null) {
                return null;
            }
            return new TextfieldStyleProvider(effectiveStyle).apply(variableManager);
        };

        // @formatter:off
        return TextfieldDescription.newTextfieldDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseCheckboxDescription(org.eclipse.sirius.components.view.CheckboxDescription viewCheckboxDescription) {
        String descriptionId = this.getDescriptionId(viewCheckboxDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewCheckboxDescription.getLabelExpression());
        String valueExpression = Optional.ofNullable(viewCheckboxDescription.getValueExpression()).orElse(""); //$NON-NLS-1$
        BooleanValueProvider valueProvider = new BooleanValueProvider(this.interpreter, valueExpression);
        BiFunction<VariableManager, Boolean, IStatus> newValueHandler = this.getNewValueHandler(viewCheckboxDescription.getBody());
        Function<VariableManager, CheckboxStyle> styleProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = viewCheckboxDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), variableManager))
                    .map(CheckboxDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewCheckboxDescription::getStyle);
            // @formatter:on
            if (effectiveStyle == null) {
                return null;
            }
            return new CheckboxStyleProvider(effectiveStyle).apply(variableManager);
        };

        // @formatter:off
        return CheckboxDescription.newCheckboxDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
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
        String candidateExpression = viewSelectDescription.getCandidatesExpression();
        Function<VariableManager, List<?>> optionsProvider = this.getMultiValueProvider(candidateExpression);
        BiFunction<VariableManager, String, IStatus> selectNewValueHandler = this.getSelectNewValueHandler(viewSelectDescription.getBody());
        Function<VariableManager, SelectStyle> styleProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = viewSelectDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), variableManager))
                    .map(SelectDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewSelectDescription::getStyle);
            // @formatter:on
            if (effectiveStyle == null) {
                return null;
            }
            return new SelectStyleProvider(effectiveStyle).apply(variableManager);
        };

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
                .styleProvider(styleProvider)
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
        Function<VariableManager, TextareaStyle> styleProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = textAreaDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), variableManager))
                    .map(TextareaDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(textAreaDescription::getStyle);
            // @formatter:on
            if (effectiveStyle == null) {
                return null;
            }
            return new TextareaStyleProvider(effectiveStyle).apply(variableManager);
        };

        // @formatter:off
        return TextareaDescription.newTextareaDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
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
        String candidateExpression = multiSelectDescription.getCandidatesExpression();
        Function<VariableManager, List<? extends Object>> optionsProvider = this.getMultiValueProvider(candidateExpression);
        BiFunction<VariableManager, List<String>, IStatus> multiSelectNewValueHandler = this.getMultiSelectNewValuesHandler(multiSelectDescription.getBody());
        Function<VariableManager, MultiSelectStyle> styleProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = multiSelectDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), variableManager))
                    .map(MultiSelectDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(multiSelectDescription::getStyle);
            // @formatter:on
            if (effectiveStyle == null) {
                return null;
            }
            return new MultiSelectStyleProvider(effectiveStyle).apply(variableManager);
        };

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
                .styleProvider(styleProvider)
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

        String candidatesExpression = radioDescription.getCandidatesExpression();
        Function<VariableManager, List<? extends Object>> optionsProvider = this.getMultiValueProvider(candidatesExpression);

        BiFunction<VariableManager, String, IStatus> newValueHandler = this.getSelectNewValueHandler(radioDescription.getBody());
        Function<VariableManager, RadioStyle> styleProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = radioDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), variableManager))
                    .map(RadioDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(radioDescription::getStyle);
            // @formatter:on
            if (effectiveStyle == null) {
                return null;
            }
            return new RadioStyleProvider(effectiveStyle).apply(variableManager);
        };

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
                .styleProvider(styleProvider)
                .build();
        // @formatter:on

    }

    @Override
    public AbstractWidgetDescription caseBarChartDescription(org.eclipse.sirius.components.view.BarChartDescription viewBarChartDescription) {
        String labelExpression = viewBarChartDescription.getYAxisLabelExpression();
        String keysExpression = viewBarChartDescription.getKeysExpression();
        String valuesExpression = viewBarChartDescription.getValuesExpression();
        // @formatter:off
        IChartDescription chartDescription = BarChartDescription.newBarChartDescription(this.getDescriptionId(viewBarChartDescription))
                .label(viewBarChartDescription.getName())
                .labelProvider(this.getStringValueProvider(labelExpression))
                .keysProvider(this.getMultiValueProvider(keysExpression, String.class))
                .valuesProvider(this.getMultiValueProvider(valuesExpression, Number.class))
                .build();
        // @formatter:on
        return this.createChartWidgetDescription(viewBarChartDescription, chartDescription);
    }

    @Override
    public AbstractWidgetDescription casePieChartDescription(org.eclipse.sirius.components.view.PieChartDescription viewPieChartDescription) {
        String keysExpression = viewPieChartDescription.getKeysExpression();
        String valuesExpression = viewPieChartDescription.getValuesExpression();
        // @formatter:off
        IChartDescription chartDescription =  PieChartDescription.newPieChartDescription(this.getDescriptionId(viewPieChartDescription))
                .label(viewPieChartDescription.getName())
                .keysProvider(this.getMultiValueProvider(keysExpression, String.class))
                .valuesProvider(this.getMultiValueProvider(valuesExpression, Number.class))
                .build();
        // @formatter:on
        return this.createChartWidgetDescription(viewPieChartDescription, chartDescription);
    }

    @Override
    public AbstractWidgetDescription caseFlexboxContainerDescription(org.eclipse.sirius.components.view.FlexboxContainerDescription flexboxContainerDescription) {
        String descriptionId = this.getDescriptionId(flexboxContainerDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(flexboxContainerDescription.getLabelExpression());
        FlexDirection flexDirection = FlexDirection.valueOf(flexboxContainerDescription.getFlexDirection().getName());
        List<AbstractWidgetDescription> children = new ArrayList<>();
        flexboxContainerDescription.getChildren().forEach(widget -> {
            children.add(ViewFormDescriptionConverterSwitch.this.doSwitch(widget));
        });

        // @formatter:off
        return FlexboxContainerDescription.newFlexboxContainerDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .flexDirection(flexDirection)
                .children(children)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseButtonDescription(org.eclipse.sirius.components.view.ButtonDescription viewButtonDescription) {
        String descriptionId = this.getDescriptionId(viewButtonDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewButtonDescription.getLabelExpression());
        StringValueProvider buttonLabelProvider = this.getStringValueProvider(viewButtonDescription.getButtonLabelExpression());
        StringValueProvider imageURLProvider = this.getStringValueProvider(viewButtonDescription.getImageExpression());
        Function<VariableManager, IStatus> pushButtonHandler = this.getOperationsHandler(viewButtonDescription.getBody());
        Function<VariableManager, ButtonStyle> styleProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = viewButtonDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), variableManager))
                    .map(ButtonDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewButtonDescription::getStyle);
            // @formatter:on
            if (effectiveStyle == null) {
                return null;
            }
            return new ButtonStyleProvider(effectiveStyle).apply(variableManager);
        };

        // @formatter:off
        return ButtonDescription.newButtonDescription(descriptionId)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .buttonLabelProvider(buttonLabelProvider)
                .imageURLProvider(imageURLProvider)
                .pushButtonHandler(pushButtonHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
    }

    private Function<VariableManager, List<?>> getMultiValueProvider(String expression) {
        String safeExpression = Optional.ofNullable(expression).orElse(""); //$NON-NLS-1$
        return variableManager -> {
            return this.interpreter.evaluateExpression(variableManager.getVariables(), safeExpression).asObjects().orElse(List.of());
        };
    }

    private <T> Function<VariableManager, List<T>> getMultiValueProvider(String expression, Class<T> type) {
        String safeExpression = Optional.ofNullable(expression).orElse(""); //$NON-NLS-1$
        return variableManager -> {
            return this.interpreter.evaluateExpression(variableManager.getVariables(), safeExpression).asObjects().orElse(List.of()).stream().map(type::cast).collect(Collectors.toList());
        };
    }

    private AbstractWidgetDescription createChartWidgetDescription(org.eclipse.sirius.components.view.WidgetDescription widgetDescription, IChartDescription chartDescription) {
        String descriptionId = this.getDescriptionId(widgetDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(widgetDescription.getLabelExpression());

        // @formatter:off
        return ChartWidgetDescription.newChartWidgetDescription(descriptionId)
                .labelProvider(labelProvider)
                .idProvider(idProvider)
                .chartDescription(chartDescription)
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
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValuesObjects);
                OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
                Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
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

    private Function<VariableManager, IStatus> getOperationsHandler(List<Operation> operations) {
        return (variableManager) -> {
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
            if (optionalVariableManager.isEmpty()) {
                return new Failure("Something went wrong while handling the widget operations execution."); //$NON-NLS-1$
            } else {
                return new Success();
            }
        };
    }

    private <T> BiFunction<VariableManager, T, IStatus> getNewValueHandler(List<Operation> operations) {
        return (variableManager, newValue) -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValue);
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
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

            Object newValueObject = null;
            if (newValue != null && !newValue.isBlank()) {
                newValueObject = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).flatMap(editingContext -> this.objectService.getObject(editingContext, newValue))
                        .orElse(null);
            }
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValueObject);
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
            if (optionalVariableManager.isEmpty()) {
                status = new Failure("Something went wrong while handling the Select widget new value."); //$NON-NLS-1$
            } else {
                status = new Success();
            }
            return status;
        };
    }

    private String getDescriptionId(EObject description) {
        String descriptionURI = EcoreUtil.getURI(description).toString();
        return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    }

    private boolean matches(String condition, VariableManager variableManager) {
        return this.interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }
}
