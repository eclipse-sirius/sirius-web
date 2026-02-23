/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
 * Builder for DiagramToolbarBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DiagramToolbarBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.DiagramToolbar.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.DiagramToolbar diagramToolbar = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createDiagramToolbar();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DiagramToolbar.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.DiagramToolbar getDiagramToolbar() {
        return this.diagramToolbar;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DiagramToolbar.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.DiagramToolbar build() {
        return this.getDiagramToolbar();
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public DiagramToolbarBuilder preconditionExpression(java.lang.String value) {
        this.getDiagramToolbar().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for ExpandedByDefault.
     *
     * @generated
     */
    public DiagramToolbarBuilder expandedByDefault(java.lang.Boolean value) {
        this.getDiagramToolbar().setExpandedByDefault(value);
        return this;
    }

}

