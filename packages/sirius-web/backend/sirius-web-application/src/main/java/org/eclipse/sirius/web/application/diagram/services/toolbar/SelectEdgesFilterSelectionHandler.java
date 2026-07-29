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

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.handlers.api.IFilterSelectionHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Edge;
import org.springframework.stereotype.Service;

/**
 * Handler to select the edges from a selection for FilterSelectionMenu in the diagram toolbar.
 *
 * @author mcharfadi
 */
@Service
public class SelectEdgesFilterSelectionHandler implements IFilterSelectionHandler {

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, String toolbarFilterSelectionMenuItemId, List<String> diagramElementIds) {
        return SelectEdgesFilterSelectionProvider.ID.equals(toolbarFilterSelectionMenuItemId);
    }

    @Override
    public List<String> getNewSelection(IEditingContext editingContext, DiagramContext diagramContext, String toolbarFilterSelectionMenuItemId, List<String> diagramElementIds) {
        return diagramContext.diagram().getEdges().stream()
                .map(Edge::getId)
                .toList();
    }
}
