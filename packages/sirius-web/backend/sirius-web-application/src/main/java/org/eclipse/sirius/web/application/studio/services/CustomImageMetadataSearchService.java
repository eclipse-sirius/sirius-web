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
package org.eclipse.sirius.web.application.studio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.view.emf.api.CustomImageMetadata;
import org.eclipse.sirius.components.view.emf.api.ICustomImageMetadataSearchService;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services.api.IProjectImageSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to return the custom images available for studios.
 *
 * @author sbegaudeau
 */
@Service
public class CustomImageMetadataSearchService implements ICustomImageMetadataSearchService {

    private final IImageSearchService imageSearchService;

    private final IProjectImageSearchService projectImageSearchService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    public CustomImageMetadataSearchService(IImageSearchService imageSearchService, IProjectImageSearchService projectImageSearchService,
            IProjectSemanticDataSearchService projectSemanticDataSearchService) {
        this.imageSearchService = Objects.requireNonNull(imageSearchService);
        this.projectImageSearchService = Objects.requireNonNull(projectImageSearchService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
    }

    @Override
    public List<CustomImageMetadata> getAvailableImages(String editingContextId) {
        var globalCustomImageMetadata = this.imageSearchService.findAll().stream()
                .map(image -> new CustomImageMetadata(image.getId(), image.getLabel(), image.getContentType()))
                .toList();

        var projectCustomImageMetadata = this.toProjectId(editingContextId)
                .map(this.projectImageSearchService::findAll)
                .orElseGet(ArrayList::new)
                .stream()
                .map(image -> new CustomImageMetadata(image.getId(), image.getLabel(), image.getContentType()))
                .toList();

        List<CustomImageMetadata> imageMetadata = new ArrayList<>();
        imageMetadata.addAll(globalCustomImageMetadata);
        imageMetadata.addAll(projectCustomImageMetadata);
        return imageMetadata;
    }

    private Optional<String> toProjectId(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .map(AggregateReference::<SemanticData, UUID>to)
                .flatMap(this.projectSemanticDataSearchService::findBySemanticDataId)
                .map(ProjectSemanticData::getProject)
                .map(AggregateReference::getId);
    }
}
