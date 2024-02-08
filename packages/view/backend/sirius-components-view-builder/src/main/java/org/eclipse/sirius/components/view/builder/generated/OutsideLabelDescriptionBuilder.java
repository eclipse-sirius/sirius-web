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
 * Builder for OutsideLabelDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class OutsideLabelDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.OutsideLabelDescription.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.diagram.OutsideLabelDescription outsideLabelDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createOutsideLabelDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.OutsideLabelDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.OutsideLabelDescription getOutsideLabelDescription() {
        return this.outsideLabelDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.OutsideLabelDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.OutsideLabelDescription build() {
        return this.getOutsideLabelDescription();
    }

    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public OutsideLabelDescriptionBuilder labelExpression(java.lang.String value) {
        this.getOutsideLabelDescription().setLabelExpression(value);
        return this;
    }

    /**
     * Setter for Position.
     *
     * @generated
     */
    public OutsideLabelDescriptionBuilder position(org.eclipse.sirius.components.view.diagram.OutsideLabelPosition value) {
        this.getOutsideLabelDescription().setPosition(value);
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public OutsideLabelDescriptionBuilder style(org.eclipse.sirius.components.view.diagram.OutsideLabelStyle value) {
        this.getOutsideLabelDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public OutsideLabelDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle... values) {
        for (org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle value : values) {
            this.getOutsideLabelDescription().getConditionalStyles().add(value);
        }
        return this;
    }


}

