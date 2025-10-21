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
package org.eclipse.sirius.web.application.representation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.representations.migration.IRepresentationMigrationParticipant;
import org.eclipse.sirius.components.collaborative.representations.migration.RepresentationMigrationData;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to persist representations.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationPersistenceService implements IRepresentationPersistenceService {

    private static final String NONE = "none";

    private final IRepresentationContentSearchService representationContentSearchService;

    private final IRepresentationContentCreationService representationContentCreationService;

    private final IRepresentationContentUpdateService representationContentUpdateService;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(RepresentationPersistenceService.class);

    private final List<IRepresentationMigrationParticipant> migrationParticipants;

    public RepresentationPersistenceService(IRepresentationContentSearchService representationContentSearchService, IRepresentationContentCreationService representationContentCreationService, IRepresentationContentUpdateService representationContentUpdateService, ObjectMapper objectMapper, List<IRepresentationMigrationParticipant> migrationParticipants) {
        this.representationContentSearchService = Objects.requireNonNull(representationContentSearchService);
        this.representationContentCreationService = Objects.requireNonNull(representationContentCreationService);
        this.representationContentUpdateService = Objects.requireNonNull(representationContentUpdateService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.migrationParticipants = migrationParticipants;
    }

    @Override
    @Transactional
    public void save(ICause cause, IEditingContext editingContext, IRepresentation representation) {
        var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
        var optionalRepresentationId = new UUIDParser().parse(representation.getId());
        if (optionalSemanticDataId.isPresent() && optionalRepresentationId.isPresent()) {
            var semanticDataId = optionalSemanticDataId.get();
            var representationId = optionalRepresentationId.get();

            String content = this.toString(representation);

            var exists = this.representationContentSearchService.existsById(representationId);

            if (exists) {
                var migrationData = this.getLastMigrationData(representation.getKind());
                this.representationContentUpdateService.updateContentByRepresentationIdWithMigrationData(cause, representationId, content, migrationData.lastMigrationPerformed(), migrationData.migrationVersion());
            } else {
                var migrationData = this.getInitialMigrationData(representation.getKind());
                this.representationContentCreationService.create(cause, representationId, semanticDataId, content, migrationData.lastMigrationPerformed(), migrationData.migrationVersion());
            }
        }
    }

    private String toString(IRepresentation representation) {
        String content = "";
        try {
            content = this.objectMapper.writeValueAsString(representation);
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return content;
    }

    private RepresentationMigrationData getInitialMigrationData(String kind) {
        return this.migrationParticipants.stream()
                .filter(migrationParticipant -> migrationParticipant.getKind().equals(kind))
                .sorted(Comparator.comparing(IRepresentationMigrationParticipant::getVersion).reversed())
                .map(migrationParticipant -> new RepresentationMigrationData(NONE, migrationParticipant.getVersion()))
                .findFirst().orElse(new RepresentationMigrationData(NONE, "0"));
    }

    private RepresentationMigrationData getLastMigrationData(String kind) {
        return this.migrationParticipants.stream()
                .filter(migrationParticipant -> migrationParticipant.getKind().equals(kind))
                .sorted(Comparator.comparing(IRepresentationMigrationParticipant::getVersion).reversed())
                .map(migrationParticipant -> new RepresentationMigrationData(migrationParticipant.getClass().getSimpleName(), migrationParticipant.getVersion()))
                .findFirst().orElse(new RepresentationMigrationData(NONE, "0"));
    }
}
