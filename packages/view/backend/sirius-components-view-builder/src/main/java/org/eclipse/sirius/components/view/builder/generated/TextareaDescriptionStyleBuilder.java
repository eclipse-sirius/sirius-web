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
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class TextareaDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.TextareaDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.TextareaDescriptionStyle textareaDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createTextareaDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.TextareaDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.TextareaDescriptionStyle getTextareaDescriptionStyle() {
        return this.textareaDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.TextareaDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.TextareaDescriptionStyle build() {
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

