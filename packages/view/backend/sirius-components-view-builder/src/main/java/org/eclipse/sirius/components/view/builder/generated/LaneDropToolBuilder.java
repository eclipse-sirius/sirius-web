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
 * Builder for LaneDropToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class LaneDropToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.LaneDropTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.LaneDropTool laneDropTool = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createLaneDropTool();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.LaneDropTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.LaneDropTool getLaneDropTool() {
        return this.laneDropTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.LaneDropTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.LaneDropTool build() {
        return this.getLaneDropTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public LaneDropToolBuilder name(java.lang.String value) {
        this.getLaneDropTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public LaneDropToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getLaneDropTool().getBody().add(value);
        }
        return this;
    }


}

