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

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolExecutor;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ExpandCollapseDiagramDescriptionProvider;
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
 * Integration tests of the expand / collapse diagrams.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ExpandCollapseDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private InvokeSingleClickOnDiagramElementToolExecutor invokeSingleClickOnDiagramElementToolExecutor;

    @Autowired
    private ExpandCollapseDiagramDescriptionProvider expandCollapseDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToExpandedCollapseDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.expandCollapseDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "ExpandCollapseDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with collapsed nodes by default, when it is opened, then some nodes are collapsed")
    public void givenDiagramWithCollapsedNodesByDefaultWhenItIsOpenedThenSomeNodesAreCollapsed() {
        var flux = this.givenSubscriptionToExpandedCollapseDiagram();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(siriusWebDomainNode).hasCollapsingState(CollapsingState.COLLAPSED);

            var nonDomainNodes = diagram.getNodes().stream()
                    .filter(node -> !node.getInsideLabel().getText().endsWith("-domain"));
            assertThat(nonDomainNodes)
                    .isNotEmpty()
                    .allSatisfy(node -> assertThat(node).hasCollapsingState(CollapsingState.EXPANDED));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with collapsed nodes by default, when a tool expanding nodes is invoked, then collapsed nodes are expanded")
    public void givenDiagramWithCollapsedNodesByDefaultWhenToolExpandingNodesIsInvokedThenCollapsedNodesAreExpanded() {
        var flux = this.givenSubscriptionToExpandedCollapseDiagram();

        var diagramId = new AtomicReference<String>();
        var collapsedNodeId = new AtomicReference<String>();

        var initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(siriusWebDomainNode).hasCollapsingState(CollapsingState.COLLAPSED);
            collapsedNodeId.set(siriusWebDomainNode.getId());
        });

        Runnable expandNodes = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), List.of(collapsedNodeId.get()), this.expandCollapseDiagramDescriptionProvider.getExpandNodeToolId(), 0, 0, List.of())
                    .isSuccess();

        var updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(siriusWebDomainNode).hasCollapsingState(CollapsingState.EXPANDED);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(expandNodes)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with expanded nodes by default, when a tool collapsing nodes is invoked, then expanded nodes are collapsed")
    public void givenDiagramWithExpandedNodesByDefaultWhenToolCollapsingNodesIsInvokedThenExpandedNodesAreCollapsed() {
        var flux = this.givenSubscriptionToExpandedCollapseDiagram();

        var diagramId = new AtomicReference<String>();
        var expandedNodeId = new AtomicReference<String>();

        var initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            diagram.getNodes().stream()
                    .filter(node -> node.getCollapsingState().equals(CollapsingState.EXPANDED))
                    .map(Node::getId)
                    .findFirst()
                    .ifPresent(expandedNodeId::set);
        });

        Runnable collapseNodes = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), List.of(expandedNodeId.get()), this.expandCollapseDiagramDescriptionProvider.getCollapseNodeToolId(), 0, 0, List.of())
                    .isSuccess();

        var updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).nodeWithId(expandedNodeId.get()).getNode())
                    .hasCollapsingState(CollapsingState.COLLAPSED);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(collapseNodes)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
