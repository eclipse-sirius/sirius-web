/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle;
import org.eclipse.sirius.components.charts.barchart.descriptions.BarChartDescription;
import org.eclipse.sirius.components.charts.descriptions.IChartDescription;
import org.eclipse.sirius.components.charts.piechart.PieChartDescription;
import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.CheckboxStyle;
import org.eclipse.sirius.components.forms.ContainerBorderStyle;
import org.eclipse.sirius.components.forms.DateTimeStyle;
import org.eclipse.sirius.components.forms.DateTimeType;
import org.eclipse.sirius.components.forms.FlexDirection;
import org.eclipse.sirius.components.forms.LabelWidgetStyle;
import org.eclipse.sirius.components.forms.LinkStyle;
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.forms.MultiSelectStyle;
import org.eclipse.sirius.components.forms.RadioStyle;
import org.eclipse.sirius.components.forms.SelectStyle;
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.forms.TextfieldStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.components.ListComponent;
import org.eclipse.sirius.components.forms.components.RadioComponent;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.DateTimeDescription;
import org.eclipse.sirius.components.forms.description.FlexboxContainerDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.forms.description.ImageDescription;
import org.eclipse.sirius.components.forms.description.LabelDescription;
import org.eclipse.sirius.components.forms.description.LinkDescription;
import org.eclipse.sirius.components.forms.description.ListDescription;
import org.eclipse.sirius.components.forms.description.MultiSelectDescription;
import org.eclipse.sirius.components.forms.description.RadioDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.forms.description.SliderDescription;
import org.eclipse.sirius.components.forms.description.SplitButtonDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.BooleanValueProvider;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.form.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.form.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.form.RichTextDescription;
import org.eclipse.sirius.components.view.form.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.TextAreaDescription;
import org.eclipse.sirius.components.view.form.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.view.form.util.FormSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A switch to dispatch View Form Widget Descriptions conversion.
 *
 * @author fbarbin
 */
public class ViewFormDescriptionConverterSwitch extends FormSwitch<Optional<AbstractControlDescription>> {

    private static final  String VARIABLE_MANAGER = "variableManager";

    private final Logger logger = LoggerFactory.getLogger(ViewFormDescriptionConverterSwitch.class);

    private final AQLInterpreter interpreter;

    private final IEditService editService;

    private final IObjectService objectService;

    private final Switch<Optional<AbstractWidgetDescription>> customWidgetConverters;

    private final IFeedbackMessageService feedbackMessageService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final IFormIdProvider widgetIdProvider;

    public ViewFormDescriptionConverterSwitch(AQLInterpreter interpreter, IEditService editService, IObjectService objectService, Switch<Optional<AbstractWidgetDescription>> customWidgetConverters, IFeedbackMessageService feedbackMessageService, IFormIdProvider idProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.objectService = Objects.requireNonNull(objectService);
        this.customWidgetConverters = Objects.requireNonNull(customWidgetConverters);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null);
        this.widgetIdProvider = Objects.requireNonNull(idProvider);
    }

    @Override
    public Optional<AbstractControlDescription> caseTextfieldDescription(org.eclipse.sirius.components.view.form.TextfieldDescription viewTextfieldDescription) {
        String descriptionId = this.getDescriptionId(viewTextfieldDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewTextfieldDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewTextfieldDescription.getIsEnabledExpression());
        StringValueProvider valueProvider = this.getStringValueProvider(viewTextfieldDescription.getValueExpression());
        BiFunction<VariableManager, String, IStatus> newValueHandler = this.getNewValueHandler(viewTextfieldDescription.getBody());
        Function<VariableManager, TextfieldStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewTextfieldDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(TextfieldDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewTextfieldDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new TextfieldStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        TextfieldDescription.Builder builder = TextfieldDescription.newTextfieldDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .styleProvider(styleProvider);

        if (viewTextfieldDescription.getHelpExpression() != null && !viewTextfieldDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewTextfieldDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseCheckboxDescription(org.eclipse.sirius.components.view.form.CheckboxDescription viewCheckboxDescription) {
        String descriptionId = this.getDescriptionId(viewCheckboxDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewCheckboxDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewCheckboxDescription.getIsEnabledExpression());
        String valueExpression = Optional.ofNullable(viewCheckboxDescription.getValueExpression()).orElse("");
        BooleanValueProvider valueProvider = new BooleanValueProvider(this.interpreter, valueExpression);
        BiFunction<VariableManager, Boolean, IStatus> newValueHandler = this.getNewValueHandler(viewCheckboxDescription.getBody());
        Function<VariableManager, CheckboxStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewCheckboxDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(CheckboxDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewCheckboxDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new CheckboxStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        CheckboxDescription.Builder builder = CheckboxDescription.newCheckboxDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .styleProvider(styleProvider);
        if (viewCheckboxDescription.getHelpExpression() != null && !viewCheckboxDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewCheckboxDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseSelectDescription(org.eclipse.sirius.components.view.form.SelectDescription viewSelectDescription) {
        String descriptionId = this.getDescriptionId(viewSelectDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewSelectDescription.getLabelExpression());
        Function<VariableManager, List<String>> optionIconURLProvider = this.getOptionIconURLProvider();
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewSelectDescription.getIsEnabledExpression());
        Function<VariableManager, String> valueProvider = this.getSelectValueProvider(viewSelectDescription.getValueExpression());
        Function<VariableManager, String> optionIdProvider = this.getOptionIdProvider();
        StringValueProvider optionLabelProvider = this.getStringValueProvider(viewSelectDescription.getCandidateLabelExpression());
        String candidateExpression = viewSelectDescription.getCandidatesExpression();
        Function<VariableManager, List<?>> optionsProvider = this.getMultiValueProvider(candidateExpression);
        BiFunction<VariableManager, String, IStatus> selectNewValueHandler = this.getSelectNewValueHandler(viewSelectDescription.getBody());
        Function<VariableManager, SelectStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewSelectDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(SelectDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewSelectDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new SelectStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        SelectDescription.Builder builder = SelectDescription.newSelectDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .valueProvider(valueProvider)
                .optionIdProvider(optionIdProvider)
                .optionLabelProvider(optionLabelProvider)
                .optionsProvider(optionsProvider)
                .optionIconURLProvider(optionIconURLProvider)
                .newValueHandler(selectNewValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .styleProvider(styleProvider);
        if (viewSelectDescription.getHelpExpression() != null && !viewSelectDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewSelectDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseTextAreaDescription(TextAreaDescription textAreaDescription) {
        String descriptionId = this.getDescriptionId(textAreaDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(textAreaDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(textAreaDescription.getIsEnabledExpression());
        StringValueProvider valueProvider = this.getStringValueProvider(textAreaDescription.getValueExpression());
        BiFunction<VariableManager, String, IStatus> newValueHandler = this.getNewValueHandler(textAreaDescription.getBody());
        Function<VariableManager, TextareaStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = textAreaDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(TextareaDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(textAreaDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new TextareaStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        TextareaDescription.Builder builder = TextareaDescription.newTextareaDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .styleProvider(styleProvider);
        if (textAreaDescription.getHelpExpression() != null && !textAreaDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(textAreaDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseRichTextDescription(RichTextDescription richTextDescription) {
        String descriptionId = this.getDescriptionId(richTextDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(richTextDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(richTextDescription.getIsEnabledExpression());
        StringValueProvider valueProvider = this.getStringValueProvider(richTextDescription.getValueExpression());
        BiFunction<VariableManager, String, IStatus> newValueHandler = this.getNewValueHandler(richTextDescription.getBody());

        org.eclipse.sirius.components.forms.description.RichTextDescription.Builder builder = org.eclipse.sirius.components.forms.description.RichTextDescription.newRichTextDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "");
        if (richTextDescription.getHelpExpression() != null && !richTextDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(richTextDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseMultiSelectDescription(org.eclipse.sirius.components.view.form.MultiSelectDescription multiSelectDescription) {
        String descriptionId = this.getDescriptionId(multiSelectDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(multiSelectDescription.getLabelExpression());
        Function<VariableManager, List<String>> optionIconURLProvider = this.getOptionIconURLProvider();
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(multiSelectDescription.getIsEnabledExpression());
        Function<VariableManager, List<String>> valuesProvider = this.getMultiSelectValuesProvider(multiSelectDescription.getValueExpression());
        Function<VariableManager, String> optionIdProvider = this.getOptionIdProvider();
        StringValueProvider optionLabelProvider = this.getStringValueProvider(multiSelectDescription.getCandidateLabelExpression());
        String candidateExpression = multiSelectDescription.getCandidatesExpression();
        Function<VariableManager, List<? extends Object>> optionsProvider = this.getMultiValueProvider(candidateExpression);
        BiFunction<VariableManager, List<String>, IStatus> multiSelectNewValueHandler = this.getMultiSelectNewValuesHandler(multiSelectDescription.getBody());
        Function<VariableManager, MultiSelectStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = multiSelectDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(MultiSelectDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(multiSelectDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new MultiSelectStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        MultiSelectDescription.Builder builder = MultiSelectDescription.newMultiSelectDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .valuesProvider(valuesProvider)
                .optionIdProvider(optionIdProvider)
                .optionLabelProvider(optionLabelProvider)
                .optionIconURLProvider(optionIconURLProvider)
                .optionsProvider(optionsProvider)
                .newValuesHandler(multiSelectNewValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .styleProvider(styleProvider);
        if (multiSelectDescription.getHelpExpression() != null && !multiSelectDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(multiSelectDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseRadioDescription(org.eclipse.sirius.components.view.form.RadioDescription radioDescription) {
        String descriptionId = this.getDescriptionId(radioDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(radioDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(radioDescription.getIsEnabledExpression());
        Function<VariableManager, String> optionIdProvider = this.getOptionIdProvider();
        StringValueProvider optionLabelProvider = this.getStringValueProvider(radioDescription.getCandidateLabelExpression());

        Function<VariableManager, Boolean> optionSelectedProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            Optional<Object> optionalResult = this.interpreter.evaluateExpression(childVariableManager.getVariables(), radioDescription.getValueExpression()).asObject();
            Object candidate = variableManager.getVariables().get(RadioComponent.CANDIDATE_VARIABLE);
            return optionalResult.map(candidate::equals).orElse(Boolean.FALSE);
        };

        String candidatesExpression = radioDescription.getCandidatesExpression();
        Function<VariableManager, List<? extends Object>> optionsProvider = this.getMultiValueProvider(candidatesExpression);

        BiFunction<VariableManager, String, IStatus> newValueHandler = this.getSelectNewValueHandler(radioDescription.getBody());
        Function<VariableManager, RadioStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = radioDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(RadioDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(radioDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new RadioStyleProvider(effectiveStyle).apply(variableManager);
        };

        RadioDescription.Builder builder = RadioDescription.newRadioDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .optionIdProvider(optionIdProvider)
                .optionLabelProvider(optionLabelProvider)
                .optionSelectedProvider(optionSelectedProvider)
                .optionsProvider(optionsProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (radioDescription.getHelpExpression() != null && !radioDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(radioDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseBarChartDescription(org.eclipse.sirius.components.view.form.BarChartDescription viewBarChartDescription) {
        String labelExpression = viewBarChartDescription.getYAxisLabelExpression();
        String keysExpression = viewBarChartDescription.getKeysExpression();
        String valuesExpression = viewBarChartDescription.getValuesExpression();
        Function<VariableManager, BarChartStyle> styleProvider = new BarChartStyleProvider(this.interpreter, viewBarChartDescription);

        if (labelExpression == null || keysExpression == null || valuesExpression == null) {
            return Optional.empty();
        }

        BarChartDescription.Builder builder = BarChartDescription.newBarChartDescription(this.getDescriptionId(viewBarChartDescription))
                .label(viewBarChartDescription.getName())
                .labelProvider(this.getStringValueProvider(labelExpression))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .keysProvider(this.getMultiValueProvider(keysExpression, String.class))
                .valuesProvider(this.getMultiValueProvider(valuesExpression, Number.class))
                .styleProvider(styleProvider)
                .width(viewBarChartDescription.getWidth())
                .height(viewBarChartDescription.getHeight());
        return Optional.of(this.createChartWidgetDescription(viewBarChartDescription, builder.build()));
    }

    @Override
    public Optional<AbstractControlDescription> casePieChartDescription(org.eclipse.sirius.components.view.form.PieChartDescription viewPieChartDescription) {
        String keysExpression = viewPieChartDescription.getKeysExpression();
        String valuesExpression = viewPieChartDescription.getValuesExpression();

        if (keysExpression == null || valuesExpression == null) {
            return Optional.empty();
        }

        Function<VariableManager, PieChartStyle> styleProvider = new PieChartStyleProvider(this.interpreter, viewPieChartDescription);
        // @formatter:off
        IChartDescription chartDescription =  PieChartDescription.newPieChartDescription(this.getDescriptionId(viewPieChartDescription))
                .label(viewPieChartDescription.getName())
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .keysProvider(this.getMultiValueProvider(keysExpression, String.class))
                .valuesProvider(this.getMultiValueProvider(valuesExpression, Number.class))
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
        return Optional.of(this.createChartWidgetDescription(viewPieChartDescription, chartDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseFlexboxContainerDescription(org.eclipse.sirius.components.view.form.FlexboxContainerDescription flexboxContainerDescription) {
        String descriptionId = this.getDescriptionId(flexboxContainerDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(flexboxContainerDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(flexboxContainerDescription.getIsEnabledExpression());
        FlexDirection flexDirection = FlexDirection.valueOf(flexboxContainerDescription.getFlexDirection().getName());
        Function<VariableManager, ContainerBorderStyle> borderStyleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = flexboxContainerDescription.getConditionalBorderStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(org.eclipse.sirius.components.view.form.ContainerBorderStyle.class::cast)
                    .findFirst()
                    .orElseGet(flexboxContainerDescription::getBorderStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ContainerBorderStyleProvider(effectiveStyle).apply(childVariableManager);
        };
        List<Optional<AbstractControlDescription>> children = new ArrayList<>();
        flexboxContainerDescription.getChildren().forEach(widget -> children.add(ViewFormDescriptionConverterSwitch.this.doSwitch(widget)));

        FlexboxContainerDescription.Builder builder = FlexboxContainerDescription.newFlexboxContainerDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .flexDirection(flexDirection)
                .children(children.stream().filter(Optional::isPresent).map(Optional::get).toList())
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .borderStyleProvider(borderStyleProvider);
        if (flexboxContainerDescription.getHelpExpression() != null && !flexboxContainerDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(flexboxContainerDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseButtonDescription(org.eclipse.sirius.components.view.form.ButtonDescription viewButtonDescription) {
        String descriptionId = this.getDescriptionId(viewButtonDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewButtonDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewButtonDescription.getIsEnabledExpression());
        StringValueProvider buttonLabelProvider = this.getStringValueProvider(viewButtonDescription.getButtonLabelExpression());
        StringValueProvider imageURLProvider = this.getStringValueProvider(viewButtonDescription.getImageExpression());
        Function<VariableManager, IStatus> pushButtonHandler = this.getOperationsHandler(viewButtonDescription.getBody());
        Function<VariableManager, ButtonStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewButtonDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(ButtonDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewButtonDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ButtonStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        ButtonDescription.Builder builder = ButtonDescription.newButtonDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .buttonLabelProvider(buttonLabelProvider)
                .imageURLProvider(imageURLProvider)
                .pushButtonHandler(pushButtonHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .styleProvider(styleProvider);
        if (viewButtonDescription.getHelpExpression() != null && !viewButtonDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewButtonDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseSplitButtonDescription(org.eclipse.sirius.components.view.form.SplitButtonDescription splitButtonDescription) {
        String descriptionId = this.getDescriptionId(splitButtonDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(splitButtonDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(splitButtonDescription.getIsEnabledExpression());

        List<ButtonDescription> actions = splitButtonDescription.getActions().stream()
                .map(ViewFormDescriptionConverterSwitch.this::doSwitch)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(ButtonDescription.class::isInstance)
                .map(ButtonDescription.class::cast).toList();

        if (actions.isEmpty()) {
            this.logger.warn("Invalid empty Reference Actions on widget {}", splitButtonDescription.getName());
            return Optional.empty();
        }

        SplitButtonDescription.Builder builder = SplitButtonDescription.newSplitButtonDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .diagnosticsProvider(variableManager -> List.of())
                .actions(actions)
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "");

        if (splitButtonDescription.getHelpExpression() != null && !splitButtonDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(splitButtonDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseLabelDescription(org.eclipse.sirius.components.view.form.LabelDescription viewLabelDescription) {
        String descriptionId = this.getDescriptionId(viewLabelDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewLabelDescription.getLabelExpression());
        StringValueProvider valueProvider = this.getStringValueProvider(viewLabelDescription.getValueExpression());
        Function<VariableManager, LabelWidgetStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewLabelDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(LabelDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewLabelDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new LabelStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        LabelDescription.Builder builder = LabelDescription.newLabelDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .valueProvider(valueProvider)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .styleProvider(styleProvider);
        if (viewLabelDescription.getHelpExpression() != null && !viewLabelDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewLabelDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseLinkDescription(org.eclipse.sirius.components.view.form.LinkDescription viewLinkDescription) {
        String descriptionId = this.getDescriptionId(viewLinkDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewLinkDescription.getLabelExpression());
        StringValueProvider valueProvider = this.getStringValueProvider(viewLinkDescription.getValueExpression());
        Function<VariableManager, LinkStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewLinkDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(LinkDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewLinkDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new LinkStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        LinkDescription.Builder builder = LinkDescription.newLinkDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .urlProvider(valueProvider)
                .styleProvider(styleProvider)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "");
        if (viewLinkDescription.getHelpExpression() != null && !viewLinkDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewLinkDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseListDescription(org.eclipse.sirius.components.view.form.ListDescription viewListDescription) {
        String descriptionId = this.getDescriptionId(viewListDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewListDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewListDescription.getIsEnabledExpression());
        Function<VariableManager, List<?>> valueProvider = this.getMultiValueProvider(viewListDescription.getValueExpression());
        StringValueProvider displayProvider = this.getStringValueProvider(viewListDescription.getDisplayExpression());
        BooleanValueProvider isDeletableProvider = this.getBooleanValueProvider(viewListDescription.getIsDeletableExpression());
        Function<VariableManager, String> itemIdProvider = this::getItemId;
        Function<VariableManager, String> itemKindProvider = this::getKind;
        Function<VariableManager, IStatus> itemDeleteHandlerProvider = this::handleItemDeletion;
        Function<VariableManager, IStatus> itemClickHandlerProvider = variableManager -> this.getListItemClickHandler(variableManager, viewListDescription.getBody());
        Function<VariableManager, List<String>> itemIconURLProvider = this::getListItemIconURL;

        Function<VariableManager, ListStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewListDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(ListDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewListDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ListStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        ListDescription.Builder builder = ListDescription.newListDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .itemsProvider(valueProvider)
                .itemKindProvider(itemKindProvider)
                .itemDeleteHandlerProvider(itemDeleteHandlerProvider)
                .itemIconURLProvider(itemIconURLProvider)
                .itemIdProvider(itemIdProvider)
                .itemLabelProvider(displayProvider)
                .itemDeletableProvider(isDeletableProvider)
                .itemClickHandlerProvider(itemClickHandlerProvider)
                .styleProvider(styleProvider)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "");
        if (viewListDescription.getHelpExpression() != null && !viewListDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewListDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseImageDescription(org.eclipse.sirius.components.view.form.ImageDescription imageDescription) {
        String descriptionId = this.getDescriptionId(imageDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(imageDescription.getLabelExpression());
        StringValueProvider urlProvider = this.getStringValueProvider(imageDescription.getUrlExpression());
        StringValueProvider maxWidthProvider = this.getStringValueProvider(imageDescription.getMaxWidthExpression());
        ImageDescription.Builder builder = ImageDescription.newImageDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .urlProvider(urlProvider)
                .maxWidthProvider(maxWidthProvider)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "");
        if (imageDescription.getHelpExpression() != null && !imageDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(imageDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<AbstractControlDescription> caseFormElementFor(FormElementFor formElementFor) {
        String forDescriptionId = this.getDescriptionId(formElementFor);

        Function<VariableManager, List<?>> iterableProvider = variableManager -> {
            String safeIterabeExpression = Optional.ofNullable(formElementFor.getIterableExpression()).orElse("");
            if (!safeIterabeExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Result result = this.interpreter.evaluateExpression(childVariableManager.getVariables(), safeIterabeExpression);
                return result.asObjects().orElse(List.of());
            } else {
                return List.of();
            }
        };

        List<Optional<AbstractControlDescription>> controlDescriptions = formElementFor.getChildren().stream().map(this::doSwitch).toList();

        return Optional.of(ForDescription.newForDescription(forDescriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .iterator(formElementFor.getIterator())
                .iterableProvider(iterableProvider)
                .controlDescriptions(controlDescriptions.stream().filter(Optional::isPresent).map(Optional::get).toList())
                .build());
    }

    @Override
    public Optional<AbstractControlDescription> caseFormElementIf(FormElementIf formElementIf) {
        String ifDescriptionId = this.getDescriptionId(formElementIf);
        return Optional.of(IfDescription.newIfDescription(ifDescriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .predicate(new BooleanValueProvider(this.interpreter, formElementIf.getPredicateExpression()))
                .controlDescriptions(formElementIf.getChildren().stream().map(this::doSwitch).filter(Optional::isPresent).map(Optional::get).toList())
                .build());
    }

    @Override
    public Optional<AbstractControlDescription> caseTreeDescription(org.eclipse.sirius.components.view.form.TreeDescription treeDescription) {
        String descriptionId = this.getDescriptionId(treeDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(treeDescription.getLabelExpression());
        StringValueProvider itemLabelProvider = this.getStringValueProvider(treeDescription.getTreeItemLabelExpression());
        Function<VariableManager, String> nodeIdProvider = this::getTreeItemId;
        Function<VariableManager, String> itemKindProvider = this::getTreeItemKind;
        Function<VariableManager, List<String>> nodeIconURLProvider = this.getTreeBeginIconValue(treeDescription.getTreeItemBeginIconExpression());
        Function<VariableManager, List<List<String>>> nodeIconEndURLProvider = this.getTreeEndIconValue(treeDescription.getTreeItemEndIconsExpression());
        Function<VariableManager, List<? extends Object>> childrenProvider = this.getMultiValueProvider(treeDescription.getChildrenExpression());
        Function<VariableManager, Boolean> nodeSelectableProvider = this.getBooleanValueProvider(treeDescription.getIsTreeItemSelectableExpression());
        Function<VariableManager, Boolean> nodeCheckbableProvider = this.getBooleanValueProvider(treeDescription.getIsCheckableExpression());
        String valueExpression = Optional.ofNullable(treeDescription.getCheckedValueExpression()).orElse("");
        BooleanValueProvider valueProvider = new BooleanValueProvider(this.interpreter, valueExpression);
        BiFunction<VariableManager, Boolean, IStatus> newValueHandler = this.getNewValueHandler(treeDescription.getBody());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(treeDescription.getIsEnabledExpression());

        var builder = TreeDescription.newTreeDescription(descriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .iconURLProvider(vm -> List.of())
                .childrenProvider(childrenProvider)
                .nodeIdProvider(nodeIdProvider)
                .nodeLabelProvider(itemLabelProvider)
                .nodeKindProvider(itemKindProvider)
                .nodeIconURLProvider(nodeIconURLProvider)
                .nodeEndIconsURLProvider(nodeIconEndURLProvider)
                .nodeSelectableProvider(nodeSelectableProvider)
                .isCheckableProvider(nodeCheckbableProvider)
                .checkedValueProvider(valueProvider)
                .newCheckedValueHandler(newValueHandler)
                .expandedNodeIdsProvider(vm -> List.of())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .isReadOnlyProvider(isReadOnlyProvider);
        if (treeDescription.getHelpExpression() != null && !treeDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(treeDescription.getHelpExpression()));
        }

        return Optional.of(builder.build());
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object treeItem = variableManager.getVariables().get(VariableManager.SELF);
        return this.objectService.getId(treeItem);
    }

    private Function<VariableManager, List<String>> getTreeBeginIconValue(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            List<String> values = new ArrayList<>();
            if (!safeValueExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Optional<List<Object>> optionalResult = this.interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression).asObjects();
                if (optionalResult.isPresent()) {
                    values = optionalResult.get().stream().filter(String.class::isInstance).map(String.class::cast).toList();
                }
            }
            return values;
        };
    }

    private Function<VariableManager, List<List<String>>> getTreeEndIconValue(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            List<List<String>> values = new ArrayList<>(new ArrayList<>());
            if (!safeValueExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Optional<List<Object>> optionalResult = this.interpreter.evaluateExpression(childVariableManager.getVariables(), safeValueExpression).asObjects();
                if (optionalResult.isPresent()) {
                    var list = optionalResult.get().stream().filter(List.class::isInstance).map(List.class::cast).toList();
                    return list.stream().map(valuesList -> (List<String>) valuesList.stream().filter(String.class::isInstance).map(String.class::cast).toList())
                            .toList();
                }
            }
            return values;
        };
    }
    private String getTreeItemKind(VariableManager variableManager) {
        Object candidate = variableManager.getVariables().get(VariableManager.SELF);
        return this.objectService.getKind(candidate);
    }

    @Override
    public Optional<AbstractControlDescription> caseWidgetDescription(WidgetDescription widgetDescription) {
        return this.customWidgetConverters.doSwitch(widgetDescription).map(AbstractControlDescription.class::cast);
    }

    @Override
    public Optional<AbstractControlDescription> caseDateTimeDescription(org.eclipse.sirius.components.view.form.DateTimeDescription viewDateTimeDescription) {
        String descriptionId = this.getDescriptionId(viewDateTimeDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewDateTimeDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewDateTimeDescription.getIsEnabledExpression());
        Function<VariableManager, String> stringValueProvider = this.getStringValueProvider(viewDateTimeDescription.getStringValueExpression());
        BiFunction<VariableManager, String, IStatus> newValueHandler = this.getNewValueHandler(viewDateTimeDescription.getBody());

        Function<VariableManager, DateTimeStyle> styleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewDateTimeDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(DateTimeDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewDateTimeDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new DateTimeStyleProvider(effectiveStyle).apply(childVariableManager);
        };

        var builder = DateTimeDescription.newDateTimeDescription(descriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .type(this.getDateTimeType(viewDateTimeDescription))
                .stringValueProvider(stringValueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewDateTimeDescription.getHelpExpression() != null && !viewDateTimeDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewDateTimeDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
    }


    private DateTimeType getDateTimeType(org.eclipse.sirius.components.view.form.DateTimeDescription viewDateTimeDescription) {
        org.eclipse.sirius.components.view.form.DateTimeType viewDisplayMode = viewDateTimeDescription.getType();
        return DateTimeType.valueOf(viewDisplayMode.getLiteral());
    }


    @Override
    public Optional<AbstractControlDescription> caseSliderDescription(org.eclipse.sirius.components.view.form.SliderDescription viewSliderDescription) {
        String descriptionId = this.getDescriptionId(viewSliderDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewSliderDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewSliderDescription.getIsEnabledExpression());
        Function<VariableManager, Integer> minValueProvider = this.getIntValueProvider(viewSliderDescription.getMinValueExpression());
        Function<VariableManager, Integer> maxValueProvider = this.getIntValueProvider(viewSliderDescription.getMaxValueExpression());
        Function<VariableManager, Integer> currentValueProvider = this.getIntValueProvider(viewSliderDescription.getCurrentValueExpression());
        Function<VariableManager, IStatus> newValueHandler = this.getOperationsHandler(viewSliderDescription.getBody());

        var builder = SliderDescription.newSliderDescription(descriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .minValueProvider(minValueProvider)
                .maxValueProvider(maxValueProvider)
                .currentValueProvider(currentValueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "");
        if (viewSliderDescription.getHelpExpression() != null && !viewSliderDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewSliderDescription.getHelpExpression()));
        }
        return Optional.of(builder.build());
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

    private IStatus handleItemDeletion(VariableManager variableManager) {
        variableManager.get(ListComponent.CANDIDATE_VARIABLE, Object.class).ifPresent(this.editService::delete);
        return this.buildSuccessWithSemanticChangeAndFeedbackMessages();
    }

    private IStatus getListItemClickHandler(VariableManager variableManager, List<Operation> operations) {
        OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(VARIABLE_MANAGER, variableManager);
        Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
        if (optionalVariableManager.isEmpty()) {
            return this.buildFailureWithFeedbackMessages("Something went wrong while handling the item click.");
        } else {
            return this.buildSuccessWithSemanticChangeAndFeedbackMessages();
        }
    }

    private String getKind(VariableManager variableManager) {
        Object candidate = variableManager.getVariables().get(ListComponent.CANDIDATE_VARIABLE);
        return this.objectService.getKind(candidate);
    }

    private String getItemId(VariableManager variableManager) {
        Object candidate = variableManager.getVariables().get(ListComponent.CANDIDATE_VARIABLE);
        return this.objectService.getId(candidate);
    }

    private Function<VariableManager, List<?>> getMultiValueProvider(String expression) {
        String safeExpression = Optional.ofNullable(expression).orElse("");
        return variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            if (safeExpression.isBlank()) {
                return List.of();
            } else {
                return this.interpreter.evaluateExpression(childVariableManager.getVariables(), safeExpression).asObjects().orElse(List.of());
            }
        };
    }

    private <T> Function<VariableManager, List<T>> getMultiValueProvider(String expression, Class<T> type) {
        String safeExpression = Optional.ofNullable(expression).orElse("");
        return variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            if (safeExpression.isBlank()) {
                return List.of();
            } else {
                return this.interpreter.evaluateExpression(childVariableManager.getVariables(), safeExpression).asObjects().orElse(List.of()).stream().map(type::cast).toList();
            }
        };
    }

    private AbstractWidgetDescription createChartWidgetDescription(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription, IChartDescription chartDescription) {
        String descriptionId = this.getDescriptionId(widgetDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(widgetDescription.getLabelExpression());
        ChartWidgetDescription.Builder builder = ChartWidgetDescription.newChartWidgetDescription(descriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .idProvider(idProvider)
                .chartDescription(chartDescription)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "");
        if (widgetDescription.getHelpExpression() != null && !widgetDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(widgetDescription.getHelpExpression()));
        }
        return builder.build();
    }

    private Function<VariableManager, String> getOptionIdProvider() {
        return variableManager -> {
            Object candidate = variableManager.getVariables().get(SelectComponent.CANDIDATE_VARIABLE);
            return Optional.ofNullable(this.objectService.getId(candidate)).orElseGet(() -> Optional.ofNullable(candidate).map(Objects::toString).orElse(""));
        };
    }

    private BiFunction<VariableManager, List<String>, IStatus> getMultiSelectNewValuesHandler(List<Operation> operations) {
        return (variableManager, newValue) -> {
            IStatus status = this.buildFailureWithFeedbackMessages("An error occurred while handling the new selected values.");
            Optional<IEditingContext> optionalEditingDomain = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
            if (optionalEditingDomain.isPresent()) {
                IEditingContext editingContext = optionalEditingDomain.get();

                List<Object> newValuesObjects = newValue.stream()
                        .map(currentValue -> this.objectService.getObject(editingContext, currentValue))
                        .flatMap(Optional::stream)
                        .toList();

                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValuesObjects);
                OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
                Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
                if (optionalVariableManager.isEmpty()) {
                    status = this.buildFailureWithFeedbackMessages("Something went wrong while handling the MultiSelect widget new values.");
                } else {
                    status = this.buildSuccessWithFeedbackMessages();
                }
            }
            return status;
        };
    }

    private Function<VariableManager, List<String>> getMultiSelectValuesProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            List<String> values = new ArrayList<>();
            if (!safeValueExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Optional<List<Object>> optionalResult = this.interpreter.evaluateExpression(childVariableManager.getVariables(), safeValueExpression).asObjects();
                if (optionalResult.isPresent()) {
                    values = optionalResult.get().stream().map(this.objectService::getId).toList();
                }
            }
            return values;
        };
    }

    private StringValueProvider getStringValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return new StringValueProvider(this.interpreter, safeValueExpression);
    }

    private BooleanValueProvider getBooleanValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return new BooleanValueProvider(this.interpreter, safeValueExpression);

    }

    private Function<VariableManager, String> getSelectValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            if (!safeValueExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Result result = this.interpreter.evaluateExpression(childVariableManager.getVariables(), safeValueExpression);
                var rawValue = result.asObject();
                return rawValue.map(this.objectService::getId).orElseGet(() -> rawValue.map(Objects::toString).orElse(""));
            }
            return "";
        };
    }

    private Function<VariableManager, IStatus> getOperationsHandler(List<Operation> operations) {
        return variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
            if (optionalVariableManager.isEmpty()) {
                return this.buildFailureWithFeedbackMessages("Something went wrong while handling the widget operations execution.");
            } else {
                return this.buildSuccessWithFeedbackMessages();
            }
        };
    }

    private <T> BiFunction<VariableManager, T, IStatus> getNewValueHandler(List<Operation> operations) {
        return (variableManager, newValue) -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValue);
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
            if (optionalVariableManager.isEmpty()) {
                return this.buildFailureWithFeedbackMessages("Something went wrong while handling the widget new value.");
            } else {
                return this.buildSuccessWithFeedbackMessages();
            }
        };
    }

    private BiFunction<VariableManager, String, IStatus> getSelectNewValueHandler(List<Operation> operations) {
        return (variableManager, newValue) -> {
            IStatus status;

            Object newValueObject = null;
            if (newValue != null && !newValue.isBlank()) {
                newValueObject = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                        .flatMap(editingContext -> this.objectService.getObject(editingContext, newValue))
                        .orElse(newValue);
            }
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValueObject);
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
            if (optionalVariableManager.isEmpty()) {
                status = this.buildFailureWithFeedbackMessages("Something went wrong while handling the Select widget new value.");
            } else {
                status = this.buildSuccessWithFeedbackMessages();
            }
            return status;
        };
    }

    private Function<VariableManager, List<String>> getOptionIconURLProvider() {
        return variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, Object.class).map(this.objectService::getImagePath).orElse(List.of());
    }

    private Function<VariableManager, Boolean> getReadOnlyValueProvider(String expression) {
        return variableManager -> {
            if (expression != null && !expression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Result result = this.interpreter.evaluateExpression(childVariableManager.getVariables(), expression);
                return result.asBoolean().map(value -> !value).orElse(Boolean.FALSE);
            }
            return Boolean.FALSE;
        };
    }

    private String getDescriptionId(FormElementDescription description) {
        return this.widgetIdProvider.getFormElementDescriptionId(description);
    }


    private List<String> getListItemIconURL(VariableManager variablemanager) {
        return variablemanager.get(ListComponent.CANDIDATE_VARIABLE, Object.class)
                .map(this.objectService::getImagePath)
                .orElse(List.of());
    }

    private boolean matches(String condition, VariableManager variableManager) {
        return this.interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }

    private Failure buildFailureWithFeedbackMessages(String technicalMessage) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(technicalMessage, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }

    private Success buildSuccessWithSemanticChangeAndFeedbackMessages() {
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private Success buildSuccessWithFeedbackMessages() {
        return new Success(this.feedbackMessageService.getFeedbackMessages());
    }

}
