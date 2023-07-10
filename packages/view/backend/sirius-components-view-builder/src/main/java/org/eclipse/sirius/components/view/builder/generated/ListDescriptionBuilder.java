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
 * Builder for ListDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ListDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.ListDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.ListDescription listDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createListDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.ListDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.ListDescription getListDescription() {
        return this.listDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.ListDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.ListDescription build() {
        return this.getListDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public ListDescriptionBuilder name(java.lang.String value) {
        this.getListDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public ListDescriptionBuilder labelExpression(java.lang.String value) {
        this.getListDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public ListDescriptionBuilder helpExpression(java.lang.String value) {
        this.getListDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public ListDescriptionBuilder valueExpression(java.lang.String value) {
        this.getListDescription().setValueExpression(value);
        return this;
    }
    /**
     * Setter for DisplayExpression.
     *
     * @generated
     */
    public ListDescriptionBuilder displayExpression(java.lang.String value) {
        this.getListDescription().setDisplayExpression(value);
        return this;
    }
    /**
     * Setter for IsDeletableExpression.
     *
     * @generated
     */
    public ListDescriptionBuilder isDeletableExpression(java.lang.String value) {
        this.getListDescription().setIsDeletableExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public ListDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getListDescription().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public ListDescriptionBuilder style(org.eclipse.sirius.components.view.form.ListDescriptionStyle value) {
        this.getListDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public ListDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle value : values) {
            this.getListDescription().getConditionalStyles().add(value);
        }
        return this;
    }

    /**
     * Setter for IsEnabledExpression.
     *
     * @generated
     */
    public ListDescriptionBuilder isEnabledExpression(java.lang.String value) {
        this.getListDescription().setIsEnabledExpression(value);
        return this;
    }


}

