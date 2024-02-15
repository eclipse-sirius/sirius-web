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
package org.eclipse.sirius.components.deck;

import java.util.Objects;

/**
 * Represents the Card concept for Deck representations.
 *
 * @author fbarbin
 */
public record Card(String id, String descriptionId, String targetObjectId, String targetObjectKind, String targetObjectLabel, String title, String label, String description, Boolean visible, DeckElementStyle style) {

    public Card {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(targetObjectLabel);
        Objects.requireNonNull(title);
        Objects.requireNonNull(label);
        Objects.requireNonNull(description);
    }
}
