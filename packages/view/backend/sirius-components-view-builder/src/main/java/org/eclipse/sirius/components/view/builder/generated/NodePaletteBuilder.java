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
 * Builder for NodePaletteBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class NodePaletteBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.NodePalette.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.NodePalette nodePalette = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createNodePalette();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodePalette.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.NodePalette getNodePalette() {
        return this.nodePalette;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodePalette.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.NodePalette build() {
        return this.getNodePalette();
    }

    /**
     * Setter for DeleteTool.
     *
     * @generated
     */
    public NodePaletteBuilder deleteTool(org.eclipse.sirius.components.view.diagram.DeleteTool value) {
        this.getNodePalette().setDeleteTool(value);
        return this;
    }
    /**
     * Setter for LabelEditTool.
     *
     * @generated
     */
    public NodePaletteBuilder labelEditTool(org.eclipse.sirius.components.view.diagram.LabelEditTool value) {
        this.getNodePalette().setLabelEditTool(value);
        return this;
    }
    /**
     * Setter for NodeTools.
     *
     * @generated
     */
    public NodePaletteBuilder nodeTools(org.eclipse.sirius.components.view.diagram.NodeTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeTool value : values) {
            this.getNodePalette().getNodeTools().add(value);
        }
        return this;
    }

    /**
     * Setter for EdgeTools.
     *
     * @generated
     */
    public NodePaletteBuilder edgeTools(org.eclipse.sirius.components.view.diagram.EdgeTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.EdgeTool value : values) {
            this.getNodePalette().getEdgeTools().add(value);
        }
        return this;
    }

    /**
     * Setter for ToolSections.
     *
     * @generated
     */
    public NodePaletteBuilder toolSections(org.eclipse.sirius.components.view.diagram.NodeToolSection ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeToolSection value : values) {
            this.getNodePalette().getToolSections().add(value);
        }
        return this;
    }


}

