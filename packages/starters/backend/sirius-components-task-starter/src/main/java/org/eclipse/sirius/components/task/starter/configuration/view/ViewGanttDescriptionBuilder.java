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
package org.eclipse.sirius.components.task.starter.configuration.view;

import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.components.view.gantt.GanttFactory;
import org.eclipse.sirius.components.view.gantt.TaskDescription;


/**
 * Builder of the "Gantt" view description.
 *
 * @author lfasani
 */
public class ViewGanttDescriptionBuilder {

    public static final String GANTT_REP_DESC_NAME = "Gantt Representation";


    public ViewGanttDescriptionBuilder() {
    }

    public GanttDescription createRepresentationDescription(View view) {
        GanttDescription ganttDescription =  this.createGanttDescription();
        TaskDescription tasksDescription = this.createTasksDescription(ganttDescription);
        ganttDescription.getTaskElementDescriptions().add(tasksDescription);

        view.getDescriptions().add(ganttDescription);
        return ganttDescription;
    }

    private GanttDescription createGanttDescription() {
        GanttDescription createGanttDescription = GanttFactory.eINSTANCE.createGanttDescription();

        createGanttDescription.setName(GANTT_REP_DESC_NAME);
        createGanttDescription.setDomainType("task::Task");
        createGanttDescription.setTitleExpression("New Gantt");
        return createGanttDescription;
    }

    private TaskDescription createTasksDescription(GanttDescription ganttGanttDescription) {

        TaskDescription taskDescription = GanttFactory.eINSTANCE.createTaskDescription();
        taskDescription.setSemanticCandidatesExpression("aql:self.subTasks");
        taskDescription.setTaskDetailExpression("aql:self.getTaskDetail()");
        taskDescription.getReusedTaskElementDescriptions().add(taskDescription);

        return taskDescription;
    }
}
