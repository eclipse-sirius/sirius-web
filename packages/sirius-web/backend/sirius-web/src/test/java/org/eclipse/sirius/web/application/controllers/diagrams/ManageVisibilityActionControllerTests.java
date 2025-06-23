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
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.InvokeManageVisibilityActionInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.tests.graphql.GetManageVisibilityActionsQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeManageVisibilityActionMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
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
    public void givenDiagramWithManageVisibilityActionsWhenTheActionsAreRequestedOnThisNodeThenTheActionsAreRetrieved() {
        var flux = this.givenSubscriptionToActionDiagram();
        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var node = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            node.getChildNodes().forEach(childNode -> assertEquals(ViewModifier.Normal, childNode.getState()));
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
    public void givenDiagramWithManageVisibilityActionWhenTheActionIsExecutedOnThisNodeThenTheActionBodyIsInvoked() {
        var flux = this.givenSubscriptionToActionDiagram();

        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            var node = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();

            node.getChildNodes().forEach(childNode -> assertEquals(ViewModifier.Normal, childNode.getState()));
            assertThat(node).hasState(ViewModifier.Normal);
            nodeId.set(node.getId());
        });

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

        Consumer<Object> updatedAfterHideDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            var node = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();

            node.getChildNodes().forEach(childNode -> assertEquals(ViewModifier.Hidden, childNode.getState()));
            assertThat(node).hasState(ViewModifier.Normal);
        });

        Consumer<Object> updatedAfterRevealDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            var node = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();

            node.getChildNodes().forEach(childNode -> assertEquals(ViewModifier.Normal, childNode.getState()));
            assertThat(node).hasState(ViewModifier.Normal);
        });

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
