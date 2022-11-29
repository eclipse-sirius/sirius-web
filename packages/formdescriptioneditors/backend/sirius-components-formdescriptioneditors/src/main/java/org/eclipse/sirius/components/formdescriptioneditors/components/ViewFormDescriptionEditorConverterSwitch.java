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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle;
import org.eclipse.sirius.components.charts.barchart.descriptions.BarChartDescription;
import org.eclipse.sirius.components.charts.descriptions.IChartDescription;
import org.eclipse.sirius.components.charts.piechart.PieChartDescription;
import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.CheckboxStyle;
import org.eclipse.sirius.components.forms.FlexDirection;
import org.eclipse.sirius.components.forms.LabelWidgetStyle;
import org.eclipse.sirius.components.forms.LinkStyle;
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.forms.MultiSelectStyle;
import org.eclipse.sirius.components.forms.RadioStyle;
import org.eclipse.sirius.components.forms.SelectStyle;
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.forms.TextfieldStyle;
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
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.ListDescriptionStyle;
import org.eclipse.sirius.components.view.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.PieChartDescriptionStyle;
import org.eclipse.sirius.components.view.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.util.ViewSwitch;

/**
 * A switch to dispatch View Form Description Editor Widget Descriptions conversion.
 *
 * @author arichard
 */
public class ViewFormDescriptionEditorConverterSwitch extends ViewSwitch<AbstractWidgetDescription> {

    private static final String AQL_PREFIX = "aql:"; //$NON-NLS-1$

    private final FormDescriptionEditorDescription formDescriptionEditorDescription;

    private final VariableManager variableManager;

    public ViewFormDescriptionEditorConverterSwitch(FormDescriptionEditorDescription formDescriptionEditorDescription, VariableManager variableManager) {
        this.formDescriptionEditorDescription = formDescriptionEditorDescription;
        this.variableManager = variableManager;
    }

    @Override
    public AbstractWidgetDescription caseTextfieldDescription(org.eclipse.sirius.components.view.TextfieldDescription viewTextfieldDescription) {
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

        // @formatter:off
        return TextfieldDescription.newTextfieldDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewTextfieldDescription, "Textfield")) //$NON-NLS-1$
                .valueProvider(vm -> "") //$NON-NLS-1$
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseCheckboxDescription(org.eclipse.sirius.components.view.CheckboxDescription viewCheckboxDescription) {
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

        // @formatter:off
        return CheckboxDescription.newCheckboxDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewCheckboxDescription, "Checkbox")) //$NON-NLS-1$
                .valueProvider(vm -> true)
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseSelectDescription(org.eclipse.sirius.components.view.SelectDescription viewSelectDescription) {
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

        // @formatter:off
        return SelectDescription.newSelectDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewSelectDescription, "Select")) //$NON-NLS-1$
                .valueProvider(vm -> "") //$NON-NLS-1$
                .optionIdProvider(vm -> "") //$NON-NLS-1$
                .optionLabelProvider(vm -> "") //$NON-NLS-1$
                .optionsProvider(vm -> List.of())
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseTextAreaDescription(org.eclipse.sirius.components.view.TextAreaDescription viewTextareaDescription) {
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

        // @formatter:off
        return TextareaDescription.newTextareaDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewTextareaDescription, "Textarea")) //$NON-NLS-1$
                .valueProvider(vm -> "") //$NON-NLS-1$
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseMultiSelectDescription(org.eclipse.sirius.components.view.MultiSelectDescription viewMultiSelectDescription) {
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

        // @formatter:off
        return MultiSelectDescription.newMultiSelectDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewMultiSelectDescription, "MultiSelect")) //$NON-NLS-1$
                .valuesProvider(vm -> List.of())
                .optionIdProvider(vm -> "") //$NON-NLS-1$
                .optionLabelProvider(vm -> "") //$NON-NLS-1$
                .optionsProvider(vm -> List.of())
                .newValuesHandler((vm, values) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseRadioDescription(org.eclipse.sirius.components.view.RadioDescription viewRadioDescription) {
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

        // @formatter:off
        return RadioDescription.newRadioDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewRadioDescription, "Radio")) //$NON-NLS-1$
                .optionIdProvider(vm -> "") //$NON-NLS-1$
                .optionLabelProvider(vm -> "") //$NON-NLS-1$
                .optionSelectedProvider(vm -> true)
                .optionsProvider(vm -> List.of())
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on

    }

    @Override
    public AbstractWidgetDescription caseFlexboxContainerDescription(org.eclipse.sirius.components.view.FlexboxContainerDescription viewFlexboxContainerDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewFlexboxContainerDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        FlexDirection flexDirection = FlexDirection.valueOf(viewFlexboxContainerDescription.getFlexDirection().getName());
        List<AbstractWidgetDescription> children = new ArrayList<>();
        viewFlexboxContainerDescription.getChildren().forEach(viewWidgetDescription -> {
            children.add(ViewFormDescriptionEditorConverterSwitch.this.doSwitch(viewWidgetDescription));

        });

        // @formatter:off
        return FlexboxContainerDescription.newFlexboxContainerDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewFlexboxContainerDescription, "FlexboxContainer")) //$NON-NLS-1$
                .flexDirection(flexDirection)
                .children(children)
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseButtonDescription(org.eclipse.sirius.components.view.ButtonDescription viewButtonDescription) {
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

        // @formatter:off
        return ButtonDescription.newButtonDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewButtonDescription, "Button")) //$NON-NLS-1$
                .buttonLabelProvider(vm -> viewButtonDescription.getButtonLabelExpression())
                .imageURLProvider(vm -> viewButtonDescription.getImageExpression())
                .pushButtonHandler(vm -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseLabelDescription(org.eclipse.sirius.components.view.LabelDescription viewLabelDescription) {
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

        // @formatter:off
        return LabelDescription.newLabelDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewLabelDescription, "Label")) //$NON-NLS-1$
                .valueProvider(vm -> "") //$NON-NLS-1$
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseLinkDescription(org.eclipse.sirius.components.view.LinkDescription viewLinkDescription) {
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

        // @formatter:off
        return LinkDescription.newLinkDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewLinkDescription, "Link")) //$NON-NLS-1$
                .urlProvider(vm -> "") //$NON-NLS-1$
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseListDescription(org.eclipse.sirius.components.view.ListDescription viewListDescription) {
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

        // @formatter:off
        ListDescription.Builder builder = ListDescription.newListDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewListDescription, "List")) //$NON-NLS-1$
                .itemsProvider(vm -> List.of())
                .itemKindProvider(vm -> "") //$NON-NLS-1$
                .itemDeleteHandlerProvider(vm -> new Success())
                .itemImageURLProvider(vm -> "") //$NON-NLS-1$
                .itemIdProvider(vm -> "") //$NON-NLS-1$
                .itemLabelProvider(vm -> "") //$NON-NLS-1$
                .itemDeletableProvider(vm -> false)
                .itemClickHandlerProvider(vm -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .styleProvider(styleProvider);
        // @formatter:on

        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseImageDescription(org.eclipse.sirius.components.view.ImageDescription viewImageDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewImageDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        // @formatter:off
        return ImageDescription.newImageDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewImageDescription, "Image")) //$NON-NLS-1$
                .urlProvider(vm -> Optional.ofNullable(viewImageDescription.getUrlExpression()).orElse("")) //$NON-NLS-1$
                .maxWidthProvider(vm -> viewImageDescription.getMaxWidthExpression())
                .diagnosticsProvider(vm -> List.of()).kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseRichTextDescription(org.eclipse.sirius.components.view.RichTextDescription viewRichTextDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewRichTextDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        // @formatter:off
        return RichTextDescription.newRichTextDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(viewRichTextDescription, "RichText")) //$NON-NLS-1$
                .valueProvider(vm -> "") //$NON-NLS-1$
                .newValueHandler((vm, value) -> new Success())
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    @Override
    public AbstractWidgetDescription caseBarChartDescription(org.eclipse.sirius.components.view.BarChartDescription viewBarChartDescription) {
        Function<VariableManager, BarChartStyle> styleProvider = vm -> {
            BarChartDescriptionStyle style = viewBarChartDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new BarChartStyleProvider(style).build();
        };

        // @formatter:off
        IChartDescription chartDescription = BarChartDescription.newBarChartDescription(UUID.randomUUID().toString())
                .label(Optional.ofNullable(viewBarChartDescription.getName()).orElse("")) //$NON-NLS-1$
                .labelProvider(vm -> this.getWidgetLabel(viewBarChartDescription, "BarChart")) //$NON-NLS-1$
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
    public AbstractWidgetDescription casePieChartDescription(org.eclipse.sirius.components.view.PieChartDescription viewPieChartDescription) {
        Function<VariableManager, PieChartStyle> styleProvider = vm -> {
            PieChartDescriptionStyle style = viewPieChartDescription.getStyle();
            if (style == null) {
                return null;
            }
            return new PieChartStyleProvider(style).build();
        };

        // @formatter:off
        IChartDescription chartDescription =  PieChartDescription.newPieChartDescription(UUID.randomUUID().toString())
                .label(this.getWidgetLabel(viewPieChartDescription, "PieChart")) //$NON-NLS-1$
                .keysProvider(vm -> List.of())
                .valuesProvider(vm -> List.of())
                .styleProvider(styleProvider)
                .build();
        // @formatter:on
        return this.createChartWidgetDescription(viewPieChartDescription, chartDescription);
    }

    private AbstractWidgetDescription createChartWidgetDescription(org.eclipse.sirius.components.view.WidgetDescription widgetDescription, IChartDescription chartDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, widgetDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

        // @formatter:off
        return ChartWidgetDescription.newChartWidgetDescription(chartDescription.getId())
                .idProvider(vm -> id)
                .labelProvider(vm -> this.getWidgetLabel(widgetDescription, "Chart")) //$NON-NLS-1$
                .chartDescription(chartDescription)
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "") //$NON-NLS-1$
                .messageProvider(object -> "") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    public String getWidgetLabel(org.eclipse.sirius.components.view.WidgetDescription widgetDescription, String defaultLabel) {
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
}
