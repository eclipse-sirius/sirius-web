/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.services.representations;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.services.IRepresentationSearchService;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve representations.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationSearchService implements IRepresentationSearchService {
    private final IRepresentationRepository representationRepository;

    private final ObjectMapper objectMapper;

    public RepresentationSearchService(IRepresentationRepository representationRepository, ObjectMapper objectMapper) {
        this.representationRepository = Objects.requireNonNull(representationRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public <T extends IRepresentation> Optional<T> findById(UUID representationId, Class<T> representationClass) {
        // @formatter:off
        return this.representationRepository.findById(representationId)
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .map(RepresentationDescriptor::getRepresentation)
                .filter(representationClass::isInstance)
                .map(representationClass::cast);
        // @formatter:on
    }

    @Override
    public boolean existsById(UUID representationId) {
        return this.representationRepository.existsById(representationId);
    }
}
