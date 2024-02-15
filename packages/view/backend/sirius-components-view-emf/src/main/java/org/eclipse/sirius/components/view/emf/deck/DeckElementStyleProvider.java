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

import org.eclipse.sirius.components.deck.DeckElementStyle;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle;

/**
 * The style provider for the Deck lane or card description from view DSL.
 *
 * @author fbarbin
 */
public class DeckElementStyleProvider implements Function<VariableManager, DeckElementStyle> {
    private final DeckElementDescriptionStyle viewStyle;

    public DeckElementStyleProvider(DeckElementDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    @Override
    public DeckElementStyle apply(VariableManager variableManager) {
        String backgroundColor = "";
        String color = "";
        if (this.viewStyle.getBackgroundColor() instanceof FixedColor fixedColor) {
            backgroundColor = fixedColor.getValue();
        }

        if (this.viewStyle.getColor() instanceof FixedColor fixedColor) {
            color = fixedColor.getValue();
        }
        return new DeckElementStyle(backgroundColor, color, this.viewStyle.getFontSize(), this.viewStyle.isItalic(), this.viewStyle.isBold(), this.viewStyle.isUnderline(), this.viewStyle.isStrikeThrough());
    }

}
