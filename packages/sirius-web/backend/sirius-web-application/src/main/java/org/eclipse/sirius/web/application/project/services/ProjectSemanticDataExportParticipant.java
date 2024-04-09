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

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.emfjson.resource.JsonResourceFactoryImpl;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.document.services.api.IDocumentExporter;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextPersistenceFilter;
import org.eclipse.sirius.web.application.project.services.api.IProjectExportParticipant;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * Used to add semantic data to the export of a project.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectSemanticDataExportParticipant implements IProjectExportParticipant {

    private final IEditingContextSearchService editingContextSearchService;

    private final List<IDocumentExporter> documentExporters;

    private final List<IEditingContextPersistenceFilter> persistenceFilters;

    private final Logger logger = LoggerFactory.getLogger(ProjectSemanticDataExportParticipant.class);

    public ProjectSemanticDataExportParticipant(IEditingContextSearchService editingContextSearchService, List<IDocumentExporter> documentExporters, List<IEditingContextPersistenceFilter> persistenceFilters) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.documentExporters = Objects.requireNonNull(documentExporters);
        this.persistenceFilters = Objects.requireNonNull(persistenceFilters);
    }

    @Override
    public Map<String, Object> exportData(Project project, ZipOutputStream outputStream) {
        Map<String, Object> manifestEntries = new HashMap<>();

        var optionalEditingContext = this.editingContextSearchService.findById(project.getId().toString())
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast);
        if (optionalEditingContext.isPresent()) {
            var editingContext = optionalEditingContext.get();

            List<String> metamodels = this.getMetamodels(editingContext);
            Map<String, String> id2DocumentName = this.exportSemanticData(editingContext, project.getName(), outputStream);
            List<String> natures = project.getNatures().stream()
                        .map(Nature::name)
                        .toList();

            manifestEntries.put("metamodels", metamodels);
            manifestEntries.put("documentIdsToName", id2DocumentName);
            manifestEntries.put("natures", natures);
        }

        return manifestEntries;
    }

    private List<String> getMetamodels(IEMFEditingContext editingContext) {
        return editingContext.getDomain().getResourceSet().getPackageRegistry().values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .map(EPackage::getNsURI)
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    private Map<String, String> exportSemanticData(IEMFEditingContext editingContext, String projectName, ZipOutputStream outputStream) {
        Map<String, String> id2DocumentName = new HashMap<>();

        List<Resource> resources = editingContext.getDomain().getResourceSet().getResources().stream()
                .filter(resource -> this.persistenceFilters.stream().allMatch(filter -> filter.shouldPersist(resource))).toList();
        for (var resource: resources) {
            var resourceId = resource.getURI().path().substring(1);
            var optionalDocumentId = new UUIDParser().parse(resourceId);

            var optionalDocumentName = resource.eAdapters().stream()
                    .filter(ResourceMetadataAdapter.class::isInstance)
                    .map(ResourceMetadataAdapter.class::cast)
                    .map(ResourceMetadataAdapter::getName)
                    .findFirst();

            var optionalContent = this.documentExporters.stream()
                    .filter(documentExporter -> documentExporter.canHandle(resource, MediaType.APPLICATION_JSON_VALUE))
                    .findFirst()
                    .flatMap(documentExporter -> documentExporter.getBytes(resource, MediaType.APPLICATION_JSON_VALUE));
            if (optionalDocumentId.isPresent() && optionalDocumentName.isPresent() && optionalContent.isPresent()) {
                var documentId = optionalDocumentId.get();
                var documentName = optionalDocumentName.get();
                var content = optionalContent.get();

                id2DocumentName.put(documentId.toString(), documentName);
                String name = projectName + "/documents/" + documentId + "." + JsonResourceFactoryImpl.EXTENSION;

                ZipEntry zipEntry = new ZipEntry(name);
                zipEntry.setSize(content.length);
                zipEntry.setTime(System.currentTimeMillis());

                try {
                    outputStream.putNextEntry(zipEntry);
                    outputStream.write(content);
                    outputStream.closeEntry();
                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }
            }
        }

        return id2DocumentName;
    }
}
