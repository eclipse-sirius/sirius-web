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

import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.tests.graphql.FilterSelectionMenuItemsExecutor;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeFilterSelectionMenuItemsExecutor;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.diagram.services.toolbar.SelectEdgesFilterSelectionProvider;
import org.eclipse.sirius.web.application.diagram.services.toolbar.SelectNodesFilterSelectionProvider;
import org.eclipse.sirius.web.application.diagram.services.toolbar.UnselectChildNodesFilterSelectionProvider;
import org.eclipse.sirius.web.application.diagram.services.toolbar.UnselectEdgesFilterSelectionProvider;
import org.eclipse.sirius.web.application.diagram.services.toolbar.UnselectNodesFilterSelectionProvider;
import org.eclipse.sirius.web.data.FlowIdentifier;
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
 * Integration tests of the filter selection of the diagram toolbar.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DiagramToolbarFilterSelectionControllerTests extends AbstractIntegrationTests {

    public static final String REPRESENTATION_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private FilterSelectionMenuItemsExecutor filterSelectionMenuItemsExecutor;

    @Autowired
    private InvokeFilterSelectionMenuItemsExecutor invokeFilterSelectionMenuItemsExecutor;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToActionDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                FlowIdentifier.FLOW_EDITING_CONTEXT_ID,
                REPRESENTATION_DESCRIPTION_ID,
                FlowIdentifier.FLOW_ROOT_SYSTEM_OBJECT,
                "FilterSelectionDiagram"
        );

        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with filter selection actions, when the actions are requested with an empty selection, then the actions can be executed")
    public void givenDiagramWithFilterSelectionActionsWhenTheActionsAreRequestedOnThisEmptySelectionThenTheActionsCanBeExecuted() {
        var flux = this.givenSubscriptionToActionDiagram();
        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> diagramId.set(diagram.getId()));

        Runnable getFilterMenuItems = () -> this.filterSelectionMenuItemsExecutor.execute(FlowIdentifier.FLOW_EDITING_CONTEXT_ID, diagramId.get(), List.of())
                .hasMenuItemIds(ids -> assertThat(ids).contains(SelectNodesFilterSelectionProvider.ID, SelectEdgesFilterSelectionProvider.ID));

        Runnable invokeSelectAllNodesAction = () -> this.invokeFilterSelectionMenuItemsExecutor.execute(FlowIdentifier.FLOW_EDITING_CONTEXT_ID, diagramId.get(), List.of(), "select_all_nodes")
                .isSuccess()
                .hasNewSelection(newSelection -> assertThat(newSelection).hasSize(5));

        Runnable invokeSelectAllEdgesAction = () -> this.invokeFilterSelectionMenuItemsExecutor.execute(FlowIdentifier.FLOW_EDITING_CONTEXT_ID, diagramId.get(), List.of(), "select_all_edges")
                .isSuccess()
                .hasNewSelection(newSelection -> assertThat(newSelection).hasSize(2));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(getFilterMenuItems)
                .then(invokeSelectAllNodesAction)
                .then(invokeSelectAllEdgesAction)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with filter selection actions, when the actions are requested with a selection, then the actions can be executed")
    public void givenDiagramWithFilterSelectionActionsWhenTheActionsAreRequestedOnThisSelectionThenTheCanBeExecuted() {
        var flux = this.givenSubscriptionToActionDiagram();
        var diagramId = new AtomicReference<String>();
        List<String> diagramElementsIds = new ArrayList<>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            // Contains 3 root nodes, 2 child nodes & 2 edges
            diagramId.set(diagram.getId());
            new DiagramNavigator(diagram).findAllNodes().stream().map(Node::getId).forEach(diagramElementsIds::add);
            diagram.getEdges().stream().map(Edge::getId).forEach(diagramElementsIds::add);
            new DiagramNavigator(diagram).findAllNodes().stream().flatMap(node -> node.getChildNodes().stream()).map(Node::getId).forEach(diagramElementsIds::add);
        });

        Runnable getFilterMenuItems = () -> this.filterSelectionMenuItemsExecutor.execute(FlowIdentifier.FLOW_EDITING_CONTEXT_ID, diagramId.get(), diagramElementsIds)
                .hasMenuItemIds(ids -> assertThat(ids).contains(UnselectNodesFilterSelectionProvider.ID, UnselectEdgesFilterSelectionProvider.ID, UnselectChildNodesFilterSelectionProvider.ID));

        Runnable invokeUnselectAllNodesAction = () -> this.invokeFilterSelectionMenuItemsExecutor.execute(FlowIdentifier.FLOW_EDITING_CONTEXT_ID, diagramId.get(), diagramElementsIds, UnselectNodesFilterSelectionProvider.ID)
                .isSuccess()
                .hasNewSelection(newSelection -> assertThat(newSelection).hasSize(2));

        Runnable invokeUnselectAllEdgesAction = () -> this.invokeFilterSelectionMenuItemsExecutor.execute(FlowIdentifier.FLOW_EDITING_CONTEXT_ID, diagramId.get(), diagramElementsIds, UnselectEdgesFilterSelectionProvider.ID)
                .isSuccess()
                .hasNewSelection(newSelection -> assertThat(newSelection).hasSize(7));

        Runnable invokeUnselectChildNodesAction = () -> this.invokeFilterSelectionMenuItemsExecutor.execute(FlowIdentifier.FLOW_EDITING_CONTEXT_ID, diagramId.get(), diagramElementsIds, UnselectChildNodesFilterSelectionProvider.ID)
                .isSuccess()
                .hasNewSelection(newSelection -> assertThat(newSelection).hasSize(5));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(getFilterMenuItems)
                .then(invokeUnselectChildNodesAction)
                .then(invokeUnselectAllEdgesAction)
                .then(invokeUnselectAllNodesAction)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
