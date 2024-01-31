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
package org.eclipse.sirius.components.collaborative.deck.api;

import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.renderer.events.IDeckEvent;

/**
 * Information used to perform some operations on the deck representation.
 *
 * @author fbarbin
 */
public interface IDeckContext {

    void update(Deck deck);

    Deck getDeck();

    void reset();

    IDeckEvent getDeckEvent();

    void setDeckEvent(IDeckEvent deckEvent);

}
