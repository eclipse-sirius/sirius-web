/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * The input for the "drop node" mutation.
 *
 * @author pcdavid
 */
public record DropNodeInput(UUID id, String editingContextId, String representationId, String droppedElementId, String targetElementId) implements IDiagramInput {
    public DropNodeInput {
        Objects.requireNonNull(id);
        Objects.requireNonNull(editingContextId);
        Objects.requireNonNull(representationId);
        Objects.requireNonNull(droppedElementId);
        // targetElementId *can* be null when dropping on the diagram's background
    }
}
