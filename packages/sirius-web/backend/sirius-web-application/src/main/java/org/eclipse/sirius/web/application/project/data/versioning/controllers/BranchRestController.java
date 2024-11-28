/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.project.data.versioning.controllers;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.web.application.project.data.versioning.dto.CreateBranchRestInput;
import org.eclipse.sirius.web.application.project.data.versioning.dto.CreateBranchRestSuccessPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.DeleteBranchRestInput;
import org.eclipse.sirius.web.application.project.data.versioning.dto.DeleteBranchRestSuccessPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetBranchByIdRestInput;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetBranchByIdRestSuccessPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetBranchesRestInput;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetBranchesRestSuccessPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestBranch;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestBranchRequest;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * REST Controller for the Project Data Versioning - Branches Endpoints.
 *
 * @author arichard
 */
@RestController
@RequestMapping("/api/rest/projects/{projectId}/branches")
public class BranchRestController {

    private static final int TIMEOUT = 20;

    private final IEditingContextDispatcher editingContextDispatcher;

    private final IProjectApplicationService projectApplicationService;

    public BranchRestController(IEditingContextDispatcher editingContextDispatcher, IProjectApplicationService projectApplicationService) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
    }

    @Operation(description = "Get branches by project.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RestBranch.class)))
        }),
        @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content() })
    })
    @GetMapping
    public ResponseEntity<List<RestBranch>> getBranches(@PathVariable String projectId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId, new GetBranchesRestInput(UUID.randomUUID())).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetBranchesRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.branches(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Create branch by project.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestBranch.class))
        })
    })
    @PostMapping
    public ResponseEntity<RestBranch> createBranch(@PathVariable String projectId, @RequestBody RestBranchRequest requestBody) {
        UUID commitId = null;
        var head = requestBody.head();
        if (head != null) {
            commitId = UUID.fromString(head.id());
        }
        var payload = this.editingContextDispatcher.dispatchMutation(projectId, new CreateBranchRestInput(UUID.randomUUID(), requestBody.name(), commitId)).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof CreateBranchRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.branch(), HttpStatus.CREATED);
        }
        // The specification does not handle other HttpStatus than HttpStatus.CREATED for this endpoint
        return null;
    }

    @Operation(description = "Get branch by project and ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestBranch.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content() })
    })
    @GetMapping(path = "/{branchId}")
    public ResponseEntity<RestBranch> getBranchById(@PathVariable String projectId, @PathVariable UUID branchId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId, new GetBranchByIdRestInput(UUID.randomUUID(), branchId)).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetBranchByIdRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.branch(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Delete branch by project and ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestBranch.class))
        }),
        @ApiResponse(responseCode = "204", description = "No content", content = { @Content() }),
        @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content() })
    })
    @DeleteMapping(path = "/{branchId}")
    public ResponseEntity<RestBranch> deleteBranch(@PathVariable String projectId, @PathVariable UUID branchId) {
        ResponseEntity<RestBranch> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        var restProject = this.projectApplicationService.findById(projectId);
        if (restProject.isPresent()) {
            var payload = this.editingContextDispatcher.dispatchQuery(projectId, new DeleteBranchRestInput(UUID.randomUUID(), branchId)).block(Duration.ofSeconds(TIMEOUT));
            if (payload instanceof DeleteBranchRestSuccessPayload successPayload) {
                responseEntity = new ResponseEntity<>(successPayload.branch(), HttpStatus.OK);
            } else if (payload instanceof ErrorPayload) {
                responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
        }
        return responseEntity;
    }
}
