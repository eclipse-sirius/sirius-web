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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.dto.EditingContextObjectsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextObjectsPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Event handler to find an objects in the editing context.
 *
 * @author pcdavid
 */
@Service
public class EditingContextObjectsEventHandler implements IEditingContextEventHandler {

    private final IObjectSearchService objectSearchService;

    public EditingContextObjectsEventHandler(IObjectSearchService objectSearchService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EditingContextObjectsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        List<Object> result = new ArrayList<>();
        if (input instanceof EditingContextObjectsInput objectsInput) {
            for (var objectId : objectsInput.objectIds()) {
                this.objectSearchService.getObject(editingContext, objectId).ifPresent(result::add);
            }
        }
        payloadSink.tryEmitValue(new EditingContextObjectsPayload(input.id(), result));
    }
}
