/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import org.eclipse.sirius.components.collaborative.dto.EditingContextChildObjectCreationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextChildObjectCreationDescriptionsPayload;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Event handler to find/compute all the child object accessible from a given editing context.
 *
 * @author frouene
 */
@Service
public class EditingContextChildObjectCreationDescriptionsEventHandler implements IEditingContextEventHandler {

    private final IEditService editService;

    public EditingContextChildObjectCreationDescriptionsEventHandler(IEditService editService) {
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EditingContextChildObjectCreationDescriptionsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        List<ChildCreationDescription> childCreationDescriptions = List.of();
        if (input instanceof EditingContextChildObjectCreationDescriptionsInput editingContextChildObjectCreationDescriptionsInput) {
            childCreationDescriptions = this.editService.getChildCreationDescriptions(editingContext, editingContextChildObjectCreationDescriptionsInput.containerId(),
                    editingContextChildObjectCreationDescriptionsInput.referenceKind());
        }
        payloadSink.tryEmitValue(new EditingContextChildObjectCreationDescriptionsPayload(input.id(), childCreationDescriptions));
    }
}
