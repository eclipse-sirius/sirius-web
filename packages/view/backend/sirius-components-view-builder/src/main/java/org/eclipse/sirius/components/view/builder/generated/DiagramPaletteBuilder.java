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
 * Builder for DiagramPaletteBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class DiagramPaletteBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.DiagramPalette.
     * @generated
     */
    private org.eclipse.sirius.components.view.DiagramPalette diagramPalette = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createDiagramPalette();

    /**
     * Return instance org.eclipse.sirius.components.view.DiagramPalette.
     * @generated
     */
    protected org.eclipse.sirius.components.view.DiagramPalette getDiagramPalette() {
        return this.diagramPalette;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.DiagramPalette.
     * @generated
     */
    public org.eclipse.sirius.components.view.DiagramPalette build() {
        return this.getDiagramPalette();
    }

    /**
     * Setter for DropTool.
     *
     * @generated
     */
    public DiagramPaletteBuilder dropTool(org.eclipse.sirius.components.view.DropTool value) {
        this.getDiagramPalette().setDropTool(value);
        return this;
    }
    /**
     * Setter for NodeTools.
     *
     * @generated
     */
    public DiagramPaletteBuilder nodeTools(org.eclipse.sirius.components.view.NodeTool ... values) {
        for (org.eclipse.sirius.components.view.NodeTool value : values) {
            this.getDiagramPalette().getNodeTools().add(value);
        }
        return this;
    }


}

