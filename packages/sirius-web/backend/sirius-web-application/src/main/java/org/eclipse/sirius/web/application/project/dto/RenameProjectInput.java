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
package org.eclipse.sirius.web.application.project.dto;

import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

import jakarta.validation.constraints.NotNull;

/**
 * The input used to rename a project.
 *
 * @author sbegaudeau
 */
public record RenameProjectInput(
        @NotNull UUID id,
        @NotNull String projectId,
        @NotNull String newName) implements IInput {
}
