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

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramEventInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.appearance.EdgeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.appearance.EditEdgeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.appearance.ResetEdgeAppearanceInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.EdgeType;
import org.eclipse.sirius.components.diagrams.layoutdata.EdgeAnchorLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.HandleType;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.tests.graphql.DiagramEventSubscriptionRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditEdgeAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ResetEdgeAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.eclipse.sirius.web.data.FlowIdentifier;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.undoredo.RedoMutationRunner;
import org.eclipse.sirius.web.tests.undoredo.UndoMutationRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests for edge appearance changes and their associated layout reset.
 *
 * @author frouene
 */
@Transactional
@GivenSiriusWebServer
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class EdgeAppearanceLayoutControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private DiagramEventSubscriptionRunner diagramEventSubscriptionRunner;

    @Autowired
    private EditEdgeAppearanceMutationRunner editEdgeAppearanceMutationRunner;

    @Autowired
    private ResetEdgeAppearanceMutationRunner resetEdgeAppearanceMutationRunner;

    @Autowired
    private UndoMutationRunner undoMutationRunner;

    @Autowired
    private RedoMutationRunner redoMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given an edge with bends, when its type is edited and undone and redone, then its type and bends change together")
    public void givenEdgeWithBendsWhenItsTypeIsEditedAndUndoneAndRedoneThenItsTypeAndBendsChangeTogether() {
        var initialDiagram = new AtomicReference<Diagram>();
        var inputId = UUID.randomUUID();

        StepVerifier.create(this.givenDiagramSubscription())
                .consumeNextWith(assertRefreshedDiagramThat(diagram -> {
                    initialDiagram.set(diagram);
                    this.assertOriginalLayout(diagram);
                }))
                .then(() -> this.editAppearance(inputId, new EdgeAppearanceInput(null, null, null, null, null, EdgeType.SmartManhattan)))
                .consumeNextWith(assertRefreshedDiagramThat(diagram -> {
                    this.assertResetLayout(diagram, EdgeType.SmartManhattan, initialDiagram.get());
                    this.assertOriginalLayout(initialDiagram.get());
                }))
                .then(() -> this.assertSuccess(this.undoMutationRunner.run(new UndoInput(UUID.randomUUID(), FlowIdentifier.FLOW_EDITING_CONTEXT_ID, inputId)), "undo"))
                .consumeNextWith(assertRefreshedDiagramThat(this::assertOriginalLayout))
                .then(() -> this.assertSuccess(this.redoMutationRunner.run(new RedoInput(UUID.randomUUID(), FlowIdentifier.FLOW_EDITING_CONTEXT_ID, inputId)), "redo"))
                .consumeNextWith(assertRefreshedDiagramThat(diagram -> this.assertResetLayout(diagram, EdgeType.SmartManhattan, initialDiagram.get())))
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given an edge with bends, when its type customization is reset and undone and redone, then its type and bends change together")
    public void givenEdgeWithBendsWhenItsTypeCustomizationIsResetAndUndoneAndRedoneThenItsTypeAndBendsChangeTogether() {
        var initialDiagram = new AtomicReference<Diagram>();
        var inputId = UUID.randomUUID();

        StepVerifier.create(this.givenDiagramSubscription())
                .consumeNextWith(assertRefreshedDiagramThat(diagram -> {
                    initialDiagram.set(diagram);
                    this.assertOriginalLayout(diagram);
                }))
                .then(() -> this.assertSuccess(this.resetEdgeAppearanceMutationRunner.run(new ResetEdgeAppearanceInput(inputId,
                        FlowIdentifier.FLOW_EDITING_CONTEXT_ID, FlowIdentifier.FLOW_DIAGRAM_REPRESENTATION_ID, List.of(FlowIdentifier.FLOW_EDGE_DATA_FLOW_1), List.of("EDGE_TYPE"))), "resetEdgeAppearance"))
                .consumeNextWith(assertRefreshedDiagramThat(diagram -> this.assertResetLayout(diagram, EdgeType.Manhattan, initialDiagram.get())))
                .then(() -> this.assertSuccess(this.undoMutationRunner.run(new UndoInput(UUID.randomUUID(), FlowIdentifier.FLOW_EDITING_CONTEXT_ID, inputId)), "undo"))
                .consumeNextWith(assertRefreshedDiagramThat(this::assertOriginalLayout))
                .then(() -> this.assertSuccess(this.redoMutationRunner.run(new RedoInput(UUID.randomUUID(), FlowIdentifier.FLOW_EDITING_CONTEXT_ID, inputId)), "redo"))
                .consumeNextWith(assertRefreshedDiagramThat(diagram -> this.assertResetLayout(diagram, EdgeType.Manhattan, initialDiagram.get())))
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given an edge with bends, when its color is edited and reset, then its bends are preserved")
    public void givenEdgeWithBendsWhenItsColorIsEditedAndResetThenItsBendsArePreserved() {
        StepVerifier.create(this.givenDiagramSubscription())
                .consumeNextWith(assertRefreshedDiagramThat(this::assertOriginalLayout))
                .then(() -> this.editAppearance(UUID.randomUUID(), new EdgeAppearanceInput(null, "blue", null, null, null, null)))
                .consumeNextWith(assertRefreshedDiagramThat(diagram -> {
                    this.assertOriginalLayout(diagram);
                    assertThat(new DiagramNavigator(diagram).edgeWithId(FlowIdentifier.FLOW_EDGE_DATA_FLOW_1).getEdge().getStyle().getColor()).isEqualTo("blue");
                }))
                .then(() -> this.assertSuccess(this.resetEdgeAppearanceMutationRunner.run(new ResetEdgeAppearanceInput(UUID.randomUUID(),
                        FlowIdentifier.FLOW_EDITING_CONTEXT_ID, FlowIdentifier.FLOW_DIAGRAM_REPRESENTATION_ID, List.of(FlowIdentifier.FLOW_EDGE_DATA_FLOW_1), List.of("COLOR"))), "resetEdgeAppearance"))
                .consumeNextWith(assertRefreshedDiagramThat(this::assertOriginalLayout))
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Flux<Object> givenDiagramSubscription() {
        var result = this.diagramEventSubscriptionRunner.run(new DiagramEventInput(UUID.randomUUID(),
                FlowIdentifier.FLOW_EDITING_CONTEXT_ID, FlowIdentifier.FLOW_DIAGRAM_REPRESENTATION_ID));
        assertThat(result.errors()).isEmpty();
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
        return result.flux().filter(DiagramRefreshedEventPayload.class::isInstance);
    }

    private void editAppearance(UUID inputId, EdgeAppearanceInput appearance) {
        var input = new EditEdgeAppearanceInput(inputId, FlowIdentifier.FLOW_EDITING_CONTEXT_ID,
                FlowIdentifier.FLOW_DIAGRAM_REPRESENTATION_ID, List.of(FlowIdentifier.FLOW_EDGE_DATA_FLOW_1), appearance);
        this.assertSuccess(this.editEdgeAppearanceMutationRunner.run(input), "editEdgeAppearance");
    }

    private void assertSuccess(GraphQLResult result, String mutationName) {
        String typename = JsonPath.read(result.data(), "$.data." + mutationName + ".__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
    }

    private void assertOriginalLayout(Diagram diagram) {
        var edge = new DiagramNavigator(diagram).edgeWithId(FlowIdentifier.FLOW_EDGE_DATA_FLOW_1).getEdge();
        assertThat(edge.getStyle().getEdgeType()).isEqualTo(EdgeType.Oblique);
        var layout = diagram.getLayoutData().edgeLayoutData().get(edge.getId());
        assertThat(layout.bendingPoints()).containsExactly(new Position(10, 20), new Position(30, 40));
        assertThat(layout.relativePositionBendingPoints()).containsExactly(new Position(1, 2), new Position(3, 4));
        assertThat(layout.edgeAnchorLayoutData()).containsExactly(new EdgeAnchorLayoutData(FlowIdentifier.FLOW_EDGE_DATA_FLOW_2, 0.5f, "left", HandleType.source));
    }

    private void assertResetLayout(Diagram diagram, EdgeType expectedType, Diagram previousDiagram) {
        var edge = new DiagramNavigator(diagram).edgeWithId(FlowIdentifier.FLOW_EDGE_DATA_FLOW_1).getEdge();
        assertThat(edge.getStyle().getEdgeType()).isEqualTo(expectedType);
        var layout = diagram.getLayoutData().edgeLayoutData().get(edge.getId());
        var previousLayout = previousDiagram.getLayoutData();
        assertThat(layout.bendingPoints()).isEmpty();
        assertThat(layout.relativePositionBendingPoints()).isEmpty();
        assertThat(layout.edgeAnchorLayoutData()).isEqualTo(previousLayout.edgeLayoutData().get(edge.getId()).edgeAnchorLayoutData());
        assertThat(diagram.getLayoutData().edgeLayoutData().get(FlowIdentifier.FLOW_EDGE_DATA_FLOW_2))
                .isEqualTo(previousLayout.edgeLayoutData().get(FlowIdentifier.FLOW_EDGE_DATA_FLOW_2));
        assertThat(diagram.getLayoutData().nodeLayoutData()).isEqualTo(previousLayout.nodeLayoutData());
        assertThat(diagram.getLayoutData().labelLayoutData()).isEqualTo(previousLayout.labelLayoutData());
    }
}
