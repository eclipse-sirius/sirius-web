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
import org.eclipse.sirius.components.collaborative.diagrams.dto.appearance.EdgeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.appearance.EditEdgeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.appearance.ResetEdgeAppearanceInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeType;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.tests.graphql.DeleteFromDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditEdgeAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ResetEdgeAppearanceMutationRunner;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

/**
 * Tests for undo redo edge appearance edition.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class UndoEdgeAppearanceControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EditEdgeAppearanceMutationRunner editEdgeAppearanceMutationRunner;

    @Autowired
    private ResetEdgeAppearanceMutationRunner resetEdgeAppearanceMutationRunner;

    @Autowired
    private DeleteFromDiagramMutationRunner deleteFromDiagramMutationRunner;

    @Autowired
    private UndoMutationRunner undoMutationRunner;

    @Autowired
    private RedoMutationRunner redoMutationRunner;

    @Autowired
    private EdgeDiagramDescriptionProvider edgeDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenDiagramSubscription() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.edgeDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "EdgeDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with an edge, when we edit all its appearance and reset changes and undo, then the edge is properly updated")
    public void givenDiagramWithEdgeWhenWeEditAllItsAppearanceAndResetChangesAndUndoRedoThenEdgeLabelIsProperlyUpdated() {
        var flux = this.givenDiagramSubscription();
        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationEdgeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getEdges())
                    .filteredOn(edge -> edge.getCenterLabel() != null && edge.getCenterLabel().text().equals("sirius-web-application -> sirius-web-domain"))
                    .hasSize(1)
                    .allMatch(edge -> edge.getStyle() != null)
                    .extracting(Edge::getStyle)
                    .allMatch(edgeStyle -> "black".equals(edgeStyle.getColor()))
                    .allMatch(edgeStyle -> edgeStyle.getSize() == 1)
                    .allMatch(edgeStyle -> edgeStyle.getSourceArrow() == ArrowStyle.None)
                    .allMatch(edgeStyle -> edgeStyle.getTargetArrow() == ArrowStyle.InputArrow)
                    .allMatch(edgeStyle -> edgeStyle.getLineStyle() == LineStyle.Solid)
                    .allMatch(edgeStyle -> edgeStyle.getEdgeType() == EdgeType.Manhattan);

            var siriusWebApplicationEdge = new DiagramNavigator(diagram).edgeWithLabel("sirius-web-application -> sirius-web-domain").getEdge();
            siriusWebApplicationEdgeId.set(siriusWebApplicationEdge.getId());
        });


        Runnable setEdgeCustomAppearance = () -> {
            var appearanceInput = new EdgeAppearanceInput(2, "blue", LineStyle.Dash_Dot, ArrowStyle.Circle, ArrowStyle.Circle, EdgeType.SmartManhattan);

            var input = new EditEdgeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(siriusWebApplicationEdgeId.get()),
                    appearanceInput
            );

            this.editEdgeAppearanceMutationRunner.run(input);
        };

        var mutationInputId = UUID.randomUUID();
        Runnable resetEdgeCustomAppearance = () -> {
            var input = new ResetEdgeAppearanceInput(
                    mutationInputId,
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(siriusWebApplicationEdgeId.get()),
                    List.of("COLOR", "SIZE", "LINESTYLE", "SOURCE_ARROW", "TARGET_ARROW", "EDGE_TYPE")
            );

            this.resetEdgeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterResetCustomAppearanceDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getEdges())
                    .filteredOn(edge -> edge.getCenterLabel() != null && edge.getCenterLabel().text().equals("sirius-web-application -> sirius-web-domain"))
                    .hasSize(1)
                    .allMatch(edge -> edge.getStyle() != null)
                    .extracting(Edge::getStyle)
                    .allMatch(edgeStyle -> "black".equals(edgeStyle.getColor()))
                    .allMatch(edgeStyle -> edgeStyle.getSize() == 1)
                    .allMatch(edgeStyle -> edgeStyle.getSourceArrow() == ArrowStyle.None)
                    .allMatch(edgeStyle -> edgeStyle.getTargetArrow() == ArrowStyle.InputArrow)
                    .allMatch(edgeStyle -> edgeStyle.getLineStyle() == LineStyle.Solid)
                    .allMatch(edgeStyle -> edgeStyle.getEdgeType() == EdgeType.Manhattan);
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
                .then(setEdgeCustomAppearance)
                .consumeNextWith(getUpdatedAfterCustomAppearanceDiagramContentConsumer())
                .then(resetEdgeCustomAppearance)
                .consumeNextWith(updatedAfterResetCustomAppearanceDiagramContentConsumer)
                .then(undoChanges)
                .consumeNextWith(getUpdatedAfterCustomAppearanceDiagramContentConsumer())
                .then(redoChanges)
                .consumeNextWith(updatedAfterResetCustomAppearanceDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an edge with an already customised appearance, when we delete the edge and undo the change, the customized property is kept")
    public void givenDiagramWithAnAlreadyCustomisedAppearanceWhenWeDeleteTheEdgeAndUndoTheChangeThenCustomizedPropertyIsKept() {
        var flux = this.givenDiagramSubscription();
        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationEdgeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getEdges())
                    .filteredOn(edge -> edge.getCenterLabel() != null && edge.getCenterLabel().text().equals("sirius-web-application -> sirius-web-domain"))
                    .hasSize(1)
                    .allMatch(edge -> edge.getStyle() != null)
                    .extracting(Edge::getStyle)
                    .allMatch(edgeStyle -> "black".equals(edgeStyle.getColor()))
                    .allMatch(edgeStyle -> edgeStyle.getSize() == 1)
                    .allMatch(edgeStyle -> edgeStyle.getSourceArrow() == ArrowStyle.None)
                    .allMatch(edgeStyle -> edgeStyle.getTargetArrow() == ArrowStyle.InputArrow)
                    .allMatch(edgeStyle -> edgeStyle.getLineStyle() == LineStyle.Solid)
                    .allMatch(edgeStyle -> edgeStyle.getEdgeType() == EdgeType.Manhattan);

            var siriusWebApplicationEdge = new DiagramNavigator(diagram).edgeWithLabel("sirius-web-application -> sirius-web-domain").getEdge();
            siriusWebApplicationEdgeId.set(siriusWebApplicationEdge.getId());
        });


        Runnable setEdgeCustomAppearance = () -> {
            var appearanceInput = new EdgeAppearanceInput(2, "blue", LineStyle.Dash_Dot, ArrowStyle.Circle, ArrowStyle.Circle, EdgeType.SmartManhattan);

            var input = new EditEdgeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(siriusWebApplicationEdgeId.get()),
                    appearanceInput
            );

            this.editEdgeAppearanceMutationRunner.run(input);
        };

        var mutationInputId = UUID.randomUUID();
        Runnable deleteEdge = () -> {
            var input = new DeleteFromDiagramInput(mutationInputId, PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), List.of(), List.of(siriusWebApplicationEdgeId.get()));
            var result = this.deleteFromDiagramMutationRunner.run(input);
            String typename = JsonPath.read(result.data(), "$.data.deleteFromDiagram.__typename");
            assertThat(typename).isEqualTo(DeleteFromDiagramSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedAfterDeleteDiagramElementConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getEdges())
                    .noneMatch(edge -> edge.getCenterLabel() != null && edge.getCenterLabel().text().equals("sirius-web-application -> sirius-web-domain"));
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
                .then(setEdgeCustomAppearance)
                .consumeNextWith(getUpdatedAfterCustomAppearanceDiagramContentConsumer())
                .then(deleteEdge)
                .consumeNextWith(updatedAfterDeleteDiagramElementConsumer)
                .then(undoChanges)
                .consumeNextWith(getUpdatedAfterCustomAppearanceDiagramContentConsumer())
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private  Consumer<Object> getUpdatedAfterCustomAppearanceDiagramContentConsumer() {
        return assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getEdges())
                    .filteredOn(edge -> edge.getCenterLabel() != null && edge.getCenterLabel().text().equals("sirius-web-application -> sirius-web-domain"))
                    .hasSize(1)
                    .allMatch(edge -> edge.getStyle() != null)
                    .extracting(Edge::getStyle)
                    .allMatch(edgeStyle -> "blue".equals(edgeStyle.getColor()))
                    .allMatch(edgeStyle -> edgeStyle.getSize() == 2)
                    .allMatch(edgeStyle -> edgeStyle.getSourceArrow() == ArrowStyle.Circle)
                    .allMatch(edgeStyle -> edgeStyle.getTargetArrow() == ArrowStyle.Circle)
                    .allMatch(edgeStyle -> edgeStyle.getLineStyle() == LineStyle.Dash_Dot)
                    .allMatch(edgeStyle -> edgeStyle.getEdgeType() == EdgeType.SmartManhattan);
        });
    }

}
