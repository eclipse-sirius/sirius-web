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
package org.eclipse.sirius.components.deck.renderer.events;

import java.util.Map;
import java.util.Objects;

/**
 * A Deck Event to handle the cards visibility state changes.
 *
 * @author fbarbin
 */
public record ChangeCardsVisibilityDeckEvent(Map<String, Boolean> cardsVisibility) implements IDeckEvent {

    public ChangeCardsVisibilityDeckEvent {
        Objects.requireNonNull(cardsVisibility);
    }
}
