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
 * Builder for DateTimeDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DateTimeDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle dateTimeDescriptionStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createDateTimeDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle getDateTimeDescriptionStyle() {
        return this.dateTimeDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle build() {
        return this.getDateTimeDescriptionStyle();
    }

    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public DateTimeDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getDateTimeDescriptionStyle().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for ForegroundColor.
     *
     * @generated
     */
    public DateTimeDescriptionStyleBuilder foregroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getDateTimeDescriptionStyle().setForegroundColor(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public DateTimeDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getDateTimeDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public DateTimeDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getDateTimeDescriptionStyle().setBold(value);
        return this;
    }

}

