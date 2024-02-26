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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.UUIDParser;
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

    @Override
    public Optional<Document> toDocument(Resource resource) {
        HashMap<Object, Object> options = new HashMap<>();
        options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
        options.put(JsonResource.OPTION_SCHEMA_LOCATION, true);

        Optional<Document> optionalDocument = Optional.empty();
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
            optionalDocument = Optional.of(document);
        } catch (IllegalArgumentException | IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return optionalDocument;
    }
}
