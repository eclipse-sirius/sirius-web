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
package org.eclipse.sirius.components.view.builder.generated.diagram;

/**
 * Builder for NodeToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class NodeToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.NodeTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.NodeTool nodeTool = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createNodeTool();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodeTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.NodeTool getNodeTool() {
        return this.nodeTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodeTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.NodeTool build() {
        return this.getNodeTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public NodeToolBuilder name(java.lang.String value) {
        this.getNodeTool().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public NodeToolBuilder preconditionExpression(java.lang.String value) {
        this.getNodeTool().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public NodeToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getNodeTool().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for DialogDescription.
     *
     * @generated
     */
    public NodeToolBuilder dialogDescription(org.eclipse.sirius.components.view.diagram.DialogDescription value) {
        this.getNodeTool().setDialogDescription(value);
        return this;
    }
    /**
     * Setter for IconURLsExpression.
     *
     * @generated
     */
    public NodeToolBuilder iconURLsExpression(java.lang.String value) {
        this.getNodeTool().setIconURLsExpression(value);
        return this;
    }

    /**
     * Setter for ElementsToSelectExpression.
     *
     * @generated
     */
    public NodeToolBuilder elementsToSelectExpression(java.lang.String value) {
        this.getNodeTool().setElementsToSelectExpression(value);
        return this;
    }

}

