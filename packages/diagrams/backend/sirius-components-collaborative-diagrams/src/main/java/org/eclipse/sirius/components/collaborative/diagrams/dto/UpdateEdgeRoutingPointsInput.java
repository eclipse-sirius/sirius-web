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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.diagrams.Position;

/**
 * The input for the update edge routing point mutation.
 *
 * @author gcoutable
 */
public class UpdateEdgeRoutingPointsInput implements IDiagramInput {
    private UUID id;

    private String representationId;

    private String editingContextId;

    private String diagramElementId;

    private List<Position> routingPoints;

    public UpdateEdgeRoutingPointsInput() {
        // Used by jackson
    }

    public UpdateEdgeRoutingPointsInput(UUID id, String editingContextId, String representationId, String diagramElementId, List<Position> routingPoints) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.diagramElementId = Objects.requireNonNull(diagramElementId);
        this.routingPoints = Objects.requireNonNull(routingPoints);
    }

    @Override
    public String getRepresentationId() {
        return this.representationId;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public String getDiagramElementId() {
        return this.diagramElementId;
    }

    public List<Position> getRoutingPoints() {
        return this.routingPoints;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, diagramElementId: {4}, routingPoints: {5}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.diagramElementId, this.routingPoints);
    }

}
