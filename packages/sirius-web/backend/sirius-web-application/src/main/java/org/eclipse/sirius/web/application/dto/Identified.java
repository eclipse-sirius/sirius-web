/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Simple record allowing to serialize an object with only its id.
 *
 * @author arichard
 */
@Schema(name = "Identified", description = "Identified represents an Object through its ID and type.")
public record Identified(@JsonProperty("@id") UUID id) {
    public Identified {
        Objects.requireNonNull(id);
    }
}
