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
 * Builder for ConditionalOutsideLabelStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalOutsideLabelStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle conditionalOutsideLabelStyle = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createConditionalOutsideLabelStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle getConditionalOutsideLabelStyle() {
        return this.conditionalOutsideLabelStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle build() {
        return this.getConditionalOutsideLabelStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalOutsideLabelStyleBuilder condition(java.lang.String value) {
        this.getConditionalOutsideLabelStyle().setCondition(value);
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public ConditionalOutsideLabelStyleBuilder style(org.eclipse.sirius.components.view.diagram.OutsideLabelStyle value) {
        this.getConditionalOutsideLabelStyle().setStyle(value);
        return this;
    }

}

