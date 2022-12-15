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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * The class of the inputs for the "Delete from diagram" mutation.
 *
 * @author pcdavid
 * @author hmarchadour
 */
public final class DeleteFromDiagramInput implements IDiagramInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private List<String> nodeIds;

    private List<String> edgeIds;

    private DeletionPolicy deletionPolicy;

    public DeleteFromDiagramInput() {
        // Used by Jackson
    }

    public DeleteFromDiagramInput(UUID id, String editingContextId, String representationId, List<String> nodeIds, List<String> edgeIds, DeletionPolicy deletionPolicy) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.nodeIds = Objects.requireNonNull(nodeIds);
        this.edgeIds = Objects.requireNonNull(edgeIds);
        this.deletionPolicy = Objects.requireNonNull(deletionPolicy);
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

    public List<String> getNodeIds() {
        return this.nodeIds;
    }

    public List<String> getEdgeIds() {
        return this.edgeIds;
    }

    public DeletionPolicy getDeletionPolicy() {
        return this.deletionPolicy;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, deletionPolicy: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.deletionPolicy);
    }
}
