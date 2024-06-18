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

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to find the metadata of a representation.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationMetadataSearchService implements IRepresentationMetadataSearchService {

    private final IRepresentationDataSearchService representationDataSearchService;

    private final List<IRepresentationMetadataProvider> representationMetadataProviders;

    public RepresentationMetadataSearchService(IRepresentationDataSearchService representationDataSearchService, List<IRepresentationMetadataProvider> representationMetadataProviders) {
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
        this.representationMetadataProviders = Objects.requireNonNull(representationMetadataProviders);
    }

    @Override
    public Optional<RepresentationMetadata> findByRepresentationId(String representationId) {
        return this.representationMetadataProviders.stream()
                .flatMap(provider -> provider.getMetadata(representationId).stream())
                .findFirst();
    }

    @Override
    public Optional<RepresentationMetadata> findByRepresentation(IRepresentation representation) {
        return Optional.of(new RepresentationMetadata(representation.getId(), representation.getKind(), representation.getLabel(), representation.getDescriptionId()));
    }

    @Override
    public List<RepresentationMetadata> findAllByTargetObjectId(IEditingContext editingContext, String targetObjectId) {
        return this.representationDataSearchService.findAllByTargetObjectId(targetObjectId)
                .stream()
                .map(representation -> new RepresentationMetadata(representation.getId().toString(), representation.getKind(), representation.getLabel(), representation.getDescriptionId()))
                .toList();
    }
}
