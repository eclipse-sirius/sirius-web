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

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.view.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.renderer.FormRenderer;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.TextfieldDescription;
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

    @Test
    void testRenderEcoreForm() throws Exception {
        FormDescription formDescription = this.createFormDescription();

        Form result = this.render(formDescription, this.buildFixture());

        assertThat(result).isNotNull();
        assertThat(result.getPages()).hasSize(1);
        assertThat(result.getPages()).extracting(Page::getGroups).hasSize(1);
        result.getPages().stream().map(Page::getGroups).flatMap(List::stream).forEach(group -> {
            assertThat(group.getWidgets()).hasSize(1);
            AbstractWidget abstractWidget = group.getWidgets().stream().findFirst().get();
            assertThat(abstractWidget).isInstanceOf(Textfield.class);
            assertThat((Textfield) abstractWidget).extracting(Textfield::getValue).isEqualTo("fixture"); //$NON-NLS-1$
            assertThat((Textfield) abstractWidget).extracting(Textfield::getLabel).isEqualTo("EPackage name"); //$NON-NLS-1$
        });
    }

    @Test
    void testEditingEcoreForm() throws Exception {
        FormDescription formDescription = this.createFormDescription();
        EPackage root = this.buildFixture();
        Form form = this.render(formDescription, root);
        assertThat(form.getPages()).flatExtracting(Page::getGroups).flatExtracting(Group::getWidgets).hasSize(1);
        Textfield textfield = this.getTextfield(form);
        assertThat(textfield).extracting(Textfield::getValue).isEqualTo("fixture"); //$NON-NLS-1$
        assertThat(root).extracting(EPackage::getName).isEqualTo("fixture"); //$NON-NLS-1$
        textfield.getNewValueHandler().apply("my New Value"); //$NON-NLS-1$
        assertThat(root).extracting(EPackage::getName).isEqualTo("my New Value"); //$NON-NLS-1$

        form = this.render(formDescription, root);
        textfield = this.getTextfield(form);
        assertThat(textfield).extracting(Textfield::getValue).isEqualTo("my New Value"); //$NON-NLS-1$
    }

    private Textfield getTextfield(Form form) {
        //@formatter:off
        Textfield textfield = form.getPages().stream()
                .map(Page::getGroups)
                .flatMap(Collection::stream)
                .map(Group::getWidgets)
                .flatMap(Collection::stream)
                .filter(Textfield.class::isInstance)
                .map(Textfield.class::cast)
                .findFirst().get();
        //@formatter:on
        return textfield;
    }

    private FormDescription createFormDescription() {
        FormDescription formDescription = ViewFactory.eINSTANCE.createFormDescription();
        formDescription.setName("Simple Ecore Form"); //$NON-NLS-1$
        formDescription.setTitleExpression("aql:self.name"); //$NON-NLS-1$
        formDescription.setDomainType("ecore::EPackage"); //$NON-NLS-1$

        TextfieldDescription textfieldDescription = ViewFactory.eINSTANCE.createTextfieldDescription();
        textfieldDescription.setLabelExpression("aql:'EPackage name'"); //$NON-NLS-1$
        textfieldDescription.setValueExpression("aql:self.name"); //$NON-NLS-1$
        textfieldDescription.setName("Class Name"); //$NON-NLS-1$
        formDescription.getWidgets().add(textfieldDescription);

        SetValue setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name"); //$NON-NLS-1$
        setValue.setValueExpression("aql:newValue"); //$NON-NLS-1$
        textfieldDescription.getBody().add(setValue);

        return formDescription;
    }

    private EPackage buildFixture() {
        EPackage fixture = EcoreFactory.eINSTANCE.createEPackage();
        fixture.setName("fixture"); //$NON-NLS-1$
        EClass klass1 = EcoreFactory.eINSTANCE.createEClass();
        klass1.setName("Class1"); //$NON-NLS-1$
        fixture.getEClassifiers().add(klass1);
        EClass klass2 = EcoreFactory.eINSTANCE.createEClass();
        klass2.setName("Class2"); //$NON-NLS-1$
        fixture.getEClassifiers().add(klass2);
        return fixture;
    }

    private Form render(FormDescription formDescription, Object target) {
        // Wrap into a View, as expected by ViewConverter
        View view = ViewFactory.eINSTANCE.createView();
        view.getDescriptions().add(formDescription);

        ViewFormDescriptionConverter formDescriptionConverter = new ViewFormDescriptionConverter(new IObjectService.NoOp(), new IEditService.NoOp());
        var viewConverter = new ViewConverter(List.of(), List.of(formDescriptionConverter));
        List<IRepresentationDescription> conversionResult = viewConverter.convert(view, List.of(EcorePackage.eINSTANCE));
        assertThat(conversionResult).hasSize(1);
        assertThat(conversionResult.get(0)).isInstanceOf(org.eclipse.sirius.components.forms.description.FormDescription.class);
        org.eclipse.sirius.components.forms.description.FormDescription convertedFormDescription = (org.eclipse.sirius.components.forms.description.FormDescription) conversionResult.get(0);

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, List.of(target));

        FormRenderer formRenderer = new FormRenderer();
        FormComponentProps props = new FormComponentProps(variableManager, convertedFormDescription);
        Element element = new Element(FormComponent.class, props);
        return formRenderer.render(element);

    }
}
