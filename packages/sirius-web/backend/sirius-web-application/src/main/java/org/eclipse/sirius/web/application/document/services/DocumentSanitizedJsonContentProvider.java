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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.utils.EMFResourceUtils;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;
import org.eclipse.sirius.web.application.document.services.api.IDocumentSanitizedJsonContentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to compute a sanitized content for a given resource.
 *
 * @author sbegaudeau
 */
@Service
public class DocumentSanitizedJsonContentProvider implements IDocumentSanitizedJsonContentProvider {

    private final Logger logger = LoggerFactory.getLogger(DocumentSanitizedJsonContentProvider.class);

    @Override
    public Optional<String> getContent(ResourceSet resourceSet, String name, InputStream inputStream) {
        Optional<String> optionalContent = Optional.empty();

        URI resourceURI = new JSONResourceFactory().createResourceURI(name);
        Optional<Resource> optionalInputResource = this.getResource(resourceSet, resourceURI, inputStream);
        if (optionalInputResource.isPresent()) {
            Resource inputResource = optionalInputResource.get();

            JsonResource ouputResource = new JSONResourceFactory().createResourceFromPath(name);
            resourceSet.getResources().add(ouputResource);
            ouputResource.getContents().addAll(inputResource.getContents());

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                Map<String, Object> saveOptions = new HashMap<>();
                saveOptions.put(JsonResource.OPTION_ENCODING, JsonResource.ENCODING_UTF_8);
                saveOptions.put(JsonResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
                saveOptions.put(JsonResource.OPTION_ID_MANAGER, new EObjectRandomIDManager());

                ouputResource.save(outputStream, saveOptions);

                optionalContent = Optional.of(outputStream.toString());
            } catch (IOException exception) {
                this.logger.warn(exception.getMessage(), exception);
            }
        }

        return optionalContent;
    }

    /**
     * Returns the {@link Resource} with the given {@link URI} or {@link Optional#empty()} regarding the content of
     * the first line of the given {@link InputStream}.
     *
     * <p>
     * Returns a {@link JsonResourceImpl} if the first line contains a '{', a {@link XMIResourceImpl} if the first line
     * contains '<', {@link Optional#empty()} otherwise.
     * </p>
     *
     * @param resourceSet
     *            The {@link ResourceSet} used to store the loaded resource
     * @param resourceURI
     *            The {@link URI} to use to create the {@link Resource}
     * @param inputStream
     *            The {@link InputStream} used to determine which {@link Resource} to create
     *
     * @return a {@link JsonResourceImpl}, a {@link XMIResourceImpl} or {@link Optional#empty()}
     */
    private Optional<Resource> getResource(ResourceSet resourceSet, URI resourceURI, InputStream inputStream) {
        Resource resource = null;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(Integer.MAX_VALUE);
        try (var reader = new BufferedReader(new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            Map<String, Object> options = new HashMap<>();
            if (line != null) {
                if (line.contains("{")) {
                    resource = new JSONResourceFactory().createResource(resourceURI);
                } else if (line.contains("<")) {
                    resource = new XMIResourceImpl(resourceURI);
                    options = new EMFResourceUtils().getXMILoadOptions();
                }
            }
            bufferedInputStream.reset();
            if (resource != null) {
                resourceSet.getResources().add(resource);
                resource.load(bufferedInputStream, options);
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return Optional.ofNullable(resource);
    }
}
