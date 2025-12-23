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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.api.IExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.services.diagrams.RelationBasedEdgeDiagramDescriptionProvider;
import org.eclipse.sirius.web.services.diagrams.RelationBasedEdgeInstanceCreatedPayload;
import org.eclipse.sirius.web.services.diagrams.RelationBasedEdgeInstanceProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of relation based edges.
 *
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class RelationBasedEdgeControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private IExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @Autowired
    private RelationBasedEdgeDiagramDescriptionProvider relationBasedEdgeDescriptionProvider;

    @Autowired
    private RelationBasedEdgeInstanceProvider relationBasedEdgeInstanceProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToRelationBasedEdgeDiagram(String objectId) {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.SYSML_SAMPLE_EDITING_CONTEXT_ID.toString(),
                this.relationBasedEdgeDescriptionProvider.getRepresentationDescriptionId(),
                objectId,
                "RelationBasedEdgeDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with composition edges, when creating the diagram, then there is only one edge between an Entity and its subEntity")
    public void givenDiagramWithCompoistionEdgesWhenThenItWorksAsExpected() {
        var input = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), TestIdentifiers.SYSML_SAMPLE_EDITING_CONTEXT_ID.toString(), this.relationBasedEdgeInstanceProvider);
        var payload = this.executeEditingContextFunctionRunner.execute(input).block();
        assertThat(payload).isInstanceOf(RelationBasedEdgeInstanceCreatedPayload.class);

        var relationBasedEdgeInstanceCreatedPayload = (RelationBasedEdgeInstanceCreatedPayload) payload;
        var flux = this.givenSubscriptionToRelationBasedEdgeDiagram(relationBasedEdgeInstanceCreatedPayload.objectId());

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var nodes = diagram.getNodes();
            assertThat(nodes).hasSize(3) // 3 root nodes
                .anyMatch(node -> node.getInsideLabel().getText().equals("Entity1"))
                .anyMatch(node -> node.getInsideLabel().getText().equals("Entity2"))
                .anyMatch(node -> node.getInsideLabel().getText().equals("Entity3"));

            var compartmentNodes = nodes.stream().map(Node::getChildNodes).flatMap(List::stream).toList();
            assertThat(compartmentNodes).hasSize(3); // Each root node has a compartment

            var subNodes = compartmentNodes.stream().map(Node::getChildNodes).flatMap(List::stream).toList();
            assertThat(subNodes).hasSize(2); // Entity1 compartment has 1 subNode, Entity2 compartment has 1 subNode, Entity3 compartment has 0 nodes

            var edges = diagram.getEdges();
            assertThat(edges).hasSize(5);

            // two nodes are only linked by one edge
            for (Edge edge : edges) {
                var sourceId = edge.getSourceId();
                var targetId = edge.getTargetId();

                assertThat(edges.stream()
                        .filter(e -> sourceId.equals(e.getSourceId()))
                        .filter(e -> targetId.equals(e.getTargetId())))
                        .hasSize(1);
            }
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
