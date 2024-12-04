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
package org.eclipse.sirius.web.application.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.application.dto.Identified;

/**
 * REST Project DTO.
 *
 * @author arichard
 */

@Schema(name = "Project", description = "Project is a subclass of Record that represents a container for other Records and an entry point for version management and data navigation.")
public record RestProject(
        @Schema(required = true, description = "The UUID assigned to the record")
        @JsonProperty("@id")
        UUID id,

        @JsonProperty("@type")
        String type,

        OffsetDateTime created,

        @Schema(required = true, description = "The default branch in the Project and a subset of branches")
        Identified defaultBranch,

        @Schema(description = "The statement that provides details about the record")
        String description,

        @Schema(required = true, description = "The name of the Project")
        String name) {
            

    public RestProject {
        Objects.requireNonNull(id);
        Objects.requireNonNull(created);
        Objects.requireNonNull(defaultBranch);
        // description can be null
        Objects.requireNonNull(name);

    }

    public RestProject(
            UUID id,
            OffsetDateTime created,
            Identified defaultBranch,
            String description,
            String name) {
        this(id, "Project", created, defaultBranch, description, name);
    }
}
