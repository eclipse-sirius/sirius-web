/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
 * Builder for DeleteTaskToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DeleteTaskToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.gantt.DeleteTaskTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.gantt.DeleteTaskTool deleteTaskTool = org.eclipse.sirius.components.view.gantt.GanttFactory.eINSTANCE.createDeleteTaskTool();

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.DeleteTaskTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.gantt.DeleteTaskTool getDeleteTaskTool() {
        return this.deleteTaskTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.DeleteTaskTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.gantt.DeleteTaskTool build() {
        return this.getDeleteTaskTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DeleteTaskToolBuilder name(java.lang.String value) {
        this.getDeleteTaskTool().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public DeleteTaskToolBuilder preconditionExpression(java.lang.String value) {
        this.getDeleteTaskTool().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public DeleteTaskToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDeleteTaskTool().getBody().add(value);
        }
        return this;
    }


}

