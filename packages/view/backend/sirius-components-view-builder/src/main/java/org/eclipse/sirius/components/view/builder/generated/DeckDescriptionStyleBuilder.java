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
package org.eclipse.sirius.components.view.builder.generated;

/**
 * Builder for DeckDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DeckDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.DeckDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.DeckDescriptionStyle deckDescriptionStyle = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createDeckDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.DeckDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.DeckDescriptionStyle getDeckDescriptionStyle() {
        return this.deckDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.DeckDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.DeckDescriptionStyle build() {
        return this.getDeckDescriptionStyle();
    }

    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public DeckDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getDeckDescriptionStyle().setBackgroundColor(value);
        return this;
    }

}

