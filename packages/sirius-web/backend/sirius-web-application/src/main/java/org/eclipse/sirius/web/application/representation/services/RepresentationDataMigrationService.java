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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.representations.migration.IRepresentationMigrationParticipant;
import org.eclipse.sirius.components.collaborative.representations.migration.RepresentationMigrationService;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationDataMigrationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections.RepresentationDataContentOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve the migrated content of the representation data.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationDataMigrationService implements IRepresentationDataMigrationService {

    private final ObjectMapper objectMapper;

    private final List<IRepresentationMigrationParticipant> migrationParticipants;

    private final Logger logger = LoggerFactory.getLogger(RepresentationDataMigrationService.class);

    public RepresentationDataMigrationService(ObjectMapper objectMapper, List<IRepresentationMigrationParticipant> migrationParticipants) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.migrationParticipants = Objects.requireNonNull(migrationParticipants);
    }

    @Override
    public Optional<ObjectNode> getMigratedContent(RepresentationDataContentOnly representationData) {
        Optional<ObjectNode> optionalObjectNode = Optional.empty();
        try {
            JsonNode rootJsonNode = this.objectMapper.readTree(representationData.content());
            if (rootJsonNode instanceof ObjectNode objectNode) {

                List<IRepresentationMigrationParticipant> applicableParticipants = this.getApplicableMigrationParticipants(representationData);
                if (!applicableParticipants.isEmpty()) {
                    var migrationService = new RepresentationMigrationService(applicableParticipants, objectNode);
                    migrationService.parseProperties(objectNode, this.objectMapper);
                }

                optionalObjectNode = Optional.of(objectNode);
            }
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            this.logger.warn(exception.getMessage());
        }

        return optionalObjectNode;
    }

    private List<IRepresentationMigrationParticipant> getApplicableMigrationParticipants(RepresentationDataContentOnly representationData) {
        var migrationVersion = representationData.migrationVersion();
        var kind = representationData.kind();

        return this.migrationParticipants.stream()
                .filter(migrationParticipant -> Objects.equals(migrationParticipant.getKind(), kind))
                .filter(migrationParticipant -> migrationParticipant.getVersion().compareTo(migrationVersion) > 0)
                .sorted(Comparator.comparing(IRepresentationMigrationParticipant::getVersion))
                .toList();
    }
}
