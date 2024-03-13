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
 * Builder for EditCardToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class EditCardToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.EditCardTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.EditCardTool editCardTool = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createEditCardTool();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.EditCardTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.EditCardTool getEditCardTool() {
        return this.editCardTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.EditCardTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.EditCardTool build() {
        return this.getEditCardTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public EditCardToolBuilder name(java.lang.String value) {
        this.getEditCardTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public EditCardToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getEditCardTool().getBody().add(value);
        }
        return this;
    }


}

