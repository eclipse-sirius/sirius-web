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
package org.eclipse.sirius.components.emf.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.view.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.components.forms.AbstractFontStyle;
import org.eclipse.sirius.components.forms.Checkbox;
import org.eclipse.sirius.components.forms.CheckboxStyle;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
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
import org.eclipse.sirius.components.view.CheckboxDescription;
import org.eclipse.sirius.components.view.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalCheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.MultiSelectDescription;
import org.eclipse.sirius.components.view.MultiSelectDescriptionStyle;
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
import org.junit.jupiter.api.Test;

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
        assertThat(group.getWidgets()).hasSize(6);
        Textfield textfield = (Textfield) group.getWidgets().get(0);
        Textarea textarea = (Textarea) group.getWidgets().get(1);
        MultiSelect multiSelect = (MultiSelect) group.getWidgets().get(2);
        Checkbox checkBox = (Checkbox) group.getWidgets().get(3);
        Select select = (Select) group.getWidgets().get(4);
        Radio radio = (Radio) group.getWidgets().get(5);

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
        assertThat(group.getWidgets()).hasSize(6);
        Textfield textfield = (Textfield) group.getWidgets().get(0);
        Textarea textarea = (Textarea) group.getWidgets().get(1);
        MultiSelect multiSelect = (MultiSelect) group.getWidgets().get(2);
        Checkbox checkBox = (Checkbox) group.getWidgets().get(3);
        Select select = (Select) group.getWidgets().get(4);
        Radio radio = (Radio) group.getWidgets().get(5);

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
        assertThat(group.getWidgets()).hasSize(6);
        Textfield textfield = (Textfield) group.getWidgets().get(0);
        Textarea textarea = (Textarea) group.getWidgets().get(1);
        MultiSelect multiSelect = (MultiSelect) group.getWidgets().get(2);
        Checkbox checkBox = (Checkbox) group.getWidgets().get(3);
        Select select = (Select) group.getWidgets().get(4);
        Radio radio = (Radio) group.getWidgets().get(5);

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
    }

    @Test
    void testEditingEcoreForm() throws Exception {
        this.buildFixture();
        FormDescription eClassFormDescription = this.createClassFormDescription(false, false);
        Form form = this.render(eClassFormDescription, this.eClass1);
        assertThat(form.getPages()).flatExtracting(Page::getGroups).flatExtracting(Group::getWidgets).hasSize(6);

        this.checkValuesEditing(this.eClass1, form);
    }

    private void checkValuesEditing(EClass eClass, Form form) {
        Group group = form.getPages().get(0).getGroups().get(0);
        assertThat(group.getWidgets()).hasSize(6);

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

    }

    private FormDescription createClassFormDescription(boolean withStyle, boolean withConditionalStyle) {
        FormDescription formDescription = ViewFactory.eINSTANCE.createFormDescription();
        formDescription.setName("Simple Ecore Form"); //$NON-NLS-1$
        formDescription.setTitleExpression("aql:self.name"); //$NON-NLS-1$
        formDescription.setDomainType("ecore::EClass"); //$NON-NLS-1$
        this.createTextfield(formDescription, withStyle, withConditionalStyle);
        this.createTextArea(formDescription, withStyle, withConditionalStyle);
        this.createMultiSelect(formDescription, withStyle, withConditionalStyle);
        this.createCheckbox(formDescription, withStyle, withConditionalStyle);
        this.createSelect(formDescription, withStyle, withConditionalStyle);
        this.createRadio(formDescription, withStyle, withConditionalStyle);
        return formDescription;
    }

    private void createRadio(FormDescription formDescription, boolean withStyle, boolean withConditionalStyle) {
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
        formDescription.getWidgets().add(radioDescription);

        SetValue radioSetValue = ViewFactory.eINSTANCE.createSetValue();
        radioSetValue.setFeatureName("eSuperTypes"); //$NON-NLS-1$
        radioSetValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        radioDescription.getBody().add(radioSetValue);
    }

    private void createSelect(FormDescription formDescription, boolean withStyle, boolean withConditionalStyle) {
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
        formDescription.getWidgets().add(selectDescription);

        UnsetValue selectUnsetValue = ViewFactory.eINSTANCE.createUnsetValue();
        selectUnsetValue.setFeatureName("eSuperTypes"); //$NON-NLS-1$
        selectUnsetValue.setElementExpression("aql:self.eSuperTypes"); //$NON-NLS-1$
        selectDescription.getBody().add(selectUnsetValue);

        SetValue selectSetValue = ViewFactory.eINSTANCE.createSetValue();
        selectSetValue.setFeatureName("eSuperTypes"); //$NON-NLS-1$
        selectSetValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        selectUnsetValue.getChildren().add(selectSetValue);
    }

    private void createCheckbox(FormDescription formDescription, boolean withStyle, boolean withConditionalStyle) {
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
        formDescription.getWidgets().add(checkboxDescription);

        SetValue checkboxSetValue = ViewFactory.eINSTANCE.createSetValue();
        checkboxSetValue.setFeatureName("abstract"); //$NON-NLS-1$
        checkboxSetValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        checkboxDescription.getBody().add(checkboxSetValue);
    }

    private void createMultiSelect(FormDescription formDescription, boolean withStyle, boolean withConditionalStyle) {
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
        formDescription.getWidgets().add(multiSelectDescription);

        UnsetValue multiSelectUnsetValue = ViewFactory.eINSTANCE.createUnsetValue();
        multiSelectUnsetValue.setFeatureName("eSuperTypes"); //$NON-NLS-1$
        multiSelectUnsetValue.setElementExpression("aql:self.eSuperTypes"); //$NON-NLS-1$
        multiSelectDescription.getBody().add(multiSelectUnsetValue);

        SetValue multiSelectSetValue = ViewFactory.eINSTANCE.createSetValue();
        multiSelectSetValue.setFeatureName("eSuperTypes"); //$NON-NLS-1$
        multiSelectSetValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        multiSelectUnsetValue.getChildren().add(multiSelectSetValue);
    }

    private void createTextArea(FormDescription formDescription, boolean withStyle, boolean withConditionalStyle) {
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
        formDescription.getWidgets().add(textareaDescription);

        SetValue textareaSetValue = ViewFactory.eINSTANCE.createSetValue();
        textareaSetValue.setFeatureName("instanceClassName"); //$NON-NLS-1$
        textareaSetValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        textareaDescription.getBody().add(textareaSetValue);
    }

    private void createTextfield(FormDescription formDescription, boolean withStyle, boolean withConditionalStyle) {
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
        formDescription.getWidgets().add(textfieldDescription);

        SetValue setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name"); //$NON-NLS-1$
        setValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        textfieldDescription.getBody().add(setValue);
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
        var viewConverter = new ViewConverter(List.of(), List.of(formDescriptionConverter));
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
