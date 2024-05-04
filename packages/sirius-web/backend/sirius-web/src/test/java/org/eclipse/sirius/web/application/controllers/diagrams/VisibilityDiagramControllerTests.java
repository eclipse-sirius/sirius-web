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
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramInstanceOfAssertFactories.NODE;
import static org.junit.jupiter.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.services.diagrams.VisibilityDiagramDescriptionProvider;
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
 * Integration tests of the hide / fade / reveal diagrams.
 *
 * @author gdaniel
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class VisibilityDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private InvokeSingleClickOnDiagramElementToolMutationRunner invokeSingleClickOnDiagramElementToolMutationRunner;

    @Autowired
    private VisibilityDiagramDescriptionProvider visibilityDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<DiagramRefreshedEventPayload> givenSubscriptionToVisibilityDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                this.visibilityDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "VisibilityDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a diagram with hidden and faded nodes by default, when it is opened, then some nodes are hidden and faded")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithHiddenAndFadedNodesByDefaultWhenItIsOpenedThenSomeNodesAreHiddenAndFaded() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {

                    var siriusWebDomainNode = new DiagramNavigator(diagram)
                            .nodeWithLabel("sirius-web-domain")
                            .getNode();
                    assertThat(siriusWebDomainNode).hasModifiers(Set.of(ViewModifier.Hidden));

                    var siriusWebApplicationNode = new DiagramNavigator(diagram)
                            .nodeWithLabel("sirius-web-application")
                            .getNode();
                    assertThat(siriusWebApplicationNode).hasModifiers(Set.of(ViewModifier.Faded));

                    var nonDomainNonApplicationNodes = diagram.getNodes().stream()
                            .filter(node -> !node.getInsideLabel().getText().endsWith("-domain")
                                        && !node.getInsideLabel().getText().endsWith("-application"));
                    assertThat(nonDomainNonApplicationNodes)
                        .isNotEmpty()
                        .allSatisfy(node -> assertThat(node).hasModifiers(Set.of()));
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram with hidden nodes by default, when a tool revealing nodes is invoked, then hidden nodes are revealed")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithHiddenNodesByDefaultWhenToolRevealingNodesIsInvokedThenHiddenNodesAreRevealed() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var hiddenNodeId = new AtomicReference<String>();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
                    assertThat(siriusWebDomainNode).hasModifiers(Set.of(ViewModifier.Hidden));
                    hiddenNodeId.set(siriusWebDomainNode.getId());
                }, () -> fail("Missing diagram"));

        Runnable revealNodes = () -> {
            String revealNodeToolId = this.visibilityDiagramDescriptionProvider.getRevealNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), hiddenNodeId.get(), revealNodeToolId, 0, 0, null);
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Consumer<DiagramRefreshedEventPayload> updatedDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
                    assertThat(siriusWebDomainNode).hasModifiers(Set.of());
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .then(revealNodes)
            .consumeNextWith(updatedDiagramContentConsumer)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram with hidden nodes by default, when a tool hidding nodes is invoked, then revealed nodes are hidden")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithHiddenNodesByDefaultWhenToolHiddingNodesIsInvokedThenRevealedNodesAreHidden() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var revealedNodeId = new AtomicReference<String>();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    diagram.getNodes().stream()
                        .filter(node -> node.getModifiers().isEmpty())
                        .map(Node::getId)
                        .findFirst()
                        .ifPresent(revealedNodeId::set);
                }, () -> fail("Missing diagram"));

        Runnable hideNodes = () -> {
            String hideNodeToolId = this.visibilityDiagramDescriptionProvider.getHideNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), revealedNodeId.get(), hideNodeToolId, 0, 0, null);
            var invokeSingleClickOnDiagramElementToolResult = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String invokeSingleClickOnDiagramElementToolResultTypename = JsonPath.read(invokeSingleClickOnDiagramElementToolResult, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(invokeSingleClickOnDiagramElementToolResultTypename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Predicate<DiagramRefreshedEventPayload> updatedDiagramContentMatcher = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .filter(diagram -> {
                    return diagram.getNodes().stream()
                            .filter(node -> node.getId().equals(revealedNodeId.get()))
                            .map(node -> node.getModifiers().contains(ViewModifier.Hidden))
                            .findFirst()
                            .orElse(false);
                })
                .isPresent();

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .then(hideNodes)
            .expectNextMatches(updatedDiagramContentMatcher)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram with faded nodes by default, when a tool fading nodes is invoked, then not faded nodes are faded")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithFadedNodesByDefaultWhenToolFadingNodesIsInvokedThenNotFadedNodesAreFaded() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var unfadedNodeId = new AtomicReference<String>();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    diagram.getNodes().stream()
                        .filter(node -> node.getModifiers().isEmpty())
                        .map(Node::getId)
                        .findFirst()
                        .ifPresent(unfadedNodeId::set);
                }, () -> fail("Missing diagram"));

        Runnable fadeNodes = () -> {
            String fadeNodeToolId = this.visibilityDiagramDescriptionProvider.getFadeNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), unfadedNodeId.get(), fadeNodeToolId, 0, 0, null);
            var invokeSingleClickOnDiagramElementToolResult = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String invokeSingleClickOnDiagramElementToolResultTypename = JsonPath.read(invokeSingleClickOnDiagramElementToolResult, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(invokeSingleClickOnDiagramElementToolResultTypename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Predicate<DiagramRefreshedEventPayload> updatedDiagramContentMatcher = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .filter(diagram -> {
                    return diagram.getNodes().stream()
                            .filter(node -> node.getId().equals(unfadedNodeId.get()))
                            .map(node -> node.getModifiers().contains(ViewModifier.Faded))
                            .findFirst()
                            .orElse(false);
                })
                .isPresent();

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .then(fadeNodes)
            .expectNextMatches(updatedDiagramContentMatcher)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram with faded nodes by default, when a tool unfading nodes is invoked, then faded nodes are unfaded")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithFadedNodesByDefaultWhenToolUnFadingNodesIsInvokedThenFadedNodesAreUnFaded() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var fadedNodeId = new AtomicReference<String>();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
                    assertThat(siriusWebApplicationNode).hasModifiers(Set.of(ViewModifier.Faded));
                    fadedNodeId.set(siriusWebApplicationNode.getId());
                }, () -> fail("Missing diagram"));

        Runnable unfadeNodes = () -> {
            String unfadeNodeToolId = this.visibilityDiagramDescriptionProvider.getUnfadeNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), fadedNodeId.get(), unfadeNodeToolId, 0, 0, null);
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Consumer<DiagramRefreshedEventPayload> updatedDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
                    assertThat(siriusWebApplicationNode).hasModifiers(Set.of());
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .then(unfadeNodes)
            .consumeNextWith(updatedDiagramContentConsumer)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram with faded and hidden nodes, when a tool resetting the visibility modifiers is invoked, then faded and hidden nodes are reset to their default visibility")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithFadedAndHiddenNodesWhenToolResettingVisibilityModifierIsInvokedThenFadedAndHiddenNodesAreResetToDefaultVisibility() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var nodeToFadeId = new AtomicReference<String>();
        var nodeToHideId = new AtomicReference<String>();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    List<Node> revealedNodes = diagram.getNodes().stream().filter(n -> n.getModifiers().isEmpty()).toList();
                    nodeToFadeId.set(revealedNodes.get(0).getId());
                    nodeToHideId.set(revealedNodes.get(1).getId());
                }, () -> fail("Missing diagram"));

        Runnable fadeNode = () -> {
            String fadeNodeToolId = this.visibilityDiagramDescriptionProvider.getFadeNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), nodeToFadeId.get(), fadeNodeToolId, 0, 0, null);
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Runnable hideNode = () -> {
            String hideNodeToolId = this.visibilityDiagramDescriptionProvider.getHideNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), nodeToHideId.get(), hideNodeToolId, 0, 0, null);
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Runnable resetFadedNodeVisibility = () -> {
            String resetNodeToolId = this.visibilityDiagramDescriptionProvider.getResetNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), nodeToFadeId.get(), resetNodeToolId, 0, 0, null);
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());

        };

        Runnable resetHiddenNodeVisibility = () -> {
            String resetNodeToolId = this.visibilityDiagramDescriptionProvider.getResetNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), nodeToHideId.get(), resetNodeToolId, 0, 0, null);
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Consumer<DiagramRefreshedEventPayload> updatedDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    var nodeToFade = diagram.getNodes().stream().filter(node -> Objects.equals(node.getId(), nodeToFadeId.get())).findFirst();
                    assertThat(nodeToFade)
                        .isPresent()
                        .get(NODE)
                        .hasModifiers(Set.of());
                    var nodeToHide = diagram.getNodes().stream().filter(node -> Objects.equals(node.getId(), nodeToHideId.get())).findFirst();
                    assertThat(nodeToHide)
                        .isPresent()
                        .get(NODE)
                        .hasModifiers(Set.of());
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .then(fadeNode)
            .consumeNextWith((payload) -> { })
            .then(hideNode)
            .consumeNextWith((payload) -> { })
            .then(resetFadedNodeVisibility)
            .consumeNextWith((payload) -> { })
            .then(resetHiddenNodeVisibility)
            .consumeNextWith(updatedDiagramContentConsumer)
            .thenCancel()
            .verify(Duration.ofSeconds(10));

    }
}
