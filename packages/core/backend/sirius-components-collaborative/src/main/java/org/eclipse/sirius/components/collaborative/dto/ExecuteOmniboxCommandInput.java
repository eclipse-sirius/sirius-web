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
package org.eclipse.sirius.components.collaborative.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input object of the execute command mutation.
 *
 * @author gdaniel
 */
public record ExecuteOmniboxCommandInput(UUID id, String editingContextId, List<String> selectedObjectIds, String commandId) implements IInput {

    public ExecuteOmniboxCommandInput {
        Objects.requireNonNull(id);
        Objects.requireNonNull(editingContextId);
        Objects.requireNonNull(selectedObjectIds);
        Objects.requireNonNull(commandId);
    }
}
