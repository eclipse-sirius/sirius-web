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
import java.util.Optional;

import org.eclipse.sirius.web.collaborative.api.services.ChangeDescription;
import org.eclipse.sirius.web.collaborative.api.services.ChangeKind;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.UpdateNodePositionInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.UpdateNodePositionSuccessPayload;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.events.MoveEvent;
import org.eclipse.sirius.web.diagrams.services.api.IDiagramService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.DiagramChangeKind;
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

    private final IDiagramService diagramService;

    private final Counter counter;

    public UpdateNodePositionEventHandler(ICollaborativeDiagramMessageService messageService, IDiagramService diagramService, MeterRegistry meterRegistry) {
        this.diagramService = Objects.requireNonNull(diagramService);
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
            result = this.handleUpdateNodePosition(diagramContext, (UpdateNodePositionInput) diagramInput);
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), UpdateNodePositionEventHandler.class.getSimpleName());
            result = new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId()), new ErrorPayload(diagramInput.getId(), message));
        }
        return result;
    }

    private EventHandlerResponse handleUpdateNodePosition(IDiagramContext diagramContext, UpdateNodePositionInput diagramInput) {
        Position newPosition = Position.at(diagramInput.getNewPositionX(), diagramInput.getNewPositionY());

        Optional<Node> optionalNode = this.diagramService.findNodeById(diagramContext.getDiagram(), diagramInput.getDiagramElementId());

        EventHandlerResponse result;
        if (optionalNode.isPresent()) {
            diagramContext.setDiagramEvent(new MoveEvent(diagramInput.getDiagramElementId(), newPosition));
            result = new EventHandlerResponse(new ChangeDescription(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE, diagramInput.getRepresentationId()),
                    new UpdateNodePositionSuccessPayload(diagramInput.getId(), diagramContext.getDiagram()));
        } else {
            String message = this.messageService.nodeNotFound(String.valueOf(diagramInput.getDiagramElementId()));
            result = new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId()), new ErrorPayload(diagramInput.getId(), message));
        }
        return result;
    }

}
