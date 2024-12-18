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
import static org.junit.jupiter.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeletionPolicy;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramEventInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropOnDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropOnDiagramSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.RedoInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.api.UndoInput;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.eclipse.sirius.components.diagrams.tests.graphql.DeleteFromDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.DiagramEventSubscriptionRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.DropOnDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.LayoutDiagramMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.FlowIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.undoredo.RedoMutationRunner;
import org.eclipse.sirius.web.tests.undoredo.UndoMutationRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests for undo/redo on diagram.
 *
 * @author gcoutable
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class UndoRedoDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private DropOnDiagramMutationRunner dropOnDiagramMutationRunner;

    @Autowired
    private LayoutDiagramMutationRunner layoutDiagramMutationRunner;

    @Autowired
    private DiagramEventSubscriptionRunner diagramEventSubscriptionRunner;

    @Autowired
    private DeleteFromDiagramMutationRunner deleteFromDiagramMutationRunner;

    @Autowired
    private UndoMutationRunner undoMutationRunner;

    @Autowired
    private RedoMutationRunner redoMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
        this.givenCommittedTransaction.commit();
    }

    private Flux<Object> givenSubscriptionToTopographyUnsynchronized() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                FlowIdentifiers.SAMPLE_FLOW_PROJECT.toString(),
                FlowIdentifiers.SAMPLE_FLOW_TOPOGRAPHY_UNSYNCHRONIZED_DESCRIPTION_ID,
                FlowIdentifiers.FLOW_SYSTEM_ID.toString(),
                "Topography Unsynchronized"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    private Flux<Object> givenSubscriptionToExistingTopographyUnsynchronized(String representationId) {
        var input = new DiagramEventInput(
                UUID.randomUUID(),
                FlowIdentifiers.SAMPLE_FLOW_PROJECT.toString(),
                representationId
        );

        return this.diagramEventSubscriptionRunner.run(input)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData);
    }

    @Test
    @DisplayName("Given a topography unsynchronized diagram, when we undo and redo a drop on diagram, then the undo and redo actions are removing and creating the node again at the same place")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenATopographyUnsynchronizedDiagramWhenWeUndoAndRedoADropOnDiagramThenTheUndoAndRedoActionsAreRemovingAndCreatingTheNodeAgainAtTheSamePlace() {
        var flux = this.givenSubscriptionToTopographyUnsynchronized();

        var diagramId = new AtomicReference<String>();
        var currentRevisionId = new AtomicReference<UUID>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());

                    assertThat(diagram.getNodes()).isEmpty();
                }, () -> fail("Missing diagram"));

        var mutationId = UUID.randomUUID();

        Runnable dropOnDiagram = () -> {
            var input = new DropOnDiagramInput(mutationId, FlowIdentifiers.SAMPLE_FLOW_PROJECT.toString(), diagramId.get(), diagramId.get(), List.of(FlowIdentifiers.FLOW_CENTRAL_UNIT_ID.toString()), 0, 0);
            var result = this.dropOnDiagramMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.dropOnDiagram.__typename");
            Assertions.assertThat(typename).isEqualTo(DropOnDiagramSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> afterDropDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return diagramPayload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes()).size().isEqualTo(1);
                    var node = diagram.getNodes().get(0);
                    nodeId.set(node.getId());

                    assertThat(node.getInsideLabel().getText()).isEqualTo("Central_Unit");
                    assertThat(diagram.getLayoutData().nodeLayoutData()).isEmpty();
                }, () -> fail("Something went wrong during drop refresh"));

        Runnable layoutAfterCauseRefresh = () -> {
            var nodeLayoutData = new NodeLayoutDataInput(nodeId.get(), new Position(0, 0), new Size(150, 70), false);
            var diagramLayoutDataInput = new DiagramLayoutDataInput(List.of(nodeLayoutData), List.of());
            var input = new LayoutDiagramInput(currentRevisionId.get(), FlowIdentifiers.SAMPLE_FLOW_PROJECT.toString(), diagramId.get(), diagramLayoutDataInput);
            var result = this.layoutDiagramMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.layoutDiagram.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> afterLayoutConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes()).size().isEqualTo(1);
                    assertThat(diagram.getNodes().get(0).getId()).isEqualTo(nodeId.get());

                    assertThat(diagram.getLayoutData().nodeLayoutData()).size().isEqualTo(1);
                    var optionalNodeLayoutData = Optional.ofNullable(diagram.getLayoutData().nodeLayoutData().get(nodeId.get()));
                    assertThat(optionalNodeLayoutData).isPresent();
                    NodeLayoutData nodeLayoutData = optionalNodeLayoutData.get();
                    assertThat(nodeLayoutData.position()).isEqualTo(new Position(0, 0));
                    assertThat(nodeLayoutData.size()).isEqualTo(new Size(150, 70));
                    assertThat(nodeLayoutData.resizedByUser()).isFalse();
                }, () -> fail("Something went wrong during layout"));

        Runnable undo = () -> {
            var input = new UndoInput(UUID.randomUUID(), FlowIdentifiers.SAMPLE_FLOW_PROJECT.toString(), mutationId.toString());
            var result = this.undoMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.undo.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Runnable redo = () -> {
            var input = new RedoInput(UUID.randomUUID(), FlowIdentifiers.SAMPLE_FLOW_PROJECT.toString(), mutationId.toString());
            var result = this.redoMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.redo.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(dropOnDiagram)
                .consumeNextWith(afterDropDiagramContentConsumer)
                .then(layoutAfterCauseRefresh)
                .consumeNextWith(afterLayoutConsumer)
                .then(undo)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(redo)
                .consumeNextWith(afterLayoutConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given an existing topography unsynchronized diagram with content, when we undo and redo a graphical remove, then the undo and redo actions are creating again the deleted not and removing once again")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void test() {
        var flux = this.givenSubscriptionToExistingTopographyUnsynchronized(FlowIdentifiers.UNSYNCHRONIZED_TOPOGRAPHY_WITH_CENTRAL_UNIT_REPRESENTATION_ID.toString());
        this.givenCommittedTransaction.commit();

        var diagramId = new AtomicReference<String>();
        var currentRevisionId = new AtomicReference<UUID>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    assertThat(diagram.getNodes()).size().isEqualTo(1);
                    var node = diagram.getNodes().get(0);
                    nodeId.set(node.getId());

                    assertThat(diagram.getLayoutData().nodeLayoutData()).size().isEqualTo(1);
                    var optionalNodeLayoutData = Optional.ofNullable(diagram.getLayoutData().nodeLayoutData().get(node.getId()));
                    assertThat(optionalNodeLayoutData).isPresent();
                    var nodeLayoutData = optionalNodeLayoutData.get();
                    assertThat(nodeLayoutData.position()).isEqualTo(new Position(350, 210));
                    assertThat(nodeLayoutData.size()).isEqualTo(new Size(150, 70));
                }, () -> fail("Missing diagram"));

        var mutationId = UUID.randomUUID();

        Runnable removeGraphicalNode = () -> {
            var input = new DeleteFromDiagramInput(mutationId, FlowIdentifiers.SAMPLE_FLOW_PROJECT.toString(),
                    FlowIdentifiers.UNSYNCHRONIZED_TOPOGRAPHY_WITH_CENTRAL_UNIT_REPRESENTATION_ID.toString(), List.of(nodeId.get()), List.of(), DeletionPolicy.GRAPHICAL);

            var result = this.deleteFromDiagramMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.deleteFromDiagram.__typename");
            Assertions.assertThat(typename).isEqualTo(DeleteFromDiagramSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> afterDeleteFromDiagramConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return diagramPayload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes()).isEmpty();

                    // The layout is not empty yet, because the layout has not been sent.
                    assertThat(diagram.getLayoutData().nodeLayoutData()).isNotEmpty();
                }, () -> fail("Something went wrong during delete from diagram refresh"));

        Runnable layoutAfterCauseRefresh = () -> {
            var diagramLayoutDataInput = new DiagramLayoutDataInput(List.of(), List.of());
            var input = new LayoutDiagramInput(currentRevisionId.get(), FlowIdentifiers.SAMPLE_FLOW_PROJECT.toString(), diagramId.get(), diagramLayoutDataInput);
            var result = this.layoutDiagramMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.layoutDiagram.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> afterLayoutConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes()).isEmpty();

                    assertThat(diagram.getLayoutData().nodeLayoutData()).isEmpty();
                }, () -> fail("Something went wrong during layout"));

        Runnable undo = () -> {
            var input = new UndoInput(UUID.randomUUID(), FlowIdentifiers.SAMPLE_FLOW_PROJECT.toString(), mutationId.toString());
            var result = this.undoMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.undo.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Runnable redo = () -> {
            var input = new RedoInput(UUID.randomUUID(), FlowIdentifiers.SAMPLE_FLOW_PROJECT.toString(), mutationId.toString());
            var result = this.redoMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.redo.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(removeGraphicalNode)
                .consumeNextWith(afterDeleteFromDiagramConsumer)
                .then(layoutAfterCauseRefresh)
                .consumeNextWith(afterLayoutConsumer)
                .then(undo)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(redo)
                .consumeNextWith(afterLayoutConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
