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
 * Builder for ContainerBorderStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ContainerBorderStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.ContainerBorderStyle.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.form.ContainerBorderStyle containerBorderStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createContainerBorderStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.ContainerBorderStyle.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.ContainerBorderStyle getContainerBorderStyle() {
        return this.containerBorderStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.ContainerBorderStyle.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.form.ContainerBorderStyle build() {
        return this.getContainerBorderStyle();
    }

    /**
     * Setter for BorderColor.
     *
     * @generated
     */
    public ContainerBorderStyleBuilder borderColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getContainerBorderStyle().setBorderColor(value);
        return this;
    }
    /**
     * Setter for BorderRadius.
     *
     * @generated
     */
    public ContainerBorderStyleBuilder borderRadius(java.lang.Integer value) {
        this.getContainerBorderStyle().setBorderRadius(value);
        return this;
    }
    /**
     * Setter for BorderSize.
     *
     * @generated
     */
    public ContainerBorderStyleBuilder borderSize(java.lang.Integer value) {
        this.getContainerBorderStyle().setBorderSize(value);
        return this;
    }

    /**
     * Setter for BorderLineStyle.
     *
     * @generated
     */
    public ContainerBorderStyleBuilder borderLineStyle(org.eclipse.sirius.components.view.form.ContainerBorderLineStyle value) {
        this.getContainerBorderStyle().setBorderLineStyle(value);
        return this;
    }

}

