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
 * Builder for EditTaskToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class EditTaskToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.gantt.EditTaskTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.gantt.EditTaskTool editTaskTool = org.eclipse.sirius.components.view.gantt.GanttFactory.eINSTANCE.createEditTaskTool();

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.EditTaskTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.gantt.EditTaskTool getEditTaskTool() {
        return this.editTaskTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.EditTaskTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.gantt.EditTaskTool build() {
        return this.getEditTaskTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public EditTaskToolBuilder name(java.lang.String value) {
        this.getEditTaskTool().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public EditTaskToolBuilder preconditionExpression(java.lang.String value) {
        this.getEditTaskTool().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public EditTaskToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getEditTaskTool().getBody().add(value);
        }
        return this;
    }


}

