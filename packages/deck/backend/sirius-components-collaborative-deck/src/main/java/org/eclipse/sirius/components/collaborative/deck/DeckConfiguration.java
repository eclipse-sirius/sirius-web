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
package org.eclipse.sirius.components.collaborative.deck;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration used to create a deck event processor.
 *
 * @author fbarbin
 */
public class DeckConfiguration implements IRepresentationConfiguration {

    private final UUID deckId;

    public DeckConfiguration(UUID deckId) {
        this.deckId = Objects.requireNonNull(deckId);
    }

    @Override
    public String getId() {
        return this.deckId.toString();
    }

}
