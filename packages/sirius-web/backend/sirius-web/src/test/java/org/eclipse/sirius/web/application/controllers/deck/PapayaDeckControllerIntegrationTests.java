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
package org.eclipse.sirius.web.application.controllers.deck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.deck.dto.DeckRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.deck.dto.input.ChangeCardsVisibilityInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.ChangeLaneCollapsedStateInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.CreateDeckCardInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.DeleteDeckCardInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.deck.PapayaDeckDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDeckSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.deck.ChangeCardsVisibilityExecutor;
import org.eclipse.sirius.web.tests.services.deck.ChangeLaneCollapsedStateExecutor;
import org.eclipse.sirius.web.tests.services.deck.CreateDeckCardExecutor;
import org.eclipse.sirius.web.tests.services.deck.DeleteDeckCardExecutor;
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
    private CreateDeckCardExecutor createDeckCardExecutor;

    @Autowired
    private DeleteDeckCardExecutor deleteDeckCardExecutor;

    @Autowired
    private ChangeCardsVisibilityExecutor changeCardsVisibilityExecutor;

    @Autowired
    private ChangeLaneCollapsedStateExecutor changeLaneCollapsedStateExecutor;

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
            this.createDeckCardExecutor.execute(createDeckCardInput).isSuccess();
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
            this.deleteDeckCardExecutor.execute(deleteDeckCardInput).isSuccess();
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

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a deck representation, when we change a card visibility, then the representation data are updated")
    public void givenDeckRepresentationWhenWeChangeCardVisibilityThenTheRepresentationDataAreUpdated() {
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
                    assertThat(lane.cards().get(0).visible()).isTrue();

                    cardId.set(lane.cards().get(0).id());
                }, () -> fail("Missing deck"));

        Runnable changeCardsVisibility = () -> {
            var changeCardsVisibilityInput = new ChangeCardsVisibilityInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    deckId.get(),
                    List.of(),
                    List.of(cardId.get())
            );
            this.changeCardsVisibilityExecutor.execute(changeCardsVisibilityInput).isSuccess();
        };

        Consumer<Object> updatedDeckContentConsumer = payload -> Optional.of(payload)
                .filter(DeckRefreshedEventPayload.class::isInstance)
                .map(DeckRefreshedEventPayload.class::cast)
                .map(DeckRefreshedEventPayload::deck)
                .ifPresentOrElse(deck -> {
                    assertThat(deck.lanes()).isNotEmpty();

                    var lane = deck.lanes().get(0);
                    assertThat(lane.cards()).hasSize(5);

                    assertThat(lane.cards().get(0).visible()).isFalse();
                }, () -> fail("Missing deck"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDeckContentConsumer)
                .then(changeCardsVisibility)
                .consumeNextWith(updatedDeckContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a deck representation, when we collapse a lane, then the representation data are updated")
    public void givenDeckRepresentationWhenWeCollapseALaneThenTheRepresentationDataAreUpdated() {
        var flux = this.givenSubscriptionToDeck();
        var deckId = new AtomicReference<String>();
        var laneId = new AtomicReference<String>();

        Consumer<Object> initial = payload -> Optional.of(payload)
                .filter(DeckRefreshedEventPayload.class::isInstance)
                .map(DeckRefreshedEventPayload.class::cast)
                .map(DeckRefreshedEventPayload::deck)
                .ifPresentOrElse(deck -> {
                    deckId.set(deck.getId());
                    laneId.set(deck.lanes().get(0).id());
                    assertThat(deck.lanes().get(0).collapsed()).isFalse();
                }, () -> fail("Missing deck"));

        Runnable collapseLane = () -> {
            var input = new ChangeLaneCollapsedStateInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    deckId.get(),
                    laneId.get(),
                    true
            );
            this.changeLaneCollapsedStateExecutor.execute(input).isSuccess();
        };

        Consumer<Object> updated = payload -> Optional.of(payload)
                .filter(DeckRefreshedEventPayload.class::isInstance)
                .map(DeckRefreshedEventPayload.class::cast)
                .map(DeckRefreshedEventPayload::deck)
                .ifPresentOrElse(deck -> {
                    assertThat(deck.lanes().get(0).collapsed()).isTrue();
                }, () -> fail("Missing deck"));

        StepVerifier.create(flux)
                .consumeNextWith(initial)
                .then(collapseLane)
                .consumeNextWith(updated)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
