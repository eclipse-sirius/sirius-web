/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.deck.renderer.component;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.description.DeckDescription;
import org.eclipse.sirius.components.deck.renderer.events.IDeckEvent;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The props of the deck component.
 *
 * @author fbarbin
 */
public record DeckComponentProps(VariableManager variableManager, DeckDescription deckDescription, Optional<Deck> optionalPreviousDeck, Optional<IDeckEvent> optionalDeckEvent) implements IProps {

    public DeckComponentProps {
        Objects.requireNonNull(variableManager);
        Objects.requireNonNull(deckDescription);
        Objects.requireNonNull(optionalPreviousDeck);
        Objects.requireNonNull(optionalDeckEvent);
    }
}
