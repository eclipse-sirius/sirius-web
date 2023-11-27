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
package org.eclipse.sirius.components.collaborative.deck.api;

import java.util.Optional;

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
     * @param label
     *            The label of the deck representation
     * @param targetObject
     *            The object used as the target
     * @param deckDescription
     *            The description of the deck representation
     * @param editingContext
     *            The editing context
     * @return A new deck representation
     */
    Deck create(String label, Object targetObject, DeckDescription deckDescription, IEditingContext editingContext);

    /**
     * Refresh an existing deck.
     *
     * <p>
     * Refreshing a deck seems to always be possible but it may not be the case. In some situation, the semantic
     * element on which the previous deck has been created may not exist anymore and thus we can return an empty
     * optional if we are unable to refresh the deck.
     * </p>
     *
     * @param editingContext
     *            The editing context
     * @param deckRepresentation
     *            The deck representation
     * @return An updated deck if we have been able to refresh it.
     */
    Optional<Deck> refresh(IEditingContext editingContext, Deck deckRepresentation);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author fbarbin
     */
    class NoOp implements IDeckCreationService {

        @Override
        public Deck create(String label, Object targetObject, DeckDescription deckDescription, IEditingContext editingContext) {
            return null;
        }

        @Override
        public Optional<Deck> refresh(IEditingContext editingContext, Deck deckRepresentation) {
            return Optional.empty();
        }
    }

}
