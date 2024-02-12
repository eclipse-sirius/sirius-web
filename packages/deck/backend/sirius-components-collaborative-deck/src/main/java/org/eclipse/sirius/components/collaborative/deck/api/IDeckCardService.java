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

import org.eclipse.sirius.components.collaborative.deck.dto.input.CreateDeckCardInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.DeleteDeckCardInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.DropDeckCardInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.EditDeckCardInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.deck.Deck;

/**
 * Service used to manage deck card.
 *
 * @author fbarbin
 */
public interface IDeckCardService {

    /**
     * Creates a new deck card using the given parameters.
     */
    IPayload createCard(CreateDeckCardInput createDeckCardInput, IEditingContext editingContext, Deck deck);

    /**
     * Delete a deck card.
     */
    IPayload deleteCard(DeleteDeckCardInput deleteDeckCardInput, IEditingContext editingContext, Deck deck);

    /**
     * Edit an existing card.
     */
    IPayload editCard(EditDeckCardInput editDeckCardInput, IEditingContext editingContext, Deck deck);

    /**
     * Move an existing card.
     */
    IPayload dropCard(DropDeckCardInput dropDeckCardInput, IEditingContext editingContext, Deck deck);


    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author fbarbin
     */
    class NoOp implements IDeckCardService {

        @Override
        public IPayload createCard(CreateDeckCardInput createDeckCardInput, IEditingContext editingContext, Deck deck) {
            return null;
        }

        @Override
        public IPayload editCard(EditDeckCardInput editDeckCardInput, IEditingContext editingContext, Deck deck) {
            return null;
        }

        @Override
        public IPayload deleteCard(DeleteDeckCardInput deleteDeckCardInput, IEditingContext editingContext, Deck deck) {
            return null;
        }

        @Override
        public IPayload dropCard(DropDeckCardInput dropDeckCardInput, IEditingContext editingContext, Deck deck) {
            return null;
        }
    }


}
