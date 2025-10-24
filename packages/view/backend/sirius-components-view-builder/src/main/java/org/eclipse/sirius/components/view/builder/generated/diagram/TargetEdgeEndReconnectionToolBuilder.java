/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
 * Builder for TargetEdgeEndReconnectionToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TargetEdgeEndReconnectionToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool targetEdgeEndReconnectionTool = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createTargetEdgeEndReconnectionTool();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool getTargetEdgeEndReconnectionTool() {
        return this.targetEdgeEndReconnectionTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool build() {
        return this.getTargetEdgeEndReconnectionTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TargetEdgeEndReconnectionToolBuilder name(java.lang.String value) {
        this.getTargetEdgeEndReconnectionTool().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public TargetEdgeEndReconnectionToolBuilder preconditionExpression(java.lang.String value) {
        this.getTargetEdgeEndReconnectionTool().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public TargetEdgeEndReconnectionToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getTargetEdgeEndReconnectionTool().getBody().add(value);
        }
        return this;
    }


}

