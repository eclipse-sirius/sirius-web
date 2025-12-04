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
package org.eclipse.sirius.components.collaborative.diagrams.handlers.appearance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.appearance.EditEdgeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.events.appearance.EditAppearanceEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeLineStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeSourceArrowStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeTargetArrowStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeTypeStyleAppearanceChange;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handles diagram events related to editing an edge appearance.
 *
 * @author mcharfadi
 */
@Service
public class EditEdgeAppearanceEventHandler implements IDiagramEventHandler {

    private final ICollaborativeDiagramMessageService messageService;

    private final IDiagramQueryService diagramQueryService;

    private final Counter counter;

    public EditEdgeAppearanceEventHandler(ICollaborativeDiagramMessageService messageService, IDiagramQueryService diagramQueryService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IDiagramInput diagramInput) {
        return diagramInput instanceof EditEdgeAppearanceInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), EditEdgeAppearanceInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof EditEdgeAppearanceInput editAppearanceInput) {
            List<String> edgeIds = editAppearanceInput.edgeIds();
            List<IAppearanceChange> appearanceChanges = new ArrayList<>();
            List<String> elementNotFoundIds = new ArrayList<>();
            for (String edgeId : edgeIds) {
                Optional<Edge> optionalEdge = diagramQueryService.findEdgeById(diagramContext.diagram(), edgeId);
                if (optionalEdge.isPresent()) {
                    Optional.ofNullable(editAppearanceInput.appearance().size()).ifPresent(size -> appearanceChanges.add(new EdgeSizeAppearanceChange(edgeId, size)));
                    Optional.ofNullable(editAppearanceInput.appearance().color()).ifPresent(color -> appearanceChanges.add(new EdgeColorAppearanceChange(edgeId, color)));
                    Optional.ofNullable(editAppearanceInput.appearance().lineStyle()).ifPresent(linestyle -> appearanceChanges.add(new EdgeLineStyleAppearanceChange(edgeId, linestyle)));
                    Optional.ofNullable(editAppearanceInput.appearance().sourceArrowStyle())
                            .ifPresent(sourceArrowStyle -> appearanceChanges.add(new EdgeSourceArrowStyleAppearanceChange(edgeId, sourceArrowStyle)));
                    Optional.ofNullable(editAppearanceInput.appearance().targetArrowStyle())
                            .ifPresent(targetArrowStyle -> appearanceChanges.add(new EdgeTargetArrowStyleAppearanceChange(edgeId, targetArrowStyle)));
                    Optional.ofNullable(editAppearanceInput.appearance().edgeType()).ifPresent(edgeType -> appearanceChanges.add(new EdgeTypeStyleAppearanceChange(edgeId, edgeType)));

                } else {
                    elementNotFoundIds.add(edgeId);
                }
            }
            if (!elementNotFoundIds.isEmpty()) {
                String nodeNotFoundMessage = this.messageService.nodeNotFound(String.join(" - ", elementNotFoundIds));
                payload = new ErrorPayload(diagramInput.id(), nodeNotFoundMessage);
            } else {
                diagramContext.diagramEvents().add(new EditAppearanceEvent(appearanceChanges));
                payload = new SuccessPayload(diagramInput.id());
                changeDescription = new ChangeDescription(DiagramChangeKind.DIAGRAM_APPEARANCE_CHANGE, diagramInput.representationId(), diagramInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
