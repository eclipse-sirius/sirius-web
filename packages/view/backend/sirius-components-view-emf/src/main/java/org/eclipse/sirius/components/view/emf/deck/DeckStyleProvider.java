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
package org.eclipse.sirius.components.view.emf.deck;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.deck.DeckStyle;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.deck.DeckDescriptionStyle;

/**
 * The style provider for the Deck description from view DSL.
 *
 * @author fbarbin
 */
public class DeckStyleProvider implements Function<VariableManager, DeckStyle> {
    private final DeckDescriptionStyle viewStyle;

    public DeckStyleProvider(DeckDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    @Override
    public DeckStyle apply(VariableManager variableManager) {
        String color = "";
        if (this.viewStyle.getBackgroundColor() instanceof FixedColor fixedColor) {
            color = fixedColor.getValue();
        }
        return new DeckStyle(color);
    }

}
