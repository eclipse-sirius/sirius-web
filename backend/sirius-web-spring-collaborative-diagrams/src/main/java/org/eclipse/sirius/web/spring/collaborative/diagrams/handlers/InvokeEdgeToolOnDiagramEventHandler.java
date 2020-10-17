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

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.InvokeEdgeToolOnDiagramInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.InvokeEdgeToolOnDiagramSuccessPayload;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.tools.CreateEdgeTool;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.springframework.stereotype.Service;

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

    public InvokeEdgeToolOnDiagramEventHandler(IObjectService objectService, IDiagramService diagramService, IToolService toolService, ICollaborativeDiagramMessageService messageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramService = Objects.requireNonNull(diagramService);
        this.toolService = Objects.requireNonNull(toolService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeEdgeToolOnDiagramInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, Diagram diagram, IDiagramInput diagramInput) {
        if (diagramInput instanceof InvokeEdgeToolOnDiagramInput) {
            InvokeEdgeToolOnDiagramInput input = (InvokeEdgeToolOnDiagramInput) diagramInput;
            // @formatter:off
            var optionalTool = this.toolService.findToolById(diagram, input.getToolId())
                    .filter(CreateEdgeTool.class::isInstance)
                    .map(CreateEdgeTool.class::cast);
            // @formatter:on
            if (optionalTool.isPresent()) {
                Status status = this.executeTool(editingContext, diagram, input.getDiagramSourceElementId(), input.getDiagramTargetElementId(), optionalTool.get());
                if (Objects.equals(status, Status.OK)) {
                    return new EventHandlerResponse(true, representation -> true, new InvokeEdgeToolOnDiagramSuccessPayload(diagram));
                }
            }
        }
        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeEdgeToolOnDiagramInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

    private Status executeTool(IEditingContext editingContext, Diagram diagram, String diagramSourceElementId, String diagramTargetElementId, CreateEdgeTool tool) {
        Status result = Status.ERROR;
        Optional<Node> sourceNode = this.diagramService.findNodeById(diagram, diagramSourceElementId);
        Optional<Node> targetNode = this.diagramService.findNodeById(diagram, diagramTargetElementId);
        Optional<Object> source = Optional.empty();
        Optional<Object> target = Optional.empty();
        if (sourceNode.isPresent() && targetNode.isPresent()) {
            source = this.objectService.getObject(editingContext, sourceNode.get().getTargetObjectId());
            target = this.objectService.getObject(editingContext, targetNode.get().getTargetObjectId());
        }

        if (source.isPresent() && target.isPresent()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(CreateEdgeTool.EDGE_SOURCE, source.get());
            variableManager.put(CreateEdgeTool.EDGE_TARGET, target.get());

            result = tool.getHandler().apply(variableManager);
        }
        return result;
    }

}
