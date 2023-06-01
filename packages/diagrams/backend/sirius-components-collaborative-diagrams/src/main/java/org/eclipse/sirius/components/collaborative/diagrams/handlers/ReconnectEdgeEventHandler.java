/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReconnectEdgeInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Edge;
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

    private final IObjectService objectService;

    private final List<IReconnectionToolsExecutor> reconnectionToolsExecutors;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public ReconnectEdgeEventHandler(IDiagramQueryService diagramQueryService, IDiagramDescriptionService diagramDescriptionService,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, IObjectService objectService, List<IReconnectionToolsExecutor> reconnectionToolsExecutors,
            ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.reconnectionToolsExecutors = Objects.requireNonNull(reconnectionToolsExecutors);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
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
                diagramContext.setDiagramEvent(
                        new ReconnectEdgeEvent(reconnectEdgeInput.reconnectEdgeKind(), reconnectEdgeInput.edgeId(), reconnectEdgeInput.newEdgeEndId(), reconnectEdgeInput.newEdgeEndPosition()));
                payload = new SuccessPayload(reconnectEdgeInput.id());
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

        // @formatter:off
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);
        // @formatter:on
        if (optionalDiagramDescription.isPresent()) {
            DiagramDescription diagramDescription = optionalDiagramDescription.get();
            var optionalReconnectionToolExecutor = this.reconnectionToolsExecutors.stream().filter(reconnectionToolExecutor -> reconnectionToolExecutor.canExecute(diagramDescription)).findFirst();
            var optionalSemanticTargetElement = this.objectService.getObject(editingContext, edge.getTargetObjectId());
            var optionalEdgeDescription = this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, edge.getDescriptionId());

            ReconnectEdgeKind reconnectEdgeKind = reconnectEdgeInput.reconnectEdgeKind();
            String edgeSourceId = edge.getSourceId();
            String edgeTargetId = edge.getTargetId();

            var optionalEdgeSource = this.getNode(diagram.getNodes(), edgeSourceId);
            var optionalEdgeTarget = this.getNode(diagram.getNodes(), edgeTargetId);

            Optional<Node> optionalPreviousEdgeEnd = optionalEdgeSource;
            Optional<Node> optionalOtherEdgeEnd = optionalEdgeTarget;

            if (ReconnectEdgeKind.TARGET.equals(reconnectEdgeKind)) {
                optionalPreviousEdgeEnd = optionalEdgeTarget;
                optionalOtherEdgeEnd = optionalEdgeSource;
            }

            var optionalPreviousSemanticEdgeEnd = optionalPreviousEdgeEnd.map(Node::getTargetObjectId).flatMap(targetObjectId -> this.objectService.getObject(editingContext, targetObjectId));

            var optionalNewEdgeEnd = this.getNode(diagram.getNodes(), reconnectEdgeInput.newEdgeEndId());
            var optionalNewSemanticEdgeEnd = optionalNewEdgeEnd.map(Node::getTargetObjectId).flatMap(targetObjectId -> this.objectService.getObject(editingContext, targetObjectId));
            var optionalSemanticOtherEdgeEnd = optionalOtherEdgeEnd.map(Node::getTargetObjectId).flatMap(otherEndEdgeId -> this.objectService.getObject(editingContext, otherEndEdgeId));

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

                // @formatter:off
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
                // @formatter:on

                status = reconnectionToolsExecutor.execute(editingContext, reconnectionToolInterpreterData, edge, optionalEdgeDescription.get(), reconnectEdgeKind, diagramDescription);
            }
        }
        return status;
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

}
