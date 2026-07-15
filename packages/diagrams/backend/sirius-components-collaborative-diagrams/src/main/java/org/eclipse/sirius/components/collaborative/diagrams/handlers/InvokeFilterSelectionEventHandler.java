/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.InvokeManageVisibilityActionInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.InvokeFilterSelectionInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.InvokeFilterSelectionSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.handlers.api.IFilterSelectionHandler;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handle "InvokeFilterSelectionEvent".
 *
 * @author mcharfadi
 */
@Service
public class InvokeFilterSelectionEventHandler implements IDiagramEventHandler {

    private final List<IFilterSelectionHandler> diagramToolBarFilterSelectionHandlers;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InvokeFilterSelectionEventHandler(List<IFilterSelectionHandler> diagramToolBarFilterSelectionHandlers, ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.diagramToolBarFilterSelectionHandlers = Objects.requireNonNull(diagramToolBarFilterSelectionHandlers);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeFilterSelectionInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeManageVisibilityActionInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof InvokeFilterSelectionInput invokeFilterSelectionInput) {
            var optionalHandler = this.diagramToolBarFilterSelectionHandlers.stream()
                    .filter(handler -> handler.canHandle(editingContext, diagramContext, invokeFilterSelectionInput.filterSelectionId(), invokeFilterSelectionInput.diagramElementIds()))
                    .findFirst();

            if (optionalHandler.isEmpty()) {
                message = this.messageService.actionHandlerNotFound(invokeFilterSelectionInput.filterSelectionId());
                payload = new ErrorPayload(diagramInput.id(), message);
            } else {
                var newSelection = optionalHandler.get().getNewSelection(editingContext, diagramContext,  invokeFilterSelectionInput.filterSelectionId(), invokeFilterSelectionInput.diagramElementIds());
                payload = new InvokeFilterSelectionSuccessPayload(invokeFilterSelectionInput.id(), newSelection, List.of());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}