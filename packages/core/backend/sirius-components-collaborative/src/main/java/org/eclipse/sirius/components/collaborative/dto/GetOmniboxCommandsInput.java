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

package org.eclipse.sirius.components.collaborative.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input for the get omnibox commands query.
 *
 * @author gcoutable
 */
public record GetOmniboxCommandsInput(UUID id, String query) implements IInput {
    public GetOmniboxCommandsInput {
        Objects.requireNonNull(id);
        Objects.requireNonNull(query);
    }
}
