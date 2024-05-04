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
import org.eclipse.sirius.components.view.builder.generated.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.FormDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.GroupDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.PageDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.SliderDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based form description to tests sliders.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class FormWithSliderDescriptionProvider implements IEditingContextProcessor {

    private final IFormIdProvider formIdProvider;

    private final View view;

    private FormDescription formDescription;

    public FormWithSliderDescriptionProvider(IFormIdProvider formIdProvider) {
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
        View sliderFormView = viewBuilder.build();
        sliderFormView.getDescriptions().add(this.createFormDescription());

        sliderFormView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("FormWithSliderDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("FormWithSliderDescription"));
        resource.getContents().add(sliderFormView);

        return sliderFormView;
    }

    private FormDescription createFormDescription() {
        var prioritySliderDescription = new SliderDescriptionBuilder()
                .name("Slider")
                .labelExpression("aql:'Cost'")
                .helpExpression("The cost of the task")
                .currentValueExpression("aql:self.cost")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:self")
                                .children(
                                        new SetValueBuilder()
                                                .featureName("cost")
                                                .valueExpression("aql:newValue")
                                                .build()
                                )
                                .build()
                )
                .build();

        var groupDescription = new GroupDescriptionBuilder()
                .name("Group")
                .labelExpression("Group")
                .semanticCandidatesExpression("aql:self")
                .children(prioritySliderDescription)
                .build();

        var pageDescription = new PageDescriptionBuilder()
                .name("Page")
                .labelExpression("Page")
                .domainType("papaya:Task")
                .semanticCandidatesExpression("aql:self")
                .groups(groupDescription)
                .build();

        this.formDescription = new FormDescriptionBuilder()
                .name("Form")
                .titleExpression("aql:'FormWithSlider'")
                .domainType("papaya:Task")
                .pages(pageDescription)
                .build();

        return this.formDescription;
    }
}
