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

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditRectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.RectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ResetNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditRectangularNodeAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ResetNodeAppearanceMutationRunner;
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
    private ResetNodeAppearanceMutationRunner resetNodeAppearanceMutationRunner;

    @Autowired
    private UndoMutationRunner undoMutationRunner;

    @Autowired
    private RedoMutationRunner redoMutationRunner;

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
                "EditRectangularNodeAppearanceDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram, when we edit all its appearance and reset changes, then the diagram is properly updated")
    public void givenDiagramWhenWeEditAllItsAppearanceAndResetChangesThenTheDiagramIsProperlyUpdated() {
        var flux = this.givenDiagramSubscription();
        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle)
                    .extracting(node -> (RectangularNodeStyle) node.getStyle())
                    .allMatch(rectangularNodeStyle -> "black".equals(rectangularNodeStyle.getBackground()))
                    .allMatch(rectangularNodeStyle -> "".equals(rectangularNodeStyle.getBorderColor()))
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderRadius() == 3)
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderSize() == 1)
                    .allMatch(rectangularNodeStyle -> LineStyle.Solid.equals(rectangularNodeStyle.getBorderStyle()));

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
        });


        Runnable setNodeCustomAppearance = () -> {
            var appearanceInput = new RectangularNodeAppearanceInput("red", "blue", 5, 2, LineStyle.Dash);

            var input = new EditRectangularNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    appearanceInput
            );

            this.editRectangularNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterCustomAppearanceDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle)
                    .extracting(node -> (RectangularNodeStyle) node.getStyle())
                    .allMatch(rectangularNodeStyle -> "red".equals(rectangularNodeStyle.getBackground()))
                    .allMatch(rectangularNodeStyle -> "blue".equals(rectangularNodeStyle.getBorderColor()))
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderRadius() == 5)
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderSize() == 2)
                    .allMatch(rectangularNodeStyle -> LineStyle.Dash.equals(rectangularNodeStyle.getBorderStyle()));
        });

        Runnable resetNodeCustomAppearance = () -> {
            var input = new ResetNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    List.of("BACKGROUND", "BORDER_COLOR", "BORDER_RADIUS", "BORDER_SIZE", "BORDER_STYLE")
            );

            this.resetNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterResetCustomAppearanceDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle)
                    .extracting(node -> (RectangularNodeStyle) node.getStyle())
                    .allMatch(rectangularNodeStyle -> "black".equals(rectangularNodeStyle.getBackground()))
                    .allMatch(rectangularNodeStyle -> "".equals(rectangularNodeStyle.getBorderColor()))
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderRadius() == 3)
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderSize() == 1)
                    .allMatch(rectangularNodeStyle -> LineStyle.Solid.equals(rectangularNodeStyle.getBorderStyle()));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(setNodeCustomAppearance)
                .consumeNextWith(updatedAfterCustomAppearanceDiagramContentConsumer)
                .then(resetNodeCustomAppearance)
                .consumeNextWith(updatedAfterResetCustomAppearanceDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram, when we edit two of its appearance and reset one change, then only one property is reset")
    public void givenDiagramWhenWeEditTwoOfItsAppearanceAndResetOneChangeThenOnlyOnePropertyIsReset() {
        var flux = this.givenDiagramSubscription();
        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle)
                    .extracting(node -> (RectangularNodeStyle) node.getStyle())
                    .allMatch(rectangularNodeStyle -> "black".equals(rectangularNodeStyle.getBackground()))
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderRadius() == 3);

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
        });


        Runnable setNodeCustomAppearance = () -> {
            var appearanceInput = new RectangularNodeAppearanceInput("red", null, 5, null, null);

            var input = new EditRectangularNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    appearanceInput
            );

            this.editRectangularNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterCustomAppearanceDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle)
                    .extracting(node -> (RectangularNodeStyle) node.getStyle())
                    .allMatch(rectangularNodeStyle -> "red".equals(rectangularNodeStyle.getBackground()))
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderRadius() == 5);
        });

        Runnable resetNodeCustomAppearance = () -> {
            var input = new ResetNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    List.of("BORDER_RADIUS")
            );

            this.resetNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterResetCustomAppearanceDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle)
                    .extracting(node -> (RectangularNodeStyle) node.getStyle())
                    .allMatch(rectangularNodeStyle -> "red".equals(rectangularNodeStyle.getBackground()))
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderRadius() == 3);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(setNodeCustomAppearance)
                .consumeNextWith(updatedAfterCustomAppearanceDiagramContentConsumer)
                .then(resetNodeCustomAppearance)
                .consumeNextWith(updatedAfterResetCustomAppearanceDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram, when we edit its appearance, undo and redo the change, then the diagram is properly updated")
    public void givenDiagramWhenWeEditItsAppearanceAndUndoRedoChangesThenTheDiagramIsProperlyUpdated() {
        var flux = this.givenDiagramSubscription();
        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle)
                    .extracting(node -> (RectangularNodeStyle) node.getStyle())
                    .allMatch(rectangularNodeStyle -> "black".equals(rectangularNodeStyle.getBackground()))
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderRadius() == 3);

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
        });

        var mutationInputId = UUID.randomUUID();

        Runnable setNodeCustomAppearance = () -> {
            var appearanceInput = new RectangularNodeAppearanceInput("red", null, 5, null, null);

            var input = new EditRectangularNodeAppearanceInput(
                    mutationInputId,
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    appearanceInput
            );

            this.editRectangularNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterCustomAppearanceDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle)
                    .extracting(node -> (RectangularNodeStyle) node.getStyle())
                    .allMatch(rectangularNodeStyle -> "red".equals(rectangularNodeStyle.getBackground()))
                    .allMatch(rectangularNodeStyle -> rectangularNodeStyle.getBorderRadius() == 5);
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
                .then(setNodeCustomAppearance)
                .consumeNextWith(updatedAfterCustomAppearanceDiagramContentConsumer)
                .then(undoChanges)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(redoChanges)
                .consumeNextWith(updatedAfterCustomAppearanceDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
