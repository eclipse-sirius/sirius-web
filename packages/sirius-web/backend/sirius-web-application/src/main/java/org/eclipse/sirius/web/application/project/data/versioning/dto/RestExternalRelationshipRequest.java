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
 * REST ExternalRelationshipRequest DTO.
 *
 * @author arichard
 */
@Schema(name = "ExternalRelationship", description = """
        ExternalRelationship is a realization of Data, and represents the relationship between a
        KerML Element in a provider tool or repository to ExternalData in another tool or repository.
        The ExternalData may be a KerML Element or a non-KerML Element. A hyperlink between a KerML
        Element to a web resource is the most primitive example of an ExternalRelationship.
        """)
public record RestExternalRelationshipRequest(
        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The UUID assigned to the record")
        @JsonProperty("@id")
        UUID id,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "ExternalRelationship")
        @JsonProperty("@type")
        String type,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The ID of Element of the ExternalRelationship")
        Identified elementEnd,

        @Schema(requiredMode = RequiredMode.REQUIRED, description = "The ID of ExternalData of the ExternalRelationship")
        Identified externalDataEnd,

        @Schema(description = "language is the name of the expression language used for the specification. This is an optional attribute")
        String language,

        @Schema(description = """
                specification is the formal representation of the semantics of the ExternalRelationship. The specification
                can be a collection of mathematical expressions. For example, an ExternalRelationship can be defined to
                map the attributes of a KerML Element to the attributes of an ExternalData. In this case, the specification
                would contain mathematical expressions, such as equations, representing the mapping. This is an optional
                attribute
                """)
        String specification) implements IRestDataRequest {

    public RestExternalRelationshipRequest {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(elementEnd);
        Objects.requireNonNull(externalDataEnd);
        // language can be null
        // specification can be null
    }

    public RestExternalRelationshipRequest(
            UUID id,
            Identified elementEnd,
            Identified externalDataEnd) {
        this(id, "ExternalRelationship", elementEnd, externalDataEnd, null, null);
    }
}
