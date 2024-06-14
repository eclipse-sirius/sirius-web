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
 * Builder for TaskDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TaskDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.gantt.TaskDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.gantt.TaskDescription taskDescription = org.eclipse.sirius.components.view.gantt.GanttFactory.eINSTANCE.createTaskDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.TaskDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.gantt.TaskDescription getTaskDescription() {
        return this.taskDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.TaskDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.gantt.TaskDescription build() {
        return this.getTaskDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TaskDescriptionBuilder name(java.lang.String value) {
        this.getTaskDescription().setName(value);
        return this;
    }

    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public TaskDescriptionBuilder domainType(java.lang.String value) {
        this.getTaskDescription().setDomainType(value);
        return this;
    }

    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public TaskDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getTaskDescription().setSemanticCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for NameExpression.
     *
     * @generated
     */
    public TaskDescriptionBuilder nameExpression(java.lang.String value) {
        this.getTaskDescription().setNameExpression(value);
        return this;
    }
    /**
     * Setter for DescriptionExpression.
     *
     * @generated
     */
    public TaskDescriptionBuilder descriptionExpression(java.lang.String value) {
        this.getTaskDescription().setDescriptionExpression(value);
        return this;
    }
    /**
     * Setter for StartTimeExpression.
     *
     * @generated
     */
    public TaskDescriptionBuilder startTimeExpression(java.lang.String value) {
        this.getTaskDescription().setStartTimeExpression(value);
        return this;
    }
    /**
     * Setter for EndTimeExpression.
     *
     * @generated
     */
    public TaskDescriptionBuilder endTimeExpression(java.lang.String value) {
        this.getTaskDescription().setEndTimeExpression(value);
        return this;
    }
    /**
     * Setter for ProgressExpression.
     *
     * @generated
     */
    public TaskDescriptionBuilder progressExpression(java.lang.String value) {
        this.getTaskDescription().setProgressExpression(value);
        return this;
    }
    /**
     * Setter for ComputeStartEndDynamicallyExpression.
     *
     * @generated
     */
    public TaskDescriptionBuilder computeStartEndDynamicallyExpression(java.lang.String value) {
        this.getTaskDescription().setComputeStartEndDynamicallyExpression(value);
        return this;
    }

    /**
     * Setter for TaskDependenciesExpression.
     *
     * @generated
     */
    public TaskDescriptionBuilder taskDependenciesExpression(java.lang.String value) {
        this.getTaskDescription().setTaskDependenciesExpression(value);
        return this;
    }

    /**
     * Setter for SubTaskElementDescriptions.
     *
     * @generated
     */
    public TaskDescriptionBuilder subTaskElementDescriptions(org.eclipse.sirius.components.view.gantt.TaskDescription ... values) {
        for (org.eclipse.sirius.components.view.gantt.TaskDescription value : values) {
            this.getTaskDescription().getSubTaskElementDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for ReusedTaskElementDescriptions.
     *
     * @generated
     */
    public TaskDescriptionBuilder reusedTaskElementDescriptions(org.eclipse.sirius.components.view.gantt.TaskDescription ... values) {
        for (org.eclipse.sirius.components.view.gantt.TaskDescription value : values) {
            this.getTaskDescription().getReusedTaskElementDescriptions().add(value);
        }
        return this;
    }


}

