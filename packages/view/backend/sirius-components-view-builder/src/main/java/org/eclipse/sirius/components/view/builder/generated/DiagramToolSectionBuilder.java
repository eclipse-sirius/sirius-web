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
 * Builder for DiagramToolSectionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DiagramToolSectionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.DiagramToolSection.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.DiagramToolSection diagramToolSection = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createDiagramToolSection();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DiagramToolSection.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.DiagramToolSection getDiagramToolSection() {
        return this.diagramToolSection;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DiagramToolSection.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.DiagramToolSection build() {
        return this.getDiagramToolSection();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DiagramToolSectionBuilder name(java.lang.String value) {
        this.getDiagramToolSection().setName(value);
        return this;
    }
    /**
     * Setter for NodeTools.
     *
     * @generated
     */
    public DiagramToolSectionBuilder nodeTools(org.eclipse.sirius.components.view.diagram.NodeTool ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeTool value : values) {
            this.getDiagramToolSection().getNodeTools().add(value);
        }
        return this;
    }


}

