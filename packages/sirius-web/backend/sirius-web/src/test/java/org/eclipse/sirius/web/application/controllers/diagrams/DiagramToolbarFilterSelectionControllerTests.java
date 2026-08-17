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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.GetFilterSelectionMenuItemsActionsQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.diagram.services.toolbar.SelectAllNodesFilterSelectionProvider;
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

import com.jayway.jsonpath.JsonPath;
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
    private GetFilterSelectionMenuItemsActionsQueryRunner getFilterSelectionMenuItemsActionsQueryRunner;

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
    @DisplayName("Given a diagram with filer selection actions, when the actions are requested with an empty selection, then the actions can be executed")
    public void givenDiagramWithFilterSelectionActionsWhenTheActionsAreRequestedOnThisEmptySelectionThenTheActionsCanBeExecuted() {
        var flux = this.givenSubscriptionToActionDiagram();
        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
        });

        Runnable getFilterMenuItems = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", FlowIdentifier.FLOW_EDITING_CONTEXT_ID,
                    "representationId", diagramId.get(),
                    "diagramElementIds", List.of()
            );
            var result = this.getFilterSelectionMenuItemsActionsQueryRunner.run(variables);
            List<String> actionsIds = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.toolbar.filterSelectionMenuItems.[*].id");
            List<String> actionsLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.toolbar.filterSelectionMenuItems.[*].label");

            assertThat(actionsIds)
                    .isNotEmpty()
                    .contains(SelectAllNodesFilterSelectionProvider.ID);
            assertThat(actionsLabels)
                    .isNotEmpty()
                    .contains("Select all nodes");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(getFilterMenuItems)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
