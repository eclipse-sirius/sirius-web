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
package org.eclipse.sirius.components.collaborative.deck.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.deck.Deck;

/**
 * Payload used to indicate that the Deck representation has been refreshed.
 *
 * @author fbarbin
 */
public record DeckRefreshedEventPayload(UUID id, Deck deck) implements IPayload {
    public DeckRefreshedEventPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(deck);
    }
}
