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
package org.eclipse.sirius.components.deck.renderer;

import java.util.Optional;

import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.representations.BaseRenderer;
import org.eclipse.sirius.components.representations.Element;

/**
 * Renderer used to create deck representations.
 *
 * @author fbarbin
 */
public class DeckRenderer {

    private final BaseRenderer baseRenderer;

    public DeckRenderer() {
        this.baseRenderer = new BaseRenderer(new DeckInstancePropsValidator(), new DeckComponentPropsValidator(), new DeckElementFactory());
    }

    public Deck render(Element element) {
        return Optional.of(this.baseRenderer.renderElement(element))
                .filter(Deck.class::isInstance)
                .map(Deck.class::cast)
                .orElse(null);
    }
}
