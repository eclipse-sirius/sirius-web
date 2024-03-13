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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckContext;
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

    private final IRepresentationPersistenceService representationPersistenceService;

    private final DeckCreationService deckCreationService;

    private final DeckEventFlux deckEventFlux;

    private final List<IDeckEventHandler> deckEventHandlers;

    private final IDeckContext deckContext;

    public DeckEventProcessor(IEditingContext editingContext, ISubscriptionManager subscriptionManager, DeckCreationService deckCreationService,
            List<IDeckEventHandler> deckEventHandlers, IDeckContext deckContext, IRepresentationPersistenceService representationPersistenceService) {

        this.editingContext = Objects.requireNonNull(editingContext);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.deckCreationService = Objects.requireNonNull(deckCreationService);
        this.deckEventHandlers = Objects.requireNonNull(deckEventHandlers);
        this.deckContext = Objects.requireNonNull(deckContext);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);

        String id = this.deckContext.getDeck().getId();
        this.logger.trace("Creating the deck event processor {}", id);

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database.
        Deck deck = this.deckCreationService.refresh(this.editingContext, deckContext).orElse(null);
        this.deckContext.update(deck);

        this.deckEventFlux = new DeckEventFlux(deck);

    }

    @Override
    public IRepresentation getRepresentation() {
        return this.deckContext.getDeck();
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
                deckEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.deckContext, deckInput);
            } else {
                this.logger.warn("No handler found for event: {}", deckInput);
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            Deck refreshedDeckRepresentation = this.deckCreationService.refresh(this.editingContext, this.deckContext).orElse(null);
            this.deckContext.reset();
            this.deckContext.update(refreshedDeckRepresentation);
            if (refreshedDeckRepresentation != null) {
                this.representationPersistenceService.save(changeDescription.getInput(), this.editingContext, refreshedDeckRepresentation);
                this.logger.trace("Deck refreshed: {}", refreshedDeckRepresentation.getId());
            }
            this.deckEventFlux.deckRefreshed(changeDescription.getInput(), this.deckContext.getDeck());
        }
    }

    /**
     * A deck representation is refreshed if there is a semantic change.
     */
    private boolean shouldRefresh(ChangeDescription changeDescription) {
        String kind = changeDescription.getKind();
        return ChangeKind.SEMANTIC_CHANGE.equals(kind) || DeckChangeKind.DECK_REPRESENTATION_UPDATE.equals(kind);
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
        String id = Optional.ofNullable(this.deckContext.getDeck()).map(Deck::id).orElse(null);
        this.logger.trace("Disposing the deck event processor {}", id);
        this.subscriptionManager.dispose();
        this.deckEventFlux.dispose();
    }
}
