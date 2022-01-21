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
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * The input for the "Invoke a node tool on diagram" mutation.
 *
 * @author pcdavid
 */
public final class InvokeNodeToolOnDiagramInput implements IDiagramInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private String diagramElementId;

    private String toolId;

    private double startingPositionX;

    private double startingPositionY;

    private String selectedObjectId;

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

    public String getDiagramElementId() {
        return this.diagramElementId;
    }

    public String getToolId() {
        return this.toolId;
    }

    public double getStartingPositionX() {
        return this.startingPositionX;
    }

    public double getStartingPositionY() {
        return this.startingPositionY;
    }

    public String getSelectedObjectId() {
        return this.selectedObjectId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, diagramElementId: {4}, toolId: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.diagramElementId, this.toolId);
    }

}
