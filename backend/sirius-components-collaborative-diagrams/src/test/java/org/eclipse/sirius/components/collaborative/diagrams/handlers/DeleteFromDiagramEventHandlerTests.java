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
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeletionPolicy;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramBuilder;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramDescriptionBuilder;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the delete from diagram event handler.
 *
 * @author sbegaudeau
 */
public class DeleteFromDiagramEventHandlerTests {

    private static final String NODE_ID = "nodeId"; //$NON-NLS-1$

    private static final String EDGE_ID = "edgeId"; //$NON-NLS-1$

    private final IObjectService objectService = new IObjectService.NoOp() {
        @Override
        public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
            return Optional.of(new Object());
        }
    };

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

    private final IDiagramDescriptionService diagramDescriptionService = new IDiagramDescriptionService.NoOp() {
        @Override
        public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, UUID nodeDescriptionId) {
            return Optional.of(new TestDiagramDescriptionBuilder().getNodeDescription(UUID.randomUUID(), variableManager -> List.of()));
        }

        @Override
        public Optional<EdgeDescription> findEdgeDescriptionById(DiagramDescription diagramDescription, UUID edgeDescriptionId) {
            return Optional.of(new TestDiagramDescriptionBuilder().getEdgeDescription(UUID.randomUUID(), this.findNodeDescriptionById(diagramDescription, UUID.randomUUID()).get()));
        }
    };

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
        @Override
        public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
            return Optional.of(new TestDiagramDescriptionBuilder().getDiagramDescription(UUID.randomUUID().toString(), List.of(), List.of(), List.of()));
        }
    };

    @Test
    public void testNodeSemanticDeletionFromDiagram() {
        var handler = new DeleteFromDiagramEventHandler(this.objectService, this.diagramQueryService, this.diagramDescriptionService, this.representationDescriptionSearchService,
                new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry());

        var nodeIds = List.of(NODE_ID);
        var edgeIds = List.<String> of();
        var input = new DeleteFromDiagramInput(UUID.randomUUID(), "editingContextId", "representationId", nodeIds, edgeIds, DeletionPolicy.SEMANTIC); //$NON-NLS-1$ //$NON-NLS-2$

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        assertThat(handler.canHandle(input)).isTrue();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(DeleteFromDiagramSuccessPayload.class);
    }

    @Test
    public void testEdgeSemanticDeletionFromDiagram() {
        var handler = new DeleteFromDiagramEventHandler(this.objectService, this.diagramQueryService, this.diagramDescriptionService, this.representationDescriptionSearchService,
                new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry());

        var nodeIds = List.<String> of();
        var edgeIds = List.<String> of(EDGE_ID);
        var input = new DeleteFromDiagramInput(UUID.randomUUID(), "editingContextId", "representationId", nodeIds, edgeIds, DeletionPolicy.SEMANTIC); //$NON-NLS-1$ //$NON-NLS-2$

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        assertThat(handler.canHandle(input)).isTrue();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(DeleteFromDiagramSuccessPayload.class);
    }
}
