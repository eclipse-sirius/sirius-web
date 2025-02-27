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
package org.eclipse.sirius.web.application.project.data.versioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * REST DataVersion DTO.
 *
 * @author arichard
 */
@Schema(name = "DataVersion", description = "DataVersion is a subclass of Record that represents Data at a specific version in its lifecycle. A DataVersion record is associated with only one DataIdentity record. DataVersion serves as a wrapper for Data (payload) in the context of a Commit in a Project.")
public record RestDataVersion(

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The UUID assigned to the record")
        @JsonProperty("@id")
        UUID id,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "DataVersion")
        @JsonProperty("@type")
        String type,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The Data Identity common to all versions of the same Data")
        RestDataIdentity identity,

        @Schema(description = "Data if exists in the commit, null otherwise")
        Map<String, Object> payload) {

    public RestDataVersion {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        // identity cannot be null in the specification but can be null in the description of createCommit
        // payload can be null
    }

    public RestDataVersion(
            UUID id,
            RestDataIdentity identity,
            Map<String, Object> payload) {
        this(id, "DataVersion", identity, payload);
    }
}
