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
package org.eclipse.sirius.components.collaborative.forms.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;

/**
 * The input object for the checkbox edition mutation.
 *
 * @author sbegaudeau
 */
public final class EditCheckboxInput implements IFormInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private String checkboxId;

    private boolean newValue;

    public EditCheckboxInput() {
        // Used by Jackson
    }

    public EditCheckboxInput(UUID id, String editingContextId, String representationId, String checkboxId, boolean newValue) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.checkboxId = Objects.requireNonNull(checkboxId);
        this.newValue = newValue;
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

    public String getCheckboxId() {
        return this.checkboxId;
    }

    public boolean getNewValue() {
        return this.newValue;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, checkboxId: {4}, newValue: {5}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.checkboxId, this.newValue);
    }
}
