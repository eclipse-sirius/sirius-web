/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.sirius.components.view.builder.generated.form.FormBuilders;
import org.eclipse.sirius.components.view.builder.generated.form.FormDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.GroupDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.PageDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based form description to test buttons.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class FormWithButtonDescriptionProvider implements IEditingContextProcessor {

    private final IFormIdProvider formIdProvider;

    private final View view;

    private FormDescription formDescription;

    public FormWithButtonDescriptionProvider(IFormIdProvider formIdProvider) {
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

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View textfieldFormView = viewBuilder.build();
        textfieldFormView.getDescriptions().add(this.createFormDescription());

        textfieldFormView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("FormWithButtonDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("FormWithButtonDescription"));
        resource.getContents().add(textfieldFormView);

        return textfieldFormView;
    }

    private FormDescription createFormDescription() {
        var clickButton = new ChangeContextBuilder()
                .expression("aql:self")
                .children(
                        new SetValueBuilder()
                                .featureName("name")
                                .valueExpression("aql:''")
                                .build()
                )
                .build();

        var buttonConditionalStyle = new FormBuilders().newConditionalButtonDescriptionStyle()
                .condition("aql:self.name = ''")
                .bold(true)
                .italic(true)
                .strikeThrough(true)
                .underline(true)
                .fontSize(24)
                .build();

        var buttonStyle = new FormBuilders().newButtonDescriptionStyle()
                .bold(false)
                .italic(false)
                .strikeThrough(false)
                .underline(false)
                .fontSize(16)
                .build();

        var buttonDescription = new FormBuilders().newButtonDescription()
                .name("Button")
                .labelExpression("aql:'Button'")
                .buttonLabelExpression("Click")
                .imageExpression("https://www.example.org/images/test.png")
                .body(clickButton)
                .style(buttonStyle)
                .conditionalStyles(buttonConditionalStyle)
                .build();

        var labelConditionalStyle = new FormBuilders().newConditionalLabelDescriptionStyle()
                .condition("aql:self.name = ''")
                .bold(true)
                .italic(true)
                .strikeThrough(true)
                .underline(true)
                .fontSize(24)
                .build();

        var labelStyle = new FormBuilders().newLabelDescriptionStyle()
                .bold(false)
                .italic(false)
                .strikeThrough(false)
                .underline(false)
                .fontSize(16)
                .build();

        var label = new FormBuilders().newLabelDescription()
                .labelExpression("Name")
                .valueExpression("aql:self.name")
                .style(labelStyle)
                .conditionalStyles(labelConditionalStyle)
                .build();

        var groupDescription = new GroupDescriptionBuilder()
                .name("Group")
                .labelExpression("Group")
                .semanticCandidatesExpression("aql:self")
                .children(label, buttonDescription)
                .build();

        var pageDescription = new PageDescriptionBuilder()
                .name("Page")
                .labelExpression("Page")
                .domainType("domain:Domain")
                .semanticCandidatesExpression("aql:self")
                .groups(groupDescription)
                .build();

        this.formDescription = new FormDescriptionBuilder()
                .name("Form")
                .titleExpression("aql:'FormWithButton'")
                .domainType("domain:Domain")
                .pages(pageDescription)
                .build();

        return this.formDescription;
    }
}
