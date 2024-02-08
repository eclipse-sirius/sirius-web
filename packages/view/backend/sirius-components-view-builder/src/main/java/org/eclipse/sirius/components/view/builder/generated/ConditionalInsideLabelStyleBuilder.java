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
 * Builder for ConditionalInsideLabelStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalInsideLabelStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle conditionalInsideLabelStyle = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createConditionalInsideLabelStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle getConditionalInsideLabelStyle() {
        return this.conditionalInsideLabelStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle build() {
        return this.getConditionalInsideLabelStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalInsideLabelStyleBuilder condition(java.lang.String value) {
        this.getConditionalInsideLabelStyle().setCondition(value);
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public ConditionalInsideLabelStyleBuilder style(org.eclipse.sirius.components.view.diagram.InsideLabelStyle value) {
        this.getConditionalInsideLabelStyle().setStyle(value);
        return this;
    }

}

