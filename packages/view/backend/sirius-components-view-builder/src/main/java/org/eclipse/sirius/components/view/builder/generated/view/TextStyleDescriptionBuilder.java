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
package org.eclipse.sirius.components.view.builder.generated.view;

/**
 * Builder for TextStyleDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TextStyleDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.TextStyleDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.TextStyleDescription textStyleDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createTextStyleDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.TextStyleDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.TextStyleDescription getTextStyleDescription() {
        return this.textStyleDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.TextStyleDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.TextStyleDescription build() {
        return this.getTextStyleDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TextStyleDescriptionBuilder name(java.lang.String value) {
        this.getTextStyleDescription().setName(value);
        return this;
    }
    /**
     * Setter for ForegroundColorExpression.
     *
     * @generated
     */
    public TextStyleDescriptionBuilder foregroundColorExpression(java.lang.String value) {
        this.getTextStyleDescription().setForegroundColorExpression(value);
        return this;
    }
    /**
     * Setter for BackgroundColorExpression.
     *
     * @generated
     */
    public TextStyleDescriptionBuilder backgroundColorExpression(java.lang.String value) {
        this.getTextStyleDescription().setBackgroundColorExpression(value);
        return this;
    }
    /**
     * Setter for IsBoldExpression.
     *
     * @generated
     */
    public TextStyleDescriptionBuilder isBoldExpression(java.lang.String value) {
        this.getTextStyleDescription().setIsBoldExpression(value);
        return this;
    }
    /**
     * Setter for IsItalicExpression.
     *
     * @generated
     */
    public TextStyleDescriptionBuilder isItalicExpression(java.lang.String value) {
        this.getTextStyleDescription().setIsItalicExpression(value);
        return this;
    }
    /**
     * Setter for IsUnderlineExpression.
     *
     * @generated
     */
    public TextStyleDescriptionBuilder isUnderlineExpression(java.lang.String value) {
        this.getTextStyleDescription().setIsUnderlineExpression(value);
        return this;
    }

}

