/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IRepresentationInput;

/**
 * The input of the rename representation mutation.
 *
 * @author arichard
 */
public final class RenameRepresentationInput implements IRepresentationInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private String newLabel;

    public RenameRepresentationInput() {
        // Used by Jackson
    }

    public RenameRepresentationInput(UUID id, String editingContextId, String representationId, String newLabel) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.newLabel = Objects.requireNonNull(newLabel);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    @Override
    public String getRepresentationId() {
        return this.representationId;
    }

    public String getNewLabel() {
        return this.newLabel;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, newLabel: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.newLabel);
    }
}
