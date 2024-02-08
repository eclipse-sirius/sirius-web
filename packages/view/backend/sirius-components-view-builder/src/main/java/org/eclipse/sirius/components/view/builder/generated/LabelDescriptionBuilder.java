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
 * Builder for LabelDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class LabelDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.LabelDescription.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.form.LabelDescription labelDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createLabelDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.LabelDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.LabelDescription getLabelDescription() {
        return this.labelDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.LabelDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.form.LabelDescription build() {
        return this.getLabelDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public LabelDescriptionBuilder name(java.lang.String value) {
        this.getLabelDescription().setName(value);
        return this;
    }

    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public LabelDescriptionBuilder labelExpression(java.lang.String value) {
        this.getLabelDescription().setLabelExpression(value);
        return this;
    }

    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public LabelDescriptionBuilder helpExpression(java.lang.String value) {
        this.getLabelDescription().setHelpExpression(value);
        return this;
    }

    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public LabelDescriptionBuilder valueExpression(java.lang.String value) {
        this.getLabelDescription().setValueExpression(value);
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public LabelDescriptionBuilder style(org.eclipse.sirius.components.view.form.LabelDescriptionStyle value) {
        this.getLabelDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public LabelDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.form.ConditionalLabelDescriptionStyle... values) {
        for (org.eclipse.sirius.components.view.form.ConditionalLabelDescriptionStyle value : values) {
            this.getLabelDescription().getConditionalStyles().add(value);
        }
        return this;
    }

}

