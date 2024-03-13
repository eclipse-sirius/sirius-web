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
 * Builder for EditLaneToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class EditLaneToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.EditLaneTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.EditLaneTool editLaneTool = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createEditLaneTool();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.EditLaneTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.EditLaneTool getEditLaneTool() {
        return this.editLaneTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.EditLaneTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.EditLaneTool build() {
        return this.getEditLaneTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public EditLaneToolBuilder name(java.lang.String value) {
        this.getEditLaneTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public EditLaneToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getEditLaneTool().getBody().add(value);
        }
        return this;
    }


}

