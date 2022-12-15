/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.UpdateEdgeRoutingPointsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.UpdateEdgeRoutingPointsSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.events.UpdateEdgeRoutingPointsEvent;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramBuilder;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests the update edge routing points event handler.
 *
 * @author gcoutable
 */
public class UpdateEdgeRoutingPointsEventHandlerTests {

    @Test
    public void testUpdateEdgeRoutingPoints() {
        String edgeId = UUID.randomUUID().toString();
        Edge edge = new TestDiagramBuilder().getEdge(edgeId, UUID.randomUUID().toString(), UUID.randomUUID().toString());

        IDiagramQueryService diagramQueryService = new IDiagramQueryService.NoOp() {
            @Override
            public Optional<Edge> findEdgeById(Diagram diagram, String edgeId) {
                return Optional.of(edge);
            }
        };

        var handler = new UpdateEdgeRoutingPointsEventHandler(new ICollaborativeDiagramMessageService.NoOp(), diagramQueryService, new SimpleMeterRegistry());
        var input = new UpdateEdgeRoutingPointsInput(UUID.randomUUID(), "editingContextId", "representationId", edgeId, List.of());

        assertThat(handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(UpdateEdgeRoutingPointsSuccessPayload.class);

        assertThat(diagramContext.getDiagramEvent()).isInstanceOf(UpdateEdgeRoutingPointsEvent.class);
    }

}
