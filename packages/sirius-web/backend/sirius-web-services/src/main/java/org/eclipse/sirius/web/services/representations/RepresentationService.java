/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.services.representations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.entities.RepresentationEntity;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * The service to manipulate representations.
 *
 * @author gcoutable
 */
@Service
public class RepresentationService implements IRepresentationService, IRepresentationPersistenceService, IDanglingRepresentationDeletionService {

    private static final String TIMER_NAME = "siriusweb_representation_save"; //$NON-NLS-1$

    private final IObjectService objectService;

    private final IProjectRepository projectRepository;

    private final IRepresentationRepository representationRepository;

    private final ObjectMapper objectMapper;

    private final Timer timer;

    private final Logger logger = LoggerFactory.getLogger(RepresentationService.class);

    public RepresentationService(IObjectService objectService, IProjectRepository projectRepository, IRepresentationRepository representationRepository, ObjectMapper objectMapper,
            MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.representationRepository = Objects.requireNonNull(representationRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);

        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Override
    public boolean hasRepresentations(String objectId) {
        return this.representationRepository.hasRepresentations(objectId);
    }

    @Override
    public Optional<RepresentationDescriptor> getRepresentationDescriptorForProjectId(String projectId, String representationId) {
        var projectUUID = new IDParser().parse(projectId);
        var representationUUID = new IDParser().parse(representationId);

        if (projectUUID.isPresent() && representationUUID.isPresent()) {
            return this.representationRepository.findByIdAndProjectId(representationUUID.get(), projectUUID.get()).map(new RepresentationMapper(this.objectMapper)::toDTO);
        }

        return Optional.empty();
    }

    @Override
    public List<RepresentationDescriptor> getRepresentationDescriptorsForProjectId(String projectId) {
        // @formatter:off
        return new IDParser().parse(projectId)
                .map(this.representationRepository::findAllByProjectId)
                .orElseGet(List::of)
                .stream()
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    @Override
    public void save(IEditingContext editingContext, ISemanticRepresentation representation) {
        long start = System.currentTimeMillis();

        var editingContextId = new IDParser().parse(editingContext.getId());
        var representationId = new IDParser().parse(representation.getId());

        if (editingContextId.isPresent() && representationId.isPresent()) {
            UUID editingContextUUID = editingContextId.get();
            UUID representationUUID = representationId.get();

            var optionalProjectEntity = this.projectRepository.findById(editingContextUUID);
            if (optionalProjectEntity.isPresent()) {
                ProjectEntity projectEntity = optionalProjectEntity.get();

                RepresentationEntity representationEntity = this.toEntity(projectEntity, representationUUID, representation);
                this.representationRepository.save(representationEntity);
            }
        }

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
    }

    private RepresentationEntity toEntity(ProjectEntity projectEntity, UUID representationId, ISemanticRepresentation representation) {
        RepresentationEntity representationEntity = new RepresentationEntity();

        representationEntity.setId(representationId);
        representationEntity.setProject(projectEntity);
        representationEntity.setLabel(representation.getLabel());
        representationEntity.setTargetObjectId(representation.getTargetObjectId());
        representationEntity.setKind(representation.getKind());
        representationEntity.setDescriptionId(representation.getDescriptionId());
        try {
            String content = this.objectMapper.writeValueAsString(representation);
            representationEntity.setContent(content);
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return representationEntity;
    }

    @Override
    public Optional<RepresentationDescriptor> getRepresentation(UUID representationId) {
        // @formatter:off
        return this.representationRepository.findById(representationId)
                .map(new RepresentationMapper(this.objectMapper)::toDTO);
        // @formatter:off
    }

    @Override
    public boolean existsById(UUID representationId) {
        return this.representationRepository.existsById(representationId);
    }

    @Override
    public void delete(UUID representationId) {
        this.representationRepository.deleteById(representationId);
    }

    @Override
    public boolean isDangling(IEditingContext editingContext, IRepresentation representation) {
        if (representation instanceof ISemanticRepresentation) {
            ISemanticRepresentation semanticRepresentation = (ISemanticRepresentation) representation;
            String targetObjectId = semanticRepresentation.getTargetObjectId();
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, targetObjectId);
            return optionalObject.isEmpty();
        }
        return false;
    }

    @Override
    public void deleteDanglingRepresentations(String editingContextId) {
        new IDParser().parse(editingContextId).ifPresent(this.representationRepository::deleteDanglingRepresentations);
    }

    @Override
    public Optional<RepresentationMetadata> findByRepresentation(IRepresentation representation) {
        // @formatter:off
        String targetObjectId = Optional.of(representation)
                .filter(ISemanticRepresentation.class::isInstance)
                .map(ISemanticRepresentation.class::cast)
                .map(ISemanticRepresentation::getTargetObjectId)
                .orElse(null);
        // @formatter:on
        return Optional.of(new RepresentationMetadata(representation.getId(), representation.getKind(), representation.getLabel(), representation.getDescriptionId(), targetObjectId));
    }

    @Override
    public List<RepresentationMetadata> findAllByTargetObjectId(IEditingContext editingContext, String targetObjectId) {
        // @formatter:off
        return this.representationRepository.findAllByTargetObjectId(targetObjectId).stream()
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .map(RepresentationDescriptor::getRepresentation)
                .map(representation -> this.findByRepresentation(representation).get())
                .collect(Collectors.toList());
        // @formatter:on
    }
}
