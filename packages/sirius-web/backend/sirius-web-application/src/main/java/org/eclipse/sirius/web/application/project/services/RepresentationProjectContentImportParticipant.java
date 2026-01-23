/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import org.eclipse.sirius.web.application.project.dto.UnserializeImportedRepresentationDataInput;
import org.eclipse.sirius.web.application.project.dto.UnserializeImportedRepresentationSuccessPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectContentImportParticipant;
import org.eclipse.sirius.web.application.project.services.api.IRepresentationImporterUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.eclipse.sirius.web.domain.events.IDomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    private final List<IRepresentationImporterUpdateService> importerUpdateServices;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public RepresentationProjectContentImportParticipant(ObjectMapper objectMapper, List<IRepresentationImporterUpdateService> importerUpdateServices, IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.importerUpdateServices = Objects.requireNonNull(importerUpdateServices);
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
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

    private List<RepresentationSerializedImportData> getRepresentationImportData(ProjectZipContent projectZipContent) {
        String representationsFolderInZip = projectZipContent.projectName() + ZIP_FOLDER_SEPARATOR + REPRESENTATIONS_FOLDER + ZIP_FOLDER_SEPARATOR;
        List<ByteArrayOutputStream> representationDescriptorsContent = projectZipContent.files().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(representationsFolderInZip))
                .map(Map.Entry::getValue)
                .toList();

        return this.getRepresentationImportData(representationDescriptorsContent);
    }

    private List<RepresentationSerializedImportData> getRepresentationImportData(List<ByteArrayOutputStream> outputStreamToTransformToRepresentationDescriptor) {
        List<RepresentationSerializedImportData> representations = new ArrayList<>();
        for (ByteArrayOutputStream outputStream : outputStreamToTransformToRepresentationDescriptor) {
            try {
                byte[] representationDescriptorBytes = outputStream.toByteArray();
                var jsonNode = this.objectMapper.readTree(representationDescriptorBytes);

                var id = UUID.fromString(jsonNode.get("id").asText());
                var projectId = jsonNode.get("projectId").asText();
                var descriptionId = jsonNode.get("descriptionId").asText();
                var targetObjectId = jsonNode.get("targetObjectId").asText();
                var label = jsonNode.get("label").asText();
                var kind = jsonNode.get("kind").asText();
                var representation = jsonNode.get("representation").toString();

                representations.add(new RepresentationSerializedImportData(id, projectId, descriptionId, targetObjectId, label, kind, representation));
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
     * @param representationId
     *         the ID of the representation to look for in Manifest
     * @return the representation details from Manifest
     */
    private Map<?, ?> getRepresentationManifest(String representationId, ProjectZipContent projectZipContent) {
        Object representationsFromManifest = projectZipContent.manifest().get(ProjectZipContent.REPRESENTATIONS);
        if (representationsFromManifest instanceof Map && representationId != null) {
            Object representationFromManifest = ((Map<?, ?>) representationsFromManifest).get(representationId);
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
        for (RepresentationSerializedImportData serializedData : this.getRepresentationImportData(projectZipContent)) {
            var metadata = this.getRepresentationImportMetadata(serializedData.id().toString(), projectZipContent);
            UnserializeImportedRepresentationDataInput unserializeImportedRepresentationDataInput = new UnserializeImportedRepresentationDataInput(inputId, serializedData, metadata);
            editingContextEventProcessor.handle(unserializeImportedRepresentationDataInput)
                    .filter(UnserializeImportedRepresentationSuccessPayload.class::isInstance)
                    .map(UnserializeImportedRepresentationSuccessPayload.class::cast)
                    .blockOptional()
                    .ifPresent(payload -> createRepresentation(inputId, payload.representationImportData(), metadata, editingContextEventProcessor, documentIdMapping, semanticIdMapping));
        }
    }

    private void createRepresentation(UUID inputId, RepresentationImportData representationData, RepresentationImportMetadata representationMetadata, IEditingContextEventProcessor editingContextEventProcessor, Map<String, String> documentIdMapping, Map<String, String> semanticIdMapping) {
        String objectId = this.getNewObjectId(representationMetadata.targetObjectUri(), documentIdMapping, semanticIdMapping);

        CreateRepresentationInput createRepresentationInput = new CreateRepresentationInput(inputId, editingContextEventProcessor.getEditingContextId(), representationMetadata.descriptionUri(), objectId, representationData.label());
        var representationPayloadCreated = editingContextEventProcessor.handle(createRepresentationInput)
                .filter(CreateRepresentationSuccessPayload.class::isInstance)
                .map(CreateRepresentationSuccessPayload.class::cast)
                .blockOptional();

        if (representationPayloadCreated.isPresent()) {
            var newRepresentationId = representationPayloadCreated.get().representation().id();
            var editingContextId = editingContextEventProcessor.getEditingContextId();
            this.importerUpdateServices.stream()
                    .filter(importerUpdateService -> importerUpdateService.canHandle(editingContextId, representationData))
                    .forEach(importerUpdateService -> importerUpdateService.handle(semanticIdMapping, createRepresentationInput, editingContextId, newRepresentationId, representationData));
        }
    }


    private RepresentationImportMetadata getRepresentationImportMetadata(String representationId, ProjectZipContent projectZipContent) {
        Map<?, ?> representationManifest = this.getRepresentationManifest(representationId, projectZipContent);

        String targetObjectURI = Optional.ofNullable(representationManifest.get(ProjectZipContent.TARGET_OBJECT_URI))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse("");
        String descriptionURI = Optional.ofNullable(representationManifest.get(ProjectZipContent.DESCRIPTION_URI))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse("");
        String latestMigrationPerformed = Optional.ofNullable(representationManifest.get(ProjectZipContent.LATEST_MIGRATION_PERFORMED))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse("none");
        String migrationVersion = Optional.ofNullable(representationManifest.get(ProjectZipContent.MIGRATION_VERSION))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse("0");

        return new RepresentationImportMetadata(descriptionURI, targetObjectURI, latestMigrationPerformed, migrationVersion);
    }
}
