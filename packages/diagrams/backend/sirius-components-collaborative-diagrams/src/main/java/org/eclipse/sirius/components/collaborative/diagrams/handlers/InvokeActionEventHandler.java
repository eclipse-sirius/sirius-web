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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IActionsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeActionInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeActionSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.actions.Action;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
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
 * Handle "Invoke action" events.
 *
 * @author arichard
 */
@Service
public class InvokeActionEventHandler implements IDiagramEventHandler {

    private final Logger logger = LoggerFactory.getLogger(InvokeActionEventHandler.class);

    private final IObjectSearchService objectSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IDiagramQueryService diagramQueryService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InvokeActionEventHandler(IObjectSearchService objectSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramDescriptionService diagramDescriptionService, IDiagramQueryService diagramQueryService, IActionsProvider actionsProvider,
            ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeActionInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeActionInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof InvokeActionInput input) {
            var diagram = diagramContext.getDiagram();
            var actionId = input.actionId();
            var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);
            var optionalNode = this.diagramQueryService.findNodeById(diagram, input.diagramElementId());
            var optionalNodeDescription = this.findNodeDescription(diagram, input.targetElementId(), optionalDiagramDescription.get(), optionalNode.orElse(null));

            Optional<Action> optionalAction = Optional.empty();
            if (optionalNodeDescription.isPresent()) {
                NodeDescription nodeDescription = optionalNodeDescription.get();
                optionalAction = nodeDescription.getActions().stream()
                    .filter(a -> Objects.equals(a.getId(), actionId))
                    .findFirst();
            }

            if (optionalAction.isPresent()) {
                IStatus status = this.executeAction(editingContext, diagramContext, input.diagramElementId(), optionalAction.get());
                if (status instanceof Success success) {
                    payload = new InvokeActionSuccessPayload(diagramInput.id(), success.getMessages());
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
                } else if (status instanceof Failure failure) {
                    payload = new ErrorPayload(diagramInput.id(), failure.getMessages());
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus executeAction(IEditingContext editingContext, IDiagramContext diagramContext, String diagramElementId, Action action) {
        IStatus result = new Failure("");
        var diagram = diagramContext.getDiagram();
        var node = this.diagramQueryService.findNodeById(diagram, diagramElementId);
        var self = this.getCurrentContext(editingContext, diagramElementId, action, diagram, node);
        if (self.isPresent()) {
            var variableManager = this.populateVariableManager(editingContext, diagramContext, node, self);
            result = action.getHandler().apply(variableManager);
        }
        return result;
    }

    private Optional<NodeDescription> findNodeDescription(Diagram diagram, String diagramElementId, DiagramDescription diagramDescription, Node node) {
        NodeDescription nodeDescription = null;
        var descriptionId = node.getDescriptionId();
        var optionalNodeDescription = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, descriptionId);
        if (optionalNodeDescription.isPresent()) {
            nodeDescription = optionalNodeDescription.get();
        }
        return Optional.ofNullable(nodeDescription);
    }

    private Optional<Object> getCurrentContext(IEditingContext editingContext, String diagramElementId, Action action, Diagram diagram, Optional<Node> node) {
        Optional<Object> self = Optional.empty();
        if (node.isPresent()) {
            self = this.objectSearchService.getObject(editingContext, node.get().getTargetObjectId());
        } else if (Objects.equals(diagram.getId(), diagramElementId)) {
            self = this.objectSearchService.getObject(editingContext, diagram.getTargetObjectId());
        } else {
            this.logger.warn("The action {0} cannot be applied on the current diagram {1} and editing context {2}", action.getId(), diagram.getId(), editingContext.getId());
        }
        return self;
    }

    private VariableManager populateVariableManager(IEditingContext editingContext, IDiagramContext diagramContext, Optional<Node> node, Optional<Object> self) {
        var variableManager = new VariableManager();
        variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
        variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
        variableManager.put(VariableManager.SELF, self.get());
        variableManager.put(Node.SELECTED_NODE, node.orElse(null));
        return variableManager;
    }
}
