/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetNodeDescriptionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetNodeDescriptionsSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.components.INodeDescriptionRequestor;
import org.eclipse.sirius.components.diagrams.components.NodeDescriptionRequestor;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handle "get node descriptions" events.
 *
 * @author arichard
 */
@Service
public class GetNodeDescriptionsEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public GetNodeDescriptionsEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof GetNodeDescriptionsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetNodeDescriptionsInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof GetNodeDescriptionsInput input) {
            Diagram diagram = diagramContext.getDiagram();
            var diagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);

            if (diagramDescription.isPresent()) {
                var allDiagramDescriptions = this.representationDescriptionSearchService.findAll(editingContext)
                        .values()
                        .stream()
                        .filter(DiagramDescription.class::isInstance)
                        .map(DiagramDescription.class::cast)
                        .toList();
                INodeDescriptionRequestor nodeDescriptionRequestor = new NodeDescriptionRequestor(allDiagramDescriptions);

                var rootNodeDescriptions = new ArrayList<>(diagramDescription.get().getNodeDescriptions());
                var allNodeDescriptions = new ArrayList<NodeDescription>();
                allNodeDescriptions.addAll(rootNodeDescriptions);
                rootNodeDescriptions.forEach(nodeDescription -> this.addChildrenNodeDescriptions(nodeDescription, allNodeDescriptions, nodeDescriptionRequestor));

                payload = new GetNodeDescriptionsSuccessPayload(diagramInput.id(), allNodeDescriptions);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private void addChildrenNodeDescriptions(NodeDescription nodeDescription, ArrayList<NodeDescription> allNodeDescriptions, INodeDescriptionRequestor nodeDescriptionRequestor) {
        List<NodeDescription> childNodeDescriptions = nodeDescription.getChildNodeDescriptions();
        allNodeDescriptions.addAll(childNodeDescriptions);
        List<NodeDescription> borderNodeDescriptions = nodeDescription.getBorderNodeDescriptions();
        allNodeDescriptions.addAll(borderNodeDescriptions);

        childNodeDescriptions.forEach(childNodeDescription -> {
            if (!allNodeDescriptions.contains(childNodeDescription)) {
                this.addChildrenNodeDescriptions(childNodeDescription, allNodeDescriptions, nodeDescriptionRequestor);
            }
        });
        borderNodeDescriptions.forEach(borderNodeDescription -> {
            if (!allNodeDescriptions.contains(borderNodeDescription)) {
                this.addChildrenNodeDescriptions(borderNodeDescription, allNodeDescriptions, nodeDescriptionRequestor);
            }
        });

        nodeDescription.getReusedChildNodeDescriptionIds()
                .stream()
                .map(nodeDescriptionRequestor::findById)
                .flatMap(Optional::stream)
                .forEach(reusedChildNodeDescription -> {
                    if (!allNodeDescriptions.contains(reusedChildNodeDescription)) {
                        allNodeDescriptions.add(reusedChildNodeDescription);
                        this.addChildrenNodeDescriptions(reusedChildNodeDescription, allNodeDescriptions, nodeDescriptionRequestor);
                    }
                });

        nodeDescription.getReusedBorderNodeDescriptionIds()
                .stream()
                .map(nodeDescriptionRequestor::findById)
                .flatMap(Optional::stream)
                .forEach(reusedBorderNodeDescription -> {
                    if (!allNodeDescriptions.contains(reusedBorderNodeDescription)) {
                        allNodeDescriptions.add(reusedBorderNodeDescription);
                        this.addChildrenNodeDescriptions(reusedBorderNodeDescription, allNodeDescriptions, nodeDescriptionRequestor);
                    }
                });
    }
}
