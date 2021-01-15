/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams.handlers;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.InvokeEdgeToolOnDiagramInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.InvokeEdgeToolOnDiagramSuccessPayload;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.tools.CreateEdgeTool;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handle "Invoke edge tool on diagram" events.
 *
 * @author pcdavid
 * @author hmarchadour
 */
@Service
public class InvokeEdgeToolOnDiagramEventHandler implements IDiagramEventHandler {

    private final IObjectService objectService;

    private final IDiagramService diagramService;

    private final IToolService toolService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InvokeEdgeToolOnDiagramEventHandler(IObjectService objectService, IDiagramService diagramService, IToolService toolService, ICollaborativeDiagramMessageService messageService,
            MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramService = Objects.requireNonNull(diagramService);
        this.toolService = Objects.requireNonNull(toolService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeEdgeToolOnDiagramInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        if (diagramInput instanceof InvokeEdgeToolOnDiagramInput) {
            InvokeEdgeToolOnDiagramInput input = (InvokeEdgeToolOnDiagramInput) diagramInput;
            Diagram diagram = diagramContext.getDiagram();
            // @formatter:off
            var optionalTool = this.toolService.findToolById(diagram, input.getToolId())
                    .filter(CreateEdgeTool.class::isInstance)
                    .map(CreateEdgeTool.class::cast);
            // @formatter:on
            if (optionalTool.isPresent()) {
                Status status = this.executeTool(editingContext, diagramContext, input.getDiagramSourceElementId(), input.getDiagramTargetElementId(), optionalTool.get());
                if (Objects.equals(status, Status.OK)) {
                    return new EventHandlerResponse(true, representation -> true, new InvokeEdgeToolOnDiagramSuccessPayload(diagram));
                }
            }
        }
        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeEdgeToolOnDiagramInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

    private Status executeTool(IEditingContext editingContext, IDiagramContext diagramContext, UUID sourceNodeId, UUID targetNodeId, CreateEdgeTool tool) {
        Status result = Status.ERROR;
        Diagram diagram = diagramContext.getDiagram();
        Optional<Node> sourceNode = this.diagramService.findNodeById(diagram, sourceNodeId);
        Optional<Node> targetNode = this.diagramService.findNodeById(diagram, targetNodeId);
        Optional<Object> source = Optional.empty();
        Optional<Object> target = Optional.empty();
        if (sourceNode.isPresent() && targetNode.isPresent()) {
            source = this.objectService.getObject(editingContext, sourceNode.get().getTargetObjectId());
            target = this.objectService.getObject(editingContext, targetNode.get().getTargetObjectId());
        }

        if (source.isPresent() && target.isPresent()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(CreateEdgeTool.EDGE_SOURCE, source.get());
            variableManager.put(CreateEdgeTool.EDGE_TARGET, target.get());

            result = tool.getHandler().apply(variableManager);
        }
        return result;
    }

}
