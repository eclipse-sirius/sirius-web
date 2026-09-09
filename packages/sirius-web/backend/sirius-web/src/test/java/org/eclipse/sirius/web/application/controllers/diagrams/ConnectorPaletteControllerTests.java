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
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ConnectorPaletteDiagramDescriptionProvider;
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
 * Integration tests of the connector palette controller.
 *
 * @author gdaniel
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ConnectorPaletteControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private ConnectorPaletteDiagramDescriptionProvider connectorPaletteDiagramDescriptionProvider;

    @Autowired
    private ConnectorPaletteQueryRunner connectorPaletteQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with entities, when the connector palette is requested between two entities, then the edge tool is available")
    public void givenDiagramWithEntitiesWhenConnectorPaletteIsRequestedBetweenTwoEntitiesThenEdgeToolIsAvailable() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, this.connectorPaletteDiagramDescriptionProvider.getRepresentationDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();

        var diagramId = new AtomicReference<String>();
        var sourceNodeId = new AtomicReference<String>();
        var targetNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            sourceNodeId.set(new DiagramNavigator(diagram).nodeWithLabel("Root").getNode().getId());
            targetNodeId.set(new DiagramNavigator(diagram).nodeWithLabel("NamedElement").getNode().getId());
        });

        Runnable requestConnectorPalette = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "representationId", diagramId.get(),
                    "sourceDiagramElementId", sourceNodeId.get(),
                    "targetDiagramElementId", targetNodeId.get()
            );
            var result = this.connectorPaletteQueryRunner.run(variables);

            List<String> toolLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.connectorPalette.paletteEntries[*].label");
            assertThat(toolLabels).contains("New edge");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestConnectorPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
