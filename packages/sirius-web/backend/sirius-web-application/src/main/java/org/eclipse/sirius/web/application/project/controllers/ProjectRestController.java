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
package org.eclipse.sirius.web.application.project.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestBranch;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.eclipse.sirius.web.application.project.dto.RenameProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.RestProject;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for the Project Endpoint.
 *
 * @author arichard
 */
@RestController
@RequestMapping("/api/rest/projects")
public class ProjectRestController {

    private final IProjectApplicationService projectApplicationService;

    public ProjectRestController(IProjectApplicationService projectApplicationService) {
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
    }

    @GetMapping
    public ResponseEntity<List<RestProject>> getProjects() {
        var restProjects = this.projectApplicationService.findAll(PageRequest.of(0, 20))
            .map(project -> new RestProject(project.id(), null, List.of(), null, null, project.name(), List.of()))
            .toList();

        return new ResponseEntity<>(restProjects, HttpStatus.OK);
    }

    @GetMapping(path = "/{projectId}")
    public ResponseEntity<RestProject> getProjectById(@PathVariable UUID projectId) {
        var restProject = this.projectApplicationService.findById(projectId)
            .map(project -> new RestProject(project.id(), null, List.of(), null, null, project.name(), List.of()));

        if (restProject.isPresent()) {
            return new ResponseEntity<>(restProject.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<RestProject> createProject(@RequestParam String name, @RequestParam Optional<String> description) {
        var createProjectInput = new CreateProjectInput(UUID.randomUUID(), name, List.of());
        var newProjectPayload = this.projectApplicationService.createProject(createProjectInput);

        if (newProjectPayload instanceof CreateProjectSuccessPayload createProjectSuccessPayload) {
            var projectDTO = createProjectSuccessPayload.project();
            var restProject = new RestProject(projectDTO.id(), null, List.of(), null, null, projectDTO.name(), List.of());
            return new ResponseEntity<>(restProject, HttpStatus.CREATED);
        }
        // The specification does not handle other HttpStatus than HttpStatus.CREATED for this endpoint
        return null;
    }

    @PutMapping(path = "/{projectId}")
    public ResponseEntity<RestProject> updateProject(@PathVariable UUID projectId, @RequestParam Optional<String> name, @RequestParam Optional<String> description, @RequestParam Optional<RestBranch> branch) {
        if (name.isPresent()) {
            var renameProjectInput = new RenameProjectInput(UUID.randomUUID(), projectId, name.get());
            var renamedProjectPayload = this.projectApplicationService.renameProject(renameProjectInput);
            if (renamedProjectPayload instanceof RenameProjectSuccessPayload) {
                var restProject = new RestProject(projectId, null, List.of(), null, null, name.get(), List.of());
                return new ResponseEntity<>(restProject, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{projectId}")
    public ResponseEntity<RestProject> deleteProject(@PathVariable UUID projectId) {
        var restProject = this.projectApplicationService.findById(projectId)
                .map(project -> new RestProject(project.id(), null, List.of(), null, null, project.name(), List.of()))
                .orElse(null);

        var deleteProjectInput = new DeleteProjectInput(UUID.randomUUID(), projectId);
        var deleteProjectPayload = this.projectApplicationService.deleteProject(deleteProjectInput);

        if (deleteProjectPayload instanceof ErrorPayload) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(restProject, HttpStatus.OK);
    }
}
