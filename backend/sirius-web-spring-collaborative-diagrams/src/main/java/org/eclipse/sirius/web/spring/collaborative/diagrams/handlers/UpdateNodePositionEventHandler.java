/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.UpdateNodePositionInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.UpdateNodePositionSuccessPayload;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handle "Update Node Position" events.
 *
 * @author fbarbin
 */
@Service
public class UpdateNodePositionEventHandler implements IDiagramEventHandler {

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public UpdateNodePositionEventHandler(ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof UpdateNodePositionInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        EventHandlerResponse result;
        if (diagramInput instanceof UpdateNodePositionInput) {
            result = this.handleUpdateNodePosition(editingContext, diagramContext, (UpdateNodePositionInput) diagramInput);
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), UpdateNodePositionEventHandler.class.getSimpleName());
            result = new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
        }
        return result;
    }

    private EventHandlerResponse handleUpdateNodePosition(IEditingContext editingContext, IDiagramContext diagramContext, UpdateNodePositionInput diagramInput) {
        // @formatter:off
        Position newPosition = Position.newPosition()
                .x(diagramInput.getNewPositionX())
                .y(diagramInput.getNewPositionY())
                .build();
        // @formatter:on
        diagramContext.getMovedElementIDToNewPositionMap().put(diagramInput.getDiagramElementId(), newPosition);
        return new EventHandlerResponse(true, representation -> true, new UpdateNodePositionSuccessPayload(diagramContext.getDiagram()));
    }
}
