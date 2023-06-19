/**
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
 */
package org.eclipse.sirius.components.view.builder.generated;

/**
 * Builder for ConditionalContainerBorderStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalContainerBorderStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle conditionalContainerBorderStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createConditionalContainerBorderStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle getConditionalContainerBorderStyle() {
        return this.conditionalContainerBorderStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle build() {
        return this.getConditionalContainerBorderStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalContainerBorderStyleBuilder condition(java.lang.String value) {
        this.getConditionalContainerBorderStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for BorderColor.
     *
     * @generated
     */
    public ConditionalContainerBorderStyleBuilder borderColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalContainerBorderStyle().setBorderColor(value);
        return this;
    }
    /**
     * Setter for BorderRadius.
     *
     * @generated
     */
    public ConditionalContainerBorderStyleBuilder borderRadius(java.lang.Integer value) {
        this.getConditionalContainerBorderStyle().setBorderRadius(value);
        return this;
    }
    /**
     * Setter for BorderSize.
     *
     * @generated
     */
    public ConditionalContainerBorderStyleBuilder borderSize(java.lang.Integer value) {
        this.getConditionalContainerBorderStyle().setBorderSize(value);
        return this;
    }

    /**
     * Setter for BorderLineStyle.
     *
     * @generated
     */
    public ConditionalContainerBorderStyleBuilder borderLineStyle(org.eclipse.sirius.components.view.form.ContainerBorderLineStyle value) {
        this.getConditionalContainerBorderStyle().setBorderLineStyle(value);
        return this;
    }

}

