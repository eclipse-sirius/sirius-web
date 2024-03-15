/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodeInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Used to test dropping nodes.
 *
 * @author frouene
 */
public class DropNodeEventHandlerTests {

    private static final String DROPPED_ELEMENT_ID = "droppedElementId";

    private static final String TARGET_ELEMENT_ID = "targetElementId";

    private final IObjectService objectService = new IObjectService.NoOp() {
        @Override
        public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
            return Optional.of(new Object());
        }
    };

    private final IDiagramQueryService diagramQueryService = new IDiagramQueryService.NoOp() {
        @Override
        public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
            return Optional.of(new TestDiagramBuilder().getNode(DROPPED_ELEMENT_ID, true));
        }
    };

    private final AtomicBoolean hasBeenExecuted = new AtomicBoolean();

    private final Function<VariableManager, IStatus> dropNodeHandler = variableManager -> {
        assertThat(variableManager).isNotNull();
        assertThat(variableManager.getVariables()).hasSize(8);
        assertThat(variableManager.getVariables()).containsKey(Environment.ENVIRONMENT);
        assertThat(variableManager.getVariables()).containsKey(IEditingContext.EDITING_CONTEXT);
        assertThat(variableManager.getVariables()).containsKey(IDiagramContext.DIAGRAM_CONTEXT);
        assertThat(variableManager.getVariables()).containsKey(IDiagramService.DIAGRAM_SERVICES);
        assertThat(variableManager.getVariables()).containsKey("droppedNode");
        assertThat(variableManager.getVariables()).containsKey("droppedElement");
        assertThat(variableManager.getVariables()).containsKey("targetNode");
        assertThat(variableManager.getVariables()).containsKey("targetElement");
        this.hasBeenExecuted.set(true);
        return new Success();
    };

    private final IDiagramDescriptionService diagramDescriptionService = new IDiagramDescriptionService.NoOp() {
        @Override
        public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, String nodeDescriptionId) {
            var baseNodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(UUID.randomUUID().toString(), variableManager -> List.of());
            return Optional.of(NodeDescription.newNodeDescription(baseNodeDescription).dropNodeHandler(DropNodeEventHandlerTests.this.dropNodeHandler).build());
        }

    };

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
        @Override
        public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
            var baseDiagramDescription = new TestDiagramDescriptionBuilder().getDiagramDescription(UUID.randomUUID().toString(), List.of(), List.of(), List.of());
            return Optional.of(DiagramDescription.newDiagramDescription(baseDiagramDescription).dropNodeHandler(DropNodeEventHandlerTests.this.dropNodeHandler).build());
        }
    };

    @Test
    public void testDropNodeToDiagram() {
        var handler = new DropNodeEventHandler(this.objectService, this.diagramQueryService, this.diagramDescriptionService, this.representationDescriptionSearchService,
                new ICollaborativeDiagramMessageService.NoOp(), new IFeedbackMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new DropNodeInput(UUID.randomUUID(), "editingContextId", "representationId", DROPPED_ELEMENT_ID, null, 0, 0);

        Sinks.One<IPayload> payloadSink = Sinks.one();
        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        assertThat(handler.canHandle(input)).isTrue();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        assertThat(this.hasBeenExecuted.get()).isTrue();
    }

    @Test
    public void testDropNodeToOtherNode() {
        var handler = new DropNodeEventHandler(this.objectService, this.diagramQueryService, this.diagramDescriptionService, this.representationDescriptionSearchService,
                new ICollaborativeDiagramMessageService.NoOp(), new IFeedbackMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new DropNodeInput(UUID.randomUUID(), "editingContextId", "representationId", DROPPED_ELEMENT_ID, TARGET_ELEMENT_ID, 0, 0);

        Sinks.One<IPayload> payloadSink = Sinks.one();
        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        assertThat(handler.canHandle(input)).isTrue();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        assertThat(this.hasBeenExecuted.get()).isTrue();
    }

}
