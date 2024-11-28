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
 * REST ExternalDataRequest DTO.
 *
 * @author arichard
 */
@Schema(name = "ExternalRelationship", description = """
        ExternalData is a realization of Data, and represents a resource external to a given tool or
        repository. ExternalData is defined only for the purpose of defining an ExternalRelationship.
        """)
public record RestExternalDataRequest(
        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The UUID assigned to the record")
        @JsonProperty("@id")
        UUID id,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "ExternalData")
        @JsonProperty("@type")
        String type,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The URI of the resource represented by the ExternalData")
        String resourceIdentifier) implements IRestDataRequest {

    public RestExternalDataRequest {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(resourceIdentifier);
    }

    public RestExternalDataRequest(
            UUID id,
            String resourceIdentifier) {
        this(id, "ExternalData", resourceIdentifier);
    }
}
