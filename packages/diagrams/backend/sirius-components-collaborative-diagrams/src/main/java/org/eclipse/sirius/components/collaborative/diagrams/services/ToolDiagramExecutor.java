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
import org.eclipse.sirius.components.collaborative.diagrams.SingleClickOnDiagramElementToolHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolHandlerProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
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

    private final IObjectSearchService objectSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final List<IToolHandlerProvider> toolHandlerProviders;

    private final IRepresentationDescriptionSearchService diagramDescriptionService;

    public ToolDiagramExecutor(IObjectSearchService objectSearchService, IDiagramQueryService diagramQueryService, List<IToolHandlerProvider> toolHandlerProviders, IRepresentationDescriptionSearchService diagramDescriptionService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.toolHandlerProviders = Objects.requireNonNull(toolHandlerProviders);
        this.diagramDescriptionService = diagramDescriptionService;
    }

    @Override
    public IStatus execute(IEditingContext editingContext, Diagram diagram, String toolId, String diagramElementId, List<ToolVariable> variables) {
        DiagramContext diagramContext = new DiagramContext(diagram);
        Optional<String> optionalDiagramElementDescriptionId = Optional.of(diagramElementId)
            .filter(elementId -> diagramElementId.equals(diagram.getId()))
            .map(elementId -> diagram.getDescriptionId())
            .or(() -> this.diagramQueryService.findNodeById(diagram, diagramElementId).map(Node::getDescriptionId))
            .or(() -> this.diagramQueryService.findEdgeById(diagram, diagramElementId).map(Edge::getDescriptionId));

        var optionalDiagramDescription = this.diagramDescriptionService.findById(editingContext, diagram.getDescriptionId());
        if (optionalDiagramDescription.isPresent() && optionalDiagramDescription.get() instanceof DiagramDescription diagramDescription && optionalDiagramElementDescriptionId.isPresent()) {
            var diagramElementDescriptionId = optionalDiagramElementDescriptionId.get();
            var optionalToolHandler = this.toolHandlerProviders.stream()
                .filter(toolHandlerProvider -> toolHandlerProvider.canHandle(editingContext, diagramDescription, diagramElementDescriptionId, toolId))
                .findFirst()
                .flatMap(toolHandlerProvider -> toolHandlerProvider.getToolHandler(editingContext, diagramDescription, diagramElementDescriptionId, toolId));

            if (optionalToolHandler.isPresent() && optionalToolHandler.get() instanceof SingleClickOnDiagramElementToolHandler toolHandler) {
                return this.executeTool(editingContext, diagramContext, diagramElementId, toolHandler, variables, toolId);
            }
        }
        return new Failure(String.format("The tool %s cannot be found on the current diagram %s and editing context %s", toolId, diagram.getId(), editingContext.getId()));
    }

    private IStatus executeTool(IEditingContext editingContext, DiagramContext diagramContext, String diagramElementId, SingleClickOnDiagramElementToolHandler toolHandler, List<ToolVariable> variables, String toolId) {
        IStatus result = new Failure("");
        Diagram diagram = diagramContext.diagram();
        Optional<Node> node = this.diagramQueryService.findNodeById(diagram, diagramElementId);
        Optional<Edge> edge = Optional.empty();
        if (node.isEmpty()) {
            // may be the tool applies on an Edge
            edge = this.diagramQueryService.findEdgeById(diagram, diagramElementId);
        }
        Optional<Object> self = this.getCurrentContext(editingContext, diagramElementId, diagram, node, edge, toolId);

        // Else, cannot find the node with the given optionalDiagramElementId
        if (self.isPresent()) {
            VariableManager variableManager = this.populateVariableManager(editingContext, diagramContext, node, edge, self);
            variables.forEach(toolVariable -> this.addToolVariablesInVariableManager(toolVariable, editingContext, variableManager));

            //We do not apply the tool if a dialog is defined but no variables have been provided
            if (toolHandler.dialogDescriptionId().isEmpty() || !variables.isEmpty()) {
                result = toolHandler.handler().apply(variableManager);
            }

        }
        if (result instanceof Success success) {
            success.getParameters().put(VIEW_CREATION_REQUESTS, diagramContext.viewCreationRequests());
            success.getParameters().put(VIEW_DELETION_REQUESTS, diagramContext.viewDeletionRequests());
            success.getParameters().put(DIAGRAM_EVENTS, diagramContext.diagramEvents());
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

    private Optional<Object> getCurrentContext(IEditingContext editingContext, String diagramElementId, Diagram diagram, Optional<Node> node,
            Optional<Edge> edge, String toolId) {
        Optional<Object> self = Optional.empty();
        if (node.isPresent()) {
            self = this.objectSearchService.getObject(editingContext, node.get().getTargetObjectId());
        } else if (edge.isPresent()) {
            self = this.objectSearchService.getObject(editingContext, edge.get().getTargetObjectId());
        } else if (Objects.equals(diagram.getId(), diagramElementId)) {
            self = this.objectSearchService.getObject(editingContext, diagram.getTargetObjectId());
        } else {
            this.logger.warn("The tool {0} cannot be applied on the current diagram {1} and editing context {2}", toolId, diagram.getId(), editingContext.getId());
        }
        return self;
    }

    private VariableManager populateVariableManager(IEditingContext editingContext, DiagramContext diagramContext, Optional<Node> node, Optional<Edge> edge, Optional<Object> self) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(DiagramContext.DIAGRAM_CONTEXT, diagramContext);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
        variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
        variableManager.put(VariableManager.SELF, self.get());
        variableManager.put(Node.SELECTED_NODE, node.orElse(null));
        variableManager.put(Edge.SELECTED_EDGE, edge.orElse(null));
        return variableManager;
    }
}
