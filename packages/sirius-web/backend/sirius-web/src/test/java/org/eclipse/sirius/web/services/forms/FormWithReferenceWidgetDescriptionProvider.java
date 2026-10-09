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
import org.eclipse.sirius.components.view.builder.generated.reference.ReferenceWidgetAddBodyBuilder;
import org.eclipse.sirius.components.view.builder.generated.reference.ReferenceWidgetDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.reference.ReferenceWidgetDescriptionStyleBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.UnsetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
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
        var addBody = new ReferenceWidgetAddBodyBuilder();

        var operation1 = new SetValueBuilder()
                .featureName("name")
                .valueExpression("Added by custom action")
                .build();

        var operation2 = new SetValueBuilder()
                .featureName("superTypes")
                .valueExpression("aql:self.superTypes->union(newValue)")
                .build();

        addBody.body(operation1, operation2);

        customAddReference.setAddBody(addBody.build());
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

        var nameTextfield = new TextfieldDescriptionBuilder()
                .name("Name")
                .labelExpression("Name")
                .valueExpression("aql:self.name")
                .build();

        var readOnlySuperTypesReference = new ReferenceWidgetDescriptionBuilder()
                .name("Read-only super types reference")
                .labelExpression("Read-only super types")
                .referenceNameExpression("superTypes")
                .referenceOwnerExpression("aql:self")
                .isEnabledExpression("aql:false")
                .build();

        var groupDescription = new GroupDescriptionBuilder()
                .name("Group")
                .labelExpression("Group")
                .semanticCandidatesExpression("aql:self")
                .children(nameTextfield, superTypesReference, readOnlySuperTypesReference)
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
}
