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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EditRectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.RectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ResetLabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ResetNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditRectangularNodeAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ResetLabelAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ResetNodeAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.EditRectangularNodeAppearanceDiagramDescriptionProvider;
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
 * Tests for rectangular node appearance edition.
 *
 * @author nvannier
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class EditRectangularNodeAppearanceControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EditRectangularNodeAppearanceMutationRunner editRectangularNodeAppearanceMutationRunner;

    @Autowired
    private ResetLabelAppearanceMutationRunner resetLabelAppearanceMutationRunner;

    @Autowired
    private ResetNodeAppearanceMutationRunner resetNodeAppearanceMutationRunner;

    @Autowired
    private EditRectangularNodeAppearanceDiagramDescriptionProvider editRectangularNodeAppearanceDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenDiagramSubscription() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.editRectangularNodeAppearanceDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "EditRectangularNodeAppearanceDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("""
            Setting a diagram node inside label to bold,
            then the same node's background to be red,
            and then resetting the label back to not being bold
            and then the node's background to not being red, back to the default style""")
    public void setNodeLabelBoldAndReset() {
        var flux = givenDiagramSubscription();
        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();
        var siriusWebApplicationNodeInsideLabelId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    assertThat(diagram.getNodes())
                            .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                            .hasSize(1)
                            .allMatch(node -> !node.getInsideLabel().getStyle()
                                    .isBold() && node.getStyle() instanceof RectangularNodeStyle rectangularNodeStyle && "#0000ff".equals(rectangularNodeStyle.getBackground()));

                    var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
                    siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
                    siriusWebApplicationNodeInsideLabelId.set(siriusWebApplicationNode.getInsideLabel().getId());
                }, () -> fail("Missing diagram or invalid state for tested node."));

        Runnable setLabelBold = getSetLabelBoldRunnable(diagramId, siriusWebApplicationNodeId, siriusWebApplicationNodeInsideLabelId);

        Consumer<Object> updatedAfterBoldDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes())
                            .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                            .hasSize(1)
                            .allMatch(node -> node.getInsideLabel().getStyle()
                                    .isBold() && node.getStyle() instanceof RectangularNodeStyle rectangularNodeStyle && "#0000ff".equals(rectangularNodeStyle.getBackground()));
                }, () -> fail("Missing diagram or node inside label has not been set to be bold or the node's background has changed unexpectedly."));

        Runnable setNodeBackgroundRed = getSetNodeBackgroundRedRunnable(diagramId, siriusWebApplicationNodeId);

        Consumer<Object> updatedAfterRedBackgroundDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes())
                            .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                            .hasSize(1)
                            .allMatch(node -> node.getInsideLabel().getStyle()
                                    .isBold() && node.getStyle() instanceof RectangularNodeStyle rectangularNodeStyle && "#ff0000".equals(rectangularNodeStyle.getBackground()));
                }, () -> fail("Missing diagram or node's background has not been set to red or node's inside label has unexpectedly switched back to not being bold."));

        Runnable resetLabelBold = getResetLabelBoldRunnable(diagramId, siriusWebApplicationNodeId, siriusWebApplicationNodeInsideLabelId);

        Consumer<Object> updatedAfterResetBoldDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes())
                            .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                            .hasSize(1)
                            .allMatch(node -> !node.getInsideLabel().getStyle()
                                    .isBold() && node.getStyle() instanceof RectangularNodeStyle rectangularNodeStyle && "#ff0000".equals(rectangularNodeStyle.getBackground()));
                }, () -> fail("Missing diagram or node inside label has not been reset to not be bold or node's background has been unexpectedly switched back to not being red."));

        Runnable resetNodeBackgroundRed = getResetNodeBackgroundRedRunnable(diagramId, siriusWebApplicationNodeId);

        Consumer<Object> updatedAfterResetRedBackgroundDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes())
                            .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                            .hasSize(1)
                            .allMatch(node -> !node.getInsideLabel().getStyle()
                                    .isBold() && node.getStyle() instanceof RectangularNodeStyle rectangularNodeStyle && "#0000ff".equals(rectangularNodeStyle.getBackground()));
                }, () -> fail("Missing diagram or node's background has not been resetted to being blue or node's inside label is unexpectedly bold again."));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(setLabelBold)
                .consumeNextWith(updatedAfterBoldDiagramContentConsumer)
                .then(setNodeBackgroundRed)
                .consumeNextWith(updatedAfterRedBackgroundDiagramContentConsumer)
                .then(resetLabelBold)
                .consumeNextWith(updatedAfterResetBoldDiagramContentConsumer)
                .then(resetNodeBackgroundRed)
                .consumeNextWith(updatedAfterResetRedBackgroundDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Runnable getResetNodeBackgroundRedRunnable(AtomicReference<String> diagramId, AtomicReference<String> nodeId) {
        return () -> {
            var input = new ResetNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    nodeId.get(),
                    List.of("background")
            );

            this.resetNodeAppearanceMutationRunner.run(input);
        };
    }

    private Runnable getSetLabelBoldRunnable(AtomicReference<String> diagramId, AtomicReference<String> nodeId, AtomicReference<String> insideLabelId) {
        return () -> {
            var appearanceInput = new RectangularNodeAppearanceInput(
                    null,
                    new LabelAppearanceInput(insideLabelId.get(), true)
            );

            var input = new EditRectangularNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    nodeId.get(),
                    appearanceInput
            );

            this.editRectangularNodeAppearanceMutationRunner.run(input);
        };
    }

    private Runnable getSetNodeBackgroundRedRunnable(AtomicReference<String> diagramId, AtomicReference<String> nodeId) {
        return () -> {
            var appearanceInput = new RectangularNodeAppearanceInput(
                    "#ff0000",
                    null
            );

            var input = new EditRectangularNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    nodeId.get(),
                    appearanceInput
            );

            this.editRectangularNodeAppearanceMutationRunner.run(input);
        };
    }

    private Runnable getResetLabelBoldRunnable(AtomicReference<String> diagramId, AtomicReference<String> nodeId, AtomicReference<String> labelId) {
        return () -> {
            var input = new ResetLabelAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    nodeId.get(),
                    labelId.get(),
                    List.of("bold")
            );

            this.resetLabelAppearanceMutationRunner.run(input);
        };
    }
}
