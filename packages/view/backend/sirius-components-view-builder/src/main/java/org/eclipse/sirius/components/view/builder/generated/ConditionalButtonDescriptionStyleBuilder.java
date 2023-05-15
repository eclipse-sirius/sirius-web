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
 * Builder for ConditionalButtonDescriptionStyleBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class ConditionalButtonDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle conditionalButtonDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createConditionalButtonDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle getConditionalButtonDescriptionStyle() {
        return this.conditionalButtonDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle build() {
        return this.getConditionalButtonDescriptionStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalButtonDescriptionStyleBuilder condition(java.lang.String value) {
        this.getConditionalButtonDescriptionStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ConditionalButtonDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getConditionalButtonDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ConditionalButtonDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getConditionalButtonDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ConditionalButtonDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getConditionalButtonDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ConditionalButtonDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getConditionalButtonDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ConditionalButtonDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getConditionalButtonDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public ConditionalButtonDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalButtonDescriptionStyle().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for ForegroundColor.
     *
     * @generated
     */
    public ConditionalButtonDescriptionStyleBuilder foregroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalButtonDescriptionStyle().setForegroundColor(value);
        return this;
    }

}

