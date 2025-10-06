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
package org.eclipse.sirius.web.application.controllers.diagrams.undo;

import com.jayway.jsonpath.JsonPath;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EditRectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.RectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.tests.graphql.DeleteFromDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditRectangularNodeAppearanceMutationRunner;
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

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

/**
 * Tests for undo redo rectangular node appearance edition.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class UndoEditRectangularNodeAppearanceControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EditRectangularNodeAppearanceMutationRunner editRectangularNodeAppearanceMutationRunner;

    @Autowired
    private DeleteFromDiagramMutationRunner deleteFromDiagramMutationRunner;

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

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a node with an already customised appearance, when we delete the node and undo the change, the customized property is kept")
    public void givenDiagramWithAnAlreadyCustomisedAppearanceWhenWeDeleteTheNodeAndUndoTheChangeThenCustomizedPropertyIsKept() {
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

        var mutationInputId = UUID.randomUUID();
        Runnable deleteNode = () -> {
            var input = new DeleteFromDiagramInput(mutationInputId, PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), List.of(siriusWebApplicationNodeId.get()), List.of());
            var result = this.deleteFromDiagramMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.deleteFromDiagram.__typename");
            assertThat(typename).isEqualTo(DeleteFromDiagramSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedAfterDeleteDiagramElementConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .allMatch(node -> !node.getInsideLabel().getText().equals("sirius-web-application"));
        });

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

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(setNodeCustomAppearance)
                .consumeNextWith(updatedAfterCustomAppearanceDiagramContentConsumer)
                .then(deleteNode)
                .consumeNextWith(updatedAfterDeleteDiagramElementConsumer)
                .then(undoChanges)
                .consumeNextWith(updatedAfterCustomAppearanceDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
