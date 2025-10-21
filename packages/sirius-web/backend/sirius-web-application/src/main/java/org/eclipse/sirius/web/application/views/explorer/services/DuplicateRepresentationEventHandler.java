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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectInput;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateRepresentationInput;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateRepresentationSuccessPayload;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationIconURL;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
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

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationContentSearchService representationContentSearchService;

    private final IRepresentationMetadataCreationService representationMetadataCreationService;

    private final IRepresentationContentCreationService representationContentCreationService;

    private final IMessageService messageService;

    private final ObjectMapper objectMapper;

    private final Counter counter;

    private final Logger logger = LoggerFactory.getLogger(DuplicateRepresentationEventHandler.class);

    public DuplicateRepresentationEventHandler(IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationContentSearchService representationContentSearchService, IRepresentationMetadataCreationService representationMetadataCreationService,
                                               IRepresentationContentCreationService representationContentCreationService, IMessageService messageService,  ObjectMapper objectMapper, MeterRegistry meterRegistry) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationContentSearchService = Objects.requireNonNull(representationContentSearchService);
        this.representationMetadataCreationService = Objects.requireNonNull(representationMetadataCreationService);
        this.representationContentCreationService = Objects.requireNonNull(representationContentCreationService);
        this.messageService = Objects.requireNonNull(messageService);
        this.objectMapper = Objects.requireNonNull(objectMapper);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
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
            var optionalRepresentationMetadata = this.duplicateRepresentation(duplicateRepresentationInput.representationId(), duplicateRepresentationInput);
            if (optionalRepresentationMetadata.isPresent()) {
                var duplicatedRepresentationMetadata = optionalRepresentationMetadata.get();
                payload = new DuplicateRepresentationSuccessPayload(input.id(), duplicatedRepresentationMetadata, List.of());
                changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_CREATION, editingContext.getId(), input);
            } else {
                payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<org.eclipse.sirius.components.core.RepresentationMetadata> duplicateRepresentation(String representationId, DuplicateRepresentationInput duplicateRepresentationInput) {
        var optionalRepresentationMetadata = new UUIDParser().parse(representationId)
                .flatMap(this.representationMetadataSearchService::findMetadataById);
        var optionalRepresentationContent = optionalRepresentationMetadata.map(RepresentationMetadata::getId)
                .flatMap(this.representationContentSearchService::findContentById);

        if (optionalRepresentationMetadata.isPresent() && optionalRepresentationContent.isPresent()) {
            var representationMetadataToDuplicate = optionalRepresentationMetadata.get();
            var representationContentToDuplicate = optionalRepresentationContent.get();

            var duplicatedRepresentationId = UUID.randomUUID();
            var duplicatedRepresentationMetadata = RepresentationMetadata.newRepresentationMetadata(duplicatedRepresentationId)
                    .semanticData(representationMetadataToDuplicate.getSemanticData())
                    .targetObjectId(representationMetadataToDuplicate.getTargetObjectId())
                    .descriptionId(representationMetadataToDuplicate.getDescriptionId())
                    .label(representationMetadataToDuplicate.getLabel() + "_copy")
                    .kind(representationMetadataToDuplicate.getKind())
                    .documentation(representationMetadataToDuplicate.getDocumentation())
                    .iconURLs(representationMetadataToDuplicate.getIconURLs())
                    .build(duplicateRepresentationInput);
            this.representationMetadataCreationService.create(duplicatedRepresentationMetadata);

            var duplicatedContent = representationContentToDuplicate.getContent().replace(representationId, duplicatedRepresentationId.toString());
            this.representationContentCreationService.create(duplicateRepresentationInput, duplicatedRepresentationId, representationMetadataToDuplicate.getSemanticData().getId(), duplicatedContent, representationContentToDuplicate.getLastMigrationPerformed(), representationContentToDuplicate.getMigrationVersion());

            var iconURLs = duplicatedRepresentationMetadata.getIconURLs().stream()
                    .map(RepresentationIconURL::url)
                    .toList();
            return Optional.of(new org.eclipse.sirius.components.core.RepresentationMetadata(duplicatedRepresentationMetadata.getId().toString(), duplicatedRepresentationMetadata.getKind(), duplicatedRepresentationMetadata.getLabel(), duplicatedRepresentationMetadata.getDescriptionId(), iconURLs));

        }
        return Optional.empty();
    }
}
