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
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataDeletionService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections.RepresentationDataMetadataOnly;
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

    private final IRepresentationDataSearchService representationDataSearchService;

    private final IRepresentationDataDeletionService representationDataDeletionService;

    public DanglingRepresentationDeletionService(IObjectSearchService objectSearchService, IRepresentationDataSearchService representationDataSearchService, IRepresentationDataDeletionService representationDataDeletionService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
        this.representationDataDeletionService = Objects.requireNonNull(representationDataDeletionService);
    }

    @Override
    public boolean isDangling(IEditingContext editingContext, IRepresentation representation) {
        String targetObjectId = representation.getTargetObjectId();
        Optional<Object> optionalObject = this.objectSearchService.getObject(editingContext, targetObjectId);
        return optionalObject.isEmpty();
    }

    @Override
    @Transactional
    public void deleteDanglingRepresentations(IEditingContext editingContext) {
        new UUIDParser().parse(editingContext.getId()).ifPresent(projectId -> {
            this.representationDataSearchService.findAllMetadataByProject(AggregateReference.to(projectId)).stream()
                    .filter(representationMetadata -> this.objectSearchService.getObject(editingContext, representationMetadata.targetObjectId()).isEmpty())
                    .map(RepresentationDataMetadataOnly::id)
                    .forEach(this.representationDataDeletionService::delete);
        });
    }
}
