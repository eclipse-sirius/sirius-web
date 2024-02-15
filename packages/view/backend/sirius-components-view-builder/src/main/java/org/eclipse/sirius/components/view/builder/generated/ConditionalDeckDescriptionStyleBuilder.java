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
 * Builder for ConditionalDeckDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalDeckDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle conditionalDeckDescriptionStyle = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createConditionalDeckDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle getConditionalDeckDescriptionStyle() {
        return this.conditionalDeckDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle build() {
        return this.getConditionalDeckDescriptionStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalDeckDescriptionStyleBuilder condition(java.lang.String value) {
        this.getConditionalDeckDescriptionStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public ConditionalDeckDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalDeckDescriptionStyle().setBackgroundColor(value);
        return this;
    }

}

