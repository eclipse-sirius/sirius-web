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
 * Builder for LinkDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class LinkDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.LinkDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.LinkDescriptionStyle linkDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createLinkDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.LinkDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.LinkDescriptionStyle getLinkDescriptionStyle() {
        return this.linkDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.LinkDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.LinkDescriptionStyle build() {
        return this.getLinkDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public LinkDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getLinkDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public LinkDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getLinkDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public LinkDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getLinkDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public LinkDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getLinkDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public LinkDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getLinkDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for Color.
     *
     * @generated
     */
    public LinkDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getLinkDescriptionStyle().setColor(value);
        return this;
    }

}

