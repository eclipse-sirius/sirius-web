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
 * Builder for BarChartDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class BarChartDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.BarChartDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.BarChartDescriptionStyle barChartDescriptionStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createBarChartDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.BarChartDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.BarChartDescriptionStyle getBarChartDescriptionStyle() {
        return this.barChartDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.BarChartDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.BarChartDescriptionStyle build() {
        return this.getBarChartDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public BarChartDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getBarChartDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public BarChartDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getBarChartDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public BarChartDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getBarChartDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public BarChartDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getBarChartDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public BarChartDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getBarChartDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BarsColor.
     *
     * @generated
     */
    public BarChartDescriptionStyleBuilder barsColor(java.lang.String value) {
        this.getBarChartDescriptionStyle().setBarsColor(value);
        return this;
    }

}

