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
 * Builder for org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class EdgeReconnectionToolBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool getEdgeReconnectionTool();

    /**
     * Setter for Name.
     *
     * @generated
     */
    public EdgeReconnectionToolBuilder name(java.lang.String value) {
        this.getEdgeReconnectionTool().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public EdgeReconnectionToolBuilder preconditionExpression(java.lang.String value) {
        this.getEdgeReconnectionTool().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public EdgeReconnectionToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getEdgeReconnectionTool().getBody().add(value);
        }
        return this;
    }


}

