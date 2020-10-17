/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.representations;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.entities.RepresentationEntity;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.eclipse.sirius.web.services.api.monitoring.IStopWatch;
import org.eclipse.sirius.web.services.api.monitoring.IStopWatchFactory;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The service to manipulate representations.
 *
 * @author gcoutable
 */
@Service
public class RepresentationService implements IRepresentationService {

    private final Logger logger = LoggerFactory.getLogger(RepresentationService.class);

    private final IProjectRepository projectRepository;

    private final IRepresentationRepository representationRepository;

    private final ObjectMapper objectMapper;

    private final IStopWatchFactory stopWatchFactory;

    public RepresentationService(IProjectRepository projectRepository, IRepresentationRepository representationRepository, ObjectMapper objectMapper, IStopWatchFactory stopWatchFactory) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.representationRepository = Objects.requireNonNull(representationRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.stopWatchFactory = Objects.requireNonNull(stopWatchFactory);
    }

    @Override
    public boolean hasRepresentations(String objectId) {
        return this.representationRepository.hasRepresentations(objectId);
    }

    @Override
    public Optional<RepresentationDescriptor> getRepresentationDescriptorForProjectId(UUID projectId, UUID representationId) {
        return this.representationRepository.findByIdAndProjectId(representationId, projectId).map(new RepresentationMapper(this.objectMapper)::toDTO);
    }

    @Override
    public List<RepresentationDescriptor> getRepresentationDescriptorsForProjectId(UUID projectId) {
        // @formatter:off
        return this.representationRepository.findAllByProjectId(projectId).stream()
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    @Override
    public List<RepresentationDescriptor> getRepresentationDescriptorsForObjectId(String objectId) {
        // @formatter:off
        return this.representationRepository.findAllByTargetObjectId(objectId).stream()
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    @Override
    public void save(RepresentationDescriptor representationDescriptor) {
        IStopWatch stopWatch = this.stopWatchFactory.createStopWatch("Saving representation"); //$NON-NLS-1$
        stopWatch.start("Finding ProjectEntity"); //$NON-NLS-1$
        var optionalProjectEntity = this.projectRepository.findById(representationDescriptor.getProjectId());
        if (optionalProjectEntity.isPresent()) {
            ProjectEntity projectEntity = optionalProjectEntity.get();
            stopWatch.stop();
            stopWatch.start("RepresentationEntity creation (incl. serialization to JSON)"); //$NON-NLS-1$
            RepresentationEntity representationEntity = new RepresentationMapper(this.objectMapper).toEntity(representationDescriptor, projectEntity);
            stopWatch.stop();
            int length = representationEntity.getContent().length() / 1024;
            stopWatch.start("Saving RepresentationEntity to database (" + length + " kiB)"); //$NON-NLS-1$ //$NON-NLS-2$
            this.representationRepository.save(representationEntity);
            stopWatch.stop();
        }
        this.logger.debug(System.lineSeparator() + stopWatch.prettyPrint());
    }

    @Override
    public Optional<RepresentationDescriptor> getRepresentation(UUID representationId) {
        // @formatter:off
        return this.representationRepository.findById(representationId)
                .map(new RepresentationMapper(this.objectMapper)::toDTO);
        // @formatter:off
    }

    @Override
    public void delete(UUID representationId) {
        this.representationRepository.deleteById(representationId);
    }
}
