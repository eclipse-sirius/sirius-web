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
 * Builder for DeckElementDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DeckElementDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle deckElementDescriptionStyle = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createDeckElementDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle getDeckElementDescriptionStyle() {
        return this.deckElementDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle build() {
        return this.getDeckElementDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public DeckElementDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getDeckElementDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public DeckElementDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getDeckElementDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public DeckElementDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getDeckElementDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public DeckElementDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getDeckElementDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public DeckElementDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getDeckElementDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public DeckElementDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getDeckElementDescriptionStyle().setBackgroundColor(value);
        return this;
    }

    /**
     * Setter for Color.
     *
     * @generated
     */
    public DeckElementDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getDeckElementDescriptionStyle().setColor(value);
        return this;
    }

}

