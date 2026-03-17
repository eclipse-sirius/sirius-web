/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.task.starter.services.view;

import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.gantt.GanttBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool;
import org.eclipse.sirius.components.view.gantt.CreateTaskTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool;
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

    public static final String WORKPACKAGE_GANTT_REP_DESC_NAME = "Gantt of Workpackage Task Details";

    private static final String AQL_SELF_NAME = "aql:self.name";

    private static final String AQL_SELF_DESCRIPTION = "aql:self.description";

    private static final String AQL_SELF_PROGRESS = "aql:self.progress";

    private static final String AQL_SELF_STARTTIME = "aql:self.startTime";

    private static final String AQL_SELF_ENDTIME = "aql:self.endTime";

    private static final String AQL_SELF_DEPENDENCIES = "aql:self.dependencies->select(dep | dep.source.toString() = sourceStartOrEnd.toString())->select(dep | dep.target.toString() = targetStartOrEnd.toString())->collect(dep | dep.dependency)";


    public ViewGanttDescriptionBuilder() {
    }

    public void addRepresentationDescription(View view) {
        GanttDescription workpackageGanttDescription =  this.createWorkpackageGanttDescription();

        view.getDescriptions().add(workpackageGanttDescription);
    }

    private GanttDescription createWorkpackageGanttDescription() {
        TaskDescription tasksDescription = this.createTaskDescriptionInWorkpackage();

        CreateTaskTool createTaskTool = this.createCreateTaskTool();
        EditTaskTool editTaskTool = this.createEditTaskTool();
        DeleteTaskTool deleteTaskTool = this.createDeleteTaskTool();
        DropTaskTool dropTaskTool = this.createDropTaskTool();
        CreateTaskDependencyTool createTaskDependencyTool = this.createTaskDependencyTool();
        DeleteTaskDependencyTool deleteTaskDependencyTool = this.createDeleteTaskDependencyTool();

        GanttDescription ganttDescription = new GanttBuilders().newGanttDescription()
                .name(WORKPACKAGE_GANTT_REP_DESC_NAME)
                .domainType("peppermm::Workpackage")
                .titleExpression("New Gantt")
                .taskElementDescriptions(tasksDescription)
                .createTool(createTaskTool)
                .editTool(editTaskTool)
                .deleteTool(deleteTaskTool)
                .dropTool(dropTaskTool)
                .createTaskDependencyTool(createTaskDependencyTool)
                .deleteTaskDependencyTool(deleteTaskDependencyTool)
                .build();

        return ganttDescription;
    }

    private DropTaskTool createDropTaskTool() {
        return new GanttBuilders().newDropTaskTool()
                .name("Drop Task")
                .body(new ChangeContextBuilder()
                        .expression("aql:sourceObject.moveTaskIntoTarget(targetObject, indexInTarget)")
                        .build())
                .build();
    }

    private DeleteTaskTool createDeleteTaskTool() {
        return new GanttBuilders().newDeleteTaskTool()
                .name("Delete Task")
                .body(new ChangeContextBuilder()
                        .expression("aql:self.deleteTask()")
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

    private CreateTaskDependencyTool createTaskDependencyTool() {
        return new GanttBuilders().newCreateTaskDependencyTool()
                .name("Create Task Dependency")
                .body(new ChangeContextBuilder()
                        .expression("aql:targetObject.createDependencyLink(sourceObject, sourceStartOrEnd, targetStartOrEnd)")
                        .build())
                .build();
    }

    private DeleteTaskDependencyTool createDeleteTaskDependencyTool() {
        return new GanttBuilders().newDeleteTaskDependencyTool()
                .name("Delete Task Dependency")
                .body(new ChangeContextBuilder()
                        .expression("aql:targetObject.deleteDependencyLink(sourceObject)")
                        .build())
                .build();
    }

    private TaskDescription createTaskDescriptionInWorkpackage() {
        TaskDescription taskDescriptionInTask = this.createTaskDescriptionInTask();

        return new GanttBuilders().newTaskDescription()
                .name("Tasks In Project")
                .semanticCandidatesExpression("aql:self.ownedTasks")
                .nameExpression(AQL_SELF_NAME)
                .descriptionExpression(AQL_SELF_DESCRIPTION)
                .startTimeExpression(AQL_SELF_STARTTIME)
                .endTimeExpression(AQL_SELF_ENDTIME)
                .progressExpression(AQL_SELF_PROGRESS)
                .computeStartEndDynamicallyExpression("aql:self.computeStartEndDynamically")
                .taskDependenciesExpression(AQL_SELF_DEPENDENCIES)
                .subTaskElementDescriptions(taskDescriptionInTask)
                .build();
    }

    private TaskDescription createTaskDescriptionInTask() {
        TaskDescription taskDescription = new GanttBuilders().newTaskDescription()
                .name("Sub Tasks")
                .semanticCandidatesExpression("aql:self.subTasks")
                .nameExpression(AQL_SELF_NAME)
                .descriptionExpression(AQL_SELF_DESCRIPTION)
                .startTimeExpression(AQL_SELF_STARTTIME)
                .endTimeExpression(AQL_SELF_ENDTIME)
                .progressExpression(AQL_SELF_PROGRESS)
                .computeStartEndDynamicallyExpression("aql:self.computeStartEndDynamically")
                .taskDependenciesExpression(AQL_SELF_DEPENDENCIES)
                .build();

        taskDescription.getReusedTaskElementDescriptions().add(taskDescription);

        return taskDescription;
    }
}
