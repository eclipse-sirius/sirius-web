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
 * Builder for GanttDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class GanttDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.gantt.GanttDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.gantt.GanttDescription ganttDescription = org.eclipse.sirius.components.view.gantt.GanttFactory.eINSTANCE.createGanttDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.GanttDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.gantt.GanttDescription getGanttDescription() {
        return this.ganttDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.GanttDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.gantt.GanttDescription build() {
        return this.getGanttDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public GanttDescriptionBuilder name(java.lang.String value) {
        this.getGanttDescription().setName(value);
        return this;
    }
    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public GanttDescriptionBuilder domainType(java.lang.String value) {
        this.getGanttDescription().setDomainType(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public GanttDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getGanttDescription().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for TitleExpression.
     *
     * @generated
     */
    public GanttDescriptionBuilder titleExpression(java.lang.String value) {
        this.getGanttDescription().setTitleExpression(value);
        return this;
    }
    /**
     * Setter for TaskElementDescriptions.
     *
     * @generated
     */
    public GanttDescriptionBuilder taskElementDescriptions(org.eclipse.sirius.components.view.gantt.TaskDescription ... values) {
        for (org.eclipse.sirius.components.view.gantt.TaskDescription value : values) {
            this.getGanttDescription().getTaskElementDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public GanttDescriptionBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getGanttDescription().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for CreateTool.
     *
     * @generated
     */
    public GanttDescriptionBuilder createTool(org.eclipse.sirius.components.view.gantt.CreateTaskTool value) {
        this.getGanttDescription().setCreateTool(value);
        return this;
    }
    /**
     * Setter for EditTool.
     *
     * @generated
     */
    public GanttDescriptionBuilder editTool(org.eclipse.sirius.components.view.gantt.EditTaskTool value) {
        this.getGanttDescription().setEditTool(value);
        return this;
    }
    /**
     * Setter for DeleteTool.
     *
     * @generated
     */
    public GanttDescriptionBuilder deleteTool(org.eclipse.sirius.components.view.gantt.DeleteTaskTool value) {
        this.getGanttDescription().setDeleteTool(value);
        return this;
    }

    /**
     * Setter for DropTool.
     *
     * @generated
     */
    public GanttDescriptionBuilder dropTool(org.eclipse.sirius.components.view.gantt.DropTaskTool value) {
        this.getGanttDescription().setDropTool(value);
        return this;
    }

}

