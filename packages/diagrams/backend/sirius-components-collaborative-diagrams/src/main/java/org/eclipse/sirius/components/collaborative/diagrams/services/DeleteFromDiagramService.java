/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.events.RemoveEdgeEvent;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Handle "Delete from Diagram".
 *
 * @author pcdavid
 */
@Service
public class DeleteFromDiagramService implements IDeleteFromDiagramService {

    private final IObjectSearchService objectSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Logger logger = LoggerFactory.getLogger(DeleteFromDiagramService.class);


    public DeleteFromDiagramService(IObjectSearchService objectSearchService, IDiagramQueryService diagramQueryService, IDiagramDescriptionService diagramDescriptionService,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, ICollaborativeDiagramMessageService messageService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);

    }

    @Override
    public IStatus deleteFromDiagram(IEditingContext editingContext, DiagramContext diagramContext, List<String> diagramElementIds, List<ToolVariable> variables) {
        Diagram diagram = diagramContext.diagram();
        List<String> deletedEdgeIds = new ArrayList<>();

        IStatus finalStatus = diagramElementIds.stream()
                .map(diagramElementId -> {
                    IStatus result = new Success();
                    var optionalNodeElement = this.diagramQueryService.findNodeById(diagram, diagramElementId);
                    if (optionalNodeElement.isPresent()) {
                        result = this.invokeDeleteNodeTool(optionalNodeElement.get(), editingContext, diagramContext);
                    } else {
                        var optionalEdgeElement = this.diagramQueryService.findEdgeById(diagram, diagramElementId);
                        if (optionalEdgeElement.isPresent()) {
                            deletedEdgeIds.add(diagramElementId);
                            result = this.invokeDeleteEdgeTool(optionalEdgeElement.get(), editingContext, diagramContext);
                        }
                    }
                    return result;
                })
                .reduce(
                        new Success(),
                        (currentStatus, nextStatus) -> {
                            if (currentStatus instanceof Failure || nextStatus instanceof Failure) {
                                return new Failure("");
                            }
                            return currentStatus;
                        }
                );

        RemoveEdgeEvent removeEdgeEvent = new RemoveEdgeEvent(deletedEdgeIds);
        diagramContext.diagramEvents().add(removeEdgeEvent);

        return finalStatus;
    }


    private IStatus invokeDeleteNodeTool(Node node, IEditingContext editingContext, DiagramContext diagramContext) {
        IStatus result;
        var optionalNodeDescription = this.findNodeDescription(node, diagramContext.diagram(), editingContext);

        if (optionalNodeDescription.isPresent()) {
            var optionalSelf = this.objectSearchService.getObject(editingContext, node.getTargetObjectId());
            if (optionalSelf.isPresent()) {
                var self = optionalSelf.get();
                var variableManager = this.populateVariableManager(editingContext, diagramContext, self, node, null);
                NodeDescription nodeDescription = optionalNodeDescription.get();
                if (nodeDescription.getDeleteHandler() != null) {
                    this.logger.atDebug()
                            .setMessage("Deleted diagram element {}")
                            .addArgument(node.getId())
                            .log();

                    result = nodeDescription.getDeleteHandler().apply(variableManager);
                } else {
                    String message = this.messageService.deleteNodeFailed(node.getTargetObjectId());

                    this.logger.atDebug()
                            .setMessage(message)
                            .log();

                    result = new Failure(message);
                }
            } else {
                result = new Success(); // The node may have been indirectly deleted as a result of a previous deletion
            }
        } else {
            String message = this.messageService.nodeDescriptionNotFound(node.getId());

            this.logger.atDebug()
                    .setMessage(message)
                    .log();

            result = new Failure(message);
        }
        return result;
    }

    private IStatus invokeDeleteEdgeTool(Edge edge, IEditingContext editingContext, DiagramContext diagramContext) {
        IStatus result;
        var optionalEdgeDescription = this.findEdgeDescription(edge, diagramContext.diagram(), editingContext);
        if (optionalEdgeDescription.isPresent()) {
            var optionalSelf = this.objectSearchService.getObject(editingContext, edge.getTargetObjectId());
            if (optionalSelf.isPresent()) {
                var self = optionalSelf.get();
                var variableManager = this.populateVariableManager(editingContext, diagramContext, self, null, edge);
                this.diagramQueryService.findNodeById(diagramContext.diagram(), edge.getSourceId())
                        .flatMap(node -> this.objectSearchService.getObject(editingContext, node.getTargetObjectId()))
                        .ifPresent(semanticElement -> variableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, semanticElement));
                this.diagramQueryService.findNodeById(diagramContext.diagram(), edge.getTargetId())
                        .flatMap(node -> this.objectSearchService.getObject(editingContext, node.getTargetObjectId()))
                        .ifPresent(semanticElement -> variableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, semanticElement));

                EdgeDescription edgeDescription = optionalEdgeDescription.get();

                if (edgeDescription.getDeleteHandler() != null) {
                    this.logger.atDebug()
                            .setMessage("Deleted diagram edge {}")
                            .addArgument(edge.getId())
                            .log();

                    result = edgeDescription.getDeleteHandler().apply(variableManager);
                } else {
                    String message = this.messageService.deleteEdgeFailed(edge.getTargetObjectId());

                    this.logger.atDebug()
                            .setMessage(message)
                            .log();

                    result = new Failure(message);
                }
            } else {
                result = new Success(); // The edge may have been indirectly deleted as a result of a previous deletion
            }
        } else {
            String message = this.messageService.edgeDescriptionNotFound(edge.getId());

            this.logger.atDebug()
                    .setMessage(message)
                    .log();

            result = new Failure(message);
        }
        return result;
    }

    private VariableManager populateVariableManager(IEditingContext editingContext, DiagramContext diagramContext, Object self, Node selectedNode, Edge selectedEdge) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(DiagramContext.DIAGRAM_CONTEXT, diagramContext);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
        variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
        variableManager.put(VariableManager.SELF, self);
        variableManager.put(Node.SELECTED_NODE, selectedNode);
        variableManager.put(Edge.SELECTED_EDGE, selectedEdge);
        return variableManager;
    }

    private Optional<NodeDescription> findNodeDescription(Node node, Diagram diagram, IEditingContext editingContext) {
        return this.representationDescriptionSearchService
                .findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .flatMap(diagramDescription -> this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, node.getDescriptionId()));
    }

    private Optional<EdgeDescription> findEdgeDescription(Edge edge, Diagram diagram, IEditingContext editingContext) {
        return this.representationDescriptionSearchService
                .findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .flatMap(diagramDescription -> this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, edge.getDescriptionId()));
    }
}
