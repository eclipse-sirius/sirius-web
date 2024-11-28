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

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.application.dto.Identified;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * REST Commit DTO.
 *
 * @author arichard
 */
@Schema(name = "Commit", description = "Commit is a subclass of Record that represents the changes made to a Project at a specific point in time in its lifecycle, such as the creation, update, or deletion of data in a Project. A Project has 0 or more Commits.")
public record RestCommit(
        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The UUID assigned to the record")
        @JsonProperty("@id")
        UUID id,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "Commit")
        @JsonProperty("@type")
        String type,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The timestamp at which the Commit was created, in ISO8601DateTime format")
        OffsetDateTime created,

        @Schema(description = "The statement that provides details about the record")
        String description,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The Project that owns the Commit")
        Identified owningProject,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The set of immediately preceding Commits")
        List<Identified> previousCommits) {

    public RestCommit {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(created);
        // description can be null
        Objects.requireNonNull(owningProject);
        Objects.requireNonNull(previousCommits);
    }

    public RestCommit(
            UUID id,
            OffsetDateTime created,
            String description,
            Identified owningProject,
            List<Identified> previousCommits) {
        this(id, "Commit", created, description, owningProject, previousCommits);
    }
}
