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

import java.util.Objects;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * REST DataIdentityRequest DTO.
 *
 * @author arichard
 */
@Schema(name = "DataIdentityRequest", description = "DataIdentityRequest is used by DataVersionRequest in CommitRequest in the createCommit POST request.")
public record RestDataIdentityRequest(
        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The UUID assigned to the record")
        @JsonProperty("@id")
        UUID id,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "DataIdentity")
        @JsonProperty("@type")
        String type) {

    public RestDataIdentityRequest {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
    }

    public RestDataIdentityRequest(UUID id) {
        this(id, "DataIdentity");
    }
}
