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
 * Builder for EdgePaletteBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class EdgePaletteBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.EdgePalette.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.EdgePalette edgePalette = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createEdgePalette();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.EdgePalette.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.EdgePalette getEdgePalette() {
        return this.edgePalette;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.EdgePalette.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.EdgePalette build() {
        return this.getEdgePalette();
    }

    /**
     * Setter for DeleteTool.
     *
     * @generated
     */
    public EdgePaletteBuilder deleteTool(org.eclipse.sirius.components.view.diagram.DeleteTool value) {
        this.getEdgePalette().setDeleteTool(value);
        return this;
    }
    /**
     * Setter for CenterLabelEditTool.
     *
     * @generated
     */
    public EdgePaletteBuilder centerLabelEditTool(org.eclipse.sirius.components.view.diagram.LabelEditTool value) {
        this.getEdgePalette().setCenterLabelEditTool(value);
        return this;
    }
    /**
     * Setter for BeginLabelEditTool.
     *
     * @generated
     */
    public EdgePaletteBuilder beginLabelEditTool(org.eclipse.sirius.components.view.diagram.LabelEditTool value) {
        this.getEdgePalette().setBeginLabelEditTool(value);
        return this;
    }
    /**
     * Setter for EndLabelEditTool.
     *
     * @generated
     */
    public EdgePaletteBuilder endLabelEditTool(org.eclipse.sirius.components.view.diagram.LabelEditTool value) {
        this.getEdgePalette().setEndLabelEditTool(value);
        return this;
    }
    /**
     * Setter for NodeTools.
     *
     * @generated
     */
    public EdgePaletteBuilder nodeTools(org.eclipse.sirius.components.view.diagram.NodeTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeTool value : values) {
            this.getEdgePalette().getNodeTools().add(value);
        }
        return this;
    }

    /**
     * Setter for EdgeReconnectionTools.
     *
     * @generated
     */
    public EdgePaletteBuilder edgeReconnectionTools(org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool value : values) {
            this.getEdgePalette().getEdgeReconnectionTools().add(value);
        }
        return this;
    }

    /**
     * Setter for ToolSections.
     *
     * @generated
     */
    public EdgePaletteBuilder toolSections(org.eclipse.sirius.components.view.diagram.EdgeToolSection ... values) {
        for (org.eclipse.sirius.components.view.diagram.EdgeToolSection value : values) {
            this.getEdgePalette().getToolSections().add(value);
        }
        return this;
    }


}

