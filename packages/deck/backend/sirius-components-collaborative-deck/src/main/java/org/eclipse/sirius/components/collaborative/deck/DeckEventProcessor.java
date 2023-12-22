/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckEventHandler;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckEventProcessor;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.RenameDeckInput;
import org.eclipse.sirius.components.collaborative.deck.service.DeckCreationService;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.deck.Deck;
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

    private final Logger logger = LoggerFactory.getLogger(DeckEventProcessor.class);

    private final IEditingContext editingContext;

    private final ISubscriptionManager subscriptionManager;

    private final DeckCreationService deckCreationService;

    private final AtomicReference<Deck> currentDeck = new AtomicReference<>();

    private final DeckEventFlux deckEventFlux;

    private final List<IDeckEventHandler> deckEventHandlers;

    public DeckEventProcessor(IEditingContext editingContext, Deck deckDiagram, ISubscriptionManager subscriptionManager, DeckCreationService deckCreationService,
            List<IDeckEventHandler> deckEventHandlers) {
        this.logger.trace("Creating the deck event processor {}", deckDiagram.getId());

        this.editingContext = Objects.requireNonNull(editingContext);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.deckCreationService = Objects.requireNonNull(deckCreationService);
        this.deckEventHandlers = Objects.requireNonNull(deckEventHandlers);

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database.
        Deck deck = this.deckCreationService.refresh(this.editingContext, deckDiagram).orElse(null);
        this.currentDeck.set(deck);

        this.deckEventFlux = new DeckEventFlux(deck);

    }

    @Override
    public IRepresentation getRepresentation() {
        return this.currentDeck.get();
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        IRepresentationInput effectiveInput = representationInput;
        if (representationInput instanceof RenameRepresentationInput renameRepresentationInput) {
            effectiveInput = new RenameDeckInput(renameRepresentationInput.id(), renameRepresentationInput.editingContextId(), renameRepresentationInput.representationId(),
                    renameRepresentationInput.newLabel());
        }
        if (effectiveInput instanceof IDeckInput deckInput) {
            Optional<IDeckEventHandler> optionalDeckEventHandler = this.deckEventHandlers.stream().filter(handler -> handler.canHandle(deckInput)).findFirst();

            if (optionalDeckEventHandler.isPresent()) {
                IDeckEventHandler deckEventHandler = optionalDeckEventHandler.get();
                deckEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.currentDeck.get(), deckInput);
            } else {
                this.logger.warn("No handler found for event: {}", deckInput);
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            Deck refreshedDeckRepresentation = this.deckCreationService.refresh(this.editingContext, this.currentDeck.get()).orElse(null);

            this.currentDeck.set(refreshedDeckRepresentation);

            this.logger.trace("Deck refreshed: {}", refreshedDeckRepresentation.getId());

            this.deckEventFlux.deckRefreshed(changeDescription.getInput(), this.currentDeck.get());
        }
    }

    /**
     * A deck representation is refreshed if there is a semantic change.
     */
    private boolean shouldRefresh(ChangeDescription changeDescription) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
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
        String id = null;
        if (this.currentDeck.get() != null) {
            id = this.currentDeck.get().getId();
        }
        this.logger.trace("Disposing the deck event processor {}", id);
        this.subscriptionManager.dispose();
        this.deckEventFlux.dispose();
    }
}
