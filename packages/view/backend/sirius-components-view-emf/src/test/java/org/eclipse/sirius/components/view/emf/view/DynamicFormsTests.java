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
package org.eclipse.sirius.components.view.emf.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.charts.IChart;
import org.eclipse.sirius.components.charts.barchart.BarChart;
import org.eclipse.sirius.components.charts.barchart.BarChartEntry;
import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle;
import org.eclipse.sirius.components.charts.piechart.PieChart;
import org.eclipse.sirius.components.charts.piechart.PieChartEntry;
import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.AbstractFontStyle;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Button;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.ChartWidget;
import org.eclipse.sirius.components.forms.Checkbox;
import org.eclipse.sirius.components.forms.CheckboxStyle;
import org.eclipse.sirius.components.forms.ClickEventKind;
import org.eclipse.sirius.components.forms.FlexboxContainer;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Image;
import org.eclipse.sirius.components.forms.LabelWidget;
import org.eclipse.sirius.components.forms.LabelWidgetStyle;
import org.eclipse.sirius.components.forms.Link;
import org.eclipse.sirius.components.forms.LinkStyle;
import org.eclipse.sirius.components.forms.ListItem;
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.forms.MultiSelect;
import org.eclipse.sirius.components.forms.MultiSelectStyle;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.forms.Radio;
import org.eclipse.sirius.components.forms.RadioStyle;
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.forms.SelectStyle;
import org.eclipse.sirius.components.forms.SplitButton;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.TextfieldStyle;
import org.eclipse.sirius.components.forms.TreeWidget;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.renderer.FormRenderer;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.emf.ViewConverter;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.components.view.form.BarChartDescription;
import org.eclipse.sirius.components.view.form.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.ButtonDescription;
import org.eclipse.sirius.components.view.form.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.form.CheckboxDescription;
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalLabelDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalPieChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalRadioDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalTextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalTextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.ImageDescription;
import org.eclipse.sirius.components.view.form.LabelDescription;
import org.eclipse.sirius.components.view.form.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.form.LinkDescription;
import org.eclipse.sirius.components.view.form.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.form.ListDescription;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.eclipse.sirius.components.view.form.MultiSelectDescription;
import org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.PieChartDescription;
import org.eclipse.sirius.components.view.form.PieChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.RadioDescription;
import org.eclipse.sirius.components.view.form.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.form.SelectDescription;
import org.eclipse.sirius.components.view.form.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.SplitButtonDescription;
import org.eclipse.sirius.components.view.form.TextAreaDescription;
import org.eclipse.sirius.components.view.form.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.TextfieldDescription;
import org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.TreeDescription;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;

/**
 * Tests for dynamically defined forms.
 *
 * @author fbarbin
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DynamicFormsTests {

    private final EClass[] eClasses = new EClass[3];

    @Test
    public void testRenderEcoreForm() {
        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(false, false);
        Form result = this.render(eClassFormDescription, this.eClasses[0]);

        assertThat(result).isNotNull();
        assertThat(result.getPages()).hasSize(1);
        assertThat(result.getPages()).extracting(Page::getGroups).hasSize(1);

        Group group = result.getPages().get(0).getGroups().get(0);
        assertThat(group.getWidgets()).hasSize(16);
        Textfield textfield = (Textfield) group.getWidgets().get(0);
        Textarea textarea = (Textarea) group.getWidgets().get(1);
        MultiSelect multiSelect = (MultiSelect) group.getWidgets().get(2);
        Checkbox checkBox = (Checkbox) group.getWidgets().get(3);
        Select select = (Select) group.getWidgets().get(4);
        Radio radio = (Radio) group.getWidgets().get(5);
        ChartWidget chartWidgetWithBarChart = (ChartWidget) group.getWidgets().get(6);
        ChartWidget chartWidgetWithPieChart = (ChartWidget) group.getWidgets().get(7);
        FlexboxContainer flexboxContainer = (FlexboxContainer) group.getWidgets().get(8);
        Button button = (Button) group.getWidgets().get(9);
        LabelWidget labelWidget = (LabelWidget) group.getWidgets().get(10);
        Link link = (Link) group.getWidgets().get(11);
        org.eclipse.sirius.components.forms.List list = (org.eclipse.sirius.components.forms.List) group.getWidgets().get(12);
        Image image = (Image) group.getWidgets().get(13);
        TreeWidget tree = (TreeWidget) group.getWidgets().get(14);
        SplitButton splitButton = (SplitButton) group.getWidgets().get(15);

        this.checkTextfield(textfield, false, false);
        this.checkTextarea(textarea, false, false);
        this.checkMultiSelect(multiSelect, false, false);
        this.checkCheckbox(checkBox, false, false);
        this.checkSelect(select, false, false);
        this.checkRadio(radio, false, false);
        this.checkButton(button, false, false);
        this.checkLabel(labelWidget, false, false);
        this.checkLink(link, false, false);
        this.checkList(list, false, false);
        this.checkBarChart(chartWidgetWithBarChart, false, false);
        this.checkPieChart(chartWidgetWithPieChart, false, false);
        this.checkImage(image);
        this.checkFlexboxContainer(flexboxContainer, false, false);
        this.checkTree(tree);
        this.checkSplitButton(splitButton, false, false);
    }

    @Test
    public void testRenderEcoreFormWithStyle() {
        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(true, false);
        Form result = this.render(eClassFormDescription, this.eClasses[0]);

        assertThat(result).isNotNull();
        assertThat(result.getPages()).hasSize(1);
        assertThat(result.getPages()).extracting(Page::getGroups).hasSize(1);

        Group group = result.getPages().get(0).getGroups().get(0);
        assertThat(group.getWidgets()).hasSize(16);
        Textfield textfield = (Textfield) group.getWidgets().get(0);
        Textarea textarea = (Textarea) group.getWidgets().get(1);
        MultiSelect multiSelect = (MultiSelect) group.getWidgets().get(2);
        Checkbox checkBox = (Checkbox) group.getWidgets().get(3);
        Select select = (Select) group.getWidgets().get(4);
        Radio radio = (Radio) group.getWidgets().get(5);
        ChartWidget chartWidgetWithBarChart = (ChartWidget) group.getWidgets().get(6);
        ChartWidget chartWidgetWithPieChart = (ChartWidget) group.getWidgets().get(7);
        FlexboxContainer flexboxContainer = (FlexboxContainer) group.getWidgets().get(8);
        Button button = (Button) group.getWidgets().get(9);
        LabelWidget labelWidget = (LabelWidget) group.getWidgets().get(10);
        Link link = (Link) group.getWidgets().get(11);
        org.eclipse.sirius.components.forms.List list = (org.eclipse.sirius.components.forms.List) group.getWidgets().get(12);
        SplitButton splitButton = (SplitButton) group.getWidgets().get(15);

        this.checkTextfield(textfield, true, false);
        this.checkTextarea(textarea, true, false);
        this.checkMultiSelect(multiSelect, true, false);
        this.checkCheckbox(checkBox, true, false);
        this.checkSelect(select, true, false);
        this.checkRadio(radio, true, false);
        this.checkButton(button, true, false);
        this.checkLabel(labelWidget, true, false);
        this.checkLink(link, true, false);
        this.checkList(list, true, false);
        this.checkBarChart(chartWidgetWithBarChart, true, false);
        this.checkPieChart(chartWidgetWithPieChart, true, false);
        // image widget doesn't have style
        this.checkFlexboxContainer(flexboxContainer, true, false);
        this.checkSplitButton(splitButton, true, false);
    }

    @Test
    public void testRenderEcoreFormWithConditionalStyle() {
        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(true, true);
        Form result = this.render(eClassFormDescription, this.eClasses[0]);

        assertThat(result).isNotNull();
        assertThat(result.getPages()).hasSize(1);
        assertThat(result.getPages()).extracting(Page::getGroups).hasSize(1);

        Group group = result.getPages().get(0).getGroups().get(0);
        assertThat(group.getWidgets()).hasSize(16);
        Textfield textfield = (Textfield) group.getWidgets().get(0);
        Textarea textarea = (Textarea) group.getWidgets().get(1);
        MultiSelect multiSelect = (MultiSelect) group.getWidgets().get(2);
        Checkbox checkBox = (Checkbox) group.getWidgets().get(3);
        Select select = (Select) group.getWidgets().get(4);
        Radio radio = (Radio) group.getWidgets().get(5);
        ChartWidget chartWidgetWithBarChart = (ChartWidget) group.getWidgets().get(6);
        ChartWidget chartWidgetWithPieChart = (ChartWidget) group.getWidgets().get(7);
        FlexboxContainer flexboxContainer = (FlexboxContainer) group.getWidgets().get(8);
        Button button = (Button) group.getWidgets().get(9);
        LabelWidget labelWidget = (LabelWidget) group.getWidgets().get(10);
        Link link = (Link) group.getWidgets().get(11);
        org.eclipse.sirius.components.forms.List list = (org.eclipse.sirius.components.forms.List) group.getWidgets().get(12);
        SplitButton splitButton = (SplitButton) group.getWidgets().get(15);

        this.checkTextfield(textfield, false, true);
        this.checkTextarea(textarea, false, true);
        this.checkMultiSelect(multiSelect, false, true);
        this.checkCheckbox(checkBox, false, true);
        this.checkSelect(select, false, true);
        this.checkRadio(radio, false, true);
        this.checkButton(button, false, true);
        this.checkLabel(labelWidget, false, true);
        this.checkLink(link, false, true);
        this.checkList(list, false, true);
        this.checkBarChart(chartWidgetWithBarChart, false, true);
        this.checkPieChart(chartWidgetWithPieChart, false, true);
        // image widget doesn't have conditional style
        this.checkFlexboxContainer(flexboxContainer, false, true);
        this.checkSplitButton(splitButton, false, true);
    }

    @Test
    public void testEditingEcoreForm() {
        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(false, false);
        Form form = this.render(eClassFormDescription, this.eClasses[0]);
        assertThat(form.getPages()).flatExtracting(Page::getGroups).flatExtracting(Group::getWidgets).hasSize(16);

        this.checkValuesEditing(this.eClasses[0], this.eClasses[1], form);
    }

    @Test
    public void testSetNullOnSelectEcoreForm() {
        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(false, false);
        Form form = this.render(eClassFormDescription, this.eClasses[0]);
        Group group = form.getPages().get(0).getGroups().get(0);
        Select select = (Select) group.getWidgets().get(4);
        assertThat(select.getValue()).isEqualTo("Class2");
        assertThat(this.eClasses[0].getESuperTypes().isEmpty()).isFalse();
        select.getNewValueHandler().apply(null);
        assertThat(this.eClasses[0].getESuperTypes().isEmpty()).isTrue();
    }

    private void checkTextfield(Textfield textfield, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(textfield.getValue()).isEqualTo("Class1");
        assertThat(textfield.getLabel()).isEqualTo("EClass name");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(textfield);
        } else if (checkStyle) {
            this.testStyle(textfield);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(textfield);
        }
    }

    private void checkTextarea(Textarea textarea, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(textarea.getValue()).isEqualTo("Class1Instance");
        assertThat(textarea.getLabel()).isEqualTo("Instance Class Name");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(textarea);
        } else if (checkStyle) {
            this.testStyle(textarea);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(textarea);
        }
    }

    private void checkMultiSelect(MultiSelect multiSelect, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(multiSelect.getOptions()).hasSize(3);
        assertThat(multiSelect.getValues()).hasSize(2);
        assertThat(multiSelect.getValues()).containsExactlyInAnyOrder("Class2", "Class3");
        assertThat(multiSelect.getLabel()).isEqualTo("ESuperTypes");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(multiSelect);
        } else if (checkStyle) {
            this.testStyle(multiSelect);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(multiSelect);
        }
    }

    private void checkCheckbox(Checkbox checkBox, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(checkBox.isValue()).isTrue();
        assertThat(checkBox.getLabel()).isEqualTo("is Abstract");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(checkBox);
        } else if (checkStyle) {
            this.testStyle(checkBox);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(checkBox);
        }
    }

    private void checkSelect(Select select, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(select.getOptions()).hasSize(3);
        assertThat(select.getValue()).isEqualTo("Class2");
        assertThat(select.getLabel()).isEqualTo("eSuper Types");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(select);
        } else if (checkStyle) {
            this.testStyle(select);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(select);
        }
    }

    private void checkRadio(Radio radio, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(radio.getOptions()).hasSize(3);
        assertThat(radio.getOptions()).allSatisfy(option -> {
            if (option.getLabel().equals("Class2")) {
                assertThat(option.isSelected()).isTrue();
            } else {
                assertThat(option.isSelected()).isFalse();
            }
        });
        assertThat(radio.getLabel()).isEqualTo("ESuperTypes");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(radio);
        } else if (checkStyle) {
            this.testStyle(radio);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(radio);
        }
    }

    private void checkButton(Button button, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(button.getButtonLabel()).isEqualTo("Class1");
        assertThat(button.getLabel()).isEqualTo("EClass name");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(button);
        } else if (checkStyle) {
            this.testStyle(button);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(button);
        }
    }

    private void checkLabel(LabelWidget labelWidget, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(labelWidget.getValue()).isEqualTo("Class1");
        assertThat(labelWidget.getLabel()).isEqualTo("Label EClass name");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(labelWidget);
        } else if (checkStyle) {
            this.testStyle(labelWidget);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(labelWidget);
        }
    }

    private void checkLink(Link link, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(link.getLabel()).isEqualTo("Label EClass link");
        assertThat(link.getUrl()).isEqualTo("myHyperLink");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(link);
        } else if (checkStyle) {
            this.testStyle(link);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(link);
        }
    }

    private void checkList(org.eclipse.sirius.components.forms.List list, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(list.getLabel()).isEqualTo("Label EClass List");
        List<ListItem> items = list.getItems();
        assertThat(items).hasSize(3);
        for (int i = 0; i < 3; i++) {
            assertThat(items.get(i).getLabel()).isEqualTo("Class" + (i + 1));
            assertThat(items.get(i).isDeletable()).isTrue();
            items.get(i).getClickHandler().apply(ClickEventKind.DOUBLE_CLICK);
            assertThat(this.eClasses[i].getName()).isEqualTo("Class" + (i + 1) + " click event kind: DOUBLE_CLICK");
            items.get(i).getClickHandler().apply(ClickEventKind.SINGLE_CLICK);
            assertThat(this.eClasses[i].getName()).isEqualTo("Class" + (i + 1) + " click event kind: DOUBLE_CLICK click event kind: SINGLE_CLICK");
            this.eClasses[i].setName("Class" + (i + 1));
        }
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(list);
        } else if (checkStyle) {
            this.testStyle(list);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(list);
        }
    }

    private void checkPieChart(ChartWidget chartWidgetWithPieChart, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(chartWidgetWithPieChart.getLabel()).isEqualTo("The Chart Widget label");
        IChart chart = chartWidgetWithPieChart.getChart();
        assertThat(chart).isInstanceOf(PieChart.class);
        PieChart pieChart = (PieChart) chart;
        List<PieChartEntry> pieChartEntries = pieChart.getEntries();
        this.checkPieChartEntry(pieChartEntries, 0, "a", 1);
        this.checkPieChartEntry(pieChartEntries, 1, "b", 3);
        this.checkPieChartEntry(pieChartEntries, 2, "c", 5);
        this.checkPieChartEntry(pieChartEntries, 3, "d", 7);
        PieChartStyle style = pieChart.getStyle();
        if (checkStyle) {
            assertThat(style).isNotNull();
            assertThat(style.getColors()).isEqualTo(List.of("AliceBlue", "AntiqueWhite", "DarkMagenta", "DarkGreen"));
            assertThat(style.getStrokeColor()).isEqualTo("Orchid");
            assertThat(style.getStrokeWidth()).isEqualTo(3);
            assertThat(style.getFontSize()).isEqualTo(20);
            assertThat(style.isItalic()).isTrue();
            assertThat(style.isBold()).isTrue();
            assertThat(style.isUnderline()).isTrue();
            assertThat(style.isStrikeThrough()).isTrue();
        } else if (checkConditionalStyle) {
            assertThat(style).isNotNull();
            assertThat(style.getColors()).isEqualTo(List.of("CadetBlue", "AntiqueWhite", "DarkMagenta", "Coral"));
            assertThat(style.getStrokeColor()).isEqualTo("PaleGoldenRod");
            assertThat(style.getStrokeWidth()).isEqualTo(2);
            assertThat(style.getFontSize()).isEqualTo(30);
            assertThat(style.isItalic()).isTrue();
            assertThat(style.isBold()).isFalse();
            assertThat(style.isUnderline()).isTrue();
            assertThat(style.isStrikeThrough()).isFalse();
        }
    }

    private void checkBarChart(ChartWidget chartWidgetWithBarChart, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(chartWidgetWithBarChart.getLabel()).isEqualTo("The Chart Widget label");
        IChart chart = chartWidgetWithBarChart.getChart();
        assertThat(chart).isInstanceOf(BarChart.class);
        BarChart barChart = (BarChart) chart;
        assertThat(barChart.getYAxisLabel()).isEqualTo("the values");
        List<BarChartEntry> barChartEntries = barChart.getEntries();
        this.checkBarChartEntry(barChartEntries, 0, "a", 1);
        this.checkBarChartEntry(barChartEntries, 1, "b", 3);
        this.checkBarChartEntry(barChartEntries, 2, "c", 5);
        this.checkBarChartEntry(barChartEntries, 3, "d", 7);
        BarChartStyle style = barChart.getStyle();
        if (checkStyle) {
            assertThat(style).isNotNull();
            assertThat(style.getBarsColor()).isEqualTo("Orchid");
            assertThat(style.getFontSize()).isEqualTo(20);
            assertThat(style.isItalic()).isTrue();
            assertThat(style.isBold()).isTrue();
            assertThat(style.isUnderline()).isTrue();
            assertThat(style.isStrikeThrough()).isTrue();
        } else if (checkConditionalStyle) {
            assertThat(style).isNotNull();
            assertThat(style.getBarsColor()).isEqualTo("PaleGoldenRod");
            assertThat(style.getFontSize()).isEqualTo(30);
            assertThat(style.isItalic()).isTrue();
            assertThat(style.isBold()).isFalse();
            assertThat(style.isUnderline()).isTrue();
            assertThat(style.isStrikeThrough()).isFalse();
        }
    }

    private void checkBarChartEntry(List<BarChartEntry> entries, int index, String expectedKey, Number expectedValue) {
        assertThat(entries.get(index)).extracting(BarChartEntry::getKey).isEqualTo(expectedKey);
        assertThat(entries.get(index)).extracting(BarChartEntry::getValue).isEqualTo(expectedValue);
    }

    private void checkPieChartEntry(List<PieChartEntry> entries, int index, String expectedKey, Number expectedValue) {
        assertThat(entries.get(index)).extracting(PieChartEntry::getKey).isEqualTo(expectedKey);
        assertThat(entries.get(index)).extracting(PieChartEntry::getValue).isEqualTo(expectedValue);
    }

    private void checkImage(Image image) {
        assertThat(image.getLabel()).isEqualTo("Icon for EClass Class1");
        assertThat(image.getUrl()).isEqualTo("icons/Class1.svg");
        assertThat(image.getMaxWidth()).isEqualTo("30%");
    }

    private void checkTree(TreeWidget tree) {
        assertThat(tree.getLabel()).isEqualTo("Tree for EClass Class1");
        assertThat(tree.getNodes()).hasSize(2);
        assertThat(tree.getNodes().get(0).isValue()).isFalse();
        assertThat(tree.getNodes().get(0).getLabel()).isEqualTo("Tree for EClass Class2");
        assertThat(tree.getNodes().get(1).getLabel()).isEqualTo("Tree for EClass Class3");
        assertThat(tree.getNodes().get(0).isCheckable()).isTrue();
        assertThat(tree.getNodes().get(0).isSelectable()).isTrue();
        assertThat(tree.getNodes().get(0).getIconURL()).hasSize(1);
        assertThat(tree.getNodes().get(0).getEndIconsURL()).hasSize(1);
        assertThat(tree.getNodes().get(0).getEndIconsURL().get(0)).hasSize(1);
        assertThat(tree.getNodes().get(0).getIconURL().get(0)).isEqualTo("icons/Class2.svg");
        assertThat(tree.getNodes().get(0).getEndIconsURL().get(0).get(0)).isEqualTo("icons/Class2.svg");
        assertThat(tree.getNodes().get(1).getIconURL().get(0)).isEqualTo("icons/Class3.svg");
        assertThat(tree.getNodes().get(1).getEndIconsURL().get(0).get(0)).isEqualTo("icons/Class3.svg");
    }

    private void checkSplitButton(SplitButton splitButton, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(splitButton.getLabel()).isEqualTo("SplitButton for EClass Class1");
        assertThat(splitButton.getActions()).hasSize(2);
        this.checkButton(splitButton.getActions().get(0), checkStyle, checkConditionalStyle);
        this.checkButton(splitButton.getActions().get(1), checkStyle, checkConditionalStyle);
    }

    private void checkFlexboxContainer(FlexboxContainer flexboxContainer, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(flexboxContainer.getLabel()).isEqualTo("A Widget Container");
        List<AbstractWidget> children = flexboxContainer.getChildren();
        assertThat(children).hasSize(2);
        assertThat(children.get(0)).isInstanceOf(Textfield.class);
        assertThat(children.get(1)).isInstanceOf(Checkbox.class);

        Textfield childrenTextfield = (Textfield) children.get(0);
        assertThat(childrenTextfield.getValue()).isEqualTo("Class1");
        assertThat(childrenTextfield.getLabel()).isEqualTo("EClass name");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(childrenTextfield);
        } else if (checkStyle) {
            this.testStyle(childrenTextfield);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(childrenTextfield);
        }

        Checkbox childrenCheckbox = (Checkbox) children.get(1);
        assertThat(childrenCheckbox.isValue()).isTrue();
        assertThat(childrenCheckbox.getLabel()).isEqualTo("is Abstract");
        if (!(checkStyle || checkConditionalStyle)) {
            this.testNoStyle(childrenCheckbox);
        } else if (checkStyle) {
            this.testStyle(childrenCheckbox);
        } else if (checkConditionalStyle) {
            this.testConditionalStyle(childrenCheckbox);
        }
    }

    private void checkValuesEditing(EClass eClass, EClass eClass2, Form form) {
        Group group = form.getPages().get(0).getGroups().get(0);
        assertThat(group.getWidgets()).hasSize(16);

        Textfield textfield = (Textfield) group.getWidgets().get(0);
        assertThat(textfield.getValue()).isEqualTo("Class1");
        assertThat(eClass.getName()).isEqualTo("Class1");
        textfield.getNewValueHandler().apply("my New Value");
        assertThat(eClass.getName()).isEqualTo("my New Value");

        Textarea textarea = (Textarea) group.getWidgets().get(1);
        assertThat(textarea.getValue()).isEqualTo("Class1Instance");
        assertThat(eClass.getInstanceClassName()).isEqualTo("Class1Instance");
        textarea.getNewValueHandler().apply("newInstanceName");
        assertThat(eClass.getInstanceClassName()).isEqualTo("newInstanceName");

        MultiSelect multiSelect = (MultiSelect) group.getWidgets().get(2);
        assertThat(multiSelect.getValues()).hasSize(2);
        assertThat(multiSelect.getValues()).containsExactlyInAnyOrder("Class2", "Class3");
        multiSelect.getNewValuesHandler().apply(List.of("Class2"));
        assertThat(eClass.getESuperTypes()).containsExactlyInAnyOrder(this.eClasses[1]);

        Checkbox checkBox = (Checkbox) group.getWidgets().get(3);
        assertThat(checkBox.isValue()).isTrue();
        assertThat(eClass.isAbstract()).isTrue();
        checkBox.getNewValueHandler().apply(false);
        assertThat(eClass.isAbstract()).isFalse();

        Select select = (Select) group.getWidgets().get(4);
        assertThat(select.getOptions()).hasSize(3);
        assertThat(select.getValue()).isEqualTo("Class2");
        select.getNewValueHandler().apply("Class3");
        assertThat(this.eClasses[0].getESuperTypes()).containsExactlyInAnyOrder(this.eClasses[2]);

        Radio radio = (Radio) group.getWidgets().get(5);
        assertThat(radio.getOptions()).hasSize(3);
        radio.getOptions().forEach(option -> {
            if (option.getLabel().equals("Class2")) {
                assertThat(option.isSelected()).isTrue();
            } else {
                assertThat(option.isSelected()).isFalse();
            }
        });

        this.renderOnEcoreFormWithStyleOnWidgetContainer(eClass, group);

        Button button = (Button) group.getWidgets().get(9);
        assertThat(button.getButtonLabel()).isEqualTo("Class1");

        TreeWidget tree = (TreeWidget) group.getWidgets().get(14);
        assertThat(tree.getNodes().get(0).isValue()).isFalse();
        assertThat(eClass2.isAbstract()).isFalse();
        tree.getNodes().get(0).getNewValueHandler().apply(true);
        assertThat(eClass2.isAbstract()).isTrue();

    }

    private void renderOnEcoreFormWithStyleOnWidgetContainer(EClass eClass, Group group) {
        FlexboxContainer flexboxContainer = (FlexboxContainer) group.getWidgets().get(8);
        assertThat(flexboxContainer.getChildren()).hasSize(2);
        assertThat(flexboxContainer.getChildren().get(0)).isInstanceOf(Textfield.class);
        assertThat(flexboxContainer.getChildren().get(1)).isInstanceOf(Checkbox.class);

        Textfield childrenTextfield = (Textfield) flexboxContainer.getChildren().get(0);
        assertThat(childrenTextfield.getValue()).isEqualTo("Class1");
        assertThat(eClass.getName()).isEqualTo("my New Value");
        childrenTextfield.getNewValueHandler().apply("my New Value 2");
        assertThat(eClass.getName()).isEqualTo("my New Value 2");

        Checkbox childrenCheckbox = (Checkbox) flexboxContainer.getChildren().get(1);
        assertThat(childrenCheckbox.isValue()).isTrue();
        assertThat(eClass.isAbstract()).isFalse();
        childrenCheckbox.getNewValueHandler().apply(true);
        assertThat(eClass.isAbstract()).isTrue();
    }

    private FormDescription createClassFormDescription(boolean withStyle, boolean withConditionalStyle) {
        FormDescription formDescription = FormFactory.eINSTANCE.createFormDescription();
        formDescription.setName("Simple Ecore Form");
        formDescription.setTitleExpression("aql:self.name");
        formDescription.setDomainType("ecore::EClass");
        PageDescription pageDescription = FormFactory.eINSTANCE.createPageDescription();
        GroupDescription groupDescription = FormFactory.eINSTANCE.createGroupDescription();
        groupDescription.setLabelExpression("aql:self.name");
        pageDescription.getGroups().add(groupDescription);
        formDescription.getPages().add(pageDescription);
        TextfieldDescription textfieldDescription = this.createTextfield(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(textfieldDescription);
        TextAreaDescription textAreaDescription = this.createTextArea(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(textAreaDescription);
        MultiSelectDescription multiSelectDescription = this.createMultiSelect(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(multiSelectDescription);
        CheckboxDescription checkboxDescription = this.createCheckbox(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(checkboxDescription);
        SelectDescription selectDescription = this.createSelect(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(selectDescription);
        RadioDescription radioDescription = this.createRadio(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(radioDescription);
        BarChartDescription barChartDescription = this.createBarChart(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(barChartDescription);
        PieChartDescription pieChartDescription = this.createPieChart(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(pieChartDescription);
        FlexboxContainerDescription flexboxContainerDescription = this.createFlexboxContainer(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(flexboxContainerDescription);
        ButtonDescription buttonDescription = this.createButton(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(buttonDescription);
        LabelDescription labelDescription = this.createLabel(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(labelDescription);
        LinkDescription linkDescription = this.createLink(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(linkDescription);
        ListDescription listDescription = this.createList(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(listDescription);
        ImageDescription imageDescription = this.createImage();
        groupDescription.getChildren().add(imageDescription);
        TreeDescription treeDescription = this.createTreeDescription();
        groupDescription.getChildren().add(treeDescription);
        SplitButtonDescription splitButtonDescription = this.createSplitButton(withStyle, withConditionalStyle);
        groupDescription.getChildren().add(splitButtonDescription);
        return formDescription;
    }

    private BarChartDescription createBarChart(boolean withStyle, boolean withConditionalStyle) {
        BarChartDescription barChartDescription = FormFactory.eINSTANCE.createBarChartDescription();
        barChartDescription.setName("barChart");
        barChartDescription.setLabelExpression("aql:'The Chart Widget label'");
        barChartDescription.setYAxisLabelExpression("aql:'the values'");
        barChartDescription.setKeysExpression("aql:Sequence{'a','b','c','d'}");
        barChartDescription.setValuesExpression("aql:Sequence{1,3,5,7}");
        if (withStyle) {
            BarChartDescriptionStyle style = FormFactory.eINSTANCE.createBarChartDescriptionStyle();
            style.setBarsColor("Orchid");
            this.setFontStyle(style);
            barChartDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalBarChartDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalBarChartDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setBarsColor("PaleGoldenRod");
            this.setConditionalFontStyle(conditionalStyle);
            barChartDescription.getConditionalStyles().add(conditionalStyle);
        }

        return barChartDescription;
    }

    private PieChartDescription createPieChart(boolean withStyle, boolean withConditionalStyle) {
        PieChartDescription pieChartDescription = FormFactory.eINSTANCE.createPieChartDescription();
        pieChartDescription.setName("chartWidget");
        pieChartDescription.setLabelExpression("aql:'The Chart Widget label'");
        pieChartDescription.setName("pieChart");
        pieChartDescription.setKeysExpression("aql:Sequence{'a','b','c','d'}");
        pieChartDescription.setValuesExpression("aql:Sequence{1,3,5,7}");

        if (withStyle) {
            PieChartDescriptionStyle style = FormFactory.eINSTANCE.createPieChartDescriptionStyle();
            style.setColors("aql:Sequence{'AliceBlue','AntiqueWhite','DarkMagenta','DarkGreen'}");
            style.setStrokeColor(this.generateUserColor("Orchid"));
            style.setStrokeWidth(3);
            this.setFontStyle(style);
            pieChartDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalPieChartDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalPieChartDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setColors("aql:Sequence{'CadetBlue','AntiqueWhite','DarkMagenta','Coral'}");
            conditionalStyle.setStrokeColor(this.generateUserColor("PaleGoldenRod"));
            conditionalStyle.setStrokeWidth(2);
            this.setConditionalFontStyle(conditionalStyle);
            pieChartDescription.getConditionalStyles().add(conditionalStyle);
        }
        return pieChartDescription;
    }

    private FlexboxContainerDescription createFlexboxContainer(boolean withStyle, boolean withConditionalStyle) {
        FlexboxContainerDescription flexboxContainerDescription = FormFactory.eINSTANCE.createFlexboxContainerDescription();
        flexboxContainerDescription.setLabelExpression("aql:'A Widget Container'");
        TextfieldDescription textfieldDescription = this.createTextfield(withStyle, withConditionalStyle);
        flexboxContainerDescription.getChildren().add(textfieldDescription);
        CheckboxDescription checkboxDescription = this.createCheckbox(withStyle, withConditionalStyle);
        flexboxContainerDescription.getChildren().add(checkboxDescription);
        return flexboxContainerDescription;
    }

    private RadioDescription createRadio(boolean withStyle, boolean withConditionalStyle) {
        RadioDescription radioDescription = FormFactory.eINSTANCE.createRadioDescription();
        radioDescription.setLabelExpression("aql:'ESuperTypes'");
        radioDescription.setValueExpression("aql:self.eSuperTypes->first()");
        radioDescription.setCandidatesExpression("aql:self.eContainer().eAllContents(ecore::EClass)");
        radioDescription.setCandidateLabelExpression("aql:candidate.name");
        if (withStyle) {
            RadioDescriptionStyle style = FormFactory.eINSTANCE.createRadioDescriptionStyle();
            style.setColor(this.generateUserColor("#de1000"));
            this.setFontStyle(style);
            radioDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalRadioDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalRadioDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setColor(this.generateUserColor("#fbb800"));
            this.setConditionalFontStyle(conditionalStyle);
            radioDescription.getConditionalStyles().add(conditionalStyle);
        }

        SetValue radioSetValue = ViewFactory.eINSTANCE.createSetValue();
        radioSetValue.setFeatureName("eSuperTypes");
        radioSetValue.setValueExpression("aql:newValue");
        radioDescription.getBody().add(radioSetValue);
        return radioDescription;
    }

    private SelectDescription createSelect(boolean withStyle, boolean withConditionalStyle) {
        SelectDescription selectDescription = FormFactory.eINSTANCE.createSelectDescription();
        selectDescription.setLabelExpression("aql:'eSuper Types'");
        selectDescription.setValueExpression("aql:self.eSuperTypes->first()");
        selectDescription.setCandidatesExpression("aql:self.eContainer().eAllContents(ecore::EClass)");
        selectDescription.setCandidateLabelExpression("aql:candidate.name");
        if (withStyle) {
            SelectDescriptionStyle style = FormFactory.eINSTANCE.createSelectDescriptionStyle();
            style.setBackgroundColor(this.generateUserColor("#de1000"));
            style.setForegroundColor(this.generateUserColor("#777777"));
            this.setFontStyle(style);
            selectDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalSelectDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalSelectDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setBackgroundColor(this.generateUserColor("#fbb800"));
            conditionalStyle.setForegroundColor(this.generateUserColor("#134cba"));
            this.setConditionalFontStyle(conditionalStyle);
            selectDescription.getConditionalStyles().add(conditionalStyle);
        }

        UnsetValue selectUnsetValue = ViewFactory.eINSTANCE.createUnsetValue();
        selectUnsetValue.setFeatureName("eSuperTypes");
        selectUnsetValue.setElementExpression("aql:self.eSuperTypes");
        selectDescription.getBody().add(selectUnsetValue);

        SetValue selectSetValue = ViewFactory.eINSTANCE.createSetValue();
        selectSetValue.setFeatureName("eSuperTypes");
        selectSetValue.setValueExpression("aql:newValue");
        selectUnsetValue.getChildren().add(selectSetValue);
        return selectDescription;
    }

    private CheckboxDescription createCheckbox(boolean withStyle, boolean withConditionalStyle) {
        CheckboxDescription checkboxDescription = FormFactory.eINSTANCE.createCheckboxDescription();
        checkboxDescription.setLabelExpression("is Abstract");
        checkboxDescription.setValueExpression("aql:self.abstract");
        if (withStyle) {
            CheckboxDescriptionStyle style = FormFactory.eINSTANCE.createCheckboxDescriptionStyle();
            style.setColor(this.generateUserColor("#de1000"));
            checkboxDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalCheckboxDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalCheckboxDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setColor(this.generateUserColor("#fbb800"));
            checkboxDescription.getConditionalStyles().add(conditionalStyle);
        }

        SetValue checkboxSetValue = ViewFactory.eINSTANCE.createSetValue();
        checkboxSetValue.setFeatureName("abstract");
        checkboxSetValue.setValueExpression("aql:newValue");
        checkboxDescription.getBody().add(checkboxSetValue);
        return checkboxDescription;
    }

    private MultiSelectDescription createMultiSelect(boolean withStyle, boolean withConditionalStyle) {
        MultiSelectDescription multiSelectDescription = FormFactory.eINSTANCE.createMultiSelectDescription();
        multiSelectDescription.setLabelExpression("aql:'ESuperTypes'");
        multiSelectDescription.setValueExpression("aql:self.eSuperTypes");
        multiSelectDescription.setCandidatesExpression("aql:self.eContainer().eAllContents(ecore::EClass)");
        multiSelectDescription.setCandidateLabelExpression("aql:candidate.name");
        if (withStyle) {
            MultiSelectDescriptionStyle style = FormFactory.eINSTANCE.createMultiSelectDescriptionStyle();
            style.setBackgroundColor(this.generateUserColor("#de1000"));
            style.setForegroundColor(this.generateUserColor("#777777"));
            this.setFontStyle(style);
            multiSelectDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalMultiSelectDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalMultiSelectDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setBackgroundColor(this.generateUserColor("#fbb800"));
            conditionalStyle.setForegroundColor(this.generateUserColor("#134cba"));
            this.setConditionalFontStyle(conditionalStyle);
            multiSelectDescription.getConditionalStyles().add(conditionalStyle);
        }

        UnsetValue multiSelectUnsetValue = ViewFactory.eINSTANCE.createUnsetValue();
        multiSelectUnsetValue.setFeatureName("eSuperTypes");
        multiSelectUnsetValue.setElementExpression("aql:self.eSuperTypes");
        multiSelectDescription.getBody().add(multiSelectUnsetValue);

        SetValue multiSelectSetValue = ViewFactory.eINSTANCE.createSetValue();
        multiSelectSetValue.setFeatureName("eSuperTypes");
        multiSelectSetValue.setValueExpression("aql:newValue");
        multiSelectUnsetValue.getChildren().add(multiSelectSetValue);
        return multiSelectDescription;
    }

    private TextAreaDescription createTextArea(boolean withStyle, boolean withConditionalStyle) {
        TextAreaDescription textareaDescription = FormFactory.eINSTANCE.createTextAreaDescription();
        textareaDescription.setLabelExpression("aql:'Instance Class Name'");
        textareaDescription.setValueExpression("aql:self.instanceClassName");
        if (withStyle) {
            TextareaDescriptionStyle style = FormFactory.eINSTANCE.createTextareaDescriptionStyle();
            style.setBackgroundColor(this.generateUserColor("#de1000"));
            style.setForegroundColor(this.generateUserColor("#777777"));
            this.setFontStyle(style);
            textareaDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalTextareaDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalTextareaDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setBackgroundColor(this.generateUserColor("#fbb800"));
            conditionalStyle.setForegroundColor(this.generateUserColor("#134cba"));
            this.setConditionalFontStyle(conditionalStyle);
            textareaDescription.getConditionalStyles().add(conditionalStyle);
        }

        SetValue textareaSetValue = ViewFactory.eINSTANCE.createSetValue();
        textareaSetValue.setFeatureName("instanceClassName");
        textareaSetValue.setValueExpression("aql:newValue");
        textareaDescription.getBody().add(textareaSetValue);
        return textareaDescription;
    }

    private TextfieldDescription createTextfield(boolean withStyle, boolean withConditionalStyle) {
        TextfieldDescription textfieldDescription = FormFactory.eINSTANCE.createTextfieldDescription();
        textfieldDescription.setLabelExpression("aql:'EClass name'");
        textfieldDescription.setValueExpression("aql:self.name");
        textfieldDescription.setName("Class Name");
        if (withStyle) {
            TextfieldDescriptionStyle style = FormFactory.eINSTANCE.createTextfieldDescriptionStyle();
            style.setBackgroundColor(this.generateUserColor("#de1000"));
            style.setForegroundColor(this.generateUserColor("#777777"));
            this.setFontStyle(style);
            textfieldDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalTextfieldDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalTextfieldDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setBackgroundColor(this.generateUserColor("#fbb800"));
            conditionalStyle.setForegroundColor(this.generateUserColor("#134cba"));
            this.setConditionalFontStyle(conditionalStyle);
            textfieldDescription.getConditionalStyles().add(conditionalStyle);
        }

        SetValue setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:newValue");
        textfieldDescription.getBody().add(setValue);
        return textfieldDescription;
    }

    private ButtonDescription createButton(boolean withStyle, boolean withConditionalStyle) {
        ButtonDescription buttonDescription = FormFactory.eINSTANCE.createButtonDescription();
        buttonDescription.setLabelExpression("aql:'EClass name'");
        buttonDescription.setButtonLabelExpression("aql:self.name");
        buttonDescription.setName("Class Name");
        if (withStyle) {
            ButtonDescriptionStyle style = FormFactory.eINSTANCE.createButtonDescriptionStyle();
            style.setBackgroundColor(this.generateUserColor("#de1000"));
            style.setForegroundColor(this.generateUserColor("#777777"));
            this.setFontStyle(style);
            buttonDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalButtonDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalButtonDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setBackgroundColor(this.generateUserColor("#fbb800"));
            conditionalStyle.setForegroundColor(this.generateUserColor("#134cba"));
            this.setConditionalFontStyle(conditionalStyle);
            buttonDescription.getConditionalStyles().add(conditionalStyle);
        }

        SetValue setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:newValue");
        buttonDescription.getBody().add(setValue);
        return buttonDescription;
    }

    private LabelDescription createLabel(boolean withStyle, boolean withConditionalStyle) {
        LabelDescription labelDescription = FormFactory.eINSTANCE.createLabelDescription();
        labelDescription.setLabelExpression("aql:'Label EClass name'");
        labelDescription.setValueExpression("aql:self.name");
        labelDescription.setName("Class Name");
        if (withStyle) {
            LabelDescriptionStyle style = FormFactory.eINSTANCE.createLabelDescriptionStyle();
            style.setColor(this.generateUserColor("#de1000"));
            this.setFontStyle(style);
            labelDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalLabelDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalLabelDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setColor(this.generateUserColor("#fbb800"));
            this.setConditionalFontStyle(conditionalStyle);
            labelDescription.getConditionalStyles().add(conditionalStyle);
        }

        return labelDescription;
    }

    private LinkDescription createLink(boolean withStyle, boolean withConditionalStyle) {
        LinkDescription linkDescription = FormFactory.eINSTANCE.createLinkDescription();
        linkDescription.setLabelExpression("aql:'Label EClass link'");
        linkDescription.setValueExpression("myHyperLink");
        linkDescription.setName("Class Name");
        if (withStyle) {
            LinkDescriptionStyle style = FormFactory.eINSTANCE.createLinkDescriptionStyle();
            style.setColor(this.generateUserColor("#de1000"));
            this.setFontStyle(style);
            linkDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalLinkDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalLinkDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setColor(this.generateUserColor("#fbb800"));
            this.setConditionalFontStyle(conditionalStyle);
            linkDescription.getConditionalStyles().add(conditionalStyle);
        }

        return linkDescription;
    }

    private ListDescription createList(boolean withStyle, boolean withConditionalStyle) {
        ListDescription listDescription = FormFactory.eINSTANCE.createListDescription();
        listDescription.setLabelExpression("aql:'Label EClass List'");
        listDescription.setName("Classes list");
        listDescription.setValueExpression("aql:self.eContainer().eAllContents(ecore::EClass)");
        listDescription.setDisplayExpression("aql:candidate.name");
        listDescription.setIsDeletableExpression("aql:true");

        ChangeContext changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:candidate");
        SetValue setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:self.name + ' click event kind: ' + onClickEventKind");
        changeContext.getChildren().add(setValue);
        listDescription.getBody().add(changeContext);

        if (withStyle) {
            ListDescriptionStyle style = FormFactory.eINSTANCE.createListDescriptionStyle();
            style.setColor(this.generateUserColor("lightBlue"));
            this.setFontStyle(style);
            listDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalListDescriptionStyle conditionalStyle = FormFactory.eINSTANCE.createConditionalListDescriptionStyle();
            conditionalStyle.setCondition("aql:true");
            conditionalStyle.setColor(this.generateUserColor("orange"));
            this.setConditionalFontStyle(conditionalStyle);
            listDescription.getConditionalStyles().add(conditionalStyle);
        }

        return listDescription;
    }

    private ImageDescription createImage() {
        ImageDescription imageDescription = FormFactory.eINSTANCE.createImageDescription();
        imageDescription.setName("EClass Icon");
        imageDescription.setLabelExpression("aql:'Icon for EClass ' + self.name");
        imageDescription.setUrlExpression("aql:'icons/' + self.name + '.svg'");
        imageDescription.setMaxWidthExpression("30%");
        return imageDescription;
    }

    private TreeDescription createTreeDescription() {
        TreeDescription treeDescription = FormFactory.eINSTANCE.createTreeDescription();
        treeDescription.setName("EClass Tree");
        treeDescription.setLabelExpression("aql:'Tree for EClass ' + self.name");

        treeDescription.setChildrenExpression("aql:self.eGenericSuperTypes.eRawType");

        treeDescription.setTreeItemLabelExpression("aql:'Tree for EClass ' + self.name");
        treeDescription.setIsCheckableExpression("aql:true");
        treeDescription.setIsTreeItemSelectableExpression("aql:true");
        treeDescription.setCheckedValueExpression("aql:self.abstract");
        treeDescription.setTreeItemBeginIconExpression("aql:Sequence{'icons/' + self.name + '.svg'}");
        treeDescription.setTreeItemEndIconsExpression("aql:Sequence{Sequence{'icons/' + self.name + '.svg'}}");

        SetValue checkboxSetValue = ViewFactory.eINSTANCE.createSetValue();
        checkboxSetValue.setFeatureName("abstract");
        checkboxSetValue.setValueExpression("aql:newValue");
        treeDescription.getBody().add(checkboxSetValue);
        return treeDescription;
    }

    private SplitButtonDescription createSplitButton(boolean withStyle, boolean withConditionalStyle) {
        SplitButtonDescription splitButtonDescription = FormFactory.eINSTANCE.createSplitButtonDescription();
        splitButtonDescription.setName("EClass SplitButton");
        splitButtonDescription.setLabelExpression("aql:'SplitButton for EClass ' + self.name");

        ButtonDescription buttonDescription = this.createButton(withStyle, withConditionalStyle);
        ButtonDescription buttonDescription2 = this.createButton(withStyle, withConditionalStyle);

        splitButtonDescription.getActions().addAll(List.of(buttonDescription, buttonDescription2));
        return splitButtonDescription;
    }

    private void setFontStyle(LabelStyle labelStyle) {
        labelStyle.setFontSize(20);
        labelStyle.setItalic(true);
        labelStyle.setBold(true);
        labelStyle.setUnderline(true);
        labelStyle.setStrikeThrough(true);
    }

    private void setConditionalFontStyle(LabelStyle labelStyle) {
        labelStyle.setFontSize(30);
        labelStyle.setItalic(true);
        labelStyle.setBold(false);
        labelStyle.setUnderline(true);
        labelStyle.setStrikeThrough(false);
    }

    private void testStyle(Textfield textfield) {
        TextfieldStyle textfieldStyle = textfield.getStyle();
        assertThat(textfieldStyle).isNotNull();
        assertThat(textfieldStyle.getBackgroundColor()).isEqualTo("#de1000");
        assertThat(textfieldStyle.getForegroundColor()).isEqualTo("#777777");
        this.testFontStyle(textfieldStyle);
    }

    private void testConditionalStyle(Textfield textfield) {
        TextfieldStyle textfieldStyle = textfield.getStyle();
        assertThat(textfieldStyle).isNotNull();
        assertThat(textfieldStyle.getBackgroundColor()).isEqualTo("#fbb800");
        assertThat(textfieldStyle.getForegroundColor()).isEqualTo("#134cba");
        this.testConditionalFontStyle(textfieldStyle);
    }

    private void testNoStyle(Textfield textfield) {
        TextfieldStyle textfieldStyle = textfield.getStyle();
        assertThat(textfieldStyle).isNull();
    }

    private void testStyle(Textarea textarea) {
        TextareaStyle textareaStyle = textarea.getStyle();
        assertThat(textareaStyle).isNotNull();
        assertThat(textareaStyle.getBackgroundColor()).isEqualTo("#de1000");
        assertThat(textareaStyle.getForegroundColor()).isEqualTo("#777777");
        this.testFontStyle(textareaStyle);
    }

    private void testConditionalStyle(Textarea textarea) {
        TextareaStyle textareaStyle = textarea.getStyle();
        assertThat(textareaStyle).isNotNull();
        assertThat(textareaStyle.getBackgroundColor()).isEqualTo("#fbb800");
        assertThat(textareaStyle.getForegroundColor()).isEqualTo("#134cba");
        this.testConditionalFontStyle(textareaStyle);
    }

    private void testNoStyle(Textarea textarea) {
        TextareaStyle textareaStyle = textarea.getStyle();
        assertThat(textareaStyle).isNull();
    }

    private void testNoStyle(LabelWidget labelWidget) {
        LabelWidgetStyle labelWidgetStyle = labelWidget.getStyle();
        assertThat(labelWidgetStyle).isNull();
    }

    private void testStyle(LabelWidget labelWidget) {
        LabelWidgetStyle labelWidgetStyle = labelWidget.getStyle();
        assertThat(labelWidgetStyle).isNotNull();
        assertThat(labelWidgetStyle.getColor()).isEqualTo("#de1000");
        this.testFontStyle(labelWidgetStyle);
    }

    private void testConditionalStyle(LabelWidget labelWidget) {
        LabelWidgetStyle labelWidgetStyle = labelWidget.getStyle();
        assertThat(labelWidgetStyle).isNotNull();
        assertThat(labelWidgetStyle.getColor()).isEqualTo("#fbb800");
        this.testConditionalFontStyle(labelWidgetStyle);
    }

    private void testNoStyle(Link link) {
        LinkStyle linkStyle = link.getStyle();
        assertThat(linkStyle).isNull();
    }

    private void testStyle(Link link) {
        LinkStyle linkStyle = link.getStyle();
        assertThat(linkStyle).isNotNull();
        assertThat(linkStyle.getColor()).isEqualTo("#de1000");
        this.testFontStyle(linkStyle);
    }

    private void testConditionalStyle(Link link) {
        LinkStyle linkStyle = link.getStyle();
        assertThat(linkStyle).isNotNull();
        assertThat(linkStyle.getColor()).isEqualTo("#fbb800");
        this.testConditionalFontStyle(linkStyle);
    }

    private void testStyle(Radio radio) {
        RadioStyle radioStyle = radio.getStyle();
        assertThat(radioStyle).isNotNull();
        assertThat(radioStyle.getColor()).isEqualTo("#de1000");
        this.testFontStyle(radioStyle);
    }

    private void testConditionalStyle(Radio radio) {
        RadioStyle radioStyle = radio.getStyle();
        assertThat(radioStyle).isNotNull();
        assertThat(radioStyle.getColor()).isEqualTo("#fbb800");
        this.testConditionalFontStyle(radioStyle);
    }

    private void testNoStyle(Radio radio) {
        RadioStyle radioStyle = radio.getStyle();
        assertThat(radioStyle).isNull();
    }

    private void testStyle(Select select) {
        SelectStyle selectStyle = select.getStyle();
        assertThat(selectStyle).isNotNull();
        assertThat(selectStyle.getBackgroundColor()).isEqualTo("#de1000");
        assertThat(selectStyle.getForegroundColor()).isEqualTo("#777777");
        this.testFontStyle(selectStyle);
    }

    private void testConditionalStyle(Select select) {
        SelectStyle selectStyle = select.getStyle();
        assertThat(selectStyle).isNotNull();
        assertThat(selectStyle.getBackgroundColor()).isEqualTo("#fbb800");
        assertThat(selectStyle.getForegroundColor()).isEqualTo("#134cba");
        this.testConditionalFontStyle(selectStyle);
    }

    private void testNoStyle(Select select) {
        SelectStyle selectStyle = select.getStyle();
        assertThat(selectStyle).isNull();
    }

    private void testStyle(Checkbox checkBox) {
        CheckboxStyle checkBoxStyle = checkBox.getStyle();
        assertThat(checkBoxStyle).isNotNull();
        assertThat(checkBoxStyle.getColor()).isEqualTo("#de1000");
    }

    private void testConditionalStyle(Checkbox checkBox) {
        CheckboxStyle checkBoxStyle = checkBox.getStyle();
        assertThat(checkBoxStyle).isNotNull();
        assertThat(checkBoxStyle.getColor()).isEqualTo("#fbb800");
    }

    private void testNoStyle(Checkbox checkBox) {
        CheckboxStyle checkBoxStyle = checkBox.getStyle();
        assertThat(checkBoxStyle).isNull();
    }

    private void testStyle(MultiSelect multiSelect) {
        MultiSelectStyle multiSelectStyle = multiSelect.getStyle();
        assertThat(multiSelectStyle).isNotNull();
        assertThat(multiSelectStyle.getBackgroundColor()).isEqualTo("#de1000");
        assertThat(multiSelectStyle.getForegroundColor()).isEqualTo("#777777");
        this.testFontStyle(multiSelectStyle);
    }

    private void testNoStyle(org.eclipse.sirius.components.forms.List list) {
        ListStyle listStyle = list.getStyle();
        assertThat(listStyle).isNull();
    }

    private void testStyle(org.eclipse.sirius.components.forms.List list) {
        ListStyle listStyle = list.getStyle();
        assertThat(listStyle).isNotNull();
        assertThat(listStyle.getColor()).isEqualTo("lightBlue");
        this.testFontStyle(listStyle);
    }

    private void testConditionalStyle(org.eclipse.sirius.components.forms.List list) {
        ListStyle listStyle = list.getStyle();
        assertThat(listStyle).isNotNull();
        assertThat(listStyle.getColor()).isEqualTo("orange");
        this.testConditionalFontStyle(listStyle);
    }

    private void testConditionalStyle(MultiSelect multiSelect) {
        MultiSelectStyle multiSelectStyle = multiSelect.getStyle();
        assertThat(multiSelectStyle).isNotNull();
        assertThat(multiSelectStyle.getBackgroundColor()).isEqualTo("#fbb800");
        assertThat(multiSelectStyle.getForegroundColor()).isEqualTo("#134cba");
        this.testConditionalFontStyle(multiSelectStyle);
    }

    private void testNoStyle(MultiSelect multiSelect) {
        MultiSelectStyle multiSelectStyle = multiSelect.getStyle();
        assertThat(multiSelectStyle).isNull();
    }

    private void testStyle(Button button) {
        ButtonStyle buttonStyle = button.getStyle();
        assertThat(buttonStyle).isNotNull();
        assertThat(buttonStyle.getBackgroundColor()).isEqualTo("#de1000");
        assertThat(buttonStyle.getForegroundColor()).isEqualTo("#777777");
        this.testFontStyle(buttonStyle);
    }

    private void testConditionalStyle(Button button) {
        ButtonStyle buttonStyle = button.getStyle();
        assertThat(buttonStyle).isNotNull();
        assertThat(buttonStyle.getBackgroundColor()).isEqualTo("#fbb800");
        assertThat(buttonStyle.getForegroundColor()).isEqualTo("#134cba");
        this.testConditionalFontStyle(buttonStyle);
    }

    private void testNoStyle(Button button) {
        ButtonStyle buttonStyle = button.getStyle();
        assertThat(buttonStyle).isNull();
    }

    private void testFontStyle(AbstractFontStyle fontStyle) {
        assertThat(fontStyle.getFontSize()).isEqualTo(20);
        assertThat(fontStyle.isItalic()).isTrue();
        assertThat(fontStyle.isBold()).isTrue();
        assertThat(fontStyle.isUnderline()).isTrue();
        assertThat(fontStyle.isStrikeThrough()).isTrue();
    }

    private void testConditionalFontStyle(AbstractFontStyle fontStyle) {
        assertThat(fontStyle.getFontSize()).isEqualTo(30);
        assertThat(fontStyle.isItalic()).isTrue();
        assertThat(fontStyle.isBold()).isFalse();
        assertThat(fontStyle.isUnderline()).isTrue();
        assertThat(fontStyle.isStrikeThrough()).isFalse();
    }

    private EPackage buildFixture() {
        EPackage fixture = EcoreFactory.eINSTANCE.createEPackage();
        fixture.setName("fixture");
        EClass eClass1 = EcoreFactory.eINSTANCE.createEClass();
        eClass1.setName("Class1");
        eClass1.setAbstract(true);
        eClass1.setInstanceClassName("Class1Instance");
        this.eClasses[0] = eClass1;

        fixture.getEClassifiers().add(eClass1);
        EClass eClass2 = EcoreFactory.eINSTANCE.createEClass();
        eClass2.setName("Class2");
        this.eClasses[1] = eClass2;
        fixture.getEClassifiers().add(eClass2);
        EClass eClass3 = EcoreFactory.eINSTANCE.createEClass();
        eClass3.setName("Class3");
        this.eClasses[2] = eClass3;
        fixture.getEClassifiers().add(eClass3);

        eClass1.getESuperTypes().add(eClass2);
        eClass1.getESuperTypes().add(eClass3);
        return fixture;
    }

    private Form render(FormDescription formDescription, Object target) {
        // Wrap into a View, as expected by ViewConverter
        View view = ViewFactory.eINSTANCE.createView();
        view.getDescriptions().add(formDescription);

        IObjectService.NoOp objectService = new IObjectService.NoOp() {
            @Override
            public String getId(Object object) {
                if (object instanceof ENamedElement) {
                    return ((ENamedElement) object).getName();
                }
                return super.getId(object);
            }

            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                Optional<Object> optional = Optional.empty();
                switch (objectId) {
                    case "Class1":
                        optional = Optional.of(DynamicFormsTests.this.eClasses[0]);
                        break;
                    case "Class2":
                        optional = Optional.of(DynamicFormsTests.this.eClasses[1]);
                        break;
                    case "Class3":
                        optional = Optional.of(DynamicFormsTests.this.eClasses[2]);
                        break;
                    default:
                        break;
                }
                return optional;
            }
        };
        IEditService.NoOp editService = new IEditService.NoOp() {

        };
        ViewFormDescriptionConverter formDescriptionConverter = new ViewFormDescriptionConverter(objectService, editService, new IFormIdProvider.NoOp(), List.of(), new IFeedbackMessageService.NoOp());
        var viewConverter = new ViewConverter(List.of(), List.of(formDescriptionConverter), new StaticApplicationContext(), List.of());
        List<IRepresentationDescription> conversionResult = viewConverter.convert(List.of(view), List.of(EcorePackage.eINSTANCE));
        assertThat(conversionResult).hasSize(1);
        assertThat(conversionResult.get(0)).isInstanceOf(org.eclipse.sirius.components.forms.description.FormDescription.class);
        org.eclipse.sirius.components.forms.description.FormDescription convertedFormDescription = (org.eclipse.sirius.components.forms.description.FormDescription) conversionResult.get(0);

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, List.of(target));

        IEditingContext editingContext = new IEditingContext.NoOp();
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

        FormRenderer formRenderer = new FormRenderer(List.of());
        FormComponentProps props = new FormComponentProps(variableManager, convertedFormDescription, List.of());
        Element element = new Element(FormComponent.class, props);
        return formRenderer.render(element);

    }

    private UserColor generateUserColor(String color) {
        var userColor = ViewFactory.eINSTANCE.createFixedColor();
        userColor.setName("color");
        userColor.setValue(color);
        return userColor;
    }
}
