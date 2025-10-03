/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramInstanceOfAssertFactories.NODE;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolExecutor;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.VisibilityDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.undoredo.RedoMutationRunner;
import org.eclipse.sirius.web.tests.undoredo.UndoMutationRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private InvokeSingleClickOnDiagramElementToolExecutor invokeSingleClickOnDiagramElementToolExecutor;

    @Autowired
    private VisibilityDiagramDescriptionProvider visibilityDiagramDescriptionProvider;

    @Autowired
    private UndoMutationRunner undoMutationRunner;

    @Autowired
    private RedoMutationRunner redoMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToVisibilityDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.visibilityDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "VisibilityDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with hidden and faded nodes by default, when it is opened, then some nodes are hidden and faded")
    public void givenDiagramWithHiddenAndFadedNodesByDefaultWhenItIsOpenedThenSomeNodesAreHiddenAndFaded() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
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
        });

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with hidden nodes by default, when a tool revealing nodes is invoked, then hidden nodes are revealed")
    public void givenDiagramWithHiddenNodesByDefaultWhenToolRevealingNodesIsInvokedThenHiddenNodesAreRevealed() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var hiddenNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(siriusWebDomainNode).hasModifiers(Set.of(ViewModifier.Hidden));
            hiddenNodeId.set(siriusWebDomainNode.getId());
        });

        Runnable revealNodes = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), hiddenNodeId.get(), this.visibilityDiagramDescriptionProvider.getRevealNodeToolId(), 0, 0, List.of())
                .isSuccess();

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(siriusWebDomainNode).hasModifiers(Set.of());
        });

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .then(revealNodes)
            .consumeNextWith(updatedDiagramContentConsumer)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with visible nodes by default, when a tool hiding nodes is invoked, then visible nodes are hidden")
    public void givenDiagramWithVisibleNodesByDefaultWhenToolHidingNodesIsInvokedThenVisibleNodesAreHidden() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var revealedNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            diagram.getNodes().stream()
                .filter(node -> node.getModifiers().isEmpty())
                .map(Node::getId)
                .findFirst()
                .ifPresent(revealedNodeId::set);
        });

        Runnable hideNodes = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), revealedNodeId.get(), this.visibilityDiagramDescriptionProvider.getHideNodeToolId(), 0, 0, List.of())
                .isSuccess();

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            var node = new DiagramNavigator(diagram).nodeWithId(revealedNodeId.get()).getNode();
            assertThat(node).hasModifiers(Set.of(ViewModifier.Hidden));
        });

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .then(hideNodes)
            .consumeNextWith(updatedDiagramContentMatcher)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with visible nodes by default, when a tool hiding nodes is invoked then undo redo is invoked, then visible nodes are hidden")
    public void givenDiagramWithVisibleNodesByDefaultWhenToolHidingNodesIsInvokedUndoRedoInvokedThenVisibleNodesAreHidden() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var revealedNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            diagram.getNodes().stream()
                    .filter(node -> node.getModifiers().isEmpty())
                    .map(Node::getId)
                    .findFirst()
                    .ifPresent(revealedNodeId::set);
        });

        var inputId = new AtomicReference<UUID>(null);
        Runnable hideNodes = () -> {
            var result = this.invokeSingleClickOnDiagramElementToolExecutor.execute(
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    revealedNodeId.get(),
                    this.visibilityDiagramDescriptionProvider.getHideNodeToolId(),
                    0,
                    0,
                    List.of()
            ).isSuccess().getResult();

            String id = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.id");
            inputId.set(UUID.fromString(id));
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            var node = new DiagramNavigator(diagram).nodeWithId(revealedNodeId.get()).getNode();
            assertThat(node).hasModifiers(Set.of(ViewModifier.Hidden));
        });

        Runnable undoChanges = () -> {
            var input = new UndoInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    inputId.get()
            );

            this.undoMutationRunner.run(input);
        };

        Runnable redoChanges = () -> {
            var input = new RedoInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    inputId.get()
            );

            this.redoMutationRunner.run(input);
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(hideNodes)
                .consumeNextWith(updatedDiagramContentMatcher)
                .then(undoChanges)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(redoChanges)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with nodes not faded by default, when a tool fading nodes is invoked, then not faded nodes are faded")
    public void givenDiagramWithNodesNotFadedByDefaultWhenToolFadingNodesIsInvokedThenNotFadedNodesAreFaded() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var unfadedNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            diagram.getNodes().stream()
                .filter(node -> node.getModifiers().isEmpty())
                .map(Node::getId)
                .findFirst()
                .ifPresent(unfadedNodeId::set);
        });

        Runnable fadeNodes = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), unfadedNodeId.get(), this.visibilityDiagramDescriptionProvider.getFadeNodeToolId(), 0, 0, List.of())
                .isSuccess();

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            var node = new DiagramNavigator(diagram).nodeWithId(unfadedNodeId.get()).getNode();
            assertThat(node).hasModifiers(Set.of(ViewModifier.Faded));
        });

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .then(fadeNodes)
            .consumeNextWith(updatedDiagramContentMatcher)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with nodes not faded by default, when a tool fading nodes is invoked and undo redo is invoked, then not faded nodes are faded")
    public void givenDiagramWithNodesNotFadedByDefaultWhenToolFadingNodesIsInvokedUndoRedoInvokedThenNotFadedNodesAreFaded() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var unfadedNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            diagram.getNodes().stream()
                    .filter(node -> node.getModifiers().isEmpty())
                    .map(Node::getId)
                    .findFirst()
                    .ifPresent(unfadedNodeId::set);
        });

        var inputId = new AtomicReference<UUID>(null);
        Runnable fadeNodes = () -> {
            var result = this.invokeSingleClickOnDiagramElementToolExecutor.execute(
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    unfadedNodeId.get(),
                    this.visibilityDiagramDescriptionProvider.getFadeNodeToolId(),
                    0,
                    0,
                    List.of()
            ).isSuccess().getResult();

            String id = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.id");
            inputId.set(UUID.fromString(id));
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            var node = new DiagramNavigator(diagram).nodeWithId(unfadedNodeId.get()).getNode();
            assertThat(node).hasModifiers(Set.of(ViewModifier.Faded));
        });

        Runnable undoChanges = () -> {
            var input = new UndoInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    inputId.get()
            );

            this.undoMutationRunner.run(input);
        };

        Runnable redoChanges = () -> {
            var input = new RedoInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    inputId.get()
            );

            this.redoMutationRunner.run(input);
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(fadeNodes)
                .consumeNextWith(updatedDiagramContentMatcher)
                .then(undoChanges)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(redoChanges)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with faded nodes by default, when a tool unfading nodes is invoked, then faded nodes are unfaded")
    public void givenDiagramWithFadedNodesByDefaultWhenToolUnFadingNodesIsInvokedThenFadedNodesAreUnFaded() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var fadedNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            assertThat(siriusWebApplicationNode).hasModifiers(Set.of(ViewModifier.Faded));
            fadedNodeId.set(siriusWebApplicationNode.getId());
        });

        Runnable unfadeNodes = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), fadedNodeId.get(), this.visibilityDiagramDescriptionProvider.getUnfadeNodeToolId(), 0, 0, List.of())
                .isSuccess();

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            assertThat(siriusWebApplicationNode).hasModifiers(Set.of());
        });

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .then(unfadeNodes)
            .consumeNextWith(updatedDiagramContentConsumer)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with faded and hidden nodes, when a tool resetting the visibility modifiers is invoked, then faded and hidden nodes are reset to their default visibility")
    public void givenDiagramWithFadedAndHiddenNodesWhenToolResettingVisibilityModifierIsInvokedThenFadedAndHiddenNodesAreResetToDefaultVisibility() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var nodeToFadeId = new AtomicReference<String>();
        var nodeToHideId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            List<Node> revealedNodes = diagram.getNodes().stream().filter(n -> n.getModifiers().isEmpty()).toList();
            nodeToFadeId.set(revealedNodes.get(0).getId());
            nodeToHideId.set(revealedNodes.get(1).getId());
        });

        Runnable fadeNode = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), nodeToFadeId.get(), this.visibilityDiagramDescriptionProvider.getFadeNodeToolId(), 0, 0, List.of())
                .isSuccess();

        Runnable hideNode = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), nodeToHideId.get(), this.visibilityDiagramDescriptionProvider.getHideNodeToolId(), 0, 0, List.of())
                .isSuccess();

        Runnable resetFadedNodeVisibility = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), nodeToFadeId.get(), this.visibilityDiagramDescriptionProvider.getResetNodeToolId(), 0, 0, List.of())
                .isSuccess();

        Runnable resetHiddenNodeVisibility = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), nodeToHideId.get(), this.visibilityDiagramDescriptionProvider.getResetNodeToolId(), 0, 0, List.of())
                .isSuccess();

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
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
        });

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

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with faded and hidden nodes, when a tool resetting the visibility modifiers is invoked and undo redo is invoked, then faded and hidden nodes are reset to their default visibility")
    public void givenDiagramWithFadedAndHiddenNodesWhenToolResettingVisibilityModifierIsInvokedAndUndoRedoIsInvokedThenFadedAndHiddenNodesAreResetToDefaultVisibility() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var nodeToFadeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            List<Node> revealedNodes = diagram.getNodes().stream().filter(n -> n.getModifiers().isEmpty()).toList();
            nodeToFadeId.set(revealedNodes.get(0).getId());
        });

        Runnable fadeNode = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), nodeToFadeId.get(), this.visibilityDiagramDescriptionProvider.getFadeNodeToolId(), 0, 0, List.of())
                .isSuccess();

        var inputId = new AtomicReference<UUID>(null);
        Runnable resetNodeVisibility = () -> {
            var result = this.invokeSingleClickOnDiagramElementToolExecutor.execute(
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    nodeToFadeId.get(),
                    this.visibilityDiagramDescriptionProvider.getResetNodeToolId(),
                    0,
                    0,
                    List.of()
            ).isSuccess().getResult();

            String id = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.id");
            inputId.set(UUID.fromString(id));
        };

        Runnable undoChanges = () -> {
            var input = new UndoInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    inputId.get()
            );

            this.undoMutationRunner.run(input);
        };

        Runnable redoChanges = () -> {
            var input = new RedoInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    inputId.get()
            );

            this.redoMutationRunner.run(input);
        };

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var nodeToFade = diagram.getNodes().stream().filter(node -> Objects.equals(node.getId(), nodeToFadeId.get())).findFirst();
            assertThat(nodeToFade)
                    .isPresent()
                    .get(NODE)
                    .hasModifiers(Set.of());
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(fadeNode)
                .consumeNextWith((payload) -> { })
                .then(resetNodeVisibility)
                .consumeNextWith(updatedDiagramContentConsumer)
                .then(undoChanges)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(redoChanges)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
