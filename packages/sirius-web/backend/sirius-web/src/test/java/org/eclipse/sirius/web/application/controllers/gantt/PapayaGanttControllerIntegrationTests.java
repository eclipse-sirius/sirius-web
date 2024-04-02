/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.controllers.gantt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.GanttRefreshedEventPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaSampleIdentifiers;
import org.eclipse.sirius.web.services.api.IGivenCreatedGanttSubscription;
import org.eclipse.sirius.web.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.services.gantt.PapayaGanttDescriptionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the gantt representation with a papaya model.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class PapayaGanttControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedGanttSubscription givenCreatedGanttSubscription;

    @Autowired
    private PapayaGanttDescriptionProvider papayaGanttDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<GanttRefreshedEventPayload> givenSubscriptionToGantt() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaSampleIdentifiers.PAPAYA_PROJECT.toString(),
                this.papayaGanttDescriptionProvider.getRepresentationDescriptionId(),
                PapayaSampleIdentifiers.PROJECT_OBJECT.toString(),
                "Deck"
        );
        return this.givenCreatedGanttSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a gantt representation, when we subscribe to its event, then the representation data are received")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenGanttRepresentationWhenWeSubscribeToItsEventThenTheRepresentationDataAreReceived() {
        var flux = this.givenSubscriptionToGantt();

        Consumer<GanttRefreshedEventPayload> initialGanttContentConsumer = payload -> Optional.of(payload)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(deck -> {
                    assertThat(deck).isNotNull();
                }, () -> fail("Missing gantt"));

        StepVerifier.create(flux)
                .consumeNextWith(initialGanttContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
