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
 * Builder for ButtonDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ButtonDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.ButtonDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.ButtonDescription buttonDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createButtonDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.ButtonDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.ButtonDescription getButtonDescription() {
        return this.buttonDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.ButtonDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.ButtonDescription build() {
        return this.getButtonDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public ButtonDescriptionBuilder name(java.lang.String value) {
        this.getButtonDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public ButtonDescriptionBuilder labelExpression(java.lang.String value) {
        this.getButtonDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public ButtonDescriptionBuilder helpExpression(java.lang.String value) {
        this.getButtonDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for ButtonLabelExpression.
     *
     * @generated
     */
    public ButtonDescriptionBuilder buttonLabelExpression(java.lang.String value) {
        this.getButtonDescription().setButtonLabelExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public ButtonDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getButtonDescription().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for ImageExpression.
     *
     * @generated
     */
    public ButtonDescriptionBuilder imageExpression(java.lang.String value) {
        this.getButtonDescription().setImageExpression(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public ButtonDescriptionBuilder style(org.eclipse.sirius.components.view.form.ButtonDescriptionStyle value) {
        this.getButtonDescription().setStyle(value);
        return this;
    }
    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public ButtonDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle value : values) {
            this.getButtonDescription().getConditionalStyles().add(value);
        }
        return this;
    }


}

