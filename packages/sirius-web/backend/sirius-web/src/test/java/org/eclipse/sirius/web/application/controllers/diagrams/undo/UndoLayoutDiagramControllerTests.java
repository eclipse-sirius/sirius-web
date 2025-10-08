/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LabelLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.eclipse.sirius.components.diagrams.tests.graphql.DeleteFromDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.LayoutDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.UndoRedoDiagramDescriptionProvider;
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

/**
 * Integration tests of undo redo for the layout of a diagram.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class UndoLayoutDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private UndoRedoDiagramDescriptionProvider diagramDescriptionProvider;

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
        var siriusWebApplicationOutsideLabelId =  new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
            siriusWebApplicationOutsideLabelId.set(siriusWebApplicationNode.getOutsideLabels().get(0).id());
        });

        Runnable initialLayout = () -> {
            var nodeLayoutDataInput = new NodeLayoutDataInput(siriusWebApplicationNodeId.get(), new Position(10, 10), new Size(10, 10), false, false, List.of(), new Size(20, 20));
            var labelLayoutDataInput = new LabelLayoutDataInput(siriusWebApplicationOutsideLabelId.get(), new Position(10, 10), new Size(10, 10), false, false);
            var diagramLayoutDataInput = new DiagramLayoutDataInput(List.of(nodeLayoutDataInput), List.of(), List.of(labelLayoutDataInput));
            var input = new LayoutDiagramInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), LayoutDiagramInput.CAUSE_LAYOUT, diagramLayoutDataInput);
            var result = this.layoutDiagramRunner.run(input);
            String typename = JsonPath.read(result.data(), "$.data.layoutDiagram.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram ->
                this.assertInitialDiagramLayout(diagram, siriusWebApplicationNodeId.get(), siriusWebApplicationOutsideLabelId.get()));

        var mutationInputId = UUID.randomUUID();
        Runnable secondLayout = () -> {
            var nodeLayoutDataInput = new NodeLayoutDataInput(siriusWebApplicationNodeId.get(), new Position(20, 20), new Size(30, 30), true, true, List.of(), new Size(20, 20));
            var labelLayoutDataInput = new LabelLayoutDataInput(siriusWebApplicationOutsideLabelId.get(), new Position(20, 20), new Size(30, 30), true, true);
            var diagramLayoutDataInput = new DiagramLayoutDataInput(List.of(nodeLayoutDataInput), List.of(), List.of(labelLayoutDataInput));
            var input = new LayoutDiagramInput(mutationInputId, PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), LayoutDiagramInput.CAUSE_LAYOUT, diagramLayoutDataInput);
            var result = this.layoutDiagramRunner.run(input);
            String typename = JsonPath.read(result.data(), "$.data.layoutDiagram.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedSecondDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getLayoutData().nodeLayoutData().get(siriusWebApplicationNodeId.get())).isNotNull();
            var nodeLayoutData = diagram.getLayoutData().nodeLayoutData().get(siriusWebApplicationNodeId.get());
            assertThat(nodeLayoutData.position().x()).isEqualTo(20);
            assertThat(nodeLayoutData.position().y()).isEqualTo(20);
            assertThat(nodeLayoutData.size().height()).isEqualTo(30);
            assertThat(nodeLayoutData.size().width()).isEqualTo(30);
            assertThat(nodeLayoutData.resizedByUser()).isTrue();
            assertThat(nodeLayoutData.movedByUser()).isTrue();

            var labelLayoutData = diagram.getLayoutData().labelLayoutData().get(siriusWebApplicationOutsideLabelId.get());
            assertThat(labelLayoutData.position().x()).isEqualTo(20);
            assertThat(labelLayoutData.position().y()).isEqualTo(20);
            assertThat(labelLayoutData.size().height()).isEqualTo(30);
            assertThat(labelLayoutData.size().width()).isEqualTo(30);
            assertThat(labelLayoutData.resizedByUser()).isTrue();
            assertThat(labelLayoutData.movedByUser()).isTrue();
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
    public void givenDiagramWithNoInitialLayoutWhenWeLayoutAnDeletedUndoThenDiagramIsUpdatedCorrectly2() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();
        var siriusWebApplicationOutsideLabelId =  new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
            siriusWebApplicationOutsideLabelId.set(siriusWebApplicationNode.getOutsideLabels().get(0).id());
        });

        Runnable initialLayout = () -> {
            var nodeLayoutDataInput = new NodeLayoutDataInput(siriusWebApplicationNodeId.get(), new Position(10, 10), new Size(10, 10), false, false, List.of(), new Size(10, 10));
            var labelLayoutDataInput = new LabelLayoutDataInput(siriusWebApplicationOutsideLabelId.get(), new Position(10, 10), new Size(10, 10), false, false);
            var diagramLayoutDataInput = new DiagramLayoutDataInput(List.of(nodeLayoutDataInput), List.of(), List.of(labelLayoutDataInput));
            var input = new LayoutDiagramInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), LayoutDiagramInput.CAUSE_LAYOUT, diagramLayoutDataInput);
            var result = this.layoutDiagramRunner.run(input);
            String typename = JsonPath.read(result.data(), "$.data.layoutDiagram.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram ->
                this.assertInitialDiagramLayout(diagram, siriusWebApplicationNodeId.get(), siriusWebApplicationOutsideLabelId.get()));

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

    public void assertInitialDiagramLayout(Diagram diagram, String nodeId, String labelId) {
        assertThat(diagram.getLayoutData().nodeLayoutData().get(nodeId)).isNotNull();
        var nodeLayoutData = diagram.getLayoutData().nodeLayoutData().get(nodeId);
        assertThat(nodeLayoutData.position().x()).isEqualTo(10);
        assertThat(nodeLayoutData.position().y()).isEqualTo(10);
        assertThat(nodeLayoutData.size().width()).isEqualTo(10);
        assertThat(nodeLayoutData.size().height()).isEqualTo(10);
        assertThat(nodeLayoutData.resizedByUser()).isFalse();
        assertThat(nodeLayoutData.movedByUser()).isFalse();

        var labelLayoutData = diagram.getLayoutData().labelLayoutData().get(labelId);
        assertThat(labelLayoutData.position().x()).isEqualTo(10);
        assertThat(labelLayoutData.position().y()).isEqualTo(10);
        assertThat(labelLayoutData.size().width()).isEqualTo(10);
        assertThat(labelLayoutData.size().height()).isEqualTo(10);
        assertThat(labelLayoutData.resizedByUser()).isFalse();
        assertThat(labelLayoutData.movedByUser()).isFalse();
    }

}
