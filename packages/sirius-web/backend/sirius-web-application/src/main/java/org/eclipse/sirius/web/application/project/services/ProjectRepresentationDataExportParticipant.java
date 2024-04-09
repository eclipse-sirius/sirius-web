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
import org.eclipse.sirius.web.application.representation.services.RepresentationSearchService;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationDataMigrationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
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

    private final IRepresentationDataSearchService representationDataSearchService;

    private final ObjectMapper objectMapper;

    private final IRepresentationDataMigrationService representationDataMigrationService;

    private final RepresentationSearchService representationSearchService;

    private final Logger logger = LoggerFactory.getLogger(ProjectRepresentationDataExportParticipant.class);

    public ProjectRepresentationDataExportParticipant(IEditingContextSearchService editingContextSearchService, IRepresentationDataSearchService representationDataSearchService, ObjectMapper objectMapper, IRepresentationDataMigrationService representationDataMigrationService, RepresentationSearchService representationSearchService) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.representationDataMigrationService = Objects.requireNonNull(representationDataMigrationService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
    }

    @Override
    public Map<String, Object> exportData(Project project, ZipOutputStream outputStream) {
        Map<String, Map<String, String>> representationManifests = new HashMap<>();

        var allRepresentationMetadata = this.representationDataSearchService.findAllMetadataByProject(AggregateReference.to(project.getId()));
        for (var representationMetadata: allRepresentationMetadata) {
            var optionalRepresentationContentNode = this.representationDataSearchService.findContentById(representationMetadata.id())
                    .flatMap(this.representationDataMigrationService::getMigratedContent);
            if (optionalRepresentationContentNode.isPresent()) {
                var representationContentNode = optionalRepresentationContentNode.get();

                var exportData = new RepresentationSerializedExportData(
                        representationMetadata.id(),
                        representationMetadata.project().getId(),
                        representationMetadata.descriptionId(),
                        representationMetadata.targetObjectId(),
                        representationMetadata.label(),
                        representationMetadata.kind(),
                        representationContentNode
                );

                // Get TargetObjectURI
                String uriFragment = "";
                var optionalEditingContext = this.editingContextSearchService.findById(project.getId().toString())
                        .filter(IEMFEditingContext.class::isInstance)
                        .map(IEMFEditingContext.class::cast);
                if (optionalEditingContext.isPresent()) {
                    var editingContext = optionalEditingContext.get();
                    String targetObjectId = representationMetadata.targetObjectId();
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
                        "type", representationMetadata.kind(),
                        "descriptionURI", representationMetadata.descriptionId(),
                        "targetObjectURI", uriFragment
                );
                representationManifests.put(representationMetadata.id().toString(), representationManifest);

                try {
                    byte[] bytes = this.objectMapper.writeValueAsBytes(exportData);

                    String name = project.getName() + "/representations/" + representationMetadata.id() + "." + JsonResourceFactoryImpl.EXTENSION;

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
