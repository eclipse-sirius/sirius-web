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

import org.eclipse.sirius.web.application.dto.Identified;

/**
 * REST BranchRequest DTO.
 *
 * @author arichard
 */
public record RestBranchRequest(
        @JsonProperty("@type") String type,
        Identified head,
        String name) {

    public RestBranchRequest {
        Objects.requireNonNull(type);
        // head can be null
        Objects.requireNonNull(name);
    }

    public RestBranchRequest(
            Identified head,
            String name) {
        this("Branch", head, name);
    }
}
