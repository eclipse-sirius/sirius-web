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
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.AbstractFontStyle;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Button;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.ChartWidget;
import org.eclipse.sirius.components.forms.Checkbox;
import org.eclipse.sirius.components.forms.CheckboxStyle;
import org.eclipse.sirius.components.forms.FlexboxContainer;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.LabelWidget;
import org.eclipse.sirius.components.forms.LabelWidgetStyle;
import org.eclipse.sirius.components.forms.MultiSelect;
import org.eclipse.sirius.components.forms.MultiSelectStyle;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.forms.Radio;
import org.eclipse.sirius.components.forms.RadioStyle;
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.forms.SelectStyle;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.TextfieldStyle;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.renderer.FormRenderer;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.BarChartDescription;
import org.eclipse.sirius.components.view.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.ButtonDescription;
import org.eclipse.sirius.components.view.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.CheckboxDescription;
import org.eclipse.sirius.components.view.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalBarChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalCheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalPieChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.LabelDescription;
import org.eclipse.sirius.components.view.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.MultiSelectDescription;
import org.eclipse.sirius.components.view.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.PieChartDescription;
import org.eclipse.sirius.components.view.PieChartDescriptionStyle;
import org.eclipse.sirius.components.view.RadioDescription;
import org.eclipse.sirius.components.view.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.SelectDescription;
import org.eclipse.sirius.components.view.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.TextAreaDescription;
import org.eclipse.sirius.components.view.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.TextfieldDescription;
import org.eclipse.sirius.components.view.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.emf.ViewConverter;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;

/**
 * Tests for dynamically defined forms.
 *
 * @author fbarbin
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DynamicFormsTests {

    private EClass eClass1;

    private EClass eClass2;

    private EClass eClass3;

    @Test
    void testRenderEcoreForm() throws Exception {

        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(false, false);
        Form result = this.render(eClassFormDescription, this.eClass1);

        assertThat(result).isNotNull();
        assertThat(result.getPages()).hasSize(1);
        assertThat(result.getPages()).extracting(Page::getGroups).hasSize(1);

        Group group = result.getPages().get(0).getGroups().get(0);
        assertThat(group.getWidgets()).hasSize(11);
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

        assertThat(textfield.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(textfield.getLabel()).isEqualTo("EClass name"); //$NON-NLS-1$
        this.testNoStyle(textfield);

        assertThat(textarea.getValue()).isEqualTo("Class1Instance"); //$NON-NLS-1$
        assertThat(textarea.getLabel()).isEqualTo("Instance Class Name"); //$NON-NLS-1$
        this.testNoStyle(textarea);

        assertThat(multiSelect.getOptions()).hasSize(3);
        assertThat(multiSelect.getValues()).hasSize(2);
        assertThat(multiSelect.getValues()).containsExactlyInAnyOrder("Class2", "Class3"); //$NON-NLS-1$ //$NON-NLS-2$
        assertThat(multiSelect.getLabel()).isEqualTo("ESuperTypes"); //$NON-NLS-1$
        this.testNoStyle(multiSelect);

        assertThat(checkBox.isValue()).isTrue();
        assertThat(checkBox.getLabel()).isEqualTo("is Abstract"); //$NON-NLS-1$
        this.testNoStyle(checkBox);

        assertThat(select.getOptions()).hasSize(3);
        assertThat(select.getValue()).isEqualTo("Class2"); //$NON-NLS-1$
        assertThat(select.getLabel()).isEqualTo("eSuper Types"); //$NON-NLS-1$
        this.testNoStyle(select);

        assertThat(labelWidget.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(labelWidget.getLabel()).isEqualTo("Label EClass name"); //$NON-NLS-1$
        this.testNoStyle(labelWidget);

        assertThat(radio.getOptions()).hasSize(3);
        assertThat(radio.getOptions()).allSatisfy(option -> {
            if (option.getLabel().equals("Class2")) { //$NON-NLS-1$
                assertThat(option.isSelected()).isTrue();
            } else {
                assertThat(option.isSelected()).isFalse();
            }
        });
        assertThat(radio.getLabel()).isEqualTo("ESuperTypes"); //$NON-NLS-1$
        this.testNoStyle(radio);

        this.checkBarChart(chartWidgetWithBarChart, false, false);
        this.checkPieChart(chartWidgetWithPieChart, false, false);

        this.renderEcoreFormOnWidgetContainer(flexboxContainer);
        this.renderEcoreFormOnButton(button);
    }

    private void renderEcoreFormOnButton(Button button) {
        assertThat(button.getButtonLabel()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(button.getLabel()).isEqualTo("EClass name"); //$NON-NLS-1$
        this.testNoStyle(button);
    }

    private void checkPieChart(ChartWidget chartWidgetWithPieChart, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(chartWidgetWithPieChart.getLabel()).isEqualTo("The Chart Widget label"); //$NON-NLS-1$
        IChart chart = chartWidgetWithPieChart.getChart();
        assertThat(chart).isInstanceOf(PieChart.class);
        PieChart pieChart = (PieChart) chart;
        List<PieChartEntry> pieChartEntries = pieChart.getEntries();
        this.checkPieChartEntry(pieChartEntries, 0, "a", 1); //$NON-NLS-1$
        this.checkPieChartEntry(pieChartEntries, 1, "b", 3); //$NON-NLS-1$
        this.checkPieChartEntry(pieChartEntries, 2, "c", 5); //$NON-NLS-1$
        this.checkPieChartEntry(pieChartEntries, 3, "d", 7); //$NON-NLS-1$
        PieChartStyle style = pieChart.getStyle();
        if (checkStyle) {
            assertThat(style).isNotNull();
            assertThat(style.getColors()).isEqualTo(List.of("AliceBlue", "AntiqueWhite", "DarkMagenta", "DarkGreen")); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
            assertThat(style.getStrokeColor()).isEqualTo("Orchid"); //$NON-NLS-1$
            assertThat(style.getStrokeWidth()).isEqualTo(3);
            assertThat(style.getFontSize()).isEqualTo(20);
            assertThat(style.isItalic()).isTrue();
            assertThat(style.isBold()).isTrue();
            assertThat(style.isUnderline()).isTrue();
            assertThat(style.isStrikeThrough()).isTrue();
        } else if (checkConditionalStyle) {
            assertThat(style).isNotNull();
            assertThat(style.getColors()).isEqualTo(List.of("CadetBlue", "AntiqueWhite", "DarkMagenta", "Coral")); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
            assertThat(style.getStrokeColor()).isEqualTo("PaleGoldenRod"); //$NON-NLS-1$
            assertThat(style.getStrokeWidth()).isEqualTo(2);
            assertThat(style.getFontSize()).isEqualTo(30);
            assertThat(style.isItalic()).isTrue();
            assertThat(style.isBold()).isFalse();
            assertThat(style.isUnderline()).isTrue();
            assertThat(style.isStrikeThrough()).isFalse();
        }
    }

    private void checkBarChart(ChartWidget chartWidgetWithBarChart, boolean checkStyle, boolean checkConditionalStyle) {
        assertThat(chartWidgetWithBarChart.getLabel()).isEqualTo("The Chart Widget label"); //$NON-NLS-1$
        IChart chart = chartWidgetWithBarChart.getChart();
        assertThat(chart).isInstanceOf(BarChart.class);
        BarChart barChart = (BarChart) chart;
        assertThat(barChart.getLabel()).isEqualTo("the values"); //$NON-NLS-1$
        List<BarChartEntry> barChartEntries = barChart.getEntries();
        this.checkBarChartEntry(barChartEntries, 0, "a", 1); //$NON-NLS-1$
        this.checkBarChartEntry(barChartEntries, 1, "b", 3); //$NON-NLS-1$
        this.checkBarChartEntry(barChartEntries, 2, "c", 5); //$NON-NLS-1$
        this.checkBarChartEntry(barChartEntries, 3, "d", 7); //$NON-NLS-1$
        BarChartStyle style = barChart.getStyle();
        if (checkStyle) {
            assertThat(style).isNotNull();
            assertThat(style.getBarsColor()).isEqualTo("Orchid"); //$NON-NLS-1$
            assertThat(style.getFontSize()).isEqualTo(20);
            assertThat(style.isItalic()).isTrue();
            assertThat(style.isBold()).isTrue();
            assertThat(style.isUnderline()).isTrue();
            assertThat(style.isStrikeThrough()).isTrue();
        } else if (checkConditionalStyle) {
            assertThat(style).isNotNull();
            assertThat(style.getBarsColor()).isEqualTo("PaleGoldenRod"); //$NON-NLS-1$
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

    private void renderEcoreFormOnWidgetContainer(FlexboxContainer flexboxContainer) {
        assertThat(flexboxContainer.getLabel()).isEqualTo("A Widget Container"); //$NON-NLS-1$
        List<AbstractWidget> children = flexboxContainer.getChildren();
        assertThat(children).hasSize(2);
        assertThat(children.get(0)).isInstanceOf(Textfield.class);
        assertThat(children.get(1)).isInstanceOf(Checkbox.class);

        Textfield childrenTextfield = (Textfield) children.get(0);
        assertThat(childrenTextfield.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(childrenTextfield.getLabel()).isEqualTo("EClass name"); //$NON-NLS-1$
        this.testNoStyle(childrenTextfield);

        Checkbox childrenCheckbox = (Checkbox) children.get(1);
        assertThat(childrenCheckbox.isValue()).isTrue();
        assertThat(childrenCheckbox.getLabel()).isEqualTo("is Abstract"); //$NON-NLS-1$
        this.testNoStyle(childrenCheckbox);
    }

    @Test
    void testRenderEcoreFormWithStyle() throws Exception {

        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(true, false);
        Form result = this.render(eClassFormDescription, this.eClass1);

        assertThat(result).isNotNull();
        assertThat(result.getPages()).hasSize(1);
        assertThat(result.getPages()).extracting(Page::getGroups).hasSize(1);

        Group group = result.getPages().get(0).getGroups().get(0);
        assertThat(group.getWidgets()).hasSize(11);
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

        assertThat(textfield.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(textfield.getLabel()).isEqualTo("EClass name"); //$NON-NLS-1$
        this.testStyle(textfield);

        assertThat(textarea.getValue()).isEqualTo("Class1Instance"); //$NON-NLS-1$
        assertThat(textarea.getLabel()).isEqualTo("Instance Class Name"); //$NON-NLS-1$
        this.testStyle(textarea);

        assertThat(multiSelect.getOptions()).hasSize(3);
        assertThat(multiSelect.getValues()).hasSize(2);
        assertThat(multiSelect.getValues()).containsExactlyInAnyOrder("Class2", "Class3"); //$NON-NLS-1$ //$NON-NLS-2$
        assertThat(multiSelect.getLabel()).isEqualTo("ESuperTypes"); //$NON-NLS-1$
        this.testStyle(multiSelect);

        assertThat(checkBox.isValue()).isTrue();
        assertThat(checkBox.getLabel()).isEqualTo("is Abstract"); //$NON-NLS-1$
        this.testStyle(checkBox);

        assertThat(select.getOptions()).hasSize(3);
        assertThat(select.getValue()).isEqualTo("Class2"); //$NON-NLS-1$
        assertThat(select.getLabel()).isEqualTo("eSuper Types"); //$NON-NLS-1$
        this.testStyle(select);

        assertThat(radio.getOptions()).hasSize(3);
        radio.getOptions().forEach(option -> {
            if (option.getLabel().equals("Class2")) { //$NON-NLS-1$
                assertThat(option.isSelected()).isTrue();
            } else {
                assertThat(option.isSelected()).isFalse();
            }
        });
        assertThat(radio.getLabel()).isEqualTo("ESuperTypes"); //$NON-NLS-1$
        this.testStyle(radio);

        this.renderEcoreFormWithStyleOnWidgetContainer(flexboxContainer);

        assertThat(labelWidget.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(labelWidget.getLabel()).isEqualTo("Label EClass name"); //$NON-NLS-1$
        this.testStyle(labelWidget);

        assertThat(button.getButtonLabel()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(button.getLabel()).isEqualTo("EClass name"); //$NON-NLS-1$
        this.testStyle(button);
        this.checkBarChart(chartWidgetWithBarChart, true, false);

        this.checkPieChart(chartWidgetWithPieChart, true, false);
    }

    private void renderEcoreFormWithStyleOnWidgetContainer(FlexboxContainer flexboxContainer) {
        assertThat(flexboxContainer.getLabel()).isEqualTo("A Widget Container"); //$NON-NLS-1$
        List<AbstractWidget> children = flexboxContainer.getChildren();
        assertThat(children).hasSize(2);
        assertThat(children.get(0)).isInstanceOf(Textfield.class);
        assertThat(children.get(1)).isInstanceOf(Checkbox.class);

        Textfield childrenTextfield = (Textfield) children.get(0);
        assertThat(childrenTextfield.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(childrenTextfield.getLabel()).isEqualTo("EClass name"); //$NON-NLS-1$
        this.testStyle(childrenTextfield);

        Checkbox childrenCheckbox = (Checkbox) children.get(1);
        assertThat(childrenCheckbox.isValue()).isTrue();
        assertThat(childrenCheckbox.getLabel()).isEqualTo("is Abstract"); //$NON-NLS-1$
        this.testStyle(childrenCheckbox);
    }

    @Test
    void testRenderEcoreFormWithConditionalStyle() throws Exception {

        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(true, true);
        Form result = this.render(eClassFormDescription, this.eClass1);

        assertThat(result).isNotNull();
        assertThat(result.getPages()).hasSize(1);
        assertThat(result.getPages()).extracting(Page::getGroups).hasSize(1);

        Group group = result.getPages().get(0).getGroups().get(0);
        assertThat(group.getWidgets()).hasSize(11);
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

        assertThat(textfield.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(textfield.getLabel()).isEqualTo("EClass name"); //$NON-NLS-1$
        this.testConditionalStyle(textfield);

        assertThat(textarea.getValue()).isEqualTo("Class1Instance"); //$NON-NLS-1$
        assertThat(textarea.getLabel()).isEqualTo("Instance Class Name"); //$NON-NLS-1$
        this.testConditionalStyle(textarea);

        assertThat(multiSelect.getOptions()).hasSize(3);
        assertThat(multiSelect.getValues()).hasSize(2);
        assertThat(multiSelect.getValues()).containsExactlyInAnyOrder("Class2", "Class3"); //$NON-NLS-1$ //$NON-NLS-2$
        assertThat(multiSelect.getLabel()).isEqualTo("ESuperTypes"); //$NON-NLS-1$
        this.testConditionalStyle(multiSelect);

        assertThat(checkBox.isValue()).isTrue();
        assertThat(checkBox.getLabel()).isEqualTo("is Abstract"); //$NON-NLS-1$
        this.testConditionalStyle(checkBox);

        assertThat(select.getOptions()).hasSize(3);
        assertThat(select.getValue()).isEqualTo("Class2"); //$NON-NLS-1$
        assertThat(select.getLabel()).isEqualTo("eSuper Types"); //$NON-NLS-1$
        this.testConditionalStyle(select);

        assertThat(radio.getOptions()).hasSize(3);
        assertThat(radio.getOptions()).allSatisfy(option -> {
            if (option.getLabel().equals("Class2")) { //$NON-NLS-1$
                assertThat(option.isSelected()).isTrue();
            } else {
                assertThat(option.isSelected()).isFalse();
            }
        });
        assertThat(radio.getLabel()).isEqualTo("ESuperTypes"); //$NON-NLS-1$
        this.testConditionalStyle(radio);

        this.renderEcoreFormWithConditionalStyleOnWidgetContainer(flexboxContainer);

        assertThat(button.getButtonLabel()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(button.getLabel()).isEqualTo("EClass name"); //$NON-NLS-1$
        this.testConditionalStyle(button);

        assertThat(labelWidget.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(labelWidget.getLabel()).isEqualTo("Label EClass name"); //$NON-NLS-1$
        this.testConditionalStyle(labelWidget);

        this.checkBarChart(chartWidgetWithBarChart, false, true);

        this.checkPieChart(chartWidgetWithPieChart, false, true);
    }

    private void renderEcoreFormWithConditionalStyleOnWidgetContainer(FlexboxContainer flexboxContainer) {
        assertThat(flexboxContainer.getLabel()).isEqualTo("A Widget Container"); //$NON-NLS-1$
        List<AbstractWidget> children = flexboxContainer.getChildren();
        assertThat(children).hasSize(2);
        assertThat(children.get(0)).isInstanceOf(Textfield.class);
        assertThat(children.get(1)).isInstanceOf(Checkbox.class);

        Textfield childrenTextfield = (Textfield) children.get(0);
        assertThat(childrenTextfield.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(childrenTextfield.getLabel()).isEqualTo("EClass name"); //$NON-NLS-1$
        this.testConditionalStyle(childrenTextfield);

        Checkbox childrenCheckbox = (Checkbox) children.get(1);
        assertThat(childrenCheckbox.isValue()).isTrue();
        assertThat(childrenCheckbox.getLabel()).isEqualTo("is Abstract"); //$NON-NLS-1$
        this.testConditionalStyle(childrenCheckbox);
    }

    @Test
    void testEditingEcoreForm() throws Exception {
        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(false, false);
        Form form = this.render(eClassFormDescription, this.eClass1);
        assertThat(form.getPages()).flatExtracting(Page::getGroups).flatExtracting(Group::getWidgets).hasSize(11);

        this.checkValuesEditing(this.eClass1, form);
    }

    @Test
    void testSetNullOnSelectEcoreForm() throws Exception {
        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(false, false);
        Form form = this.render(eClassFormDescription, this.eClass1);
        Group group = form.getPages().get(0).getGroups().get(0);
        Select select = (Select) group.getWidgets().get(4);
        assertThat(select.getValue()).isEqualTo("Class2"); //$NON-NLS-1$
        assertThat(this.eClass1.getESuperTypes().isEmpty()).isFalse();
        select.getNewValueHandler().apply(null);
        assertThat(this.eClass1.getESuperTypes().isEmpty()).isTrue();
    }

    private void checkValuesEditing(EClass eClass, Form form) {
        Group group = form.getPages().get(0).getGroups().get(0);
        assertThat(group.getWidgets()).hasSize(11);

        Textfield textfield = (Textfield) group.getWidgets().get(0);
        assertThat(textfield.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(eClass.getName()).isEqualTo("Class1"); //$NON-NLS-1$
        textfield.getNewValueHandler().apply("my New Value"); //$NON-NLS-1$
        assertThat(eClass.getName()).isEqualTo("my New Value"); //$NON-NLS-1$

        Textarea textarea = (Textarea) group.getWidgets().get(1);
        assertThat(textarea.getValue()).isEqualTo("Class1Instance"); //$NON-NLS-1$
        assertThat(eClass.getInstanceClassName()).isEqualTo("Class1Instance"); //$NON-NLS-1$
        textarea.getNewValueHandler().apply("newInstanceName"); //$NON-NLS-1$
        assertThat(eClass.getInstanceClassName()).isEqualTo("newInstanceName"); //$NON-NLS-1$

        MultiSelect multiSelect = (MultiSelect) group.getWidgets().get(2);
        assertThat(multiSelect.getValues()).hasSize(2);
        assertThat(multiSelect.getValues()).containsExactlyInAnyOrder("Class2", "Class3"); //$NON-NLS-1$//$NON-NLS-2$
        multiSelect.getNewValuesHandler().apply(List.of("Class2")); //$NON-NLS-1$
        assertThat(eClass.getESuperTypes()).containsExactlyInAnyOrder(this.eClass2);

        Checkbox checkBox = (Checkbox) group.getWidgets().get(3);
        assertThat(checkBox.isValue()).isTrue();
        assertThat(eClass.isAbstract()).isTrue();
        checkBox.getNewValueHandler().apply(false);
        assertThat(eClass.isAbstract()).isFalse();

        Select select = (Select) group.getWidgets().get(4);
        assertThat(select.getOptions()).hasSize(3);
        assertThat(select.getValue()).isEqualTo("Class2"); //$NON-NLS-1$
        select.getNewValueHandler().apply("Class3"); //$NON-NLS-1$
        assertThat(this.eClass1.getESuperTypes()).containsExactlyInAnyOrder(this.eClass3);

        Radio radio = (Radio) group.getWidgets().get(5);
        assertThat(radio.getOptions()).hasSize(3);
        radio.getOptions().forEach(option -> {
            if (option.getLabel().equals("Class2")) { //$NON-NLS-1$
                assertThat(option.isSelected()).isTrue();
            } else {
                assertThat(option.isSelected()).isFalse();
            }
        });

        this.renderOnEcoreFormWithStyleOnWidgetContainer(eClass, group);

        Button button = (Button) group.getWidgets().get(9);
        assertThat(button.getButtonLabel()).isEqualTo("Class1"); //$NON-NLS-1$
    }

    private void renderOnEcoreFormWithStyleOnWidgetContainer(EClass eClass, Group group) {
        FlexboxContainer flexboxContainer = (FlexboxContainer) group.getWidgets().get(8);
        assertThat(flexboxContainer.getChildren()).hasSize(2);
        assertThat(flexboxContainer.getChildren().get(0)).isInstanceOf(Textfield.class);
        assertThat(flexboxContainer.getChildren().get(1)).isInstanceOf(Checkbox.class);

        Textfield childrenTextfield = (Textfield) flexboxContainer.getChildren().get(0);
        assertThat(childrenTextfield.getValue()).isEqualTo("Class1"); //$NON-NLS-1$
        assertThat(eClass.getName()).isEqualTo("my New Value"); //$NON-NLS-1$
        childrenTextfield.getNewValueHandler().apply("my New Value 2"); //$NON-NLS-1$
        assertThat(eClass.getName()).isEqualTo("my New Value 2"); //$NON-NLS-1$

        Checkbox childrenCheckbox = (Checkbox) flexboxContainer.getChildren().get(1);
        assertThat(childrenCheckbox.isValue()).isTrue();
        assertThat(eClass.isAbstract()).isFalse();
        childrenCheckbox.getNewValueHandler().apply(true);
        assertThat(eClass.isAbstract()).isTrue();
    }

    private FormDescription createClassFormDescription(boolean withStyle, boolean withConditionalStyle) {
        FormDescription formDescription = ViewFactory.eINSTANCE.createFormDescription();
        formDescription.setName("Simple Ecore Form"); //$NON-NLS-1$
        formDescription.setTitleExpression("aql:self.name"); //$NON-NLS-1$
        formDescription.setDomainType("ecore::EClass"); //$NON-NLS-1$
        TextfieldDescription textfieldDescription = this.createTextfield(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(textfieldDescription);
        TextAreaDescription textAreaDescription = this.createTextArea(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(textAreaDescription);
        MultiSelectDescription multiSelectDescription = this.createMultiSelect(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(multiSelectDescription);
        CheckboxDescription checkboxDescription = this.createCheckbox(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(checkboxDescription);
        SelectDescription selectDescription = this.createSelect(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(selectDescription);
        RadioDescription radioDescription = this.createRadio(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(radioDescription);
        BarChartDescription barChartDescription = this.createBarChart(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(barChartDescription);
        PieChartDescription pieChartDescription = this.createPieChart(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(pieChartDescription);
        FlexboxContainerDescription flexboxContainerDescription = this.createFlexboxContainer(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(flexboxContainerDescription);
        ButtonDescription buttonDescription = this.createButton(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(buttonDescription);
        LabelDescription labelDescription = this.createLabel(withStyle, withConditionalStyle);
        formDescription.getWidgets().add(labelDescription);
        return formDescription;
    }

    private BarChartDescription createBarChart(boolean withStyle, boolean withConditionalStyle) {
        BarChartDescription barChartDescription = ViewFactory.eINSTANCE.createBarChartDescription();
        barChartDescription.setName("barChart"); //$NON-NLS-1$
        barChartDescription.setLabelExpression("aql:'The Chart Widget label'"); //$NON-NLS-1$
        barChartDescription.setYAxisLabelExpression("aql:'the values'"); //$NON-NLS-1$
        barChartDescription.setKeysExpression("aql:Sequence{'a','b','c','d'}"); //$NON-NLS-1$
        barChartDescription.setValuesExpression("aql:Sequence{1,3,5,7}"); //$NON-NLS-1$
        if (withStyle) {
            BarChartDescriptionStyle style = ViewFactory.eINSTANCE.createBarChartDescriptionStyle();
            style.setBarsColor("Orchid"); //$NON-NLS-1$
            this.setFontStyle(style);
            barChartDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalBarChartDescriptionStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalBarChartDescriptionStyle();
            conditionalStyle.setCondition("aql:true"); //$NON-NLS-1$
            conditionalStyle.setBarsColor("PaleGoldenRod"); //$NON-NLS-1$
            this.setConditionalFontStyle(conditionalStyle);
            barChartDescription.getConditionalStyles().add(conditionalStyle);
        }

        return barChartDescription;
    }

    private PieChartDescription createPieChart(boolean withStyle, boolean withConditionalStyle) {
        PieChartDescription pieChartDescription = ViewFactory.eINSTANCE.createPieChartDescription();
        pieChartDescription.setName("chartWidget"); //$NON-NLS-1$
        pieChartDescription.setLabelExpression("aql:'The Chart Widget label'"); //$NON-NLS-1$
        pieChartDescription.setName("pieChart"); //$NON-NLS-1$
        pieChartDescription.setKeysExpression("aql:Sequence{'a','b','c','d'}"); //$NON-NLS-1$
        pieChartDescription.setValuesExpression("aql:Sequence{1,3,5,7}"); //$NON-NLS-1$

        if (withStyle) {
            PieChartDescriptionStyle style = ViewFactory.eINSTANCE.createPieChartDescriptionStyle();
            style.setColors("aql:Sequence{'AliceBlue','AntiqueWhite','DarkMagenta','DarkGreen'}"); //$NON-NLS-1$
            style.setStrokeColor("Orchid"); //$NON-NLS-1$
            style.setStrokeWidth("3"); //$NON-NLS-1$
            this.setFontStyle(style);
            pieChartDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalPieChartDescriptionStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalPieChartDescriptionStyle();
            conditionalStyle.setCondition("aql:true"); //$NON-NLS-1$
            conditionalStyle.setColors("aql:Sequence{'CadetBlue','AntiqueWhite','DarkMagenta','Coral'}"); //$NON-NLS-1$
            conditionalStyle.setStrokeColor("PaleGoldenRod"); //$NON-NLS-1$
            conditionalStyle.setStrokeWidth("2"); //$NON-NLS-1$
            this.setConditionalFontStyle(conditionalStyle);
            pieChartDescription.getConditionalStyles().add(conditionalStyle);
        }
        return pieChartDescription;
    }

    private FlexboxContainerDescription createFlexboxContainer(boolean withStyle, boolean withConditionalStyle) {
        FlexboxContainerDescription flexboxContainerDescription = ViewFactory.eINSTANCE.createFlexboxContainerDescription();
        flexboxContainerDescription.setLabelExpression("aql:'A Widget Container'"); //$NON-NLS-1$
        TextfieldDescription textfieldDescription = this.createTextfield(withStyle, withConditionalStyle);
        flexboxContainerDescription.getChildren().add(textfieldDescription);
        CheckboxDescription checkboxDescription = this.createCheckbox(withStyle, withConditionalStyle);
        flexboxContainerDescription.getChildren().add(checkboxDescription);
        return flexboxContainerDescription;
    }

    private RadioDescription createRadio(boolean withStyle, boolean withConditionalStyle) {
        RadioDescription radioDescription = ViewFactory.eINSTANCE.createRadioDescription();
        radioDescription.setLabelExpression("aql:'ESuperTypes'"); //$NON-NLS-1$
        radioDescription.setValueExpression("aql:self.eSuperTypes->first()"); //$NON-NLS-1$
        radioDescription.setCandidatesExpression("aql:self.eContainer().eAllContents(ecore::EClass)"); //$NON-NLS-1$
        radioDescription.setCandidateLabelExpression("aql:candidate.name"); //$NON-NLS-1$
        if (withStyle) {
            RadioDescriptionStyle style = ViewFactory.eINSTANCE.createRadioDescriptionStyle();
            style.setColor("#de1000"); //$NON-NLS-1$
            this.setFontStyle(style);
            radioDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalRadioDescriptionStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalRadioDescriptionStyle();
            conditionalStyle.setCondition("aql:true"); //$NON-NLS-1$
            conditionalStyle.setColor("#fbb800"); //$NON-NLS-1$
            this.setConditionalFontStyle(conditionalStyle);
            radioDescription.getConditionalStyles().add(conditionalStyle);
        }

        SetValue radioSetValue = ViewFactory.eINSTANCE.createSetValue();
        radioSetValue.setFeatureName("eSuperTypes"); //$NON-NLS-1$
        radioSetValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        radioDescription.getBody().add(radioSetValue);
        return radioDescription;
    }

    private SelectDescription createSelect(boolean withStyle, boolean withConditionalStyle) {
        SelectDescription selectDescription = ViewFactory.eINSTANCE.createSelectDescription();
        selectDescription.setLabelExpression("aql:'eSuper Types'"); //$NON-NLS-1$
        selectDescription.setValueExpression("aql:self.eSuperTypes->first()"); //$NON-NLS-1$
        selectDescription.setCandidatesExpression("aql:self.eContainer().eAllContents(ecore::EClass)"); //$NON-NLS-1$
        selectDescription.setCandidateLabelExpression("aql:candidate.name"); //$NON-NLS-1$
        if (withStyle) {
            SelectDescriptionStyle style = ViewFactory.eINSTANCE.createSelectDescriptionStyle();
            style.setBackgroundColor("#de1000"); //$NON-NLS-1$
            style.setForegroundColor("#777777"); //$NON-NLS-1$
            this.setFontStyle(style);
            selectDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalSelectDescriptionStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalSelectDescriptionStyle();
            conditionalStyle.setCondition("aql:true"); //$NON-NLS-1$
            conditionalStyle.setBackgroundColor("#fbb800"); //$NON-NLS-1$
            conditionalStyle.setForegroundColor("#134cba"); //$NON-NLS-1$
            this.setConditionalFontStyle(conditionalStyle);
            selectDescription.getConditionalStyles().add(conditionalStyle);
        }

        UnsetValue selectUnsetValue = ViewFactory.eINSTANCE.createUnsetValue();
        selectUnsetValue.setFeatureName("eSuperTypes"); //$NON-NLS-1$
        selectUnsetValue.setElementExpression("aql:self.eSuperTypes"); //$NON-NLS-1$
        selectDescription.getBody().add(selectUnsetValue);

        SetValue selectSetValue = ViewFactory.eINSTANCE.createSetValue();
        selectSetValue.setFeatureName("eSuperTypes"); //$NON-NLS-1$
        selectSetValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        selectUnsetValue.getChildren().add(selectSetValue);
        return selectDescription;
    }

    private CheckboxDescription createCheckbox(boolean withStyle, boolean withConditionalStyle) {
        CheckboxDescription checkboxDescription = ViewFactory.eINSTANCE.createCheckboxDescription();
        checkboxDescription.setLabelExpression("is Abstract"); //$NON-NLS-1$
        checkboxDescription.setValueExpression("aql:self.abstract"); //$NON-NLS-1$
        if (withStyle) {
            CheckboxDescriptionStyle style = ViewFactory.eINSTANCE.createCheckboxDescriptionStyle();
            style.setColor("#de1000"); //$NON-NLS-1$
            checkboxDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalCheckboxDescriptionStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalCheckboxDescriptionStyle();
            conditionalStyle.setCondition("aql:true"); //$NON-NLS-1$
            conditionalStyle.setColor("#fbb800"); //$NON-NLS-1$
            checkboxDescription.getConditionalStyles().add(conditionalStyle);
        }

        SetValue checkboxSetValue = ViewFactory.eINSTANCE.createSetValue();
        checkboxSetValue.setFeatureName("abstract"); //$NON-NLS-1$
        checkboxSetValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        checkboxDescription.getBody().add(checkboxSetValue);
        return checkboxDescription;
    }

    private MultiSelectDescription createMultiSelect(boolean withStyle, boolean withConditionalStyle) {
        MultiSelectDescription multiSelectDescription = ViewFactory.eINSTANCE.createMultiSelectDescription();
        multiSelectDescription.setLabelExpression("aql:'ESuperTypes'"); //$NON-NLS-1$
        multiSelectDescription.setValueExpression("aql:self.eSuperTypes"); //$NON-NLS-1$
        multiSelectDescription.setCandidatesExpression("aql:self.eContainer().eAllContents(ecore::EClass)"); //$NON-NLS-1$
        multiSelectDescription.setCandidateLabelExpression("aql:candidate.name"); //$NON-NLS-1$
        if (withStyle) {
            MultiSelectDescriptionStyle style = ViewFactory.eINSTANCE.createMultiSelectDescriptionStyle();
            style.setBackgroundColor("#de1000"); //$NON-NLS-1$
            style.setForegroundColor("#777777"); //$NON-NLS-1$
            this.setFontStyle(style);
            multiSelectDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalMultiSelectDescriptionStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalMultiSelectDescriptionStyle();
            conditionalStyle.setCondition("aql:true"); //$NON-NLS-1$
            conditionalStyle.setBackgroundColor("#fbb800"); //$NON-NLS-1$
            conditionalStyle.setForegroundColor("#134cba"); //$NON-NLS-1$
            this.setConditionalFontStyle(conditionalStyle);
            multiSelectDescription.getConditionalStyles().add(conditionalStyle);
        }

        UnsetValue multiSelectUnsetValue = ViewFactory.eINSTANCE.createUnsetValue();
        multiSelectUnsetValue.setFeatureName("eSuperTypes"); //$NON-NLS-1$
        multiSelectUnsetValue.setElementExpression("aql:self.eSuperTypes"); //$NON-NLS-1$
        multiSelectDescription.getBody().add(multiSelectUnsetValue);

        SetValue multiSelectSetValue = ViewFactory.eINSTANCE.createSetValue();
        multiSelectSetValue.setFeatureName("eSuperTypes"); //$NON-NLS-1$
        multiSelectSetValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        multiSelectUnsetValue.getChildren().add(multiSelectSetValue);
        return multiSelectDescription;
    }

    private TextAreaDescription createTextArea(boolean withStyle, boolean withConditionalStyle) {
        TextAreaDescription textareaDescription = ViewFactory.eINSTANCE.createTextAreaDescription();
        textareaDescription.setLabelExpression("aql:'Instance Class Name'"); //$NON-NLS-1$
        textareaDescription.setValueExpression("aql:self.instanceClassName"); //$NON-NLS-1$
        if (withStyle) {
            TextareaDescriptionStyle style = ViewFactory.eINSTANCE.createTextareaDescriptionStyle();
            style.setBackgroundColor("#de1000"); //$NON-NLS-1$
            style.setForegroundColor("#777777"); //$NON-NLS-1$
            this.setFontStyle(style);
            textareaDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalTextareaDescriptionStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalTextareaDescriptionStyle();
            conditionalStyle.setCondition("aql:true"); //$NON-NLS-1$
            conditionalStyle.setBackgroundColor("#fbb800"); //$NON-NLS-1$
            conditionalStyle.setForegroundColor("#134cba"); //$NON-NLS-1$
            this.setConditionalFontStyle(conditionalStyle);
            textareaDescription.getConditionalStyles().add(conditionalStyle);
        }

        SetValue textareaSetValue = ViewFactory.eINSTANCE.createSetValue();
        textareaSetValue.setFeatureName("instanceClassName"); //$NON-NLS-1$
        textareaSetValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        textareaDescription.getBody().add(textareaSetValue);
        return textareaDescription;
    }

    private TextfieldDescription createTextfield(boolean withStyle, boolean withConditionalStyle) {
        TextfieldDescription textfieldDescription = ViewFactory.eINSTANCE.createTextfieldDescription();
        textfieldDescription.setLabelExpression("aql:'EClass name'"); //$NON-NLS-1$
        textfieldDescription.setValueExpression("aql:self.name"); //$NON-NLS-1$
        textfieldDescription.setName("Class Name"); //$NON-NLS-1$
        if (withStyle) {
            TextfieldDescriptionStyle style = ViewFactory.eINSTANCE.createTextfieldDescriptionStyle();
            style.setBackgroundColor("#de1000"); //$NON-NLS-1$
            style.setForegroundColor("#777777"); //$NON-NLS-1$
            this.setFontStyle(style);
            textfieldDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalTextfieldDescriptionStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalTextfieldDescriptionStyle();
            conditionalStyle.setCondition("aql:true"); //$NON-NLS-1$
            conditionalStyle.setBackgroundColor("#fbb800"); //$NON-NLS-1$
            conditionalStyle.setForegroundColor("#134cba"); //$NON-NLS-1$
            this.setConditionalFontStyle(conditionalStyle);
            textfieldDescription.getConditionalStyles().add(conditionalStyle);
        }

        SetValue setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name"); //$NON-NLS-1$
        setValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        textfieldDescription.getBody().add(setValue);
        return textfieldDescription;
    }

    private ButtonDescription createButton(boolean withStyle, boolean withConditionalStyle) {
        ButtonDescription buttonDescription = ViewFactory.eINSTANCE.createButtonDescription();
        buttonDescription.setLabelExpression("aql:'EClass name'"); //$NON-NLS-1$
        buttonDescription.setButtonLabelExpression("aql:self.name"); //$NON-NLS-1$
        buttonDescription.setName("Class Name"); //$NON-NLS-1$
        if (withStyle) {
            ButtonDescriptionStyle style = ViewFactory.eINSTANCE.createButtonDescriptionStyle();
            style.setBackgroundColor("#de1000"); //$NON-NLS-1$
            style.setForegroundColor("#777777"); //$NON-NLS-1$
            this.setFontStyle(style);
            buttonDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalButtonDescriptionStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalButtonDescriptionStyle();
            conditionalStyle.setCondition("aql:true"); //$NON-NLS-1$
            conditionalStyle.setBackgroundColor("#fbb800"); //$NON-NLS-1$
            conditionalStyle.setForegroundColor("#134cba"); //$NON-NLS-1$
            this.setConditionalFontStyle(conditionalStyle);
            buttonDescription.getConditionalStyles().add(conditionalStyle);
        }

        SetValue setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name"); //$NON-NLS-1$
        setValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        buttonDescription.getBody().add(setValue);
        return buttonDescription;
    }

    private LabelDescription createLabel(boolean withStyle, boolean withConditionalStyle) {
        LabelDescription labelDescription = ViewFactory.eINSTANCE.createLabelDescription();
        labelDescription.setLabelExpression("aql:'Label EClass name'"); //$NON-NLS-1$
        labelDescription.setValueExpression("aql:self.name"); //$NON-NLS-1$
        labelDescription.setName("Class Name"); //$NON-NLS-1$
        if (withStyle) {
            LabelDescriptionStyle style = ViewFactory.eINSTANCE.createLabelDescriptionStyle();
            style.setColor("#de1000"); //$NON-NLS-1$
            this.setFontStyle(style);
            labelDescription.setStyle(style);
        }
        if (withConditionalStyle) {
            ConditionalLabelDescriptionStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalLabelDescriptionStyle();
            conditionalStyle.setCondition("aql:true"); //$NON-NLS-1$
            conditionalStyle.setColor("#fbb800"); //$NON-NLS-1$
            this.setConditionalFontStyle(conditionalStyle);
            labelDescription.getConditionalStyles().add(conditionalStyle);
        }

        return labelDescription;
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
        assertThat(textfieldStyle.getBackgroundColor()).isEqualTo("#de1000"); //$NON-NLS-1$
        assertThat(textfieldStyle.getForegroundColor()).isEqualTo("#777777"); //$NON-NLS-1$
        this.testFontStyle(textfieldStyle);
    }

    private void testConditionalStyle(Textfield textfield) {
        TextfieldStyle textfieldStyle = textfield.getStyle();
        assertThat(textfieldStyle).isNotNull();
        assertThat(textfieldStyle.getBackgroundColor()).isEqualTo("#fbb800"); //$NON-NLS-1$
        assertThat(textfieldStyle.getForegroundColor()).isEqualTo("#134cba"); //$NON-NLS-1$
        this.testConditionalFontStyle(textfieldStyle);
    }

    private void testNoStyle(Textfield textfield) {
        TextfieldStyle textfieldStyle = textfield.getStyle();
        assertThat(textfieldStyle).isNull();
    }

    private void testStyle(Textarea textarea) {
        TextareaStyle textareaStyle = textarea.getStyle();
        assertThat(textareaStyle).isNotNull();
        assertThat(textareaStyle.getBackgroundColor()).isEqualTo("#de1000"); //$NON-NLS-1$
        assertThat(textareaStyle.getForegroundColor()).isEqualTo("#777777"); //$NON-NLS-1$
        this.testFontStyle(textareaStyle);
    }

    private void testConditionalStyle(Textarea textarea) {
        TextareaStyle textareaStyle = textarea.getStyle();
        assertThat(textareaStyle).isNotNull();
        assertThat(textareaStyle.getBackgroundColor()).isEqualTo("#fbb800"); //$NON-NLS-1$
        assertThat(textareaStyle.getForegroundColor()).isEqualTo("#134cba"); //$NON-NLS-1$
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
        assertThat(labelWidgetStyle.getColor()).isEqualTo("#de1000"); //$NON-NLS-1$
        this.testFontStyle(labelWidgetStyle);
    }

    private void testConditionalStyle(LabelWidget labelWidget) {
        LabelWidgetStyle labelWidgetStyle = labelWidget.getStyle();
        assertThat(labelWidgetStyle).isNotNull();
        assertThat(labelWidgetStyle.getColor()).isEqualTo("#fbb800"); //$NON-NLS-1$
        this.testConditionalFontStyle(labelWidgetStyle);
    }

    private void testStyle(Radio radio) {
        RadioStyle radioStyle = radio.getStyle();
        assertThat(radioStyle).isNotNull();
        assertThat(radioStyle.getColor()).isEqualTo("#de1000"); //$NON-NLS-1$
        this.testFontStyle(radioStyle);
    }

    private void testConditionalStyle(Radio radio) {
        RadioStyle radioStyle = radio.getStyle();
        assertThat(radioStyle).isNotNull();
        assertThat(radioStyle.getColor()).isEqualTo("#fbb800"); //$NON-NLS-1$
        this.testConditionalFontStyle(radioStyle);
    }

    private void testNoStyle(Radio radio) {
        RadioStyle radioStyle = radio.getStyle();
        assertThat(radioStyle).isNull();
    }

    private void testStyle(Select select) {
        SelectStyle selectStyle = select.getStyle();
        assertThat(selectStyle).isNotNull();
        assertThat(selectStyle.getBackgroundColor()).isEqualTo("#de1000"); //$NON-NLS-1$
        assertThat(selectStyle.getForegroundColor()).isEqualTo("#777777"); //$NON-NLS-1$
        this.testFontStyle(selectStyle);
    }

    private void testConditionalStyle(Select select) {
        SelectStyle selectStyle = select.getStyle();
        assertThat(selectStyle).isNotNull();
        assertThat(selectStyle.getBackgroundColor()).isEqualTo("#fbb800"); //$NON-NLS-1$
        assertThat(selectStyle.getForegroundColor()).isEqualTo("#134cba"); //$NON-NLS-1$
        this.testConditionalFontStyle(selectStyle);
    }

    private void testNoStyle(Select select) {
        SelectStyle selectStyle = select.getStyle();
        assertThat(selectStyle).isNull();
    }

    private void testStyle(Checkbox checkBox) {
        CheckboxStyle checkBoxStyle = checkBox.getStyle();
        assertThat(checkBoxStyle).isNotNull();
        assertThat(checkBoxStyle.getColor()).isEqualTo("#de1000"); //$NON-NLS-1$
    }

    private void testConditionalStyle(Checkbox checkBox) {
        CheckboxStyle checkBoxStyle = checkBox.getStyle();
        assertThat(checkBoxStyle).isNotNull();
        assertThat(checkBoxStyle.getColor()).isEqualTo("#fbb800"); //$NON-NLS-1$
    }

    private void testNoStyle(Checkbox checkBox) {
        CheckboxStyle checkBoxStyle = checkBox.getStyle();
        assertThat(checkBoxStyle).isNull();
    }

    private void testStyle(MultiSelect multiSelect) {
        MultiSelectStyle multiSelectStyle = multiSelect.getStyle();
        assertThat(multiSelectStyle).isNotNull();
        assertThat(multiSelectStyle.getBackgroundColor()).isEqualTo("#de1000"); //$NON-NLS-1$
        assertThat(multiSelectStyle.getForegroundColor()).isEqualTo("#777777"); //$NON-NLS-1$
        this.testFontStyle(multiSelectStyle);
    }

    private void testConditionalStyle(MultiSelect multiSelect) {
        MultiSelectStyle multiSelectStyle = multiSelect.getStyle();
        assertThat(multiSelectStyle).isNotNull();
        assertThat(multiSelectStyle.getBackgroundColor()).isEqualTo("#fbb800"); //$NON-NLS-1$
        assertThat(multiSelectStyle.getForegroundColor()).isEqualTo("#134cba"); //$NON-NLS-1$
        this.testConditionalFontStyle(multiSelectStyle);
    }

    private void testNoStyle(MultiSelect multiSelect) {
        MultiSelectStyle multiSelectStyle = multiSelect.getStyle();
        assertThat(multiSelectStyle).isNull();
    }

    private void testStyle(Button button) {
        ButtonStyle buttonStyle = button.getStyle();
        assertThat(buttonStyle).isNotNull();
        assertThat(buttonStyle.getBackgroundColor()).isEqualTo("#de1000"); //$NON-NLS-1$
        assertThat(buttonStyle.getForegroundColor()).isEqualTo("#777777"); //$NON-NLS-1$
        this.testFontStyle(buttonStyle);
    }

    private void testConditionalStyle(Button button) {
        ButtonStyle buttonStyle = button.getStyle();
        assertThat(buttonStyle).isNotNull();
        assertThat(buttonStyle.getBackgroundColor()).isEqualTo("#fbb800"); //$NON-NLS-1$
        assertThat(buttonStyle.getForegroundColor()).isEqualTo("#134cba"); //$NON-NLS-1$
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
        fixture.setName("fixture"); //$NON-NLS-1$
        this.eClass1 = EcoreFactory.eINSTANCE.createEClass();
        this.eClass1.setName("Class1"); //$NON-NLS-1$
        this.eClass1.setAbstract(true);
        this.eClass1.setInstanceClassName("Class1Instance"); //$NON-NLS-1$
        fixture.getEClassifiers().add(this.eClass1);
        this.eClass2 = EcoreFactory.eINSTANCE.createEClass();
        this.eClass2.setName("Class2"); //$NON-NLS-1$
        fixture.getEClassifiers().add(this.eClass2);
        this.eClass3 = EcoreFactory.eINSTANCE.createEClass();
        this.eClass3.setName("Class3"); //$NON-NLS-1$
        fixture.getEClassifiers().add(this.eClass3);

        this.eClass1.getESuperTypes().add(this.eClass2);
        this.eClass1.getESuperTypes().add(this.eClass3);
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
                case "Class1": //$NON-NLS-1$
                    optional = Optional.of(DynamicFormsTests.this.eClass1);
                    break;
                case "Class2": //$NON-NLS-1$
                    optional = Optional.of(DynamicFormsTests.this.eClass2);
                    break;
                case "Class3": //$NON-NLS-1$
                    optional = Optional.of(DynamicFormsTests.this.eClass3);
                    break;
                default:
                    break;
                }
                return optional;
            }
        };
        IEditService.NoOp editService = new IEditService.NoOp() {

        };
        ViewFormDescriptionConverter formDescriptionConverter = new ViewFormDescriptionConverter(objectService, editService);
        var viewConverter = new ViewConverter(List.of(), List.of(formDescriptionConverter), new StaticApplicationContext());
        List<IRepresentationDescription> conversionResult = viewConverter.convert(view, List.of(EcorePackage.eINSTANCE));
        assertThat(conversionResult).hasSize(1);
        assertThat(conversionResult.get(0)).isInstanceOf(org.eclipse.sirius.components.forms.description.FormDescription.class);
        org.eclipse.sirius.components.forms.description.FormDescription convertedFormDescription = (org.eclipse.sirius.components.forms.description.FormDescription) conversionResult.get(0);

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, List.of(target));

        IEditingContext editingContext = new IEditingContext.NoOp();
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

        FormRenderer formRenderer = new FormRenderer();
        FormComponentProps props = new FormComponentProps(variableManager, convertedFormDescription);
        Element element = new Element(FormComponent.class, props);
        return formRenderer.render(element);

    }
}
