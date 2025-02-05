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

import java.util.Map;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * REST DataVersionRequest DTO.
 *
 * @author arichard
 */
@Schema(name = "DataVersionRequest", description = "DataVersionRequest is used by CommitRequest in the createCommit POST request.")
public record RestDataVersionRequest(
        @Schema(requiredMode = RequiredMode.REQUIRED, description = "DataVersion")
        @JsonProperty("@type")
        String type,

        @Schema(description = """
                DataVersion.identity is unique among records listed in
                Commit.versionedData and in Commit.change.
                DataVersion.identity is either left empty, in which case
                a new DataIdentity needs to be created by the Service
                and assigned to DataVersion.identity in the new
                commit; or provided a brand new value (one that does
                not already exist in any of the previousCommits) by the
                client and accepted by the Service as is
                """)
        RestDataIdentityRequest identity,

        @Schema(description = "DataVersion.payload should be populated with the Data being created")
        Map<String, Object> payload) {

    public RestDataVersionRequest {
        Objects.requireNonNull(type);
        // identity can be null
        // payload can be null
    }

    public RestDataVersionRequest(
            RestDataIdentityRequest identity,
            Map<String, Object> payload) {
        this("DataVersion", identity, payload);
    }
}
