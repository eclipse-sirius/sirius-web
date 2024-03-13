/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import org.eclipse.sirius.components.collaborative.widget.reference.browser.ModelBrowsersDescriptionProvider;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.entities.RepresentationEntity;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.ITransientRepresentationMetadataSearchService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.services.explorer.ExplorerDescriptionProvider;
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
public class RepresentationService implements IRepresentationService, IRepresentationPersistenceService, IDanglingRepresentationDeletionService, ITransientRepresentationMetadataSearchService {

    private static final String TIMER_NAME = "siriusweb_representation_save";

    private final IObjectSearchService objectSearchService;

    private final IProjectRepository projectRepository;

    private final IRepresentationRepository representationRepository;

    private final ObjectMapper objectMapper;

    private final Timer timer;

    private final Logger logger = LoggerFactory.getLogger(RepresentationService.class);

    public RepresentationService(IObjectSearchService objectSearchService, IProjectRepository projectRepository, IRepresentationRepository representationRepository, ObjectMapper objectMapper, MeterRegistry meterRegistry) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
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
        return new IDParser().parse(projectId)
                .map(this.representationRepository::findAllByProjectId)
                .orElseGet(List::of)
                .stream()
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void save(ICause cause, IEditingContext editingContext, ISemanticRepresentation representation) {
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
        return this.representationRepository.findById(representationId)
                .map(new RepresentationMapper(this.objectMapper)::toDTO);
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
            Optional<Object> optionalObject = this.objectSearchService.getObject(editingContext, targetObjectId);
            return optionalObject.isEmpty();
        }
        return false;
    }

    @Override
    public void deleteDanglingRepresentations(String editingContextId) {
        new IDParser().parse(editingContextId).ifPresent(this.representationRepository::deleteDanglingRepresentations);
    }

    @Override
    public Optional<RepresentationMetadata> findByRepresentationId(String representationId) {
        Optional<RepresentationMetadata> transientRepresentation = this.findTransientRepresentationById(representationId);
        if (transientRepresentation.isPresent()) {
            return transientRepresentation;
        }
        return new IDParser().parse(representationId)
                .flatMap(this.representationRepository::findById)
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .map(RepresentationDescriptor::getRepresentation)
                .filter(IRepresentation.class::isInstance)
                .map(IRepresentation.class::cast)
                .map(representation -> new RepresentationMetadata(representation.getId(), representation.getKind(), representation.getLabel(), representation.getDescriptionId()));
    }

    @Override
    public Optional<RepresentationMetadata> findByRepresentation(IRepresentation representation) {
        return Optional.of(new RepresentationMetadata(representation.getId(), representation.getKind(), representation.getLabel(), representation.getDescriptionId()));
    }

    @Override
    public List<RepresentationMetadata> findAllByTargetObjectId(IEditingContext editingContext, String targetObjectId) {
        return this.representationRepository.findAllByTargetObjectId(targetObjectId).stream()
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .map(RepresentationDescriptor::getRepresentation)
                .map(representation -> this.findByRepresentation(representation).get())
                .toList();
    }

    @Override
    public Optional<RepresentationMetadata> findTransientRepresentationById(String representationId) {
        Optional<RepresentationMetadata> representationMetadata = Optional.empty();
        if (representationId.startsWith(ExplorerDescriptionProvider.REPRESENTATION_ID)) {
            representationMetadata = Optional.of(new RepresentationMetadata(ExplorerDescriptionProvider.REPRESENTATION_ID, Tree.KIND, ExplorerDescriptionProvider.REPRESENTATION_NAME, ExplorerDescriptionProvider.DESCRIPTION_ID));
        } else if (representationId.startsWith(ModelBrowsersDescriptionProvider.MODEL_BROWSER_CONTAINER_KIND)) {
            representationMetadata = Optional.of(new RepresentationMetadata(ModelBrowsersDescriptionProvider.MODEL_BROWSER_CONTAINER_KIND, Tree.KIND, ModelBrowsersDescriptionProvider.REPRESENTATION_NAME, ModelBrowsersDescriptionProvider.CONTAINER_DESCRIPTION_ID));
        } else if (representationId.startsWith(ModelBrowsersDescriptionProvider.MODEL_BROWSER_REFERENCE_KIND)) {
            representationMetadata = Optional.of(new RepresentationMetadata(ModelBrowsersDescriptionProvider.MODEL_BROWSER_REFERENCE_KIND, Tree.KIND, ModelBrowsersDescriptionProvider.REPRESENTATION_NAME, ModelBrowsersDescriptionProvider.REFERENCE_DESCRIPTION_ID));
        }
        return representationMetadata;
    }
}
