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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.CopySemanticDataCause;
import org.eclipse.sirius.web.application.project.dto.IProjectBinaryContent;
import org.eclipse.sirius.web.application.project.dto.ImportContentStatus;
import org.eclipse.sirius.web.application.project.dto.InitProjectContentInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectContentImportParticipant;
import org.eclipse.sirius.web.application.project.services.api.IRepresentationImporterUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.eclipse.sirius.web.domain.events.IDomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
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

    private final List<IRepresentationImporterUpdateService> diagramImporterUpdateServices;
    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;
    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public RepresentationProjectContentImportParticipant(ObjectMapper objectMapper, List<IRepresentationImporterUpdateService> diagramImporterUpdateServices,
            IProjectSemanticDataSearchService projectSemanticDataSearchService, IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.objectMapper = objectMapper;
        this.diagramImporterUpdateServices = diagramImporterUpdateServices;
        this.projectSemanticDataSearchService = projectSemanticDataSearchService;
        this.editingContextEventProcessorRegistry = editingContextEventProcessorRegistry;
    }

    @Override
    public boolean canHandle(IDomainEvent event) {
        return event instanceof SemanticDataUpdatedEvent
                && event.causedBy() instanceof CopySemanticDataCause copySemanticCause
                && copySemanticCause.causedBy() instanceof InitProjectContentInput;
    }

    @Override
    public ImportContentStatus handle(IDomainEvent event, IProjectBinaryContent projectContent) {
        SemanticDataUpdatedEvent semanticDataUpdatedEvent = (SemanticDataUpdatedEvent) event;
        CopySemanticDataCause copySemanticCause = (CopySemanticDataCause) semanticDataUpdatedEvent.causedBy();
        InitProjectContentInput uploadProjectContentInput = (InitProjectContentInput) copySemanticCause.causedBy();


        var optionalEditingContextId = this.projectSemanticDataSearchService.findBySemanticDataId(AggregateReference.to(semanticDataUpdatedEvent.semanticData().getId()))
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .map(UUID::toString);

        return optionalEditingContextId.flatMap(this.editingContextEventProcessorRegistry::getOrCreateEditingContextEventProcessor)
                .map(editingContextEventProcessor -> createRepresentations(event.id(), projectContent, editingContextEventProcessor, copySemanticCause.documentIds(), copySemanticCause.semanticDataIds()))
                .orElse(new ImportContentStatus(false, List.of("Unable to get editing context event processor for editing domain id :" + semanticDataUpdatedEvent.semanticData().getId())));

    }

    private List<RepresentationImportData> getRepresentationImportData(IProjectBinaryContent projectContent) {
        String representationsFolderInZip = projectContent.getName() + ZIP_FOLDER_SEPARATOR + REPRESENTATIONS_FOLDER + ZIP_FOLDER_SEPARATOR;

        List<ByteArrayOutputStream> representationDescriptorsContent = this.selectAndTransformIntoRepresentationDescriptorsContent(projectContent.getFileContent(), representationsFolderInZip);

        return this.getRepresentationImportDatas(representationDescriptorsContent);
    }

    private List<ByteArrayOutputStream> selectAndTransformIntoRepresentationDescriptorsContent(Map<String, ByteArrayOutputStream> zipEntryNameToContent, String representationsFolderInZip) {
        return zipEntryNameToContent.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(representationsFolderInZip))
                .map(Map.Entry::getValue)
                .toList();
    }

    private List<RepresentationImportData> getRepresentationImportDatas(List<ByteArrayOutputStream> outputStreamToTransformToRepresentationDescriptor) {
        List<RepresentationImportData> representations = new ArrayList<>();
        for (ByteArrayOutputStream outputStream : outputStreamToTransformToRepresentationDescriptor) {
            try {
                byte[] representationDescriptorBytes = outputStream.toByteArray();
                RepresentationSerializedImportData representationSerializedImportData = null;
                representationSerializedImportData = this.objectMapper.readValue(representationDescriptorBytes, RepresentationSerializedImportData.class);
                var representationDescriptor = new RepresentationImportData(representationSerializedImportData.id(), representationSerializedImportData.projectId(),
                        representationSerializedImportData.descriptionId(), representationSerializedImportData.targetObjectId(), representationSerializedImportData.label(),
                        representationSerializedImportData.kind(), representationSerializedImportData.representation());
                representations.add(representationDescriptor);
            } catch (IOException e) {
                logger.warn("Unable to convert one of the given representation : {}", e.getMessage(), e);
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
    private Map<?, ?> getRepresentationManifest(RepresentationImportData representationImportData, IProjectBinaryContent projectContent) {
        Object representationsFromManifest = projectContent.getManifest().get("representations");
        UUID representationId = representationImportData.id();
        if (representationsFromManifest instanceof Map && representationId != null) {
            Object representationFromManifest = ((Map<?, ?>) representationsFromManifest).get(representationImportData.id().toString());
            if (representationFromManifest instanceof Map) {
                return (Map<?, ?>) representationFromManifest;
            }
        }
        return new HashMap<>();
    }

    /**
     * Adapt the targetObjectURI/object id to point to the new object after import.
     *
     * @param targetObjectURI
     *         the target object URI/id stored in the manifest.
     * @return the URI/id of the equivalent model element in the newly imported documents.
     */
    private String getNewObjectId(String targetObjectURI, IProjectBinaryContent projectContent, Map<String, String> documentIdMapping, Map<String, String> semanticIdMapping) {
        String objectId;

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

    /**
     * Creates all representations in the project thanks to the {@link IEditingContextEventProcessor} and the create
     * representation input. If at least one representation has not been created it will return <code>false</code>.
     *
     * @param inputId
     *         The identifier of the input which has triggered this import
     * @return <code>true</code> whether all representations has been created, <code>false</code> otherwise
     */
    private ImportContentStatus createRepresentations(UUID inputId, IProjectBinaryContent projectContent, IEditingContextEventProcessor editingContextEventProcessor, Map<String, String> documentIdMapping, Map<String, String> semanticIdMapping) {

        List<String> errorMsgs = new ArrayList<>();
        logger.info("Starting to create representation");
        for (RepresentationImportData representationImportData : getRepresentationImportData(projectContent)) {
            Map<?, ?> representationManifest = this.getRepresentationManifest(representationImportData, projectContent);

            String targetObjectURI = (String) representationManifest.get("targetObjectURI");

            String objectId = this.getNewObjectId(targetObjectURI, projectContent, documentIdMapping, semanticIdMapping);

            String descriptionURI = (String) representationManifest.get("descriptionURI");

            boolean representationCreated = false;

            CreateRepresentationInput createRepresentationInput = new CreateRepresentationInput(inputId, editingContextEventProcessor.getEditingContextId(), descriptionURI, objectId, representationImportData.label());
            logger.info("Representation in creation " + representationImportData.label());
            var representationPayloadCreated = editingContextEventProcessor.handle(createRepresentationInput)
                    .filter(CreateRepresentationSuccessPayload.class::isInstance)
                    .map(CreateRepresentationSuccessPayload.class::cast)
                    .blockOptional();

            representationCreated = representationPayloadCreated
                    .map(CreateRepresentationSuccessPayload::representation)
                    .isPresent();

            if (representationPayloadCreated.isPresent()) {
                logger.info("Representation created " + representationImportData.label());
                var newRepresentationId = representationPayloadCreated.get().representation().id();
                var editingContextId = editingContextEventProcessor.getEditingContextId();
                logger.info("Updating representation with content" + representationImportData.label());
                this.diagramImporterUpdateServices.stream()
                        .filter(diagramImporterUpdateService -> diagramImporterUpdateService.canHandle(editingContextId, representationImportData))
                        .forEach(diagramImporterUpdateService -> diagramImporterUpdateService.handle(semanticIdMapping, createRepresentationInput, editingContextId, newRepresentationId, representationImportData));
            }

            if (!representationCreated) {
                errorMsgs.add("The representation " + representationImportData.label() + " has not been created.");
            }
        }
        logger.info("End of reprensetations creation");

        return new ImportContentStatus(errorMsgs.isEmpty(), errorMsgs);
    }

}
