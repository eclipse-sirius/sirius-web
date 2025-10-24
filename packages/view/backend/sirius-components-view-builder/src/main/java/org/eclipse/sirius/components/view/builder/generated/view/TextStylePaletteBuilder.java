/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated.view;

/**
 * Builder for TextStylePaletteBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TextStylePaletteBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.TextStylePalette.
     * @generated
     */
    private org.eclipse.sirius.components.view.TextStylePalette textStylePalette = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createTextStylePalette();

    /**
     * Return instance org.eclipse.sirius.components.view.TextStylePalette.
     * @generated
     */
    protected org.eclipse.sirius.components.view.TextStylePalette getTextStylePalette() {
        return this.textStylePalette;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.TextStylePalette.
     * @generated
     */
    public org.eclipse.sirius.components.view.TextStylePalette build() {
        return this.getTextStylePalette();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TextStylePaletteBuilder name(java.lang.String value) {
        this.getTextStylePalette().setName(value);
        return this;
    }
    /**
     * Setter for Styles.
     *
     * @generated
     */
    public TextStylePaletteBuilder styles(org.eclipse.sirius.components.view.TextStyleDescription ... values) {
        for (org.eclipse.sirius.components.view.TextStyleDescription value : values) {
            this.getTextStylePalette().getStyles().add(value);
        }
        return this;
    }


}

