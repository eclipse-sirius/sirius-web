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
 * Builder for ImageDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ImageDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ImageDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.ImageDescription imageDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createImageDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.ImageDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ImageDescription getImageDescription() {
        return this.imageDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ImageDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.ImageDescription build() {
        return this.getImageDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public ImageDescriptionBuilder name(java.lang.String value) {
        this.getImageDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public ImageDescriptionBuilder labelExpression(java.lang.String value) {
        this.getImageDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public ImageDescriptionBuilder helpExpression(java.lang.String value) {
        this.getImageDescription().setHelpExpression(value);
        return this;
    }

    /**
     * Setter for UrlExpression.
     *
     * @generated
     */
    public ImageDescriptionBuilder urlExpression(java.lang.String value) {
        this.getImageDescription().setUrlExpression(value);
        return this;
    }
    /**
     * Setter for MaxWidthExpression.
     *
     * @generated
     */
    public ImageDescriptionBuilder maxWidthExpression(java.lang.String value) {
        this.getImageDescription().setMaxWidthExpression(value);
        return this;
    }

}

