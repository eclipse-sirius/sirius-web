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
package org.eclipse.sirius.web.application.representation.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataUpdateService;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * The handler to rename a representation.
 *
 * @author gcoutable
 */
@Service
public class RenameRepresentationEventHandler implements IEditingContextEventHandler {

    private final IRepresentationMetadataUpdateService representationMetadataUpdateService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public RenameRepresentationEventHandler(IRepresentationMetadataUpdateService representationMetadataUpdateService, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationMetadataUpdateService = Objects.requireNonNull(representationMetadataUpdateService);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof RenameRepresentationInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), RenameRepresentationInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof RenameRepresentationInput renameRepresentationInput) {
            var optionalRepresentationUUID = new UUIDParser().parse(renameRepresentationInput.representationId());
            if (optionalRepresentationUUID.isPresent()) {
                var representationUUID = optionalRepresentationUUID.get();
                var result = this.representationMetadataUpdateService.updateLabel(input, representationUUID, renameRepresentationInput.newLabel());
                if (result instanceof Success<Void>) {
                    payload = new SuccessPayload(renameRepresentationInput.id());
                    changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_RENAMING, renameRepresentationInput.representationId(), renameRepresentationInput);
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
