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
package org.eclipse.sirius.web.application.studio.services.library;

import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;

import jakarta.validation.constraints.NotNull;

/**
 * Used to indicate that the creation of a library has been requested.
 *
 * @author sbegaudeau
 */
public record StudioLibrarySemanticDataCreationRequested(
        @NotNull UUID id,
        @NotNull ICause causedBy,
        @NotNull String libraryName) implements ICause {
}
