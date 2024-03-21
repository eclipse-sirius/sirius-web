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
 * Builder for CreateTaskDependencyToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CreateTaskDependencyToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool createTaskDependencyTool = org.eclipse.sirius.components.view.gantt.GanttFactory.eINSTANCE.createCreateTaskDependencyTool();

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool getCreateTaskDependencyTool() {
        return this.createTaskDependencyTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool build() {
        return this.getCreateTaskDependencyTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public CreateTaskDependencyToolBuilder name(java.lang.String value) {
        this.getCreateTaskDependencyTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public CreateTaskDependencyToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCreateTaskDependencyTool().getBody().add(value);
        }
        return this;
    }


}

