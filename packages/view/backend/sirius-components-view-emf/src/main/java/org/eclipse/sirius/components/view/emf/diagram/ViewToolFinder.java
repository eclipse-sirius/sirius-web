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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewToolFinder;
import org.springframework.stereotype.Service;

/**
 * Used to find a specific view tool for a specific diagramElementDescription.
 *
 * @author mcharfadi
 */
@Service
public class ViewToolFinder implements IViewToolFinder {

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramIdProvider diagramIdProvider;

    public ViewToolFinder(IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IDiagramIdProvider diagramIdProvider) {
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
    }

    @Override
    public Optional<NodeTool> findNodeTool(IEditingContext editingContext, String diagramDescriptionId, String diagramElementDescriptionId, String toolId) {
        return this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescriptionId)
                .filter(viewDiagramDescription -> this.diagramIdProvider.getId(viewDiagramDescription).equals(diagramDescriptionId))
                .flatMap(viewDiagramDescription -> new ToolFinder().getNodeToolByIdFromDiagramDescription(viewDiagramDescription, toolId))
                .or(() -> this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, diagramElementDescriptionId)
                        .flatMap(viewNodeDescription -> new ToolFinder().getNodeToolByIdFromNodeDescription(viewNodeDescription, toolId)))
                .or(() -> this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, diagramElementDescriptionId)
                        .flatMap(viewEdgeDescription -> new ToolFinder().getNodeToolByIdFromEdgeDescription(viewEdgeDescription, toolId)));
    }

    @Override
    public Optional<EdgeTool> findEdgeTool(IEditingContext editingContext, String diagramDescriptionId, String diagramElementDescriptionId, String toolId) {
        return this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, diagramElementDescriptionId)
                .flatMap(viewNodeDescription -> new ToolFinder().getEdgeToolByIdFromNodeDescription(viewNodeDescription, toolId))
                .or(() -> this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, diagramElementDescriptionId)
                        .flatMap(viewEdgeDescription -> new ToolFinder().getEdgeToolByIdFromEdgeDescription(viewEdgeDescription, toolId)));
    }
}
