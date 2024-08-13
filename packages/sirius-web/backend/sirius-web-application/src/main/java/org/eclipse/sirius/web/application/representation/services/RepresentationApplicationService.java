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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.representation.dto.RepresentationMetadataDTO;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationApplicationService;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationMetadataMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<RepresentationMetadataDTO> findAllByEditingContextId(String editingContextId, Pageable pageable) {
        var representationMetadata =  new UUIDParser().parse(editingContextId)
                .map(AggregateReference::<Project, UUID>to)
                .map(this.representationMetadataSearchService::findAllMetadataByProject)
                .orElse(List.of())
                .stream()
                .sorted(Comparator.comparing(RepresentationMetadata::getLabel))
                .toList();

        int startIndex = (int) pageable.getOffset() * pageable.getPageSize();
        int endIndex = Math.min(((int) pageable.getOffset() + 1) * pageable.getPageSize(), representationMetadata.size());
        var representationMetadataDTO = representationMetadata.subList(startIndex, endIndex).stream()
                .map(this.representationMetadataMapper::toDTO)
                .toList();
        return new PageImpl<>(representationMetadataDTO, pageable, representationMetadataDTO.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> findEditingContextIdFromRepresentationId(String representationId) {
        return new UUIDParser().parse(representationId)
                .flatMap(this.representationMetadataSearchService::findProjectByRepresentationId)
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
