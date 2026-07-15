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
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.handlers.api.IFilterSelectionHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Handler to filter out the nodes from a selection for FilterSelectionMenu in the diagram toolbar.
 *
 * @author mcharfadi
 */
@Service
public class UnselectNodesFilterSelectionHandler implements IFilterSelectionHandler {

    private final IDiagramQueryService diagramQueryService;

    public UnselectNodesFilterSelectionHandler(IDiagramQueryService diagramQueryService) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, String toolbarFilterSelectionMenuItemId, List<String> diagramElementIds) {
        return UnselectNodesFilterSelectionProvider.ID.equals(toolbarFilterSelectionMenuItemId);
    }

    @Override
    public List<String> getNewSelection(IEditingContext editingContext, DiagramContext diagramContext, String toolbarFilterSelectionMenuItemId, List<String> diagramElementIds) {
        return diagramElementIds.stream()
                .filter(diagramElementId -> this.diagramQueryService.findNodeById(diagramContext.diagram(), diagramElementId).isEmpty())
                .toList();
    }
}
