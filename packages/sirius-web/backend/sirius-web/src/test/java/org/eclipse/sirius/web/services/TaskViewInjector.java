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

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionSuccessPayload;
import org.eclipse.sirius.components.task.starter.services.view.ViewDeckDescriptionBuilder;
import org.eclipse.sirius.components.task.starter.services.view.ViewGanttDescriptionBuilder;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * Used to inject the view from tasks into an editing context.
 *
 * @author sbegaudeau
 */
@Service
public class TaskViewInjector implements BiFunction<IEditingContext, IInput, IPayload> {
    @Override
    public IPayload apply(IEditingContext editingContext, IInput input) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            emfEditingContext.getDomain().getResourceSet().getResources().add(this.getTaskView().eResource());

            return new ExecuteEditingContextFunctionSuccessPayload(input.id(), null);
        }
        return new ErrorPayload(input.id(), "Invalid editing context", List.of());
    }

    private View getTaskView() {
        View view = ViewFactory.eINSTANCE.createView();
        new ViewGanttDescriptionBuilder().addRepresentationDescription(view);
        new ViewDeckDescriptionBuilder().addRepresentationDescriptions(view);

        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("TaskView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("TaskView"));
        resource.getContents().add(view);

        return view;
    }
}
