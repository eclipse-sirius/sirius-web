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

import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.ConditionalTextfieldDescriptionStyleBuilder;
import org.eclipse.sirius.components.view.builder.generated.FormDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.GroupDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.PageDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.TextfieldDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.TextfieldDescriptionStyleBuilder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based form description to tests textfields.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class FormWithTextfieldDescriptionProvider implements IEditingContextProcessor {

    public static final String REPRESENTATION_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=formDescription&sourceKind=view&sourceId=5247c1b5-48c9-31f5-a1cb-0e8d37e2e748&sourceElementId=e932123d-b916-3537-84d2-86a4f5873d93";

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().add(this.createView());
        }
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View view = viewBuilder.build();
        view.getDescriptions().add(this.createFormDescription());

        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("FormWithTextfieldDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("FormWithTextfieldDescription"));
        resource.getContents().add(view);

        return view;
    }

    private FormDescription createFormDescription() {
        var defaultTextStyle = new TextfieldDescriptionStyleBuilder()
                .bold(true)
                .build();

        var isReadOnlyConditionalTextStyle = new ConditionalTextfieldDescriptionStyleBuilder()
                .condition("aql:self.name.size() >= 10")
                .italic(true)
                .build();

        var editTextfield = new ChangeContextBuilder()
                .expression("aql:self")
                .children(
                        new SetValueBuilder()
                                .featureName("name")
                                .valueExpression("aql:newValue")
                                .build()
                )
                .build();

        var nameTextfieldDescription = new TextfieldDescriptionBuilder()
                .name("Textfield")
                .labelExpression("aql:'Name'")
                .helpExpression("The name of the object")
                .isEnabledExpression("aql:self.name.size() < 10")
                .valueExpression("aql:self.name")
                .style(defaultTextStyle)
                .conditionalStyles(isReadOnlyConditionalTextStyle)
                .body(editTextfield)
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
                .domainType("domain:Domain")
                .semanticCandidatesExpression("aql:self")
                .groups(groupDescription)
                .build();

        var formDescription = new FormDescriptionBuilder()
                .name("Form")
                .titleExpression("aql:'FormWithTextfield'")
                .domainType("domain:Domain")
                .pages(pageDescription)
                .build();

        return formDescription;
    }
}
