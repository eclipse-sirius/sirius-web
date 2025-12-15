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

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodesInput;
import org.eclipse.sirius.components.collaborative.diagrams.handlers.api.IDropNodesVariableManagerProvider;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramVariables;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
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
public class DropNodesEventHandler implements IDiagramEventHandler {

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IDropNodesVariableManagerProvider dropNodesVariableManagerProvider;

    private final Counter counter;

    public DropNodesEventHandler(IDiagramDescriptionService diagramDescriptionService, IRepresentationDescriptionSearchService representationDescriptionSearchService, ICollaborativeDiagramMessageService messageService,
                                 IFeedbackMessageService feedbackMessageService, IDropNodesVariableManagerProvider dropNodesVariableManagerProvider, MeterRegistry meterRegistry) {
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.dropNodesVariableManagerProvider = Objects.requireNonNull(dropNodesVariableManagerProvider);
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
            var optionalVariableManager = this.dropNodesVariableManagerProvider.getVariableManager(editingContext, diagramContext, input.targetElementId(), input.droppedElementIds());
            if (optionalVariableManager.isPresent()) {
                var variableManager = optionalVariableManager.get();

                Optional<Node> optionalDropTargetNode = variableManager.get(DiagramVariables.TARGET_NODE.name(), Node.class);
                var optionalHandler = this.findDropNodeHandler(editingContext, diagramContext.diagram(), optionalDropTargetNode);
                if (optionalHandler.isPresent()) {
                    var handler = optionalHandler.get();
                    var status = handler.apply(variableManager);

                    if (status instanceof Success) {
                        payload = new SuccessPayload(diagramInput.id(), this.feedbackMessageService.getFeedbackMessages());
                        changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
                    }
                }
            } else {
                payload = new ErrorPayload(diagramInput.id(), this.feedbackMessageService.getFeedbackMessages());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<Function<VariableManager, IStatus>> findDropNodeHandler(IEditingContext editingContext, Diagram diagram, Optional<Node> optionalDropTargetNode) {
        if (optionalDropTargetNode.isPresent()) {
            return this.findNodeDescription(optionalDropTargetNode.get(), diagram, editingContext)
                    .flatMap(nodeDescription -> Optional.ofNullable(nodeDescription.getDropNodeHandler()));
        }
        return this.findDiagramDescription(diagram, editingContext)
                .flatMap(diagramDescription -> Optional.ofNullable(diagramDescription.getDropNodeHandler()));
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
