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
 * Builder for EdgeToolSectionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class EdgeToolSectionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.EdgeToolSection.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.EdgeToolSection edgeToolSection = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createEdgeToolSection();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.EdgeToolSection.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.EdgeToolSection getEdgeToolSection() {
        return this.edgeToolSection;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.EdgeToolSection.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.EdgeToolSection build() {
        return this.getEdgeToolSection();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public EdgeToolSectionBuilder name(java.lang.String value) {
        this.getEdgeToolSection().setName(value);
        return this;
    }
    /**
     * Setter for NodeTools.
     *
     * @generated
     */
    public EdgeToolSectionBuilder nodeTools(org.eclipse.sirius.components.view.diagram.NodeTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeTool value : values) {
            this.getEdgeToolSection().getNodeTools().add(value);
        }
        return this;
    }


}

