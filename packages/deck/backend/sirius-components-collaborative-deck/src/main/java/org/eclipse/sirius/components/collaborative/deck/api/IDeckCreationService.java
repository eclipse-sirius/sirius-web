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
package org.eclipse.sirius.components.collaborative.deck.api;

import org.eclipse.sirius.components.collaborative.deck.DeckContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.description.DeckDescription;

/**
 * Service used to create deck representations from scratch.
 *
 * @author fbarbin
 */
public interface IDeckCreationService {

    /**
     * Creates a new deck representation using the given parameters.
     *
     * @param editingContext
     *            The editing context
     * @param deckDescription
     *            The description of the deck representation
     * @param targetObject
     *            The object used as the target
     * @param deckContext
     *           The context of the deck representation
     * @return A new deck representation
     */
    Deck create(IEditingContext editingContext, DeckDescription deckDescription, Object targetObject, DeckContext deckContext);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author fbarbin
     */
    class NoOp implements IDeckCreationService {

        @Override
        public Deck create(IEditingContext editingContext, DeckDescription deckDescription, Object targetObject, DeckContext deckContext) {
            return null;
        }
    }

}
