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
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Event handler to find an object in the editing context.
 *
 * @author gdaniel
 */
@Service
public class EditingContextObjectEventHandler implements IEditingContextEventHandler {

    private final IObjectSearchService objectSearchService;

    public EditingContextObjectEventHandler(IObjectSearchService objectSearchService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EditingContextObjectInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        Optional<Object> optionalObject = Optional.empty();
        if (input instanceof EditingContextObjectInput objectInput) {
            optionalObject = this.objectSearchService.getObject(editingContext, objectInput.objectId());
        }
        payloadSink.tryEmitValue(new EditingContextObjectPayload(input.id(), optionalObject.orElse(null)));
    }
}
