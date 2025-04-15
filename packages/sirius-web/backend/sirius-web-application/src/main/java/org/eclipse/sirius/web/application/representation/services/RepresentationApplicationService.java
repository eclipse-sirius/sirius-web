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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.components.core.graphql.dto.RepresentationMetadataDTO;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationApplicationService;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationMetadataMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to interact with representations.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationApplicationService implements IRepresentationApplicationService {

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationMetadataMapper representationMetadataMapper;

    public RepresentationApplicationService(IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationMetadataMapper representationMetadataMapper) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationMetadataMapper = Objects.requireNonNull(representationMetadataMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Window<RepresentationMetadataDTO> findAllByEditingContextId(String editingContextId, KeysetScrollPosition position, int limit) {
        var optionalSemanticData = new UUIDParser().parse(editingContextId)
                .map(AggregateReference::<SemanticData, UUID>to);
        if (optionalSemanticData.isPresent()) {
            var semanticData = optionalSemanticData.get();
            var window = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(semanticData, position, limit);
            return new Window<>(window.map(this.representationMetadataMapper::toDTO), window.hasPrevious());
        }
        return new Window<>(List.of(), index -> position, false, false);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> findEditingContextIdFromRepresentationId(String representationId) {
        return new UUIDParser().parse(representationId)
                .flatMap(this.representationMetadataSearchService::findSemanticDataByRepresentationId)
                .map(AggregateReference::getId)
                .map(UUID::toString);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RepresentationMetadataDTO> findRepresentationMetadataById(String representationMetadataId) {
        return new UUIDParser().parse(representationMetadataId)
                .flatMap(this.representationMetadataSearchService::findMetadataById)
                .map(this.representationMetadataMapper::toDTO);
    }
}
