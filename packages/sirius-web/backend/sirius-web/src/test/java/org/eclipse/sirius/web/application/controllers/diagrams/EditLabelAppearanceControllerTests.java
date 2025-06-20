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
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ResetLabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditLabelAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ResetLabelAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ExpandCollapseDiagramDescriptionProvider;
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
 * Tests for label appearance edition.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class EditLabelAppearanceControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EditLabelAppearanceMutationRunner editLabelAppearanceMutationRunner;

    @Autowired
    private ResetLabelAppearanceMutationRunner resetLabelAppearanceMutationRunner;

    @Autowired
    private ExpandCollapseDiagramDescriptionProvider diagramDescriptionProvider;

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
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a label, when we edit all its appearance and reset changes, then the label is properly updated")
    public void givenDiagramWithLabelWhenWeEditAllItsAppearanceAndResetChangesThenTheLabelIsProperlyUpdated() {
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
                    .allMatch(node -> LineStyle.Solid.equals(node.getInsideLabel().getStyle().getBorderStyle()));

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
            siriusWebApplicationNodeInsideLabelId.set(siriusWebApplicationNode.getInsideLabel().getId());
        });

        Runnable setLabelBold = () -> {
            var appearanceInput = new LabelAppearanceInput(10, true, true, true, true, "blue", 5, 2, LineStyle.Dash, "red", "green");

            var input = new EditLabelAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    siriusWebApplicationNodeInsideLabelId.get(),
                    appearanceInput
            );

            this.editLabelAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterBoldDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> assertThat(diagram.getNodes())
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
                .allMatch(node -> LineStyle.Dash.equals(node.getInsideLabel().getStyle().getBorderStyle())));

        Runnable resetLabelBold = () -> {
            var input = new ResetLabelAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    siriusWebApplicationNodeInsideLabelId.get(),
                    List.of("FONT_SIZE", "ITALIC", "BOLD", "UNDERLINE", "STRIKE_THROUGH", "BORDER_COLOR", "BORDER_RADIUS", "BORDER_SIZE", "BORDER_STYLE", "COLOR", "BACKGROUND")
            );

            this.resetLabelAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterResetBoldDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> assertThat(diagram.getNodes())
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
                .allMatch(node -> LineStyle.Solid.equals(node.getInsideLabel().getStyle().getBorderStyle())));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(setLabelBold)
                .consumeNextWith(updatedAfterBoldDiagramContentConsumer)
                .then(resetLabelBold)
                .consumeNextWith(updatedAfterResetBoldDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a label, when we edit two of its appearance and reset  one change, then the label is properly updated")
    public void givenDiagramWithLabelWhenWeEditTwoOfItsAppearanceAndResetOneChangeThenTheLabelIsProperlyUpdated() {
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
                    .allMatch(node -> !node.getInsideLabel().getStyle().isItalic());

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
            siriusWebApplicationNodeInsideLabelId.set(siriusWebApplicationNode.getInsideLabel().getId());
        });

        Runnable setLabelBold = () -> {
            var appearanceInput = new LabelAppearanceInput(10, true, null, null, null, null, null, null, null, null, null);

            var input = new EditLabelAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    siriusWebApplicationNodeInsideLabelId.get(),
                    appearanceInput
            );

            this.editLabelAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterBoldDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> assertThat(diagram.getNodes())
                .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                .hasSize(1)
                .allMatch(node -> node.getInsideLabel().getStyle().getFontSize() == 10)
                .allMatch(node -> node.getInsideLabel().getStyle().isItalic()));

        Runnable resetLabelBold = () -> {
            var input = new ResetLabelAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    siriusWebApplicationNodeInsideLabelId.get(),
                    List.of("FONT_SIZE")
            );

            this.resetLabelAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterResetBoldDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> assertThat(diagram.getNodes())
                .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                .hasSize(1)
                .allMatch(node -> node.getInsideLabel().getStyle().getFontSize() == 14)
                .allMatch(node -> node.getInsideLabel().getStyle().isItalic()));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(setLabelBold)
                .consumeNextWith(updatedAfterBoldDiagramContentConsumer)
                .then(resetLabelBold)
                .consumeNextWith(updatedAfterResetBoldDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
