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
import org.eclipse.sirius.web.diagrams.events.MoveEvent;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.UpdateNodePositionInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.UpdateNodePositionSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Update Node Position" events.
 *
 * @author fbarbin
 */
@Service
public class UpdateNodePositionEventHandler implements IDiagramEventHandler {

    private final ICollaborativeDiagramMessageService messageService;

    private final IDiagramQueryService diagramQueryService;

    private final Counter counter;

    public UpdateNodePositionEventHandler(ICollaborativeDiagramMessageService messageService, IDiagramQueryService diagramQueryService, MeterRegistry meterRegistry) {
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
        return diagramInput instanceof UpdateNodePositionInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, ISemanticRepresentationMetadata diagramMetadata, IDiagramInput diagramInput) {
        this.counter.increment();

        if (diagramInput instanceof UpdateNodePositionInput) {
            this.handleUpdateNodePosition(payloadSink, changeDescriptionSink, diagramContext, (UpdateNodePositionInput) diagramInput);
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), UpdateNodePositionEventHandler.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(diagramInput.getId(), message));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput));
        }
    }

    private void handleUpdateNodePosition(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IDiagramContext diagramContext, UpdateNodePositionInput diagramInput) {
        Position newPosition = Position.at(diagramInput.getNewPositionX(), diagramInput.getNewPositionY());

        Optional<Node> optionalNode = this.diagramQueryService.findNodeById(diagramContext.getDiagram(), diagramInput.getDiagramElementId());

        if (optionalNode.isPresent()) {
            diagramContext.setDiagramEvent(new MoveEvent(diagramInput.getDiagramElementId(), newPosition));
            payloadSink.tryEmitValue(new UpdateNodePositionSuccessPayload(diagramInput.getId(), diagramContext.getDiagram()));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE, diagramInput.getRepresentationId(), diagramInput));
        } else {
            String message = this.messageService.nodeNotFound(String.valueOf(diagramInput.getDiagramElementId()));
            payloadSink.tryEmitValue(new ErrorPayload(diagramInput.getId(), message));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput));
        }
    }

}
