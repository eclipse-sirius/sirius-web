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
package org.eclipse.sirius.components.collaborative.handlers;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionFromDescriptionIdInput;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to retrieve a representation description from the editing context with a representation description id.
 *
 * @author frouene
 */
@Service
public class GetRepresentationDescriptionFromRepresentationDescriptionIdEventHandler implements IEditingContextEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeMessageService collaborativeMessageService;

    public GetRepresentationDescriptionFromRepresentationDescriptionIdEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, ICollaborativeMessageService collaborativeMessageService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.collaborativeMessageService = Objects.requireNonNull(collaborativeMessageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetRepresentationDescriptionFromDescriptionIdInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        IPayload payload = new ErrorPayload(input.id(), this.collaborativeMessageService.invalidInput(GetRepresentationDescriptionFromDescriptionIdInput.class.getSimpleName(), input.getClass().getSimpleName()));

        if (input instanceof GetRepresentationDescriptionFromDescriptionIdInput getRepresentationDescriptionInput) {
            var optionalRepresentationDescription = this.representationDescriptionSearchService.findById(editingContext, getRepresentationDescriptionInput.representationDescriptionId());
            if (optionalRepresentationDescription.isPresent()) {
                var representationDescription = optionalRepresentationDescription.get();
                payload = new GetRepresentationDescriptionPayload(input.id(), representationDescription);
            }
        }

        payloadSink.tryEmitValue(payload);
    }
}
