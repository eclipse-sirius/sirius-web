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

import java.util.Objects;
import java.util.UUID;

/**
 * REST DataIdentity DTO.
 *
 * @author arichard
 */
@Schema(name = "DataIdentity", description = "DataIdentity is a subclass of Record that represents a unique, version-independent representation of Data through its lifecycle. A DataIdentity is associated with 1 or more DataVersion records that represent different versions of the same Data.")
public record RestDataIdentity(

        @Schema(required = true, description = "The UUID assigned to the record")
        @JsonProperty("@id")
        UUID id,
        
        @JsonProperty("@type") String type) {

    public RestDataIdentity {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
    }

    public RestDataIdentity(UUID id) {
        this(id, "DataIdentity");
    }
}
