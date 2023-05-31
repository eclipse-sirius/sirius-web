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
 * Builder for TextAreaDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TextAreaDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.TextAreaDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.TextAreaDescription textAreaDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createTextAreaDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.TextAreaDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.TextAreaDescription getTextAreaDescription() {
        return this.textAreaDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.TextAreaDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.TextAreaDescription build() {
        return this.getTextAreaDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TextAreaDescriptionBuilder name(java.lang.String value) {
        this.getTextAreaDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public TextAreaDescriptionBuilder labelExpression(java.lang.String value) {
        this.getTextAreaDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public TextAreaDescriptionBuilder helpExpression(java.lang.String value) {
        this.getTextAreaDescription().setHelpExpression(value);
        return this;
    }

    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public TextAreaDescriptionBuilder valueExpression(java.lang.String value) {
        this.getTextAreaDescription().setValueExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public TextAreaDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getTextAreaDescription().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public TextAreaDescriptionBuilder style(org.eclipse.sirius.components.view.TextareaDescriptionStyle value) {
        this.getTextAreaDescription().setStyle(value);
        return this;
    }
    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public TextAreaDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle value : values) {
            this.getTextAreaDescription().getConditionalStyles().add(value);
        }
        return this;
    }


}

