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
package org.eclipse.sirius.components.emf.handlers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRootObjectCreationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRootObjectCreationDescriptionsPayload;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Event handler to find/compute all the root object accessible from a given editing context.
 *
 * @author frouene
 */
@Service
public class EditingContextRootObjectCreationDescriptionsEventHandler implements IEditingContextEventHandler {

    private final IEditService editService;

    public EditingContextRootObjectCreationDescriptionsEventHandler(IEditService editService) {
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EditingContextRootObjectCreationDescriptionsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        List<ChildCreationDescription> childCreationDescriptions = List.of();
        if (input instanceof EditingContextRootObjectCreationDescriptionsInput editingContextRootObjectCreationDescriptionsInput) {
            childCreationDescriptions = editService.getRootCreationDescriptions(editingContext, editingContextRootObjectCreationDescriptionsInput.domainId(),
                    editingContextRootObjectCreationDescriptionsInput.suggested());
        }
        payloadSink.tryEmitValue(new EditingContextRootObjectCreationDescriptionsPayload(input.id(), childCreationDescriptions));
    }
}
