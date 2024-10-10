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

package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationContent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationContentRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to find representation content.
 *
 * @author gcoutable
 */
@Service
public class RepresentationContentSearchService implements IRepresentationContentSearchService {

    private final IRepresentationContentRepository representationContentRepository;

    public RepresentationContentSearchService(IRepresentationContentRepository representationContentRepository) {
        this.representationContentRepository = Objects.requireNonNull(representationContentRepository);
    }

    @Override
    public Optional<RepresentationContent> findContentByRepresentationMetadata(AggregateReference<RepresentationMetadata, UUID> representationMetadata) {
        return this.representationContentRepository.findContentByRepresentationMetadataId(representationMetadata.getId());
    }
}
