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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReconnectEdgeInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.diagrams.tests.graphql.ConnectorToolsQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnTwoDiagramElementsToolMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ReconnectEdgeMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.EdgeDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests for the manipulation of edges.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class EdgeControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EdgeDiagramDescriptionProvider edgeDiagramDescriptionProvider;

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

    private Flux<Object> givenSubscriptionToLabelEditableDiagramDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                this.edgeDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "EdgeDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a diagram with some nodes, when the connectors tools between two nodes are requested, then the available tools are returned")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithSomeNodesWhenTheConnectorToolsBetweenTwoNodesAreRequestedThenTheAvailableToolsAreReturned() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();

        var diagramId = new AtomicReference<String>();
        var siriusWebDomainNodeId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();
        var siriusWebInfrastructureNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
                    siriusWebDomainNodeId.set(siriusWebDomainNode.getId());

                    var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
                    siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());

                    var siriusWebInfrastructureNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode();
                    siriusWebInfrastructureNodeId.set(siriusWebInfrastructureNode.getId());
                }, () -> fail("Missing diagram"));

        Runnable requestValidConnectorTools = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    "representationId", diagramId.get(),
                    "sourceDiagramElementId", siriusWebInfrastructureNodeId.get(),
                    "targetDiagramElementId", siriusWebApplicationNodeId.get()
            );
            var connectorToolsResult = this.connectorToolsQueryRunner.run(variables);
            List<String> connectorToolsLabel = JsonPath.read(connectorToolsResult, "$.data.viewer.editingContext.representation.description.connectorTools[*].label");
            assertThat(connectorToolsLabel).contains("New dependencies");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestValidConnectorTools)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram with some nodes, when the creation of an edge is requested, then the edge is created")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithSomeNodesWhenTheCreationOfAnEdgeIsRequestedThenTheEdgeIsCreated() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();

        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();
        var siriusWebInfrastructureNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
                    siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());

                    var siriusWebInfrastructureNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode();
                    siriusWebInfrastructureNodeId.set(siriusWebInfrastructureNode.getId());

                    assertThat(diagram.getEdges())
                            .filteredOn(edge -> edge.getCenterLabel().getText().equals("sirius-web-infrastructure -> sirius-web-application"))
                            .isEmpty();
                }, () -> fail("Missing diagram"));

        Runnable createDependency = () -> {
            var input = new InvokeSingleClickOnTwoDiagramElementsToolInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    diagramId.get(),
                    siriusWebInfrastructureNodeId.get(),
                    siriusWebApplicationNodeId.get(),
                    0,
                    0,
                    0,
                    0,
                    this.edgeDiagramDescriptionProvider.getNewDependencyToolId()
            );
            var result = this.invokeSingleClickOnTwoDiagramElementsToolMutationRunner.run(input);
            String payloadTypeName = JsonPath.read(result, "$.data.invokeSingleClickOnTwoDiagramElementsTool.__typename");
            assertThat(payloadTypeName).isEqualTo(InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getEdges())
                            .filteredOn(edge -> edge.getCenterLabel().getText().equals("sirius-web-infrastructure -> sirius-web-application"))
                            .hasSize(1);
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(createDependency)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram with some nodes and edges, when the source of an edge is reconnected, then the diagram is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithSomeNodesAndEdgesWhenTheSourceOfAnEdgeIsReconnectedThenTheDiagramIsUpdated() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();

        var diagramId = new AtomicReference<String>();
        var edgeId = new AtomicReference<String>();
        var siriusWebInfrastructureNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    var siriusWebInfrastructureNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode();
                    siriusWebInfrastructureNodeId.set(siriusWebInfrastructureNode.getId());

                    var dependencyEdge = diagram.getEdges().stream()
                            .filter(edge -> edge.getCenterLabel().getText().equals("sirius-web-application -> sirius-web-domain"))
                            .findFirst().orElseThrow(IllegalStateException::new);
                    edgeId.set(dependencyEdge.getId());
                    assertThat(diagram.getEdges())
                            .filteredOn(edge -> edge.getCenterLabel().getText().equals("sirius-web-infrastructure -> sirius-web-domain"))
                            .isEmpty();
                }, () -> fail("Missing diagram"));

        Runnable reconnectSource = () -> {
            var input = new ReconnectEdgeInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    diagramId.get(),
                    edgeId.get(),
                    siriusWebInfrastructureNodeId.get(),
                    ReconnectEdgeKind.SOURCE,
                    Position.UNDEFINED
            );
            var result = this.reconnectEdgeMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.reconnectEdge.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getEdges())
                            .filteredOn(edge -> edge.getCenterLabel().getText().equals("sirius-web-infrastructure -> sirius-web-domain"))
                            .hasSize(1);
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(reconnectSource)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram with some nodes and edges, when the target of an edge is reconnected, then the diagram is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithSomeNodesAndEdgesWhenTheTargetOfAnEdgeIsReconnectedThenTheDiagramIsUpdated() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();

        var diagramId = new AtomicReference<String>();
        var edgeId = new AtomicReference<String>();
        var siriusWebInfrastructureNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    var siriusWebInfrastructureNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode();
                    siriusWebInfrastructureNodeId.set(siriusWebInfrastructureNode.getId());

                    var dependencyEdge = diagram.getEdges().stream()
                            .filter(edge -> edge.getCenterLabel().getText().equals("sirius-web-application -> sirius-web-domain"))
                            .findFirst().orElseThrow(IllegalStateException::new);
                    edgeId.set(dependencyEdge.getId());
                    assertThat(diagram.getEdges())
                            .filteredOn(edge -> edge.getCenterLabel().getText().equals("sirius-web-application -> sirius-web-infrastructure"))
                            .isEmpty();
                }, () -> fail("Missing diagram"));

        Runnable reconnectSource = () -> {
            var input = new ReconnectEdgeInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    diagramId.get(),
                    edgeId.get(),
                    siriusWebInfrastructureNodeId.get(),
                    ReconnectEdgeKind.TARGET,
                    Position.UNDEFINED
            );
            var result = this.reconnectEdgeMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.reconnectEdge.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getEdges())
                            .filteredOn(edge -> edge.getCenterLabel().getText().equals("sirius-web-application -> sirius-web-infrastructure"))
                            .hasSize(1);
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(reconnectSource)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
