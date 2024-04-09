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
package org.eclipse.sirius.web.application.document.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.document.services.api.IDocumentExporter;
import org.eclipse.sirius.web.application.editingcontext.services.JsonResourceSerializationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * Used to export documents as Json resources.
 *
 * @author sbegaudeau
 */
@Service
public class JsonDocumentExporter implements IDocumentExporter {

    private final Logger logger = LoggerFactory.getLogger(JsonDocumentExporter.class);

    @Override
    public boolean canHandle(Resource resource, String mediaType) {
        return MediaType.APPLICATION_JSON.toString().equals(mediaType);
    }

    @Override
    public Optional<byte[]> getBytes(Resource resource, String mediaType) {
        Optional<byte[]> optionalBytes = Optional.empty();

        if (resource instanceof JsonResource jsonResource) {
            var serializationListener = new JsonResourceSerializationListener();

            HashMap<Object, Object> options = new HashMap<>();
            options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
            options.put(JsonResource.OPTION_SCHEMA_LOCATION, true);
            options.put(JsonResource.OPTION_SERIALIZATION_LISTENER, serializationListener);

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
                jsonResource.save(outputStream, options);
                optionalBytes = Optional.of(outputStream.toByteArray());
            } catch (IOException exception) {
                this.logger.warn(exception.getMessage(), exception);
            }
        }

        return optionalBytes;
    }
}
