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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.components.view.form.CheckboxDescription;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.LabelDescription;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.SelectDescription;
import org.eclipse.sirius.components.view.form.TextfieldDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.widgets.reference.ReferenceFactory;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.web.sample.services.DomainAttributeServices;
import org.eclipse.sirius.web.services.api.representations.IInMemoryViewRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * Provides custom Details view for some of the Domain DSL elements based on a View-based Form description.
 *
 * @author pcdavid
 */
@Configuration
public class DomainPropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    private static final String CORE_PROPERTIES = "Core Properties";

    private final ViewFormDescriptionConverter converter;

    private final IFeedbackMessageService feedbackMessageService;

    private final IInMemoryViewRegistry viewRegistry;

    public DomainPropertiesConfigurer(ViewFormDescriptionConverter converter, IFeedbackMessageService feedbackMessageService, IInMemoryViewRegistry viewRegistry) {
        this.viewRegistry = Objects.requireNonNull(viewRegistry);
        this.converter = Objects.requireNonNull(converter);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
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

        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        this.viewRegistry.register(view);

        // Convert the View-based FormDescription and register the result into the system
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(new DomainAttributeServices(this.feedbackMessageService)), List.of(DomainPackage.eINSTANCE));
        IRepresentationDescription converted = this.converter.convert(viewFormDescription, List.of(), interpreter);
        if (converted instanceof org.eclipse.sirius.components.forms.description.FormDescription formDescription) {
            formDescription.getPageDescriptions().forEach(registry::add);
        }
    }

    private FormDescription getAttributeDetails() {
        FormDescription form = FormFactory.eINSTANCE.createFormDescription();
        form.setName("Attribute Details");
        form.setDomainType("domain::Attribute");
        form.setTitleExpression("Attribute Details");

        PageDescription page = FormFactory.eINSTANCE.createPageDescription();
        page.setDomainType("domain::Attribute");
        page.setPreconditionExpression("");
        page.setLabelExpression("aql:self.name + ': ' + self.getDataType().capitalize()");
        form.getPages().add(page);
        page.getGroups().add(this.createGroup());

        PageDescription entityPage = FormFactory.eINSTANCE.createPageDescription();
        entityPage.setDomainType("domain::Entity");
        entityPage.setPreconditionExpression("");
        entityPage.setLabelExpression("aql:self.name");
        form.getPages().add(entityPage);
        entityPage.getGroups().add(this.createEntityGroup());
        return form;
    }

    private GroupDescription createEntityGroup() {
        GroupDescription group = FormFactory.eINSTANCE.createGroupDescription();
        group.setDisplayMode(GroupDisplayMode.LIST);
        group.setName(CORE_PROPERTIES);
        group.setLabelExpression(CORE_PROPERTIES);
        group.setSemanticCandidatesExpression("aql:self");
        group.getChildren().add(this.createStringAttributeEditWidget("Name", DomainPackage.Literals.NAMED_ELEMENT__NAME.getName()));
        group.getChildren().add(this.createReferenceWidget("Super Type", DomainPackage.Literals.ENTITY__SUPER_TYPES.getName()));
        group.getChildren().add(this.createReferenceWidget("Attributes", DomainPackage.Literals.ENTITY__ATTRIBUTES.getName()));
        group.getChildren().add(this.createReferenceWidget("Relations", DomainPackage.Literals.ENTITY__RELATIONS.getName()));
        group.getChildren().add(this.createBooleanAttributeEditWidget("Abstract", DomainPackage.Literals.ENTITY__ABSTRACT.getName()));
        return group;
    }

    private GroupDescription createGroup() {
        GroupDescription group = FormFactory.eINSTANCE.createGroupDescription();
        group.setDisplayMode(GroupDisplayMode.LIST);
        group.setName(CORE_PROPERTIES);
        group.setLabelExpression(CORE_PROPERTIES);
        group.setSemanticCandidatesExpression("aql:self");
        group.getChildren().add(this.createStringAttributeEditWidget("Name", DomainPackage.Literals.NAMED_ELEMENT__NAME.getName()));
        group.getChildren().add(this.createTypeSelectorWidget());
        group.getChildren().add(this.createBooleanAttributeEditWidget("Optional", DomainPackage.Literals.FEATURE__OPTIONAL.getName()));
        group.getChildren().add(this.createBooleanAttributeEditWidget("Many", DomainPackage.Literals.FEATURE__MANY.getName()));
        group.getChildren().add(this.createCardinalityLabel());
        return group;
    }

    private WidgetDescription createStringAttributeEditWidget(String title, String attributeName) {
        TextfieldDescription textfield = FormFactory.eINSTANCE.createTextfieldDescription();
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
        CheckboxDescription checkbox = FormFactory.eINSTANCE.createCheckboxDescription();
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
        SelectDescription selectWidget = FormFactory.eINSTANCE.createSelectDescription();
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

    private WidgetDescription createReferenceWidget(String name, String referenceName) {
        ReferenceWidgetDescription refWidget = ReferenceFactory.eINSTANCE.createReferenceWidgetDescription();
        refWidget.setName(name);
        refWidget.setLabelExpression(name);
        refWidget.setReferenceNameExpression(referenceName);
        return refWidget;
    }

    private WidgetDescription createCardinalityLabel() {
        LabelDescription cardinalityLabel = FormFactory.eINSTANCE.createLabelDescription();
        cardinalityLabel.setName("Cardinality");
        cardinalityLabel.setLabelExpression("Cardinality");
        cardinalityLabel.setValueExpression("aql:(if self.optional then '0' else '1' endif) + '..' + (if self.many then '*' else '1' endif)");
        return cardinalityLabel;
    }

}
