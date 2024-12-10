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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.application.dto.Identified;

/**
 * REST Commit DTO.
 *
 * @author arichard
 */
@Schema(name = "Commit")
public record RestCommit(
        @JsonProperty("@id") UUID id,
        @JsonProperty("@type") String type,
        OffsetDateTime created,
        String description,
        Identified owningProject,
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
