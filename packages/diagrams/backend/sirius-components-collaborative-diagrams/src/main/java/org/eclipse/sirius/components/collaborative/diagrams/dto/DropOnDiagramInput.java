/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * The input for the "drop on diagram" mutation.
 *
 * @author hmarchadour
 */
public final class DropOnDiagramInput implements IDiagramInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private String diagramTargetElementId;

    private List<String> objectIds;

    private double startingPositionX;

    private double startingPositionY;

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

    public String getDiagramTargetElementId() {
        return this.diagramTargetElementId;
    }

    public List<String> getObjectIds() {
        return this.objectIds;
    }

    public double getStartingPositionX() {
        return this.startingPositionX;
    }

    public double getStartingPositionY() {
        return this.startingPositionY;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, diagramTargetElementId: {3}, representationId: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.diagramTargetElementId, this.representationId);
    }
}
