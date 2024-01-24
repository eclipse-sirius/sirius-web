/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInputReferencePositionProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodeInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropOnDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReferencePosition;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.springframework.stereotype.Service;

/**
 * Provider use to retrieve the reference position for InvokeSingleClickOnDiagramElementToolInput, DropNodeInput and DropOnDiagramInput input.
 *
 * @author frouene
 */
@Service
public class GenericDiagramToolReferencePositionProvider implements IDiagramInputReferencePositionProvider {

    @Override
    public boolean canHandle(IInput diagramInput) {
        return diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput || diagramInput instanceof DropNodeInput || diagramInput instanceof DropOnDiagramInput;
    }

    @Override
    public ReferencePosition getReferencePosition(IInput diagramInput, IDiagramContext diagramContext) {
        ReferencePosition referencePosition = null;
        if (diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput input) {
            String parentId = this.getParentId(diagramContext, input.diagramElementId());
            referencePosition = new ReferencePosition(parentId, new Position(input.startingPositionX(), input.startingPositionY()));
        } else if (diagramInput instanceof DropNodeInput input) {
            referencePosition = new ReferencePosition(input.targetElementId(), new Position(input.x(), input.y()));
        } else if (diagramInput instanceof DropOnDiagramInput input) {
            String parentId = this.getParentId(diagramContext, input.diagramTargetElementId());
            referencePosition = new ReferencePosition(parentId, new Position(input.startingPositionX(), input.startingPositionY()));
        }
        return referencePosition;
    }

    private String getParentId(IDiagramContext diagramContext, String targetId) {
        String parentId = null;
        if (!diagramContext.getDiagram().getId().equals(targetId)) {
            parentId = targetId;
        }
        return parentId;
    }
}
