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

package org.eclipse.sirius.web.application.representation.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.api.IImageURLSanitizer;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.web.application.representation.dto.RepresentationMetadataDTO;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationMetadataMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * Used to convert a representation metadata to a DTO.
 *
 * @author gcoutable
 */
@Service
public class RepresentationMetadataMapper implements IRepresentationMetadataMapper {

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final IImageURLSanitizer imageURLSanitizer;

    public RepresentationMetadataMapper(List<IRepresentationImageProvider> representationImageProviders, IImageURLSanitizer imageURLSanitizer) {
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.imageURLSanitizer = Objects.requireNonNull(imageURLSanitizer);
    }

    @Override
    public RepresentationMetadataDTO toDTO(RepresentationMetadata representationMetadata) {
        var icons = this.representationImageProviders.stream()
                .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(url -> this.imageURLSanitizer.sanitize(URLConstants.IMAGE_BASE_PATH, url))
                .toList();
        
        return new RepresentationMetadataDTO(representationMetadata.getId().toString(), representationMetadata.getLabel(), representationMetadata.getKind(), representationMetadata.getDescriptionId(), icons);
    }
}
