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
 * Builder for EdgeToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class EdgeToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.EdgeTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.EdgeTool edgeTool = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createEdgeTool();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.EdgeTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.EdgeTool getEdgeTool() {
        return this.edgeTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.EdgeTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.EdgeTool build() {
        return this.getEdgeTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public EdgeToolBuilder name(java.lang.String value) {
        this.getEdgeTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public EdgeToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getEdgeTool().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for TargetElementDescriptions.
     *
     * @generated
     */
    public EdgeToolBuilder targetElementDescriptions(org.eclipse.sirius.components.view.diagram.DiagramElementDescription ... values) {
        for (org.eclipse.sirius.components.view.diagram.DiagramElementDescription value : values) {
            this.getEdgeTool().getTargetElementDescriptions().add(value);
        }
        return this;
    }


}

