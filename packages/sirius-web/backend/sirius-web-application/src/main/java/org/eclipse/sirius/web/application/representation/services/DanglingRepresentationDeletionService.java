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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataDeletionService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to delete dangling representations.
 *
 * @author sbegaudeau
 */
@Service
public class DanglingRepresentationDeletionService implements IDanglingRepresentationDeletionService {

    private final IObjectSearchService objectSearchService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationMetadataDeletionService representationMetadataDeletionService;

    public DanglingRepresentationDeletionService(IObjectSearchService objectSearchService, IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationMetadataDeletionService representationMetadataDeletionService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationMetadataDeletionService = Objects.requireNonNull(representationMetadataDeletionService);
    }

    @Override
    public boolean isDangling(IEditingContext editingContext, IRepresentation representation) {
        String targetObjectId = representation.getTargetObjectId();
        Optional<Object> optionalObject = this.objectSearchService.getObject(editingContext, targetObjectId);
        return optionalObject.isEmpty();
    }

    @Override
    @Transactional
    public void deleteDanglingRepresentations(ICause cause, IEditingContext editingContext) {
        new UUIDParser().parse(editingContext.getId()).ifPresent(projectId -> {
            this.representationMetadataSearchService.findAllMetadataByProject(AggregateReference.to(projectId)).stream()
                    .filter(representationMetadata -> this.objectSearchService.getObject(editingContext, representationMetadata.getTargetObjectId()).isEmpty())
                    .map(RepresentationMetadata::getId)
                    .forEach(representationId -> this.representationMetadataDeletionService.delete(cause, representationId));
        });
    }
}
