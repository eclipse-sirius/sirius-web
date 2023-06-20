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
 * Builder for ConditionalLinkDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalLinkDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle conditionalLinkDescriptionStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createConditionalLinkDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle getConditionalLinkDescriptionStyle() {
        return this.conditionalLinkDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle build() {
        return this.getConditionalLinkDescriptionStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalLinkDescriptionStyleBuilder condition(java.lang.String value) {
        this.getConditionalLinkDescriptionStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ConditionalLinkDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getConditionalLinkDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ConditionalLinkDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getConditionalLinkDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ConditionalLinkDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getConditionalLinkDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ConditionalLinkDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getConditionalLinkDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ConditionalLinkDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getConditionalLinkDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for Color.
     *
     * @generated
     */
    public ConditionalLinkDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalLinkDescriptionStyle().setColor(value);
        return this;
    }

}

