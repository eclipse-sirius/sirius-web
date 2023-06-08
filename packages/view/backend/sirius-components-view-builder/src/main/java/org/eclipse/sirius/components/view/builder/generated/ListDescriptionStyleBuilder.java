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
 * Builder for ListDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ListDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ListDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.ListDescriptionStyle listDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createListDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.ListDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ListDescriptionStyle getListDescriptionStyle() {
        return this.listDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ListDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.ListDescriptionStyle build() {
        return this.getListDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ListDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getListDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ListDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getListDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ListDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getListDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ListDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getListDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ListDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getListDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for Color.
     *
     * @generated
     */
    public ListDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getListDescriptionStyle().setColor(value);
        return this;
    }

}

