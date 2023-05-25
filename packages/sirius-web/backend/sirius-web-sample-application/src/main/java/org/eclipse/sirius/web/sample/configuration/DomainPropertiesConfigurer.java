/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.sample.configuration;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.CheckboxDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.GroupDisplayMode;
import org.eclipse.sirius.components.view.LabelDescription;
import org.eclipse.sirius.components.view.PageDescription;
import org.eclipse.sirius.components.view.SelectDescription;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.TextfieldDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.WidgetDescription;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.web.sample.services.DomainAttributeServices;
import org.springframework.context.annotation.Configuration;

/**
 * Provides custom Details view for some of the Domain DSL elements based on a View-based Form description.
 *
 * @author pcdavid
 */
@Configuration
public class DomainPropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {
    private final ViewFormDescriptionConverter converter;

    public DomainPropertiesConfigurer(ViewFormDescriptionConverter converter) {
        this.converter = Objects.requireNonNull(converter);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        // Build the actual FormDescription
        FormDescription viewFormDescription = this.getAttributeDetails();

        // The FormDescription must be part of View inside a proper EMF Resource to be correctly handled
        URI uri = URI.createURI(EditingContext.RESOURCE_SCHEME + ":///" + UUID.nameUUIDFromBytes(DomainPropertiesConfigurer.class.getCanonicalName().getBytes()));
        Resource resource = new XMIResourceImpl(uri);
        View view = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createView();
        resource.getContents().add(view);
        view.getDescriptions().add(viewFormDescription);

        // Convert the View-based FormDescription and register the result into the system
        AQLInterpreter interpreter = new AQLInterpreter(List.of(DomainAttributeServices.class), List.of(), List.of(DomainPackage.eINSTANCE));
        IRepresentationDescription converted = this.converter.convert(viewFormDescription, List.of(), interpreter);
        if (converted instanceof org.eclipse.sirius.components.forms.description.FormDescription formDescription) {
            registry.add(formDescription);
        }
    }

    private FormDescription getAttributeDetails() {
        FormDescription form = ViewFactory.eINSTANCE.createFormDescription();
        form.setName("Attribute Details");
        form.setDomainType("domain::Attribute");
        form.setTitleExpression("Attribute Details");

        PageDescription page = ViewFactory.eINSTANCE.createPageDescription();
        // FormDescriptionAggregator works at the Page level and uses a *list* of selected
        // objects as input to determine which pages to instantiate according to their precondition.
        page.setPreconditionExpression("aql:self->first().oclIsKindOf(domain::Attribute)");
        page.setLabelExpression("aql:self.name + ': ' + self.getDataType().capitalize()");
        form.getPages().add(page);
        page.getGroups().add(this.createGroup());
        return form;
    }

    private GroupDescription createGroup() {
        GroupDescription group = ViewFactory.eINSTANCE.createGroupDescription();
        group.setDisplayMode(GroupDisplayMode.LIST);
        group.setName("Core Properties");
        group.setLabelExpression("Core Properties");
        group.setSemanticCandidatesExpression("aql:self");
        group.getWidgets().add(this.createStringAttributeEditWidget("Name", DomainPackage.Literals.NAMED_ELEMENT__NAME.getName()));
        group.getWidgets().add(this.createTypeSelectorWidget());
        group.getWidgets().add(this.createBooleanAttributeEditWidget("Optional", DomainPackage.Literals.FEATURE__OPTIONAL.getName()));
        group.getWidgets().add(this.createBooleanAttributeEditWidget("Many", DomainPackage.Literals.FEATURE__MANY.getName()));
        group.getWidgets().add(this.createCardinalityLabel());
        return group;
    }

    private WidgetDescription createStringAttributeEditWidget(String title, String attributeName) {
        TextfieldDescription textfield = ViewFactory.eINSTANCE.createTextfieldDescription();
        textfield.setName(title);
        textfield.setLabelExpression(title);
        textfield.setValueExpression("aql:self.%s".formatted(attributeName));
        SetValue setValueOperation = ViewFactory.eINSTANCE.createSetValue();
        setValueOperation.setFeatureName(attributeName);
        setValueOperation.setValueExpression("aql:" + ViewFormDescriptionConverter.NEW_VALUE);
        textfield.getBody().add(setValueOperation);
        return textfield;
    }

    private WidgetDescription createBooleanAttributeEditWidget(String title, String attributeName) {
        CheckboxDescription checkbox = ViewFactory.eINSTANCE.createCheckboxDescription();
        checkbox.setName(title);
        checkbox.setLabelExpression(title);
        checkbox.setValueExpression("aql:self.%s".formatted(attributeName));
        SetValue setValueOperation = ViewFactory.eINSTANCE.createSetValue();
        setValueOperation.setFeatureName(attributeName);
        setValueOperation.setValueExpression("aql:" + ViewFormDescriptionConverter.NEW_VALUE);
        checkbox.getBody().add(setValueOperation);
        return checkbox;
    }

    private WidgetDescription createTypeSelectorWidget() {
        SelectDescription selectWidget = ViewFactory.eINSTANCE.createSelectDescription();
        selectWidget.setName("Type");
        selectWidget.setLabelExpression("Type");
        selectWidget.setCandidatesExpression("aql:self.getAvailableDataTypes()");
        selectWidget.setValueExpression("aql:self.getDataType()");
        selectWidget.setCandidateLabelExpression("aql:candidate.capitalize()");
        ChangeContext setValueOperation = ViewFactory.eINSTANCE.createChangeContext();
        setValueOperation.setExpression("aql:self.setDataType(newValue)");
        selectWidget.getBody().add(setValueOperation);
        return selectWidget;
    }

    private WidgetDescription createCardinalityLabel() {
        LabelDescription cardinalityLabel = ViewFactory.eINSTANCE.createLabelDescription();
        cardinalityLabel.setName("Cardinality");
        cardinalityLabel.setLabelExpression("Cardinality");
        cardinalityLabel.setValueExpression("aql:(if self.optional then '0' else '1' fi) + '..' + (if self.many then '*' else '1' fi)");
        return cardinalityLabel;
    }

}
