/*******************************************************************************
 * Copyright (c) 2021, 2024 THALES GLOBAL SERVICES.
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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.UpdateNodeBoundsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.UpdateNodeBoundsSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
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
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        if (diagramInput instanceof UpdateNodeBoundsInput updateNodeBoundsInput) {
            this.handleUpdateNodeBounds(payloadSink, changeDescriptionSink, diagramContext, updateNodeBoundsInput);
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), UpdateNodeBoundsEventHandler.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(diagramInput.id(), message));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput));
        }
    }

    private void handleUpdateNodeBounds(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IDiagramContext diagramContext, UpdateNodeBoundsInput diagramInput) {
        // @formatter:off
        Position newPosition = Position.newPosition()
                .x(diagramInput.newPositionX())
                .y(diagramInput.newPositionY())
                .build();

        Size newSize = Size.newSize()
                .width(diagramInput.newWidth())
                .height(diagramInput.newHeight())
                .build();
        // @formatter:on

        Optional<Node> optionalNode = this.diagramQueryService.findNodeById(diagramContext.getDiagram(), diagramInput.diagramElementId());

        if (optionalNode.isPresent() && newSize.equals(optionalNode.get().getSize())) {
            Position oldPosition = optionalNode.get().getPosition();
            if (diagramContext.getDiagram().getLabel().endsWith("__EXPERIMENTAL")) {
                NodeLayoutData nodeLayoutData = diagramContext.getDiagram().getLayoutData().nodeLayoutData().get(optionalNode.get().getId());
                if (nodeLayoutData != null) {
                    oldPosition = Position.at(nodeLayoutData.position().x(), nodeLayoutData.position().y());
                }
            }
            //@formatter:off
            Position delta = Position.newPosition()
                    .x(oldPosition.getX() - newPosition.getX())
                    .y(oldPosition.getY() - newPosition.getY())
                    .build();
            //@formatter:on
            diagramContext.getDiagramEvents().add(new ResizeEvent(diagramInput.diagramElementId(), delta, newSize));

            payloadSink.tryEmitValue(new UpdateNodeBoundsSuccessPayload(diagramInput.id(), diagramContext.getDiagram()));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE, diagramInput.representationId(), diagramInput));
        } else {
            String message = this.messageService.nodeNotFound(String.valueOf(diagramInput.diagramElementId()));
            payloadSink.tryEmitValue(new ErrorPayload(diagramInput.id(), message));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput));
        }
    }

}
