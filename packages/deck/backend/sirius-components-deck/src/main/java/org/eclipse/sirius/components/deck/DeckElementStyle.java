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
package org.eclipse.sirius.components.deck;

import java.util.Objects;

/**
 * Represents the Lane Description and Card Description Style.
 *
 * @author fbarbin
 */
public record DeckElementStyle(String backgroundColor, String color, Integer fontSize, Boolean italic, Boolean bold, Boolean underline, Boolean strikeThrough) {

    public DeckElementStyle {
        Objects.requireNonNull(backgroundColor);
        Objects.requireNonNull(color);
        Objects.requireNonNull(fontSize);
        Objects.requireNonNull(italic);
        Objects.requireNonNull(bold);
        Objects.requireNonNull(underline);
        Objects.requireNonNull(strikeThrough);
    }

}
