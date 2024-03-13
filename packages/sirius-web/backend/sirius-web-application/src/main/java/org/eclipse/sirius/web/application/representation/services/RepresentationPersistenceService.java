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

import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;
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

    private final IRepresentationDataSearchService representationDataSearchService;

    private final IRepresentationDataCreationService representationDataCreationService;

    private final IRepresentationDataUpdateService representationDataUpdateService;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(RepresentationPersistenceService.class);

    public RepresentationPersistenceService(IRepresentationDataSearchService representationDataSearchService, IRepresentationDataCreationService representationDataCreationService, IRepresentationDataUpdateService representationDataUpdateService, ObjectMapper objectMapper) {
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
        this.representationDataCreationService = Objects.requireNonNull(representationDataCreationService);
        this.representationDataUpdateService = representationDataUpdateService;
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    @Transactional
    public void save(ICause cause, IEditingContext editingContext, ISemanticRepresentation representation) {
        var optionalProjectId = new UUIDParser().parse(editingContext.getId());
        var optionalRepresentationId = new UUIDParser().parse(representation.getId());

        if (optionalProjectId.isPresent() && optionalRepresentationId.isPresent()) {
            var projectId = optionalProjectId.get();
            var representationId = optionalRepresentationId.get();

            String content = this.toString(representation);

            var exists = this.representationDataSearchService.existsById(representationId);
            if (exists) {
                this.representationDataUpdateService.updateContent(cause, representationId, content);
            } else {
                var representationData = RepresentationData.newRepresentationData(representationId)
                        .project(AggregateReference.to(projectId))
                        .label(representation.getLabel())
                        .kind(representation.getKind())
                        .descriptionId(representation.getDescriptionId())
                        .targetObjectId(representation.getTargetObjectId())
                        .content(content)
                        .build(cause);

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
}
