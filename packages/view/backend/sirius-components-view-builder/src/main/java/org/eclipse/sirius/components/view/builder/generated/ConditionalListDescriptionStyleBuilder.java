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
 * Builder for ConditionalListDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalListDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ConditionalListDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.ConditionalListDescriptionStyle conditionalListDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createConditionalListDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalListDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ConditionalListDescriptionStyle getConditionalListDescriptionStyle() {
        return this.conditionalListDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalListDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.ConditionalListDescriptionStyle build() {
        return this.getConditionalListDescriptionStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalListDescriptionStyleBuilder condition(java.lang.String value) {
        this.getConditionalListDescriptionStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ConditionalListDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getConditionalListDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ConditionalListDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getConditionalListDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ConditionalListDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getConditionalListDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ConditionalListDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getConditionalListDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ConditionalListDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getConditionalListDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for Color.
     *
     * @generated
     */
    public ConditionalListDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalListDescriptionStyle().setColor(value);
        return this;
    }

}

