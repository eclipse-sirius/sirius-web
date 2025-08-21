/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.providers;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInputReferencePositionProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodeInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropOnDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReferencePosition;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.springframework.stereotype.Service;

/**
 * Provider use to retrieve the reference position for InvokeSingleClickOnDiagramElementToolInput, InvokeSingleClickOnTwoDiagramElementsToolInput
 * DropNodeInput and DropOnDiagramInput input.
 *
 * @author frouene
 */
@Service
public class GenericDiagramToolReferencePositionProvider implements IDiagramInputReferencePositionProvider {

    @Override
    public boolean canHandle(IInput diagramInput) {
        return diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput || diagramInput instanceof DropNodeInput || diagramInput instanceof DropOnDiagramInput || diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput;
    }

    @Override
    public ReferencePosition getReferencePosition(IInput diagramInput, DiagramContext diagramContext) {
        ReferencePosition referencePosition = null;
        if (diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput input) {
            String parentId = this.getParentId(diagramContext, input.diagramElementId());
            referencePosition = new ReferencePosition(parentId, new Position(input.startingPositionX(), input.startingPositionY()), input.getClass().getSimpleName());
        } else if (diagramInput instanceof DropNodeInput input) {
            referencePosition = new ReferencePosition(input.targetElementId(), new Position(input.x(), input.y()), input.getClass().getSimpleName());
        } else if (diagramInput instanceof DropOnDiagramInput input) {
            String parentId = this.getParentId(diagramContext, input.diagramTargetElementId());
            referencePosition = new ReferencePosition(parentId, new Position(input.startingPositionX(), input.startingPositionY()), input.getClass().getSimpleName());
        } else if (diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput input) {
            referencePosition = new ReferencePosition(input.diagramTargetElementId(), new Position(input.targetPositionX(), input.targetPositionY()), input.getClass().getSimpleName());
        }
        return referencePosition;
    }

    private String getParentId(DiagramContext diagramContext, String targetId) {
        String parentId = null;
        if (!diagramContext.diagram().getId().equals(targetId)) {
            parentId = targetId;
        }
        return parentId;
    }
}
