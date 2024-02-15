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
package org.eclipse.sirius.components.deck.renderer.elements;

import java.util.Objects;

import org.eclipse.sirius.components.deck.DeckElementStyle;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the card element.
 *
 * @author fbarbin
 */

public record CardElementProps(String id, String descriptionId, String targetObjectId, String targetObjectKind, String targetObjectLabel, String title, String label, String description, Boolean visible,
        DeckElementStyle style) implements IProps {

    public static final String TYPE = "Card";

    public CardElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(targetObjectLabel);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(title);
        Objects.requireNonNull(label);
        Objects.requireNonNull(description);
    }
}
