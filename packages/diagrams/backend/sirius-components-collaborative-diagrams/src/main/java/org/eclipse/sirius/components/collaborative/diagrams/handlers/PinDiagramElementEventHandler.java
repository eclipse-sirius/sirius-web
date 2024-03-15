/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.PinDiagramElementInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.events.PinDiagramElementEvent;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle 'pin modifier' events.
 *
 * @author frouene
 */
@Service
public class PinDiagramElementEventHandler implements IDiagramEventHandler {

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    private final IDiagramQueryService diagramQueryService;

    public PinDiagramElementEventHandler(ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry, IDiagramQueryService diagramQueryService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof PinDiagramElementInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        if (diagramInput instanceof PinDiagramElementInput pinInput) {
            this.handlePinDiagramElement(payloadSink, changeDescriptionSink, diagramContext, pinInput);
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), PinDiagramElementInput.class.getSimpleName());
            IPayload payload = new ErrorPayload(diagramInput.id(), message);
            ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

            payloadSink.tryEmitValue(payload);
            changeDescriptionSink.tryEmitNext(changeDescription);
        }
    }

    private void handlePinDiagramElement(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IDiagramContext diagramContext, PinDiagramElementInput diagramInput) {
        List<String> errors = new ArrayList<>(diagramInput.elementIds().size());
        Set<String> resolvedIds = new HashSet<>();

        for (String id : diagramInput.elementIds()) {
            Optional<Edge> optionalEdge = this.diagramQueryService.findEdgeById(diagramContext.getDiagram(), id);
            Optional<Node> optionalNode = this.diagramQueryService.findNodeById(diagramContext.getDiagram(), id);

            if (optionalEdge.isPresent() || optionalNode.isPresent()) {
                resolvedIds.add(id);
            } else {
                errors.add(this.messageService.edgeNotFound(id));
                errors.add(this.messageService.nodeNotFound(id));
            }
        }

        if (!resolvedIds.isEmpty()) {
            diagramContext.getDiagramEvents().add(new PinDiagramElementEvent(resolvedIds, diagramInput.pinned()));
        }

        this.sendResponse(payloadSink, changeDescriptionSink, errors, !resolvedIds.isEmpty(), diagramInput);
    }

    private void sendResponse(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, List<String> errors, boolean atLeastOneSuccess,
            PinDiagramElementInput diagramInput) {
        var changeDescription = new ChangeDescription(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE, diagramInput.representationId(), diagramInput);
        IPayload payload = new SuccessPayload(diagramInput.id());
        if (!errors.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.messageService.deleteFailed());
            for (String error : errors) {
                stringBuilder.append(error);
            }

            changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);
            if (atLeastOneSuccess) {
                changeDescription = new ChangeDescription(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE, diagramInput.representationId(), diagramInput);
            }

            payload = new ErrorPayload(diagramInput.id(), stringBuilder.toString());
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
