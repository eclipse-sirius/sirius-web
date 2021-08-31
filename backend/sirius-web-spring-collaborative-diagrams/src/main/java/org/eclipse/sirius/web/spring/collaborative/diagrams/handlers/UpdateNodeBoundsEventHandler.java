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
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.events.ResizeEvent;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
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
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

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
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, ISemanticRepresentationMetadata diagramMetadata, IDiagramInput diagramInput) {
        this.counter.increment();

        if (diagramInput instanceof UpdateNodeBoundsInput) {
            this.handleUpdateNodeBounds(payloadSink, changeDescriptionSink, diagramContext, (UpdateNodeBoundsInput) diagramInput);
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), UpdateNodeBoundsEventHandler.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(diagramInput.getId(), message));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput));
        }
    }

    private void handleUpdateNodeBounds(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IDiagramContext diagramContext, UpdateNodeBoundsInput diagramInput) {
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

        if (optionalNode.isPresent()) {
            Position oldPosition = optionalNode.get().getPosition();
            //@formatter:off
            Position delta = Position.newPosition()
                    .x(oldPosition.getX() - newPosition.getX())
                    .y(oldPosition.getY() - newPosition.getY())
                    .build();
            //@formatter:on
            diagramContext.setDiagramEvent(new ResizeEvent(diagramInput.getDiagramElementId(), delta, newSize));

            payloadSink.tryEmitValue(new UpdateNodeBoundsSuccessPayload(diagramInput.getId(), diagramContext.getDiagram()));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE, diagramInput.getRepresentationId(), diagramInput));
        } else {
            String message = this.messageService.nodeNotFound(String.valueOf(diagramInput.getDiagramElementId()));
            payloadSink.tryEmitValue(new ErrorPayload(diagramInput.getId(), message));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput));
        }
    }

}
