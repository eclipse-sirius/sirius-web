/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
 * Builder for ConditionalNodeStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalNodeStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle conditionalNodeStyle = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createConditionalNodeStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle getConditionalNodeStyle() {
        return this.conditionalNodeStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle build() {
        return this.getConditionalNodeStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalNodeStyleBuilder condition(java.lang.String value) {
        this.getConditionalNodeStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public ConditionalNodeStyleBuilder style(org.eclipse.sirius.components.view.diagram.NodeStyleDescription value) {
        this.getConditionalNodeStyle().setStyle(value);
        return this;
    }

}

