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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.web.services.diagrams.DiagramConnectorPaletteCustomizer.CUSTOMIZED_DIAGRAM_CONNECTOR_TOOL_LABEL;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.ConnectorPaletteQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.EdgeDiagramDescriptionProvider;
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
 * Integration tests of the customization of the connector palette.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ConnectorPaletteCustomizationControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EdgeDiagramDescriptionProvider edgeDiagramDescriptionProvider;

    @Autowired
    private ConnectorPaletteQueryRunner connectorPaletteQueryRunner;

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
    @DisplayName("Given a customizer for a diagram connector palette, when the palette is requested for the diagram, then the customization is available")
    public void givenCustomizerForDiagramConnectorPaletteWhenThePaletteIsRequestedForTheDiagramThenTheCustomizationIsAvailable() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();

        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();
        var siriusWebInfrastructureNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());

            var siriusWebInfrastructureNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode();
            siriusWebInfrastructureNodeId.set(siriusWebInfrastructureNode.getId());
        });

        Runnable requestConnectorPalette = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get(),
                    "sourceDiagramElementId", siriusWebInfrastructureNodeId.get(),
                    "targetDiagramElementId", siriusWebApplicationNodeId.get()
            );
            var connectorToolsResult = this.connectorPaletteQueryRunner.run(variables);
            List<String> quickAccessToolLabels = JsonPath.read(connectorToolsResult.data(), "$.data.viewer.editingContext.representation.description.connectorPalette.quickAccessTools[*].label");
            assertThat(quickAccessToolLabels).contains(CUSTOMIZED_DIAGRAM_CONNECTOR_TOOL_LABEL);
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestConnectorPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
