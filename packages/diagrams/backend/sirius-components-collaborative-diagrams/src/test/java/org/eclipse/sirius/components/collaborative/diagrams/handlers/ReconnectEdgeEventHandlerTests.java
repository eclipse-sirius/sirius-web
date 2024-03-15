/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IReconnectionToolsExecutor;
import org.eclipse.sirius.components.collaborative.diagrams.api.ReconnectionToolInterpreterData;
import org.eclipse.sirius.components.collaborative.diagrams.configuration.DiagramEventHandlerConfiguration;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReconnectEdgeInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeEvent;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to test the execution of an edge reconnection.
 *
 * @author gcoutable
 */
public class ReconnectEdgeEventHandlerTests {

    @Test
    public void testReconnectEdge() {
        String edgeId = UUID.randomUUID().toString();
        String sourceEdgeEndId = UUID.randomUUID().toString();
        String previousEdgeEndId = UUID.randomUUID().toString();
        String newEdgeEndId = UUID.randomUUID().toString();
        Edge edge = new TestDiagramBuilder().getEdge(edgeId, sourceEdgeEndId, previousEdgeEndId);
        Node sourceEdgeEnd = new TestDiagramBuilder().getNode(sourceEdgeEndId, true);
        Node previousEdgeEnd = new TestDiagramBuilder().getNode(previousEdgeEndId, true);
        Node newEdgeEnd = new TestDiagramBuilder().getNode(newEdgeEndId, true);

        Diagram initialDiagram = new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString());

        Diagram diagram = Diagram.newDiagram(initialDiagram)
                .edges(List.of(edge))
                .nodes(List.of(sourceEdgeEnd, previousEdgeEnd, newEdgeEnd))
                .build();

        var diagramQueryService = new IDiagramQueryService.NoOp() {

            @Override
            public Optional<Edge> findEdgeById(Diagram diagram, String edgeId) {
                return Optional.of(edge);
            }
        };

        var diagramDescriptionService = new IDiagramDescriptionService.NoOp() {

            @Override
            public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, String nodeDescriptionId) {
                return Optional.of(new TestDiagramDescriptionBuilder().getNodeDescription(UUID.randomUUID().toString(), variableManager -> List.of()));
            }

            @Override
            public Optional<EdgeDescription> findEdgeDescriptionById(DiagramDescription diagramDescription, String edgeDescriptionId) {
                return Optional.of(new TestDiagramDescriptionBuilder().getEdgeDescription(UUID.randomUUID().toString(), this.findNodeDescriptionById(diagramDescription, UUID.randomUUID().toString())
                        .get()));
            }
        };

        var representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {

            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                return Optional.of(new TestDiagramDescriptionBuilder().getDiagramDescription(UUID.randomUUID().toString(), List.of(), List.of(), List.of()));
            }
        };

        var objectService = new IObjectService.NoOp() {

            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };
        var reconnectionToolExecutor = new IReconnectionToolsExecutor.NoOp() {
            @Override
            public boolean canExecute(DiagramDescription diagramDescription) {
                return true;
            }

            @Override
            public IStatus execute(IEditingContext editingContext, ReconnectionToolInterpreterData toolInterpreterData, Edge edge, EdgeDescription edgeDescription, ReconnectEdgeKind reconnectEdgeKind,
                    DiagramDescription diagramDescription) {
                return new Success();
            }
        };
        var messageService = new ICollaborativeDiagramMessageService.NoOp();

        var handler = new ReconnectEdgeEventHandler(new DiagramEventHandlerConfiguration(objectService, diagramQueryService, diagramDescriptionService, representationDescriptionSearchService,
                messageService, new IFeedbackMessageService.NoOp()), List.of(reconnectionToolExecutor), new SimpleMeterRegistry());
        var input = new ReconnectEdgeInput(UUID.randomUUID(), "editingContextId", "representationId", edgeId, newEdgeEndId, ReconnectEdgeKind.TARGET, Position.UNDEFINED);

        assertThat(handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        DiagramContext diagramContext = new DiagramContext(diagram);

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        assertThat(diagramContext.getDiagramEvents()).hasSize(1);
        assertThat(diagramContext.getDiagramEvents().get(0)).isInstanceOf(ReconnectEdgeEvent.class);
    }

}
