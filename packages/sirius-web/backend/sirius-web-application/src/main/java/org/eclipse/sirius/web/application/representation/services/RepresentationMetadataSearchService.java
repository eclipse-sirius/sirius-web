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
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
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

    public RepresentationMetadataSearchService(IRepresentationDataSearchService representationDataSearchService) {
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
    }

    @Override
    public Optional<RepresentationMetadata> findByRepresentationId(String representationId) {
        return new UUIDParser().parse(representationId)
                .flatMap(this.representationDataSearchService::findById)
                .map(representation -> new RepresentationMetadata(representation.getId().toString(), representation.getKind(), representation.getLabel(), representation.getDescriptionId()))
                .or(() -> this.findTransientRepresentationById(representationId));
    }

    private Optional<RepresentationMetadata> findTransientRepresentationById(String representationId) {
        Optional<RepresentationMetadata> representationMetadata = Optional.empty();
        if (representationId.startsWith(ExplorerDescriptionProvider.REPRESENTATION_ID)) {
            representationMetadata = Optional.of(new RepresentationMetadata(ExplorerDescriptionProvider.REPRESENTATION_ID, Tree.KIND, ExplorerDescriptionProvider.REPRESENTATION_NAME, ExplorerDescriptionProvider.DESCRIPTION_ID));
        }
        return representationMetadata;
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
