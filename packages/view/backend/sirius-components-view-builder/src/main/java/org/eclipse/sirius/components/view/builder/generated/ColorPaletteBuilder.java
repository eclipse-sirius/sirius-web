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
 * Builder for ColorPaletteBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class ColorPaletteBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ColorPalette.
     * @generated
     */
    private org.eclipse.sirius.components.view.ColorPalette colorPalette = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createColorPalette();

    /**
     * Return instance org.eclipse.sirius.components.view.ColorPalette.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ColorPalette getColorPalette() {
        return this.colorPalette;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ColorPalette.
     * @generated
     */
    public org.eclipse.sirius.components.view.ColorPalette build() {
        return this.getColorPalette();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public ColorPaletteBuilder name(java.lang.String value) {
        this.getColorPalette().setName(value);
        return this;
    }
    /**
     * Setter for Colors.
     *
     * @generated
     */
    public ColorPaletteBuilder colors(org.eclipse.sirius.components.view.UserColor ... values) {
        for (org.eclipse.sirius.components.view.UserColor value : values) {
            this.getColorPalette().getColors().add(value);
        }
        return this;
    }


}

