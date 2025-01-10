/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.web.application.dto.Identified;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestBranch;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.eclipse.sirius.web.application.project.dto.RenameProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.RestProject;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import graphql.relay.Relay;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * REST Controller for the Project Endpoint.
 *
 * @author arichard
 */
@RestController
@RequestMapping("/api/rest/projects")
public class ProjectRestController {

    private static final int DEFAULT_PAGE_SIZE = 20;

    private static final OffsetDateTime DEFAULT_CREATED = Instant.EPOCH.atOffset(ZoneOffset.UTC);

    private final IProjectApplicationService projectApplicationService;

    public ProjectRestController(IProjectApplicationService projectApplicationService) {
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
    }

    @Operation(description = "Get all projects.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RestProject.class)))
        })
    })
    @GetMapping
    public ResponseEntity<List<RestProject>> getProjects(@RequestParam(name = "page[size]") Optional<Integer> pageSize, @RequestParam(name = "page[after]") Optional<String> pageAfter, @RequestParam(name = "page[before]") Optional<String> pageBefore) {
        final KeysetScrollPosition position;
        if (pageAfter.isPresent() && pageBefore.isEmpty()) {
            var cursorProjectId = new Relay().fromGlobalId(pageAfter.get()).getId();
            position = ScrollPosition.forward(Map.of("id", cursorProjectId));
        } else if (pageBefore.isPresent() && pageAfter.isEmpty()) {
            var cursorProjectId = new Relay().fromGlobalId(pageBefore.get()).getId();
            position = ScrollPosition.backward(Map.of("id", cursorProjectId));
        } else if (pageBefore.isPresent() && pageAfter.isPresent()) {
            position = ScrollPosition.keyset();
        } else {
            position = ScrollPosition.keyset();
        }
        int limit = pageSize.orElse(DEFAULT_PAGE_SIZE);
        var window = this.projectApplicationService.findAll(position, limit);
        var restProjects = window
            .map(project -> new RestProject(project.id(), DEFAULT_CREATED, new Identified(project.id()), null, project.name()))
            .toList();
        var headers = this.handleLinkResponseHeader(restProjects, position, window.hasNext(), limit);
        return new ResponseEntity<>(restProjects, headers, HttpStatus.OK);
    }

    @Operation(description = "Get project with the given id (projectId).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestProject.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content() })
    })
    @GetMapping(path = "/{projectId}")
    public ResponseEntity<RestProject> getProjectById(@PathVariable UUID projectId) {
        var restProject = this.projectApplicationService.findById(projectId)
            .map(project -> new RestProject(project.id(), DEFAULT_CREATED, new Identified(project.id()), null, project.name()));

        if (restProject.isPresent()) {
            return new ResponseEntity<>(restProject.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Create a new project with the given name and description (optional).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestProject.class))
        })
    })
    @PostMapping
    public ResponseEntity<RestProject> createProject(@RequestParam String name, @RequestParam Optional<String> description) {
        var createProjectInput = new CreateProjectInput(UUID.randomUUID(), name, List.of());
        var newProjectPayload = this.projectApplicationService.createProject(createProjectInput);

        if (newProjectPayload instanceof CreateProjectSuccessPayload createProjectSuccessPayload) {
            var projectDTO = createProjectSuccessPayload.project();
            var restProject = new RestProject(projectDTO.id(), DEFAULT_CREATED, new Identified(projectDTO.id()), null, projectDTO.name());
            return new ResponseEntity<>(restProject, HttpStatus.CREATED);
        }
        // The specification does not handle other HttpStatus than HttpStatus.CREATED for this endpoint
        return null;
    }

    @Operation(description = "Update the project with the given id (projectId).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestProject.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content() })
    })
    @PutMapping(path = "/{projectId}")
    public ResponseEntity<RestProject> updateProject(@PathVariable UUID projectId, @RequestParam Optional<String> name, @RequestParam Optional<String> description, @RequestParam Optional<RestBranch> branch) {
        if (name.isPresent()) {
            var renameProjectInput = new RenameProjectInput(UUID.randomUUID(), projectId, name.get());
            var renamedProjectPayload = this.projectApplicationService.renameProject(renameProjectInput);
            if (renamedProjectPayload instanceof RenameProjectSuccessPayload) {
                var restProject = new RestProject(projectId, DEFAULT_CREATED, new Identified(projectId), null, name.get());
                return new ResponseEntity<>(restProject, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Delete the project with the given id (projectId).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestProject.class))
        }),
        @ApiResponse(responseCode = "204", description = "No content", content = { @Content() })
    })
    @DeleteMapping(path = "/{projectId}")
    public ResponseEntity<RestProject> deleteProject(@PathVariable UUID projectId) {
        var restProject = this.projectApplicationService.findById(projectId)
                .map(project -> new RestProject(project.id(), DEFAULT_CREATED, new Identified(project.id()), null, project.name()))
                .orElse(null);

        var deleteProjectInput = new DeleteProjectInput(UUID.randomUUID(), projectId);
        var deleteProjectPayload = this.projectApplicationService.deleteProject(deleteProjectInput);

        if (deleteProjectPayload instanceof ErrorPayload) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(restProject, HttpStatus.OK);
    }

    private MultiValueMap<String, String> handleLinkResponseHeader(List<RestProject> projects, KeysetScrollPosition position, boolean hasNext, int limit) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        int projectsSize = projects.size();
        if (projectsSize > 0 && position.scrollsForward() && hasNext) {
            var headerLink = this.createHeaderLink(projects, limit, "after", "next");
            headers.add(HttpHeaders.LINK, headerLink);
        } else if (projectsSize > 0 && position.scrollsBackward() && hasNext) {
            var headerLink = this.createHeaderLink(projects, limit, "before", "prev");
            headers.add(HttpHeaders.LINK, headerLink);
        }
        return headers;
    }

    private String createHeaderLink(List<RestProject> projects, int limit, String beforeOrAfterPage, String relationType) {
        var header = new StringBuilder();
        var lastProject = projects.get(Math.min(projects.size() - 1, limit - 1));
        var cursorId = new Relay().toGlobalId("Project", lastProject.id().toString());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .queryParam("page[" + beforeOrAfterPage + "]", cursorId)
                .queryParam("page[size]", limit)
                .build();
        header.append("<");
        header.append(uriComponents.toUriString());
        header.append(">; rel=\"");
        header.append(relationType);
        header.append("\"");
        return header.toString();
    }
}
