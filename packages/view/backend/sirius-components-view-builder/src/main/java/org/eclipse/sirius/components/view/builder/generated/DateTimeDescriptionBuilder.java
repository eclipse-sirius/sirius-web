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
 * Builder for DateTimeDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DateTimeDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.DateTimeDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.DateTimeDescription dateTimeDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createDateTimeDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.DateTimeDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.DateTimeDescription getDateTimeDescription() {
        return this.dateTimeDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.DateTimeDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.DateTimeDescription build() {
        return this.getDateTimeDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DateTimeDescriptionBuilder name(java.lang.String value) {
        this.getDateTimeDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public DateTimeDescriptionBuilder labelExpression(java.lang.String value) {
        this.getDateTimeDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public DateTimeDescriptionBuilder helpExpression(java.lang.String value) {
        this.getDateTimeDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for StringValueExpression.
     *
     * @generated
     */
    public DateTimeDescriptionBuilder stringValueExpression(java.lang.String value) {
        this.getDateTimeDescription().setStringValueExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public DateTimeDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDateTimeDescription().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for IsEnabledExpression.
     *
     * @generated
     */
    public DateTimeDescriptionBuilder isEnabledExpression(java.lang.String value) {
        this.getDateTimeDescription().setIsEnabledExpression(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public DateTimeDescriptionBuilder style(org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle value) {
        this.getDateTimeDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public DateTimeDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle value : values) {
            this.getDateTimeDescription().getConditionalStyles().add(value);
        }
        return this;
    }

    /**
     * Setter for Type.
     *
     * @generated
     */
    public DateTimeDescriptionBuilder type(org.eclipse.sirius.components.view.form.DateTimeType value) {
        this.getDateTimeDescription().setType(value);
        return this;
    }

}

