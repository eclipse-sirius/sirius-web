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
package org.eclipse.sirius.web.application.project.data.versioning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.application.dto.Identified;

/**
 * REST Branch DTO.
 *
 * @author arichard
 */
@Schema(name = "Branch", description = "Branch is an indirect subclass of Record (via CommitReference) that represents an independent line of development in a Project. A Project can have 1 or more Branches. When a Project is created, a default Branch is also created. The default Branch of a Project can be changed, and a Project can have only 1 default Branch.")
public record RestBranch(
        @Schema(required = true, description = "The UUID assigned to the record")
        @JsonProperty("@id") 
        UUID id,

        @JsonProperty("@type")
        String type,

        @Schema(required = true, description = "The timestamp at which the CommitReference was created")
        OffsetDateTime created,

        @Schema(description = "The Commit to which the Branch is currently pointing. It represents the latest state of the Project on the given Branch.")
        Identified head,

        @Schema(required = true, description = "The name of the Branch")
        String name,

        @Schema(required = true, description = "The Project that owns the given CommitReference")
        Identified owningProject,

        @Schema(required = true, description = "The commit referenced by the Branch")
        Identified referencedCommit) {

    public RestBranch {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(created);
        // head can be null
        Objects.requireNonNull(name);
        Objects.requireNonNull(owningProject);
        Objects.requireNonNull(referencedCommit);
    }

    public RestBranch(
            UUID id,
            OffsetDateTime created,
            Identified head,
            String name,
            Identified owningProject,
            Identified referencedCommit) {
        this(id, "Branch", created, head, name, owningProject, referencedCommit);
    }
}
