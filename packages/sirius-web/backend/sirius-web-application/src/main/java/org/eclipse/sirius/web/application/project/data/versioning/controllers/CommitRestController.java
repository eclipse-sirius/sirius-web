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
package org.eclipse.sirius.web.application.project.data.versioning.controllers;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.web.application.project.data.versioning.dto.ChangeType;
import org.eclipse.sirius.web.application.project.data.versioning.dto.CreateCommitRestInput;
import org.eclipse.sirius.web.application.project.data.versioning.dto.CreateCommitRestSuccessPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetCommitByIdRestInput;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetCommitByIdRestSuccessPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetCommitChangeByIdRestInput;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetCommitChangeByIdRestSuccessPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetCommitChangeRestInput;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetCommitChangeRestSuccessPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetCommitsRestInput;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetCommitsRestSuccessPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestCommit;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestDataVersion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

/**
 * REST Controller for the Project Data Versioning - Commits Endpoints.
 *
 * @author arichard
 */
@RestController
@RequestMapping("/api/rest/projects/{projectId}/commits")
public class CommitRestController {

    private static final int TIMEOUT = 20;

    private final IEditingContextDispatcher editingContextDispatcher;

    public CommitRestController(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Operation(description = "Get all the commits in the given project.")
    @GetMapping
    public ResponseEntity<List<RestCommit>> getCommits(@PathVariable UUID projectId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetCommitsRestInput(UUID.randomUUID())).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetCommitsRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.commits(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(description = """
        Create a new commit with the given change (collection
        of DataVersion records) in the given branch of the
        project. If the branch is not specified, the default branch
        of the project is used. Commit.change should include
        the following for each Data object that needs to be
        created, updated, or deleted in the new commit. (1)
        Creating Data - Commit.change should include a
        DataVersion record with DataVersion.payload
        populated with the Data being created.
        DataVersion.identity is not provided, thereby indicating
        that a new DataIdentity needs to be created in the new
        commit. (2) Updating Data - Commit.change should
        include a DataVersion record with DataVersion.payload
        populated with the updated Data. DataVersion.identity
        should be populated with the DataIdentity for which a
        new DataVersion record will be created in the new
        commit. (3) Deleting Data - Commit.change should
        include a DataVersion record with DataVersion.payload
        not provided, thereby indicating deletion of DataIdentity
        in the new commit. DataVersion.identity should be
        populated with the DataIdentity that will be deleted in
        the new commit. When a DataIdentity is deleted in a
        commit, all its versions (DataVersion) are also deleted,
        and any references from other DataIdentity are also
        removed to maintain data integrity. In addition, for
        Element Data (KerML), deletion of an Element must
        also result in deletion of incoming Relationships. When
        Element Data (KerML) is created or updated, derived
        properties must be computed or verified if the API
        provider claims Derived Property Conformance.
        """)
    @PostMapping
    public ResponseEntity<RestCommit> createCommit(@PathVariable UUID projectId, @RequestParam Optional<UUID> branchId) {
        var payload = this.editingContextDispatcher.dispatchMutation(projectId.toString(), new CreateCommitRestInput(UUID.randomUUID(), branchId)).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof CreateCommitRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.commit(), HttpStatus.OK);
        }
        // The specification does not handle other HttpStatus than HttpStatus.CREATED for this endpoint
        return null;
    }

    @Operation(description = "Get the commit with the given id (commitId) in the given project.")
    @GetMapping(path = "/{commitId}")
    public ResponseEntity<RestCommit> getCommitById(@PathVariable UUID projectId, @PathVariable UUID commitId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetCommitByIdRestInput(UUID.randomUUID(), commitId)).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetCommitByIdRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.commit(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Get the change in the given commit of the given project.")
    @GetMapping(path = "/{commitId}/changes")
    public ResponseEntity<List<RestDataVersion>> getCommitChange(@PathVariable UUID projectId, @PathVariable UUID commitId, @RequestParam Optional<List<ChangeType>> changeTypes) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetCommitChangeRestInput(UUID.randomUUID(), commitId)).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetCommitChangeRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.commitChanges(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Get the change with the given id (changeId) in the given commit of the given project. The changeId is the id of the DataVersion that changed in the commit.")
    @GetMapping(path = "/{commitId}/changes/{changeId}")
    public ResponseEntity<RestDataVersion> getCommitChangeById(@PathVariable UUID projectId, @PathVariable UUID commitId, @PathVariable UUID changeId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetCommitChangeByIdRestInput(UUID.randomUUID(), commitId, changeId)).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetCommitChangeByIdRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.commitChange(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
