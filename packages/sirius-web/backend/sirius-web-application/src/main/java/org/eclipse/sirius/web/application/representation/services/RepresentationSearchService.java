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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.representations.migration.IRepresentationMigrationParticipant;
import org.eclipse.sirius.components.collaborative.representations.migration.RepresentationMigrationService;
import org.eclipse.sirius.components.collaborative.representations.migration.RepresentationMigrationData;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataUpdateService;
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

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(RepresentationSearchService.class);

    private final List<IRepresentationMigrationParticipant> migrationParticipants;

    public RepresentationSearchService(IRepresentationDataSearchService representationDataSearchService, ObjectMapper objectMapper, List<IRepresentationMigrationParticipant> migrationParticipants, IRepresentationDataUpdateService representationDataUpdateService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.migrationParticipants = Objects.requireNonNull(migrationParticipants);
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
    }

    @Override
    public <T extends IRepresentation> Optional<T> findById(IEditingContext editingContext, String representationId, Class<T> representationClass) {
        return new UUIDParser().parse(representationId)
                .flatMap(this.representationDataSearchService::findById)
                .map(RepresentationData::getContent)
                .map(this::migrateContent)
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

    private String migrateContent(String content) {
        try {
            JsonNode rootJsonNode = this.objectMapper.readTree(content);
            ObjectNode rootObjectNode = (ObjectNode) rootJsonNode;
            var id = rootJsonNode.get("id").asText();
            var representationData = this.representationDataSearchService.findById(UUID.fromString(id));

            if (representationData.isPresent()) {
                var lastMigrationPerformed = representationData.get().getLastMigrationPerformed();
                var migrationVersion = representationData.get().getMigrationVersion();
                var migrationData = new RepresentationMigrationData(lastMigrationPerformed, migrationVersion);
                var migrationService = new RepresentationMigrationService(migrationParticipants, migrationData, rootObjectNode);
                migrationService.parseProperties(rootObjectNode, this.objectMapper);
                return rootObjectNode.toString();
            }
        }
        catch (JsonProcessingException | IllegalArgumentException exception) {
            this.logger.warn(exception.getMessage());
        }
        return content;
    }

}
