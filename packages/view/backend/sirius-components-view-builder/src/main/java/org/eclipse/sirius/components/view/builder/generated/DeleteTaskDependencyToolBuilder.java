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
 * Builder for DeleteTaskDependencyToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DeleteTaskDependencyToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool.
     *
     * @generated
     */
    private org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool deleteTaskDependencyTool = org.eclipse.sirius.components.view.gantt.GanttFactory.eINSTANCE.createDeleteTaskDependencyTool();

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool getDeleteTaskDependencyTool() {
        return this.deleteTaskDependencyTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool build() {
        return this.getDeleteTaskDependencyTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DeleteTaskDependencyToolBuilder name(java.lang.String value) {
        this.getDeleteTaskDependencyTool().setName(value);
        return this;
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public DeleteTaskDependencyToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDeleteTaskDependencyTool().getBody().add(value);
        }
        return this;
    }


}

