/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.services.ISingleClickOnTwoDiagramElementHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewToolFinder;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.ISingleClickOnTwoDiagramElementsVariableManagerProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolExecutor;
import org.springframework.stereotype.Service;

/**
 * Service to handle diagram connector tool execution.
 *
 * @author mcharfadi
 */
@Service
public class SingleClickOnTwoDiagramElementHandler implements ISingleClickOnTwoDiagramElementHandler {

    private final ISingleClickOnTwoDiagramElementsVariableManagerProvider singleClickOnTwoDiagramElementsVariableManagerProvider;

    private final IDiagramQueryService diagramQueryService;

    private final IToolExecutor toolExecutor;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final IViewToolFinder viewToolFinder;

    public SingleClickOnTwoDiagramElementHandler(ISingleClickOnTwoDiagramElementsVariableManagerProvider singleClickOnTwoDiagramElementsVariableManagerProvider, IDiagramQueryService diagramQueryService, IToolExecutor toolExecutor, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IViewAQLInterpreterFactory aqlInterpreterFactory, IViewToolFinder viewToolFinder) {
        this.singleClickOnTwoDiagramElementsVariableManagerProvider = Objects.requireNonNull(singleClickOnTwoDiagramElementsVariableManagerProvider);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.toolExecutor = Objects.requireNonNull(toolExecutor);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.viewToolFinder = Objects.requireNonNull(viewToolFinder);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, Diagram diagram, String toolId, String sourceDiagramElementId, String targetDiagramElementId) {
        Optional<String> optionalDiagramElementDescriptionId = Optional.of(sourceDiagramElementId)
                .filter(elementId -> sourceDiagramElementId.equals(diagram.getId()))
                .map(elementId -> diagram.getDescriptionId())
                .or(() -> this.diagramQueryService.findNodeById(diagram, sourceDiagramElementId).map(Node::getDescriptionId))
                .or(() -> this.diagramQueryService.findEdgeById(diagram, sourceDiagramElementId).map(Edge::getDescriptionId));

        if (optionalDiagramElementDescriptionId.isPresent()) {
            var optionalEdgeTool = this.viewToolFinder.findEdgeTool(editingContext, diagram.getDescriptionId(), optionalDiagramElementDescriptionId.get(), toolId);
            return optionalEdgeTool.isPresent();
        }
        return false;
    }

    @Override
    public IStatus execute(IEditingContext editingContext, Diagram diagram, String toolId, String sourceDiagramElementId, String targetDiagramElementId, List<ToolVariable> variables) {
        IStatus result = new Failure("");
        DiagramContext diagramContext = new DiagramContext(diagram);

        Optional<String> optionalDiagramElementDescriptionId = Optional.of(sourceDiagramElementId)
                .filter(elementId -> sourceDiagramElementId.equals(diagram.getId()))
                .map(elementId -> diagram.getDescriptionId())
                .or(() -> this.diagramQueryService.findNodeById(diagram, sourceDiagramElementId).map(Node::getDescriptionId))
                .or(() -> this.diagramQueryService.findEdgeById(diagram, sourceDiagramElementId).map(Edge::getDescriptionId));

        if (optionalDiagramElementDescriptionId.isPresent()) {
            var optionalEdgeTool = this.viewToolFinder.findEdgeTool(editingContext, diagram.getDescriptionId(), optionalDiagramElementDescriptionId.get(), toolId);
            if (optionalEdgeTool.isPresent()) {
                result = this.executeTool(editingContext, diagramContext, sourceDiagramElementId, targetDiagramElementId, variables, optionalEdgeTool.get());
            }
        }
        return result;
    }

    private IStatus executeTool(IEditingContext editingContext, DiagramContext diagramContext, String sourceDiagramElementId, String targetDiagramElementId, List<ToolVariable> variables, EdgeTool edgeTool) {
        String diagramDescriptionId = diagramContext.diagram().getDescriptionId();
        var optionalViewDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescriptionId);
        if (optionalViewDiagramDescription.isPresent() && optionalViewDiagramDescription.get().eContainer() instanceof View view) {
            AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, view);

            var optionalVariableManager = this.singleClickOnTwoDiagramElementsVariableManagerProvider.getVariableManager(editingContext, diagramContext, sourceDiagramElementId, targetDiagramElementId, variables);
            if (optionalVariableManager.isPresent()) {
                VariableManager childVariableManager = optionalVariableManager.get().createChild();
                return this.toolExecutor.executeTool(edgeTool, interpreter, childVariableManager);
            }
        }
        return new Failure("");
    }

}
