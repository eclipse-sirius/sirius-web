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
 * Builder for InsideLabelStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class InsideLabelStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.InsideLabelStyle.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.diagram.InsideLabelStyle insideLabelStyle = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createInsideLabelStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.InsideLabelStyle.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.InsideLabelStyle getInsideLabelStyle() {
        return this.insideLabelStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.InsideLabelStyle.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.InsideLabelStyle build() {
        return this.getInsideLabelStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public InsideLabelStyleBuilder fontSize(java.lang.Integer value) {
        this.getInsideLabelStyle().setFontSize(value);
        return this;
    }

    /**
     * Setter for Italic.
     *
     * @generated
     */
    public InsideLabelStyleBuilder italic(java.lang.Boolean value) {
        this.getInsideLabelStyle().setItalic(value);
        return this;
    }

    /**
     * Setter for Bold.
     *
     * @generated
     */
    public InsideLabelStyleBuilder bold(java.lang.Boolean value) {
        this.getInsideLabelStyle().setBold(value);
        return this;
    }

    /**
     * Setter for Underline.
     *
     * @generated
     */
    public InsideLabelStyleBuilder underline(java.lang.Boolean value) {
        this.getInsideLabelStyle().setUnderline(value);
        return this;
    }

    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public InsideLabelStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getInsideLabelStyle().setStrikeThrough(value);
        return this;
    }

    /**
     * Setter for LabelColor.
     *
     * @generated
     */
    public InsideLabelStyleBuilder labelColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getInsideLabelStyle().setLabelColor(value);
        return this;
    }

    /**
     * Setter for ShowIcon.
     *
     * @generated
     */
    public InsideLabelStyleBuilder showIcon(java.lang.Boolean value) {
        this.getInsideLabelStyle().setShowIcon(value);
        return this;
    }

    /**
     * Setter for LabelIcon.
     *
     * @generated
     */
    public InsideLabelStyleBuilder labelIcon(java.lang.String value) {
        this.getInsideLabelStyle().setLabelIcon(value);
        return this;
    }

    /**
     * Setter for WithHeader.
     *
     * @generated
     */
    public InsideLabelStyleBuilder withHeader(java.lang.Boolean value) {
        this.getInsideLabelStyle().setWithHeader(value);
        return this;
    }

    /**
     * Setter for DisplayHeaderSeparator.
     *
     * @generated
     */
    public InsideLabelStyleBuilder displayHeaderSeparator(java.lang.Boolean value) {
        this.getInsideLabelStyle().setDisplayHeaderSeparator(value);
        return this;
    }

}

