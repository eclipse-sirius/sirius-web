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
package org.eclipse.sirius.web.services;

import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.FormDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.FormVariableBuilder;
import org.eclipse.sirius.components.view.builder.generated.GroupDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.ListDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.PageDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.TreeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.ListDescription;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.TreeDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;

import org.springframework.stereotype.Service;

/**
 * Used to provide a Form description for testing purpose.
 *
 * @author mcharfadi
 */
@Service
public class FormVariableViewPreEditingContextProcessor implements IEditingContextProcessor  {

    public static final String REPRESENTATION_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=formDescription&sourceKind=view&sourceId=c70a4bb6-7310-335d-bda7-a4d1b7c5486f&sourceElementId=e932123d-b916-3537-84d2-86a4f5873d93";

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

        // Add an ID to all view elements
        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("FormVariableFormDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("FormVariableFormDescription"));
        resource.getContents().add(view);

        return view;
    }

    private FormDescription createFormDescription() {
        TreeDescription treeDescription = new TreeDescriptionBuilder()
                .name("Tree")
                .childrenExpression("aql:self.eContents()}")
                .labelExpression("Tree")
                .treeItemLabelExpression("aql:self.name")
                .isCheckableExpression("aql:true")
                .checkedValueExpression("aql:listValues->includes(self)")
                .build();

        ListDescription listDescription = new ListDescriptionBuilder()
                .name("List")
                .labelExpression("List")
                .displayExpression("aql:candidate.name")
                .valueExpression("aql:listValues")
                .build();

        GroupDescription groupDescription = new GroupDescriptionBuilder()
                .name("Group")
                .labelExpression("Group")
                .children(treeDescription, listDescription)
                .build();

        PageDescription pageDescription = new PageDescriptionBuilder()
                .name("Page")
                .labelExpression("Page")
                .domainType("domain:Domain")
                .groups(groupDescription)
                .build();

        return new FormDescriptionBuilder()
                .name("Form")
                .formVariables(new FormVariableBuilder().name("listValues")
                        .defaultValueExpression("aql:self.eContents()->at(1)")
                        .build())
                .titleExpression("aql:'Form'")
                .pages(pageDescription)
                .domainType("ecore:EPackage")
                .build();
    }
}
