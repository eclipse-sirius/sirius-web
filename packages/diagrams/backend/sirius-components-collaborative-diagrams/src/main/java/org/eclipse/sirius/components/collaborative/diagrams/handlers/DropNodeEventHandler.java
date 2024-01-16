/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodeInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
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
public class DropNodeEventHandler implements IDiagramEventHandler {

    private static final String DROPPED_NODE = "droppedNode";

    private static final String DROPPED_ELEMENT = "droppedElement";

    private static final String TARGET_NODE = "targetNode";

    private static final String TARGET_ELEMENT = "targetElement";

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final IFeedbackMessageService feedbackMessageService;

    private final Counter counter;

    public DropNodeEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IDiagramDescriptionService diagramDescriptionService,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, ICollaborativeDiagramMessageService messageService, IFeedbackMessageService feedbackMessageService, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof DropNodeInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), DropNodeInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof DropNodeInput input) {
            Diagram diagram = diagramContext.getDiagram();
            var optionalDroppedNode = this.diagramQueryService.findNodeById(diagram, input.droppedElementId());
            if (optionalDroppedNode.isPresent()) {
                var optionalDropTarget = Optional.ofNullable(input.targetElementId()).flatMap(elementId -> this.diagramQueryService.findNodeById(diagram, elementId));
                boolean handled = this.invokeDropNodeTool(editingContext, diagramContext, diagram, optionalDroppedNode.get(), optionalDropTarget);
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

    private boolean invokeDropNodeTool(IEditingContext editingContext, IDiagramContext diagramContext, Diagram diagram, Node droppedNode, Optional<Node> optionalDropTargetNode) {
        var optionalHandler = this.findDropNodeHandler(editingContext, diagram, optionalDropTargetNode);
        var optionalTargetElement = this.objectService.getObject(editingContext, optionalDropTargetNode.map(Node::getTargetObjectId).orElse(diagram.getTargetObjectId()));
        var optionalDroppedElement = this.objectService.getObject(editingContext, droppedNode.getTargetObjectId());
        if (optionalHandler.isPresent() && optionalTargetElement.isPresent() && optionalDroppedElement.isPresent()) {
            var handler = optionalHandler.get();
            var targetElement = optionalTargetElement.get();

            VariableManager variableManager = new VariableManager();
            variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
            variableManager.put(DROPPED_ELEMENT, optionalDroppedElement.get());
            variableManager.put(DROPPED_NODE, droppedNode);
            variableManager.put(TARGET_ELEMENT, targetElement);
            variableManager.put(TARGET_NODE, optionalDropTargetNode.orElse(null));
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
