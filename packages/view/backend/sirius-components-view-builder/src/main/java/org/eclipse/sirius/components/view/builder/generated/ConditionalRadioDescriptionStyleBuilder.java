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
 * Builder for ConditionalRadioDescriptionStyleBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class ConditionalRadioDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle conditionalRadioDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createConditionalRadioDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle getConditionalRadioDescriptionStyle() {
        return this.conditionalRadioDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle build() {
        return this.getConditionalRadioDescriptionStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalRadioDescriptionStyleBuilder condition(java.lang.String value) {
        this.getConditionalRadioDescriptionStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ConditionalRadioDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getConditionalRadioDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ConditionalRadioDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getConditionalRadioDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ConditionalRadioDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getConditionalRadioDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ConditionalRadioDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getConditionalRadioDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ConditionalRadioDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getConditionalRadioDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for Color.
     *
     * @generated
     */
    public ConditionalRadioDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalRadioDescriptionStyle().setColor(value);
        return this;
    }

}

