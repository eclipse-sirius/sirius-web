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

    @GetMapping
    public ResponseEntity<List<RestCommit>> getCommits(@PathVariable UUID projectId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetCommitsRestInput(UUID.randomUUID())).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetCommitsRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.commits(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<RestCommit> createCommit(@PathVariable UUID projectId, @RequestParam Optional<UUID> branchId) {
        var payload = this.editingContextDispatcher.dispatchMutation(projectId.toString(), new CreateCommitRestInput(UUID.randomUUID(), branchId)).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof CreateCommitRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.commit(), HttpStatus.OK);
        }
        // The specification does not handle other HttpStatus than HttpStatus.CREATED for this endpoint
        return null;
    }

    @GetMapping(path = "/{commitId}")
    public ResponseEntity<RestCommit> getCommitById(@PathVariable UUID projectId, @PathVariable UUID commitId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetCommitByIdRestInput(UUID.randomUUID(), commitId)).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetCommitByIdRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.commit(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/{commitId}/changes")
    public ResponseEntity<List<RestDataVersion>> getCommitChange(@PathVariable UUID projectId, @PathVariable UUID commitId, @RequestParam Optional<List<ChangeType>> changeTypes) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetCommitChangeRestInput(UUID.randomUUID(), commitId)).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetCommitChangeRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.commitChanges(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/{commitId}/changes/{changeId}")
    public ResponseEntity<RestDataVersion> getCommitChangeById(@PathVariable UUID projectId, @PathVariable UUID commitId, @PathVariable UUID changeId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetCommitChangeByIdRestInput(UUID.randomUUID(), commitId, changeId)).block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetCommitChangeByIdRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.commitChange(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
