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
package org.eclipse.sirius.web.services.representations;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IRepresentationMetadataProvider} for persistent representations stored in the database.
 *
 * @author pcdavid
 */
@Service
public class PersistentRepresentationMetadataProvider implements IRepresentationMetadataProvider {

    private final IRepresentationRepository representationRepository;

    private final ObjectMapper objectMapper;

    public PersistentRepresentationMetadataProvider(IRepresentationRepository representationRepository, ObjectMapper objectMapper) {
        this.representationRepository = Objects.requireNonNull(representationRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public Optional<RepresentationMetadata> getMetadata(String representationId) {
        return new IDParser().parse(representationId)
                .flatMap(this.representationRepository::findById)
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .map(RepresentationDescriptor::getRepresentation)
                .filter(IRepresentation.class::isInstance)
                .map(IRepresentation.class::cast)
                .map(representation -> new RepresentationMetadata(representation.getId(), representation.getKind(), representation.getLabel(), representation.getDescriptionId()));
    }

}
