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
 * Builder for DropTaskToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DropTaskToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.gantt.DropTaskTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.gantt.DropTaskTool dropTaskTool = org.eclipse.sirius.components.view.gantt.GanttFactory.eINSTANCE.createDropTaskTool();

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.DropTaskTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.gantt.DropTaskTool getDropTaskTool() {
        return this.dropTaskTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.DropTaskTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.gantt.DropTaskTool build() {
        return this.getDropTaskTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DropTaskToolBuilder name(java.lang.String value) {
        this.getDropTaskTool().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public DropTaskToolBuilder preconditionExpression(java.lang.String value) {
        this.getDropTaskTool().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public DropTaskToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDropTaskTool().getBody().add(value);
        }
        return this;
    }


}

