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
 * Builder for ImageNodeStyleDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ImageNodeStyleDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription imageNodeStyleDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createImageNodeStyleDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription getImageNodeStyleDescription() {
        return this.imageNodeStyleDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription build() {
        return this.getImageNodeStyleDescription();
    }

    /**
     * Setter for Color.
     *
     * @generated
     */
    public ImageNodeStyleDescriptionBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getImageNodeStyleDescription().setColor(value);
        return this;
    }

    /**
     * Setter for BorderColor.
     *
     * @generated
     */
    public ImageNodeStyleDescriptionBuilder borderColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getImageNodeStyleDescription().setBorderColor(value);
        return this;
    }

    /**
     * Setter for BorderRadius.
     *
     * @generated
     */
    public ImageNodeStyleDescriptionBuilder borderRadius(java.lang.Integer value) {
        this.getImageNodeStyleDescription().setBorderRadius(value);
        return this;
    }

    /**
     * Setter for BorderSize.
     *
     * @generated
     */
    public ImageNodeStyleDescriptionBuilder borderSize(java.lang.Integer value) {
        this.getImageNodeStyleDescription().setBorderSize(value);
        return this;
    }

    /**
     * Setter for BorderLineStyle.
     *
     * @generated
     */
    public ImageNodeStyleDescriptionBuilder borderLineStyle(org.eclipse.sirius.components.view.diagram.LineStyle value) {
        this.getImageNodeStyleDescription().setBorderLineStyle(value);
        return this;
    }

    /**
     * Setter for Shape.
     *
     * @generated
     */
    public ImageNodeStyleDescriptionBuilder shape(java.lang.String value) {
        this.getImageNodeStyleDescription().setShape(value);
        return this;
    }

    /**
     * Setter for PositionDependentRotation.
     *
     * @generated
     */
    public ImageNodeStyleDescriptionBuilder positionDependentRotation(java.lang.Boolean value) {
        this.getImageNodeStyleDescription().setPositionDependentRotation(value);
        return this;
    }

}

