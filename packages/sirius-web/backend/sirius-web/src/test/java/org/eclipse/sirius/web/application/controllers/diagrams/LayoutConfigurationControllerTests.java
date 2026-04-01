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

import com.jayway.jsonpath.JsonPath;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.GetLayoutConfigurationQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.diagrams.LayoutConfigurationDiagramDescriptionProvider;
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

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;
/**
 * Integration tests of the layout configuration controllers.
 *
 * @author ocailleau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class LayoutConfigurationControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private LayoutConfigurationDiagramDescriptionProvider diagramDescriptionProvider;

    @Autowired
    private GetLayoutConfigurationQueryRunner getLayoutConfigurationsQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a domain diagram, when the layout configurations are requested, then the default configurations are retrieved")
    public void givenDomainDiagramWhenLayoutConfigurationsRequestedThenConfigurationsAreRetrieved() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, this.diagramDescriptionProvider.getRepresentationDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> diagramId.set(diagram.getId()));

        Runnable requestLayoutConfigurations = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "diagramId", diagramId.get()
            );

            var result = this.getLayoutConfigurationsQueryRunner.run(variables);
            System.out.println(result);
            List<String> configIds = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.layoutConfigurations[*].id");
            List<String> configLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.layoutConfigurations[*].label");
            List<String> firstLayoutAlgorithms = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.layoutConfigurations[*].layoutOptions[?(@.key=='elk.algorithm')].value");

            assertThat(configIds).isNotEmpty().contains("elk-layered", "elk-rect-packing");
            assertThat(configLabels).isNotEmpty().contains("Flow Layout", "Compact Layout");
            assertThat(firstLayoutAlgorithms).isNotEmpty().contains("layered", "rectpacking");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestLayoutConfigurations)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}