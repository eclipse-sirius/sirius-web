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
 * Builder for ConditionalSelectDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalSelectDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle conditionalSelectDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createConditionalSelectDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle getConditionalSelectDescriptionStyle() {
        return this.conditionalSelectDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle build() {
        return this.getConditionalSelectDescriptionStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalSelectDescriptionStyleBuilder condition(java.lang.String value) {
        this.getConditionalSelectDescriptionStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ConditionalSelectDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getConditionalSelectDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ConditionalSelectDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getConditionalSelectDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ConditionalSelectDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getConditionalSelectDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ConditionalSelectDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getConditionalSelectDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ConditionalSelectDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getConditionalSelectDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public ConditionalSelectDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalSelectDescriptionStyle().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for ForegroundColor.
     *
     * @generated
     */
    public ConditionalSelectDescriptionStyleBuilder foregroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalSelectDescriptionStyle().setForegroundColor(value);
        return this;
    }

}

