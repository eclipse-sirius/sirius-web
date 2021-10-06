/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.tools.DeleteTool;
import org.eclipse.sirius.web.representations.Failure;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.InvokeDeleteToolOnDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.InvokeDeleteToolOnDiagramSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.InvokeNodeToolOnDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Invoke delete tool on diagram" events.
 *
 * @author arichard
 */
@Service
public class InvokeDeleteToolOnDiagramEventHandler implements IDiagramEventHandler {

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IToolService toolService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InvokeDeleteToolOnDiagramEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IToolService toolService, ICollaborativeDiagramMessageService messageService,
            MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
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
        return diagramInput instanceof InvokeDeleteToolOnDiagramInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeNodeToolOnDiagramInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput);

        if (diagramInput instanceof InvokeDeleteToolOnDiagramInput) {
            InvokeDeleteToolOnDiagramInput input = (InvokeDeleteToolOnDiagramInput) diagramInput;
            Diagram diagram = diagramContext.getDiagram();
            // @formatter:off
            var optionalTool = this.toolService.findToolById(editingContext, diagram, input.getToolId())
                    .filter(DeleteTool.class::isInstance)
                    .map(DeleteTool.class::cast);
            // @formatter:on
            if (optionalTool.isPresent()) {
                IStatus status = this.executeTool(editingContext, diagramContext, input.getDiagramElementId(), optionalTool.get());
                if (status instanceof Success) {

                    payload = new InvokeDeleteToolOnDiagramSuccessPayload(diagramInput.getId(), diagram);
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.getRepresentationId(), diagramInput);
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus executeTool(IEditingContext editingContext, IDiagramContext diagramContext, String diagramElementId, DeleteTool tool) {
        IStatus result = new Failure(""); //$NON-NLS-1$
        Diagram diagram = diagramContext.getDiagram();
        Optional<Node> node = this.diagramQueryService.findNodeById(diagram, diagramElementId);
        Optional<Object> self = Optional.empty();
        if (node.isPresent()) {
            self = this.objectService.getObject(editingContext, node.get().getTargetObjectId());
        }

        if (self.isPresent()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(VariableManager.SELF, self.get());
            variableManager.put(Node.SELECTED_NODE, node.get());
            result = tool.getHandler().apply(variableManager);
        }
        return result;
    }

}
