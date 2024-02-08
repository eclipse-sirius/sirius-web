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
 * Builder for IconLabelNodeStyleDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class IconLabelNodeStyleDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription iconLabelNodeStyleDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createIconLabelNodeStyleDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription getIconLabelNodeStyleDescription() {
        return this.iconLabelNodeStyleDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription build() {
        return this.getIconLabelNodeStyleDescription();
    }

    /**
     * Setter for Color.
     *
     * @generated
     */
    public IconLabelNodeStyleDescriptionBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getIconLabelNodeStyleDescription().setColor(value);
        return this;
    }

    /**
     * Setter for BorderColor.
     *
     * @generated
     */
    public IconLabelNodeStyleDescriptionBuilder borderColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getIconLabelNodeStyleDescription().setBorderColor(value);
        return this;
    }

    /**
     * Setter for BorderRadius.
     *
     * @generated
     */
    public IconLabelNodeStyleDescriptionBuilder borderRadius(java.lang.Integer value) {
        this.getIconLabelNodeStyleDescription().setBorderRadius(value);
        return this;
    }

    /**
     * Setter for BorderSize.
     *
     * @generated
     */
    public IconLabelNodeStyleDescriptionBuilder borderSize(java.lang.Integer value) {
        this.getIconLabelNodeStyleDescription().setBorderSize(value);
        return this;
    }

    /**
     * Setter for BorderLineStyle.
     *
     * @generated
     */
    public IconLabelNodeStyleDescriptionBuilder borderLineStyle(org.eclipse.sirius.components.view.diagram.LineStyle value) {
        this.getIconLabelNodeStyleDescription().setBorderLineStyle(value);
        return this;
    }

}

