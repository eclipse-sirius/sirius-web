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
 * Builder for TextfieldDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TextfieldDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle textfieldDescriptionStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createTextfieldDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle getTextfieldDescriptionStyle() {
        return this.textfieldDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle build() {
        return this.getTextfieldDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public TextfieldDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getTextfieldDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public TextfieldDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getTextfieldDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public TextfieldDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getTextfieldDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public TextfieldDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getTextfieldDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public TextfieldDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getTextfieldDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public TextfieldDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getTextfieldDescriptionStyle().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for ForegroundColor.
     *
     * @generated
     */
    public TextfieldDescriptionStyleBuilder foregroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getTextfieldDescriptionStyle().setForegroundColor(value);
        return this;
    }

}

