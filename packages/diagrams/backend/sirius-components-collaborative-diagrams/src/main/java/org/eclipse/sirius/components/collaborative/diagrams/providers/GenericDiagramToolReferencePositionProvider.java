/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import java.util.List;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInputReferencePositionProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodesInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropOnDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReconnectEdgeInput;
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

    private static final Set<Class<? extends IInput>> HANDLED_INPUT_TYPES = Set.of(
            InvokeSingleClickOnDiagramElementToolInput.class,
            DropNodesInput.class,
            DropOnDiagramInput.class,
            InvokeSingleClickOnTwoDiagramElementsToolInput.class,
            ReconnectEdgeInput.class
    );

    @Override
    public boolean canHandle(IInput input) {
        return HANDLED_INPUT_TYPES.stream().anyMatch(type -> type.isInstance(input));

    }

    @Override
    public ReferencePosition getReferencePosition(IInput diagramInput, DiagramContext diagramContext) {
        ReferencePosition referencePosition = null;

        if (diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput input) {
            referencePosition = this.handleInvokeSingleClickOnDiagramElement(input, diagramContext);
        } else if (diagramInput instanceof DropNodesInput input) {
            referencePosition = this.handleDropNodes(input);
        } else if (diagramInput instanceof DropOnDiagramInput input) {
            referencePosition = this.handleDropOnDiagram(input, diagramContext);
        } else if (diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput input) {
            referencePosition = this.handleInvokeSingleClickOnTwoDiagramElements(input);
        } else if (diagramInput instanceof ReconnectEdgeInput input) {
            referencePosition = this.handleReconnectEdge(input);
        }

        return referencePosition;
    }

    private ReferencePosition handleInvokeSingleClickOnDiagramElement(InvokeSingleClickOnDiagramElementToolInput input, DiagramContext diagramContext) {
        if (input.diagramElementIds().isEmpty()) {
            return null;
        }
        String parentId = this.getParentId(diagramContext, input.diagramElementIds().get(0));
        return new ReferencePosition(parentId, List.of(new Position(input.startingPositionX(), input.startingPositionY())), input.getClass().getSimpleName());
    }

    private ReferencePosition handleDropNodes(DropNodesInput input) {
        return new ReferencePosition(input.targetElementId(), input.dropPositions(), input.getClass().getSimpleName());
    }

    private ReferencePosition handleDropOnDiagram(DropOnDiagramInput input, DiagramContext diagramContext) {
        String parentId = this.getParentId(diagramContext, input.diagramTargetElementId());
        return new ReferencePosition(parentId, List.of(new Position(input.startingPositionX(), input.startingPositionY())), input.getClass().getSimpleName());
    }

    private ReferencePosition handleInvokeSingleClickOnTwoDiagramElements(InvokeSingleClickOnTwoDiagramElementsToolInput input) {
        if (input.targetPositionX() == 0 && input.targetPositionY() == 0) {
            return null;
        }
        var position = new Position(input.targetPositionX(), input.targetPositionY());
        return new ReferencePosition(input.diagramTargetElementId(), List.of(position), input.getClass().getSimpleName());
    }

    private ReferencePosition handleReconnectEdge(ReconnectEdgeInput input) {
        if (input.targetPositionX() == 0 && input.targetPositionY() == 0) {
            return null;
        }
        var position = new Position(input.targetPositionX(), input.targetPositionY());
        return new ReferencePosition(input.newEdgeEndId(), List.of(position), input.getClass().getSimpleName());
    }

    private String getParentId(DiagramContext diagramContext, String targetId) {
        String parentId = null;
        if (!diagramContext.diagram().getId().equals(targetId)) {
            parentId = targetId;
        }
        return parentId;
    }
}
