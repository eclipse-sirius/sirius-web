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
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.document.services.api.IDocumentExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * Used to export documents as XML resources.
 *
 * @author sbegaudeau
 */
@Service
public class XMLDocumentExporter implements IDocumentExporter {

    private final Logger logger = LoggerFactory.getLogger(XMLDocumentExporter.class);

    @Override
    public boolean canHandle(Resource resource, String mediaType) {
        return MediaType.APPLICATION_XML.toString().equals(mediaType);
    }

    @Override
    public Optional<byte[]> getBytes(Resource resource, String mediaType) {
        Optional<byte[]> optionalBytes = Optional.empty();

        var outputResource = new XMIResourceImpl(resource.getURI());
        outputResource.getContents().addAll(resource.getContents());

        Map<String, Object> options = new HashMap<>();
        options.put(XMIResource.OPTION_ENCODING, JsonResource.ENCODING_UTF_8);
        options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
        options.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            outputResource.save(outputStream, options);
            optionalBytes = Optional.of(outputStream.toByteArray());
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return optionalBytes;
    }
}
