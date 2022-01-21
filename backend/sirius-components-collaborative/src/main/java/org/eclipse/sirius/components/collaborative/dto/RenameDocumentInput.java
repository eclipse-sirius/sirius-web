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
 * The input of the rename document mutation.
 *
 * @author fbarbin
 */
public final class RenameDocumentInput implements IInput {
    private UUID id;

    private UUID documentId;

    private String newName;

    public RenameDocumentInput() {
        // Used by Jackson
    }

    public RenameDocumentInput(UUID id, UUID documentId, String newName) {
        this.id = Objects.requireNonNull(id);
        this.documentId = Objects.requireNonNull(documentId);
        this.newName = Objects.requireNonNull(newName);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public UUID getDocumentId() {
        return this.documentId;
    }

    public String getNewName() {
        return this.newName;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, documentId: {2}, newName: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.documentId, this.newName);
    }
}
