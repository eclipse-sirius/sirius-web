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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.InvokeFilterSelectionInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.InvokeFilterSelectionSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handle "InvokeFilterSelectionEvent".
 *
 * @author mcharfadi
 */
@Service
public class InvokeFilterSelectionEventHandler implements IDiagramEventHandler {

    private final IDiagramQueryService diagramQueryService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InvokeFilterSelectionEventHandler(IDiagramQueryService diagramQueryService, ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeFilterSelectionInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeFilterSelectionInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof InvokeFilterSelectionInput invokeFilterSelectionInput) {
            switch (invokeFilterSelectionInput.filterSelectionId()) {
                case "select_all_nodes" -> {
                    var newSelection = this.getAllNodesIDs(diagramContext.diagram().getNodes());
                    payload = new InvokeFilterSelectionSuccessPayload(invokeFilterSelectionInput.id(), newSelection);
                }
                case "select_all_edges" -> {
                    var newSelection = diagramContext.diagram().getEdges().stream().map(Edge::getId).toList();
                    payload = new InvokeFilterSelectionSuccessPayload(invokeFilterSelectionInput.id(), newSelection);
                }
                case "unselect_all_nodes" -> {
                    var newSelection = invokeFilterSelectionInput.diagramElementIds().stream()
                            .filter(diagramElementId -> this.diagramQueryService.findNodeById(diagramContext.diagram(), diagramElementId).isEmpty())
                            .toList();
                    payload = new InvokeFilterSelectionSuccessPayload(invokeFilterSelectionInput.id(), newSelection);
                }
                case "unselect_child_nodes" -> {
                    var newSelection = invokeFilterSelectionInput.diagramElementIds().stream()
                            .filter(diagramElementId -> isRootNode(diagramContext, diagramElementId)
                                    || this.diagramQueryService.findEdgeById(diagramContext.diagram(), diagramElementId).isPresent())
                            .toList();
                    payload = new InvokeFilterSelectionSuccessPayload(invokeFilterSelectionInput.id(), newSelection);
                }
                case "unselect_all_edges" -> {
                    var newSelection = invokeFilterSelectionInput.diagramElementIds().stream()
                            .filter(diagramElementId -> this.diagramQueryService.findEdgeById(diagramContext.diagram(), diagramElementId).isEmpty())
                            .toList();
                    payload = new InvokeFilterSelectionSuccessPayload(invokeFilterSelectionInput.id(), newSelection);
                }
                default -> {
                    message = this.messageService.actionHandlerNotFound(invokeFilterSelectionInput.filterSelectionId());
                    payload = new ErrorPayload(diagramInput.id(), message);
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    public List<String> getAllNodesIDs(List<Node> nodes) {
        var allNodes = new LinkedList<String>();
        for (Node node : nodes) {
            allNodes.add(node.getId());
            var children = new LinkedList<>(node.getChildNodes());
            allNodes.addAll(this.getAllNodesIDs(children));
        }
        return allNodes;
    }

    private boolean isRootNode(DiagramContext diagramContext, String diagramElementId) {
        return diagramContext.diagram().getNodes().stream()
                .map(Node::getId)
                .anyMatch(nodeId -> nodeId.equals(diagramElementId));
    }
}