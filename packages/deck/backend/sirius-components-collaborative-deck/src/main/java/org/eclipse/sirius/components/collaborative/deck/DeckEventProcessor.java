/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckEventHandler;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckEventProcessor;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Reacts to the input that targets the deck of a specific object and publishes updated versions of the
 * {@link Deck} to interested subscribers.
 *
 * @author fbarbin
 */
public class DeckEventProcessor implements IDeckEventProcessor {

    private final IEditingContext editingContext;

    private final ISubscriptionManager subscriptionManager;

    private final DeckEventFlux deckEventFlux;

    private final List<IDeckEventHandler> deckEventHandlers;

    private DeckContext deckContext;

    private final Logger logger = LoggerFactory.getLogger(DeckEventProcessor.class);

    public DeckEventProcessor(IEditingContext editingContext, ISubscriptionManager subscriptionManager, List<IDeckEventHandler> deckEventHandlers, DeckContext deckContext) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.deckEventHandlers = Objects.requireNonNull(deckEventHandlers);
        this.deckContext = Objects.requireNonNull(deckContext);
        this.deckEventFlux = new DeckEventFlux(deckContext.deck());
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.deckContext.deck();
    }

    public DeckContext getDeckContext() {
        return this.deckContext;
    }

    @Override
    public void update(ICause cause, Deck deck) {
        this.deckContext = new DeckContext(deck, new ArrayList<>());
        this.deckEventFlux.deckRefreshed(cause, deck);
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        if (representationInput instanceof IDeckInput deckInput) {
            Optional<IDeckEventHandler> optionalDeckEventHandler = this.deckEventHandlers.stream()
                    .filter(handler -> handler.canHandle(this.editingContext, deckInput))
                    .findFirst();

            if (optionalDeckEventHandler.isPresent()) {
                IDeckEventHandler deckEventHandler = optionalDeckEventHandler.get();
                deckEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.deckContext, deckInput);
            } else {
                this.logger.atWarn()
                        .setMessage("No handler found for event: {}")
                        .addArgument(deckInput)
                        .log();
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        // Do nothing
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        return Flux.merge(
            this.deckEventFlux.getFlux(input),
            this.subscriptionManager.getFlux(input)
        );
    }

    @Override
    public void dispose() {
        String id = Optional.ofNullable(this.deckContext.deck()).map(Deck::id).orElse(null);
        this.logger.atTrace()
                .setMessage("Disposing the deck event processor {}")
                .addArgument(id)
                .log();

        this.subscriptionManager.dispose();
        this.deckEventFlux.dispose();
    }
}
