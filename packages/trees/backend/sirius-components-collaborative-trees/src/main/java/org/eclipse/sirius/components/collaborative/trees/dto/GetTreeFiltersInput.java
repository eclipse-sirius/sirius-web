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
package org.eclipse.sirius.components.collaborative.trees.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;

/**
 * The input to get the filters of a tree.
 *
 * @author gdaniel
 */
public record GetTreeFiltersInput(UUID id, String editingContextId, String representationId) implements ITreeInput {

    public GetTreeFiltersInput {
        Objects.requireNonNull(id);
        Objects.requireNonNull(editingContextId);
        Objects.requireNonNull(representationId);
    }
}
