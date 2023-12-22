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
 * Builder for CreateCardToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CreateCardToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.CreateCardTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.CreateCardTool createCardTool = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createCreateCardTool();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.CreateCardTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.CreateCardTool getCreateCardTool() {
        return this.createCardTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.CreateCardTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.CreateCardTool build() {
        return this.getCreateCardTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public CreateCardToolBuilder name(java.lang.String value) {
        this.getCreateCardTool().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public CreateCardToolBuilder preconditionExpression(java.lang.String value) {
        this.getCreateCardTool().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public CreateCardToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCreateCardTool().getBody().add(value);
        }
        return this;
    }


}

