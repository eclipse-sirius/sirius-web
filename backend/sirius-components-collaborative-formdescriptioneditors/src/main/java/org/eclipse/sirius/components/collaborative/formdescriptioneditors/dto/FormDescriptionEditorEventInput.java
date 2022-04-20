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

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input of the form description editor event subscription.
 *
 * @author arichard
 */
public final class FormDescriptionEditorEventInput implements IInput {
    private UUID id;

    private String editingContextId;

    private String formDescriptionEditorId;

    public FormDescriptionEditorEventInput() {
        // Used by Jackson
    }

    public FormDescriptionEditorEventInput(UUID id, String editingContextId, String formDescriptionEditorId) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.formDescriptionEditorId = Objects.requireNonNull(formDescriptionEditorId);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public String getFormDescriptionEditorId() {
        return this.formDescriptionEditorId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, formDescriptionEditorId: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.editingContextId, this.formDescriptionEditorId);
    }
}
