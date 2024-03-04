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
 * Builder for ConditionalDeckElementDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalDeckElementDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle conditionalDeckElementDescriptionStyle = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createConditionalDeckElementDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle getConditionalDeckElementDescriptionStyle() {
        return this.conditionalDeckElementDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle build() {
        return this.getConditionalDeckElementDescriptionStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalDeckElementDescriptionStyleBuilder condition(java.lang.String value) {
        this.getConditionalDeckElementDescriptionStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ConditionalDeckElementDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getConditionalDeckElementDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ConditionalDeckElementDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getConditionalDeckElementDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ConditionalDeckElementDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getConditionalDeckElementDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ConditionalDeckElementDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getConditionalDeckElementDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ConditionalDeckElementDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getConditionalDeckElementDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public ConditionalDeckElementDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalDeckElementDescriptionStyle().setBackgroundColor(value);
        return this;
    }

    /**
     * Setter for Color.
     *
     * @generated
     */
    public ConditionalDeckElementDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalDeckElementDescriptionStyle().setColor(value);
        return this;
    }

}

