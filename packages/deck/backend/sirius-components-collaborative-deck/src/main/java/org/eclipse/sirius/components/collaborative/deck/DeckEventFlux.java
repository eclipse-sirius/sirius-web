/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.deck.dto.DeckRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.deck.Deck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

/**
 * Service used to manage the deck event flux.
 *
 * @author fbarbin
 */
public class DeckEventFlux {

    private final Logger logger = LoggerFactory.getLogger(DeckEventFlux.class);

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private Deck currentDeck;

    public DeckEventFlux(Deck currentDeck) {
        this.currentDeck = Objects.requireNonNull(currentDeck);
    }

    public void deckRefreshed(IInput input, Deck newDeck) {
        this.currentDeck = newDeck;
        if (this.sink.currentSubscriberCount() > 0) {
            EmitResult emitResult = this.sink.tryEmitNext(new DeckRefreshedEventPayload(input.id(), this.currentDeck));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a DeckRefreshedEventPayload: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    public Flux<IPayload> getFlux(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new DeckRefreshedEventPayload(input.id(), this.currentDeck));
        return Flux.concat(initialRefresh, this.sink.asFlux());
    }

    public void dispose() {
        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }
    }
}
