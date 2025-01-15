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
package org.eclipse.sirius.components.collaborative.diagrams.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service to handle diagram tool execution.
 *
 * @author frouene
 */
@Service
public class ToolDiagramExecutor implements IToolDiagramExecutor {

    public static final String VIEW_CREATION_REQUESTS = "viewCreationRequests";

    public static final String VIEW_DELETION_REQUESTS = "viewDeletionRequests";

    public static final String DIAGRAM_EVENTS = "diagramEvents";

    private final Logger logger = LoggerFactory.getLogger(ToolDiagramExecutor.class);

    private final IToolService toolService;

    private final IObjectSearchService objectSearchService;

    private final IDiagramQueryService diagramQueryService;

    public ToolDiagramExecutor(IToolService toolService, IObjectSearchService objectSearchService, IDiagramQueryService diagramQueryService) {
        this.toolService = Objects.requireNonNull(toolService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, Diagram diagram, String toolId, String diagramElementId, List<ToolVariable> variables) {

        DiagramContext diagramContext = new DiagramContext(diagram);

        var optionalTool = this.toolService.findToolById(editingContext, diagram, toolId)
                .filter(SingleClickOnDiagramElementTool.class::isInstance)
                .map(SingleClickOnDiagramElementTool.class::cast);

        if (optionalTool.isPresent()) {
            return this.executeTool(editingContext, diagramContext, diagramElementId, optionalTool.get(), variables);
        }

        return new Failure(String.format("The tool %s cannot be found on the current diagram %s and editing context %s", toolId, diagram.getId(), editingContext.getId()));
    }

    private IStatus executeTool(IEditingContext editingContext, IDiagramContext diagramContext, String diagramElementId, SingleClickOnDiagramElementTool tool, List<ToolVariable> variables) {
        IStatus result = new Failure("");
        Diagram diagram = diagramContext.getDiagram();
        Optional<Node> node = this.diagramQueryService.findNodeById(diagram, diagramElementId);
        Optional<Edge> edge = Optional.empty();
        if (node.isEmpty()) {
            // may be the tool applies on an Edge
            edge = this.diagramQueryService.findEdgeById(diagram, diagramElementId);
        }
        Optional<Object> self = this.getCurrentContext(editingContext, diagramElementId, tool, diagram, node, edge);

        // Else, cannot find the node with the given optionalDiagramElementId
        if (self.isPresent()) {
            VariableManager variableManager = this.populateVariableManager(editingContext, diagramContext, node, edge, self);
            var dialogDescriptionId = tool.getDialogDescriptionId();
            variables.forEach(toolVariable -> this.addToolVariablesInVariableManager(toolVariable, editingContext, variableManager));

            //We do not apply the tool if a dialog is defined but no variables have been provided
            if (dialogDescriptionId == null || !variables.isEmpty()) {
                result = tool.getHandler().apply(variableManager);
            }
        }
        if (result instanceof Success success) {
            success.getParameters().put(VIEW_CREATION_REQUESTS, diagramContext.getViewCreationRequests());
            success.getParameters().put(VIEW_DELETION_REQUESTS, diagramContext.getViewDeletionRequests());
            success.getParameters().put(DIAGRAM_EVENTS, diagramContext.getDiagramEvents());
        }
        return result;
    }

    private void addToolVariablesInVariableManager(ToolVariable toolvariable, IEditingContext editingContext, VariableManager variableManager) {
        switch (toolvariable.type()) {
            case STRING -> variableManager.put(toolvariable.name(), toolvariable.value());
            case OBJECT_ID -> {
                var optionalObject = this.objectSearchService.getObject(editingContext, toolvariable.value());
                variableManager.put(toolvariable.name(), optionalObject.orElse(null));
            }
            case OBJECT_ID_ARRAY -> {
                String value = toolvariable.value();
                List<String> objectsIds = List.of(value.split(","));
                List<Object> objects = objectsIds.stream()
                        .map(objectId -> this.objectSearchService.getObject(editingContext, objectId))
                        .map(optionalObject -> optionalObject.orElse(null))
                        .toList();
                variableManager.put(toolvariable.name(), objects);
            }
            default -> {
                //We do nothing, the variable type is not supported
            }
        }
    }

    private Optional<Object> getCurrentContext(IEditingContext editingContext, String diagramElementId, SingleClickOnDiagramElementTool tool, Diagram diagram, Optional<Node> node,
            Optional<Edge> edge) {
        Optional<Object> self = Optional.empty();
        if (node.isPresent()) {
            self = this.objectSearchService.getObject(editingContext, node.get().getTargetObjectId());
        } else if (edge.isPresent()) {
            self = this.objectSearchService.getObject(editingContext, edge.get().getTargetObjectId());
        } else if (Objects.equals(diagram.getId(), diagramElementId)) {
            self = this.objectSearchService.getObject(editingContext, diagram.getTargetObjectId());
        } else {
            this.logger.warn("The tool {0} cannot be applied on the current diagram {1} and editing context {2}", tool.getId(), diagram.getId(), editingContext.getId());
        }
        return self;
    }

    private VariableManager populateVariableManager(IEditingContext editingContext, IDiagramContext diagramContext, Optional<Node> node, Optional<Edge> edge, Optional<Object> self) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
        variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
        variableManager.put(VariableManager.SELF, self.get());
        variableManager.put(Node.SELECTED_NODE, node.orElse(null));
        variableManager.put(Edge.SELECTED_EDGE, edge.orElse(null));
        return variableManager;
    }
}
