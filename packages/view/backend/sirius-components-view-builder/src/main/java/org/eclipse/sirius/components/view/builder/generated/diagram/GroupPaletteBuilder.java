/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated.diagram;

/**
 * Builder for GroupPaletteBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class GroupPaletteBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.GroupPalette.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.GroupPalette groupPalette = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createGroupPalette();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.GroupPalette.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.GroupPalette getGroupPalette() {
        return this.groupPalette;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.GroupPalette.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.GroupPalette build() {
        return this.getGroupPalette();
    }

    /**
     * Setter for NodeTools.
     *
     * @generated
     */
    public GroupPaletteBuilder nodeTools(org.eclipse.sirius.components.view.diagram.NodeTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeTool value : values) {
            this.getGroupPalette().getNodeTools().add(value);
        }
        return this;
    }

    /**
     * Setter for QuickAccessTools.
     *
     * @generated
     */
    public GroupPaletteBuilder quickAccessTools(org.eclipse.sirius.components.view.diagram.NodeTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeTool value : values) {
            this.getGroupPalette().getQuickAccessTools().add(value);
        }
        return this;
    }

    /**
     * Setter for ToolSections.
     *
     * @generated
     */
    public GroupPaletteBuilder toolSections(org.eclipse.sirius.components.view.diagram.ToolSection ... values) {
        for (org.eclipse.sirius.components.view.diagram.ToolSection value : values) {
            this.getGroupPalette().getToolSections().add(value);
        }
        return this;
    }


}

