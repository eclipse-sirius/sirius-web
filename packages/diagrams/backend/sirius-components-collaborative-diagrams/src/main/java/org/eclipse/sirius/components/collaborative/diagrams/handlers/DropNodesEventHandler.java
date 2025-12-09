/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodesInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramVariableProvider;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.api.variables.CommonVariables;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * The handler invoked when a node is dropped onto another one (or on the diagram's background).
 *
 * @author pcdavid
 */
@Service
public class DropNodesEventHandler implements IDiagramEventHandler {

    private static final String DROPPED_NODES = "droppedNodes";

    private static final String DROPPED_ELEMENTS = "droppedElements";

    private static final String TARGET_NODE = "targetNode";

    private static final String TARGET_ELEMENT = "targetElement";

    private final IObjectSearchService objectSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final IFeedbackMessageService feedbackMessageService;

    private final Counter counter;

    public DropNodesEventHandler(IObjectSearchService objectSearchService, IDiagramQueryService diagramQueryService, IDiagramDescriptionService diagramDescriptionService,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, ICollaborativeDiagramMessageService messageService, IFeedbackMessageService feedbackMessageService, MeterRegistry meterRegistry) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
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
    public boolean canHandle(IEditingContext editingContext, IDiagramInput diagramInput) {
        return diagramInput instanceof DropNodesInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), DropNodesInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof DropNodesInput input) {
            Diagram diagram = diagramContext.diagram();
            var droppedNodes = input.droppedElementIds().stream().flatMap(droppedElementId -> this.diagramQueryService.findNodeById(diagram, droppedElementId).stream()).toList();
            if (droppedNodes.size() == input.droppedElementIds().size()) {
                var optionalDropTarget = Optional.ofNullable(input.targetElementId()).flatMap(elementId -> this.diagramQueryService.findNodeById(diagram, elementId));
                boolean handled = this.invokeDropNodesTool(editingContext, diagramContext, diagram, droppedNodes, optionalDropTarget);
                if (handled) {
                    payload = new SuccessPayload(diagramInput.id(), this.feedbackMessageService.getFeedbackMessages());
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
                } else {
                    payload = new ErrorPayload(diagramInput.id(), this.feedbackMessageService.getFeedbackMessages());
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private boolean invokeDropNodesTool(IEditingContext editingContext, DiagramContext diagramContext, Diagram diagram, List<Node> droppedNodes, Optional<Node> optionalDropTargetNode) {
        var optionalHandler = this.findDropNodeHandler(editingContext, diagram, optionalDropTargetNode);

        var optionalTargetElement = this.objectSearchService.getObject(editingContext, optionalDropTargetNode
                .map(Node::getTargetObjectId)
                .orElse(diagram.getTargetObjectId()));

        var droppedElements = droppedNodes.stream()
                .map(droppedNode -> this.objectSearchService.getObject(editingContext, droppedNode.getTargetObjectId()))
                .flatMap(Optional::stream)
                .toList();

        if (optionalHandler.isPresent() && optionalTargetElement.isPresent() && droppedElements.size() == droppedNodes.size()) {
            var handler = optionalHandler.get();
            var targetElement = optionalTargetElement.get();

            var droppedElement = droppedElements.stream()
                    .findFirst()
                    .orElse(null);

            var droppedNode = droppedNodes.stream()
                    .findFirst()
                    .orElse(null);

            VariableManager variableManager = new VariableManager();
            variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
            variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
            variableManager.put(CommonVariables.EDITING_CONTEXT.name(), editingContext);
            variableManager.put(DiagramContext.DIAGRAM_CONTEXT, diagramContext);
            variableManager.put(DiagramVariableProvider.DROPPED_ELEMENTS.name(), droppedElements);
            variableManager.put(DiagramVariableProvider.DROPPED_NODES.name(), droppedNodes);
            variableManager.put(DiagramVariableProvider.DROPPED_ELEMENT.name(), droppedElement);
            variableManager.put(DiagramVariableProvider.DROPPED_NODE.name(), droppedNode);
            variableManager.put(DiagramVariableProvider.TARGET_ELEMENT.name(), targetElement);
            variableManager.put(DiagramVariableProvider.TARGET_NODE.name(), optionalDropTargetNode.orElse(null));
            return handler.apply(variableManager) instanceof Success;
        } else {
            return false;
        }
    }

    private Optional<Function<VariableManager, IStatus>> findDropNodeHandler(IEditingContext editingContext, Diagram diagram, Optional<Node> optionalDropTargetNode) {
        if (optionalDropTargetNode.isPresent()) {
            return this.findNodeDescription(optionalDropTargetNode.get(), diagram, editingContext)
                    .flatMap(nodeDescription -> Optional.ofNullable(nodeDescription.getDropNodeHandler()));
        } else {
            return this.findDiagramDescription(diagram, editingContext)
                    .flatMap(diagramDescription -> Optional.ofNullable(diagramDescription.getDropNodeHandler()));
        }
    }

    private Optional<DiagramDescription> findDiagramDescription(Diagram diagram, IEditingContext editingContext) {
        return this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);
    }

    private Optional<NodeDescription> findNodeDescription(Node node, Diagram diagram, IEditingContext editingContext) {
        return this.findDiagramDescription(diagram, editingContext)
                .flatMap(diagramDescription -> this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, node.getDescriptionId()));
    }
}
