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
package org.eclipse.sirius.components.collaborative.deck;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.renderer.events.IDeckEvent;

/**
 * The Deck Context implementation.
 *
 * @author fbarbin
 */
public record DeckContext(Deck deck, List<IDeckEvent> deckEvents) {
    public DeckContext {
        Objects.requireNonNull(deck);
        Objects.requireNonNull(deckEvents);
    }
}
