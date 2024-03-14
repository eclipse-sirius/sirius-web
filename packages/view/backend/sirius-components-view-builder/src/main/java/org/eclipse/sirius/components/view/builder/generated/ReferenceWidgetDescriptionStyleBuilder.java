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
 * Builder for ReferenceWidgetDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ReferenceWidgetDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle referenceWidgetDescriptionStyle = org.eclipse.sirius.components.widgets.reference.ReferenceFactory.eINSTANCE.createReferenceWidgetDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle getReferenceWidgetDescriptionStyle() {
        return this.referenceWidgetDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle build() {
        return this.getReferenceWidgetDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getReferenceWidgetDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getReferenceWidgetDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getReferenceWidgetDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getReferenceWidgetDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getReferenceWidgetDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for Color.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getReferenceWidgetDescriptionStyle().setColor(value);
        return this;
    }

}

