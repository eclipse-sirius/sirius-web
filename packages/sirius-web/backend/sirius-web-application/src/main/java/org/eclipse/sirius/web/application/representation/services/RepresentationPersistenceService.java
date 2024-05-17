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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.representations.migration.IRepresentationMigrationParticipant;
import org.eclipse.sirius.components.collaborative.representations.migration.RepresentationMigrationData;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
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

    private final IRepresentationDataSearchService representationDataSearchService;

    private final IRepresentationDataCreationService representationDataCreationService;

    private final IRepresentationDataUpdateService representationDataUpdateService;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(RepresentationPersistenceService.class);

    private final List<IRepresentationMigrationParticipant> migrationParticipants;

    public RepresentationPersistenceService(IRepresentationDataSearchService representationDataSearchService, IRepresentationDataCreationService representationDataCreationService, IRepresentationDataUpdateService representationDataUpdateService, ObjectMapper objectMapper, List<IRepresentationMigrationParticipant> migrationParticipants) {
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
        this.representationDataCreationService = Objects.requireNonNull(representationDataCreationService);
        this.representationDataUpdateService = representationDataUpdateService;
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.migrationParticipants = migrationParticipants;
    }

    @Override
    @Transactional
    public void save(IEditingContext editingContext, IRepresentation representation) {
        var optionalProjectId = new UUIDParser().parse(editingContext.getId());
        var optionalRepresentationId = new UUIDParser().parse(representation.getId());
        if (optionalProjectId.isPresent() && optionalRepresentationId.isPresent()) {
            var projectId = optionalProjectId.get();
            var representationId = optionalRepresentationId.get();

            String content = this.toString(representation);

            var exists = this.representationDataSearchService.existsById(representationId);

            if (exists) {
                var migrationData = this.getLastMigrationData(representation.getKind());
                this.representationDataUpdateService.updateContentWithMigrationData(representationId, content, migrationData.lastMigrationPerformed(), migrationData.migrationVersion());
            } else {
                var migrationData = this.getInitialMigrationData(representation.getKind());
                var representationData = RepresentationData.newRepresentationData(representationId)
                        .project(AggregateReference.to(projectId))
                        .label(representation.getLabel())
                        .kind(representation.getKind())
                        .descriptionId(representation.getDescriptionId())
                        .targetObjectId(representation.getTargetObjectId())
                        .content(content)
                        .lastMigrationPerformed(migrationData.lastMigrationPerformed())
                        .migrationVersion(migrationData.migrationVersion())
                        .build();

                this.representationDataCreationService.create(representationData);
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
                .sorted(Comparator.comparing(IRepresentationMigrationParticipant::getVersion))
                .sorted(Collections.reverseOrder())
                .map(migrationParticipant -> new RepresentationMigrationData(NONE, migrationParticipant.getVersion()))
                .findFirst().orElse(new RepresentationMigrationData(NONE, "0"));
    }

    private RepresentationMigrationData getLastMigrationData(String kind) {
        return this.migrationParticipants.stream()
                .filter(migrationParticipant -> migrationParticipant.getKind().equals(kind))
                .sorted(Comparator.comparing(IRepresentationMigrationParticipant::getVersion))
                .sorted(Collections.reverseOrder())
                .map(migrationParticipant -> new RepresentationMigrationData(migrationParticipant.getClass().getSimpleName(), migrationParticipant.getVersion()))
                .findFirst().orElse(new RepresentationMigrationData(NONE, "0"));
    }
}
