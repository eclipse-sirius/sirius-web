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
 * Builder for SelectDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class SelectDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.SelectDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.SelectDescription selectDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createSelectDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.SelectDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.SelectDescription getSelectDescription() {
        return this.selectDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.SelectDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.SelectDescription build() {
        return this.getSelectDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public SelectDescriptionBuilder name(java.lang.String value) {
        this.getSelectDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public SelectDescriptionBuilder labelExpression(java.lang.String value) {
        this.getSelectDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public SelectDescriptionBuilder helpExpression(java.lang.String value) {
        this.getSelectDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public SelectDescriptionBuilder valueExpression(java.lang.String value) {
        this.getSelectDescription().setValueExpression(value);
        return this;
    }
    /**
     * Setter for CandidatesExpression.
     *
     * @generated
     */
    public SelectDescriptionBuilder candidatesExpression(java.lang.String value) {
        this.getSelectDescription().setCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for CandidateLabelExpression.
     *
     * @generated
     */
    public SelectDescriptionBuilder candidateLabelExpression(java.lang.String value) {
        this.getSelectDescription().setCandidateLabelExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public SelectDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getSelectDescription().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public SelectDescriptionBuilder style(org.eclipse.sirius.components.view.form.SelectDescriptionStyle value) {
        this.getSelectDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public SelectDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.form.ConditionalSelectDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.form.ConditionalSelectDescriptionStyle value : values) {
            this.getSelectDescription().getConditionalStyles().add(value);
        }
        return this;
    }

    /**
     * Setter for IsEnabledExpression.
     *
     * @generated
     */
    public SelectDescriptionBuilder isEnabledExpression(java.lang.String value) {
        this.getSelectDescription().setIsEnabledExpression(value);
        return this;
    }


}

