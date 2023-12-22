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

import java.util.List;
import java.util.Objects;

/**
 * Represents the Lane concept for Deck representations.
 *
 * @author fbarbin
 */
public record Lane(String id, String descriptionId, String targetObjectId, String targetObjectKind, String targetObjectLabel, String title, String label, List<Card> cards) {
    public Lane {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(targetObjectLabel);
        Objects.requireNonNull(title);
        Objects.requireNonNull(label);
        Objects.requireNonNull(cards);
    }
}
