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

import org.eclipse.sirius.components.collaborative.deck.dto.input.DropDeckLaneInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.EditDeckLaneInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.deck.Deck;

/**
 * Service used to manage deck lane.
 *
 * @author fbarbin
 */
public interface IDeckLaneService {

    IPayload editLane(EditDeckLaneInput editDeckLaneInput, IEditingContext editingContext, Deck deck);

    IPayload dropLane(DropDeckLaneInput dropDeckLaneInput, IEditingContext editingContext, Deck deck);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author fbarbin
     */
    class NoOp implements IDeckLaneService {

        @Override
        public IPayload editLane(EditDeckLaneInput editDeckLaneInput, IEditingContext editingContext, Deck deck) {
            return null;
        }

        @Override
        public IPayload dropLane(DropDeckLaneInput dropDeckLaneInput, IEditingContext editingContext, Deck deck) {
            return null;
        }
    }


}
