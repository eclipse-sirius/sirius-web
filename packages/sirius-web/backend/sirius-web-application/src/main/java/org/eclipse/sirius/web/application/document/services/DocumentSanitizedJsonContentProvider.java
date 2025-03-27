/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.emf.migration.MigrationService;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.document.services.api.IDocumentSanitizedJsonContentProvider;
import org.eclipse.sirius.web.application.document.services.api.IExternalResourceLoaderService;
import org.eclipse.sirius.web.application.document.services.api.IProxyValidator;
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

    private final List<IExternalResourceLoaderService> externalResourceLoaderServices;

    private final List<IMigrationParticipant> migrationParticipants;

    private final IProxyValidator proxyValidator;

    public DocumentSanitizedJsonContentProvider(List<IExternalResourceLoaderService> externalResourceLoaderServices, IProxyValidator proxyValidator, List<IMigrationParticipant> migrationParticipants) {
        this.externalResourceLoaderServices = Objects.requireNonNull(externalResourceLoaderServices);
        this.proxyValidator = Objects.requireNonNull(proxyValidator);
        this.migrationParticipants = migrationParticipants;
    }

    @Override
    public Optional<String> getContent(ResourceSet resourceSet, String name, InputStream inputStream, boolean applyMigrationParticipants) {
        Optional<String> optionalContent = Optional.empty();

        URI resourceURI = new JSONResourceFactory().createResourceURI(name);
        Optional<Resource> optionalInputResource = this.getResource(resourceSet, resourceURI, inputStream, applyMigrationParticipants);
        if (optionalInputResource.isPresent()) {
            Resource inputResource = optionalInputResource.get();
            try {
                var hasProxies = this.proxyValidator.hasProxies(inputResource);
                if (hasProxies) {
                    this.logger.warn("The resource {} contains unresolvable proxies and will not be uploaded.", name);
                } else {
                    JsonResource ouputResource;
                    if (Objects.equals(inputResource.getURI(), resourceURI) && inputResource instanceof JsonResource jsonInputResource) {
                        ouputResource = jsonInputResource;
                    } else {
                        ouputResource = new JSONResourceFactory().createResource(resourceURI);
                        resourceSet.getResources().add(ouputResource);
                        ouputResource.getContents().addAll(inputResource.getContents());
                    }

                    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                        Map<String, Object> saveOptions = new HashMap<>();
                        saveOptions.put(JsonResource.OPTION_ENCODING, JsonResource.ENCODING_UTF_8);
                        saveOptions.put(JsonResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
                        saveOptions.put(JsonResource.OPTION_ID_MANAGER, new EObjectRandomIDManager());
                        if (applyMigrationParticipants) {
                            var migrationExtendedMetaData = new MigrationService(this.migrationParticipants);
                            saveOptions.put(JsonResource.OPTION_EXTENDED_META_DATA, migrationExtendedMetaData);
                            saveOptions.put(JsonResource.OPTION_JSON_RESSOURCE_PROCESSOR, migrationExtendedMetaData);
                        }

                        ouputResource.save(outputStream, saveOptions);

                        optionalContent = Optional.of(outputStream.toString());
                    } catch (IOException exception) {
                        this.logger.warn(exception.getMessage(), exception);
                    }
                }
            } finally {
                // Remove any temporary resource we may have added to the ResourceSet
                resourceSet.getResources().remove(inputResource);
                resourceSet.getResources().removeIf(res -> Objects.equals(res.getURI(), resourceURI));
            }
        }

        return optionalContent;
    }

    /**
     * Returns the {@link Resource} with the given {@link URI} or {@link Optional#empty()}.
     *
     * @param resourceSet
     *         The {@link ResourceSet} used to store the loaded resource
     * @param resourceURI
     *         The {@link URI} to use to create the {@link Resource}
     * @param inputStream
     *         The {@link InputStream} used to determine which {@link Resource} to create
     * @return a {@link Resource} or {@link Optional#empty()}
     */
    private Optional<Resource> getResource(ResourceSet resourceSet, URI resourceURI, InputStream inputStream, boolean applyMigrationParticipants) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            inputStream.transferTo(baos);
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        byte[] bytes = baos.toByteArray();
        return this.externalResourceLoaderServices.stream()
                .filter(loader -> loader.canHandle(new ByteArrayInputStream(bytes), resourceURI, resourceSet))
                .findFirst()
                .flatMap(loader -> loader.getResource(new ByteArrayInputStream(bytes), resourceURI, resourceSet, applyMigrationParticipants));
    }
}
