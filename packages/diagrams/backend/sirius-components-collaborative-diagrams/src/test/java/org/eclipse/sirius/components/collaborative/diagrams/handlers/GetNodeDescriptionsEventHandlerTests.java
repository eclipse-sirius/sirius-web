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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetNodeDescriptionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetNodeDescriptionsSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramDescriptionBuilder;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Test for {@link GetNodeDescriptionsEventHandler}.
 *
 * @author arichard
 */
public class GetNodeDescriptionsEventHandlerTests {

    private static final String ANOTHER_DIAGRAM_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String DIAGRAM_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String NODE_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String BORDER_NODE_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String CHILD_NODE_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String REUSED_BORDER_NODE_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String REUSED_CHILD_NODE_DESCRIPTION_ID = UUID.randomUUID().toString();

    private static final String DIAGRAM_ID = "diagramId";

    @Test
    public void testGetNodeDescriptions() {
        var reusedBorderNodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(REUSED_BORDER_NODE_DESCRIPTION_ID, variableManager -> List.of());
        var reusedChildNodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(REUSED_CHILD_NODE_DESCRIPTION_ID, variableManager -> List.of());
        var borderNodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(BORDER_NODE_DESCRIPTION_ID, variableManager -> List.of());
        var childNodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(CHILD_NODE_DESCRIPTION_ID, variableManager -> List.of());
        var nodeDescription = NodeDescription.newNodeDescription(new TestDiagramDescriptionBuilder().getNodeDescription(NODE_DESCRIPTION_ID, variableManager -> List.of()))
                .borderNodeDescriptions(List.of(borderNodeDescription))
                .childNodeDescriptions(List.of(childNodeDescription))
                .reusedBorderNodeDescriptionIds(List.of(REUSED_BORDER_NODE_DESCRIPTION_ID))
                .reusedChildNodeDescriptionIds(List.of(REUSED_CHILD_NODE_DESCRIPTION_ID))
                .build();

        var diagramDescription = new TestDiagramDescriptionBuilder().getDiagramDescription(DIAGRAM_DESCRIPTION_ID, List.of(nodeDescription), List.of(), List.of());
        var anotherDiagramDescription = new TestDiagramDescriptionBuilder().getDiagramDescription(ANOTHER_DIAGRAM_DESCRIPTION_ID, List.of(reusedBorderNodeDescription, reusedChildNodeDescription), List.of(), List.of());

        var representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                var desc = Optional.<IRepresentationDescription>empty();
                if (representationDescriptionId.equals(diagramDescription.getId())) {
                    desc = Optional.of(diagramDescription);
                } else if (representationDescriptionId.equals(anotherDiagramDescription.getId())) {
                    desc = Optional.of(anotherDiagramDescription);
                }
                return desc;
            }
            @Override
            public Map<String, IRepresentationDescription> findAll(IEditingContext editingContext) {
                var descriptions = new HashMap<String, IRepresentationDescription>();
                descriptions.put(DIAGRAM_DESCRIPTION_ID, diagramDescription);
                descriptions.put(ANOTHER_DIAGRAM_DESCRIPTION_ID, anotherDiagramDescription);
                return descriptions;
            }
        };
        var handler = new GetNodeDescriptionsEventHandler(representationDescriptionSearchService, new ICollaborativeDiagramMessageService.NoOp(),
                new SimpleMeterRegistry());

        var input = new GetNodeDescriptionsInput(UUID.randomUUID(), UUID.randomUUID().toString(), DIAGRAM_ID);

        assertThat(handler.canHandle(input)).isTrue();

        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        Sinks.One<IPayload> payloadSink = Sinks.one();

        IEditingContext editingContext = () -> UUID.randomUUID().toString();
        IDiagramContext.NoOp diagramContext = new IDiagramContext.NoOp() {
            @Override
            public Diagram getDiagram() {
                return Diagram.newDiagram(UUID.randomUUID().toString())
                        .descriptionId(DIAGRAM_DESCRIPTION_ID)
                        .edges(List.of())
                        .label("")
                        .layoutData(new DiagramLayoutData(Map.of(), Map.of(), Map.of()))
                        .position(Position.UNDEFINED)
                        .nodes(List.of())
                        .size(Size.UNDEFINED)
                        .targetObjectId("")
                        .build();
            }
        };
        handler.handle(payloadSink, changeDescriptionSink, editingContext, diagramContext, input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(GetNodeDescriptionsSuccessPayload.class);
        assert payload != null;
        List<NodeDescription> nodeDescriptions = ((GetNodeDescriptionsSuccessPayload) payload).nodeDescriptions();
        assertThat(nodeDescriptions).hasSize(5);
    }
}
