/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetNodeDescriptionBorderNodeDescriptionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetNodeDescriptionsPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramDescriptionBuilder;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Test for {@link GetNodeDescriptionBorderNodeDescriptionsEventHandler}
 *
 * @author frouene
 */
class GetNodeDescriptionBorderNodeDescriptionsEventHandlerTests {

    private static final String DIAGRAM_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String NODE_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String BORDER_NODE_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String CHILD_NODE_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String REUSED_BORDER_NODE_DESCRIPTION_ID = UUID.randomUUID().toString();
    private static final String REUSED_CHILD_NODE_DESCRIPTION_ID = UUID.randomUUID().toString();

    private static final String DIAGRAM_ID = "diagramId";

    @Test
    public void testGetNodeDescriptionBorderNodeDescriptions() {
        var reusedBorderNodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(REUSED_BORDER_NODE_DESCRIPTION_ID, variableManager -> List.of());
        var reusedChildNodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(REUSED_CHILD_NODE_DESCRIPTION_ID, variableManager -> List.of());
        var diagramDescription = new TestDiagramDescriptionBuilder().getDiagramDescription(DIAGRAM_DESCRIPTION_ID, List.of(reusedBorderNodeDescription, reusedChildNodeDescription), List.of(), List.of());

        var borderNodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(BORDER_NODE_DESCRIPTION_ID, variableManager -> List.of());
        var childNodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(CHILD_NODE_DESCRIPTION_ID, variableManager -> List.of());
        var nodeDescription = NodeDescription.newNodeDescription(new TestDiagramDescriptionBuilder().getNodeDescription(NODE_DESCRIPTION_ID, variableManager -> List.of()))
                .borderNodeDescriptions(List.of(borderNodeDescription))
                .childNodeDescriptions(List.of(childNodeDescription))
                .reusedBorderNodeDescriptionIds(List.of(REUSED_BORDER_NODE_DESCRIPTION_ID))
                .reusedChildNodeDescriptionIds(List.of(REUSED_CHILD_NODE_DESCRIPTION_ID))
                .build();

        var representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Map<String, IRepresentationDescription> findAll(IEditingContext editingContext) {
                return Collections.singletonMap(DIAGRAM_DESCRIPTION_ID, diagramDescription);
            }
        };
        var handler = new GetNodeDescriptionBorderNodeDescriptionsEventHandler(representationDescriptionSearchService, new ICollaborativeDiagramMessageService.NoOp(),
                new SimpleMeterRegistry());

        var input = new GetNodeDescriptionBorderNodeDescriptionsInput(UUID.randomUUID(), UUID.randomUUID().toString(), DIAGRAM_ID, nodeDescription);

        assertThat(handler.canHandle(input)).isTrue();

        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        Sinks.One<IPayload> payloadSink = Sinks.one();

        IEditingContext editingContext = () -> UUID.randomUUID().toString();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, new IDiagramContext.NoOp(), input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(GetNodeDescriptionsPayload.class);
        assert payload != null;
        List<NodeDescription> nodeDescriptions = ((GetNodeDescriptionsPayload) payload).nodeDescriptions();
        assertThat(nodeDescriptions).hasSize(2);
        assertThat(nodeDescriptions.stream().map(NodeDescription::getId).toList()).contains(BORDER_NODE_DESCRIPTION_ID);
        assertThat(nodeDescriptions.stream().map(NodeDescription::getId).toList()).contains(REUSED_BORDER_NODE_DESCRIPTION_ID);
    }

}
