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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections.RepresentationDataMetadataOnly;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to find representation data.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationDataSearchService implements IRepresentationDataSearchService {

    private final IRepresentationDataRepository representationDataRepository;

    public RepresentationDataSearchService(IRepresentationDataRepository representationDataRepository) {
        this.representationDataRepository = Objects.requireNonNull(representationDataRepository);
    }

    @Override
    public boolean existsById(UUID id) {
        return this.representationDataRepository.existsById(id);
    }

    @Override
    public Optional<RepresentationData> findById(UUID id) {
        return this.representationDataRepository.findById(id);
    }

    @Override
    public List<RepresentationData> findAllByProject(AggregateReference<Project, UUID> project) {
        return this.representationDataRepository.findAllByProjectId(project.getId());
    }

    @Override
    public List<RepresentationDataMetadataOnly> findAllMetadataByProject(AggregateReference<Project, UUID> project) {
        return this.representationDataRepository.findAllMetadataByProjectId(project.getId());
    }

    @Override
    public boolean existAnyRepresentationForTargetObjectId(String targetObjectId) {
        return this.representationDataRepository.existAnyRepresentationForTargetObjectId(targetObjectId);
    }

    @Override
    public List<RepresentationData> findAllByTargetObjectId(String targetObjectId) {
        return this.representationDataRepository.findAllByTargetObjectId(targetObjectId);
    }

    @Override
    public Optional<AggregateReference<Project, UUID>> findProjectByRepresentationId(UUID representationId) {
        return this.representationDataRepository.findProjectIdFromRepresentationId(representationId)
                .map(AggregateReference::to);
    }
}
