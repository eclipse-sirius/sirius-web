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
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.InvokeManageVisibilityActionInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.tests.graphql.GetManageVisibilityActionsQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeManageVisibilityActionMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.nodeaction.managevisibility.ManageVisibilityHideAllAction;
import org.eclipse.sirius.web.application.nodeaction.managevisibility.ManageVisibilityRevealAllAction;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ManageVisibilityActionDiagramDescriptionProvider;
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
 * Integration tests of the action controllers.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ManageVisibilityActionControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private ManageVisibilityActionDiagramDescriptionProvider manageVisibilityActionDiagramDescriptionProvider;

    @Autowired
    private GetManageVisibilityActionsQueryRunner getActionsQueryRunner;

    @Autowired
    private InvokeManageVisibilityActionMutationRunner invokeActionMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToActionDiagram() {
        var input = new CreateRepresentationInput(
            UUID.randomUUID(),
            PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
            this.manageVisibilityActionDiagramDescriptionProvider.getRepresentationDescriptionId(),
            PapayaIdentifiers.PROJECT_OBJECT.toString(),
            "ActionDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with manage visibility actions, when the actions are requested on this node, then the actions are retrieved")
    public void testGetManageVisibilityActions() {
        var flux = this.givenSubscriptionToActionDiagram();
        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    var node = diagram.getNodes().stream()
                            .filter(n -> "sirius-web-domain".equals(n.getTargetObjectLabel()))
                            .findFirst();
                    assertTrue(node.isPresent());

                    node.get().getChildNodes().forEach(childNode -> assertEquals(ViewModifier.Normal, childNode.getState()));
                    assertEquals(ViewModifier.Normal, node.get().getState());
                    nodeId.set(node.get().getId());
                }, () -> fail("Missing diagram"));

        Runnable getActions = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "diagramId", diagramId.get(),
                    "diagramElementId", nodeId.get()
            );
            var result = this.getActionsQueryRunner.run(variables);
            List<String> actionsIds = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.manageVisibilityActions[*].id");
            List<String> actionsLabels = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.manageVisibilityActions[*].label");

            assertThat(actionsIds)
                    .isNotEmpty()
                    .contains(ManageVisibilityRevealAllAction.ACTION_ID)
                    .contains(ManageVisibilityHideAllAction.ACTION_ID);
            assertThat(actionsLabels)
                    .isNotEmpty()
                    .contains("Hide all")
                    .contains("Reveal all");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(getActions)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with manage visibility action, when the action is executed on this node, then the action body is invoked")
    public void testInvokeManageVisibilityAction() {
        var flux = this.givenSubscriptionToActionDiagram();

        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    var node = diagram.getNodes().stream()
                            .filter(n -> "sirius-web-domain".equals(n.getTargetObjectLabel()))
                            .findFirst();
                    assertTrue(node.isPresent());
                    node.get().getChildNodes().forEach(childNode -> assertEquals(ViewModifier.Normal, childNode.getState()));
                    assertEquals(ViewModifier.Normal, node.get().getState());
                    nodeId.set(node.get().getId());
                }, () -> fail("Missing diagram"));

        Runnable invokeHideAction = () -> {
            var input = new InvokeManageVisibilityActionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), nodeId.get(), ManageVisibilityHideAllAction.ACTION_ID);
            var result = this.invokeActionMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.invokeManageVisibilityAction.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Runnable invokeRevealAction = () -> {
            var input = new InvokeManageVisibilityActionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), nodeId.get(), ManageVisibilityRevealAllAction.ACTION_ID);
            var result = this.invokeActionMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.invokeManageVisibilityAction.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedAfterHideDiagramContentMatcher = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    var node = diagram.getNodes().stream()
                            .filter(n -> "sirius-web-domain".equals(n.getTargetObjectLabel()))
                            .findFirst();
                    assertTrue(node.isPresent());
                    node.get().getChildNodes().forEach(childNode -> assertEquals(ViewModifier.Hidden, childNode.getState()));
                    assertEquals(ViewModifier.Normal, node.get().getState());
                }, () -> fail("Missing diagram"));

        Consumer<Object> updatedAfterRevealDiagramContentMatcher = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    var node = diagram.getNodes().stream()
                            .filter(n -> "sirius-web-domain".equals(n.getTargetObjectLabel()))
                            .findFirst();
                    assertTrue(node.isPresent());
                    node.get().getChildNodes().forEach(childNode -> assertEquals(ViewModifier.Normal, childNode.getState()));
                    assertEquals(ViewModifier.Normal, node.get().getState());
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(invokeHideAction)
                .consumeNextWith(updatedAfterHideDiagramContentMatcher)
                .then(invokeRevealAction)
                .consumeNextWith(updatedAfterRevealDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
