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
 * Builder for DropToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DropToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.DropTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.DropTool dropTool = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createDropTool();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DropTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.DropTool getDropTool() {
        return this.dropTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DropTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.DropTool build() {
        return this.getDropTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DropToolBuilder name(java.lang.String value) {
        this.getDropTool().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public DropToolBuilder preconditionExpression(java.lang.String value) {
        this.getDropTool().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public DropToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDropTool().getBody().add(value);
        }
        return this;
    }


}

