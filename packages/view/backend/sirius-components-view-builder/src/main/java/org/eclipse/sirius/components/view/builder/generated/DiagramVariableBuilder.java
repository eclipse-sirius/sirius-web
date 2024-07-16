/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
 * Builder for DiagramVariableBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DiagramVariableBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.DiagramVariable.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.DiagramVariable diagramVariable = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createDiagramVariable();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DiagramVariable.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.DiagramVariable getDiagramVariable() {
        return this.diagramVariable;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DiagramVariable.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.DiagramVariable build() {
        return this.getDiagramVariable();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DiagramVariableBuilder name(java.lang.String value) {
        this.getDiagramVariable().setName(value);
        return this;
    }
    /**
     * Setter for DefaultValueExpression.
     *
     * @generated
     */
    public DiagramVariableBuilder defaultValueExpression(java.lang.String value) {
        this.getDiagramVariable().setDefaultValueExpression(value);
        return this;
    }

}

