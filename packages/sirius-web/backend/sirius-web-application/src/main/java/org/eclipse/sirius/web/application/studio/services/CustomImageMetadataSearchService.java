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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.view.emf.api.CustomImageMetadata;
import org.eclipse.sirius.components.view.emf.api.ICustomImageMetadataSearchService;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to return the custom images available for studios.
 *
 * @author sbegaudeau
 */
@Service
public class CustomImageMetadataSearchService implements ICustomImageMetadataSearchService {

    private final IImageSearchService imageSearchService;

    public CustomImageMetadataSearchService(IImageSearchService imageSearchService) {
        this.imageSearchService = Objects.requireNonNull(imageSearchService);
    }

    @Override
    public List<CustomImageMetadata> getAvailableImages(String editingContextId) {
        var projectCustomImageMetadata = new UUIDParser().parse(editingContextId)
                .map(this.imageSearchService::findAll)
                .orElse(List.of())
                .stream()
                .map(image -> new CustomImageMetadata(image.getId(), image.getLabel(), image.getContentType()))
                .toList();

        return new ArrayList<>(projectCustomImageMetadata);
    }
}
