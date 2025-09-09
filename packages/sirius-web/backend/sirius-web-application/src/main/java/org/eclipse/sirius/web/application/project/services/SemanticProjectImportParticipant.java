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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;
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
import org.eclipse.sirius.web.application.project.dto.CopySemanticDataCause;
import org.eclipse.sirius.web.application.project.dto.IProjectBinaryContent;
import org.eclipse.sirius.web.application.project.dto.ImportContentStatus;
import org.eclipse.sirius.web.application.project.dto.InitProjectContentInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectContentImportParticipant;
import org.eclipse.sirius.web.application.project.services.api.IRewriteProxiesService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.events.IDomainEvent;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * {@link IProjectContentImportParticipant} that import semantic data in a project.
 *
 * @author Arthur Daussy
 */
@Service
public class SemanticProjectImportParticipant implements IProjectContentImportParticipant {

    private static final String DOCUMENTS_FOLDER = "documents";

    private static final String ZIP_FOLDER_SEPARATOR = "/";

    private final IEditingContextSearchService editingContextSearchService;

    private final IUploadFileLoader uploadDocumentLoader;

    private final List<IUploadDocumentReportProvider> uploadDocumentReportProviders;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final IRewriteProxiesService rewriteProxiesService;

    public SemanticProjectImportParticipant(
            IEditingContextSearchService editingContextSearchService, IUploadFileLoader uploadDocumentLoader, List<IUploadDocumentReportProvider> uploadDocumentReportProviders,
            IEditingContextPersistenceService editingContextPersistenceService, IRewriteProxiesService rewriteProxiesService) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.uploadDocumentLoader = Objects.requireNonNull(uploadDocumentLoader);
        this.uploadDocumentReportProviders = Objects.requireNonNull(uploadDocumentReportProviders);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.rewriteProxiesService = Objects.requireNonNull(rewriteProxiesService);
    }

    @Override
    public boolean canHandle(IDomainEvent event) {
        return event instanceof ProjectSemanticDataCreatedEvent projectSemanticDataCreatedEvent
                && projectSemanticDataCreatedEvent.causedBy() instanceof SemanticDataCreatedEvent semanticDataCreatedEvent
                && semanticDataCreatedEvent.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent
                && projectCreatedEvent.causedBy() instanceof InitProjectContentInput;

    }

    @Override
    public ImportContentStatus handle(IDomainEvent event, IProjectBinaryContent projectContent) {
        ProjectSemanticDataCreatedEvent projectSemCreationEvent = (ProjectSemanticDataCreatedEvent) event;
        SemanticDataCreatedEvent semCreationEvent = (SemanticDataCreatedEvent) projectSemCreationEvent.causedBy();
        InitProjectContentInput originalInput = (InitProjectContentInput) event.causedBy().causedBy().causedBy();

        var documentsToUpload = getDocuments(projectContent);
        if (!documentsToUpload.isEmpty()) {
            return copySemantic(documentsToUpload, semCreationEvent.semanticData().getId().toString(), originalInput);
        }
        return new ImportContentStatus(true, List.of());
    }

    /**
     * Gets the document to upload from the {@link IProjectBinaryContent}.
     *
     * @param projectUploadContent
     *         the project content
     * @return a map linking document id with their matching {@link UploadFile}
     */
    private List<Pair<String, UploadFile>> getDocuments(IProjectBinaryContent projectUploadContent) {
        String documentsFolderInZip = projectUploadContent.getName() + ZIP_FOLDER_SEPARATOR + DOCUMENTS_FOLDER + ZIP_FOLDER_SEPARATOR;
        Map<String, ByteArrayOutputStream> documentIdToDocumentContent = this.selectAndTransformIntoDocumentIdToDocumentContent(projectUploadContent.getFileContent(), documentsFolderInZip);
        List<Pair<String, UploadFile>> documents = new ArrayList<>();
        for (Map.Entry<String, ByteArrayOutputStream> entry : documentIdToDocumentContent.entrySet()) {
            String documentId = entry.getKey();
            ByteArrayOutputStream outputStream = entry.getValue();
            String documentName = null;
            Object documentIdsToName = projectUploadContent.getManifest().get("documentIdsToName");
            if (documentIdsToName instanceof Map) {
                documentName = (String) ((Map<?, ?>) documentIdsToName).get(documentId);
            }
            documents.add(Pair.of(documentId, new UploadFile(documentName, new ByteArrayInputStream(outputStream.toByteArray()))));
        }
        return documents;
    }

    /**
     * Selects and transforms the given map into a map of document id to document content.
     *
     * <p>
     * Select entries in the given map where the key represented by the zip entry name are in the document folder (start with documentsFolderInZip). For each remaining entries extract the document id
     * by removing documentsFolderInZip from the zip entry name.
     * </p>
     *
     * @param zipEntryNameToContent
     *         The map of all zip entries to their content
     * @param documentsFolderInZip
     *         The path of documents folder in zip
     * @return The map of document id to document content
     */
    private Map<String, ByteArrayOutputStream> selectAndTransformIntoDocumentIdToDocumentContent(Map<String, ByteArrayOutputStream> zipEntryNameToContent, String documentsFolderInZip) {
        Function<Map.Entry<String, ByteArrayOutputStream>, String> mapZipEntryNameToDocumentId = e -> {
            String fullPath = e.getKey();
            String fileName = fullPath.substring(documentsFolderInZip.length());
            int extensionIndex = fileName.lastIndexOf('.');
            if (extensionIndex >= 0) {
                return fileName.substring(0, extensionIndex);
            } else {
                return fileName;
            }
        };

        return zipEntryNameToContent.entrySet().stream().filter(entry -> entry.getKey().startsWith(documentsFolderInZip))
                .collect(Collectors.toMap(mapZipEntryNameToDocumentId::apply, Map.Entry::getValue));
    }

    private Optional<IEMFEditingContext> getEditingContext(String editingContextId) {
        return this.editingContextSearchService.findById(editingContextId)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast);
    }

    private String getReport(Resource resource) {
        return this.uploadDocumentReportProviders.stream()
                .filter(provider -> provider.canHandle(resource))
                .map(provider -> provider.createReport(resource))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private ImportContentStatus copySemantic(List<Pair<String, UploadFile>> uploadFiles, String editingContextId, ICause event) {

        Optional<IEMFEditingContext> optEditingContext = this.getEditingContext(editingContextId);
        List<String> errorsMessages = new ArrayList<>();

        if (optEditingContext.isPresent()) {
            IEMFEditingContext editingContext = optEditingContext.get();

            Map<String, String> semanticIds = new HashMap<>();
            Map<String, String> documentIds = new HashMap<>();
            for (Pair<String, UploadFile> fileWithId : uploadFiles) {
                var uploadFile = fileWithId.getSecond();
                IResult<UploadedResource> result = this.uploadDocumentLoader.load(editingContext.getDomain().getResourceSet(), editingContext,
                        new UploadFile(uploadFile.getName(), uploadFile.getInputStream()), false);

                if (result instanceof Success<UploadedResource> success) {
                    var newResource = success.data().resource();
                    var optionalId = new UUIDParser().parse(newResource.getURI().path().substring(1));
                    var optionalName = newResource.eAdapters().stream().filter(ResourceMetadataAdapter.class::isInstance).map(ResourceMetadataAdapter.class::cast).findFirst()
                            .map(ResourceMetadataAdapter::getName);

                    if (optionalId.isPresent() && optionalName.isPresent()) {
                        var id = optionalId.get();
                        var name = optionalName.get();
                        semanticIds.putAll(success.data().idMapping());
                        documentIds.put(fileWithId.getFirst(), id.toString());
                    }

                } else if (result instanceof Failure<UploadedResource> failure) {
                    String errorMessage = failure.message();
                    if (errorMessage == null) {
                        errorMessage = "Fail to import " + uploadFile.getName();
                    }
                    errorsMessages.add(errorMessage);
                }
            }

            rewriteProxiesService.rewriteProxies(editingContext, documentIds);
            editingContextPersistenceService.persist(new CopySemanticDataCause(UUID.randomUUID(), event, semanticIds, documentIds), editingContext);
        } else {
            errorsMessages.add("Unable to find editing context with id : " + editingContextId);
        }

        return new ImportContentStatus(errorsMessages.isEmpty(), errorsMessages);
    }
}
