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
package org.eclipse.sirius.web.application.views.explorer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectInput;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateRepresentationInput;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateRepresentationSuccessPayload;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationIconURL;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handler used to duplicate a representation.
 *
 * @author frouene
 */
@Service
public class DuplicateRepresentationEventHandler implements IEditingContextEventHandler {

    private static final String NEW_REPRESENTATION_METADATA = "newRepresentationMetadata";
    private static final String COPY_SUFFIX = "_copy";

    private final Logger logger = LoggerFactory.getLogger(DuplicateRepresentationEventHandler.class);

    private final ICollaborativeMessageService messageService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final ObjectMapper objectMapper;

    private final Counter counter;

    public DuplicateRepresentationEventHandler(ICollaborativeMessageService messageService, IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationSearchService representationSearchService,
            IRepresentationMetadataPersistenceService representationMetadataPersistenceService, IRepresentationPersistenceService representationPersistenceService, ObjectMapper objectMapper,
            MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationMetadataPersistenceService = Objects.requireNonNull(representationMetadataPersistenceService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.objectMapper = Objects.requireNonNull(objectMapper);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof DuplicateRepresentationInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        List<Message> messages = List.of(new Message(this.messageService.invalidInput(input.getClass().getSimpleName(), DuplicateObjectInput.class.getSimpleName()), MessageLevel.ERROR));
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        IPayload payload = new ErrorPayload(input.id(), messages);

        if (input instanceof DuplicateRepresentationInput duplicateRepresentationInput) {
            IStatus duplicationResult = this.duplicateRepresentation(editingContext, duplicateRepresentationInput.representationId(), duplicateRepresentationInput);

            if (duplicationResult instanceof Success success) {
                payload = new DuplicateRepresentationSuccessPayload(input.id(), (org.eclipse.sirius.components.core.RepresentationMetadata) success.getParameters()
                        .get(NEW_REPRESENTATION_METADATA), success.getMessages());
                changeDescription = new ChangeDescription(success.getChangeKind(), editingContext.getId(), input);
            } else if (duplicationResult instanceof Failure failure) {
                payload = new ErrorPayload(input.id(), failure.getMessages());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus duplicateRepresentation(IEditingContext editingContext, String representationId, DuplicateRepresentationInput duplicateRepresentationInput) {

        Optional<RepresentationMetadata> representationMetadataOptional = new UUIDParser().parse(representationId)
                .flatMap(this.representationMetadataSearchService::findMetadataById);
        Optional<IRepresentation> representationOptional = this.representationSearchService.findById(editingContext, representationId);

        if (representationMetadataOptional.isPresent() && representationOptional.isPresent()) {
            String duplicatedRepresentationId = UUID.randomUUID().toString();
            var duplicatedRepresentationMetadata = org.eclipse.sirius.components.core.RepresentationMetadata.newRepresentationMetadata(duplicatedRepresentationId)
                    .kind(representationMetadataOptional.get().getKind())
                    .label(representationMetadataOptional.get().getLabel() + COPY_SUFFIX)
                    .descriptionId(representationMetadataOptional.get().getDescriptionId())
                    .iconURLs(representationMetadataOptional.get().getIconURLs().stream().map(RepresentationIconURL::url).toList())
                    .build();

            var content = this.getContent(representationOptional.get(), representationId, duplicatedRepresentationId);

            this.representationMetadataPersistenceService.save(duplicateRepresentationInput, editingContext, duplicatedRepresentationMetadata, representationMetadataOptional.get()
                    .getTargetObjectId());
            this.representationPersistenceService.save(duplicateRepresentationInput, editingContext, duplicatedRepresentationId, content, representationOptional.get().getKind());
            return new Success(ChangeKind.REPRESENTATION_CREATION, Map.of(NEW_REPRESENTATION_METADATA, duplicatedRepresentationMetadata));
        }
        return new Failure("The duplication of the representation has failed");
    }

    private String getContent(IRepresentation representation, String oldRepresentationId, String newRepresentationId) {
        String content = "";
        try {
            content = this.objectMapper.writeValueAsString(representation);
            content = content.replace(oldRepresentationId, newRepresentationId);
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return content;
    }
}
