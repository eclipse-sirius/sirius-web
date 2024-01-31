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
package org.eclipse.sirius.components.collaborative.deck;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.deck.api.IDeckContext;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.renderer.events.IDeckEvent;

/**
 * The Deck Context implementation.
 *
 * @author fbarbin
 */
public class DeckContext implements IDeckContext {

    private Deck deck;

    private IDeckEvent deckEvent;

    public DeckContext(Deck initialDeck) {
        this.deck = Objects.requireNonNull(initialDeck);
    }

    @Override
    public void update(Deck newDeck) {
        this.deck = Objects.requireNonNull(newDeck);
    }

    @Override
    public Deck getDeck() {
        return this.deck;
    }

    @Override
    public void reset() {
        this.deckEvent = null;
    }

    @Override
    public IDeckEvent getDeckEvent() {
        return this.deckEvent;
    }

    @Override
    public void setDeckEvent(IDeckEvent deckEvent) {
        this.deckEvent = Objects.requireNonNull(deckEvent);
    }

}
