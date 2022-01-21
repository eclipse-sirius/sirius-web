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

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input of the rename object mutation.
 *
 * @author arichard
 */
public final class RenameObjectInput implements IInput {

    private UUID id;

    private String editingContextId;

    private String objectId;

    private String newName;

    public RenameObjectInput() {
        // Used by Jackson
    }

    public RenameObjectInput(UUID id, String editingContextId, String objectId, String newName) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.objectId = Objects.requireNonNull(objectId);
        this.newName = Objects.requireNonNull(newName);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public String getNewName() {
        return this.newName;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, objectId: {3}, newName: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.objectId, this.newName);
    }
}
