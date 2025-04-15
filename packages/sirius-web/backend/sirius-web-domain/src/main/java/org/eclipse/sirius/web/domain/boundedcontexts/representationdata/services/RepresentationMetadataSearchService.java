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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services;

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationMetadataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Used to find representation metadata.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationMetadataSearchService implements IRepresentationMetadataSearchService {

    private final IRepresentationMetadataRepository representationMetadataRepository;

    public RepresentationMetadataSearchService(IRepresentationMetadataRepository representationMetadataRepository) {
        this.representationMetadataRepository = Objects.requireNonNull(representationMetadataRepository);
    }

    @Override
    public boolean existsById(UUID id) {
        return this.representationMetadataRepository.existsById(id);
    }

    @Override
    public Optional<RepresentationMetadata> findMetadataById(UUID id) {
        return this.representationMetadataRepository.findMetadataById(id);
    }

    @Override
    public boolean existsByIdAndKind(UUID id, List<String> kinds) {
        return this.representationMetadataRepository.existsByIdAndKind(id, kinds);
    }

    @Override
    public List<RepresentationMetadata> findAllRepresentationMetadataBySemanticData(AggregateReference<SemanticData, UUID> semanticData) {
        return this.representationMetadataRepository.findAllRepresentationMetadataBySemanticDataId(semanticData.getId());
    }

    @Override
    public Window<RepresentationMetadata> findAllRepresentationMetadataBySemanticData(AggregateReference<SemanticData, UUID> semanticData, KeysetScrollPosition position, int limit) {
        Window<RepresentationMetadata> window = new Window<>(List.of(), index -> position, false, false);
        if (limit > 0) {
            var cursorKey = position.getKeys().get("id");
            if (cursorKey instanceof UUID cursorId) {
                if (this.existsById(cursorId)) {
                    if (position.scrollsForward()) {
                        window = this.findAllForwardBySemanticData(semanticData, position, cursorId, limit);
                    } else if (position.scrollsBackward()) {
                        window = this.findAllBackwardBySemanticData(semanticData, position, cursorId, limit);
                    }
                }
            } else {
                var allRepresentationMetadata = this.representationMetadataRepository.findAllRepresentationMetadataBySemanticDataIdAfter(semanticData.getId(), null, limit + 1);
                boolean hasNext = allRepresentationMetadata.size() > limit;
                boolean hasPrevious = false;
                window = new Window<>(allRepresentationMetadata.subList(0, Math.min(allRepresentationMetadata.size(), limit)), index -> position, hasNext, hasPrevious);
            }
        }
        return window;
    }

    private Window<RepresentationMetadata> findAllForwardBySemanticData(AggregateReference<SemanticData, UUID> semanticData, KeysetScrollPosition position, UUID cursorId, int limit) {
        var allRepresentationMetadata = this.representationMetadataRepository.findAllRepresentationMetadataBySemanticDataIdAfter(semanticData.getId(), cursorId, limit + 1);
        boolean hasNext = allRepresentationMetadata.size() > limit;
        boolean hasPrevious = false;

        if (!allRepresentationMetadata.isEmpty()) {
            var firstRepresentationMetadataId = allRepresentationMetadata.get(0).getId();
            hasPrevious = !this.representationMetadataRepository.findAllRepresentationMetadataBySemanticDataIdBefore(semanticData.getId(), firstRepresentationMetadataId, 1).isEmpty();
        }

        return new Window<>(allRepresentationMetadata.subList(0, Math.min(allRepresentationMetadata.size(), limit)), index -> position, hasNext, hasPrevious);
    }

    private Window<RepresentationMetadata> findAllBackwardBySemanticData(AggregateReference<SemanticData, UUID> semanticData, KeysetScrollPosition position, UUID cursorId, int limit) {
        var allRepresentationMetadata = this.representationMetadataRepository.findAllRepresentationMetadataBySemanticDataIdBefore(semanticData.getId(), cursorId, limit + 1);
        boolean hasPrevious = allRepresentationMetadata.size() > limit;
        boolean hasNext = false;

        if (!allRepresentationMetadata.isEmpty()) {
            var lastRepresentationMetadataId = allRepresentationMetadata.get(allRepresentationMetadata.size() - 1).getId();
            hasNext = !this.representationMetadataRepository.findAllRepresentationMetadataBySemanticDataIdAfter(semanticData.getId(), lastRepresentationMetadataId, 1).isEmpty();
        }

        if (hasPrevious) {
            return new Window<>(allRepresentationMetadata.subList(allRepresentationMetadata.size() - limit, limit + 1), index -> position, hasNext, hasPrevious);
        }
        return new Window<>(allRepresentationMetadata, index -> position, hasNext, hasPrevious);
    }

    @Override
    public List<RepresentationMetadata> findAllRepresentationMetadataBySemanticDataAndTargetObjectId(AggregateReference<SemanticData, UUID> semanticData, String targetObjectId) {
        return this.representationMetadataRepository.findAllRepresentationMetadataBySemanticDataIdAndTargetObjectId(semanticData.getId(), targetObjectId);
    }

    @Override
    public boolean existAnyRepresentationMetadataForSemanticDataAndTargetObjectId(AggregateReference<SemanticData, UUID> semanticData, String targetObjectId) {
        return this.representationMetadataRepository.existAnyRepresentationMetadataForSemanticDataIdAndTargetObjectId(semanticData.getId(), targetObjectId);
    }

    @Override
    public Optional<AggregateReference<SemanticData, UUID>> findSemanticDataByRepresentationId(UUID representationId) {
        return this.representationMetadataRepository.findSemanticDataIdFromRepresentationId(representationId)
                .map(AggregateReference::to);
    }
}
