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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorInput;

/**
 * The input of the rename diagram mutation.
 *
 * @author arichard
 */
public final class RenameFormDescriptionEditorInput implements IFormDescriptionEditorInput {

    private UUID id;

    private String editingContextId;

    private String formDescriptionEditorId;

    private String newLabel;

    public RenameFormDescriptionEditorInput() {
        // Used by Jackson
    }

    public RenameFormDescriptionEditorInput(UUID id, String editingContextId, String formDescriptionEditorId, String newLabel) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.formDescriptionEditorId = Objects.requireNonNull(formDescriptionEditorId);
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
        return this.formDescriptionEditorId;
    }

    public String getNewLabel() {
        return this.newLabel;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, formDescriptionEditorId: {3}, newLabel: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.formDescriptionEditorId, this.newLabel);
    }
}
