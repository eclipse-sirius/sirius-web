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

import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.GetLayoutConfigurationExecutor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.FlowIdentifier;
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
 * Integration tests of the layout configuration controllers.
 *
 * @author ocailleau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class LayoutConfigurationControllerTests extends AbstractIntegrationTests {

    public static final String REPRESENTATION_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38";

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private GetLayoutConfigurationExecutor getLayoutConfigurationExecutor;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a domain diagram, when the layout configurations are requested, then the default configurations are retrieved")
    public void givenDomainDiagramWhenLayoutConfigurationsRequestedThenConfigurationsAreRetrieved() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                FlowIdentifier.FLOW_EDITING_CONTEXT_ID,
                REPRESENTATION_DESCRIPTION_ID,
                FlowIdentifier.FLOW_ROOT_SYSTEM_OBJECT,
                "Topography"
        );
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> diagramId.set(diagram.getId()));

        Runnable requestLayoutConfigurations = () -> this.getLayoutConfigurationExecutor.execute(FlowIdentifier.FLOW_EDITING_CONTEXT_ID, diagramId.get())
                .hasConfigurationIds(ids -> assertThat(ids).containsExactly("elk-layered", "elk-rect-packing", "elk-spore-overlap"))
                .hasConfigurationLabels(labels -> assertThat(labels).containsExactly("Flow Layout", "Compact Layout", "Orthogonal Layout"))
                .hasLayoutAlgorithms(algorithms -> assertThat(algorithms).containsExactly("layered", "rectpacking", "sporeOverlap"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestLayoutConfigurations)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
