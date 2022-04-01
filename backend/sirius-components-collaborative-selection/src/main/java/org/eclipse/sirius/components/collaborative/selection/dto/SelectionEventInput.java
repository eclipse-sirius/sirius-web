/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.selection.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input of the selection event subscription.
 *
 * @author arichard
 */
public final class SelectionEventInput implements IInput {

    private UUID id;

    private String editingContextId;

    private String selectionId;

    private String targetObjectId;

    public SelectionEventInput() {
        // Used by Jackson
    }

    public SelectionEventInput(UUID id, String editingContextId, String selectionId, String targetObjectId) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.selectionId = Objects.requireNonNull(selectionId);
        this.targetObjectId = Objects.requireNonNull(targetObjectId);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public String getSelectionId() {
        return this.selectionId;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, selectionId: {3}, targetObjectId: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.selectionId, this.targetObjectId);
    }

}
