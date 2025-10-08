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

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ResetLabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.LabelVisibility;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditLabelAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ResetLabelAppearanceMutationRunner;
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
 * Tests for undo redo edge label appearance edition.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class UndoEditEdgeLabelAppearanceControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EditLabelAppearanceMutationRunner editLabelAppearanceMutationRunner;

    @Autowired
    private ResetLabelAppearanceMutationRunner resetLabelAppearanceMutationRunner;

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

    private Flux<Object> givenSubscriptionToLabelEditableDiagramDiagram() {
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
    @DisplayName("Given a diagram with an edge label, when we edit all its appearance and reset changes and undo, then the label is properly updated")
    public void givenDiagramWithLabelWhenWeEditAllItsAppearanceAndResetChangesAndUndoRedoThenTheLabelIsProperlyUpdated() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();
        var diagramId = new AtomicReference<String>();
        var edgeId = new AtomicReference<String>();
        var edgeIdLabelId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getEdges())
                    .filteredOn(edge -> edge.getCenterLabel() != null && edge.getCenterLabel().text().equals("sirius-web-application -> sirius-web-domain"))
                    .hasSize(1)
                    .allMatch(edge -> edge.getCenterLabel().style().getFontSize() == 14)
                    .allMatch(edge -> !edge.getCenterLabel().style().isItalic())
                    .allMatch(edge -> !edge.getCenterLabel().style().isBold())
                    .allMatch(edge -> !edge.getCenterLabel().style().isUnderline())
                    .allMatch(edge -> !edge.getCenterLabel().style().isStrikeThrough())
                    .allMatch(edge -> "black".equals(edge.getCenterLabel().style().getBorderColor()))
                    .allMatch(edge -> edge.getCenterLabel().style().getBorderRadius() == 3)
                    .allMatch(edge -> edge.getCenterLabel().style().getBorderSize() == 1)
                    .allMatch(edge -> "black".equals(edge.getCenterLabel().style().getColor()))
                    .allMatch(edge -> "transparent".equals(edge.getCenterLabel().style().getBackground()))
                    .allMatch(edge -> LineStyle.Solid.equals(edge.getCenterLabel().style().getBorderStyle()))
                    .allMatch(edge -> LabelVisibility.visible.equals(edge.getCenterLabel().style().getVisibility()));

            var dependencyEdge = diagram.getEdges().stream()
                    .filter(edge -> edge.getCenterLabel().text().equals("sirius-web-application -> sirius-web-domain"))
                    .findFirst().orElseThrow(IllegalStateException::new);
            edgeId.set(dependencyEdge.getId());
            edgeIdLabelId.set(dependencyEdge.getCenterLabel().id());
        });

        Runnable setLabelCustomisation = () -> {
            var appearanceInput = new LabelAppearanceInput(10, true, true, true, true, "blue", 5, 2, LineStyle.Dash, "red", "green", LabelVisibility.hidden);

            var input = new EditLabelAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(edgeId.get()),
                    List.of(edgeIdLabelId.get()),
                    appearanceInput
            );

            this.editLabelAppearanceMutationRunner.run(input);
        };

        var mutationInputId = UUID.randomUUID();
        Runnable resetLabelCustomisation = () -> {
            var input = new ResetLabelAppearanceInput(
                    mutationInputId,
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(edgeId.get()),
                    List.of(edgeIdLabelId.get()),
                    List.of("FONT_SIZE", "ITALIC", "BOLD", "UNDERLINE", "STRIKE_THROUGH", "BORDER_COLOR", "BORDER_RADIUS", "BORDER_SIZE", "BORDER_STYLE", "COLOR", "BACKGROUND", "VISIBILITY")
            );

            this.resetLabelAppearanceMutationRunner.run(input);
        };

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
                .then(setLabelCustomisation)
                .consumeNextWith(getUpdatedAfterAppearanceChangeConsumer())
                .then(resetLabelCustomisation)
                .consumeNextWith(getUpdateAfterResetConsumer())
                .then(undoChanges)
                .consumeNextWith(getUpdatedAfterAppearanceChangeConsumer())
                .then(redoChanges)
                .consumeNextWith(getUpdateAfterResetConsumer())
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Consumer<Object> getUpdatedAfterAppearanceChangeConsumer() {
        return assertRefreshedDiagramThat(diagram -> assertThat(diagram.getEdges())
                .filteredOn(edge -> edge.getCenterLabel() != null && edge.getCenterLabel().text().equals("sirius-web-application -> sirius-web-domain"))
                .hasSize(1)
                .allMatch(edge -> edge.getCenterLabel().style().getFontSize() == 10)
                .allMatch(edge -> edge.getCenterLabel().style().isItalic())
                .allMatch(edge -> edge.getCenterLabel().style().isBold())
                .allMatch(edge -> edge.getCenterLabel().style().isUnderline())
                .allMatch(edge -> edge.getCenterLabel().style().isStrikeThrough())
                .allMatch(edge -> "blue".equals(edge.getCenterLabel().style().getBorderColor()))
                .allMatch(edge -> edge.getCenterLabel().style().getBorderRadius() == 5)
                .allMatch(edge -> edge.getCenterLabel().style().getBorderSize() == 2)
                .allMatch(edge -> "red".equals(edge.getCenterLabel().style().getColor()))
                .allMatch(edge -> "green".equals(edge.getCenterLabel().style().getBackground()))
                .allMatch(edge -> LineStyle.Dash.equals(edge.getCenterLabel().style().getBorderStyle()))
                .allMatch(edge -> LabelVisibility.hidden.equals(edge.getCenterLabel().style().getVisibility())));
    }

    private Consumer<Object> getUpdateAfterResetConsumer() {
        return assertRefreshedDiagramThat(diagram -> assertThat(diagram.getEdges())
                .filteredOn(edge -> edge.getCenterLabel() != null && edge.getCenterLabel().text().equals("sirius-web-application -> sirius-web-domain"))
                .hasSize(1)
                .allMatch(edge -> edge.getCenterLabel().style().getFontSize() == 14)
                .allMatch(edge -> !edge.getCenterLabel().style().isItalic())
                .allMatch(edge -> !edge.getCenterLabel().style().isBold())
                .allMatch(edge -> !edge.getCenterLabel().style().isUnderline())
                .allMatch(edge -> !edge.getCenterLabel().style().isStrikeThrough())
                .allMatch(edge -> "black".equals(edge.getCenterLabel().style().getBorderColor()))
                .allMatch(edge -> edge.getCenterLabel().style().getBorderRadius() == 3)
                .allMatch(edge -> edge.getCenterLabel().style().getBorderSize() == 1)
                .allMatch(edge -> "black".equals(edge.getCenterLabel().style().getColor()))
                .allMatch(edge -> "transparent".equals(edge.getCenterLabel().style().getBackground()))
                .allMatch(edge -> LabelVisibility.visible.equals(edge.getCenterLabel().style().getVisibility())));
    }

}
