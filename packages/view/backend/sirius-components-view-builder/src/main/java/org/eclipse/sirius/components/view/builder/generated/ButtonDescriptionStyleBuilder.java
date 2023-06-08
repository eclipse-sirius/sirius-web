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
 * Builder for ButtonDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ButtonDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ButtonDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.ButtonDescriptionStyle buttonDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createButtonDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.ButtonDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ButtonDescriptionStyle getButtonDescriptionStyle() {
        return this.buttonDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ButtonDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.ButtonDescriptionStyle build() {
        return this.getButtonDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ButtonDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getButtonDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ButtonDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getButtonDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ButtonDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getButtonDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ButtonDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getButtonDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ButtonDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getButtonDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public ButtonDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getButtonDescriptionStyle().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for ForegroundColor.
     *
     * @generated
     */
    public ButtonDescriptionStyleBuilder foregroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getButtonDescriptionStyle().setForegroundColor(value);
        return this;
    }

}

