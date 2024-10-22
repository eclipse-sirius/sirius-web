/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import org.eclipse.sirius.components.view.builder.generated.form.ConditionalTextfieldDescriptionStyleBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.FormDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.GroupDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.PageDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.TextfieldDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.TextfieldDescriptionStyleBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based form description to test textfield style.
 *
 * @author frouene
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class FormWithStyledTextfieldDescriptionProvider implements IEditingContextProcessor {

    private final IFormIdProvider formIdProvider;

    private final View view;

    private FormDescription formDescription;

    public FormWithStyledTextfieldDescriptionProvider(IFormIdProvider formIdProvider) {
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

        String resourcePath = UUID.nameUUIDFromBytes("FormWithStyledTextfieldDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("FormWithStyledTextfieldDescription"));
        resource.getContents().add(textfieldFormView);

        return textfieldFormView;
    }

    private FormDescription createFormDescription() {
        var defaultTextStyle = new TextfieldDescriptionStyleBuilder()
                .strikeThrough(false)
                .underline(false)
                .bold(false)
                .backgroundColor(new ViewBuilders().newFixedColor().value("#7FFFD4").name("primary-color").build())
                .fontSize(8)
                .foregroundColor(new ViewBuilders().newFixedColor().value("#7FFFD4").name("primary-color").build())
                .italic(false)
                .gridTemplateColumns("none")
                .gridTemplateRows("none")
                .gap("normal")
                .labelGridColumn("auto")
                .labelGridRow("auto")
                .widgetGridColumn("auto")
                .widgetGridRow("auto")
                .build();

        var conditionalStyle = new ConditionalTextfieldDescriptionStyleBuilder()
                .strikeThrough(true)
                .underline(true)
                .bold(true)
                .backgroundColor(new ViewBuilders().newFixedColor().value("#A52A2A").name("secondary-color").build())
                .fontSize(10)
                .foregroundColor(new ViewBuilders().newFixedColor().value("#A52A2A").name("secondary-color").build())
                .italic(true)
                .condition("aql:self.name = 'Human'")
                .gridTemplateColumns("max-content")
                .gridTemplateRows("max-content")
                .gap("1px")
                .labelGridColumn("1")
                .labelGridRow("1")
                .widgetGridColumn("2")
                .widgetGridRow("2")
                .build();


        var nameTextfieldDescription = new TextfieldDescriptionBuilder()
                .name("Textfield")
                .labelExpression("aql:'Name'")
                .valueExpression("aql:self.name")
                .style(defaultTextStyle)
                .conditionalStyles(conditionalStyle)
                .build();

        var groupDescription = new GroupDescriptionBuilder()
                .name("Group")
                .labelExpression("Group")
                .semanticCandidatesExpression("aql:self")
                .children(nameTextfieldDescription)
                .build();

        var pageDescription = new PageDescriptionBuilder()
                .name("Page")
                .labelExpression("Page")
                .domainType("domain:Entity")
                .semanticCandidatesExpression("aql:self")
                .groups(groupDescription)
                .build();

        this.formDescription = new FormDescriptionBuilder()
                .name("Form")
                .titleExpression("aql:'FormWithStyledTextfield'")
                .domainType("domain:Entity")
                .pages(pageDescription)
                .build();

        return this.formDescription;
    }
}
