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

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.events.ResizeEvent;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.EventHandlerResponse;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.UpdateNodeBoundsInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.UpdateNodeBoundsSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handle "Update Node Bounds" events.
 *
 * @author fbarbin
 */
@Service
public class UpdateNodeBoundsEventHandler implements IDiagramEventHandler {

    private final ICollaborativeDiagramMessageService messageService;

    private final IDiagramQueryService diagramQueryService;

    private final Counter counter;

    public UpdateNodeBoundsEventHandler(ICollaborativeDiagramMessageService messageService, IDiagramQueryService diagramQueryService, MeterRegistry meterRegistry) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof UpdateNodeBoundsInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        EventHandlerResponse result;
        if (diagramInput instanceof UpdateNodeBoundsInput) {
            result = this.handleUpdateNodeBounds(diagramContext, (UpdateNodeBoundsInput) diagramInput);
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), UpdateNodeBoundsEventHandler.class.getSimpleName());
            result = new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId()), new ErrorPayload(diagramInput.getId(), message));
        }
        return result;
    }

    private EventHandlerResponse handleUpdateNodeBounds(IDiagramContext diagramContext, UpdateNodeBoundsInput diagramInput) {
        // @formatter:off
        Position newPosition = Position.newPosition()
                .x(diagramInput.getNewPositionX())
                .y(diagramInput.getNewPositionY())
                .build();

        Size newSize = Size.newSize()
                .width(diagramInput.getNewWidth())
                .height(diagramInput.getNewHeight())
                .build();
        // @formatter:on

        Optional<Node> optionalNode = this.diagramQueryService.findNodeById(diagramContext.getDiagram(), diagramInput.getDiagramElementId());

        EventHandlerResponse result;
        if (optionalNode.isPresent()) {
            Position oldPosition = optionalNode.get().getPosition();
            //@formatter:off
            Position delta = Position.newPosition()
                    .x(oldPosition.getX() - newPosition.getX())
                    .y(oldPosition.getY() - newPosition.getY())
                    .build();
            //@formatter:on
            diagramContext.setDiagramEvent(new ResizeEvent(diagramInput.getDiagramElementId(), delta, newSize));
            result = new EventHandlerResponse(new ChangeDescription(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE, diagramInput.getRepresentationId()),
                    new UpdateNodeBoundsSuccessPayload(diagramInput.getId(), diagramContext.getDiagram()));
        } else {
            String message = this.messageService.nodeNotFound(String.valueOf(diagramInput.getDiagramElementId()));
            result = new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId()), new ErrorPayload(diagramInput.getId(), message));
        }
        return result;
    }

}
