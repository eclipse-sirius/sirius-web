/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.PaletteQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.studio.services.representations.api.IDomainDiagramDescriptionProvider;
import org.eclipse.sirius.web.data.FlowIdentifier;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the palette controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class PaletteControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private IDomainDiagramDescriptionProvider domainDiagramDescriptionProvider;

    @Autowired
    private PaletteQueryRunner paletteQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a domain diagram, when the palette is requested for the diagram, then the relevant tools are available")
    public void givenDomainDiagramOnStudioWhenItIsOpenedThenEntitiesAreVisible() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, this.domainDiagramDescriptionProvider.getDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> diagramId.set(diagram.getId()));

        Runnable requestDiagramPalette = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "representationId", diagramId.get(),
                    "diagramElementIds", List.of(diagramId.get())
            );
            var result = this.paletteQueryRunner.run(variables);

            List<String> topLevelToolsLabel = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].label");
            assertThat(topLevelToolsLabel)
                    .isNotEmpty()
                    .anySatisfy(toolLabel -> assertThat(toolLabel).isEqualTo("New entity"));
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestDiagramPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a domain diagram, when the palette is requested for an edge element, then the relevant quick access tools are available")
    public void givenDomainDiagramWhenPaletteIsRequestedOnEdgeElementThenQuickAccessToolsAreAvailable() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, this.domainDiagramDescriptionProvider.getDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();

        var diagramId = new AtomicReference<String>();
        var edgeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            edgeId.set(diagram.getEdges().get(0).getId());
        });

        Runnable requestDiagramPalette = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "representationId", diagramId.get(),
                    "diagramElementIds", List.of(edgeId.get())
            );
            var result = this.paletteQueryRunner.run(variables);

            List<String> quickAccessToolsLabel = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.quickAccessTools[*].label");
            assertThat(quickAccessToolsLabel)
                    .isNotEmpty()
                    .anySatisfy(toolLabel -> assertThat(toolLabel).isEqualTo("Edit"))
                    .anySatisfy(toolLabel -> assertThat(toolLabel).isEqualTo("Delete from model"));
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestDiagramPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with an edge with custom handle, when the palette is requested for this edge element, then the reset handles position quick access tool is available")
    public void givenDiagramWithEdgeWithCustomHandleWhenPaletteRequestedThenResetHandlesPositionToolAvailable() {
        Map<String, Object> variables = Map.of(
                "editingContextId", FlowIdentifier.FLOW_EDITING_CONTEXT_ID,
                "representationId", FlowIdentifier.FLOW_DIAGRAM_REPRESENTATION_ID,
                "diagramElementIds", List.of(FlowIdentifier.FLOW_EDGE_DATA_FLOW_1)
        );
        var result = this.paletteQueryRunner.run(variables);

        List<String> quickAccessToolsIds = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.quickAccessTools[*].id");
        assertThat(quickAccessToolsIds)
                .isNotEmpty()
                .anySatisfy(toolId -> assertThat(toolId).isEqualTo("reset-handles-position"));

    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with an edge without custom handle, when the palette is requested for this edge element, then the reset handles position quick access tool is not available")
    public void givenDiagramWithEdgeWithoutCustomHandleWhenPaletteRequestedThenResetHandlesPositionToolNotAvailable() {
        Map<String, Object> variables = Map.of(
                "editingContextId", FlowIdentifier.FLOW_EDITING_CONTEXT_ID,
                "representationId", FlowIdentifier.FLOW_DIAGRAM_REPRESENTATION_ID,
                "diagramElementIds", List.of(FlowIdentifier.FLOW_EDGE_DATA_FLOW_2)
        );
        var result = this.paletteQueryRunner.run(variables);

        List<String> quickAccessToolsIds = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.quickAccessTools[*].id");
        assertThat(quickAccessToolsIds)
                .isNotEmpty()
                .noneSatisfy(toolId -> assertThat(toolId).isEqualTo("reset-handles-position"));
    }
}
