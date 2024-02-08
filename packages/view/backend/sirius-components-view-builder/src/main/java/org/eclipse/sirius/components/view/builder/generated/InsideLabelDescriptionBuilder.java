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
 * Builder for InsideLabelDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class InsideLabelDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.InsideLabelDescription.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.diagram.InsideLabelDescription insideLabelDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createInsideLabelDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.InsideLabelDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.InsideLabelDescription getInsideLabelDescription() {
        return this.insideLabelDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.InsideLabelDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.InsideLabelDescription build() {
        return this.getInsideLabelDescription();
    }

    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public InsideLabelDescriptionBuilder labelExpression(java.lang.String value) {
        this.getInsideLabelDescription().setLabelExpression(value);
        return this;
    }

    /**
     * Setter for Position.
     *
     * @generated
     */
    public InsideLabelDescriptionBuilder position(org.eclipse.sirius.components.view.diagram.InsideLabelPosition value) {
        this.getInsideLabelDescription().setPosition(value);
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public InsideLabelDescriptionBuilder style(org.eclipse.sirius.components.view.diagram.InsideLabelStyle value) {
        this.getInsideLabelDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public InsideLabelDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle... values) {
        for (org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle value : values) {
            this.getInsideLabelDescription().getConditionalStyles().add(value);
        }
        return this;
    }


}

