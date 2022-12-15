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

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.FadeDiagramElementInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.FadeDiagramElementSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.HideDiagramElementInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.HideDiagramElementSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramBuilder;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the hide and fade from diagram event handlers.
 *
 * @author tgiraudet
 */
public class ChangeVisibilityEventHandlerTests {

    private static final String NODE_ID = "nodeId";

    private static final String EDGE_ID = "edgeId";

    private static final String EDITING_CONTEXT_ID = "editingContextId";

    private static final String REPRESENTATION_ID = "representationId";

    private final IDiagramQueryService diagramQueryService = new IDiagramQueryService.NoOp() {
        @Override
        public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
            return Optional.of(new TestDiagramBuilder().getNode(NODE_ID));
        }

        @Override
        public Optional<Edge> findEdgeById(Diagram diagram, String edgeId) {
            return Optional.of(new TestDiagramBuilder().getEdge(EDGE_ID, NODE_ID, NODE_ID));
        }
    };

    @Test
    public void testHideElement() {
        var handler = new HideDiagramElementEventHandler(new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry(), this.diagramQueryService);

        var input = new HideDiagramElementInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, Set.of(NODE_ID), true);

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        assertThat(handler.canHandle(input)).isTrue();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(DiagramChangeKind.DIAGRAM_ELEMENT_VISIBILITY_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(HideDiagramElementSuccessPayload.class);
    }

    @Test
    public void testUnhideElement() {
        var handler = new HideDiagramElementEventHandler(new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry(), this.diagramQueryService);

        var input = new HideDiagramElementInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, Set.of(NODE_ID), false);

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        assertThat(handler.canHandle(input)).isTrue();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(DiagramChangeKind.DIAGRAM_ELEMENT_VISIBILITY_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(HideDiagramElementSuccessPayload.class);
    }

    @Test
    public void testFadeElement() {
        var handler = new FadeDiagramElementEventHandler(new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry(), this.diagramQueryService);

        var input = new FadeDiagramElementInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, Set.of(NODE_ID), true);

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        assertThat(handler.canHandle(input)).isTrue();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(DiagramChangeKind.DIAGRAM_ELEMENT_VISIBILITY_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(FadeDiagramElementSuccessPayload.class);
    }

    @Test
    public void testUnfadeElement() {
        var handler = new FadeDiagramElementEventHandler(new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry(), this.diagramQueryService);

        var input = new FadeDiagramElementInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, Set.of(NODE_ID), false);

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        assertThat(handler.canHandle(input)).isTrue();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(DiagramChangeKind.DIAGRAM_ELEMENT_VISIBILITY_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(FadeDiagramElementSuccessPayload.class);
    }

}
