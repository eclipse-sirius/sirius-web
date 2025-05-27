/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentInput;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentSuccessPayload;
import org.eclipse.sirius.web.application.project.services.api.IRepresentationImporterUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to import a project.
 *
 * @author jmallet
 */
public class ProjectImporter {

    private final Logger logger = LoggerFactory.getLogger(ProjectImporter.class);

    private final IEditingContextEventProcessor editingContextEventProcessor;

    private final Map<String, UploadFile> documents;

    private final List<RepresentationImportData> representations;

    private final Map<String, Object> projectManifest;

    private final Map<String, UUID> oldDocumentIdToNewDocumentId = new HashMap<>();

    private final Map<String, String> semanticElementsIdMappings = new HashMap<>();

    private final List<IRepresentationImporterUpdateService> diagramImporterUpdateServices;

    public ProjectImporter(IEditingContextEventProcessor editingContextEventProcessor, Map<String, UploadFile> documents, List<RepresentationImportData> representations,
                           Map<String, Object> projectManifest, List<IRepresentationImporterUpdateService> diagramImporterUpdateServices) {
        this.editingContextEventProcessor = Objects.requireNonNull(editingContextEventProcessor);
        this.documents = Objects.requireNonNull(documents);
        this.representations = Objects.requireNonNull(representations);
        this.projectManifest = Objects.requireNonNull(projectManifest);
        this.diagramImporterUpdateServices = Objects.requireNonNull(diagramImporterUpdateServices);
    }

    public boolean importProject(UUID inputId) {
        boolean errorOccurred = !this.createDocuments(inputId);

        if (!errorOccurred) {
            this.createRepresentations(inputId);
        }

        return !errorOccurred;
    }

    /**
     * Creates all representations in the project thanks to the {@link IEditingContextEventProcessor} and the create
     * representation input. If at least one representation has not been created it will return <code>false</code>.
     *
     * @param inputId
     *            The identifier of the input which has triggered this import
     * @return <code>true</code> whether all representations has been created, <code>false</code> otherwise
     */
    private boolean createRepresentations(UUID inputId) {
        boolean allRepresentationCreated = true;

        for (RepresentationImportData representationImportData : this.representations) {
            Map<?, ?> representationManifest = this.getRepresentationManifest(representationImportData);

            String targetObjectURI = (String) representationManifest.get("targetObjectURI");
            String objectId = this.getNewObjectId(targetObjectURI);

            String descriptionURI = (String) representationManifest.get("descriptionURI");

            boolean representationCreated = false;

            CreateRepresentationInput createRepresentationInput = new CreateRepresentationInput(inputId, this.editingContextEventProcessor.getEditingContextId(), descriptionURI, objectId, representationImportData.label());

            var representationPayloadCreated = this.editingContextEventProcessor.handle(createRepresentationInput)
                    .filter(CreateRepresentationSuccessPayload.class::isInstance)
                    .map(CreateRepresentationSuccessPayload.class::cast)
                    .blockOptional();

            representationCreated = representationPayloadCreated
                    .map(CreateRepresentationSuccessPayload::representation)
                    .isPresent();

            if (representationPayloadCreated.isPresent()) {
                var newRepresentationId = representationPayloadCreated.get().representation().id();
                var editingContextId = this.editingContextEventProcessor.getEditingContextId();
                this.diagramImporterUpdateServices.stream()
                        .filter(diagramImporterUpdateService -> diagramImporterUpdateService.canHandle(editingContextId, representationImportData))
                        .forEach(diagramImporterUpdateService -> diagramImporterUpdateService.handle(this.semanticElementsIdMappings, createRepresentationInput, editingContextId, newRepresentationId, representationImportData));
            }

            if (!representationCreated) {
                this.logger.warn("The representation {} has not been created", representationImportData.label());
            }

            allRepresentationCreated = allRepresentationCreated && representationCreated;
        }

        return allRepresentationCreated;
    }

    /**
     * Adapt the targetObjectURI/object id stored in the archive to point to the equivalent object after import. The new
     * object will be in a document with a different id, and will itself have been given a new, unique id during the
     * upload.
     *
     * @param targetObjectURI
     *            the target object URI/id stored in the manifest.
     * @return the URI/id of the equivalent model element in the newly imported documents.
     */
    private String getNewObjectId(String targetObjectURI) {
        String objectId;

        String oldDocumentId = URI.create(targetObjectURI).getPath().substring(1);
        UUID newDocumentId = this.oldDocumentIdToNewDocumentId.get(oldDocumentId);
        if (newDocumentId != null) {
            objectId = targetObjectURI.replace(oldDocumentId, newDocumentId.toString());
        } else {
            objectId = targetObjectURI;
        }

        String oldSemantidElementId = URI.create(targetObjectURI).getFragment();
        String newSemanticElementId = this.semanticElementsIdMappings.get(oldSemantidElementId);
        if (newSemanticElementId != null) {
            objectId = objectId.replace(oldSemantidElementId, newSemanticElementId);
        }
        return objectId;
    }

    /**
     * Get the representation (type, targetObjectUri, descriptionUri) described into the Manifest from a given
     * representation identifier.
     *
     * @param representationImportData
     *            the representation to look for in Manifest
     * @return the representation details from Manifest
     */
    private Map<?, ?> getRepresentationManifest(RepresentationImportData representationImportData) {
        Object representationsFromManifest = this.projectManifest.get("representations");
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
     * Creates all documents in the project thanks to the {@link IEditingContextEventProcessor}. If at least one
     * document has not been created it will return <code>false</code>.
     *
     * @param inputId
     * @return <code>true</code> whether all documents has been created, <code>false</code> otherwise
     */
    private boolean createDocuments(UUID inputId) {
        for (Entry<String, UploadFile> entry : this.documents.entrySet()) {
            String oldDocumentId = entry.getKey();
            UploadFile uploadFile = entry.getValue();
            UploadDocumentInput input = new UploadDocumentInput(inputId, this.editingContextEventProcessor.getEditingContextId(), uploadFile);

            var optionalSuccess = this.editingContextEventProcessor.handle(input)
                    .filter(UploadDocumentSuccessPayload.class::isInstance)
                    .map(UploadDocumentSuccessPayload.class::cast)
                    .blockOptional();
            if (optionalSuccess.isPresent()) {
                UUID newDocumentId = optionalSuccess.get().document().id();
                Map<String, String> idMapping = optionalSuccess.get().idMapping();

                if (newDocumentId == null) {
                    String documentIdNotCreated = null;
                    Object documentIdsToName = this.projectManifest.get("documentIdsToName");
                    if (documentIdsToName instanceof Map) {
                        documentIdNotCreated = (String) ((Map<?, ?>) documentIdsToName).get(oldDocumentId);
                    }
                    this.logger.warn("The document {} has not been created", documentIdNotCreated);
                }
                this.oldDocumentIdToNewDocumentId.put(oldDocumentId, newDocumentId);
                this.semanticElementsIdMappings.putAll(idMapping);
            }
        }

        Map<String, String> documentIds = new HashMap<>();
        for (Map.Entry<String, UUID> entry : this.oldDocumentIdToNewDocumentId.entrySet()) {
            documentIds.put(entry.getKey(), entry.getValue().toString());
        }
        RewriteProxiesInput rewriteInput = new RewriteProxiesInput(UUID.randomUUID(), this.editingContextEventProcessor.getEditingContextId(), documentIds);
        this.editingContextEventProcessor.handle(rewriteInput).blockOptional();

        return this.oldDocumentIdToNewDocumentId.values().stream().allMatch(Objects::nonNull);
    }

}
