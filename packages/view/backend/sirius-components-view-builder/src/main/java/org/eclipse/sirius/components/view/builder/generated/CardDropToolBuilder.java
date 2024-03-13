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
package org.eclipse.sirius.components.view.builder.generated;

/**
 * Builder for CardDropToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CardDropToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.CardDropTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.CardDropTool cardDropTool = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createCardDropTool();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.CardDropTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.CardDropTool getCardDropTool() {
        return this.cardDropTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.CardDropTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.CardDropTool build() {
        return this.getCardDropTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public CardDropToolBuilder name(java.lang.String value) {
        this.getCardDropTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public CardDropToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCardDropTool().getBody().add(value);
        }
        return this;
    }


}

