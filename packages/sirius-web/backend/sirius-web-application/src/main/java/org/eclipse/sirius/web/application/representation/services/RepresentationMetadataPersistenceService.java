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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataUpdateService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to persist representation metadata.
 *
 * @author gcoutable
 */
@Service
public class RepresentationMetadataPersistenceService implements IRepresentationMetadataPersistenceService {

    private final IRepresentationMetadataCreationService representationMetadataCreationService;

    private final IRepresentationMetadataUpdateService representationMetadataUpdateService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public RepresentationMetadataPersistenceService(IRepresentationMetadataCreationService representationMetadataCreationService,
            IRepresentationMetadataUpdateService representationMetadataUpdateService,
            IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.representationMetadataCreationService = Objects.requireNonNull(representationMetadataCreationService);
        this.representationMetadataUpdateService = Objects.requireNonNull(representationMetadataUpdateService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
    }

    @Override
    public void save(ICause cause, IEditingContext editingContext, org.eclipse.sirius.components.core.RepresentationMetadata representationMetadata, String targetObjectId) {
        var optionalProjectId = new UUIDParser().parse(editingContext.getId());
        var optionalRepresentationId = new UUIDParser().parse(representationMetadata.id());

        if (optionalRepresentationId.isPresent()) {
            var representationId = optionalRepresentationId.get();

            var exists = this.representationMetadataSearchService.existsById(representationId);
            if (exists) {
                this.representationMetadataUpdateService.updateRepresentationMetadataLabelById(cause, representationId, representationMetadata.label());
            } else if (optionalProjectId.isPresent()) {
                var projectId = optionalProjectId.get();
                var swRepresentationMetadata = RepresentationMetadata.newRepresentationMetadata(representationId)
                        .project(AggregateReference.to(projectId))
                        .label(representationMetadata.label())
                        .kind(representationMetadata.kind())
                        .descriptionId(representationMetadata.descriptionId())
                        .targetObjectId(targetObjectId)
                        .build(cause);
                this.representationMetadataCreationService.create(swRepresentationMetadata);
            }
        }
    }
}
