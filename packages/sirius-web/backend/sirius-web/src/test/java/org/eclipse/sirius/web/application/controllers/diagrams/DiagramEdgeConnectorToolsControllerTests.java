/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramEventInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.tests.graphql.ConnectorPaletteExecutor;
import org.eclipse.sirius.components.diagrams.tests.graphql.DiagramEventSubscriptionRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnTwoDiagramElementsToolMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.EdgeOnEdgeIdentifiers;
import org.eclipse.sirius.web.e2e.tests.diagramEdge.DiagramEdgeViewProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Tests connector tools contributed by DiagramEdgeViewProvider.
 *
 * @author mcharfadi
 */
@Transactional
@ActiveProfiles("e2e")
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DiagramEdgeConnectorToolsControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private DiagramEventSubscriptionRunner diagramEventSubscriptionRunner;

    @Autowired
    private DiagramEdgeViewProvider diagramEdgeViewProvider;

    @Autowired
    private IDiagramIdProvider diagramIdProvider;

    @Autowired
    private ConnectorPaletteExecutor connectorPaletteExecutor;

    @Autowired
    private InvokeSingleClickOnTwoDiagramElementsToolMutationRunner invokeToolMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given Entity1 and Entity2, when connector tools are requested and invoked, then both edges are created")
    public void givenTwoEntitiesWhenConnectorToolsAreInvokedThenEdgesAreCreated() {
        var subscriptionInput = new DiagramEventInput(UUID.randomUUID(), EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, EdgeOnEdgeIdentifiers.DIAGRAM_REPRESENTATION_ID);
        var subscriptionResult = this.diagramEventSubscriptionRunner.run(subscriptionInput);
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
        var flux = subscriptionResult.flux();

        var description = (DiagramDescription) this.diagramEdgeViewProvider.createViews().getFirst().getDescriptions().getFirst();
        var expectedDescriptionIds = description.getEdgeDescriptions().stream()
                .collect(Collectors.toMap(EdgeDescription::getName, this.diagramIdProvider::getId));

        var diagramId = new AtomicReference<String>();
        var sourceId = new AtomicReference<String>();
        var targetId = new AtomicReference<String>();
        var initialEdgeId = new AtomicReference<String>();
        var toolIds = new AtomicReference<List<String>>();

        Consumer<Object> initialDiagram = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            var navigator = new DiagramNavigator(diagram);
            sourceId.set(navigator.nodeWithLabel("Entity1").getNode().getId());
            targetId.set(navigator.nodeWithLabel("Entity2_A").getNode().getId());
            initialEdgeId.set(navigator.edgeWithLabel("E1TOE2A").getEdge().getId());
            assertThat(navigator.findDiagramEdgeCount()).isEqualTo(1);
        });

        Runnable requestTools = () -> {
            var palette = this.connectorPaletteExecutor.execute(EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, diagramId.get(), sourceId.get(), targetId.get());
            palette.hasTypeName(typeName -> assertThat(typeName).isEqualTo("Palette"))
                    .hasPaletteEntriesLabel(labels -> assertThat(labels).containsExactly("E1toE2A", "E1toE2B"))
                    .hasPaletteEntriesId(ids -> toolIds.set(ids.subList(0, 2)));
            assertThat(toolIds.get()).hasSize(2);
        };

        Runnable createFirstEdge = () -> {
            var input = new InvokeSingleClickOnTwoDiagramElementsToolInput(UUID.randomUUID(), EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, diagramId.get(),
                    sourceId.get(), targetId.get(), 0, 0, 0, 0, toolIds.get().get(0), List.of());
            var result = this.invokeToolMutationRunner.run(input);
            String payloadTypeName = JsonPath.read(result.data(), "$.data.invokeSingleClickOnTwoDiagramElementsTool.__typename");
            assertThat(payloadTypeName).isEqualTo(InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> diagramAfterFirstEdge = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).findDiagramEdgeCount()).isEqualTo(2);
            var newEdges = diagram.getEdges().stream().filter(edge -> !edge.getId().equals(initialEdgeId.get())).toList();
            assertThat(newEdges).hasSize(1).allSatisfy(edge -> {
                assertThat(edge.getSourceId()).isEqualTo(sourceId.get());
                assertThat(edge.getTargetId()).isEqualTo(targetId.get());
            });
            assertThat(newEdges).extracting(Edge::getDescriptionId)
                    .containsExactlyInAnyOrder(expectedDescriptionIds.get("E1toE2A"));
        });

        Runnable createSecondEdge = () -> {
            var input = new InvokeSingleClickOnTwoDiagramElementsToolInput(UUID.randomUUID(), EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, diagramId.get(),
                    sourceId.get(), targetId.get(), 0, 0, 0, 0, toolIds.get().get(1), List.of());
            var result = this.invokeToolMutationRunner.run(input);
            String payloadTypeName = JsonPath.read(result.data(), "$.data.invokeSingleClickOnTwoDiagramElementsTool.__typename");
            assertThat(payloadTypeName).isEqualTo(InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> diagramAfterSecondEdge = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).findDiagramEdgeCount()).isEqualTo(3);
            var newEdges = diagram.getEdges().stream().filter(edge -> !edge.getId().equals(initialEdgeId.get())).toList();
            assertThat(newEdges).hasSize(2).allSatisfy(edge -> {
                assertThat(edge.getSourceId()).isEqualTo(sourceId.get());
                assertThat(edge.getTargetId()).isEqualTo(targetId.get());
            });
            assertThat(newEdges).extracting(Edge::getDescriptionId)
                    .containsExactlyInAnyOrder(expectedDescriptionIds.get("E1toE2A"), expectedDescriptionIds.get("E1toE2B"));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagram)
                .then(requestTools)
                .then(createFirstEdge)
                .consumeNextWith(diagramAfterFirstEdge)
                .then(createSecondEdge)
                .consumeNextWith(diagramAfterSecondEdge)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an existing edge and Entity2, when the connector tool is invoked, then an edge is created")
    public void givenEdgeAndEntityWhenConnectorToolIsInvokedThenEdgeIsCreated() {
        var subscriptionInput = new DiagramEventInput(UUID.randomUUID(), EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, EdgeOnEdgeIdentifiers.DIAGRAM_REPRESENTATION_ID);
        var subscriptionResult = this.diagramEventSubscriptionRunner.run(subscriptionInput);
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
        var flux = subscriptionResult.flux();

        var description = (DiagramDescription) this.diagramEdgeViewProvider.createViews().getFirst().getDescriptions().getFirst();
        var expectedDescriptionIds = description.getEdgeDescriptions().stream()
                .collect(Collectors.toMap(EdgeDescription::getName, this.diagramIdProvider::getId));

        var diagramId = new AtomicReference<String>();
        var sourceId = new AtomicReference<String>();
        var targetId = new AtomicReference<String>();
        var initialEdgeId = new AtomicReference<String>();
        var toolIds = new AtomicReference<List<String>>();

        Consumer<Object> initialDiagram = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            var navigator = new DiagramNavigator(diagram);
            sourceId.set(navigator.edgeWithLabel("E1TOE2A").getEdge().getId());
            targetId.set(navigator.nodeWithLabel("Entity3_B").getNode().getId());
            initialEdgeId.set(navigator.edgeWithLabel("E1TOE2A").getEdge().getId());
            assertThat(navigator.findDiagramEdgeCount()).isEqualTo(1);
        });

        Runnable requestTools = () -> {
            var palette = this.connectorPaletteExecutor.execute(EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, diagramId.get(), sourceId.get(), targetId.get());
            palette.hasTypeName(typeName -> assertThat(typeName).isEqualTo("SingleClickOnTwoDiagramElementsTool"))
                    .hasToolLabel(label -> assertThat(label).isEqualTo("EdgeToE2"))
                    .hasToolId(id -> toolIds.set(List.of(id)));
            assertThat(toolIds.get()).hasSize(1);
        };

        Runnable createFirstEdge = () -> {
            var input = new InvokeSingleClickOnTwoDiagramElementsToolInput(UUID.randomUUID(), EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, diagramId.get(),
                    sourceId.get(), targetId.get(), 0, 0, 0, 0, toolIds.get().get(0), List.of());
            var result = this.invokeToolMutationRunner.run(input);
            String payloadTypeName = JsonPath.read(result.data(), "$.data.invokeSingleClickOnTwoDiagramElementsTool.__typename");
            assertThat(payloadTypeName).isEqualTo(InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> diagramAfterFirstEdge = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).findDiagramEdgeCount()).isEqualTo(2);
            var newEdges = diagram.getEdges().stream().filter(edge -> !edge.getId().equals(initialEdgeId.get())).toList();
            assertThat(newEdges).hasSize(1).allSatisfy(edge -> {
                assertThat(edge.getSourceId()).isEqualTo(sourceId.get());
                assertThat(edge.getTargetId()).isEqualTo(targetId.get());
            });
            assertThat(newEdges).extracting(Edge::getDescriptionId)
                    .containsExactlyInAnyOrder(expectedDescriptionIds.get("EdgeToE2"));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagram)
                .then(requestTools)
                .then(createFirstEdge)
                .consumeNextWith(diagramAfterFirstEdge)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given Entity2 and an existing edge, when quick connector tools are invoked, then both edges are created")
    public void givenEntityAndEdgeWhenQuickConnectorToolsAreInvokedThenEdgesAreCreated() {
        var subscriptionInput = new DiagramEventInput(UUID.randomUUID(), EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, EdgeOnEdgeIdentifiers.DIAGRAM_REPRESENTATION_ID);
        var subscriptionResult = this.diagramEventSubscriptionRunner.run(subscriptionInput);
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
        var flux = subscriptionResult.flux();

        var description = (DiagramDescription) this.diagramEdgeViewProvider.createViews().getFirst().getDescriptions().getFirst();
        var expectedDescriptionIds = description.getEdgeDescriptions().stream()
                .collect(Collectors.toMap(EdgeDescription::getName, this.diagramIdProvider::getId));

        var diagramId = new AtomicReference<String>();
        var sourceId = new AtomicReference<String>();
        var targetId = new AtomicReference<String>();
        var initialEdgeId = new AtomicReference<String>();
        var toolIds = new AtomicReference<List<String>>();

        Consumer<Object> initialDiagram = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            var navigator = new DiagramNavigator(diagram);
            sourceId.set(navigator.nodeWithLabel("Entity2_A").getNode().getId());
            targetId.set(navigator.edgeWithLabel("E1TOE2A").getEdge().getId());
            initialEdgeId.set(navigator.edgeWithLabel("E1TOE2A").getEdge().getId());
            assertThat(navigator.findDiagramEdgeCount()).isEqualTo(1);
        });

        Runnable requestTools = () -> {
            var palette = this.connectorPaletteExecutor.execute(EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, diagramId.get(), sourceId.get(), targetId.get());
            palette.hasTypeName(typeName -> assertThat(typeName).isEqualTo("Palette"))
                    .hasQuickAccessToolLabel(labels -> assertThat(labels).startsWith("E2ToEdge1A", "E2ToEdge1B"))
                    .hasQuickAccessToolId(ids -> toolIds.set(ids.subList(0, 2)));
            assertThat(toolIds.get()).hasSize(2);
        };

        Runnable createFirstEdge = () -> {
            var input = new InvokeSingleClickOnTwoDiagramElementsToolInput(UUID.randomUUID(), EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, diagramId.get(),
                    sourceId.get(), targetId.get(), 0, 0, 0, 0, toolIds.get().get(0), List.of());
            var result = this.invokeToolMutationRunner.run(input);
            String payloadTypeName = JsonPath.read(result.data(), "$.data.invokeSingleClickOnTwoDiagramElementsTool.__typename");
            assertThat(payloadTypeName).isEqualTo(InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> diagramAfterFirstEdge = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).findDiagramEdgeCount()).isEqualTo(2);
            var newEdges = diagram.getEdges().stream().filter(edge -> !edge.getId().equals(initialEdgeId.get())).toList();
            assertThat(newEdges).hasSize(1).allSatisfy(edge -> {
                assertThat(edge.getSourceId()).isEqualTo(sourceId.get());
                assertThat(edge.getTargetId()).isEqualTo(targetId.get());
            });
            assertThat(newEdges).extracting(Edge::getDescriptionId)
                    .containsExactlyInAnyOrder(expectedDescriptionIds.get("E2ToEdge1A"));
        });

        Runnable createSecondEdge = () -> {
            var input = new InvokeSingleClickOnTwoDiagramElementsToolInput(UUID.randomUUID(), EdgeOnEdgeIdentifiers.EDITING_CONTEXT_ID, diagramId.get(),
                    sourceId.get(), targetId.get(), 0, 0, 0, 0, toolIds.get().get(1), List.of());
            var result = this.invokeToolMutationRunner.run(input);
            String payloadTypeName = JsonPath.read(result.data(), "$.data.invokeSingleClickOnTwoDiagramElementsTool.__typename");
            assertThat(payloadTypeName).isEqualTo(InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> diagramAfterSecondEdge = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).findDiagramEdgeCount()).isEqualTo(3);
            var newEdges = diagram.getEdges().stream().filter(edge -> !edge.getId().equals(initialEdgeId.get())).toList();
            assertThat(newEdges).hasSize(2).allSatisfy(edge -> {
                assertThat(edge.getSourceId()).isEqualTo(sourceId.get());
                assertThat(edge.getTargetId()).isEqualTo(targetId.get());
            });
            assertThat(newEdges).extracting(Edge::getDescriptionId)
                    .containsExactlyInAnyOrder(expectedDescriptionIds.get("E2ToEdge1A"), expectedDescriptionIds.get("E2ToEdge1B"));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagram)
                .then(requestTools)
                .then(createFirstEdge)
                .consumeNextWith(diagramAfterFirstEdge)
                .then(createSecondEdge)
                .consumeNextWith(diagramAfterSecondEdge)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

