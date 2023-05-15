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
 * Builder for TextfieldDescriptionBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class TextfieldDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.TextfieldDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.TextfieldDescription textfieldDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createTextfieldDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.TextfieldDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.TextfieldDescription getTextfieldDescription() {
        return this.textfieldDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.TextfieldDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.TextfieldDescription build() {
        return this.getTextfieldDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TextfieldDescriptionBuilder name(java.lang.String value) {
        this.getTextfieldDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public TextfieldDescriptionBuilder labelExpression(java.lang.String value) {
        this.getTextfieldDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public TextfieldDescriptionBuilder valueExpression(java.lang.String value) {
        this.getTextfieldDescription().setValueExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public TextfieldDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getTextfieldDescription().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public TextfieldDescriptionBuilder style(org.eclipse.sirius.components.view.TextfieldDescriptionStyle value) {
        this.getTextfieldDescription().setStyle(value);
        return this;
    }
    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public TextfieldDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle value : values) {
            this.getTextfieldDescription().getConditionalStyles().add(value);
        }
        return this;
    }


}

