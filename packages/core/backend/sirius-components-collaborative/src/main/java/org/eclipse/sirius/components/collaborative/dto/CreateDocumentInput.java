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
 * The input object of the create document mutation.
 *
 * @author sbegaudeau
 */
public final class CreateDocumentInput implements IInput {
    private UUID id;

    private String editingContextId;

    private String name;

    private UUID stereotypeDescriptionId;

    public CreateDocumentInput() {
        // Used by Jackson
    }

    public CreateDocumentInput(UUID id, String editingContextId, String name, UUID stereotypeDescriptionId) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.name = Objects.requireNonNull(name);
        this.stereotypeDescriptionId = Objects.requireNonNull(stereotypeDescriptionId);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public String getName() {
        return this.name;
    }

    public UUID getStereotypeDescriptionId() {
        return this.stereotypeDescriptionId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, name: {3}, stereotypeDescriptionId: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.name, this.stereotypeDescriptionId);
    }
}
