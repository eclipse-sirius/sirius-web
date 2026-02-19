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
package org.eclipse.sirius.web.application.project.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectContentImportParticipant;
import org.eclipse.sirius.web.application.project.services.api.IRepresentationImporterUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.eclipse.sirius.web.domain.events.IDomainEvent;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

/**
 * {@link IProjectContentImportParticipant} in charge of importing representations in a project.
 *
 * @author Arthur Daussy
 */
@Service
public class RepresentationProjectContentImportParticipant implements IProjectContentImportParticipant {

    private static final String ZIP_FOLDER_SEPARATOR = "/";

    private static final String REPRESENTATIONS_FOLDER = "representations";

    private final Logger logger = LoggerFactory.getLogger(RepresentationProjectContentImportParticipant.class);

    private final ObjectMapper objectMapper;

    private final List<IRepresentationImporterUpdateService> diagramImporterUpdateServices;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IMessageService messageService;

    public RepresentationProjectContentImportParticipant(ObjectMapper objectMapper, List<IRepresentationImporterUpdateService> diagramImporterUpdateServices, IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry,
        IMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.diagramImporterUpdateServices = Objects.requireNonNull(diagramImporterUpdateServices);
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.messageService = messageService;
    }

    @Override
    public boolean canHandle(IDomainEvent event) {
        return event instanceof SemanticDataUpdatedEvent
                && event.causedBy() instanceof CopySemanticDataCause copySemanticCause
                && copySemanticCause.causedBy() instanceof InitializeProjectInput;
    }

    @Override
    public void handle(IDomainEvent event, ProjectZipContent projectContent) {
        if (event instanceof SemanticDataUpdatedEvent semanticDataUpdatedEvent
                && event.causedBy() instanceof CopySemanticDataCause copySemanticCause
                && copySemanticCause.causedBy() instanceof InitializeProjectInput) {
            var editingContextId = semanticDataUpdatedEvent.semanticData().getId().toString();
            this.editingContextEventProcessorRegistry.getOrCreateEditingContextEventProcessor(editingContextId)
                    .ifPresent(editingContextEventProcessor -> this.createRepresentations(event.id(), projectContent, editingContextEventProcessor, copySemanticCause.documentIds(), copySemanticCause.semanticDataIds()));
        }
    }

    private List<RepresentationImportData> getRepresentationImportData(ProjectZipContent projectZipContent) {
        String representationsFolderInZip = projectZipContent.projectName() + ZIP_FOLDER_SEPARATOR + REPRESENTATIONS_FOLDER + ZIP_FOLDER_SEPARATOR;
        List<ByteArrayOutputStream> representationDescriptorsContent = projectZipContent.files().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(representationsFolderInZip))
                .map(Map.Entry::getValue)
                .toList();

        return this.getRepresentationImportData(representationDescriptorsContent);
    }

    private List<RepresentationImportData> getRepresentationImportData(List<ByteArrayOutputStream> outputStreamToTransformToRepresentationDescriptor) {
        List<RepresentationImportData> representations = new ArrayList<>();
        for (ByteArrayOutputStream outputStream : outputStreamToTransformToRepresentationDescriptor) {
            try {
                byte[] representationDescriptorBytes = outputStream.toByteArray();
                RepresentationSerializedImportData representationSerializedImportData = null;
                representationSerializedImportData = this.objectMapper.readValue(representationDescriptorBytes, RepresentationSerializedImportData.class);
                var representationDescriptor = new RepresentationImportData(representationSerializedImportData.id(),
                        representationSerializedImportData.projectId(),
                        representationSerializedImportData.descriptionId(),
                        representationSerializedImportData.targetObjectId(),
                        representationSerializedImportData.label(),
                        representationSerializedImportData.kind(),
                        representationSerializedImportData.representation());
                representations.add(representationDescriptor);
            } catch (IOException exception) {
                logger.warn("Unable to convert one of the given representation : {}", exception.getMessage(), exception);
            }
        }
        return representations;
    }

    /**
     * Get the representation (type, targetObjectUri, descriptionUri) described into the binary file from a given
     * representation identifier.
     *
     * @param representationImportData
     *         the representation to look for in Manifest
     * @return the representation details from Manifest
     */
    private Map<?, ?> getRepresentationManifest(RepresentationImportData representationImportData, ProjectZipContent projectZipContent) {
        Object representationsFromManifest = projectZipContent.manifest().get(ProjectZipContent.REPRESENTATIONS);
        UUID representationId = representationImportData.id();
        if (representationsFromManifest instanceof Map && representationId != null) {
            Object representationFromManifest = ((Map<?, ?>) representationsFromManifest).get(representationImportData.id().toString());
            if (representationFromManifest instanceof Map) {
                return (Map<?, ?>) representationFromManifest;
            }
        }
        return new HashMap<>();
    }

    private String getNewObjectId(String targetObjectURI, Map<String, String> documentIdMapping, Map<String, String> semanticIdMapping) {
        String objectId = null;

        String oldDocumentId = getNewDocumentId(targetObjectURI);
        String newDocumentId = documentIdMapping.get(oldDocumentId);
        if (newDocumentId != null) {
            objectId = targetObjectURI.replace(oldDocumentId, newDocumentId);
        } else {
            objectId = targetObjectURI;
        }

        String oldSemanticElementId = URI.create(targetObjectURI).getFragment();
        String newSemanticElementId = semanticIdMapping.get(oldSemanticElementId);
        if (newSemanticElementId != null) {
            objectId = objectId.replace(oldSemanticElementId, newSemanticElementId);
        }
        return objectId;
    }

    private String getNewDocumentId(String targetObjectURI) {
        return URI.create(targetObjectURI).getPath().substring(1);
    }

    private void createRepresentations(UUID inputId, ProjectZipContent projectZipContent, IEditingContextEventProcessor editingContextEventProcessor, Map<String, String> documentIdMapping, Map<String, String> semanticIdMapping) {
        for (RepresentationImportData representationImportData : this.getRepresentationImportData(projectZipContent)) {
            Map<?, ?> representationManifest = this.getRepresentationManifest(representationImportData, projectZipContent);

            String targetObjectURI = Optional.ofNullable(representationManifest.get(ProjectZipContent.TARGET_OBJECT_URI))
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .orElse("");
            String descriptionURI = Optional.ofNullable(representationManifest.get(ProjectZipContent.DESCRIPTION_URI))
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .orElse("");

            String objectId = this.getNewObjectId(targetObjectURI, documentIdMapping, semanticIdMapping);

            CreateRepresentationInput createRepresentationInput = new CreateRepresentationInput(inputId, editingContextEventProcessor.getEditingContextId(), descriptionURI, objectId, representationImportData.label());
            var timeoutFallback = Mono.just(new ErrorPayload(inputId, this.messageService.timeout()))
                .doOnSuccess(payload -> this.logger.warn("Timeout fallback for the representation upload"));

            var representationPayloadCreated = editingContextEventProcessor.handle(createRepresentationInput)
                    .timeout(Duration.ofSeconds(5), timeoutFallback)
                    .filter(CreateRepresentationSuccessPayload.class::isInstance)
                    .map(CreateRepresentationSuccessPayload.class::cast)
                    .blockOptional();

            if (representationPayloadCreated.isPresent()) {
                var newRepresentationId = representationPayloadCreated.get().representation().id();
                var editingContextId = editingContextEventProcessor.getEditingContextId();
                this.diagramImporterUpdateServices.stream()
                        .filter(diagramImporterUpdateService -> diagramImporterUpdateService.canHandle(editingContextId, representationImportData))
                        .forEach(diagramImporterUpdateService -> diagramImporterUpdateService.handle(semanticIdMapping, createRepresentationInput, editingContextId, newRepresentationId, representationImportData));
            }
        }
    }

}
