/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.representations.migration.IRepresentationMigrationParticipant;
import org.eclipse.sirius.components.collaborative.representations.migration.RepresentationMigrationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.migration.MigrationVersionComparator;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationContentMigrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tools.jackson.databind.node.ObjectNode;

/**
 * Used to retrieve the migrated content of the representation content.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationContentMigrationService implements IRepresentationContentMigrationService {

    private final ObjectMapper objectMapper;

    private final List<IRepresentationMigrationParticipant> migrationParticipants;

    private final Logger logger = LoggerFactory.getLogger(RepresentationContentMigrationService.class);

    public RepresentationContentMigrationService(ObjectMapper objectMapper, List<IRepresentationMigrationParticipant> migrationParticipants) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.migrationParticipants = Objects.requireNonNull(migrationParticipants);
    }

    @Override
    public Optional<ObjectNode> getMigratedContent(IEditingContext editingContext, String representationContent, String kind, String lastMigrationPerformed, String migrationVersion) {
        Optional<ObjectNode> optionalObjectNode = Optional.empty();
        try {
            JsonNode rootJsonNode = this.objectMapper.readTree(representationContent);
            if (rootJsonNode instanceof ObjectNode objectNode) {

                List<IRepresentationMigrationParticipant> applicableParticipants = this.getApplicableMigrationParticipants(kind, migrationVersion);
                if (!applicableParticipants.isEmpty()) {
                    var migrationService = new RepresentationMigrationService(editingContext, applicableParticipants, objectNode);
                    migrationService.parseProperties(objectNode, this.objectMapper);
                }

                optionalObjectNode = Optional.of(objectNode);
            }
        } catch (JacksonException | IllegalArgumentException exception) {
            this.logger.atWarn()
                    .setMessage("Deserialization of the representation failed")
                    .setCause(exception)
                    .log();
        }

        return optionalObjectNode;
    }

    private List<IRepresentationMigrationParticipant> getApplicableMigrationParticipants(String kind, String migrationVersion) {
        MigrationVersionComparator migrationVersionComparator = new MigrationVersionComparator();
        return this.migrationParticipants.stream()
                .filter(migrationParticipant -> Objects.equals(migrationParticipant.getKind(), kind))
                .filter(migrationParticipant -> migrationVersionComparator.compare(migrationParticipant.getVersion(), migrationVersion) > 0)
                .sorted(Comparator.comparing(IRepresentationMigrationParticipant::getVersion, migrationVersionComparator))
                .toList();
    }
}
