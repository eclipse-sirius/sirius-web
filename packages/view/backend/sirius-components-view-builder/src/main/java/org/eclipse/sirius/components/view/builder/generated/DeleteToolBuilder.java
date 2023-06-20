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
 * Builder for DeleteToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DeleteToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.DeleteTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.DeleteTool deleteTool = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createDeleteTool();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DeleteTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.DeleteTool getDeleteTool() {
        return this.deleteTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DeleteTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.DeleteTool build() {
        return this.getDeleteTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DeleteToolBuilder name(java.lang.String value) {
        this.getDeleteTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public DeleteToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDeleteTool().getBody().add(value);
        }
        return this;
    }


}

