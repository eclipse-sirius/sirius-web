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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.document.services.api.IUploadDocumentReportProvider;
import org.eclipse.sirius.web.application.document.services.api.IUploadFileLoader;
import org.eclipse.sirius.web.application.document.services.api.UploadedResource;
import org.eclipse.sirius.web.application.project.services.api.IProjectContentImportParticipant;
import org.eclipse.sirius.web.application.project.services.api.IRewriteProxiesService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.eclipse.sirius.web.domain.events.IDomainEvent;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * {@link IProjectContentImportParticipant} that import semantic data in a project.
 *
 * @author Arthur Daussy
 */
@Service
public class SemanticDataProjectContentImportParticipant implements IProjectContentImportParticipant {

    private static final String DOCUMENTS_FOLDER = "documents";

    private static final String ZIP_FOLDER_SEPARATOR = "/";

    private final IEditingContextSearchService editingContextSearchService;

    private final ISemanticDataUpdateService semanticDataUpdateService;

    private final IUploadFileLoader uploadDocumentLoader;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final IRewriteProxiesService rewriteProxiesService;

    private final Logger logger = LoggerFactory.getLogger(SemanticDataProjectContentImportParticipant.class);

    public SemanticDataProjectContentImportParticipant(
            IEditingContextSearchService editingContextSearchService, IUploadFileLoader uploadDocumentLoader, List<IUploadDocumentReportProvider> uploadDocumentReportProviders, ISemanticDataUpdateService semanticDataUpdateService,
            IEditingContextPersistenceService editingContextPersistenceService, IRewriteProxiesService rewriteProxiesService) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.uploadDocumentLoader = Objects.requireNonNull(uploadDocumentLoader);
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.rewriteProxiesService = Objects.requireNonNull(rewriteProxiesService);
    }

    @Override
    public boolean canHandle(IDomainEvent event) {
        return event instanceof ProjectSemanticDataCreatedEvent projectSemanticDataCreatedEvent
                && projectSemanticDataCreatedEvent.causedBy() instanceof SemanticDataCreatedEvent semanticDataCreatedEvent
                && semanticDataCreatedEvent.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent
                && projectCreatedEvent.causedBy() instanceof InitializeProjectInput;
    }

    @Override
    public void handle(IDomainEvent event, ProjectZipContent projectContent) {
        if (event instanceof ProjectSemanticDataCreatedEvent projectSemanticDataCreatedEvent
                && projectSemanticDataCreatedEvent.causedBy() instanceof SemanticDataCreatedEvent semanticDataCreatedEvent
                && semanticDataCreatedEvent.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent
                && projectCreatedEvent.causedBy() instanceof InitializeProjectInput initializeProjectInput) {
            var dependenciesEntry = initializeProjectInput.projectContent().manifest().get("dependencies");
            if (dependenciesEntry instanceof List<?> list) {
                var dependencies = list.stream()
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .map(new UUIDParser()::parse)
                        .flatMap(Optional::stream)
                        .map(AggregateReference::<SemanticData, UUID> to)
                        .toList();

                this.semanticDataUpdateService.addDependencies(event, AggregateReference.to(semanticDataCreatedEvent.semanticData().getId()), dependencies);
            }

            var documentsToUpload = this.getDocuments(projectContent);
            if (!documentsToUpload.isEmpty()) {
                var editingContextId = semanticDataCreatedEvent.semanticData().getId().toString();
                this.copyDocuments(initializeProjectInput, editingContextId, documentsToUpload);
            }
        }
    }

    /**
     * Gets the document to upload from the {@link ProjectZipContent}.
     *
     * @param projectZipContent
     *         the project content
     * @return a map linking document id with their matching {@link UploadFile}
     */
    private Map<String, UploadFile> getDocuments(ProjectZipContent projectZipContent) {
        Map<String, UploadFile> documents = new HashMap<>();

        String documentsFolderInZip = projectZipContent.projectName() + ZIP_FOLDER_SEPARATOR + DOCUMENTS_FOLDER + ZIP_FOLDER_SEPARATOR;

        Map<String, ByteArrayOutputStream> documentIdToDocumentContent = projectZipContent.files().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(documentsFolderInZip))
                .collect(Collectors.toMap(
                        entry -> this.toDocumentId(entry, documentsFolderInZip),
                        Map.Entry::getValue)
                );
        for (Map.Entry<String, ByteArrayOutputStream> entry : documentIdToDocumentContent.entrySet()) {
            String documentId = entry.getKey();
            ByteArrayOutputStream outputStream = entry.getValue();

            String documentName = null;
            Object documentIdsToName = projectZipContent.manifest().get("documentIdsToName");
            if (documentIdsToName instanceof Map map) {
                var value = map.get(documentId);
                if (value instanceof String stringValue) {
                    documentName = stringValue;
                }
            }
            documents.put(documentId, new UploadFile(documentName, new ByteArrayInputStream(outputStream.toByteArray())));
        }
        return documents;
    }

    private String toDocumentId(Map.Entry<String, ByteArrayOutputStream> entry, String documentsFolderInZip) {
        String fullPath = entry.getKey();
        String fileName = fullPath.substring(documentsFolderInZip.length());
        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex >= 0) {
            return fileName.substring(0, extensionIndex);
        }
        return fileName;
    }

    private void copyDocuments(ICause cause, String editingContextId, Map<String, UploadFile> uploadFiles) {
        var optionalIEMFEditingContext = this.editingContextSearchService.findById(editingContextId)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast);
        if (optionalIEMFEditingContext.isPresent()) {
            IEMFEditingContext editingContext = optionalIEMFEditingContext.get();

            Map<String, String> semanticIds = new HashMap<>();
            Map<String, String> documentIds = new HashMap<>();
            for (Map.Entry<String, UploadFile> entry : uploadFiles.entrySet()) {
                var uploadFile = entry.getValue();
                IResult<UploadedResource> result = this.uploadDocumentLoader.load(editingContext.getDomain().getResourceSet(), editingContext,
                        new UploadFile(uploadFile.getName(), uploadFile.getInputStream()), false);

                if (result instanceof Success<UploadedResource> success) {
                    var newResource = success.data().resource();
                    var optionalId = new UUIDParser().parse(newResource.getURI().path().substring(1));
                    var optionalName = newResource.eAdapters().stream()
                            .filter(ResourceMetadataAdapter.class::isInstance)
                            .map(ResourceMetadataAdapter.class::cast)
                            .findFirst()
                            .map(ResourceMetadataAdapter::getName);

                    if (optionalId.isPresent() && optionalName.isPresent()) {
                        var id = optionalId.get();
                        semanticIds.putAll(success.data().idMapping());
                        documentIds.put(entry.getKey(), id.toString());
                    }

                } else if (result instanceof Failure<UploadedResource> failure) {
                    this.logger.warn(failure.message());
                }
            }

            this.rewriteProxiesService.rewriteProxies(editingContext, documentIds);
            this.editingContextPersistenceService.persist(new CopySemanticDataCause(UUID.randomUUID(), cause, semanticIds, documentIds), editingContext);
        }
    }
}
