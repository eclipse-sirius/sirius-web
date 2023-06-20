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
 * Builder for BarChartDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class BarChartDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.BarChartDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.BarChartDescription barChartDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createBarChartDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.BarChartDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.BarChartDescription getBarChartDescription() {
        return this.barChartDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.BarChartDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.BarChartDescription build() {
        return this.getBarChartDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public BarChartDescriptionBuilder name(java.lang.String value) {
        this.getBarChartDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public BarChartDescriptionBuilder labelExpression(java.lang.String value) {
        this.getBarChartDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public BarChartDescriptionBuilder helpExpression(java.lang.String value) {
        this.getBarChartDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for ValuesExpression.
     *
     * @generated
     */
    public BarChartDescriptionBuilder valuesExpression(java.lang.String value) {
        this.getBarChartDescription().setValuesExpression(value);
        return this;
    }
    /**
     * Setter for KeysExpression.
     *
     * @generated
     */
    public BarChartDescriptionBuilder keysExpression(java.lang.String value) {
        this.getBarChartDescription().setKeysExpression(value);
        return this;
    }
    /**
     * Setter for YAxisLabelExpression.
     *
     * @generated
     */
    public BarChartDescriptionBuilder yAxisLabelExpression(java.lang.String value) {
        this.getBarChartDescription().setYAxisLabelExpression(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public BarChartDescriptionBuilder style(org.eclipse.sirius.components.view.form.BarChartDescriptionStyle value) {
        this.getBarChartDescription().setStyle(value);
        return this;
    }
    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public BarChartDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle value : values) {
            this.getBarChartDescription().getConditionalStyles().add(value);
        }
        return this;
    }

    /**
     * Setter for Width.
     *
     * @generated
     */
    public BarChartDescriptionBuilder width(java.lang.Integer value) {
        this.getBarChartDescription().setWidth(value);
        return this;
    }
    /**
     * Setter for Height.
     *
     * @generated
     */
    public BarChartDescriptionBuilder height(java.lang.Integer value) {
        this.getBarChartDescription().setHeight(value);
        return this;
    }

}

