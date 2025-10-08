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

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ResetLabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.LabelVisibility;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.tests.graphql.DeleteFromDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditLabelAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ResetLabelAppearanceMutationRunner;
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
 * Tests for undo redo inside label appearance edition.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class UndoEditInsideLabelAppearanceControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EditLabelAppearanceMutationRunner editLabelAppearanceMutationRunner;

    @Autowired
    private ResetLabelAppearanceMutationRunner resetLabelAppearanceMutationRunner;

    @Autowired
    private DeleteFromDiagramMutationRunner deleteFromDiagramMutationRunner;

    @Autowired
    private UndoMutationRunner undoMutationRunner;

    @Autowired
    private RedoMutationRunner redoMutationRunner;

    @Autowired
    private UndoRedoDiagramDescriptionProvider diagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenDiagramSubscription() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.diagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "EditLabelAppearanceDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a node with an already customised appearance, when we delete the node and undo the change, the customized property is kept")
    public void givenDiagramWithAnAlreadyCustomisedAppearanceWhenWeDeleteTheNodeAndUndoTheChangeThenCustomizedPropertyIsKept() {
        var flux = this.givenDiagramSubscription();
        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();
        var siriusWebApplicationNodeInsideLabelId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getInsideLabel().getStyle().getFontSize() == 14)
                    .allMatch(node -> !node.getInsideLabel().getStyle().isItalic())
                    .allMatch(node -> !node.getInsideLabel().getStyle().isBold())
                    .allMatch(node -> !node.getInsideLabel().getStyle().isUnderline())
                    .allMatch(node -> !node.getInsideLabel().getStyle().isStrikeThrough())
                    .allMatch(node -> "black".equals(node.getInsideLabel().getStyle().getBorderColor()))
                    .allMatch(node -> node.getInsideLabel().getStyle().getBorderRadius() == 3)
                    .allMatch(node -> node.getInsideLabel().getStyle().getBorderSize() == 1)
                    .allMatch(node -> "black".equals(node.getInsideLabel().getStyle().getColor()))
                    .allMatch(node -> "transparent".equals(node.getInsideLabel().getStyle().getBackground()))
                    .allMatch(node -> LineStyle.Solid.equals(node.getInsideLabel().getStyle().getBorderStyle()))
                    .allMatch(node -> LabelVisibility.visible.equals(node.getInsideLabel().getStyle().getVisibility()));

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
            siriusWebApplicationNodeInsideLabelId.set(siriusWebApplicationNode.getInsideLabel().getId());
        });

        Runnable setLabelCustomisation = () -> {
            var appearanceInput = new LabelAppearanceInput(10, true, true, true, true, "blue", 5, 2, LineStyle.Dash, "red", "green", LabelVisibility.hidden);

            var input = new EditLabelAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(siriusWebApplicationNodeId.get()),
                    List.of(siriusWebApplicationNodeInsideLabelId.get()),
                    appearanceInput
            );

            this.editLabelAppearanceMutationRunner.run(input);
        };

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
                .then(setLabelCustomisation)
                .consumeNextWith(getUpdatedAfterAppearanceChangeConsumer())
                .then(deleteNode)
                .consumeNextWith(updatedAfterDeleteDiagramElementConsumer)
                .then(undoChanges)
                .consumeNextWith(getUpdatedAfterAppearanceChangeConsumer())
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a label, when we edit all its appearance and reset changes and undo, then the label is properly updated")
    public void givenDiagramWithLabelWhenWeEditAllItsAppearanceAndResetChangesAndUndoRedoThenTheLabelIsProperlyUpdated() {
        var flux = this.givenDiagramSubscription();
        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();
        var siriusWebApplicationNodeInsideLabelId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getInsideLabel().getStyle().getFontSize() == 14)
                    .allMatch(node -> !node.getInsideLabel().getStyle().isItalic())
                    .allMatch(node -> !node.getInsideLabel().getStyle().isBold())
                    .allMatch(node -> !node.getInsideLabel().getStyle().isUnderline())
                    .allMatch(node -> !node.getInsideLabel().getStyle().isStrikeThrough())
                    .allMatch(node -> "black".equals(node.getInsideLabel().getStyle().getBorderColor()))
                    .allMatch(node -> node.getInsideLabel().getStyle().getBorderRadius() == 3)
                    .allMatch(node -> node.getInsideLabel().getStyle().getBorderSize() == 1)
                    .allMatch(node -> "black".equals(node.getInsideLabel().getStyle().getColor()))
                    .allMatch(node -> "transparent".equals(node.getInsideLabel().getStyle().getBackground()))
                    .allMatch(node -> LineStyle.Solid.equals(node.getInsideLabel().getStyle().getBorderStyle()))
                    .allMatch(node -> LabelVisibility.visible.equals(node.getInsideLabel().getStyle().getVisibility()));

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
            siriusWebApplicationNodeInsideLabelId.set(siriusWebApplicationNode.getInsideLabel().getId());
        });

        Runnable setLabelCustomisation = () -> {
            var appearanceInput = new LabelAppearanceInput(10, true, true, true, true, "blue", 5, 2, LineStyle.Dash, "red", "green", LabelVisibility.hidden);

            var input = new EditLabelAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(siriusWebApplicationNodeId.get()),
                    List.of(siriusWebApplicationNodeInsideLabelId.get()),
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
                    List.of(siriusWebApplicationNodeId.get()),
                    List.of(siriusWebApplicationNodeInsideLabelId.get()),
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
        return assertRefreshedDiagramThat(diagram -> assertThat(diagram.getNodes())
                .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                .hasSize(1)
                .allMatch(node -> node.getInsideLabel().getStyle().getFontSize() == 10)
                .allMatch(node -> node.getInsideLabel().getStyle().isItalic())
                .allMatch(node -> node.getInsideLabel().getStyle().isBold())
                .allMatch(node -> node.getInsideLabel().getStyle().isUnderline())
                .allMatch(node -> node.getInsideLabel().getStyle().isStrikeThrough())
                .allMatch(node -> "blue".equals(node.getInsideLabel().getStyle().getBorderColor()))
                .allMatch(node -> node.getInsideLabel().getStyle().getBorderRadius() == 5)
                .allMatch(node -> node.getInsideLabel().getStyle().getBorderSize() == 2)
                .allMatch(node -> "red".equals(node.getInsideLabel().getStyle().getColor()))
                .allMatch(node -> "green".equals(node.getInsideLabel().getStyle().getBackground()))
                .allMatch(node -> LineStyle.Dash.equals(node.getInsideLabel().getStyle().getBorderStyle()))
                .allMatch(node -> LabelVisibility.hidden.equals(node.getInsideLabel().getStyle().getVisibility())));
    }

    private Consumer<Object> getUpdateAfterResetConsumer() {
        return assertRefreshedDiagramThat(diagram -> assertThat(diagram.getNodes())
                .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                .hasSize(1)
                .allMatch(node -> node.getInsideLabel().getStyle().getFontSize() == 14)
                .allMatch(node -> !node.getInsideLabel().getStyle().isItalic())
                .allMatch(node -> !node.getInsideLabel().getStyle().isBold())
                .allMatch(node -> !node.getInsideLabel().getStyle().isUnderline())
                .allMatch(node -> !node.getInsideLabel().getStyle().isStrikeThrough())
                .allMatch(node -> "black".equals(node.getInsideLabel().getStyle().getBorderColor()))
                .allMatch(node -> node.getInsideLabel().getStyle().getBorderRadius() == 3)
                .allMatch(node -> node.getInsideLabel().getStyle().getBorderSize() == 1)
                .allMatch(node -> "black".equals(node.getInsideLabel().getStyle().getColor()))
                .allMatch(node -> "transparent".equals(node.getInsideLabel().getStyle().getBackground()))
                .allMatch(node -> LabelVisibility.visible.equals(node.getInsideLabel().getStyle().getVisibility())));
    }

}
