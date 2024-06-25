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

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections.RepresentationDataMetadataOnly;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
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

    private final IRepresentationDataSearchService representationDataSearchService;

    public RepresentationApplicationService(IRepresentationDataSearchService representationDataSearchService) {
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
    }

    @Override
    public Page<RepresentationMetadata> findAllByEditingContextId(String editingContextId, Pageable pageable) {
        var representationData =  new UUIDParser().parse(editingContextId)
                .map(AggregateReference::<Project, UUID>to)
                .map(this.representationDataSearchService::findAllMetadataByProject)
                .orElse(List.of())
                .stream()
                .sorted(Comparator.comparing(RepresentationDataMetadataOnly::label))
                .toList();

        int startIndex = (int) pageable.getOffset() * pageable.getPageSize();
        int endIndex = Math.min(((int) pageable.getOffset() + 1) * pageable.getPageSize(), representationData.size());
        var representationMetadata = representationData.subList(startIndex, endIndex).stream()
                .map(this::toRepresentationMetadata)
                .toList();
        return new PageImpl<>(representationMetadata, pageable, representationData.size());
    }

    private RepresentationMetadata toRepresentationMetadata(RepresentationDataMetadataOnly representationData) {
        return new RepresentationMetadata(representationData.id().toString(), representationData.kind(), representationData.label(), representationData.descriptionId());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> findEditingContextIdFromRepresentationId(String representationId) {
        return new UUIDParser().parse(representationId)
                .flatMap(this.representationDataSearchService::findProjectByRepresentationId)
                .map(AggregateReference::getId)
                .map(UUID::toString);
    }
}
