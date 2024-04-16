/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
 * Builder for ConditionalDateTimeDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalDateTimeDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle conditionalDateTimeDescriptionStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createConditionalDateTimeDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle getConditionalDateTimeDescriptionStyle() {
        return this.conditionalDateTimeDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle build() {
        return this.getConditionalDateTimeDescriptionStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalDateTimeDescriptionStyleBuilder condition(java.lang.String value) {
        this.getConditionalDateTimeDescriptionStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public ConditionalDateTimeDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalDateTimeDescriptionStyle().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for ForegroundColor.
     *
     * @generated
     */
    public ConditionalDateTimeDescriptionStyleBuilder foregroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalDateTimeDescriptionStyle().setForegroundColor(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ConditionalDateTimeDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getConditionalDateTimeDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ConditionalDateTimeDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getConditionalDateTimeDescriptionStyle().setBold(value);
        return this;
    }

}

