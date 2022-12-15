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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;

/**
 * The input for the reconnect edge mutation.
 *
 * @author gcoutable
 */
public class ReconnectEdgeInput implements IDiagramInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private String edgeId;

    private String newEdgeEndId;

    private ReconnectEdgeKind reconnectEdgeKind;

    private Position newEdgeEndPosition;

    public ReconnectEdgeInput() {
        // Used by Jackson
    }

    public ReconnectEdgeInput(UUID id, String editingContextId, String representationId, String edgeId, String newEdgeEndId, ReconnectEdgeKind reconnectEdgeKind, Position newEdgeEndPosition) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.edgeId = Objects.requireNonNull(edgeId);
        this.newEdgeEndId = Objects.requireNonNull(newEdgeEndId);
        this.reconnectEdgeKind = Objects.requireNonNull(reconnectEdgeKind);
        this.newEdgeEndPosition = Objects.requireNonNull(newEdgeEndPosition);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getRepresentationId() {
        return this.representationId;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public String getEdgeId() {
        return this.edgeId;
    }

    public String getNewEdgeEndId() {
        return this.newEdgeEndId;
    }

    public ReconnectEdgeKind getReconnectEdgeKind() {
        return this.reconnectEdgeKind;
    }

    public Position getNewEdgeEndPosition() {
        return this.newEdgeEndPosition;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId);
    }

}
