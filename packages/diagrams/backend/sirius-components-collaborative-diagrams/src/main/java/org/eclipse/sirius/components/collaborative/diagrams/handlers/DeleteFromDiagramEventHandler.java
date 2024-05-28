/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeletionPolicy;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
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
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Delete from Diagram" events.
 *
 * @author pcdavid
 */
@Service
public class DeleteFromDiagramEventHandler implements IDiagramEventHandler {

    public static final String DELETION_POLICY = "deletionPolicy";

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final IFeedbackMessageService feedbackMessageService;

    private final Logger logger = LoggerFactory.getLogger(DeleteFromDiagramEventHandler.class);

    private final Counter counter;

    public DeleteFromDiagramEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IDiagramDescriptionService diagramDescriptionService,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, ICollaborativeDiagramMessageService messageService, IFeedbackMessageService feedbackMessageService, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof DeleteFromDiagramInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        if (diagramInput instanceof DeleteFromDiagramInput deleteFromDiagramInput) {
            this.handleDelete(payloadSink, changeDescriptionSink, editingContext, diagramContext, deleteFromDiagramInput);
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), DeleteFromDiagramInput.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(diagramInput.id(), message));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput));
        }
    }

    private void handleDelete(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
            DeleteFromDiagramInput diagramInput) {
        List<Message> errors = new ArrayList<>();
        boolean atLeastOneOk = false;
        Diagram diagram = diagramContext.getDiagram();
        List<String> deletedEdgeIds = new ArrayList<>();
        for (String edgeId : diagramInput.edgeIds()) {
            var optionalElement = this.diagramQueryService.findEdgeById(diagram, edgeId);
            if (optionalElement.isPresent()) {
                IStatus status = this.invokeDeleteEdgeTool(optionalElement.get(), editingContext, diagramContext, diagramInput.deletionPolicy());
                if (status instanceof Success) {
                    deletedEdgeIds.add(edgeId);
                    atLeastOneOk = true;
                }
                if (status instanceof Failure failure) {
                    errors.addAll(failure.getMessages());
                }
            } else {
                errors.add(new Message(this.messageService.edgeNotFound(edgeId), MessageLevel.ERROR));
            }
        }
        for (String nodeId : diagramInput.nodeIds()) {
            var optionalElement = this.diagramQueryService.findNodeById(diagram, nodeId);
            if (optionalElement.isPresent()) {
                IStatus status = this.invokeDeleteNodeTool(optionalElement.get(), editingContext, diagramContext, diagramInput.deletionPolicy());
                if (status instanceof Success) {
                    atLeastOneOk = true;
                }
                if (status instanceof Failure failure) {
                    errors.addAll(failure.getMessages());
                }
            } else {
                errors.add(new Message(this.messageService.nodeNotFound(nodeId), MessageLevel.ERROR));
            }
        }

        RemoveEdgeEvent removeEdgeEvent = new RemoveEdgeEvent(deletedEdgeIds);
        diagramContext.getDiagramEvents().add(removeEdgeEvent);
        this.sendResponse(payloadSink, changeDescriptionSink, errors, atLeastOneOk, diagramContext, diagramInput);
    }

    private void sendResponse(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, List<Message> errors, boolean atLeastOneSuccess,
            IDiagramContext diagramContext,
            DeleteFromDiagramInput diagramInput) {

        var changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
        IPayload payload = new DeleteFromDiagramSuccessPayload(diagramInput.id(), diagramContext.getDiagram(), this.feedbackMessageService.getFeedbackMessages());
        if (!errors.isEmpty()) {
            errors.add(new Message(this.messageService.deleteFailed(), MessageLevel.ERROR));

            changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);
            if (atLeastOneSuccess) {
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
            }

            payload = new ErrorPayload(diagramInput.id(), errors);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus invokeDeleteNodeTool(Node node, IEditingContext editingContext, IDiagramContext diagramContext, DeletionPolicy deletionPolicy) {
        IStatus result = new Failure("");
        var optionalNodeDescription = this.findNodeDescription(node, diagramContext.getDiagram(), editingContext);

        if (optionalNodeDescription.isPresent()) {
            var optionalSelf = this.objectService.getObject(editingContext, node.getTargetObjectId());
            if (optionalSelf.isPresent()) {
                var self = optionalSelf.get();
                var variableManager = this.populateVariableManager(editingContext, diagramContext, self, node, null, deletionPolicy);
                NodeDescription nodeDescription = optionalNodeDescription.get();
                this.logger.debug("Deleted diagram element {}", node.getId());
                result = nodeDescription.getDeleteHandler().apply(variableManager);
            } else {
                String message = this.messageService.semanticObjectNotFound(node.getTargetObjectId());
                this.logger.debug(message);
                result = new Failure(message);
            }
        } else {
            String message = this.messageService.nodeDescriptionNotFound(node.getId());
            this.logger.debug(message);
            result = new Failure(message);
        }
        return result;
    }

    private IStatus invokeDeleteEdgeTool(Edge edge, IEditingContext editingContext, IDiagramContext diagramContext, DeletionPolicy deletionPolicy) {
        IStatus result = new Failure("");
        var optionalEdgeDescription = this.findEdgeDescription(edge, diagramContext.getDiagram(), editingContext);
        if (optionalEdgeDescription.isPresent()) {
            var optionalSelf = this.objectService.getObject(editingContext, edge.getTargetObjectId());
            if (optionalSelf.isPresent()) {
                var self = optionalSelf.get();
                var variableManager = this.populateVariableManager(editingContext, diagramContext, self, null, edge, deletionPolicy);
                this.diagramQueryService.findNodeById(diagramContext.getDiagram(), edge.getSourceId())
                        .flatMap(node -> this.objectService.getObject(editingContext, node.getTargetObjectId()))
                        .ifPresent(semanticElement -> variableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, semanticElement));
                this.diagramQueryService.findNodeById(diagramContext.getDiagram(), edge.getTargetId())
                        .flatMap(node -> this.objectService.getObject(editingContext, node.getTargetObjectId()))
                        .ifPresent(semanticElement -> variableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, semanticElement));

                EdgeDescription edgeDescription = optionalEdgeDescription.get();
                this.logger.debug("Deleted diagram edge {}", edge.getId());
                result = edgeDescription.getDeleteHandler().apply(variableManager);
            } else {
                String message = this.messageService.semanticObjectNotFound(edge.getTargetObjectId());
                this.logger.debug(message);
                result = new Failure(message);
            }
        } else {
            String message = this.messageService.edgeDescriptionNotFound(edge.getId());
            this.logger.debug(message);
            result = new Failure(message);
        }
        return result;
    }

    private VariableManager populateVariableManager(IEditingContext editingContext, IDiagramContext diagramContext, Object self, Node selectedNode, Edge selectedEdge, DeletionPolicy deletionPolicy) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
        variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
        variableManager.put(VariableManager.SELF, self);
        variableManager.put(DELETION_POLICY, deletionPolicy);
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
