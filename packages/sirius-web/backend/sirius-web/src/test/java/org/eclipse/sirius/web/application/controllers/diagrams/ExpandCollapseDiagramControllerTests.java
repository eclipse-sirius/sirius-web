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
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.services.diagrams.ExpandCollapseDiagramDescriptionProvider;
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
    private InvokeSingleClickOnDiagramElementToolMutationRunner invokeSingleClickOnDiagramElementToolMutationRunner;

    @Autowired
    private ExpandCollapseDiagramDescriptionProvider expandCollapseDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<DiagramRefreshedEventPayload> givenSubscriptionToExpandedCollapseDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                this.expandCollapseDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "ExpandCollapseDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a diagram with collapsed nodes by default, when it is opened, then some nodes are collapsed")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithCollapsedNodesByDefaultWhenItIsOpenedThenSomeNodesAreCollapsed() {
        var flux = this.givenSubscriptionToExpandedCollapseDiagram();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
                    assertThat(siriusWebDomainNode).hasCollapsingState(CollapsingState.COLLAPSED);

                    var nonDomainNodes = diagram.getNodes().stream()
                            .filter(node -> !node.getInsideLabel().getText().endsWith("-domain"));
                    assertThat(nonDomainNodes)
                            .isNotEmpty()
                            .allSatisfy(node -> assertThat(node).hasCollapsingState(CollapsingState.EXPANDED));
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram with collapsed nodes by default, when a tool expanding nodes is invoked, then collapsed nodes are expanded")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithCollapsedNodesByDefaultWhenToolExpandingNodesIsInvokedThenCollapsedNodesAreExpanded() {
        var flux = this.givenSubscriptionToExpandedCollapseDiagram();

        var diagramId = new AtomicReference<String>();
        var collapsedNodeId = new AtomicReference<String>();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
                    assertThat(siriusWebDomainNode).hasCollapsingState(CollapsingState.COLLAPSED);
                    collapsedNodeId.set(siriusWebDomainNode.getId());
                }, () -> fail("Missing diagram"));

        Runnable expandNodes = () -> {
            String expandToolId = this.expandCollapseDiagramDescriptionProvider.getExpandNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), collapsedNodeId.get(), expandToolId, 0, 0, null);
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Consumer<DiagramRefreshedEventPayload> updatedDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
                    assertThat(siriusWebDomainNode).hasCollapsingState(CollapsingState.EXPANDED);
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(expandNodes)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram with collapsed nodes by default, when a tool collapsing nodes is invoked, then expanded nodes are collapsed")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithCollapsedNodesByDefaultWhenToolCollapsingNodesIsInvokedThenExpandedNodesAreCollapsed() {
        var flux = this.givenSubscriptionToExpandedCollapseDiagram();

        var diagramId = new AtomicReference<String>();
        var expandedNodeId = new AtomicReference<String>();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    diagram.getNodes().stream()
                            .filter(node -> node.getCollapsingState().equals(CollapsingState.EXPANDED))
                            .map(Node::getId)
                            .findFirst()
                            .ifPresent(expandedNodeId::set);
                }, () -> fail("Missing diagram"));

        Runnable collapseNodes = () -> {
            String collapseToolId = this.expandCollapseDiagramDescriptionProvider.getCollapseNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagramId.get(), expandedNodeId.get(), collapseToolId, 0, 0, null);
            var invokeSingleClickOnDiagramElementToolResult = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String invokeSingleClickOnDiagramElementToolResultTypename = JsonPath.read(invokeSingleClickOnDiagramElementToolResult, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(invokeSingleClickOnDiagramElementToolResultTypename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Predicate<DiagramRefreshedEventPayload> updatedDiagramContentMatcher = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .filter(diagram -> {
                    return diagram.getNodes().stream()
                        .filter(node -> node.getId().equals(expandedNodeId.get()))
                        .map(node -> node.getCollapsingState().equals(CollapsingState.COLLAPSED))
                        .findFirst()
                        .orElse(false);
                })
                .isPresent();

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(collapseNodes)
                .expectNextMatches(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
