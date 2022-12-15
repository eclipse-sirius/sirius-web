/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
package org.eclipse.sirius.components.compatibility.emf.compatibility.properties;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.components.compatibility.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.components.compatibility.emf.AQLInterpreterFactory;
import org.eclipse.sirius.components.compatibility.emf.SemanticCandidatesProvider;
import org.eclipse.sirius.components.compatibility.emf.modeloperations.ModelOperationHandlerSwitch;
import org.eclipse.sirius.components.compatibility.forms.ViewExtensionDescriptionConverter;
import org.eclipse.sirius.components.compatibility.services.representations.IdentifiedElementLabelProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.RadioDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.forms.renderer.FormRenderer;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.properties.Category;
import org.eclipse.sirius.properties.PropertiesFactory;
import org.eclipse.sirius.properties.ViewExtensionDescription;
import org.junit.jupiter.api.Test;

/**
 * Tests conversion of viewExtensionDescription to Sirius Web FormDescription.
 *
 * @author fbarbin
 */
public class FormRendererTests {

    private static final String AQL_TRUE = "aql:true";

    private static final String AQL_SELF_EPACKAGE_ECLASSIFIERS = "aql:self.ePackage.eClassifiers";

    @Test
    public void testEcoreModel() {
        ViewExtensionDescription viewExtensionDescription = this.createSiriusProperties();

        AQLInterpreterFactory interpreterFactory = new AQLInterpreterFactory() {
            @Override
            public AQLInterpreter create(ViewExtensionDescription viewExtensionDescription) {
                return new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
            }
        };

        IObjectService objectService = new IObjectService.NoOp();
        IRepresentationMetadataSearchService representationMetadataSearchService = new IRepresentationMetadataSearchService.NoOp();
        IIdentifierProvider identifierProvider = new IIdentifierProvider.NoOp() {
            @Override
            public String getIdentifier(Object element) {
                return UUID.randomUUID().toString();
            }
        };
        IdentifiedElementLabelProvider identifiedElementLabelProvider = new IdentifiedElementLabelProvider();
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = SemanticCandidatesProvider::new;

        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = interpeter -> new ModelOperationHandlerSwitch(objectService, representationMetadataSearchService, identifierProvider,
                List.of(), interpeter);
        ViewExtensionDescriptionConverter converter = new ViewExtensionDescriptionConverter(objectService, interpreterFactory, identifierProvider, semanticCandidatesProviderFactory,
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
        ifDescription.setName("myIf");
        ifDescription.setPredicateExpression(AQL_TRUE);
        ifDescription.setWidget(checkboxDescription);
        return ifDescription;
    }

    private org.eclipse.sirius.properties.DynamicMappingForDescription createForDescription() {
        org.eclipse.sirius.properties.DynamicMappingForDescription forDescription = PropertiesFactory.eINSTANCE.createDynamicMappingForDescription();
        forDescription.setName("myFor");
        forDescription.setIterator("element");
        forDescription.setIterableExpression(AQL_SELF_EPACKAGE_ECLASSIFIERS);
        return forDescription;
    }

    private org.eclipse.sirius.properties.SelectDescription createSelect() {
        org.eclipse.sirius.properties.SelectDescription selectDescription = PropertiesFactory.eINSTANCE.createSelectDescription();
        selectDescription.setName("myRadio");
        selectDescription.setLabelExpression("my select");
        selectDescription.setValueExpression("EClassifier");
        selectDescription.setCandidatesExpression(AQL_SELF_EPACKAGE_ECLASSIFIERS);
        return selectDescription;
    }

    private org.eclipse.sirius.properties.RadioDescription createRadio() {
        org.eclipse.sirius.properties.RadioDescription radioDescription = PropertiesFactory.eINSTANCE.createRadioDescription();
        radioDescription.setName("myRadio");
        radioDescription.setLabelExpression("my radio");
        radioDescription.setValueExpression("EClass");
        radioDescription.setCandidatesExpression(AQL_SELF_EPACKAGE_ECLASSIFIERS);
        radioDescription.setValueExpression(AQL_TRUE);
        return radioDescription;
    }

    private org.eclipse.sirius.properties.CheckboxDescription createCheckbox() {
        org.eclipse.sirius.properties.CheckboxDescription checkboxDescription = PropertiesFactory.eINSTANCE.createCheckboxDescription();
        checkboxDescription.setName("myCheckbox");
        checkboxDescription.setLabelExpression("my checkbox");
        checkboxDescription.setValueExpression(AQL_TRUE);
        return checkboxDescription;
    }

    private org.eclipse.sirius.properties.TextAreaDescription createTextArea() {
        org.eclipse.sirius.properties.TextAreaDescription textAreaDescription = PropertiesFactory.eINSTANCE.createTextAreaDescription();
        textAreaDescription.setName("myTextArea");
        textAreaDescription.setLabelExpression("my TextField");
        textAreaDescription.setValueExpression("aql:self.name");
        return textAreaDescription;
    }

    private org.eclipse.sirius.properties.TextDescription createTextfield() {
        org.eclipse.sirius.properties.TextDescription textDescription = PropertiesFactory.eINSTANCE.createTextDescription();
        textDescription.setName("myTextfield");
        textDescription.setLabelExpression("my TextField");
        textDescription.setValueExpression("aql:self.name");
        return textDescription;
    }

    private org.eclipse.sirius.properties.PageDescription createPageDescription(org.eclipse.sirius.properties.GroupDescription groupDescription) {
        org.eclipse.sirius.properties.PageDescription pageDescription = PropertiesFactory.eINSTANCE.createPageDescription();
        pageDescription.getGroups().add(groupDescription);
        pageDescription.setName("myPage");
        pageDescription.setSemanticCandidateExpression("var:self");
        pageDescription.setLabelExpression("Page");
        return pageDescription;
    }

    private org.eclipse.sirius.properties.GroupDescription createGroupDescription() {
        org.eclipse.sirius.properties.GroupDescription groupDescription = PropertiesFactory.eINSTANCE.createGroupDescription();
        groupDescription.setName("myGroup");
        groupDescription.setLabelExpression("Group");
        groupDescription.setSemanticCandidateExpression("var:self");
        groupDescription.setDomainClass("EPackage");
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
        variableManager.put(VariableManager.SELF, List.of(EcorePackage.eINSTANCE));

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
                assertThat(abstractWidget.getId()).endsWith("#" + i);
            }
        }
    }
}
