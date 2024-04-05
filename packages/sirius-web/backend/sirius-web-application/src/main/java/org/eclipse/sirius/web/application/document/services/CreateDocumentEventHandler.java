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
package org.eclipse.sirius.web.application.document.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.document.dto.CreateDocumentInput;
import org.eclipse.sirius.web.application.document.dto.CreateDocumentSuccessPayload;
import org.eclipse.sirius.web.application.document.services.api.IStereotypeHandler;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Event handler used to create a new document from a stereotype.
 *
 * @author sbegaudeau
 */
@Service
public class CreateDocumentEventHandler implements IEditingContextEventHandler {

    private final List<IStereotypeHandler> stereotypeHandlers;

    private final IMessageService messageService;

    private final Counter counter;

    public CreateDocumentEventHandler(List<IStereotypeHandler> stereotypeHandlers, IMessageService messageService, MeterRegistry meterRegistry) {
        this.stereotypeHandlers = Objects.requireNonNull(stereotypeHandlers);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof CreateDocumentInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        IPayload payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof CreateDocumentInput createDocumentInput) {
            if (createDocumentInput.name().isBlank()) {
                payload = new ErrorPayload(input.id(), this.messageService.invalidName());
            } else {
                var optionalStereotypeHandler = this.stereotypeHandlers.stream()
                        .filter(handler -> handler.canHandle(editingContext, createDocumentInput.stereotypeId()))
                        .findFirst();

                if (optionalStereotypeHandler.isPresent()) {
                    var handler = optionalStereotypeHandler.get();
                    var optionalDocument = handler.handle(editingContext, createDocumentInput.stereotypeId(), createDocumentInput.name());
                    if (optionalDocument.isPresent()) {
                        payload = new CreateDocumentSuccessPayload(input.id(), optionalDocument.get());
                        changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
                    }
                } else {
                    payload = new ErrorPayload(input.id(), this.messageService.notFound());
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
