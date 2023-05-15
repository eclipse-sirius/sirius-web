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
 * Builder for ConditionalLabelDescriptionStyleBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class ConditionalLabelDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle conditionalLabelDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createConditionalLabelDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle getConditionalLabelDescriptionStyle() {
        return this.conditionalLabelDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle build() {
        return this.getConditionalLabelDescriptionStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalLabelDescriptionStyleBuilder condition(java.lang.String value) {
        this.getConditionalLabelDescriptionStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ConditionalLabelDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getConditionalLabelDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ConditionalLabelDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getConditionalLabelDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ConditionalLabelDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getConditionalLabelDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ConditionalLabelDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getConditionalLabelDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ConditionalLabelDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getConditionalLabelDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for Color.
     *
     * @generated
     */
    public ConditionalLabelDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalLabelDescriptionStyle().setColor(value);
        return this;
    }

}

