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
 * The input of the delete document mutation.
 *
 * @author sbegaudeau
 */
public final class DeleteDocumentInput implements IInput {
    private UUID id;

    private UUID documentId;

    public DeleteDocumentInput() {
        // Used by Jackson
    }

    public DeleteDocumentInput(UUID id, UUID documentId) {
        this.id = Objects.requireNonNull(id);
        this.documentId = Objects.requireNonNull(documentId);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public UUID getDocumentId() {
        return this.documentId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, documentId: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.documentId);
    }
}
