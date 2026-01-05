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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationContentMigrationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationContent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to find representations.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationSearchService implements IRepresentationSearchService {

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationContentSearchService representationContentSearchService;

    private final IRepresentationContentMigrationService representationContentMigrationService;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(RepresentationSearchService.class);

    public RepresentationSearchService(IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationContentSearchService representationContentSearchService, IRepresentationContentMigrationService representationContentMigrationService, ObjectMapper objectMapper) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationContentSearchService = Objects.requireNonNull(representationContentSearchService);
        this.representationContentMigrationService = Objects.requireNonNull(representationContentMigrationService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public <T extends IRepresentation> Optional<T> findById(IEditingContext editingContext, String representationId, Class<T> representationClass) {
        var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
        var optionalRepresentationMetadataId = new UUIDParser().parse(representationId);
        if (optionalSemanticDataId.isPresent() && optionalRepresentationMetadataId.isPresent()) {
            var semanticDataId = optionalSemanticDataId.get();
            var representationMetadataId = optionalRepresentationMetadataId.get();

            return this.representationMetadataSearchService.findMetadataById(AggregateReference.to(semanticDataId), representationMetadataId)
                    .flatMap(representationMetadata -> this.getRepresentation(editingContext, representationMetadata))
                    .filter(representationClass::isInstance)
                    .map(representationClass::cast);
        }
        return Optional.empty();
    }

    private Optional<IRepresentation> getRepresentation(IEditingContext editingContext, RepresentationMetadata representationMetadata) {
        return this.representationContentSearchService.findContentById(representationMetadata.getId())
                .map(representationContent -> this.migratedContent(editingContext, representationMetadata, representationContent))
                .flatMap(this::toRepresentation);
    }

    @Override
    public boolean existByIdAndKind(IEditingContext editingContext, String representationId, List<String> kinds) {
        var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
        var optionalRepresentationMetadataId = new UUIDParser().parse(representationId);
        if (optionalSemanticDataId.isPresent() && optionalRepresentationMetadataId.isPresent()) {
            var semanticDataId = optionalSemanticDataId.get();
            var representationMetadataId = optionalRepresentationMetadataId.get();

            return this.representationMetadataSearchService.existsByIdAndKind(AggregateReference.to(semanticDataId), representationMetadataId, kinds);
        }
        return false;
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

    private String migratedContent(IEditingContext editingContext, RepresentationMetadata representationMetadata, RepresentationContent representationContent) {
        return this.representationContentMigrationService.getMigratedContent(editingContext, representationMetadata, representationContent)
                .map(Object::toString)
                .orElse("");
    }

}
