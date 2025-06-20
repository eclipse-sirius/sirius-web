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
import org.eclipse.sirius.components.collaborative.diagrams.dto.RectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ResetNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditRectangularNodeAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ResetNodeAppearanceMutationRunner;
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
    @DisplayName("Given a diagram, when we edit its appearance and reset changes, then the diagram is properly updated")
    public void givenDiagramWhenWeEditItsAppearanceAndResetChangesThenTheDiagramIsProperlyUpdated() {
        var flux = givenDiagramSubscription();
        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    assertThat(diagram.getNodes())
                            .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                            .hasSize(1)
                            .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle rectangularNodeStyle && "black".equals(rectangularNodeStyle.getBackground()));

                    var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
                    siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
                }, () -> fail("Missing diagram or invalid state for tested node."));


        Runnable setNodeBackgroundRed = () -> {
            var appearanceInput = new RectangularNodeAppearanceInput("red");

            var input = new EditRectangularNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    appearanceInput
            );

            this.editRectangularNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterRedBackgroundDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes())
                            .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                            .hasSize(1)
                            .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle rectangularNodeStyle && "red".equals(rectangularNodeStyle.getBackground()));
                }, () -> fail("Missing diagram or node's background has not been set to red."));

        Runnable resetNodeBackgroundRed = () -> {
            var input = new ResetNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    siriusWebApplicationNodeId.get(),
                    List.of("BACKGROUND")
            );

            this.resetNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterResetRedBackgroundDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes())
                            .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                            .hasSize(1)
                            .allMatch(node -> node.getStyle() instanceof RectangularNodeStyle rectangularNodeStyle && "black".equals(rectangularNodeStyle.getBackground()));
                }, () -> fail("Missing diagram or node's background has not been resetted to being black."));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(setNodeBackgroundRed)
                .consumeNextWith(updatedAfterRedBackgroundDiagramContentConsumer)
                .then(resetNodeBackgroundRed)
                .consumeNextWith(updatedAfterResetRedBackgroundDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
