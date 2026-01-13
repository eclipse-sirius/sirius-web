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

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.deck.DeckContext;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.events.ICause;

/**
 * Interface implemented by the deck event processor.
 *
 * @author fbarbin
 */
public interface IDeckEventProcessor extends IRepresentationEventProcessor {

    /**
     * Returns the current deck context.
     *
     * @return The current deck context
     * @since v2026.9.0
     */
    DeckContext getDeckContext();

    /**
     * Used to update the content of the representation event processor.
     *
     * @param cause The cause which has triggered the update
     * @param deck The new version of the representation
     *
     * @technical-debt This API should not be considered stable for the moment, it is still being evaluated against the
     * various use cases of our event processors
     */
    void update(ICause cause, Deck deck);
}
