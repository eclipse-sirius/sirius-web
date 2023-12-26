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
 * Builder for CreateTaskToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CreateTaskToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.gantt.CreateTaskTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.gantt.CreateTaskTool createTaskTool = org.eclipse.sirius.components.view.gantt.GanttFactory.eINSTANCE.createCreateTaskTool();

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.CreateTaskTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.gantt.CreateTaskTool getCreateTaskTool() {
        return this.createTaskTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.CreateTaskTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.gantt.CreateTaskTool build() {
        return this.getCreateTaskTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public CreateTaskToolBuilder name(java.lang.String value) {
        this.getCreateTaskTool().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public CreateTaskToolBuilder preconditionExpression(java.lang.String value) {
        this.getCreateTaskTool().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public CreateTaskToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCreateTaskTool().getBody().add(value);
        }
        return this;
    }


}

