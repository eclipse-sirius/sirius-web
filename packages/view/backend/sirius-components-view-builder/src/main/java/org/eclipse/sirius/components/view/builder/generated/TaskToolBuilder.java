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
 * Builder for org.eclipse.sirius.components.view.gantt.TaskTool.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class TaskToolBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.gantt.TaskTool.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.gantt.TaskTool getTaskTool();

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TaskToolBuilder name(java.lang.String value) {
        this.getTaskTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public TaskToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getTaskTool().getBody().add(value);
        }
        return this;
    }


}

