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
 * Builder for TextareaDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TextareaDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.TextareaDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.TextareaDescriptionStyle textareaDescriptionStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createTextareaDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.TextareaDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.TextareaDescriptionStyle getTextareaDescriptionStyle() {
        return this.textareaDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.TextareaDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.TextareaDescriptionStyle build() {
        return this.getTextareaDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public TextareaDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getTextareaDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public TextareaDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getTextareaDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public TextareaDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getTextareaDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public TextareaDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getTextareaDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public TextareaDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getTextareaDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public TextareaDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getTextareaDescriptionStyle().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for ForegroundColor.
     *
     * @generated
     */
    public TextareaDescriptionStyleBuilder foregroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getTextareaDescriptionStyle().setForegroundColor(value);
        return this;
    }

}

