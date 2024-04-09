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
package org.eclipse.sirius.web.application.project.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service used to import a project.
 *
 * @author gcoutable
 */
@Service
public class ProjectImportService implements IProjectImportService {

    private static final String ZIP_FOLDER_SEPARATOR = "/";

    private static final String MANIFEST_JSON_FILE = "manifest.json";

    private static final String REPRESENTATIONS_FOLDER = "representations";

    private static final String DOCUMENTS_FOLDER = "documents";

    private final Logger logger = LoggerFactory.getLogger(ProjectImportService.class);

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final ObjectMapper objectMapper;

    private final IProjectApplicationService projectApplicationService;

    public ProjectImportService(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, ObjectMapper objectMapper, IProjectApplicationService projectApplicationService) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
    }

    /**
     * Returns {@link UploadProjectSuccessPayload} if the project import has been successful, {@link ErrorPayload}
     * otherwise.
     *
     * <p>
     * Unzip the given {@link UploadFile}, then creates a project with the name of the root directory in the zip file,
     * then use {@link ProjectImporter} to create documents and representations. If the project has not been imported,
     * it disposes the {@link IEditingContextEventProcessor} used to create documents and representations then delete
     * the created project in order to keep the server in the same state before the project upload attempt.
     * </p>
     *
     * @param inputId
     *            The identifier of the input which has triggered the upload
     * @param file
     *            the file to upload
     * @return {@link UploadProjectSuccessPayload} whether the project import has been successful, {@link ErrorPayload}
     *         otherwise
     */
    @Override
    public IPayload importProject(UUID inputId, UploadFile file) {
        Map<String, ByteArrayOutputStream> zipEntryNameToContent = this.readZipFile(file.getInputStream());

        // Manifest import
        String projectName = null;
        Map<String, Object> projectManifest = new HashMap<>();
        Optional<String> optionalProjectName = this.handleProjectName(zipEntryNameToContent);
        if (optionalProjectName.isPresent()) {
            projectName = optionalProjectName.get();
            String manifestPathInZip = projectName + ZIP_FOLDER_SEPARATOR + MANIFEST_JSON_FILE;

            byte[] manifestBytes = zipEntryNameToContent.entrySet().stream()
                    .filter(entry -> entry.getKey().equals(manifestPathInZip))
                    .map(Entry::getValue)
                    .map(ByteArrayOutputStream::toByteArray)
                    .findFirst()
                    .orElse(new byte[0]);

            try {
                projectManifest = this.objectMapper.readValue(manifestBytes, HashMap.class);
            } catch (IOException exception) {
                this.logger.warn(exception.getMessage(), exception);
            }
        }

        // Semantic data Import
        String documentsFolderInZip = projectName + ZIP_FOLDER_SEPARATOR + DOCUMENTS_FOLDER + ZIP_FOLDER_SEPARATOR;
        Map<String, ByteArrayOutputStream> documentIdToDocumentContent = this.selectAndTransformIntoDocumentIdToDocumentContent(zipEntryNameToContent, documentsFolderInZip);
        Map<String, UploadFile> documents = new HashMap<>();
        for (Entry<String, ByteArrayOutputStream> entry : documentIdToDocumentContent.entrySet()) {
            String documentId = entry.getKey();
            ByteArrayOutputStream outputStream = entry.getValue();
            String documentName = null;
            Object documentIdsToName = projectManifest.get("documentIdsToName");
            if (documentIdsToName instanceof Map) {
                documentName = (String) ((Map<?, ?>) documentIdsToName).get(documentId);
            }
            UploadFile uploadFile = new UploadFile(documentName, new ByteArrayInputStream(outputStream.toByteArray()));
            documents.put(documentId, uploadFile);
        }

        // representation import
        String representationsFolderInZip = projectName + ZIP_FOLDER_SEPARATOR + REPRESENTATIONS_FOLDER + ZIP_FOLDER_SEPARATOR;
        List<ByteArrayOutputStream> representationDescritorsContent = this.selectAndTransformIntoRepresentationDescriptorsContent(zipEntryNameToContent, representationsFolderInZip);
        List<RepresentationImportData> representationImportDatas = null;
        try {
            representationImportDatas = this.getRepresentationImportDatas(representationDescritorsContent);
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        var createProjectInput = new CreateProjectInput(inputId, projectName, (List<String>) projectManifest.get("natures"));
        IPayload createProjectPayload = this.projectApplicationService.createProject(createProjectInput);
        IPayload payload = new ErrorPayload(inputId, "");
        if (createProjectPayload instanceof CreateProjectSuccessPayload createProjectSuccessPayload) {
            var project = createProjectSuccessPayload.project();
            Optional<IEditingContextEventProcessor> optionalEditingContextEventProcessor = this.editingContextEventProcessorRegistry
                    .getOrCreateEditingContextEventProcessor(project.id().toString());
            if (optionalEditingContextEventProcessor.isPresent()) {
                IEditingContextEventProcessor editingContextEventProcessor = optionalEditingContextEventProcessor.get();

                ProjectImporter projectImporter = new ProjectImporter(project.id().toString(), editingContextEventProcessor, documents, representationImportDatas, projectManifest);
                boolean hasBeenImported = projectImporter.importProject(inputId);

                if (!hasBeenImported) {
                    this.editingContextEventProcessorRegistry.disposeEditingContextEventProcessor(project.id().toString());
                    this.projectApplicationService.deleteProject(new DeleteProjectInput(inputId, project.id()));
                } else {
                    payload = new UploadProjectSuccessPayload(inputId, project);
                }
            }
        }
        return payload;
    }

    /**
     * Returns the project name if all zip entries represented by couple (zipEntry name -> OutputStream) in the given
     * {@link Map}, have their zip entry name starting by the project name, which should be the first segment of the
     * path of each zip entries.
     *
     * @param zipEntryToOutputStreams
     *            The map of zip entry name to the zip entry content
     * @return The name of the project
     */
    private Optional<String> handleProjectName(Map<String, ByteArrayOutputStream> zipEntryToOutputStreams) {
        Iterator<String> iterator = zipEntryToOutputStreams.keySet().iterator();
        if (!iterator.hasNext()) {
            // zip was empty
            return Optional.empty();
        }

        Optional<String> optionalProjectName = Optional.empty();
        String possibleProjectName = iterator.next().split(ZIP_FOLDER_SEPARATOR)[0];
        if (!possibleProjectName.isBlank() && zipEntryToOutputStreams.keySet().stream().allMatch(key -> key.split(ZIP_FOLDER_SEPARATOR)[0].equals(possibleProjectName))) {
            optionalProjectName = Optional.of(possibleProjectName);
        }

        return optionalProjectName;
    }

    /**
     * Reads the zip file and returns the a map of zip entry name to zip entry content.
     *
     * @return The zip entry names mapped to its zip entry contents
     */
    private Map<String, ByteArrayOutputStream> readZipFile(InputStream inputStream) {
        Map<String, ByteArrayOutputStream> entryToHandle = new HashMap<>();
        try (var zipperProjectInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry = zipperProjectInputStream.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    ByteArrayOutputStream entryBaos = new ByteArrayOutputStream();
                    zipperProjectInputStream.transferTo(entryBaos);
                    entryToHandle.put(name, entryBaos);
                }
                zipEntry = zipperProjectInputStream.getNextEntry();
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return entryToHandle;
    }

    /**
     * Selects and transforms the given map of zip entry name to zip entry content into a map of document id to document
     * content.
     *
     * <p>
     * Select entries in the given map where the key represented by the zip entry name are in the document folder (start
     * with documentsFolderInZip). For each remaining entries extract the document id by removing documentsFolderInZip
     * from the zip entry name.
     * </p>
     *
     * @param zipEntryNameToContent
     *            The map of all zip entries to their content
     * @param documentsFolderInZip
     *            The path of documents folder in zip
     * @return The map of document id to document content
     */
    private Map<String, ByteArrayOutputStream> selectAndTransformIntoDocumentIdToDocumentContent(Map<String, ByteArrayOutputStream> zipEntryNameToContent, String documentsFolderInZip) {
        Function<Entry<String, ByteArrayOutputStream>, String> mapZipEntryNameToDocumentId = e -> {
            String fullPath = e.getKey();
            String fileName = fullPath.substring(documentsFolderInZip.length());
            int extensionIndex = fileName.lastIndexOf('.');
            if (extensionIndex >= 0) {
                return fileName.substring(0, extensionIndex);
            } else {
                return fileName;
            }
        };

        return zipEntryNameToContent.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(documentsFolderInZip))
                .collect(Collectors.toMap(mapZipEntryNameToDocumentId::apply, Entry::getValue));
    }
    private List<ByteArrayOutputStream> selectAndTransformIntoRepresentationDescriptorsContent(Map<String, ByteArrayOutputStream> zipEntryNameToContent, String representationsFolderInZip) {
        return zipEntryNameToContent.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(representationsFolderInZip))
                .map(Entry::getValue)
                .toList();
    }

    private List<RepresentationImportData> getRepresentationImportDatas(List<ByteArrayOutputStream> outputStreamToTransformToRepresentationDescriptor) throws IOException {
        List<RepresentationImportData> representations = new ArrayList<>();
        for (ByteArrayOutputStream outputStream : outputStreamToTransformToRepresentationDescriptor) {
            byte[] representationDescriptorBytes = outputStream.toByteArray();
            RepresentationSerializedImportData representationSerializedImportData = this.objectMapper.readValue(representationDescriptorBytes, RepresentationSerializedImportData.class);
            var representationDescriptor = new RepresentationImportData(representationSerializedImportData.id(), representationSerializedImportData.projectId(),
                    representationSerializedImportData.descriptionId(), representationSerializedImportData.targetObjectId(), representationSerializedImportData.label(),
                    representationSerializedImportData.kind(), representationSerializedImportData.representation().toString());
            representations.add(representationDescriptor);
        }
        return representations;
    }

}
