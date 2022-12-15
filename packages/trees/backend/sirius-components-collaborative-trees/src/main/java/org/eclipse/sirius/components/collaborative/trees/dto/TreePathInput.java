/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;

/**
 * The input for the treePath query.
 *
 * @author pcdavid
 */
public class TreePathInput implements ITreeInput {
    private final UUID id;

    private final String editingContextId;

    private final String representationId;

    private final List<String> selectionEntryIds;

    public TreePathInput(UUID id, String editingContextId, String representationId, List<String> selectionEntryIds) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.selectionEntryIds = List.copyOf(Objects.requireNonNull(selectionEntryIds));
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

    public List<String> getSelectionEntryIds() {
        return this.selectionEntryIds;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, selectionEntryIds: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.selectionEntryIds);
    }

}
