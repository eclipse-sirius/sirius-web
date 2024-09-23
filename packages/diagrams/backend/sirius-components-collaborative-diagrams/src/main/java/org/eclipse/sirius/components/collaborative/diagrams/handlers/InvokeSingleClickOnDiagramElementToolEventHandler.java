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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Invoke single click on diagram element tool" events.
 *
 * @author pcdavid
 */
@Service
public class InvokeSingleClickOnDiagramElementToolEventHandler implements IDiagramEventHandler {

    private static final String OBJECT_ID_ARRAY_SEPARATOR = ",";

    private final Logger logger = LoggerFactory.getLogger(InvokeSingleClickOnDiagramElementToolEventHandler.class);

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IToolService toolService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InvokeSingleClickOnDiagramElementToolEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IToolService toolService,
            ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.toolService = Objects.requireNonNull(toolService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeSingleClickOnDiagramElementToolInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput input) {
            Diagram diagram = diagramContext.getDiagram();
            var optionalTool = this.toolService.findToolById(editingContext, diagram, input.toolId())
                    .filter(SingleClickOnDiagramElementTool.class::isInstance)
                    .map(SingleClickOnDiagramElementTool.class::cast);
            if (optionalTool.isPresent()) {
                IStatus status = this.executeTool(editingContext, diagramContext, input.diagramElementId(), optionalTool.get(), input.startingPositionX(), input.startingPositionY(),
                        input.variables());
                if (status instanceof Success success) {
                    WorkbenchSelection newSelection = null;
                    Object newSelectionParameter = success.getParameters().get(Success.NEW_SELECTION);
                    if (newSelectionParameter instanceof WorkbenchSelection workbenchSelection) {
                        newSelection = workbenchSelection;
                    }
                    payload = new InvokeSingleClickOnDiagramElementToolSuccessPayload(diagramInput.id(), newSelection, success.getMessages());
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
                } else if (status instanceof Failure failure) {
                    payload = new ErrorPayload(diagramInput.id(), failure.getMessages());
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus executeTool(IEditingContext editingContext, IDiagramContext diagramContext, String diagramElementId, SingleClickOnDiagramElementTool tool, double startingPositionX,
            double startingPositionY, List<ToolVariable> variables) {
        IStatus result = new Failure("");
        Diagram diagram = diagramContext.getDiagram();
        Optional<Node> node = this.diagramQueryService.findNodeById(diagram, diagramElementId);
        Optional<Edge> edge = Optional.empty();
        if (node.isEmpty()) {
            // may be the tool applies on an Edge
            edge = this.diagramQueryService.findEdgeById(diagram, diagramElementId);
        }
        Optional<Object> self = this.getCurrentContext(editingContext, diagramElementId, tool, diagram, node, edge);

        // Else, cannot find the node with the given optionalDiagramElementId
        if (self.isPresent()) {
            VariableManager variableManager = this.populateVariableManager(editingContext, diagramContext, node, edge, self);
            var dialogDescriptionId = tool.getDialogDescriptionId();
            if (dialogDescriptionId != null) {
                variables.forEach(toolVariable -> this.handleToolVariable(toolVariable, editingContext, variableManager));
            }

            //We do not apply the tool if a dialog is defined but no variables have been provided
            if (dialogDescriptionId == null || !variables.isEmpty()) {
                result = tool.getHandler().apply(variableManager);
            }
        }
        return result;
    }

    private void handleToolVariable(ToolVariable toolvariable, IEditingContext editingContext, VariableManager variableManager) {
        switch (toolvariable.type()) {
            case STRING -> variableManager.put(toolvariable.name(), toolvariable.value());
            case OBJECT_ID -> {
                var optionalObject = this.objectService.getObject(editingContext, toolvariable.value());
                variableManager.put(toolvariable.name(), optionalObject.orElse(null));
            }
            case OBJECT_ID_ARRAY -> {
                String value = toolvariable.value();
                List<String> objectsIds = List.of(value.split(OBJECT_ID_ARRAY_SEPARATOR));
                List<Object> objects = objectsIds.stream()
                        .map(objectId -> this.objectService.getObject(editingContext, objectId))
                        .map(optionalObject -> optionalObject.orElse(null))
                        .toList();
                variableManager.put(toolvariable.name(), objects);
            }
            default -> this.logger.warn("Unexpected value: " + toolvariable.type());
        }
    }

    private Optional<Object> getCurrentContext(IEditingContext editingContext, String diagramElementId, SingleClickOnDiagramElementTool tool, Diagram diagram, Optional<Node> node,
            Optional<Edge> edge) {
        Optional<Object> self = Optional.empty();
        if (node.isPresent()) {
            self = this.objectService.getObject(editingContext, node.get().getTargetObjectId());
        } else if (edge.isPresent()) {
            self = this.objectService.getObject(editingContext, edge.get().getTargetObjectId());
        } else if (Objects.equals(diagram.getId(), diagramElementId)) {
            self = this.objectService.getObject(editingContext, diagram.getTargetObjectId());
        } else {
            this.logger.warn("The tool {0} cannot be applied on the current diagram {1} and editing context {2}", tool.getId(), diagram.getId(), editingContext.getId());
        }
        return self;
    }

    private VariableManager populateVariableManager(IEditingContext editingContext, IDiagramContext diagramContext, Optional<Node> node, Optional<Edge> edge, Optional<Object> self) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
        variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
        variableManager.put(VariableManager.SELF, self.get());
        variableManager.put(Node.SELECTED_NODE, node.orElse(null));
        variableManager.put(Edge.SELECTED_EDGE, edge.orElse(null));
        return variableManager;
    }
}
