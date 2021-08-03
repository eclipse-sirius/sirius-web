/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.emf.compatibility.properties;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.properties.Category;
import org.eclipse.sirius.properties.PropertiesFactory;
import org.eclipse.sirius.properties.ViewExtensionDescription;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.web.compat.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.web.compat.forms.ViewExtensionDescriptionConverter;
import org.eclipse.sirius.web.compat.services.representations.IdentifiedElementLabelProvider;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.emf.compatibility.AQLInterpreterFactory;
import org.eclipse.sirius.web.emf.compatibility.SemanticCandidatesProvider;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ModelOperationHandlerSwitch;
import org.eclipse.sirius.web.forms.AbstractWidget;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.Group;
import org.eclipse.sirius.web.forms.components.FormComponent;
import org.eclipse.sirius.web.forms.components.FormComponentProps;
import org.eclipse.sirius.web.forms.description.CheckboxDescription;
import org.eclipse.sirius.web.forms.description.ForDescription;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.forms.description.RadioDescription;
import org.eclipse.sirius.web.forms.description.SelectDescription;
import org.eclipse.sirius.web.forms.description.TextfieldDescription;
import org.eclipse.sirius.web.forms.renderer.FormRenderer;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.jupiter.api.Test;

/**
 * Tests conversion of viewExtensionDescription to Sirius Web FormDescription.
 *
 * @author fbarbin
 */
public class FormRendererTests {

    private static final String AQL_TRUE = "aql:true"; //$NON-NLS-1$

    private static final String AQL_SELF_EPACKAGE_ECLASSIFIERS = "aql:self.ePackage.eClassifiers"; //$NON-NLS-1$

    @Test
    public void testEcoreModel() {
        ViewExtensionDescription viewExtensionDescription = this.createSiriusProperties();

        AQLInterpreterFactory interpreterFactory = new AQLInterpreterFactory() {
            @Override
            public AQLInterpreter create(ViewExtensionDescription viewExtensionDescription) {
                return new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
            }
        };

        IIdentifierProvider identifierProvider = element -> UUID.randomUUID().toString();
        IdentifiedElementLabelProvider identifiedElementLabelProvider = new IdentifiedElementLabelProvider();
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = SemanticCandidatesProvider::new;
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = ModelOperationHandlerSwitch::new;
        ViewExtensionDescriptionConverter converter = new ViewExtensionDescriptionConverter(new IObjectService.NoOp(), interpreterFactory, identifierProvider, semanticCandidatesProviderFactory,
                modelOperationHandlerSwitchProvider, identifiedElementLabelProvider);
        FormDescription description = converter.convert(viewExtensionDescription);

        this.checkResult(description);
    }

    private ViewExtensionDescription createSiriusProperties() {
        ViewExtensionDescription viewExtensionDescription = PropertiesFactory.eINSTANCE.createViewExtensionDescription();
        Category category = PropertiesFactory.eINSTANCE.createCategory();
        viewExtensionDescription.getCategories().add(category);

        org.eclipse.sirius.properties.GroupDescription groupDescription = this.createGroupDescription();

        org.eclipse.sirius.properties.PageDescription pageDescription = this.createPageDescription(groupDescription);

        org.eclipse.sirius.properties.TextDescription textDescription = this.createTextfield();

        org.eclipse.sirius.properties.TextAreaDescription textAreaDescription = this.createTextArea();

        org.eclipse.sirius.properties.CheckboxDescription checkboxDescription = this.createCheckbox();

        org.eclipse.sirius.properties.RadioDescription radioDescription = this.createRadio();

        org.eclipse.sirius.properties.SelectDescription selectDescription = this.createSelect();

        org.eclipse.sirius.properties.DynamicMappingForDescription forDescription = this.createForDescription();

        org.eclipse.sirius.properties.CheckboxDescription ifCheckboxDescription = this.createCheckbox();

        org.eclipse.sirius.properties.DynamicMappingIfDescription ifDescription = this.createIfDescription(ifCheckboxDescription);

        forDescription.getIfs().add(ifDescription);

        groupDescription.getControls().add(textDescription);
        groupDescription.getControls().add(textAreaDescription);
        groupDescription.getControls().add(checkboxDescription);
        groupDescription.getControls().add(radioDescription);
        groupDescription.getControls().add(selectDescription);
        groupDescription.getControls().add(forDescription);

        category.getGroups().add(groupDescription);
        category.getPages().add(pageDescription);
        return viewExtensionDescription;
    }

    private org.eclipse.sirius.properties.DynamicMappingIfDescription createIfDescription(org.eclipse.sirius.properties.CheckboxDescription checkboxDescription) {
        org.eclipse.sirius.properties.DynamicMappingIfDescription ifDescription = PropertiesFactory.eINSTANCE.createDynamicMappingIfDescription();
        ifDescription.setName("myIf"); //$NON-NLS-1$
        ifDescription.setPredicateExpression(AQL_TRUE);
        ifDescription.setWidget(checkboxDescription);
        return ifDescription;
    }

    private org.eclipse.sirius.properties.DynamicMappingForDescription createForDescription() {
        org.eclipse.sirius.properties.DynamicMappingForDescription forDescription = PropertiesFactory.eINSTANCE.createDynamicMappingForDescription();
        forDescription.setName("myFor"); //$NON-NLS-1$
        forDescription.setIterator("element"); //$NON-NLS-1$
        forDescription.setIterableExpression(AQL_SELF_EPACKAGE_ECLASSIFIERS);
        return forDescription;
    }

    private org.eclipse.sirius.properties.SelectDescription createSelect() {
        org.eclipse.sirius.properties.SelectDescription selectDescription = PropertiesFactory.eINSTANCE.createSelectDescription();
        selectDescription.setName("myRadio"); //$NON-NLS-1$
        selectDescription.setLabelExpression("my select"); //$NON-NLS-1$
        selectDescription.setValueExpression("EClassifier"); //$NON-NLS-1$
        selectDescription.setCandidatesExpression(AQL_SELF_EPACKAGE_ECLASSIFIERS);
        return selectDescription;
    }

    private org.eclipse.sirius.properties.RadioDescription createRadio() {
        org.eclipse.sirius.properties.RadioDescription radioDescription = PropertiesFactory.eINSTANCE.createRadioDescription();
        radioDescription.setName("myRadio"); //$NON-NLS-1$
        radioDescription.setLabelExpression("my radio"); //$NON-NLS-1$
        radioDescription.setValueExpression("EClass"); //$NON-NLS-1$
        radioDescription.setCandidatesExpression(AQL_SELF_EPACKAGE_ECLASSIFIERS);
        radioDescription.setValueExpression(AQL_TRUE);
        return radioDescription;
    }

    private org.eclipse.sirius.properties.CheckboxDescription createCheckbox() {
        org.eclipse.sirius.properties.CheckboxDescription checkboxDescription = PropertiesFactory.eINSTANCE.createCheckboxDescription();
        checkboxDescription.setName("myCheckbox"); //$NON-NLS-1$
        checkboxDescription.setLabelExpression("my checkbox"); //$NON-NLS-1$
        checkboxDescription.setValueExpression(AQL_TRUE);
        return checkboxDescription;
    }

    private org.eclipse.sirius.properties.TextAreaDescription createTextArea() {
        org.eclipse.sirius.properties.TextAreaDescription textAreaDescription = PropertiesFactory.eINSTANCE.createTextAreaDescription();
        textAreaDescription.setName("myTextArea"); //$NON-NLS-1$
        textAreaDescription.setLabelExpression("my TextField"); //$NON-NLS-1$
        textAreaDescription.setValueExpression("aql:self.name"); //$NON-NLS-1$
        return textAreaDescription;
    }

    private org.eclipse.sirius.properties.TextDescription createTextfield() {
        org.eclipse.sirius.properties.TextDescription textDescription = PropertiesFactory.eINSTANCE.createTextDescription();
        textDescription.setName("myTextfield"); //$NON-NLS-1$
        textDescription.setLabelExpression("my TextField"); //$NON-NLS-1$
        textDescription.setValueExpression("aql:self.name"); //$NON-NLS-1$
        return textDescription;
    }

    private org.eclipse.sirius.properties.PageDescription createPageDescription(org.eclipse.sirius.properties.GroupDescription groupDescription) {
        org.eclipse.sirius.properties.PageDescription pageDescription = PropertiesFactory.eINSTANCE.createPageDescription();
        pageDescription.getGroups().add(groupDescription);
        pageDescription.setName("myPage"); //$NON-NLS-1$
        pageDescription.setSemanticCandidateExpression("var:self"); //$NON-NLS-1$
        pageDescription.setLabelExpression("Page"); //$NON-NLS-1$
        return pageDescription;
    }

    private org.eclipse.sirius.properties.GroupDescription createGroupDescription() {
        org.eclipse.sirius.properties.GroupDescription groupDescription = PropertiesFactory.eINSTANCE.createGroupDescription();
        groupDescription.setName("myGroup"); //$NON-NLS-1$
        groupDescription.setLabelExpression("Group"); //$NON-NLS-1$
        groupDescription.setSemanticCandidateExpression("var:self"); //$NON-NLS-1$
        groupDescription.setDomainClass("EPackage"); //$NON-NLS-1$
        return groupDescription;
    }

    private void checkResult(FormDescription description) {
        // test SiriusViewExtensionDescriptionConverter
        assertThat(description).isNotNull();
        assertThat(description.getGroupDescriptions()).hasSize(1);
        assertThat(description.getPageDescriptions()).hasSize(1);
        assertThat(description.getPageDescriptions().get(0).getGroupDescriptions()).hasSize(1);
        assertThat(description.getPageDescriptions()).hasSize(1);
        assertThat(description.getPageDescriptions().get(0).getGroupDescriptions().get(0)).isEqualTo(description.getGroupDescriptions().get(0));
        assertThat(description.getGroupDescriptions().stream().flatMap(g -> g.getControlDescriptions().stream())).hasSize(6);
        assertThat(description.getGroupDescriptions().stream().flatMap(g -> g.getControlDescriptions().stream()).filter(CheckboxDescription.class::isInstance)).hasSize(1);
        assertThat(description.getGroupDescriptions().stream().flatMap(g -> g.getControlDescriptions().stream()).filter(RadioDescription.class::isInstance)).hasSize(1);
        assertThat(description.getGroupDescriptions().stream().flatMap(g -> g.getControlDescriptions().stream()).filter(SelectDescription.class::isInstance)).hasSize(1);
        assertThat(description.getGroupDescriptions().stream().flatMap(g -> g.getControlDescriptions().stream()).filter(TextfieldDescription.class::isInstance)).hasSize(1);
        assertThat(description.getGroupDescriptions().stream().flatMap(g -> g.getControlDescriptions().stream()).filter(TextfieldDescription.class::isInstance)).hasSize(1);
        Optional<ForDescription> forOptional = description.getGroupDescriptions().stream().flatMap(g -> g.getControlDescriptions().stream()).filter(ForDescription.class::isInstance)
                .map(ForDescription.class::cast).findFirst();
        assertThat(forOptional).isNotEmpty();
        assertThat(forOptional.get().getIfDescriptions()).hasSize(1);
        assertThat(forOptional.get().getIfDescriptions().stream().findFirst().get().getWidgetDescription()).isNotNull();

        // Test FormRenderer
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, EcorePackage.eINSTANCE);

        FormRenderer formRenderer = new FormRenderer();
        FormComponentProps props = new FormComponentProps(variableManager, description);
        Element element = new Element(FormComponent.class, props);
        Form form = formRenderer.render(element);

        assertThat(form.getPages()).hasSize(1);
        assertThat(form.getPages().stream().flatMap(p -> p.getGroups().stream())).hasSize(1);
        assertThat(form.getPages().stream().flatMap(p -> p.getGroups().stream()).flatMap(g -> g.getWidgets().stream())).hasSize(5);

        List<Group> groups = form.getPages().stream().flatMap(p -> p.getGroups().stream()).collect(Collectors.toList());
        this.checkIdsInGroups(groups);
    }

    /**
     * Checks that inside a group all widget id are different.
     *
     * @param groups
     *            The list of groups
     */
    private void checkIdsInGroups(List<Group> groups) {
        for (Group group : groups) {
            List<AbstractWidget> widgets = group.getWidgets();
            for (int i = 0; i < widgets.size(); i++) {
                AbstractWidget abstractWidget = widgets.get(i);
                assertThat(abstractWidget.getId()).endsWith("#" + i); //$NON-NLS-1$
            }
        }
    }
}
