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

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

import jakarta.validation.constraints.NotNull;

/**
 * Input used to create a new project.
 *
 * @author sbegaudeau
 */
public record CreateProjectInput(
        @NotNull UUID id,
        @NotNull String name,
        @NotNull String templateId,
        // TODO: We also have discussion about removing the nature from the input since it is only used in cypress (and playwright tests?)
        //       Maybe it's out of scope
        @NotNull List<String> natures,
        List<String> libraryIds) implements IInput {
}
