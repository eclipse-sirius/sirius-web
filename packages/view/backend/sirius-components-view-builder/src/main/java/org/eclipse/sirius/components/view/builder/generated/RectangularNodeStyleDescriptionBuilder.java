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
 * Builder for RectangularNodeStyleDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class RectangularNodeStyleDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription rectangularNodeStyleDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription getRectangularNodeStyleDescription() {
        return this.rectangularNodeStyleDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription build() {
        return this.getRectangularNodeStyleDescription();
    }

    /**
     * Setter for Color.
     *
     * @generated
     */
    public RectangularNodeStyleDescriptionBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getRectangularNodeStyleDescription().setColor(value);
        return this;
    }

    /**
     * Setter for BorderColor.
     *
     * @generated
     */
    public RectangularNodeStyleDescriptionBuilder borderColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getRectangularNodeStyleDescription().setBorderColor(value);
        return this;
    }

    /**
     * Setter for BorderRadius.
     *
     * @generated
     */
    public RectangularNodeStyleDescriptionBuilder borderRadius(java.lang.Integer value) {
        this.getRectangularNodeStyleDescription().setBorderRadius(value);
        return this;
    }

    /**
     * Setter for BorderSize.
     *
     * @generated
     */
    public RectangularNodeStyleDescriptionBuilder borderSize(java.lang.Integer value) {
        this.getRectangularNodeStyleDescription().setBorderSize(value);
        return this;
    }

    /**
     * Setter for BorderLineStyle.
     *
     * @generated
     */
    public RectangularNodeStyleDescriptionBuilder borderLineStyle(org.eclipse.sirius.components.view.diagram.LineStyle value) {
        this.getRectangularNodeStyleDescription().setBorderLineStyle(value);
        return this;
    }

}

