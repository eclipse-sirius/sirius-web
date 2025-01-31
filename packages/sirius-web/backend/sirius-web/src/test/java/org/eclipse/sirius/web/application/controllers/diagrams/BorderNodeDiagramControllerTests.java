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
import static org.assertj.core.api.Assertions.fail;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.components.BorderNodePosition;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.BorderNodeDiagramDescriptionProvider;
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
 * Integration tests for diagram with border nodes.
 *
 * @author lfasani
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class BorderNodeDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private BorderNodeDiagramDescriptionProvider borderNodeDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToBorderNodeDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.borderNodeDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "BorderNodeDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with border nodes, when it is opened, then border nodes should be properly positioned")
    public void givenDiagramWithBorderNodesWhenItIsOpenedThenBorderNodesShouldNotAppear() {
        var flux = this.givenSubscriptionToBorderNodeDiagram();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getNodes()).hasSize(1);
                    assertThat(diagram.getNodes().get(0).getBorderNodes()).hasSize(5);
                    assertThat(diagram.getNodes().get(0).getBorderNodes().get(0))
                            .hasFieldOrPropertyWithValue("borderNode", true)
                            .hasFieldOrPropertyWithValue("initialBorderNodePosition", BorderNodePosition.SOUTH);
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
