/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
 * Builder for OutsideLabelStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class OutsideLabelStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.OutsideLabelStyle.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.diagram.OutsideLabelStyle outsideLabelStyle = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createOutsideLabelStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.OutsideLabelStyle.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.OutsideLabelStyle getOutsideLabelStyle() {
        return this.outsideLabelStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.OutsideLabelStyle.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.OutsideLabelStyle build() {
        return this.getOutsideLabelStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public OutsideLabelStyleBuilder fontSize(java.lang.Integer value) {
        this.getOutsideLabelStyle().setFontSize(value);
        return this;
    }

    /**
     * Setter for Italic.
     *
     * @generated
     */
    public OutsideLabelStyleBuilder italic(java.lang.Boolean value) {
        this.getOutsideLabelStyle().setItalic(value);
        return this;
    }

    /**
     * Setter for Bold.
     *
     * @generated
     */
    public OutsideLabelStyleBuilder bold(java.lang.Boolean value) {
        this.getOutsideLabelStyle().setBold(value);
        return this;
    }

    /**
     * Setter for Underline.
     *
     * @generated
     */
    public OutsideLabelStyleBuilder underline(java.lang.Boolean value) {
        this.getOutsideLabelStyle().setUnderline(value);
        return this;
    }

    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public OutsideLabelStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getOutsideLabelStyle().setStrikeThrough(value);
        return this;
    }

    /**
     * Setter for LabelColor.
     *
     * @generated
     */
    public OutsideLabelStyleBuilder labelColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getOutsideLabelStyle().setLabelColor(value);
        return this;
    }

    /**
     * Setter for ShowIcon.
     *
     * @generated
     */
    public OutsideLabelStyleBuilder showIcon(java.lang.Boolean value) {
        this.getOutsideLabelStyle().setShowIcon(value);
        return this;
    }

    /**
     * Setter for LabelIcon.
     *
     * @generated
     */
    public OutsideLabelStyleBuilder labelIcon(java.lang.String value) {
        this.getOutsideLabelStyle().setLabelIcon(value);
        return this;
    }

}

