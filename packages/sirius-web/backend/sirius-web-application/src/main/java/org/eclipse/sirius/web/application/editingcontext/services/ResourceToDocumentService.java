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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.migration.MigrationService;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

/**
 * Used to transform an EMF resource into a document.
 *
 * @author sbegaudeau
 */
@Service
public class ResourceToDocumentService implements IResourceToDocumentService {

    private final Logger logger = LoggerFactory.getLogger(ResourceToDocumentService.class);

    private final List<IMigrationParticipant> migrationParticipants;

    public ResourceToDocumentService(List<IMigrationParticipant> migrationParticipants) {
        this.migrationParticipants = migrationParticipants;
    }

    @Override
    public Optional<DocumentData> toDocument(Resource resource) {
        var serializationListener = new JsonResourceSerializationListener();
        var migrationService = new MigrationService(this.migrationParticipants);

        HashMap<Object, Object> options = new HashMap<>();
        options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
        options.put(JsonResource.OPTION_SCHEMA_LOCATION, true);
        options.put(JsonResource.OPTION_SERIALIZATION_LISTENER, serializationListener);
        options.put(JsonResource.OPTION_JSON_RESSOURCE_PROCESSOR, migrationService);
        options.put(JsonResource.OPTION_EXTENDED_META_DATA, migrationService);

        Optional<DocumentData> optionalDocumentData = Optional.empty();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            resource.save(outputStream, options);

            for (Resource.Diagnostic warning : resource.getWarnings()) {
                this.logger.warn(warning.getMessage());
            }
            for (Resource.Diagnostic error : resource.getErrors()) {
                this.logger.warn(error.getMessage());
            }

            var name = resource.eAdapters().stream()
                    .filter(ResourceMetadataAdapter.class::isInstance)
                    .map(ResourceMetadataAdapter.class::cast)
                    .findFirst()
                    .map(ResourceMetadataAdapter::getName)
                    .orElse("");
            var content = outputStream.toString();

            var resourceId = resource.getURI().path().substring(1);
            var documentId = new UUIDParser().parse(resourceId).orElse(UUID.randomUUID());

            var document = Document.newDocument(documentId)
                    .name(name)
                    .content(content)
                    .build();
            var documentData = new DocumentData(document, serializationListener.getePackageEntries());
            optionalDocumentData = Optional.of(documentData);
        } catch (IllegalArgumentException | IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return optionalDocumentData;
    }

    public Optional<ResourceMetadataAdapter> getOptionalResourceMetadataAdapter(Resource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst();
    }
}
