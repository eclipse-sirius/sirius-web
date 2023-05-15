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
 * Builder for ConditionalTextareaDescriptionStyleBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class ConditionalTextareaDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle conditionalTextareaDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createConditionalTextareaDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle getConditionalTextareaDescriptionStyle() {
        return this.conditionalTextareaDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle build() {
        return this.getConditionalTextareaDescriptionStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalTextareaDescriptionStyleBuilder condition(java.lang.String value) {
        this.getConditionalTextareaDescriptionStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ConditionalTextareaDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getConditionalTextareaDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ConditionalTextareaDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getConditionalTextareaDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ConditionalTextareaDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getConditionalTextareaDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ConditionalTextareaDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getConditionalTextareaDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ConditionalTextareaDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getConditionalTextareaDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public ConditionalTextareaDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalTextareaDescriptionStyle().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for ForegroundColor.
     *
     * @generated
     */
    public ConditionalTextareaDescriptionStyleBuilder foregroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalTextareaDescriptionStyle().setForegroundColor(value);
        return this;
    }

}

