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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IActionsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Action;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetActionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetActionsSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to get the node actions.
 *
 * @author arichard
 */
@Service
public class GetActionsEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IObjectSearchService objectSearchService;

    private final IActionsProvider actionsProvider;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetActionsEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService,
            IDiagramDescriptionService diagramDescriptionService, IObjectSearchService objectSearchService, IActionsProvider actionsProvider, ICollaborativeMessageService messageService,
            MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.actionsProvider = Objects.requireNonNull(actionsProvider);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof GetActionsInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetActionsInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        List<Action> actions = null;

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof GetActionsInput actionsInput) {
            String diagramElementId = actionsInput.diagramElementId();

            Diagram diagram = diagramContext.getDiagram();
            var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);
            if (optionalDiagramDescription.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();
                var optionalTargetElement = this.findTargetElement(diagram, diagramElementId, editingContext);
                var optionalNode = this.findNode(diagram, diagramElementId);
                var optionalDiagramElementDescription = this.findNodeDescription(diagram, diagramElementId, diagramDescription, optionalNode);

                if (this.actionsProvider.canHandle(diagramDescription) && optionalTargetElement.isPresent() && optionalDiagramElementDescription.isPresent()) {
                    actions = this.actionsProvider.handle(optionalTargetElement.get(), optionalNode.orElse(null), optionalDiagramElementDescription.get(),
                            diagramDescription, editingContext);
                }
            }
        }
        if (actions != null) {
            payload = new GetActionsSuccessPayload(diagramInput.id(), actions);
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<Node> findNode(Diagram diagram, String diagramElementId) {
        Node node = null;
        var findNodeById = this.diagramQueryService.findNodeById(diagram, diagramElementId);
        if (findNodeById.isPresent()) {
            node = findNodeById.get();
        }
        return Optional.ofNullable(node);
    }

    private Optional<NodeDescription> findNodeDescription(Diagram diagram, String diagramElementId, DiagramDescription diagramDescription, Optional<Node> node) {
        if (node.isPresent()) {
            NodeDescription nodeDescription = null;
            var descriptionId = node.get().getDescriptionId();
            var optionalNodeDescription = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, descriptionId);
            if (optionalNodeDescription.isPresent()) {
                nodeDescription = optionalNodeDescription.get();
            }
            return Optional.ofNullable(nodeDescription);
        }
        return Optional.empty();
    }

    private Optional<Object> findTargetElement(Diagram diagram, String diagramElementId, IEditingContext editingContext) {
        String targetObjectId = null;
        boolean appliesToRootDiagram = diagram.getId().equals(diagramElementId);
        if (appliesToRootDiagram) {
            targetObjectId = diagram.getTargetObjectId();
        } else {
            var findNodeById = this.diagramQueryService.findNodeById(diagram, diagramElementId);
            if (findNodeById.isPresent()) {
                Node node = findNodeById.get();
                targetObjectId = node.getTargetObjectId();
            }
        }
        if (targetObjectId != null) {
            return this.objectSearchService.getObject(editingContext, targetObjectId);
        }
        return Optional.empty();
    }
}
