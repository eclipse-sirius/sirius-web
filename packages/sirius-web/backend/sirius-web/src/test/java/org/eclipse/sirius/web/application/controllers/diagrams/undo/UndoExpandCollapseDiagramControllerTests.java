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
package org.eclipse.sirius.web.application.controllers.diagrams.undo;

import com.jayway.jsonpath.JsonPath;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolExecutor;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ExpandCollapseDiagramDescriptionProvider;
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

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

/**
 * Integration tests of undo redo expand / collapse mutation diagrams.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class UndoExpandCollapseDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private InvokeSingleClickOnDiagramElementToolExecutor invokeSingleClickOnDiagramElementToolExecutor;

    @Autowired
    private ExpandCollapseDiagramDescriptionProvider expandCollapseDiagramDescriptionProvider;

    @Autowired
    private UndoMutationRunner undoMutationRunner;

    @Autowired
    private RedoMutationRunner redoMutationRunner;

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
    @DisplayName("Given a diagram with expanded nodes , when a tool collapsing nodes is invoked then undo and redo are done, then expanded nodes are collapsed")
    public void givenDiagramWithExpandedNodesByDefaultWhenToolCollapsingNodesIsInvokedUndoRedoIsInvokedThenExpandedNodesAreCollapsed() {
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

        var inputId = new AtomicReference<UUID>(null);
        Runnable collapseNodes = () -> {
            var result = this.invokeSingleClickOnDiagramElementToolExecutor.execute(
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    expandedNodeId.get(),
                    this.expandCollapseDiagramDescriptionProvider.getCollapseNodeToolId(),
                    0,
                    0,
                    List.of()
            ).isSuccess().getResult();

            String id = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.id");
            inputId.set(UUID.fromString(id));
        };

        var updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).nodeWithId(expandedNodeId.get()).getNode())
                    .hasCollapsingState(CollapsingState.COLLAPSED);
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
                .then(collapseNodes)
                .consumeNextWith(updatedDiagramContentMatcher)
                .then(undoChanges)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(redoChanges)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
