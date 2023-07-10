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
 * Builder for CheckboxDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CheckboxDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.CheckboxDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.CheckboxDescription checkboxDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createCheckboxDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.CheckboxDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.CheckboxDescription getCheckboxDescription() {
        return this.checkboxDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.CheckboxDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.CheckboxDescription build() {
        return this.getCheckboxDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public CheckboxDescriptionBuilder name(java.lang.String value) {
        this.getCheckboxDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public CheckboxDescriptionBuilder labelExpression(java.lang.String value) {
        this.getCheckboxDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public CheckboxDescriptionBuilder helpExpression(java.lang.String value) {
        this.getCheckboxDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public CheckboxDescriptionBuilder valueExpression(java.lang.String value) {
        this.getCheckboxDescription().setValueExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public CheckboxDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCheckboxDescription().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public CheckboxDescriptionBuilder style(org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle value) {
        this.getCheckboxDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public CheckboxDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle value : values) {
            this.getCheckboxDescription().getConditionalStyles().add(value);
        }
        return this;
    }

    /**
     * Setter for IsEnabledExpression.
     *
     * @generated
     */
    public CheckboxDescriptionBuilder isEnabledExpression(java.lang.String value) {
        this.getCheckboxDescription().setIsEnabledExpression(value);
        return this;
    }


}

