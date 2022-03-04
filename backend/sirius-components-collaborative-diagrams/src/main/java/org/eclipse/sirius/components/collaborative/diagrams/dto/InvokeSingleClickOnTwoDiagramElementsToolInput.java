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
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * The input for the "Invoke single click on two diagram elements tool" mutation.
 *
 * @author pcdavid
 * @author hmarchadour
 */
public final class InvokeSingleClickOnTwoDiagramElementsToolInput implements IDiagramInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private String diagramSourceElementId;

    private String diagramTargetElementId;

    private double sourcePositionX;

    private double sourcePositionY;

    private double targetPositionX;

    private double targetPositionY;

    private String toolId;

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

    public String getDiagramSourceElementId() {
        return this.diagramSourceElementId;
    }

    public String getDiagramTargetElementId() {
        return this.diagramTargetElementId;
    }

    public String getToolId() {
        return this.toolId;
    }

    public double getSourcePositionX() {
        return this.sourcePositionX;
    }

    public double getSourcePositionY() {
        return this.sourcePositionY;
    }

    public double getTargetPositionX() {
        return this.targetPositionX;
    }

    public double getTargetPositionY() {
        return this.targetPositionY;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, diagramSourceElementId: {4}, diagramTargetElementId: {5}, toolId: {6}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.diagramSourceElementId, this.diagramTargetElementId,
                this.toolId);
    }
}
