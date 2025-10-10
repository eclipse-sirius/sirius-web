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
package org.eclipse.sirius.web.application.controllers.diagrams.undo;

import com.jayway.jsonpath.JsonPath;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EdgeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.layoutdata.HandleLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.HandleType;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.eclipse.sirius.components.diagrams.tests.graphql.DeleteFromDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.LayoutDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.EdgeDiagramDescriptionProvider;
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
import java.util.function.Consumer;

import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

/**
 * Integration tests of undo redo for the edge layout of a diagram.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class UndoEdgeLayoutDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EdgeDiagramDescriptionProvider diagramDescriptionProvider;

    @Autowired
    private UndoMutationRunner undoMutationRunner;

    @Autowired
    private RedoMutationRunner redoMutationRunner;

    @Autowired
    private DeleteFromDiagramMutationRunner deleteFromDiagramMutationRunner;

    @Autowired
    private LayoutDiagramMutationRunner layoutDiagramRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToVisibilityDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.diagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "VisibilityDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with no initial layout, when the diagram is layouted and undo redo is performed, then the diagram is correctly updated")
    public void givenDiagramWithNoInitialLayoutWhenWeLayoutAndUndoRedoThenDiagramIsUpdatedCorrectly() {
        var flux = this.givenSubscriptionToVisibilityDiagram();
        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();
        var siriusWebApplicationEdgeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
            var siriusWebApplicationEdge = new DiagramNavigator(diagram).edgeWithLabel("sirius-web-application -> sirius-web-domain").getEdge();
            siriusWebApplicationEdgeId.set(siriusWebApplicationEdge.getId());
        });

        Runnable initialLayout = () -> {
            var handleLayoutData = new HandleLayoutData(siriusWebApplicationEdgeId.get(), new Position(10, 10), "top", HandleType.source);
            var nodeLayoutDataInput = new NodeLayoutDataInput(siriusWebApplicationNodeId.get(), new Position(10, 10), new Size(10, 10), false, false, List.of(handleLayoutData), new Size(10, 10));
            var bendingPoints = List.of(new Position(10, 10), new Position(20, 20), new Position(30, 30));
            var edgeLayoutDataInput = new EdgeLayoutDataInput(siriusWebApplicationEdgeId.get(), bendingPoints, List.of());
            var diagramLayoutDataInput = new DiagramLayoutDataInput(List.of(nodeLayoutDataInput), List.of(edgeLayoutDataInput), List.of());
            var input = new LayoutDiagramInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), LayoutDiagramInput.CAUSE_LAYOUT, diagramLayoutDataInput);
            var result = this.layoutDiagramRunner.run(input);
            String typename = JsonPath.read(result.data(), "$.data.layoutDiagram.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram ->
                assertUpdatedDiagram(diagram, siriusWebApplicationNodeId.get(), siriusWebApplicationEdgeId.get()));

        var mutationInputId = UUID.randomUUID();
        Runnable secondLayout = () -> {
            var handleLayoutData = new HandleLayoutData(siriusWebApplicationEdgeId.get(), new Position(20, 20), "bottom", HandleType.source);
            var bendingPoints = List.of(new Position(20, 20), new Position(30, 30), new Position(40, 40));
            var nodeLayoutDataInput = new NodeLayoutDataInput(siriusWebApplicationNodeId.get(), new Position(10, 10), new Size(10, 10), true, true, List.of(handleLayoutData), new Size(10, 10));
            var edgeLayoutDataInput = new EdgeLayoutDataInput(siriusWebApplicationEdgeId.get(), bendingPoints, List.of());
            var diagramLayoutDataInput = new DiagramLayoutDataInput(List.of(nodeLayoutDataInput), List.of(edgeLayoutDataInput), List.of());
            var input = new LayoutDiagramInput(mutationInputId, PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), LayoutDiagramInput.CAUSE_LAYOUT, diagramLayoutDataInput);
            var result = this.layoutDiagramRunner.run(input);
            String typename = JsonPath.read(result.data(), "$.data.layoutDiagram.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedSecondDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getLayoutData().nodeLayoutData().get(siriusWebApplicationNodeId.get())).isNotNull();
            var nodeLayoutData = diagram.getLayoutData().nodeLayoutData().get(siriusWebApplicationNodeId.get());
            assertThat(nodeLayoutData.position().x()).isEqualTo(10);
            assertThat(nodeLayoutData.position().y()).isEqualTo(10);
            assertThat(nodeLayoutData.size().height()).isEqualTo(10);
            assertThat(nodeLayoutData.size().width()).isEqualTo(10);
            assertThat(nodeLayoutData.resizedByUser()).isTrue();
            var handleLayoutData = nodeLayoutData.handleLayoutData();
            assertThat(handleLayoutData.get(0)).isNotNull();
            assertThat(handleLayoutData.get(0).handlePosition()).isEqualTo("bottom");
            assertThat(handleLayoutData.get(0).position()).isEqualTo(new Position(20, 20));
            var edgeLayoutData = diagram.getLayoutData().edgeLayoutData().get(siriusWebApplicationEdgeId.get());
            assertThat(edgeLayoutData.bendingPoints()).contains(new Position(20, 20), new Position(30, 30), new Position(40, 40));
        });

        Runnable undoChanges = () -> {
            var input = new UndoInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    mutationInputId
            );

            this.undoMutationRunner.run(input);
        };
        Runnable redoChanges = () -> {
            var input = new RedoInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    mutationInputId
            );

            this.redoMutationRunner.run(input);
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(initialLayout)
                .consumeNextWith(updatedDiagramContentMatcher)
                .then(secondLayout)
                .consumeNextWith(updatedSecondDiagramContentMatcher)
                .then(undoChanges)
                .consumeNextWith(updatedDiagramContentMatcher)
                .then(redoChanges)
                .consumeNextWith(updatedSecondDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(50));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with no initial layout, when the diagram is layouted and the node is deleted and the change undo, then the diagram is correctly updated")
    public void givenDiagramWithNoInitialLayoutWhenWeLayoutAnDeletedUndoThenDiagramIsUpdatedCorrectly() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();
        var siriusWebApplicationEdgeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
            var siriusWebApplicationEdge = new DiagramNavigator(diagram).edgeWithLabel("sirius-web-application -> sirius-web-domain").getEdge();
            siriusWebApplicationEdgeId.set(siriusWebApplicationEdge.getId());
        });

        Runnable initialLayout = () -> {
            var handleLayoutData = new HandleLayoutData(siriusWebApplicationEdgeId.get(), new Position(10, 10), "top", HandleType.source);
            var nodeLayoutDataInput = new NodeLayoutDataInput(siriusWebApplicationNodeId.get(), new Position(10, 10), new Size(10, 10), false, false, List.of(handleLayoutData), new Size(10, 10));
            var bendingPoints = List.of(new Position(10, 10), new Position(20, 20), new Position(30, 30));
            var edgeLayoutDataInput = new EdgeLayoutDataInput(siriusWebApplicationEdgeId.get(), bendingPoints, List.of());
            var diagramLayoutDataInput = new DiagramLayoutDataInput(List.of(nodeLayoutDataInput), List.of(edgeLayoutDataInput), List.of());
            var input = new LayoutDiagramInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), LayoutDiagramInput.CAUSE_LAYOUT, diagramLayoutDataInput);
            var result = this.layoutDiagramRunner.run(input);
            String typename = JsonPath.read(result.data(), "$.data.layoutDiagram.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram ->
                assertUpdatedDiagram(diagram, siriusWebApplicationNodeId.get(), siriusWebApplicationEdgeId.get()));

        var mutationInputId = UUID.randomUUID();
        Runnable deleteNode = () -> {
            var input = new DeleteFromDiagramInput(mutationInputId, PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), List.of(siriusWebApplicationNodeId.get()), List.of());
            var result = this.deleteFromDiagramMutationRunner.run(input);
            String typename = JsonPath.read(result.data(), "$.data.deleteFromDiagram.__typename");
            assertThat(typename).isEqualTo(DeleteFromDiagramSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedAfterDeleteDiagramElementConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .allMatch(node -> !node.getInsideLabel().getText().equals("sirius-web-application"));
        });

        Runnable undoChanges = () -> {
            var input = new UndoInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    mutationInputId
            );

            this.undoMutationRunner.run(input);
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(initialLayout)
                .consumeNextWith(updatedDiagramContentMatcher)
                .then(deleteNode)
                .consumeNextWith(updatedAfterDeleteDiagramElementConsumer)
                .then(undoChanges)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private void assertUpdatedDiagram(Diagram diagram, String siriusWebApplicationNodeId, String siriusWebApplicationEdgeId) {
        assertThat(diagram.getLayoutData().nodeLayoutData().get(siriusWebApplicationNodeId)).isNotNull();
        var nodeLayoutData = diagram.getLayoutData().nodeLayoutData().get(siriusWebApplicationNodeId);
        assertThat(nodeLayoutData.position().x()).isEqualTo(10);
        assertThat(nodeLayoutData.position().y()).isEqualTo(10);
        assertThat(nodeLayoutData.size().width()).isEqualTo(10);
        assertThat(nodeLayoutData.size().height()).isEqualTo(10);
        assertThat(nodeLayoutData.resizedByUser()).isFalse();
        var handleLayoutData = nodeLayoutData.handleLayoutData();
        assertThat(handleLayoutData.get(0)).isNotNull();
        assertThat(handleLayoutData.get(0).handlePosition()).isEqualTo("top");
        assertThat(handleLayoutData.get(0).position()).isEqualTo(new Position(10, 10));
        var edgeLayoutData = diagram.getLayoutData().edgeLayoutData().get(siriusWebApplicationEdgeId);
        assertThat(edgeLayoutData.bendingPoints()).contains(new Position(10, 10), new Position(20, 20), new Position(30, 30));
    };

}