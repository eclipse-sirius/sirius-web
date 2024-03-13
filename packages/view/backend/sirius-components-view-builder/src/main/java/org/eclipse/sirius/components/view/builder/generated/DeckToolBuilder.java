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
 * Builder for org.eclipse.sirius.components.view.deck.DeckTool.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class DeckToolBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.deck.DeckTool.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.deck.DeckTool getDeckTool();

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DeckToolBuilder name(java.lang.String value) {
        this.getDeckTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public DeckToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDeckTool().getBody().add(value);
        }
        return this;
    }


}

