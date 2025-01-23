/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.ContainerBorderStyle;
import org.eclipse.sirius.components.forms.DateTimeStyle;
import org.eclipse.sirius.components.forms.DateTimeType;
import org.eclipse.sirius.components.forms.FlexDirection;
import org.eclipse.sirius.components.forms.LabelWidgetStyle;
import org.eclipse.sirius.components.forms.LinkStyle;
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.components.ListComponent;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.forms.description.DateTimeDescription;
import org.eclipse.sirius.components.forms.description.FlexboxContainerDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.forms.description.ImageDescription;
import org.eclipse.sirius.components.forms.description.LabelDescription;
import org.eclipse.sirius.components.forms.description.LinkDescription;
import org.eclipse.sirius.components.forms.description.ListDescription;
import org.eclipse.sirius.components.forms.description.SliderDescription;
import org.eclipse.sirius.components.forms.description.SplitButtonDescription;
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
import org.eclipse.sirius.components.view.emf.form.converters.ButtonDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.CheckboxDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.MultiSelectDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.RadioDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.RichTextDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.SelectDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.TextareaDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.TextfieldDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.form.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.eclipse.sirius.components.view.form.RichTextDescription;
import org.eclipse.sirius.components.view.form.TextAreaDescription;
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

    public static final String VARIABLE_MANAGER = "variableManager";

    private final Logger logger = LoggerFactory.getLogger(ViewFormDescriptionConverterSwitch.class);

    private final AQLInterpreter interpreter;

    private final IEditService editService;

    private final IObjectService objectService;

    private final Switch<Optional<AbstractWidgetDescription>> customWidgetConverters;

    private final IFeedbackMessageService feedbackMessageService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final IFormIdProvider widgetIdProvider;

    public ViewFormDescriptionConverterSwitch(AQLInterpreter interpreter, IEditService editService, IObjectService objectService, Switch<Optional<AbstractWidgetDescription>> customWidgetConverters,
            IFeedbackMessageService feedbackMessageService, IFormIdProvider idProvider) {
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
        return Optional.of(new TextfieldDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewTextfieldDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseCheckboxDescription(org.eclipse.sirius.components.view.form.CheckboxDescription viewCheckboxDescription) {
        return Optional.of(new CheckboxDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewCheckboxDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseSelectDescription(org.eclipse.sirius.components.view.form.SelectDescription viewSelectDescription) {
        return Optional.of(new SelectDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewSelectDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseTextAreaDescription(TextAreaDescription viewTextAreaDescription) {
        return Optional.of(new TextareaDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewTextAreaDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseRichTextDescription(RichTextDescription viewRichTextDescription) {
        return Optional.of(new RichTextDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewRichTextDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseMultiSelectDescription(org.eclipse.sirius.components.view.form.MultiSelectDescription viewMultiSelectDescription) {
        return Optional.of(new MultiSelectDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewMultiSelectDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseRadioDescription(org.eclipse.sirius.components.view.form.RadioDescription viewRadioDescription) {
        return Optional.of(new RadioDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewRadioDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseBarChartDescription(org.eclipse.sirius.components.view.form.BarChartDescription viewBarChartDescription) {
        String labelExpression = viewBarChartDescription.getLabelExpression();
        String keysExpression = viewBarChartDescription.getKeysExpression();
        String valuesExpression = viewBarChartDescription.getValuesExpression();
        String yAxisLabelExpression = viewBarChartDescription.getYAxisLabelExpression();
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
                .height(viewBarChartDescription.getHeight())
                .yAxisLabelProvider(this.getStringValueProvider(yAxisLabelExpression));
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
        IChartDescription chartDescription =  PieChartDescription.newPieChartDescription(this.getDescriptionId(viewPieChartDescription))
                .label(viewPieChartDescription.getName())
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .keysProvider(this.getMultiValueProvider(keysExpression, String.class))
                .valuesProvider(this.getMultiValueProvider(valuesExpression, Number.class))
                .styleProvider(styleProvider)
                .build();

        return Optional.of(this.createChartWidgetDescription(viewPieChartDescription, chartDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseFlexboxContainerDescription(org.eclipse.sirius.components.view.form.FlexboxContainerDescription viewFlexboxContainerDescription) {
        String descriptionId = this.getDescriptionId(viewFlexboxContainerDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewFlexboxContainerDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewFlexboxContainerDescription.getIsEnabledExpression());
        FlexDirection flexDirection = FlexDirection.valueOf(viewFlexboxContainerDescription.getFlexDirection().getName());
        Function<VariableManager, ContainerBorderStyle> borderStyleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewFlexboxContainerDescription.getConditionalBorderStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), childVariableManager))
                    .map(org.eclipse.sirius.components.view.form.ContainerBorderStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewFlexboxContainerDescription::getBorderStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ContainerBorderStyleProvider(effectiveStyle).apply(childVariableManager);
        };
        List<Optional<AbstractControlDescription>> children = new ArrayList<>();
        viewFlexboxContainerDescription.getChildren().forEach(widget -> children.add(ViewFormDescriptionConverterSwitch.this.doSwitch(widget)));

        var flexboxContainerDescription = FlexboxContainerDescription.newFlexboxContainerDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .flexDirection(flexDirection)
                .children(children.stream().filter(Optional::isPresent).map(Optional::get).toList())
                .borderStyleProvider(borderStyleProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewFlexboxContainerDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewFlexboxContainerDescription.getHelpExpression()))
                .build();
        return Optional.of(flexboxContainerDescription);
    }

    @Override
    public Optional<AbstractControlDescription> caseButtonDescription(org.eclipse.sirius.components.view.form.ButtonDescription viewButtonDescription) {
        return Optional.of(new ButtonDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewButtonDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseSplitButtonDescription(org.eclipse.sirius.components.view.form.SplitButtonDescription viewSplitButtonDescription) {
        String descriptionId = this.getDescriptionId(viewSplitButtonDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewSplitButtonDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewSplitButtonDescription.getIsEnabledExpression());

        List<ButtonDescription> actions = viewSplitButtonDescription.getActions().stream()
                .map(ViewFormDescriptionConverterSwitch.this::doSwitch)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(ButtonDescription.class::isInstance)
                .map(ButtonDescription.class::cast).toList();

        if (actions.isEmpty()) {
            this.logger.warn("Invalid empty Reference Actions on widget {}", viewSplitButtonDescription.getName());
            return Optional.empty();
        }

        var splitButtonDescription = SplitButtonDescription.newSplitButtonDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .actions(actions)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewSplitButtonDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewSplitButtonDescription.getHelpExpression()))
                .build();
        return Optional.of(splitButtonDescription);
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

        var labelDescription = LabelDescription.newLabelDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .valueProvider(valueProvider)
                .styleProvider(styleProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewLabelDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewLabelDescription.getHelpExpression()))
                .build();
        return Optional.of(labelDescription);
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

        var linkDescription = LinkDescription.newLinkDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .urlProvider(valueProvider)
                .styleProvider(styleProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewLinkDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewLinkDescription.getHelpExpression()))
                .build();
        return Optional.of(linkDescription);
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

        var listDescription = ListDescription.newListDescription(descriptionId)
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
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewListDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewListDescription.getHelpExpression()))
                .build();
        return Optional.of(listDescription);
    }

    @Override
    public Optional<AbstractControlDescription> caseImageDescription(org.eclipse.sirius.components.view.form.ImageDescription viewImageDescription) {
        String descriptionId = this.getDescriptionId(viewImageDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewImageDescription.getLabelExpression());
        StringValueProvider urlProvider = this.getStringValueProvider(viewImageDescription.getUrlExpression());
        StringValueProvider maxWidthProvider = this.getStringValueProvider(viewImageDescription.getMaxWidthExpression());

        var imageDescription = ImageDescription.newImageDescription(descriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .urlProvider(urlProvider)
                .maxWidthProvider(maxWidthProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewImageDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewImageDescription.getHelpExpression()))
                .build();
        return Optional.of(imageDescription);
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
    public Optional<AbstractControlDescription> caseTreeDescription(org.eclipse.sirius.components.view.form.TreeDescription viewTreeDescription) {
        String descriptionId = this.getDescriptionId(viewTreeDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewTreeDescription.getLabelExpression());
        StringValueProvider itemLabelProvider = this.getStringValueProvider(viewTreeDescription.getTreeItemLabelExpression());
        Function<VariableManager, String> nodeIdProvider = this::getTreeItemId;
        Function<VariableManager, String> itemKindProvider = this::getTreeItemKind;
        Function<VariableManager, List<String>> nodeIconURLProvider = this.getTreeBeginIconValue(viewTreeDescription.getTreeItemBeginIconExpression());
        Function<VariableManager, List<List<String>>> nodeIconEndURLProvider = this.getTreeEndIconValue(viewTreeDescription.getTreeItemEndIconsExpression());
        Function<VariableManager, List<? extends Object>> childrenProvider = this.getMultiValueProvider(viewTreeDescription.getChildrenExpression());
        Function<VariableManager, Boolean> nodeSelectableProvider = this.getBooleanValueProvider(viewTreeDescription.getIsTreeItemSelectableExpression());
        Function<VariableManager, Boolean> nodeCheckbableProvider = this.getBooleanValueProvider(viewTreeDescription.getIsCheckableExpression());
        String valueExpression = Optional.ofNullable(viewTreeDescription.getCheckedValueExpression()).orElse("");
        BooleanValueProvider valueProvider = new BooleanValueProvider(this.interpreter, valueExpression);
        BiFunction<VariableManager, Boolean, IStatus> newValueHandler = this.getNewValueHandler(viewTreeDescription.getBody());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewTreeDescription.getIsEnabledExpression());

        var treeDescription = TreeDescription.newTreeDescription(descriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .iconURLProvider(variableManager -> List.of())
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
                .expandedNodeIdsProvider(variableManager -> List.of())
                .isReadOnlyProvider(isReadOnlyProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewTreeDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewTreeDescription.getHelpExpression()))
                .build();

        return Optional.of(treeDescription);
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

        var dateTimeDescription = DateTimeDescription.newDateTimeDescription(descriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .type(this.getDateTimeType(viewDateTimeDescription))
                .stringValueProvider(stringValueProvider)
                .newValueHandler(newValueHandler)
                .styleProvider(styleProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewDateTimeDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewDateTimeDescription.getHelpExpression()))
                .build();
        return Optional.of(dateTimeDescription);
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

        var sliderDescription = SliderDescription.newSliderDescription(descriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .isReadOnlyProvider(isReadOnlyProvider)
                .minValueProvider(minValueProvider)
                .maxValueProvider(maxValueProvider)
                .currentValueProvider(currentValueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewSliderDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewSliderDescription.getHelpExpression()))
                .build();
        return Optional.of(sliderDescription);
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

        return ChartWidgetDescription.newChartWidgetDescription(descriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(labelProvider)
                .idProvider(idProvider)
                .chartDescription(chartDescription)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, widgetDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, widgetDescription.getHelpExpression()))
                .build();
    }

    private StringValueProvider getStringValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return new StringValueProvider(this.interpreter, safeValueExpression);
    }

    private BooleanValueProvider getBooleanValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return new BooleanValueProvider(this.interpreter, safeValueExpression);

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
