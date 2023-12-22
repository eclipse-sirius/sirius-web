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
package org.eclipse.sirius.components.collaborative.deck;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.deck.Deck;
import org.springframework.stereotype.Service;

/**
 * Provide the image representing a deck.
 *
 * @author fbarbin
 */
@Service
public class DeckImageProvider implements IRepresentationImageProvider {

    @Override
    public Optional<String> getImageURL(String kind) {
        if (Deck.KIND.equals(kind)) {
            return Optional.of("/deck-images/deck.svg");
        }
        return Optional.empty();
    }

}
