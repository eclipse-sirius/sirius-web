/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.deck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.deck.dto.DeckRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.deck.dto.input.CreateDeckCardInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.DeleteDeckCardInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.deck.PapayaDeckDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDeckSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.deck.CreateDeckCardMutationRunner;
import org.eclipse.sirius.web.tests.services.deck.DeleteDeckCardMutationRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the deck representation with a papaya model.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class PapayaDeckControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDeckSubscription givenCreatedDeckSubscription;

    @Autowired
    private PapayaDeckDescriptionProvider papayaDeckDescriptionProvider;

    @Autowired
    private CreateDeckCardMutationRunner createDeckCardMutationRunner;

    @Autowired
    private DeleteDeckCardMutationRunner deleteDeckCardMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToDeck() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.papayaDeckDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.SIRIUS_WEB_PLANNING_PROJECT_OBJECT.toString(),
                "Deck"
        );
        return this.givenCreatedDeckSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a deck representation, when we subscribe to its event, then the representation data are received")
    public void givenDeckRepresentationWhenWeSubscribeToItsEventThenTheRepresentationDataAreReceived() {
        var flux = this.givenSubscriptionToDeck();

        Consumer<Object> initialDeckContentConsumer = payload -> Optional.of(payload)
                .filter(DeckRefreshedEventPayload.class::isInstance)
                .map(DeckRefreshedEventPayload.class::cast)
                .map(DeckRefreshedEventPayload::deck)
                .ifPresentOrElse(deck -> {
                    assertThat(deck).isNotNull();
                }, () -> fail("Missing deck"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDeckContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a deck representation, when we create a new card, then the representation data are updated")
    public void givenDeckRepresentationWhenWeCreateNewCardThenTheRepresentationDataAreUpdated() {
        var flux = this.givenSubscriptionToDeck();

        var deckId = new AtomicReference<String>();
        var laneId = new AtomicReference<String>();

        Consumer<Object> initialDeckContentConsumer = payload -> Optional.of(payload)
                .filter(DeckRefreshedEventPayload.class::isInstance)
                .map(DeckRefreshedEventPayload.class::cast)
                .map(DeckRefreshedEventPayload::deck)
                .ifPresentOrElse(deck -> {
                    deckId.set(deck.getId());
                    assertThat(deck.lanes()).isNotEmpty();

                    var lane = deck.lanes().get(0);
                    laneId.set(lane.id());
                    assertThat(lane.cards()).hasSize(5);
                }, () -> fail("Missing deck"));

        Runnable createNewCard = () -> {
            var createDeckCardInput = new CreateDeckCardInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    deckId.get(),
                    laneId.get(),
                    "title",
                    "label",
                    "description"
            );
            var result = this.createDeckCardMutationRunner.run(createDeckCardInput);
            String typename = JsonPath.read(result.data(), "$.data.createDeckCard.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDeckContentConsumer = payload -> Optional.of(payload)
                .filter(DeckRefreshedEventPayload.class::isInstance)
                .map(DeckRefreshedEventPayload.class::cast)
                .map(DeckRefreshedEventPayload::deck)
                .ifPresentOrElse(deck -> {
                    assertThat(deck.lanes()).isNotEmpty();
                    assertThat(deck.lanes().get(0).cards()).hasSize(6);
                }, () -> fail("Missing deck"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDeckContentConsumer)
                .then(createNewCard)
                .consumeNextWith(updatedDeckContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a deck representation, when we delete an existing card, then the representation data are updated")
    public void givenDeckRepresentationWhenWeDeleteAnExistingCardThenTheRepresentationDataAreUpdated() {
        var flux = this.givenSubscriptionToDeck();

        var deckId = new AtomicReference<String>();
        var laneId = new AtomicReference<String>();
        var cardId = new AtomicReference<String>();

        Consumer<Object> initialDeckContentConsumer = payload -> Optional.of(payload)
                .filter(DeckRefreshedEventPayload.class::isInstance)
                .map(DeckRefreshedEventPayload.class::cast)
                .map(DeckRefreshedEventPayload::deck)
                .ifPresentOrElse(deck -> {
                    deckId.set(deck.getId());
                    assertThat(deck.lanes()).isNotEmpty();

                    var lane = deck.lanes().get(0);
                    laneId.set(lane.id());
                    assertThat(lane.cards()).hasSize(5);

                    cardId.set(lane.cards().get(0).id());
                }, () -> fail("Missing deck"));

        Runnable deleteCard = () -> {
            var deleteDeckCardInput = new DeleteDeckCardInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    deckId.get(),
                    cardId.get()
            );
            var result = this.deleteDeckCardMutationRunner.run(deleteDeckCardInput);
            String typename = JsonPath.read(result.data(), "$.data.deleteDeckCard.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDeckContentConsumer = payload -> Optional.of(payload)
                .filter(DeckRefreshedEventPayload.class::isInstance)
                .map(DeckRefreshedEventPayload.class::cast)
                .map(DeckRefreshedEventPayload::deck)
                .ifPresentOrElse(deck -> {
                    assertThat(deck.lanes()).isNotEmpty();
                    assertThat(deck.lanes().get(0).cards()).hasSize(4);
                }, () -> fail("Missing deck"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDeckContentConsumer)
                .then(deleteCard)
                .consumeNextWith(updatedDeckContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
