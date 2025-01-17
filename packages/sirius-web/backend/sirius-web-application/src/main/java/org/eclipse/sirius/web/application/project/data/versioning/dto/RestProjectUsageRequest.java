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

import org.eclipse.sirius.web.application.dto.Identified;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * REST ProjectUsageRequest DTO.
 *
 * @author arichard
 */
@Schema(name = "ProjectUsageRequest", description = "Project Usage is a subclass of Record that represents the use of a Project in the context of another Project. Project Usage is represented as a realization of Data")
public record RestProjectUsageRequest(
        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The UUID assigned to the record")
        @JsonProperty("@id")
        UUID id,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "ProjectUsage")
        @JsonProperty("@type")
        String type,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "usedCommit references the Commit of the Project being used")
        Identified usedCommit,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "usedProject references the Project being used")
        Identified usedProject) implements IRestDataRequest {

    public RestProjectUsageRequest {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(usedCommit);
        Objects.requireNonNull(usedProject);
    }

    public RestProjectUsageRequest(
            UUID id,
            Identified usedCommit,
            Identified usedProject) {
        this(id, "ProjectUsage", usedCommit, usedProject);
    }
}
