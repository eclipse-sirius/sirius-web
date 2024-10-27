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

import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * Root concept of the Deck representation.
 *
 * @author fbarbin
 */
public record Deck(String id, String descriptionId, String targetObjectId, DeckStyle style, List<Lane> lanes) implements IRepresentation {

    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=Deck";

    public Deck {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(lanes);
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public String getKind() {
        return KIND;
    }
}
