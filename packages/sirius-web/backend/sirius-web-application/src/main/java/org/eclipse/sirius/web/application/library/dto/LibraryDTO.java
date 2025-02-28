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
package org.eclipse.sirius.web.application.library.dto;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * The DTO for libraries.
 *
 * @author gdaniel
 */
public record LibraryDTO(
        @NotNull UUID id,
        @NotNull String namespace,
        @NotNull String name,
        @NotNull String version,
        @NotNull String description,
        @NotNull Instant createdOn) {
}
