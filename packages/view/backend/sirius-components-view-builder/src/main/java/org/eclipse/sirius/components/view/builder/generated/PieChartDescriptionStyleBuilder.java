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
 * Builder for PieChartDescriptionStyleBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class PieChartDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.PieChartDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.PieChartDescriptionStyle pieChartDescriptionStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createPieChartDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.PieChartDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.PieChartDescriptionStyle getPieChartDescriptionStyle() {
        return this.pieChartDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.PieChartDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.PieChartDescriptionStyle build() {
        return this.getPieChartDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public PieChartDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getPieChartDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public PieChartDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getPieChartDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public PieChartDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getPieChartDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public PieChartDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getPieChartDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public PieChartDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getPieChartDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for Colors.
     *
     * @generated
     */
    public PieChartDescriptionStyleBuilder colors(java.lang.String value) {
        this.getPieChartDescriptionStyle().setColors(value);
        return this;
    }
    /**
     * Setter for StrokeWidth.
     *
     * @generated
     */
    public PieChartDescriptionStyleBuilder strokeWidth(java.lang.Integer value) {
        this.getPieChartDescriptionStyle().setStrokeWidth(value);
        return this;
    }
    /**
     * Setter for StrokeColor.
     *
     * @generated
     */
    public PieChartDescriptionStyleBuilder strokeColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getPieChartDescriptionStyle().setStrokeColor(value);
        return this;
    }

}

