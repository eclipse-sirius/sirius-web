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
 * Builder for RadioDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class RadioDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.RadioDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.RadioDescriptionStyle radioDescriptionStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createRadioDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.RadioDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.RadioDescriptionStyle getRadioDescriptionStyle() {
        return this.radioDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.RadioDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.RadioDescriptionStyle build() {
        return this.getRadioDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public RadioDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getRadioDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public RadioDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getRadioDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public RadioDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getRadioDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public RadioDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getRadioDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public RadioDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getRadioDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for Color.
     *
     * @generated
     */
    public RadioDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getRadioDescriptionStyle().setColor(value);
        return this;
    }

}

