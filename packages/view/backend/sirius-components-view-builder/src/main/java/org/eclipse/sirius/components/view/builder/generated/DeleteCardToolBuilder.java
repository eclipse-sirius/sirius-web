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
 * Builder for DeleteCardToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DeleteCardToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.DeleteCardTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.DeleteCardTool deleteCardTool = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createDeleteCardTool();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.DeleteCardTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.DeleteCardTool getDeleteCardTool() {
        return this.deleteCardTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.DeleteCardTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.DeleteCardTool build() {
        return this.getDeleteCardTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DeleteCardToolBuilder name(java.lang.String value) {
        this.getDeleteCardTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public DeleteCardToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDeleteCardTool().getBody().add(value);
        }
        return this;
    }


}

