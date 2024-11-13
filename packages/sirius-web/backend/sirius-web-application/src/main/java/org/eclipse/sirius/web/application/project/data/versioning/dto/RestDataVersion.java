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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.application.project.data.versioning.services.RestDataVersionPayloadSerializer;

/**
 * REST DataVersion DTO.
 *
 * @author arichard
 */
public record RestDataVersion(
        @JsonProperty("@id") UUID id,
        @JsonProperty("@type") String type,
        RestDataIdentity identity,
        @JsonSerialize(using = RestDataVersionPayloadSerializer.class)
        Object payload) {

    public RestDataVersion {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(identity);
        // payload can be null
    }

    public RestDataVersion(
            UUID id,
            RestDataIdentity identity,
            Object payload) {
        this(id, "DataVersion", identity, payload);
    }
}
