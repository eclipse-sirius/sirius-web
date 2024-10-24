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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
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

    private final List<IRepresentationMetadataProvider> representationMetadataProviders;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public RenameRepresentationEventHandler(List<IRepresentationMetadataProvider> representationMetadataProviders, IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationMetadataPersistenceService representationMetadataPersistenceService,
            ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationMetadataProviders = Objects.requireNonNull(representationMetadataProviders);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationMetadataPersistenceService = Objects.requireNonNull(representationMetadataPersistenceService);
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
            var representationId = renameRepresentationInput.representationId();
            var optionalRepresentationUUID = new UUIDParser().parse(representationId);
            var newLabel = renameRepresentationInput.newLabel();
            var optionalRepresentationMetadata = this.representationMetadataProviders.stream()
                    .flatMap(provider -> provider.getMetadata(representationId).stream())
                    .findFirst();
            if (optionalRepresentationMetadata.isPresent() && optionalRepresentationUUID.isPresent()) {
                var representationMetadata = optionalRepresentationMetadata.get();
                var representationUUID = optionalRepresentationUUID.get();
                var renamedRepresentation = RepresentationMetadata.newRepresentationMetadata(representationMetadata)
                        .label(newLabel)
                        .build();

                var optionalTargetObjectId = this.representationMetadataSearchService.findMetadataById(representationUUID)
                        .map(org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata::getTargetObjectId);
                if (optionalTargetObjectId.isPresent()) {
                    var targetObjectId = optionalTargetObjectId.get();
                    this.representationMetadataPersistenceService.save(renameRepresentationInput, editingContext, renamedRepresentation, targetObjectId);

                    payload = new RenameRepresentationSuccessPayload(renameRepresentationInput.id(), renamedRepresentation);
                    changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_RENAMING, renameRepresentationInput.representationId(), renameRepresentationInput);
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
