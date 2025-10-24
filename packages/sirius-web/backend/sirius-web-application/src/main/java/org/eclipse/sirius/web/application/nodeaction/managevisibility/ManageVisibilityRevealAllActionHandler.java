/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.nodeaction.managevisibility;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.nodeactions.IManageVisibilityMenuActionHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.springframework.stereotype.Service;

/**
 * Used to provide the reveal all action handler for the manage visibility node action.
 *
 * @author mcharfadi
 */
@Service
public class ManageVisibilityRevealAllActionHandler implements IManageVisibilityMenuActionHandler {

    private final IDiagramQueryService diagramQueryService;

    public ManageVisibilityRevealAllActionHandler(IDiagramQueryService diagramQueryService) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, IDiagramElement diagramElement, String actionId) {
        return actionId.equals(ManageVisibilityRevealAllAction.ACTION_ID);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, DiagramContext diagramContext, IDiagramElement diagramElement, String actionId) {
        Optional<Node> optionalNode = this.diagramQueryService.findNodeById(diagramContext.diagram(), diagramElement.getId());
        if (optionalNode.isPresent()) {
            var childrenIds = optionalNode.get().getChildNodes().stream().map(Node::getId).collect(Collectors.toSet());
            diagramContext.diagramEvents().add(new HideDiagramElementEvent(childrenIds, false));
        }
        return new Success();
    }
}
