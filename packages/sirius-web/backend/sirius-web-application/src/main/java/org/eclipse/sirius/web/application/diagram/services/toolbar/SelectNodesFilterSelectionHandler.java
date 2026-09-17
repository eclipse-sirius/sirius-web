/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.diagram.services.toolbar;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.handlers.api.IDiagramToolbarFilterSelectionHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Node;
import org.springframework.stereotype.Service;

/**
 * Handler to select all nodes for FilterSelectionMenu in the diagram toolbar.
 *
 * @author mcharfadi
 */
@Service
public class SelectNodesFilterSelectionHandler implements IDiagramToolbarFilterSelectionHandler {

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, String toolbarFilterSelectionMenuItemId, List<String> diagramElementIds) {
        return toolbarFilterSelectionMenuItemId.equals(SelectNodesFilterSelectionProvider.ID);
    }

    @Override
    public List<String> getNewSelection(IEditingContext editingContext, DiagramContext diagramContext, String toolbarFilterSelectionMenuItemId, List<String> diagramElementIds) {
        return this.getAllNodesIDs(diagramContext.diagram().getNodes());
    }

    public List<String> getAllNodesIDs(List<Node> nodes) {
        var allNodes = new LinkedList<String>();
        for (Node node : nodes) {
            allNodes.add(node.getId());
            var children = new LinkedList<>(node.getChildNodes());
            allNodes.addAll(this.getAllNodesIDs(children));
        }
        return allNodes;
    }
}
