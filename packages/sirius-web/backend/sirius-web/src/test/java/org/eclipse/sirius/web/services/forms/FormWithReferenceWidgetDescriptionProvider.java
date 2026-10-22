/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.services.forms;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.form.FormDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.GroupDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.PageDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.TextfieldDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.reference.ReferenceWidgetAddBuilder;
import org.eclipse.sirius.components.view.builder.generated.reference.ReferenceWidgetCreateButtonDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.reference.ReferenceWidgetDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.reference.ReferenceWidgetDescriptionStyleBuilder;
import org.eclipse.sirius.components.view.builder.generated.reference.ReferenceWidgetRemoveBuilder;
import org.eclipse.sirius.components.view.builder.generated.reference.ReferenceWidgetSetBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.CreateInstanceBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.UnsetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based form description to tests reference widgets.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class FormWithReferenceWidgetDescriptionProvider implements IEditingContextProcessor {

    private final IFormIdProvider formIdProvider;

    private final View view;

    private FormDescription formDescription;

    private FormDescription formDescriptionWithDefaultClear;

    public FormWithReferenceWidgetDescriptionProvider(IFormIdProvider formIdProvider) {
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
        this.view = this.createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    public String getRepresentationDescriptionId() {
        return this.formIdProvider.getId(this.formDescription);
    }

    public String getRepresentationDescriptionIdWithDefaultClear() {
        return this.formIdProvider.getId(this.formDescriptionWithDefaultClear);
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View textfieldFormView = viewBuilder.build();
        this.formDescription = this.createFormDescription(true);
        this.formDescriptionWithDefaultClear = this.createFormDescription(false);
        var customAddReference = new ReferenceWidgetDescriptionBuilder()
                .name("Super types with custom add")
                .labelExpression("Super types with custom add")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .build();
        var add = new ReferenceWidgetAddBuilder();

        var operation1 = new SetValueBuilder()
                .featureName("name")
                .valueExpression("Added by custom action")
                .build();

        var operation2 = new SetValueBuilder()
                .featureName("superTypes")
                .valueExpression("aql:self.superTypes->union(newValue)")
                .build();

        add.body(operation1, operation2);

        customAddReference.setAdd(add.build());
        this.formDescription.getPages().get(0).getGroups().get(0).getChildren().add(customAddReference);
        textfieldFormView.getDescriptions().add(this.formDescription);
        textfieldFormView.getDescriptions().add(this.formDescriptionWithDefaultClear);

        textfieldFormView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("FormWithReferenceWidgetDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("FormWithReferenceWidgetDescription"));
        resource.getContents().add(textfieldFormView);

        return textfieldFormView;
    }

    private FormDescription createFormDescription(boolean customClear) {
        var nameTextfield = new TextfieldDescriptionBuilder()
                .name("Name")
                .labelExpression("Name")
                .valueExpression("aql:self.name")
                .build();

        var groupDescription = new GroupDescriptionBuilder()
                .name("Group")
                .labelExpression("Group")
                .semanticCandidatesExpression("aql:self")
                .children(nameTextfield)
                .children(this.createReferenceWidgetDescriptions(customClear))
                .children(this.createReferenceWidgetDescriptionsForSet())
                .children(this.createReferenceWidgetDescriptionsWithoutAdd())
                .children(this.createReferenceWidgetDescriptionWithDynamicAddPrecondition())
                .children(this.createReferenceWidgetDescriptionsForRemove())
                .build();

        var pageDescription = new PageDescriptionBuilder()
                .name("Page")
                .labelExpression("Page")
                .domainType("domain:Entity")
                .semanticCandidatesExpression("aql:self")
                .groups(groupDescription)
                .build();

        String formName = "Form";
        String formTitle = "FormWithReferenceWidget";
        if (!customClear) {
            formName = "FormWithDefaultClear";
            formTitle = "FormWithReferenceWidgetDefaultClear";
        }

        return new FormDescriptionBuilder()
                .name(formName)
                .titleExpression(formTitle)
                .domainType("domain:Entity")
                .pages(pageDescription)
                .build();
    }

    private ReferenceWidgetDescription[] createReferenceWidgetDescriptions(boolean customClear) {
        var superTypesReferenceStyle = new ReferenceWidgetDescriptionStyleBuilder()
                .bold(true)
                .italic(true)
                .strikeThrough(true)
                .underline(true)
                .build();

        var superTypesReference = new ReferenceWidgetDescriptionBuilder()
                .name("Super types reference")
                .labelExpression("Super types")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .helpExpression("aql:'Specify the super-types of ' + self.name")
                .style(superTypesReferenceStyle)
                .add(new ReferenceWidgetAddBuilder().preconditionExpression("").build())
                .createButton(new ReferenceWidgetCreateButtonDescriptionBuilder().build())
                .build();

        var clearButton = ReferenceFactory.eINSTANCE.createReferenceWidgetClearButtonDescription();
        if (customClear) {
            clearButton.getBody().add(new SetValueBuilder()
                    .featureName("name")
                    .valueExpression("Cleared by custom action")
                    .build());
            clearButton.getBody().add(new UnsetValueBuilder()
                    .featureName("superTypes")
                    .build());
        }
        superTypesReference.setClearButton(clearButton);

        var readOnlySuperTypesReference = new ReferenceWidgetDescriptionBuilder()
                .name("Read-only super types reference")
                .labelExpression("Read-only super types")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .isEnabledExpression("aql:false")
                .add(new ReferenceWidgetAddBuilder().build())
                .build();

        var customCreationReference = new ReferenceWidgetDescriptionBuilder()
                .name("Custom creation super types")
                .labelExpression("Custom creation super types")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .createButton(new ReferenceWidgetCreateButtonDescriptionBuilder()
                        .body(new SetValueBuilder()
                                .featureName("name")
                                .valueExpression("aql:'Custom creation executed'")
                                .build())
                        .build())
                .build();

        var instanceCreationReference = new ReferenceWidgetDescriptionBuilder()
                .name("Instance creation super types")
                .labelExpression("Instance creation super types")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .createButton(new ReferenceWidgetCreateButtonDescriptionBuilder()
                        .body(new ChangeContextBuilder()
                                .expression("aql:creationContainer")
                                .children(new CreateInstanceBuilder()
                                        .typeName("domain::Entity")
                                        .referenceName("types")
                                        .variableName("createdEntity")
                                        .build())
                                .build())
                        .build())
                .build();

        var failingCreationReference = new ReferenceWidgetDescriptionBuilder()
                .name("Failing creation super types")
                .labelExpression("Failing creation super types")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .createButton(new ReferenceWidgetCreateButtonDescriptionBuilder()
                        .body(new ChangeContextBuilder().expression("aql:null").build())
                        .build())
                .build();

        var hiddenCreateButtonReference = new ReferenceWidgetDescriptionBuilder()
                .name("Hidden create button")
                .labelExpression("Hidden create button")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .createButton(new ReferenceWidgetCreateButtonDescriptionBuilder()
                        .preconditionExpression("aql:false")
                        .build())
                .build();

        return new ReferenceWidgetDescription[] {
            superTypesReference, readOnlySuperTypesReference, customCreationReference,
            instanceCreationReference, failingCreationReference, hiddenCreateButtonReference,
        };
    }

    private ReferenceWidgetDescription[] createReferenceWidgetDescriptionsForSet() {
        var targetType = new ReferenceWidgetDescriptionBuilder()
                .name("Target type")
                .labelExpression("Target type")
                .referenceNameExpression("targetType")
                .referenceOwnerExpression("aql:self.eContainer().types.relations->first()")
                .set(new ReferenceWidgetSetBuilder().preconditionExpression("").build())
                .build();

        var customSet = new ReferenceWidgetDescriptionBuilder()
                .name("Target type with custom set")
                .labelExpression("Target type with custom set")
                .referenceNameExpression("targetType")
                .referenceOwnerExpression("aql:self.eContainer().types.relations->first()")
                .set(new ReferenceWidgetSetBuilder()
                        .body(new SetValueBuilder().featureName("name").valueExpression("Set by custom action").build(),
                                new ChangeContextBuilder().expression("aql:referenceOwner")
                                        .children(new SetValueBuilder().featureName("targetType").valueExpression("aql:newValue").build()).build())
                        .build())
                .build();

        var readOnlyTargetType = new ReferenceWidgetDescriptionBuilder()
                .name("Read-only target type")
                .labelExpression("Read-only target type")
                .referenceNameExpression("targetType")
                .referenceOwnerExpression("aql:self.eContainer().types.relations->first()")
                .isEnabledExpression("aql:false")
                .set(new ReferenceWidgetSetBuilder().build())
                .build();

        var withoutSet = new ReferenceWidgetDescriptionBuilder()
                .name("Target type without set")
                .labelExpression("Target type without set")
                .referenceNameExpression("targetType")
                .referenceOwnerExpression("aql:self.eContainer().types.relations->first()")
                .build();

        var dynamicSet = new ReferenceWidgetDescriptionBuilder()
                .name("Target type with dynamic set precondition")
                .labelExpression("Target type with dynamic set precondition")
                .referenceNameExpression("targetType")
                .referenceOwnerExpression("aql:self.eContainer().types.relations->first()")
                .set(new ReferenceWidgetSetBuilder().preconditionExpression("aql:self.name = 'Human'").build())
                .build();

        return new ReferenceWidgetDescription[] { targetType, customSet, readOnlyTargetType, withoutSet, dynamicSet };
    }

    private ReferenceWidgetDescription[] createReferenceWidgetDescriptionsWithoutAdd() {
        var withoutAddReference = new ReferenceWidgetDescriptionBuilder()
                .name("Super types without add")
                .labelExpression("Super types without add")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .createButton(new ReferenceWidgetCreateButtonDescriptionBuilder().build())
                .build();

        var singleValuedWithoutAddReference = new ReferenceWidgetDescriptionBuilder()
                .name("Target type without add")
                .labelExpression("Target type without add")
                .referenceNameExpression("targetType")
                .referenceOwnerExpression("aql:self.eContainer().types.relations->first()")
                .set(new ReferenceWidgetSetBuilder().build())
                .build();

        return new ReferenceWidgetDescription[] { withoutAddReference, singleValuedWithoutAddReference };
    }

    private ReferenceWidgetDescription createReferenceWidgetDescriptionWithDynamicAddPrecondition() {
        return new ReferenceWidgetDescriptionBuilder()
                .name("Super types with dynamic add precondition")
                .labelExpression("Super types with dynamic add precondition")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .add(new ReferenceWidgetAddBuilder().preconditionExpression("aql:self.name = 'Human'").build())
                .build();
    }

    private ReferenceWidgetDescription[] createReferenceWidgetDescriptionsForRemove() {
        var defaultRemoveReference = new ReferenceWidgetDescriptionBuilder()
                .name("Super types with default remove")
                .labelExpression("Super types with default remove")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .build();
        defaultRemoveReference.setRemove(new ReferenceWidgetRemoveBuilder().build());

        var customRemoveReference = new ReferenceWidgetDescriptionBuilder()
                .name("Super types with custom remove")
                .labelExpression("Super types with custom remove")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .build();
        customRemoveReference.setRemove(new ReferenceWidgetRemoveBuilder()
                .preconditionExpression("aql:self.name = 'Human'")
                .body(new SetValueBuilder()
                                .featureName("name")
                                .valueExpression("aql:'Removed ' + valueToRemove.name")
                                .build(),
                        new UnsetValueBuilder()
                                .featureName("superTypes")
                                .elementExpression("aql:valueToRemove")
                                .build())
                .build());

        return new ReferenceWidgetDescription[] { defaultRemoveReference, customRemoveReference };
    }
}
