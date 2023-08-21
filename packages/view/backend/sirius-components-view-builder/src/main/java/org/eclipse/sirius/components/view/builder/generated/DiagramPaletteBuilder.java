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
 *
 * @author BuilderGenerator
 * @generated
 */
public class DiagramPaletteBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.DiagramPalette.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.DiagramPalette diagramPalette = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createDiagramPalette();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DiagramPalette.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.DiagramPalette getDiagramPalette() {
        return this.diagramPalette;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DiagramPalette.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.DiagramPalette build() {
        return this.getDiagramPalette();
    }

    /**
     * Setter for DropTool.
     *
     * @generated
     */
    public DiagramPaletteBuilder dropTool(org.eclipse.sirius.components.view.diagram.DropTool value) {
        this.getDiagramPalette().setDropTool(value);
        return this;
    }
    /**
     * Setter for NodeTools.
     *
     * @generated
     */
    public DiagramPaletteBuilder nodeTools(org.eclipse.sirius.components.view.diagram.NodeTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeTool value : values) {
            this.getDiagramPalette().getNodeTools().add(value);
        }
        return this;
    }

    /**
     * Setter for ToolSections.
     *
     * @generated
     */
    public DiagramPaletteBuilder toolSections(org.eclipse.sirius.components.view.diagram.DiagramToolSection ... values) {
        for (org.eclipse.sirius.components.view.diagram.DiagramToolSection value : values) {
            this.getDiagramPalette().getToolSections().add(value);
        }
        return this;
    }


}

