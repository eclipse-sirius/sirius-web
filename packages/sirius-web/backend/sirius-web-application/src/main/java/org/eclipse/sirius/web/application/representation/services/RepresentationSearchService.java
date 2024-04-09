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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationDataMigrationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections.RepresentationDataContentOnly;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to find representations.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationSearchService implements IRepresentationSearchService {

    private final IRepresentationDataSearchService representationDataSearchService;

    private final IRepresentationDataMigrationService representationDataMigrationService;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(RepresentationSearchService.class);

    public RepresentationSearchService(IRepresentationDataSearchService representationDataSearchService, IRepresentationDataMigrationService representationDataMigrationService, ObjectMapper objectMapper) {
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
        this.representationDataMigrationService = Objects.requireNonNull(representationDataMigrationService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public <T extends IRepresentation> Optional<T> findById(IEditingContext editingContext, String representationId, Class<T> representationClass) {
        return new UUIDParser().parse(representationId)
                .flatMap(this.representationDataSearchService::findContentById)
                .map(this::migratedContent)
                .flatMap(this::toRepresentation)
                .filter(representationClass::isInstance)
                .map(representationClass::cast);
    }


    private Optional<IRepresentation> toRepresentation(String content) {
        Optional<IRepresentation> optionalRepresentation = Optional.empty();

        try {
            IRepresentation representation = this.objectMapper.readValue(content, IRepresentation.class);
            optionalRepresentation = Optional.of(representation);
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return optionalRepresentation;
    }

    private String migratedContent(RepresentationDataContentOnly representationData) {
        return this.representationDataMigrationService.getMigratedContent(representationData)
                .map(Object::toString)
                .orElse("");
    }

}