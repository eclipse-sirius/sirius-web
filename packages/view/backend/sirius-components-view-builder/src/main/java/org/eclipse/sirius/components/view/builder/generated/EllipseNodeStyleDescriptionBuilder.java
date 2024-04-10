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
 * Builder for EllipseNodeStyleDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class EllipseNodeStyleDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription ellipseNodeStyleDescription = org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesFactory.eINSTANCE.createEllipseNodeStyleDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription getEllipseNodeStyleDescription() {
        return this.ellipseNodeStyleDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription build() {
        return this.getEllipseNodeStyleDescription();
    }

    /**
     * Setter for Color.
     *
     * @generated
     */
    public EllipseNodeStyleDescriptionBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getEllipseNodeStyleDescription().setColor(value);
        return this;
    }
    /**
     * Setter for BorderColor.
     *
     * @generated
     */
    public EllipseNodeStyleDescriptionBuilder borderColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getEllipseNodeStyleDescription().setBorderColor(value);
        return this;
    }
    /**
     * Setter for BorderRadius.
     *
     * @generated
     */
    public EllipseNodeStyleDescriptionBuilder borderRadius(java.lang.Integer value) {
        this.getEllipseNodeStyleDescription().setBorderRadius(value);
        return this;
    }
    /**
     * Setter for BorderSize.
     *
     * @generated
     */
    public EllipseNodeStyleDescriptionBuilder borderSize(java.lang.Integer value) {
        this.getEllipseNodeStyleDescription().setBorderSize(value);
        return this;
    }
    /**
     * Setter for BorderLineStyle.
     *
     * @generated
     */
    public EllipseNodeStyleDescriptionBuilder borderLineStyle(org.eclipse.sirius.components.view.diagram.LineStyle value) {
        this.getEllipseNodeStyleDescription().setBorderLineStyle(value);
        return this;
    }

}

