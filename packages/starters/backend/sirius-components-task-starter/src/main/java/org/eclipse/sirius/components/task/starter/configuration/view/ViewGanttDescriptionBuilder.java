/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import org.eclipse.sirius.components.view.builder.generated.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.DeleteElementBuilder;
import org.eclipse.sirius.components.view.builder.generated.GanttBuilders;
import org.eclipse.sirius.components.view.gantt.CreateTaskTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskTool;
import org.eclipse.sirius.components.view.gantt.DropTaskTool;
import org.eclipse.sirius.components.view.gantt.EditTaskTool;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
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

    public void addRepresentationDescription(View view) {
        GanttDescription ganttDescription =  this.createGanttDescription();


        view.getDescriptions().add(ganttDescription);
    }

    private GanttDescription createGanttDescription() {
        TaskDescription tasksDescription = this.createTaskDescriptionInProject();

        CreateTaskTool createTaskTool = this.createCreateTaskTool();
        EditTaskTool editTaskTool = this.createEditTaskTool();
        DeleteTaskTool deleteTaskTool = this.createDeleteTaskTool();
        DropTaskTool dropTaskTool = this.createDropTaskTool();

        GanttDescription ganttDescription = new GanttBuilders().newGanttDescription()
                .name(GANTT_REP_DESC_NAME)
                .domainType("task::Project")
                .titleExpression("New Gantt")
                .taskElementDescriptions(tasksDescription)
                .createTool(createTaskTool)
                .editTool(editTaskTool)
                .deleteTool(deleteTaskTool)
                .dropTool(dropTaskTool)
                .build();

        return ganttDescription;
    }

    private DropTaskTool createDropTaskTool() {
        return new GanttBuilders().newDropTaskTool()
                .name("Drop Task")
                .body(new ChangeContextBuilder()
                        .expression("aql:source.moveTaskIntoTarget(target, indexInTarget)")
                        .build())
                .build();
    }

    private DeleteTaskTool createDeleteTaskTool() {
        return new GanttBuilders().newDeleteTaskTool()
                .name("Delete Task")
                .body(new DeleteElementBuilder()
                        .build())
                .build();
    }

    private EditTaskTool createEditTaskTool() {
        return new GanttBuilders().newEditTaskTool()
                .name("Edit Task")
                .body(new ChangeContextBuilder()
                        .expression("aql:self.editTask(newName, newDescription, newStartTime, newEndTime, newProgress)")
                        .build())
                .build();
    }

    private CreateTaskTool createCreateTaskTool() {
        return new GanttBuilders().newCreateTaskTool()
                .name("Create Task After")
                .body(new ChangeContextBuilder()
                        .expression("aql:self.createTask()")
                        .build())
                .build();
    }

    private TaskDescription createTaskDescriptionInProject() {
        TaskDescription taskDescriptionInTask = this.createTaskDescriptionInTask();

        return new GanttBuilders().newTaskDescription()
                .name("Tasks In Project")
                .semanticCandidatesExpression("aql:self.ownedTasks")
                .nameExpression("aql:self.name")
                .descriptionExpression("aql:self.description")
                .startTimeExpression("aql:self.startTime")
                .endTimeExpression("aql:self.endTime")
                .progressExpression("aql:self.progress")
                .computeStartEndDynamicallyExpression("aql:self.computeStartEndDynamically")
                .dependenciesExpression("aql:self.dependencies")
                .subTaskElementDescriptions(taskDescriptionInTask)
                .build();
    }

    private TaskDescription createTaskDescriptionInTask() {
        TaskDescription taskDescription = new GanttBuilders().newTaskDescription()
                .name("Sub Tasks")
                .semanticCandidatesExpression("aql:self.subTasks")
                .nameExpression("aql:self.name")
                .descriptionExpression("aql:self.description")
                .startTimeExpression("aql:self.startTime")
                .endTimeExpression("aql:self.endTime")
                .progressExpression("aql:self.progress")
                .computeStartEndDynamicallyExpression("aql:self.computeStartEndDynamically")
                .dependenciesExpression("aql:self.dependencies")
                .build();

        taskDescription.getReusedTaskElementDescriptions().add(taskDescription);

        return taskDescription;
    }
}
