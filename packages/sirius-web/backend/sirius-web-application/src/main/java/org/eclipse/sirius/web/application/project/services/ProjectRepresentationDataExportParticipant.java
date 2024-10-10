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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.emfjson.resource.JsonResourceFactoryImpl;
import org.eclipse.sirius.web.application.project.services.api.IProjectExportParticipant;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationContentMigrationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to add representation data to the export of a project.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectRepresentationDataExportParticipant implements IProjectExportParticipant {

    private final IEditingContextSearchService editingContextSearchService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationContentSearchService representationContentSearchService;

    private final ObjectMapper objectMapper;

    private final IRepresentationContentMigrationService representationContentMigrationService;

    private final Logger logger = LoggerFactory.getLogger(ProjectRepresentationDataExportParticipant.class);

    public ProjectRepresentationDataExportParticipant(IEditingContextSearchService editingContextSearchService, IRepresentationMetadataSearchService representationMetadataSearchService,
            IRepresentationContentSearchService representationContentSearchService, ObjectMapper objectMapper, IRepresentationContentMigrationService representationContentMigrationService) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationContentSearchService = Objects.requireNonNull(representationContentSearchService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.representationContentMigrationService = Objects.requireNonNull(representationContentMigrationService);
    }

    @Override
    public Map<String, Object> exportData(Project project, ZipOutputStream outputStream) {
        Map<String, Map<String, String>> representationManifests = new HashMap<>();

        var allRepresentationMetadata = this.representationMetadataSearchService.findAllMetadataByProject(AggregateReference.to(project.getId()));
        for (var representationMetadata: allRepresentationMetadata) {
            var optionalRepresentationContentNode = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(representationMetadata.getId()))
                    .flatMap(representationContent -> this.representationContentMigrationService.getMigratedContent(representationMetadata, representationContent));

            if (optionalRepresentationContentNode.isPresent()) {
                var representationContentNode = optionalRepresentationContentNode.get();

                var exportData = new RepresentationSerializedExportData(
                        representationMetadata.getId(),
                        representationMetadata.getProject().getId(),
                        representationMetadata.getDescriptionId(),
                        representationMetadata.getTargetObjectId(),
                        representationMetadata.getLabel(),
                        representationMetadata.getKind(),
                        representationContentNode
                );

                // Get TargetObjectURI
                String uriFragment = "";
                var optionalEditingContext = this.editingContextSearchService.findById(project.getId().toString())
                        .filter(IEMFEditingContext.class::isInstance)
                        .map(IEMFEditingContext.class::cast);
                if (optionalEditingContext.isPresent()) {
                    var editingContext = optionalEditingContext.get();
                    String targetObjectId = representationMetadata.getTargetObjectId();
                    for (Resource resource : editingContext.getDomain().getResourceSet().getResources()) {
                        EObject eObject = resource.getEObject(targetObjectId);
                        if (eObject != null) {
                            uriFragment = EcoreUtil.getURI(eObject).toString();
                            break;
                        }
                    }
                }
                if (uriFragment.isEmpty()) {
                    this.logger.warn("The serialization of the representationManifest won't be complete.");
                }

                Map<String, String> representationManifest = Map.of(
                        "type", representationMetadata.getKind(),
                        "descriptionURI", representationMetadata.getDescriptionId(),
                        "targetObjectURI", uriFragment
                );
                representationManifests.put(representationMetadata.getId().toString(), representationManifest);

                try {
                    byte[] bytes = this.objectMapper.writeValueAsBytes(exportData);

                    String name = project.getName() + "/representations/" + representationMetadata.getId() + "." + JsonResourceFactoryImpl.EXTENSION;

                    ZipEntry zipEntry = new ZipEntry(name);
                    zipEntry.setSize(bytes.length);
                    zipEntry.setTime(System.currentTimeMillis());

                    outputStream.putNextEntry(zipEntry);
                    outputStream.write(bytes);
                    outputStream.closeEntry();
                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage());
                }
            }
        }

        return Map.of("representations", representationManifests);
    }

}
