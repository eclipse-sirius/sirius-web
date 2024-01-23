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
 * Builder for SliderDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class SliderDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.SliderDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.SliderDescription sliderDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createSliderDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.SliderDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.SliderDescription getSliderDescription() {
        return this.sliderDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.SliderDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.SliderDescription build() {
        return this.getSliderDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public SliderDescriptionBuilder name(java.lang.String value) {
        this.getSliderDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public SliderDescriptionBuilder labelExpression(java.lang.String value) {
        this.getSliderDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public SliderDescriptionBuilder helpExpression(java.lang.String value) {
        this.getSliderDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for MinValueExpression.
     *
     * @generated
     */
    public SliderDescriptionBuilder minValueExpression(java.lang.String value) {
        this.getSliderDescription().setMinValueExpression(value);
        return this;
    }
    /**
     * Setter for MaxValueExpression.
     *
     * @generated
     */
    public SliderDescriptionBuilder maxValueExpression(java.lang.String value) {
        this.getSliderDescription().setMaxValueExpression(value);
        return this;
    }
    /**
     * Setter for CurrentValueExpression.
     *
     * @generated
     */
    public SliderDescriptionBuilder currentValueExpression(java.lang.String value) {
        this.getSliderDescription().setCurrentValueExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public SliderDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getSliderDescription().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for IsEnabledExpression.
     *
     * @generated
     */
    public SliderDescriptionBuilder isEnabledExpression(java.lang.String value) {
        this.getSliderDescription().setIsEnabledExpression(value);
        return this;
    }

}

