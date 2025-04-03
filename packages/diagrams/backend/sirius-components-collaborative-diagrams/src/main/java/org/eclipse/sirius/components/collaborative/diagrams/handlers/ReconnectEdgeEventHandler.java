/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IReconnectionToolsExecutor;
import org.eclipse.sirius.components.collaborative.diagrams.api.ReconnectionToolInterpreterData;
import org.eclipse.sirius.components.collaborative.diagrams.configuration.DiagramEventHandlerConfiguration;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReconnectEdgeInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeEvent;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle the reconnect edge events.
 *
 * @author gcoutable
 */
@Service
public class ReconnectEdgeEventHandler implements IDiagramEventHandler {

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectSearchService objectSearchService;

    private final List<IReconnectionToolsExecutor> reconnectionToolsExecutors;

    private final ICollaborativeDiagramMessageService messageService;

    private final IFeedbackMessageService feedbackMessageService;

    private final Counter counter;

    public ReconnectEdgeEventHandler(DiagramEventHandlerConfiguration diagramEventHandlerConfiguration, List<IReconnectionToolsExecutor> reconnectionToolsExecutors, MeterRegistry meterRegistry) {
        this.diagramQueryService = Objects.requireNonNull(diagramEventHandlerConfiguration.getDiagramQueryService());
        this.diagramDescriptionService = Objects.requireNonNull(diagramEventHandlerConfiguration.getDiagramDescriptionService());
        this.representationDescriptionSearchService = Objects.requireNonNull(diagramEventHandlerConfiguration.getRepresentationDescriptionSearchService());
        this.objectSearchService = Objects.requireNonNull(diagramEventHandlerConfiguration.getObjectSearchService());
        this.reconnectionToolsExecutors = Objects.requireNonNull(reconnectionToolsExecutors);
        this.messageService = Objects.requireNonNull(diagramEventHandlerConfiguration.getMessageService());
        this.feedbackMessageService = Objects.requireNonNull(diagramEventHandlerConfiguration.getFeedbackMessageService());

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof ReconnectEdgeInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        if (diagramInput instanceof ReconnectEdgeInput) {
            this.handleReconnect(payloadSink, changeDescriptionSink, editingContext, diagramContext, (ReconnectEdgeInput) diagramInput);
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), ReconnectEdgeInput.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(diagramInput.id(), message));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput));
        }
    }

    private void handleReconnect(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
                                 ReconnectEdgeInput reconnectEdgeInput) {
        String message = this.messageService.edgeNotFound(String.valueOf(reconnectEdgeInput.edgeId()));
        IPayload payload = new ErrorPayload(reconnectEdgeInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, reconnectEdgeInput.representationId(), reconnectEdgeInput);

        Optional<Edge> optionalEdge = this.diagramQueryService.findEdgeById(diagramContext.getDiagram(), reconnectEdgeInput.edgeId());

        if (optionalEdge.isPresent()) {
            IStatus status = this.invokeReconnectEdgeTool(optionalEdge.get(), editingContext, diagramContext, reconnectEdgeInput);
            if (status instanceof Success) {
                diagramContext.getDiagramEvents().add(
                        new ReconnectEdgeEvent(reconnectEdgeInput.reconnectEdgeKind(), reconnectEdgeInput.edgeId()));
                payload = new SuccessPayload(reconnectEdgeInput.id(), this.feedbackMessageService.getFeedbackMessages());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, reconnectEdgeInput.representationId(), reconnectEdgeInput);
            } else {
                payload = new ErrorPayload(reconnectEdgeInput.id(), ((Failure) status).getMessages());
                // The frontend action has been send, thus, the edge has been reconnected on he frontend. We need to
                // force the backend to send the refreshed diagram to "undo" the reconnect.
                changeDescription = new ChangeDescription(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE, reconnectEdgeInput.representationId(), reconnectEdgeInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus invokeReconnectEdgeTool(Edge edge, IEditingContext editingContext, IDiagramContext diagramContext, ReconnectEdgeInput reconnectEdgeInput) {
        IStatus status = new Failure("");

        var diagram = diagramContext.getDiagram();

        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);
        if (optionalDiagramDescription.isPresent()) {
            DiagramDescription diagramDescription = optionalDiagramDescription.get();
            var optionalReconnectionToolExecutor = this.reconnectionToolsExecutors.stream().filter(reconnectionToolExecutor -> reconnectionToolExecutor.canExecute(diagramDescription)).findFirst();
            var optionalSemanticTargetElement = this.objectSearchService.getObject(editingContext, edge.getTargetObjectId());
            var optionalEdgeDescription = this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, edge.getDescriptionId());

            ReconnectEdgeKind reconnectEdgeKind = reconnectEdgeInput.reconnectEdgeKind();
            String edgeSourceId = edge.getSourceId();
            String edgeTargetId = edge.getTargetId();

            var optionalEdgeSource = this.getDiagramElement(diagram, edgeSourceId);
            var optionalEdgeTarget = this.getDiagramElement(diagram, edgeTargetId);

            Optional<IDiagramElement> optionalPreviousEdgeEnd = optionalEdgeSource;
            Optional<IDiagramElement> optionalOtherEdgeEnd = optionalEdgeTarget;

            if (ReconnectEdgeKind.TARGET.equals(reconnectEdgeKind)) {
                optionalPreviousEdgeEnd = optionalEdgeTarget;
                optionalOtherEdgeEnd = optionalEdgeSource;
            }

            var optionalPreviousSemanticEdgeEnd = optionalPreviousEdgeEnd.map(this::getTargetObjectId).flatMap(targetObjectId -> this.objectSearchService.getObject(editingContext, targetObjectId));

            var optionalNewEdgeEnd = this.getDiagramElement(diagram, reconnectEdgeInput.newEdgeEndId());
            var optionalNewSemanticEdgeEnd = optionalNewEdgeEnd.map(this::getTargetObjectId).flatMap(targetObjectId -> this.objectSearchService.getObject(editingContext, targetObjectId));
            var optionalSemanticOtherEdgeEnd = optionalOtherEdgeEnd.map(this::getTargetObjectId).flatMap(otherEndEdgeId -> this.objectSearchService.getObject(editingContext, otherEndEdgeId));

            boolean canExecuteReconnectTool = optionalReconnectionToolExecutor.isPresent();
            canExecuteReconnectTool = canExecuteReconnectTool && optionalSemanticTargetElement.isPresent();
            canExecuteReconnectTool = canExecuteReconnectTool && optionalEdgeDescription.isPresent();
            canExecuteReconnectTool = canExecuteReconnectTool && optionalPreviousEdgeEnd.isPresent();
            canExecuteReconnectTool = canExecuteReconnectTool && optionalPreviousSemanticEdgeEnd.isPresent();
            canExecuteReconnectTool = canExecuteReconnectTool && optionalNewSemanticEdgeEnd.isPresent();
            canExecuteReconnectTool = canExecuteReconnectTool && optionalOtherEdgeEnd.isPresent();
            canExecuteReconnectTool = canExecuteReconnectTool && optionalSemanticOtherEdgeEnd.isPresent();

            if (canExecuteReconnectTool && reconnectEdgeInput.newEdgeEndId().equals(optionalPreviousEdgeEnd.get().getId())) {
                canExecuteReconnectTool = false;
                status = new Failure(this.messageService.reconnectEdgeSameEdgeEnd());
            }

            if (canExecuteReconnectTool) {
                IReconnectionToolsExecutor reconnectionToolsExecutor = optionalReconnectionToolExecutor.get();

                ReconnectionToolInterpreterData reconnectionToolInterpreterData = ReconnectionToolInterpreterData.newReconnectionToolInterpreterData()
                        .diagramContext(diagramContext)
                        .semanticReconnectionSource(optionalPreviousSemanticEdgeEnd.get())
                        .reconnectionSourceView(optionalPreviousEdgeEnd.get())
                        .semanticReconnectionTarget(optionalNewSemanticEdgeEnd.get())
                        .reconnectionTargetView(optionalNewEdgeEnd.get())
                        .semanticElement(optionalSemanticTargetElement.get())
                        .otherEdgeEnd(optionalOtherEdgeEnd.get())
                        .semanticOtherEdgeEnd(optionalSemanticOtherEdgeEnd.get())
                        .edgeView(edge)
                        .kind(reconnectEdgeKind)
                        .build();

                status = reconnectionToolsExecutor.execute(editingContext, reconnectionToolInterpreterData, edge, optionalEdgeDescription.get(), reconnectEdgeKind, diagramDescription);
            }
        }
        return status;
    }

    private Optional<IDiagramElement> getDiagramElement(Diagram diagram, String diagramElementId) {
        Optional<IDiagramElement> optionalDiagramElement = diagram.getEdges().stream()
                .filter(edge -> edge.getId().equals(diagramElementId))
                .map(IDiagramElement.class::cast)
                .findFirst();

        if (optionalDiagramElement.isEmpty()) {
            optionalDiagramElement = getNode(diagram.getNodes(), diagramElementId).map(IDiagramElement.class::cast);
        }
        return optionalDiagramElement;
    }

    private Optional<Node> getNode(List<Node> nodes, String nodeId) {
        Optional<Node> optionalNode = Optional.empty();
        List<Node> deeperNode = new ArrayList<>();

        Iterator<Node> nodeIt = nodes.iterator();
        while (optionalNode.isEmpty() && nodeIt.hasNext()) {
            Node node = nodeIt.next();
            if (nodeId.equals(node.getId())) {
                optionalNode = Optional.of(node);
            } else {
                deeperNode.addAll(node.getChildNodes());
                deeperNode.addAll(node.getBorderNodes());
            }
        }

        if (optionalNode.isEmpty() && !deeperNode.isEmpty()) {
            optionalNode = this.getNode(deeperNode, nodeId);
        }

        return optionalNode;
    }

    private String getTargetObjectId(IDiagramElement diagramElement) {
        String targetId = "";
        if (diagramElement instanceof Edge edge) {
            targetId = edge.getTargetObjectId();
        }
        if (diagramElement instanceof  Node node) {
            targetId = node.getTargetObjectId();
        }
        return targetId;
    }

}
