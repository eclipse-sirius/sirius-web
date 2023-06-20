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
 * Builder for LabelDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class LabelDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.LabelDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.LabelDescriptionStyle labelDescriptionStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createLabelDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.LabelDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.LabelDescriptionStyle getLabelDescriptionStyle() {
        return this.labelDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.LabelDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.LabelDescriptionStyle build() {
        return this.getLabelDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public LabelDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getLabelDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public LabelDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getLabelDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public LabelDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getLabelDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public LabelDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getLabelDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public LabelDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getLabelDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for Color.
     *
     * @generated
     */
    public LabelDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getLabelDescriptionStyle().setColor(value);
        return this;
    }

}

