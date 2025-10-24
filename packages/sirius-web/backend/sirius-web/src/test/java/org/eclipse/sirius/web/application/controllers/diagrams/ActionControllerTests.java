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

import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeActionInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.tests.graphql.GetActionsQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeActionMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ActionDiagramDescriptionProvider;
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
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ActionControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private GetActionsQueryRunner getActionsQueryRunner;

    @Autowired
    private InvokeActionMutationRunner invokeActionMutationRunner;

    @Autowired
    private ActionDiagramDescriptionProvider actionDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToActionDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.actionDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "ActionDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a node with actions, when the actions are requested on this node, then the actions are retrieved")
    public void givenDiagramWithNodeWithActionsWhenTheActionsAreRequestedOnThisNodeThenTheActionsAreRetrieved() {
        var flux = this.givenSubscriptionToActionDiagram();

        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var node = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(node).hasState(ViewModifier.Normal);
            nodeId.set(node.getId());
        });

        Runnable getActions = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "diagramId", diagramId.get(),
                    "diagramElementId", nodeId.get()
            );
            var result = this.getActionsQueryRunner.run(variables);
            List<String> actionsIds = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.actions[*].id");
            List<String> actionsTooltips = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.actions[*].tooltip");
            List<List<String>> actionsIconURLs = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.actions[*].iconURLs");
            assertThat(actionsTooltips)
                    .isNotEmpty()
                    .contains("Hide", "Fade");
            assertThat(actionsIds)
                    .contains(UUID.nameUUIDFromBytes("HideAction".getBytes()).toString(), UUID.nameUUIDFromBytes("FadeAction".getBytes()).toString());
            assertThat(actionsIconURLs)
                    .isNotEmpty()
                    .anySatisfy(actionIconURL -> assertThat(actionIconURL).contains("/api/images/icons/full/obj16/HideTool.svg"))
                    .anySatisfy(actionIconURL -> assertThat(actionIconURL).contains("/api/images/icons/full/obj16/FadeTool.svg"));
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(getActions)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a node with actions using preconditions, when the actions are requested on this node, then only the actions with precondition evaluated to true are retrieved")
    public void givenDiagramWithNodeWithActionsUsingPreconditionsWhenTheActionsAreRequestedOnThisNodeThenOnlyTheActionsWithPreconditionsEvaluatedToTrueAreRetrieved() {
        var flux = this.givenSubscriptionToActionDiagram();

        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var node = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode();
            assertThat(node).hasState(ViewModifier.Normal);
            nodeId.set(node.getId());
        });

        Runnable getActions = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "diagramId", diagramId.get(),
                    "diagramElementId", nodeId.get()
            );
            var result = this.getActionsQueryRunner.run(variables);
            List<String> actionsIds = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.actions[*].id");
            List<String> actionsTooltips = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.actions[*].tooltip");
            List<List<String>> actionsIconURLs = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.actions[*].iconURLs");
            assertThat(actionsTooltips)
                    .isNotEmpty()
                    .contains("Hide")
                    .doesNotContain("Fade");
            assertThat(actionsIds)
                    .isNotEmpty()
                    .contains(UUID.nameUUIDFromBytes("HideAction".getBytes()).toString())
                    .doesNotContain(UUID.nameUUIDFromBytes("FadeAction".getBytes()).toString());
            assertThat(actionsIconURLs)
                    .isNotEmpty()
                    .anySatisfy(actionIconURL -> assertThat(actionIconURL).contains("/api/images/icons/full/obj16/HideTool.svg"))
                    .noneSatisfy(actionIconURL -> assertThat(actionIconURL).contains("/api/images/icons/full/obj16/FadeTool.svg"));
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(getActions)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a node with an action, when the action is executed on this node, then the action body is invoked")
    public void givenDiagramWithNodeWithAnActionWhenTheActionIsExecutedOnThisNodeThenTheActionBodyIsInvoked() {
        var flux = this.givenSubscriptionToActionDiagram();

        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var node = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(node).hasState(ViewModifier.Normal);
            nodeId.set(node.getId());
        });

        Runnable invokeAction = () -> {
            String actionId = UUID.nameUUIDFromBytes("HideAction".getBytes()).toString();
            var input = new InvokeActionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), nodeId.get(), actionId);
            var result = this.invokeActionMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.invokeAction.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            var node = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(node).hasState(ViewModifier.Hidden);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(invokeAction)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
