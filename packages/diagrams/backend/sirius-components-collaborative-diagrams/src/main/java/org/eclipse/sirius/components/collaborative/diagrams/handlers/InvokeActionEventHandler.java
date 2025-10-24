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

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IActionHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeActionInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Invoke action" events.
 *
 * @author arichard
 */
@Service
public class InvokeActionEventHandler implements IDiagramEventHandler {

    private final IDiagramQueryService diagramQueryService;

    private final List<IActionHandler> actionHandlers;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InvokeActionEventHandler(IDiagramQueryService diagramQueryService, List<IActionHandler> actionHandlers, ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.actionHandlers = Objects.requireNonNull(actionHandlers);
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
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeActionInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof InvokeActionInput input) {
            var actionId = input.actionId();
            IStatus status = this.executeAction(editingContext, diagramContext, input.diagramElementId(), actionId);
            if (status instanceof Success success) {
                payload = new SuccessPayload(diagramInput.id(), success.getMessages());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
            } else if (status instanceof Failure failure) {
                payload = new ErrorPayload(diagramInput.id(), failure.getMessages());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus executeAction(IEditingContext editingContext, DiagramContext diagramContext, String diagramElementId, String actionId) {
        IStatus result = new Failure(this.messageService.nodeNotFound(diagramElementId));
        var diagram = diagramContext.diagram();
        var optionalNode = this.diagramQueryService.findNodeById(diagram, diagramElementId);
        if (optionalNode.isPresent()) {
            var node = optionalNode.get();
            result = this.actionHandlers.stream()
                    .filter(handler -> handler.canHandle(editingContext, diagramContext, node, actionId))
                    .findFirst()
                    .map(handler -> handler.handle(editingContext, diagramContext, node, actionId))
                    .orElseGet(() -> new Failure(this.messageService.actionHandlerNotFound(actionId)));
        }
        return result;
    }
}
