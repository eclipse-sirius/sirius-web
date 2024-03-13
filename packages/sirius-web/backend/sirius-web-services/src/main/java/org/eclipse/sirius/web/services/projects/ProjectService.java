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
package org.eclipse.sirius.web.services.projects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.entities.ProjectNatureEntity;
import org.eclipse.sirius.web.persistence.repositories.IProjectNatureRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.projects.CreateProjectFromTemplateInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectFromTemplateSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.CreateProjectInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.ProjectRenamedEventPayload;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

/**
 * Service used to manipulate projects.
 *
 * @author sbegaudeau
 * @author pcdavid
 */
@Service
public class ProjectService implements IProjectService {

    private final IServicesMessageService messageService;

    private final IProjectRepository projectRepository;

    private final IProjectNatureRepository projectNatureRepository;

    private final IProjectTemplateService projectTemplateService;

    private final IEditingContextSearchService editingContextSearchService;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final ProjectMapper projectMapper;

    private Map<UUID, Many<IPayload>> projectIdsToSink = new HashMap<>();

    public ProjectService(IServicesMessageService messageService, IProjectRepository projectRepository, IProjectNatureRepository projectNatureRepository,
            IProjectTemplateService projectTemplateService,
            IEditingContextSearchService editingContextSearchService, IEditingContextPersistenceService editingContextPersistenceService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.projectNatureRepository = Objects.requireNonNull(projectNatureRepository);
        this.projectTemplateService = Objects.requireNonNull(projectTemplateService);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.projectMapper = new ProjectMapper();
    }

    @Override
    public Optional<Project> getProject(UUID projectId) {
        return this.projectRepository.findById(projectId).map(this.projectMapper::toDTO);
    }

    @Override
    public List<String> getNatures(UUID projectId) {
        return this.projectNatureRepository.findAllByProjectId(projectId).stream().map(ProjectNatureEntity::getName).toList();
    }

    @Override
    public Page<Project> getProjects(int page, int limit) {
        var pageable = PageRequest.of(page, limit);
        return this.projectRepository.findAll(pageable).map(this.projectMapper::toDTO);
    }

    @Override
    public IPayload createProject(CreateProjectInput input) {
        IPayload payload = null;
        String name = input.name().trim();

        if (!this.isValidProjectName(name)) {
            payload = new ErrorPayload(input.id(), this.messageService.invalidProjectName());
        } else {

            ProjectEntity projectEntity = this.createProjectEntity(name);
            projectEntity = this.projectRepository.save(projectEntity);
            List<String> natures = input.natures();
            if (natures != null && !natures.isEmpty()) {
                UUID projectid = projectEntity.getId();
                input.natures().stream()
                    .map(nature -> this.createProjectNatureEntity(projectid, nature))
                    .forEach(this.projectNatureRepository::save);
            }
            Project project = this.projectMapper.toDTO(projectEntity);
            payload = new CreateProjectSuccessPayload(input.id(), project);

        }
        return payload;
    }

    @Override
    public IPayload createProject(CreateProjectFromTemplateInput input) {
        IPayload result = new ErrorPayload(input.id(), this.messageService.unexpectedError());
        // @formatter:off
        var optionalTemplate = this.projectTemplateService.getProjectTemplateProviders().stream()
                .map(IProjectTemplateProvider::getProjectTemplates)
                .flatMap(List::stream)
                .filter(template -> template.getId().equals(input.templateId()))
                .findFirst();

        var optionalProjectTemplateInitializer = this.projectTemplateService.getProjectTemplateInitializers().stream()
                .filter(initializer -> initializer.canHandle(input.templateId()))
                .findFirst();
        // @formatter:on
        if (optionalTemplate.isPresent() && optionalProjectTemplateInitializer.isPresent()) {
            var template = optionalTemplate.get();
            var projectTemplateInitializer = optionalProjectTemplateInitializer.get();

            var createProjectInput = new CreateProjectInput(UUID.randomUUID(), template.getLabel());
            var payload = this.createProject(createProjectInput);
            if (payload instanceof CreateProjectSuccessPayload createProjectSuccessPayload) {
                var projectId = createProjectSuccessPayload.project().getId();

                template.getNatures().stream()
                        .map(nature -> this.createProjectNatureEntity(projectId, nature.natureId()))
                        .forEach(this.projectNatureRepository::save);

                var optionalEditingContext = this.editingContextSearchService.findById(projectId.toString());
                if (optionalEditingContext.isPresent()) {
                    var editingContext = optionalEditingContext.get();
                    var representationToOpen = projectTemplateInitializer.handle(input.templateId(), editingContext).orElse(null);

                    this.editingContextPersistenceService.persist(input, editingContext);
                    result = new CreateProjectFromTemplateSuccessPayload(createProjectInput.id(), createProjectSuccessPayload.project(), representationToOpen);
                }
            } else {
                result = payload;
            }
        }
        return result;
    }

    private ProjectNatureEntity createProjectNatureEntity(UUID projectId, String natureName) {
        ProjectNatureEntity projectNatureEntity = new ProjectNatureEntity();
        projectNatureEntity.setName(natureName);
        var projectEntity = new ProjectEntity();
        projectEntity.setId(projectId);
        projectNatureEntity.setProject(projectEntity);
        return projectNatureEntity;
    }

    private ProjectEntity createProjectEntity(String projectName) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(projectName);
        return projectEntity;
    }

    private boolean isValidProjectName(String name) {
        return 3 <= name.length() && name.length() <= 1024;
    }

    @Override
    public void delete(UUID projectId) {
        if (this.projectRepository.existsById(projectId)) {
            this.projectRepository.deleteById(projectId);
            this.projectIdsToSink.remove(projectId);
        }
    }

    @Override
    public Optional<Project> renameProject(UUID projectId, String newName) {
        Optional<ProjectEntity> optionalProjectEntity = this.projectRepository.findById(projectId);
        if (optionalProjectEntity.isPresent()) {
            ProjectEntity projectEntity = optionalProjectEntity.get();
            projectEntity.setName(newName);

            ProjectEntity renamedProjectEntity = this.projectRepository.save(projectEntity);
            Project renamedProject = this.projectMapper.toDTO(renamedProjectEntity);
            Optional.ofNullable(this.projectIdsToSink.get(projectId)).ifPresent(many -> many.tryEmitNext(new ProjectRenamedEventPayload(UUID.randomUUID(), projectId, newName)));
            return Optional.of(renamedProject);
        }
        return Optional.empty();
    }

    @Override
    public Flux<IPayload> getOutputEvents(UUID projectId) {
        Many<IPayload> many = this.projectIdsToSink.get(projectId);
        if (many == null) {
            many = Sinks.many().multicast().directBestEffort();
            this.projectIdsToSink.put(projectId, many);
        }
        return many.asFlux();
    }

}
