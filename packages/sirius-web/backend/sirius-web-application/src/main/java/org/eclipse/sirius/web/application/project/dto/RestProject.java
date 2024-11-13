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

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.application.dto.Identified;

/**
 * REST Project DTO.
 *
 * @author arichard
 */
public record RestProject(
        @JsonProperty("@id") UUID id,
        @JsonProperty("@type") String type,
        OffsetDateTime created,
        Identified defaultBranch,
        String description,
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
