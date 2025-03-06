/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.web.papaya.services.library;

import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;

import jakarta.validation.constraints.NotNull;

/**
 * Command used to request the publication of the standard library.
 *
 * @author sbegaudeau
 */
public record PublishPapayaLibraryCommand(
        @NotNull UUID id,
        @NotNull String namespace,
        @NotNull String name,
        @NotNull String version,
        @NotNull String description) implements ICause {
}
