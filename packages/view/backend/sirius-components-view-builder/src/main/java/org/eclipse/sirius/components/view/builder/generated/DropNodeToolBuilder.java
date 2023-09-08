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
 * Builder for DropNodeToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DropNodeToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.DropNodeTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.DropNodeTool dropNodeTool = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createDropNodeTool();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DropNodeTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.DropNodeTool getDropNodeTool() {
        return this.dropNodeTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DropNodeTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.DropNodeTool build() {
        return this.getDropNodeTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DropNodeToolBuilder name(java.lang.String value) {
        this.getDropNodeTool().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public DropNodeToolBuilder preconditionExpression(java.lang.String value) {
        this.getDropNodeTool().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public DropNodeToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDropNodeTool().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for AcceptedNodeTypes.
     *
     * @generated
     */
    public DropNodeToolBuilder acceptedNodeTypes(org.eclipse.sirius.components.view.diagram.NodeDescription ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeDescription value : values) {
            this.getDropNodeTool().getAcceptedNodeTypes().add(value);
        }
        return this;
    }


}

