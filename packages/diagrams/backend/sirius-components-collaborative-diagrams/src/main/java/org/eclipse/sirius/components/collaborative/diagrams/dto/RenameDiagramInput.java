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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * The input of the rename diagram mutation.
 *
 * @author pcdavid
 */
public final class RenameDiagramInput implements IDiagramInput {

    private UUID id;

    private String editingContextId;

    private String diagramId;

    private String newLabel;

    public RenameDiagramInput() {
        // Used by Jackson
    }

    public RenameDiagramInput(UUID id, String editingContextId, String diagramId, String newLabel) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.diagramId = Objects.requireNonNull(diagramId);
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
        return this.diagramId;
    }

    public String getNewLabel() {
        return this.newLabel;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, diagramId: {3}, newLabel: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.diagramId, this.newLabel);
    }
}
