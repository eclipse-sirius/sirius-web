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

package org.eclipse.sirius.web.application.representation.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.graphql.dto.RepresentationMetadataDTO;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationMetadataMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationIconURL;
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

    public RepresentationMetadataMapper(List<IRepresentationImageProvider> representationImageProviders) {
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
    }

    @Override
    public RepresentationMetadataDTO toDTO(RepresentationMetadata representationMetadata) {
        List<String> icons = new ArrayList<>();
        if (representationMetadata.getIconURLs().isEmpty()) {
            icons = this.representationImageProviders.stream()
                    .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } else {
            icons = representationMetadata.getIconURLs().stream()
                    .map(RepresentationIconURL::url)
                    .toList();
        }
        return new RepresentationMetadataDTO(representationMetadata.getId().toString(), representationMetadata.getLabel(), representationMetadata.getKind(), representationMetadata.getDescriptionId(), icons);
    }
}
