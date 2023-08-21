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
 * Builder for NodeToolSectionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class NodeToolSectionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.NodeToolSection.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.NodeToolSection nodeToolSection = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createNodeToolSection();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodeToolSection.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.NodeToolSection getNodeToolSection() {
        return this.nodeToolSection;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodeToolSection.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.NodeToolSection build() {
        return this.getNodeToolSection();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public NodeToolSectionBuilder name(java.lang.String value) {
        this.getNodeToolSection().setName(value);
        return this;
    }
    /**
     * Setter for NodeTools.
     *
     * @generated
     */
    public NodeToolSectionBuilder nodeTools(org.eclipse.sirius.components.view.diagram.NodeTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeTool value : values) {
            this.getNodeToolSection().getNodeTools().add(value);
        }
        return this;
    }

    /**
     * Setter for EdgeTools.
     *
     * @generated
     */
    public NodeToolSectionBuilder edgeTools(org.eclipse.sirius.components.view.diagram.EdgeTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.EdgeTool value : values) {
            this.getNodeToolSection().getEdgeTools().add(value);
        }
        return this;
    }


}

