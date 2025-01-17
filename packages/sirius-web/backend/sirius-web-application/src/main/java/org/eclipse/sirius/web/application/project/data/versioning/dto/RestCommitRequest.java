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
package org.eclipse.sirius.web.application.project.data.versioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * REST CommitRequest DTO.
 *
 * @author arichard
 */
@Schema(name = "CommitRequest", description = "CommitRequest is used as the body of the createCommit POST request.")
public record RestCommitRequest(
        @Schema(requiredMode = RequiredMode.REQUIRED, description = "Commit")
        @JsonProperty("@type")
        String type,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = """
                The content of CommitRequest for creating, updating,
                and deleting Data maps directly to the corresponding PIM
                operation (createCommit), and is described below.
                For creating new Data, CommitRequest.change should
                include a DataVersion where DataVersion.payload
                includes the Data being created and
                DataVersion.identity is either not specified or set to a
                new DataIdentity that does not already exist in any of
                the previousCommits.
                For updating existing Data, CommitRequest.change
                should include a DataVersion where
                DataVersion.payload includes the updated Data, and
                DataVersion.identity is the DataIdentity for which a
                new DataVersion will be created in the commit.
                For deleting existing Data, CommitRequest.change
                should include a DataVersion where
                DataVerision.payload is not specified, and
                DataVersion.identity is the DataIdentity that will be
                deleted in the commit.
                """)
        List<RestDataVersionRequest> change,

        @Schema(description = "The statement that provides details about the record")
        String description) {

    public RestCommitRequest {
        Objects.requireNonNull(type);
        Objects.requireNonNull(change);
        // description can be null
    }

    public RestCommitRequest(
            List<RestDataVersionRequest> change,
            String description) {
        this("Commit", change, description);
    }
}
