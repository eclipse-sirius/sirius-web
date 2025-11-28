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

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReconnectEdgeInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.diagrams.tests.graphql.ConnectorToolsQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnTwoDiagramElementsToolMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ReconnectEdgeMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
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
 * Integration tests for the manipulation of edges on edges.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class EdgeOnEdgeControllerTests extends AbstractIntegrationTests {

    public static final String REPRESENTATION_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=83f1194e-9833-398b-80ad-be5d77e02585&sourceElementId=37dc91b6-6550-3337-85a5-6f1dfd02ca4c";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private ConnectorToolsQueryRunner connectorToolsQueryRunner;

    @Autowired
    private InvokeSingleClickOnTwoDiagramElementsToolMutationRunner invokeSingleClickOnTwoDiagramElementsToolMutationRunner;

    @Autowired
    private ReconnectEdgeMutationRunner reconnectEdgeMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToLifeCycleDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                REPRESENTATION_DESCRIPTION_ID,
                PapayaIdentifiers.PAPAYA_SIRIUS_WEB_LIFECYCLE_ROOT_OBJECT.toString(),
                "EdgeOnEdgeDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with an edge connected to another edge, when the diagram payload is received, then the edges are returned")
    public void givenDiagramWithSomeEdgesOnEdgeWhenTheDiagramPayloadIsReceivedThenTheEdgesAreRetured() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var nodeCount = new DiagramNavigator(diagram).findDiagramNodeCount();
            assertThat(nodeCount).isEqualTo(11);

            var edgeCount = new DiagramNavigator(diagram).findDiagramEdgeCount();
            assertThat(edgeCount).isEqualTo(3);

            var edgeOnEdgeSource = new DiagramNavigator(diagram).edgeWithLabel("channel").sourceNode().getNode();
            var edgeOnEdgeTarget = new DiagramNavigator(diagram).edgeWithLabel("channel").targetEdge();
            var edgeSource = edgeOnEdgeTarget.sourceNode().getNode();
            var targetEdge = edgeOnEdgeTarget.targetNode().getNode();

            assertThat(edgeOnEdgeSource.getTargetObjectLabel()).isEqualTo("HTTP");
            assertThat(edgeSource.getTargetObjectLabel()).isEqualTo("Controller1");
            assertThat(targetEdge.getTargetObjectLabel()).isEqualTo("Command1");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some edges, when the creation of an edge on another edge is requested, then the edge is created")
    public void givenDiagramWithSomeNodesWhenTheCreationOfAnEdgeonEdgeFromANodeIsRequestedThenTheEdgeIsCreated() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();

        var diagramId = new AtomicReference<String>();
        var channelNodeId = new AtomicReference<String>();
        var subscriptionEdgeId = new AtomicReference<String>();
        var connectorToolId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var channelNode = new DiagramNavigator(diagram).nodeWithLabel("HTTPS").getNode();
            channelNodeId.set(channelNode.getId());

            var subscriptionEdge = new DiagramNavigator(diagram).edgeWithLabel("listened by").getEdge();
            subscriptionEdgeId.set(subscriptionEdge.getId());

            var edgeCount = new DiagramNavigator(diagram).findDiagramEdgeCount();
            assertThat(edgeCount).isEqualTo(3);
        });

        Runnable requestConnectorTools = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get(),
                    "sourceDiagramElementId", channelNodeId,
                    "targetDiagramElementId", subscriptionEdgeId
            );
            var connectorToolsResult = this.connectorToolsQueryRunner.run(variables);
            List<String> connectorToolsId = JsonPath.read(connectorToolsResult, "$.data.viewer.editingContext.representation.description.connectorTools[*].id");
            assertThat(connectorToolsId).hasSize(1);
            connectorToolId.set(connectorToolsId.get(0));
        };

        Runnable createEdge = () -> {
            var input = new InvokeSingleClickOnTwoDiagramElementsToolInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    channelNodeId.get(),
                    subscriptionEdgeId.get(),
                    0,
                    0,
                    0,
                    0,
                    connectorToolId.get(),
                    List.of()
            );
            var result = this.invokeSingleClickOnTwoDiagramElementsToolMutationRunner.run(input);
            String payloadTypeName = JsonPath.read(result, "$.data.invokeSingleClickOnTwoDiagramElementsTool.__typename");
            assertThat(payloadTypeName).isEqualTo(InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var edgeCount = new DiagramNavigator(diagram).findDiagramEdgeCount();
            assertThat(edgeCount).isEqualTo(4);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestConnectorTools)
                .then(createEdge)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some edges, when the creation of an edge from another edge is requested, then the edge is created")
    public void givenDiagramWithSomeNodesWhenTheCreationOfAnEdgeonEdgeFromAnEdgeIsRequestedThenTheEdgeIsCreated() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();

        var diagramId = new AtomicReference<String>();
        var channelNodeId = new AtomicReference<String>();
        var subscriptionEdgeId = new AtomicReference<String>();
        var connectorToolId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var channelNode = new DiagramNavigator(diagram).nodeWithLabel("HTTPS").getNode();
            channelNodeId.set(channelNode.getId());

            var subscriptionEdge = new DiagramNavigator(diagram).edgeWithLabel("listened by").getEdge();
            subscriptionEdgeId.set(subscriptionEdge.getId());

            var edgeCount = new DiagramNavigator(diagram).findDiagramEdgeCount();
            assertThat(edgeCount).isEqualTo(3);
        });

        Runnable requestConnectorTools = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get(),
                    "sourceDiagramElementId", subscriptionEdgeId,
                    "targetDiagramElementId", channelNodeId
            );
            var connectorToolsResult = this.connectorToolsQueryRunner.run(variables);
            List<String> connectorToolsId = JsonPath.read(connectorToolsResult, "$.data.viewer.editingContext.representation.description.connectorTools[*].id");
            assertThat(connectorToolsId).hasSize(1);
            connectorToolId.set(connectorToolsId.get(0));
        };

        Runnable createEdge = () -> {
            var input = new InvokeSingleClickOnTwoDiagramElementsToolInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    subscriptionEdgeId.get(),
                    channelNodeId.get(),
                    0,
                    0,
                    0,
                    0,
                    connectorToolId.get(),
                    List.of()
            );
            var result = this.invokeSingleClickOnTwoDiagramElementsToolMutationRunner.run(input);
            String payloadTypeName = JsonPath.read(result, "$.data.invokeSingleClickOnTwoDiagramElementsTool.__typename");
            assertThat(payloadTypeName).isEqualTo(InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var edgeCount = new DiagramNavigator(diagram).findDiagramEdgeCount();
            assertThat(edgeCount).isEqualTo(4);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestConnectorTools)
                .then(createEdge)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes and edges, when the source of an edge is reconnected, then the diagram is updated")
    public void givenDiagramWithSomeNodesAndEdgesWhenTheTargetOfAnEdgeIsReconnectedThenTheDiagramIsUpdated() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();

        var diagramId = new AtomicReference<String>();
        var edgeToReconnectId = new AtomicReference<String>();
        var newEdgeTargetId  = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var edgeToReconnect = new DiagramNavigator(diagram).edgeWithLabel("channel").getEdge();
            edgeToReconnectId.set(edgeToReconnect.getId());

            var newEdgeTarget = new DiagramNavigator(diagram).edgeWithLabel("listened by").getEdge();
            newEdgeTargetId.set(newEdgeTarget.getId());
        });

        Runnable reconnectSource = () -> {
            var input = new ReconnectEdgeInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    edgeToReconnectId.get(),
                    newEdgeTargetId.get(),
                    ReconnectEdgeKind.SOURCE
            );
            var result = this.reconnectEdgeMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.reconnectEdge.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var nodeCount = new DiagramNavigator(diagram).findDiagramNodeCount();
            assertThat(nodeCount).isEqualTo(11);

            var edgeCount = new DiagramNavigator(diagram).findDiagramEdgeCount();
            assertThat(edgeCount).isEqualTo(3);

            var edgeOnEdgeSource = new DiagramNavigator(diagram).edgeWithLabel("channel").sourceNode().getNode();
            var edgeOnEdgeTarget = new DiagramNavigator(diagram).edgeWithLabel("channel").targetEdge();
            var edgeSource = edgeOnEdgeTarget.sourceNode().getNode();
            var targetEdge = edgeOnEdgeTarget.targetNode().getNode();

            assertThat(edgeOnEdgeSource.getTargetObjectLabel()).isEqualTo("HTTP");
            assertThat(edgeSource.getTargetObjectLabel()).isEqualTo("Controller2");
            assertThat(targetEdge.getTargetObjectLabel()).isEqualTo("Command2");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(reconnectSource)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}