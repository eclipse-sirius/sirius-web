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
 * Builder for ViewBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ViewBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.View.
     * @generated
     */
    private org.eclipse.sirius.components.view.View view = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createView();

    /**
     * Return instance org.eclipse.sirius.components.view.View.
     * @generated
     */
    protected org.eclipse.sirius.components.view.View getView() {
        return this.view;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.View.
     * @generated
     */
    public org.eclipse.sirius.components.view.View build() {
        return this.getView();
    }

    /**
     * Setter for Descriptions.
     *
     * @generated
     */
    public ViewBuilder descriptions(org.eclipse.sirius.components.view.RepresentationDescription ... values) {
        for (org.eclipse.sirius.components.view.RepresentationDescription value : values) {
            this.getView().getDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for ColorPalettes.
     *
     * @generated
     */
    public ViewBuilder colorPalettes(org.eclipse.sirius.components.view.ColorPalette ... values) {
        for (org.eclipse.sirius.components.view.ColorPalette value : values) {
            this.getView().getColorPalettes().add(value);
        }
        return this;
    }


}

