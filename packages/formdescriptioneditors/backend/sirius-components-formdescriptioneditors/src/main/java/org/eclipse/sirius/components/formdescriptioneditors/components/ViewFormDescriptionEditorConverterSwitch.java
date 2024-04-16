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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle;
import org.eclipse.sirius.components.charts.barchart.descriptions.BarChartDescription;
import org.eclipse.sirius.components.charts.descriptions.IChartDescription;
import org.eclipse.sirius.components.charts.piechart.PieChartDescription;
import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorForDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorIfDescription;
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
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.FlexboxContainerDescription;
import org.eclipse.sirius.components.forms.description.ImageDescription;
import org.eclipse.sirius.components.forms.description.LabelDescription;
import org.eclipse.sirius.components.forms.description.LinkDescription;
import org.eclipse.sirius.components.forms.description.ListDescription;
import org.eclipse.sirius.components.forms.description.MultiSelectDescription;
import org.eclipse.sirius.components.forms.description.RadioDescription;
import org.eclipse.sirius.components.forms.description.RichTextDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.forms.description.SplitButtonDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.form.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.DateTimeDescription;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.form.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.PieChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.form.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.SliderDescription;
import org.eclipse.sirius.components.view.form.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.view.form.util.FormSwitch;

/**
 * A switch to dispatch View Form Description Editor Widget Descriptions conversion.
 *
 * @author arichard
 */
public class ViewFormDescriptionEditorConverterSwitch extends FormSwitch<AbstractWidgetDescription> {

    private static final String AQL_PREFIX = "aql:";

    private final FormDescriptionEditorDescription formDescriptionEditorDescription;

    private final VariableManager variableManager;

    private final Switch<AbstractWidgetDescription> customWidgetConverter;

    public ViewFormDescriptionEditorConverterSwitch(FormDescriptionEditorDescription formDescriptionEditorDescription, VariableManager variableManager, Switch<AbstractWidgetDescription> customWidgetConverter) {
        this.formDescriptionEditorDescription = formDescriptionEditorDescription;
        this.variableManager = variableManager;
        this.customWidgetConverter = customWidgetConverter;
    }

    @Override
    public AbstractWidgetDescription caseTreeDescription(org.eclipse.sirius.components.view.form.TreeDescription treeDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, treeDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        return TreeDescription.newTreeDescription(id)
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(treeDescription, "Tree"))
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .targetObjectIdProvider(vm -> "")
                .childrenProvider(vm -> List.of())
                .nodeIdProvider(vm -> "")
                .nodeEndIconsURLProvider(vm -> List.of())
                .nodeSelectableProvider(vm -> false)
                .helpTextProvider(vm -> "")
                .nodeIconURLProvider(vm -> List.of())
                .nodeKindProvider(object -> "")
                .nodeLabelProvider(vm -> "")
                .expandedNodeIdsProvider(vm -> List.of())
                .isCheckableProvider(vm -> false)
                .checkedValueProvider(vm -> false)
                .newCheckedValueHandler((vm, newValue) -> new Success())
                .iconURLProvider(vm -> List.of())
                .build();
    }

    @Override
    public AbstractWidgetDescription caseTextfieldDescription(org.eclipse.sirius.components.view.form.TextfieldDescription viewTextfieldDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewTextfieldDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, TextfieldStyle> styleProvider = vm -> {
            TextfieldDescriptionStyle style = viewTextfieldDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new TextfieldStyleProvider(style).build();
        };

        TextfieldDescription.Builder builder = TextfieldDescription.newTextfieldDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewTextfieldDescription, "Textfield"))
                .valueProvider(vm -> "")
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewTextfieldDescription.getHelpExpression() != null && !viewTextfieldDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewTextfieldDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseCheckboxDescription(org.eclipse.sirius.components.view.form.CheckboxDescription viewCheckboxDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewCheckboxDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, CheckboxStyle> styleProvider = vm -> {
            CheckboxDescriptionStyle style = viewCheckboxDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new CheckboxStyleProvider(style).build();
        };

        CheckboxDescription.Builder builder = CheckboxDescription.newCheckboxDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewCheckboxDescription, "Checkbox"))
                .valueProvider(vm -> true)
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewCheckboxDescription.getHelpExpression() != null && !viewCheckboxDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewCheckboxDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseSelectDescription(org.eclipse.sirius.components.view.form.SelectDescription viewSelectDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewSelectDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, SelectStyle> styleProvider = vm -> {
            SelectDescriptionStyle style = viewSelectDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new SelectStyleProvider(style).build();
        };

        SelectDescription.Builder builder = SelectDescription.newSelectDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewSelectDescription, "Select"))
                .valueProvider(vm -> "")
                .optionIdProvider(vm -> "")
                .optionLabelProvider(vm -> "")
                .optionIconURLProvider(vm -> List.of())
                .optionsProvider(vm -> List.of())
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewSelectDescription.getHelpExpression() != null && !viewSelectDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewSelectDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseTextAreaDescription(org.eclipse.sirius.components.view.form.TextAreaDescription viewTextareaDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewTextareaDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, TextareaStyle> styleProvider = vm -> {
            TextareaDescriptionStyle style = viewTextareaDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new TextareaStyleProvider(style).build();
        };

        TextareaDescription.Builder builder = TextareaDescription.newTextareaDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewTextareaDescription, "Textarea"))
                .valueProvider(vm -> "")
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewTextareaDescription.getHelpExpression() != null && !viewTextareaDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewTextareaDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseMultiSelectDescription(org.eclipse.sirius.components.view.form.MultiSelectDescription viewMultiSelectDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewMultiSelectDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, MultiSelectStyle> styleProvider = vm -> {
            MultiSelectDescriptionStyle style = viewMultiSelectDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new MultiSelectStyleProvider(style).build();
        };

        MultiSelectDescription.Builder builder = MultiSelectDescription.newMultiSelectDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewMultiSelectDescription, "MultiSelect"))
                .valuesProvider(vm -> List.of())
                .optionIdProvider(vm -> "")
                .optionLabelProvider(vm -> "")
                .optionIconURLProvider(vm -> List.of())
                .optionsProvider(vm -> List.of())
                .newValuesHandler((vm, values) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewMultiSelectDescription.getHelpExpression() != null && !viewMultiSelectDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewMultiSelectDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseRadioDescription(org.eclipse.sirius.components.view.form.RadioDescription viewRadioDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewRadioDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, RadioStyle> styleProvider = vm -> {
            RadioDescriptionStyle style = viewRadioDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new RadioStyleProvider(style).build();
        };

        RadioDescription.Builder builder = RadioDescription.newRadioDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewRadioDescription, "Radio"))
                .optionIdProvider(vm -> "")
                .optionLabelProvider(vm -> "")
                .optionSelectedProvider(vm -> true)
                .optionsProvider(vm -> List.of())
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewRadioDescription.getHelpExpression() != null && !viewRadioDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewRadioDescription));
        }
        return builder.build();

    }

    @Override
    public AbstractWidgetDescription caseFlexboxContainerDescription(org.eclipse.sirius.components.view.form.FlexboxContainerDescription viewFlexboxContainerDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewFlexboxContainerDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        FlexDirection flexDirection = FlexDirection.valueOf(viewFlexboxContainerDescription.getFlexDirection().getName());
        List<AbstractControlDescription> children = new ArrayList<>();
        viewFlexboxContainerDescription.getChildren().forEach(viewWidgetDescription -> {
            children.add(ViewFormDescriptionEditorConverterSwitch.this.doSwitch(viewWidgetDescription));

        });
        Function<VariableManager, ContainerBorderStyle> borderStyleProvider = vm -> {
            org.eclipse.sirius.components.view.form.ContainerBorderStyle style = viewFlexboxContainerDescription.getBorderStyle();
            if (style == null) {
                return null;
            }
            return new ContainerBorderStyleProvider(style).build();
        };

        FlexboxContainerDescription.Builder builder = FlexboxContainerDescription.newFlexboxContainerDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewFlexboxContainerDescription, "FlexboxContainer"))
                .flexDirection(flexDirection)
                .children(children)
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .borderStyleProvider(borderStyleProvider);
        if (viewFlexboxContainerDescription.getHelpExpression() != null && !viewFlexboxContainerDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewFlexboxContainerDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseButtonDescription(org.eclipse.sirius.components.view.form.ButtonDescription viewButtonDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewButtonDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, ButtonStyle> styleProvider = vm -> {
            ButtonDescriptionStyle style = viewButtonDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new ButtonStyleProvider(style).build();
        };

        ButtonDescription.Builder builder = ButtonDescription.newButtonDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewButtonDescription, "Button"))
                .buttonLabelProvider(vm -> viewButtonDescription.getButtonLabelExpression())
                .imageURLProvider(vm -> viewButtonDescription.getImageExpression())
                .pushButtonHandler(vm -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewButtonDescription.getHelpExpression() != null && !viewButtonDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewButtonDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseSplitButtonDescription(org.eclipse.sirius.components.view.form.SplitButtonDescription splitButtonDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, splitButtonDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        List<ButtonDescription> actions = splitButtonDescription.getActions().stream()
                .map(ViewFormDescriptionEditorConverterSwitch.this::doSwitch)
                .filter(ButtonDescription.class::isInstance)
                .map(ButtonDescription.class::cast).toList();

        SplitButtonDescription.Builder builder = SplitButtonDescription.newSplitButtonDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(splitButtonDescription, "SplitButton"))
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .actions(actions)
                .messageProvider(object -> "");

        if (splitButtonDescription.getHelpExpression() != null && !splitButtonDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(splitButtonDescription));
        }

        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseLabelDescription(org.eclipse.sirius.components.view.form.LabelDescription viewLabelDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewLabelDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, LabelWidgetStyle> styleProvider = vm -> {
            LabelDescriptionStyle style = viewLabelDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new LabelStyleProvider(style).build();
        };

        LabelDescription.Builder builder = LabelDescription.newLabelDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewLabelDescription, "Label"))
                .valueProvider(vm -> "")
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewLabelDescription.getHelpExpression() != null && !viewLabelDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewLabelDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseLinkDescription(org.eclipse.sirius.components.view.form.LinkDescription viewLinkDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewLinkDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, LinkStyle> styleProvider = vm -> {
            LinkDescriptionStyle style = viewLinkDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new LinkStyleProvider(style).build();
        };

        LinkDescription.Builder builder = LinkDescription.newLinkDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewLinkDescription, "Link"))
                .urlProvider(vm -> "")
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewLinkDescription.getHelpExpression() != null && !viewLinkDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewLinkDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseListDescription(org.eclipse.sirius.components.view.form.ListDescription viewListDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewListDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, ListStyle> styleProvider = vm -> {
            ListDescriptionStyle style = viewListDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new ListStyleProvider(style).build();
        };

        ListDescription.Builder builder = ListDescription.newListDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewListDescription, "List"))
                .itemsProvider(vm -> List.of())
                .itemKindProvider(vm -> "")
                .itemDeleteHandlerProvider(vm -> new Success())
                .itemIconURLProvider(vm -> List.of())
                .itemIdProvider(vm -> "")
                .itemLabelProvider(vm -> "")
                .itemDeletableProvider(vm -> false)
                .itemClickHandlerProvider(vm -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(styleProvider);
        if (viewListDescription.getHelpExpression() != null && !viewListDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewListDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseImageDescription(org.eclipse.sirius.components.view.form.ImageDescription viewImageDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewImageDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        ImageDescription.Builder builder = ImageDescription.newImageDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewImageDescription, "Image"))
                .urlProvider(vm -> Optional.ofNullable(viewImageDescription.getUrlExpression()).orElse(""))
                .maxWidthProvider(vm -> viewImageDescription.getMaxWidthExpression())
                .diagnosticsProvider(vm -> List.of()).kindProvider(object -> "")
                .messageProvider(object -> "");
        if (viewImageDescription.getHelpExpression() != null && !viewImageDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewImageDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseRichTextDescription(org.eclipse.sirius.components.view.form.RichTextDescription viewRichTextDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewRichTextDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        RichTextDescription.Builder builder = RichTextDescription.newRichTextDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewRichTextDescription, "RichText"))
                .valueProvider(vm -> "")
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "");
        if (viewRichTextDescription.getHelpExpression() != null && !viewRichTextDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewRichTextDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseBarChartDescription(org.eclipse.sirius.components.view.form.BarChartDescription viewBarChartDescription) {
        Function<VariableManager, BarChartStyle> styleProvider = vm -> {
            BarChartDescriptionStyle style = viewBarChartDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new BarChartStyleProvider(style).build();
        };

        // @formatter:off
        IChartDescription chartDescription = BarChartDescription.newBarChartDescription(UUID.randomUUID().toString())
                .label(Optional.ofNullable(viewBarChartDescription.getName()).orElse(""))
                .labelProvider(vm -> this.getWidgetLabel(viewBarChartDescription, "BarChart"))
                .targetObjectIdProvider(vm -> vm.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId).orElse(null))
                .keysProvider(vm -> List.of())
                .valuesProvider(vm -> List.of())
                .styleProvider(styleProvider)
                .width(viewBarChartDescription.getWidth())
                .height(viewBarChartDescription.getHeight())
                .build();
        // @formatter:on
        return this.createChartWidgetDescription(viewBarChartDescription, chartDescription);
    }

    @Override
    public AbstractWidgetDescription casePieChartDescription(org.eclipse.sirius.components.view.form.PieChartDescription viewPieChartDescription) {
        Function<VariableManager, PieChartStyle> styleProvider = vm -> {
            PieChartDescriptionStyle style = viewPieChartDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new PieChartStyleProvider(style).build();
        };

        // @formatter:off
        IChartDescription chartDescription =  PieChartDescription.newPieChartDescription(UUID.randomUUID().toString())
                .label(this.getWidgetLabel(viewPieChartDescription, "PieChart"))
                .targetObjectIdProvider(vm -> vm.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId).orElse(null))
                .keysProvider(vm -> List.of())
                .valuesProvider(vm -> List.of())
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
        return this.createChartWidgetDescription(viewPieChartDescription, chartDescription);
    }

    private AbstractWidgetDescription createChartWidgetDescription(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription, IChartDescription chartDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, widgetDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        ChartWidgetDescription.Builder builder = ChartWidgetDescription.newChartWidgetDescription(chartDescription.getId())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(widgetDescription, "Chart"))
                .chartDescription(chartDescription)
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "");
        if (widgetDescription.getHelpExpression() != null && !widgetDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(widgetDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseFormElementFor(FormElementFor formElementFor) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, formElementFor);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        List<AbstractControlDescription> children = new ArrayList<>();
        formElementFor.getChildren().forEach(viewWidgetDescription -> {
            children.add(ViewFormDescriptionEditorConverterSwitch.this.doSwitch(viewWidgetDescription));
        });

        var iterator = formElementFor.getIterator();
        if (iterator == null || iterator.isBlank()) {
            iterator = "<no iterator>";
        }
        var iterable = formElementFor.getIterableExpression();
        if (iterable == null || iterable.isBlank()) {
            iterable = "<no iterable>";
        }
        var label = "for %s in %s".formatted(iterator, iterable);

        return FormDescriptionEditorForDescription.newFormDescriptionEditorForDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> label)
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .children(children)
                .build();
    }

    @Override
    public AbstractWidgetDescription caseFormElementIf(FormElementIf formElementIf) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, formElementIf);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        List<AbstractControlDescription> children = new ArrayList<>();
        formElementIf.getChildren().forEach(viewWidgetDescription -> {
            children.add(ViewFormDescriptionEditorConverterSwitch.this.doSwitch(viewWidgetDescription));
        });

        var predicate = formElementIf.getPredicateExpression();
        if (predicate == null || predicate.isBlank()) {
            predicate = "<no predicate>";
        }
        var label = "if %s".formatted(predicate);

        return FormDescriptionEditorIfDescription.newFormDescriptionEditorIfDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> label)
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .children(children)
                .build();
    }

    @Override
    public AbstractWidgetDescription caseWidgetDescription(WidgetDescription widgetDescription) {
        return ViewFormDescriptionEditorConverterSwitch.this.customWidgetConverter.doSwitch(widgetDescription);
    }

    @Override
    public AbstractWidgetDescription caseSliderDescription(SliderDescription sliderDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, sliderDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);
        var builder =  org.eclipse.sirius.components.forms.description.SliderDescription.newSliderDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(sliderDescription, "Slider"))
                .minValueProvider(vm -> 0)
                .maxValueProvider(vm -> 100)
                .currentValueProvider(vm -> 50)
                .newValueHandler(vm -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "");
        if (sliderDescription.getHelpExpression() != null && !sliderDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(sliderDescription));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseDateTimeDescription(DateTimeDescription dateTimeDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, dateTimeDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        Function<VariableManager, DateTimeStyle> styleProvider = vm -> {
            DateTimeDescriptionStyle style = dateTimeDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new DateTimeStyleProvider(style).build();
        };

        var builder =  org.eclipse.sirius.components.forms.description.DateTimeDescription.newDateTimeDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(dateTimeDescription, "DateTime"))
                .stringValueProvider(vm -> "2030-06-20T10:30:00Z")
                .newValueHandler((vm, newValue) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .type(this.getDateTimeType(dateTimeDescription))
                .styleProvider(styleProvider);
        if (dateTimeDescription.getHelpExpression() != null && !dateTimeDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(dateTimeDescription));
        }
        return builder.build();
    }

    private DateTimeType getDateTimeType(org.eclipse.sirius.components.view.form.DateTimeDescription viewDateTimeDescription) {
        org.eclipse.sirius.components.view.form.DateTimeType viewDisplayMode = viewDateTimeDescription.getType();
        return DateTimeType.valueOf(viewDisplayMode.getLiteral());
    }

    public String getWidgetLabel(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription, String defaultLabel) {
        String widgetLabel = defaultLabel;
        String name = widgetDescription.getName();
        String labelExpression = widgetDescription.getLabelExpression();
        if (labelExpression != null && !labelExpression.isBlank() && !labelExpression.startsWith(AQL_PREFIX)) {
            widgetLabel = labelExpression;
        } else if (name != null && !name.isBlank()) {
            widgetLabel = name;
        }
        return widgetLabel;
    }

    public String getWidgetHelpText(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription) {
        String helpText = "Help";
        String helpExpression = widgetDescription.getHelpExpression();
        if (helpExpression != null && !helpExpression.isBlank() && !helpExpression.startsWith(AQL_PREFIX)) {
            helpText = helpExpression;
        }
        return helpText;
    }

}
