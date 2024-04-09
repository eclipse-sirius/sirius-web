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
package org.eclipse.sirius.web.application.studio.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.web.application.studio.services.api.IStudioColorPalettesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Used to load the color palettes for a studio.
 *
 * @author sbegaudeau
 */
@Service
public class StudioColorPalettesLoader implements IStudioColorPalettesLoader {

    private final Logger logger = LoggerFactory.getLogger(StudioColorPalettesLoader.class);

    @Override
    public Optional<View> loadStudioColorPalettes(ResourceSet resourceSet) {
        ClassPathResource classPathResource = new ClassPathResource("studioColorPalettes.json");
        URI uri = URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///" + UUID.nameUUIDFromBytes(classPathResource.getPath().getBytes()));
        var resource = new JSONResourceFactory().createResource(uri);
        try (var inputStream = new ByteArrayInputStream(classPathResource.getContentAsByteArray())) {
            resourceSet.getResources().add(resource);
            resource.load(inputStream, null);
            resource.eAdapters().add(new ResourceMetadataAdapter("studioColorPalettes"));
        } catch (IOException exception) {
            this.logger.warn("An error occured while loading document studioColorPalettes.json: {}.", exception.getMessage());
            resourceSet.getResources().remove(resource);
        }

        return resource.getContents()
                .stream()
                .filter(View.class::isInstance)
                .map(View.class::cast)
                .findFirst();
    }
}
