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
package org.eclipse.sirius.web.application.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Objects;

import org.eclipse.sirius.web.application.dto.Identified;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * REST Project DTO.
 *
 * @author arichard
 */

@Schema(name = "Project", description = "Project is a subclass of Record that represents a container for other Records and an entry point for version management and data navigation.")
public record RestProject(
        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The UUID assigned to the record")
        @JsonProperty("@id")
        String id,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "Project")
        @JsonProperty("@type")
        String type,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The creation timestamp for the Project, in ISO8601DateTime format")
        OffsetDateTime created,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The default branch in the Project and a subset of branches")
        Identified defaultBranch,

        @Schema(description = "The statement that provides details about the record")
        String description,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The name of the Project")
        String name) {


    public RestProject {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(created);
        Objects.requireNonNull(defaultBranch);
        // description can be null
        Objects.requireNonNull(name);

    }

    public RestProject(
            String id,
            OffsetDateTime created,
            Identified defaultBranch,
            String description,
            String name) {
        this(id, "Project", created, defaultBranch, description, name);
    }
}
