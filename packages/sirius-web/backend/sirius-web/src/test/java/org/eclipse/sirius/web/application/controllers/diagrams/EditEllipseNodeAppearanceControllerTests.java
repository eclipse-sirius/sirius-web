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

import org.eclipse.sirius.components.collaborative.diagrams.dto.ResetNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditEllipseNodeAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.ResetNodeAppearanceMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.diagram.EllipseNodeStyle;
import org.eclipse.sirius.web.application.diagram.dto.EditEllipseNodeAppearanceInput;
import org.eclipse.sirius.web.application.diagram.dto.EllipseNodeAppearanceInput;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.CustomNodesDiagramDescriptionProvider;
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
 * Tests for ellipse node appearance edition.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class EditEllipseNodeAppearanceControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EditEllipseNodeAppearanceMutationRunner editEllipseNodeAppearanceMutationRunner;

    @Autowired
    private ResetNodeAppearanceMutationRunner resetNodeAppearanceMutationRunner;

    @Autowired
    private CustomNodesDiagramDescriptionProvider diagramDescriptionProvider;

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
                "EditEllipseNodeAppearanceDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
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
                    .allMatch(node -> node.getStyle() instanceof EllipseNodeStyle)
                    .extracting(node -> (EllipseNodeStyle) node.getStyle())
                    .allMatch(ellipseNodeStyle -> "transparent".equals(ellipseNodeStyle.getBackground()))
                    .allMatch(ellipseNodeStyle -> "black".equals(ellipseNodeStyle.getBorderColor()))
                    .allMatch(ellipseNodeStyle -> ellipseNodeStyle.getBorderSize() == 1)
                    .allMatch(ellipseNodeStyle -> LineStyle.Solid.equals(ellipseNodeStyle.getBorderStyle()));

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
        });


        Runnable setNodeCustomAppearance = () -> {
            var appearanceInput = new EllipseNodeAppearanceInput("red", "blue", 5, LineStyle.Dash);

            var input = new EditEllipseNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(siriusWebApplicationNodeId.get()),
                    appearanceInput
            );

            this.editEllipseNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterCustomAppearanceDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof EllipseNodeStyle)
                    .extracting(node -> (EllipseNodeStyle) node.getStyle())
                    .allMatch(ellipseNodeStyle -> "red".equals(ellipseNodeStyle.getBackground()))
                    .allMatch(ellipseNodeStyle -> "blue".equals(ellipseNodeStyle.getBorderColor()))
                    .allMatch(ellipseNodeStyle -> ellipseNodeStyle.getBorderSize() == 5)
                    .allMatch(ellipseNodeStyle -> LineStyle.Dash.equals(ellipseNodeStyle.getBorderStyle()));
        });

        Runnable resetNodeCustomAppearance = () -> {
            var input = new ResetNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(siriusWebApplicationNodeId.get()),
                    List.of("BACKGROUND", "BORDER_COLOR", "BORDER_RADIUS", "BORDER_SIZE", "BORDER_STYLE")
            );

            this.resetNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterResetCustomAppearanceDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof EllipseNodeStyle)
                    .extracting(node -> (EllipseNodeStyle) node.getStyle())
                    .allMatch(ellipseNodeStyle -> "transparent".equals(ellipseNodeStyle.getBackground()))
                    .allMatch(ellipseNodeStyle -> "black".equals(ellipseNodeStyle.getBorderColor()))
                    .allMatch(ellipseNodeStyle -> ellipseNodeStyle.getBorderSize() == 1)
                    .allMatch(ellipseNodeStyle -> LineStyle.Solid.equals(ellipseNodeStyle.getBorderStyle()));
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
                    .allMatch(node -> node.getStyle() instanceof EllipseNodeStyle)
                    .extracting(node -> (EllipseNodeStyle) node.getStyle())
                    .allMatch(ellipseNodeStyle -> "transparent".equals(ellipseNodeStyle.getBackground()))
                    .allMatch(ellipseNodeStyle -> ellipseNodeStyle.getBorderSize() == 1);

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());
        });

        Runnable setNodeCustomAppearance = () -> {
            var appearanceInput = new EllipseNodeAppearanceInput("red", null, 5, null);

            var input = new EditEllipseNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(siriusWebApplicationNodeId.get()),
                    appearanceInput
            );

            this.editEllipseNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterCustomAppearanceDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof EllipseNodeStyle)
                    .extracting(node -> (EllipseNodeStyle) node.getStyle())
                    .allMatch(ellipseNodeStyle -> "red".equals(ellipseNodeStyle.getBackground()))
                    .allMatch(ellipseNodeStyle -> ellipseNodeStyle.getBorderSize() == 5);
        });

        Runnable resetNodeCustomAppearance = () -> {
            var input = new ResetNodeAppearanceInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(siriusWebApplicationNodeId.get()),
                    List.of("BORDER_SIZE")
            );

            this.resetNodeAppearanceMutationRunner.run(input);
        };

        Consumer<Object> updatedAfterResetCustomAppearanceDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .filteredOn(node -> node.getInsideLabel() != null && node.getInsideLabel().getText().equals("sirius-web-application"))
                    .hasSize(1)
                    .allMatch(node -> node.getStyle() instanceof EllipseNodeStyle)
                    .extracting(node -> (EllipseNodeStyle) node.getStyle())
                    .allMatch(ellipseNodeStyle -> "red".equals(ellipseNodeStyle.getBackground()))
                    .allMatch(ellipseNodeStyle -> ellipseNodeStyle.getBorderSize() == 1);
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

}
