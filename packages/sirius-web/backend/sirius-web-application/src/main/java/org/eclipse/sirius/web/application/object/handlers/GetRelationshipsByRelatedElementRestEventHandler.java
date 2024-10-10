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
package org.eclipse.sirius.web.application.object.handlers;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.object.dto.GetRelationshipsByRelatedElementRestInput;
import org.eclipse.sirius.web.application.object.dto.GetRelationshipsByRelatedElementRestSuccessPayload;
import org.eclipse.sirius.web.application.object.services.api.IObjectRestService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to execute the "getRelationshipsByRelatedElement" REST API.
 *
 * @author arichard
 */
@Service
public class GetRelationshipsByRelatedElementRestEventHandler implements IEditingContextEventHandler {

    private final IObjectRestService objectRestService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetRelationshipsByRelatedElementRestEventHandler(ICollaborativeMessageService messageService, MeterRegistry meterRegistry, IObjectRestService objectRestService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.objectRestService = Objects.requireNonNull(objectRestService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetRelationshipsByRelatedElementRestInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetRelationshipsByRelatedElementRestInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (input instanceof GetRelationshipsByRelatedElementRestInput restInput) {
            var elementId = restInput.elementId();
            var direction = restInput.direction();
            var relationships = this.objectRestService.getRelationshipsByRelatedElement(editingContext, elementId, direction);
            payload = new GetRelationshipsByRelatedElementRestSuccessPayload(UUID.randomUUID(), relationships);
        }
        payloadSink.tryEmitValue(payload);

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
