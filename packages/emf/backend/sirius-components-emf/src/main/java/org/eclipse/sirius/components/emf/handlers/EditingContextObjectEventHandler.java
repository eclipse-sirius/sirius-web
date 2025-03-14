/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.dto.EditingContextObjectInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextObjectPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Event handler to find an object in the editing context.
 *
 * @author gdaniel
 */
@Service
public class EditingContextObjectEventHandler implements IEditingContextEventHandler {

    private final IObjectService objectService;

    public EditingContextObjectEventHandler(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EditingContextObjectInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        Optional<Object> object = Optional.empty();
        if (input instanceof EditingContextObjectInput objectInput) {
            object = this.objectService.getObject(editingContext, objectInput.objectId());
            if (object.isEmpty() && editingContext instanceof IEMFEditingContext emfEditingContext) {
                object = emfEditingContext.getDomain().getResourceSet().getResources().stream().filter(resource -> resource.getURI().toString().contains(objectInput.objectId()))
                        .map(Object.class::cast)
                        .findFirst();
            }
        }
        payloadSink.tryEmitValue(new EditingContextObjectPayload(input.id(), object));
    }
}
