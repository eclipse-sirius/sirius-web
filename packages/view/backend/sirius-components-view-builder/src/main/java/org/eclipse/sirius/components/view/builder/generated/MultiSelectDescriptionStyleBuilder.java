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
 * Builder for MultiSelectDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class MultiSelectDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle multiSelectDescriptionStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createMultiSelectDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle getMultiSelectDescriptionStyle() {
        return this.multiSelectDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle build() {
        return this.getMultiSelectDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public MultiSelectDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getMultiSelectDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public MultiSelectDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getMultiSelectDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public MultiSelectDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getMultiSelectDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public MultiSelectDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getMultiSelectDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public MultiSelectDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getMultiSelectDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public MultiSelectDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getMultiSelectDescriptionStyle().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for ForegroundColor.
     *
     * @generated
     */
    public MultiSelectDescriptionStyleBuilder foregroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getMultiSelectDescriptionStyle().setForegroundColor(value);
        return this;
    }
    /**
     * Setter for ShowIcon.
     *
     * @generated
     */
    public MultiSelectDescriptionStyleBuilder showIcon(java.lang.Boolean value) {
        this.getMultiSelectDescriptionStyle().setShowIcon(value);
        return this;
    }

}

