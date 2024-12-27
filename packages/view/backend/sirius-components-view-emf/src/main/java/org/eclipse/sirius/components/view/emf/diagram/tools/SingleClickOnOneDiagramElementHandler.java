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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.handlers.InvokeSingleClickOnDiagramElementToolEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.services.ISingleClickOnOneDiagramElementHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewToolFinder;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.ISingleClickOnOneDiagramElementVariableManagerProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service to handle diagram tool execution.
 *
 * @author frouene
 */
@Service
public class SingleClickOnOneDiagramElementHandler implements ISingleClickOnOneDiagramElementHandler {

    private final Logger logger = LoggerFactory.getLogger(SingleClickOnOneDiagramElementHandler.class);

    private final ISingleClickOnOneDiagramElementVariableManagerProvider singleClickOnOneDiagramElementVariableManagerProvider;

    private final IDiagramQueryService diagramQueryService;

    private final IToolExecutor toolExecutor;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IViewAQLInterpreterFactory viewAQLInterpreterFactory;

    private final IViewToolFinder viewToolFinder;

    private final IDiagramIdProvider diagramIdProvider;

    public SingleClickOnOneDiagramElementHandler(ISingleClickOnOneDiagramElementVariableManagerProvider singleClickOnOneDiagramElementVariableManagerProvider, IDiagramQueryService diagramQueryService, IToolExecutor toolExecutor, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IViewAQLInterpreterFactory viewAQLInterpreterFactory, IViewToolFinder viewToolFinder, IDiagramIdProvider diagramIdProvider) {
        this.singleClickOnOneDiagramElementVariableManagerProvider = Objects.requireNonNull(singleClickOnOneDiagramElementVariableManagerProvider);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.toolExecutor = Objects.requireNonNull(toolExecutor);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.viewAQLInterpreterFactory = Objects.requireNonNull(viewAQLInterpreterFactory);
        this.viewToolFinder = Objects.requireNonNull(viewToolFinder);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, String toolId, String diagramElementId) {
        var diagram = diagramContext.diagram();
        Optional<String> optionalDiagramElementDescriptionId = Optional.of(diagramElementId)
                .filter(elementId -> diagramElementId.equals(diagram.getId()))
                .map(elementId -> diagram.getDescriptionId())
                .or(() -> this.diagramQueryService.findNodeById(diagram, diagramElementId).map(Node::getDescriptionId))
                .or(() -> this.diagramQueryService.findEdgeById(diagram, diagramElementId).map(Edge::getDescriptionId));

        return optionalDiagramElementDescriptionId
                .filter(diagramElementDescriptionId -> this.viewToolFinder.findNodeTool(editingContext, diagram.getDescriptionId(), diagramElementDescriptionId, toolId).isPresent())
                .isPresent();
    }

    @Override
    public IStatus execute(IEditingContext editingContext, DiagramContext diagramContext, String toolId, String diagramElementId, List<ToolVariable> variables) {
        var diagram = diagramContext.diagram();
        Optional<String> optionalDiagramElementDescriptionId = Optional.of(diagramElementId)
                .filter(elementId -> diagramElementId.equals(diagram.getId()))
                .map(elementId -> diagram.getDescriptionId())
                .or(() -> this.diagramQueryService.findNodeById(diagram, diagramElementId).map(Node::getDescriptionId))
                .or(() -> this.diagramQueryService.findEdgeById(diagram, diagramElementId).map(Edge::getDescriptionId));

        if (optionalDiagramElementDescriptionId.isPresent()) {
            var optionalNodeTool = this.viewToolFinder.findNodeTool(editingContext, diagram.getDescriptionId(), optionalDiagramElementDescriptionId.get(), toolId);
            if (optionalNodeTool.isPresent()) {
                var nodeTool = optionalNodeTool.get();
                return this.executeTool(editingContext, diagramContext, diagram.getDescriptionId(), diagramElementId, nodeTool, variables, toolId);
            }
        }
        return new Failure(String.format("The tool %s cannot be found on the current diagram %s and editing context %s", toolId, diagram.getId(), editingContext.getId()));
    }

    private IStatus executeTool(IEditingContext editingContext, DiagramContext diagramContext, String diagramDescriptionId, String diagramElementId, NodeTool nodeTool, List<ToolVariable> variables, String toolId) {
        IStatus result = new Failure("");
        var optionalViewDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescriptionId);
        if (optionalViewDiagramDescription.isPresent() && optionalViewDiagramDescription.get().eContainer() instanceof View view) {
            AQLInterpreter interpreter = this.viewAQLInterpreterFactory.createInterpreter(editingContext, view);

            var optionalVariableManager = this.singleClickOnOneDiagramElementVariableManagerProvider.getVariableManager(editingContext, diagramContext, diagramElementId, variables);

            if (optionalVariableManager.isPresent()) {
                VariableManager variableManager = optionalVariableManager.get();

                // We do not apply the tool if a dialog is defined but no variables have been provided
                var optionalDialogDescriptionId = Optional.ofNullable(nodeTool.getDialogDescription()).map(this.diagramIdProvider::getId);
                if (optionalDialogDescriptionId.isEmpty() || !variables.isEmpty()) {
                    VariableManager childVariableManager = variableManager.createChild();
                    result = this.toolExecutor.executeTool(nodeTool, interpreter, childVariableManager);
                }
            } else {
                this.logger.warn("The tool {0} cannot be applied on the current diagram {1} and editing context {2}", toolId, diagramContext.diagram().getId(), editingContext.getId());
            }

            if (result instanceof Success success) {
                success.getParameters().put(InvokeSingleClickOnDiagramElementToolEventHandler.VIEW_CREATION_REQUESTS, diagramContext.viewCreationRequests());
                success.getParameters().put(InvokeSingleClickOnDiagramElementToolEventHandler.VIEW_DELETION_REQUESTS, diagramContext.viewDeletionRequests());
                success.getParameters().put(InvokeSingleClickOnDiagramElementToolEventHandler.DIAGRAM_EVENTS, diagramContext.diagramEvents());
            }
        }

        return result;
    }
}
