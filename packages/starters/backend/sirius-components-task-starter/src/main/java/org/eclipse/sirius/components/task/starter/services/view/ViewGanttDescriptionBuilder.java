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

import java.util.List;

import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.gantt.GanttBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.DeleteElementBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.UnsetValueBuilder;
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

    public static final String PROJECT_GANTT_REP_DESC_NAME = "Gantt of Project Workpackages";

    private static final String AQL_SELF_NAME = "aql:self.name";

    private static final String AQL_SELF_DESCRIPTION = "aql:self.description";

    private static final String AQL_SELF_PROGRESS = "aql:self.progress";


    public ViewGanttDescriptionBuilder() {
    }

    public void addRepresentationDescription(View view) {
        GanttDescription workpackageGanttDescription =  this.createWorkpackageGanttDescription();
        GanttDescription projectGanttDescription =  this.createProjectGanttDescription();

        view.getDescriptions().addAll(List.of(workpackageGanttDescription, projectGanttDescription));
    }

    private GanttDescription createWorkpackageGanttDescription() {
        TaskDescription tasksDescription = this.createTaskDescriptionInWorkpackage();

        CreateTaskTool createTaskTool = this.createCreateTaskTool();
        EditTaskTool editTaskTool = this.createEditTaskTool();
        DeleteTaskTool deleteTaskTool = this.createDeleteTaskTool("Delete Task");
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

    private DeleteTaskTool createDeleteTaskTool(String name) {
        return new GanttBuilders().newDeleteTaskTool()
                .name(name)
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

    private CreateTaskDependencyTool createTaskDependencyTool() {
        return new GanttBuilders().newCreateTaskDependencyTool()
                .name("Create Task Dependency")
                .body(new ChangeContextBuilder()
                        .expression("aql:targetObject")
                        .children(new SetValueBuilder()
                                .featureName("dependencies")
                                .valueExpression("aql:sourceObject")
                                .build())
                        .build())
                .build();
    }

    private DeleteTaskDependencyTool createDeleteTaskDependencyTool() {
        return new GanttBuilders().newDeleteTaskDependencyTool()
                .name("Delete Task Dependency")
                .body(new ChangeContextBuilder()
                        .expression("aql:targetObject")
                        .children(new UnsetValueBuilder()
                                .featureName("dependencies")
                                .elementExpression("aql:sourceObject")
                                .build())
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
                .startTimeExpression("aql:self.startTime")
                .endTimeExpression("aql:self.endTime")
                .progressExpression(AQL_SELF_PROGRESS)
                .computeStartEndDynamicallyExpression("aql:self.computeStartEndDynamically")
                .taskDependenciesExpression("aql:self.dependencies")
                .subTaskElementDescriptions(taskDescriptionInTask)
                .build();
    }

    private TaskDescription createTaskDescriptionInTask() {
        TaskDescription taskDescription = new GanttBuilders().newTaskDescription()
                .name("Sub Tasks")
                .semanticCandidatesExpression("aql:self.subTasks")
                .nameExpression(AQL_SELF_NAME)
                .descriptionExpression(AQL_SELF_DESCRIPTION)
                .startTimeExpression("aql:self.startTime")
                .endTimeExpression("aql:self.endTime")
                .progressExpression(AQL_SELF_PROGRESS)
                .computeStartEndDynamicallyExpression("aql:self.computeStartEndDynamically")
                .taskDependenciesExpression("aql:self.dependencies")
                .build();

        taskDescription.getReusedTaskElementDescriptions().add(taskDescription);

        return taskDescription;
    }


    private GanttDescription createProjectGanttDescription() {
        TaskDescription tasksDescription = this.createWorkpackageDescriptionInProject();

        CreateTaskTool createTaskTool = this.createCreateTaskToolForWorkpackage();
        EditTaskTool editTaskTool = this.createEditTaskToolForWorkpackage();
        DeleteTaskTool deleteTaskTool = this.createDeleteTaskTool("Delete Workpackage");
        DropTaskTool dropWorkpackageTool = this.createDropWorkpackageTool();

        GanttDescription ganttDescription = new GanttBuilders().newGanttDescription()
                .name(PROJECT_GANTT_REP_DESC_NAME)
                .domainType("peppermm::Project")
                .titleExpression("New Gantt")
                .taskElementDescriptions(tasksDescription)
                .createTool(createTaskTool)
                .editTool(editTaskTool)
                .deleteTool(deleteTaskTool)
                .dropTool(dropWorkpackageTool)
                .dateRoundingExpression("1D")
                .build();

        return ganttDescription;
    }

    private TaskDescription createWorkpackageDescriptionInProject() {
        return new GanttBuilders().newTaskDescription()
                .name("Workpackages In Project")
                .semanticCandidatesExpression("aql:self.ownedWorkpackages")
                .nameExpression(AQL_SELF_NAME)
                .descriptionExpression(AQL_SELF_DESCRIPTION)
                .startTimeExpression("aql:self.startDate")
                .endTimeExpression("aql:self.endDate")
                .progressExpression(AQL_SELF_PROGRESS)
                .computeStartEndDynamicallyExpression("aql:false")
                .taskDependenciesExpression("")
                .build();
    }

    private CreateTaskTool createCreateTaskToolForWorkpackage() {
        return new GanttBuilders().newCreateTaskTool()
                .name("Create Workpackage After")
                .body(new ChangeContextBuilder()
                        .expression("aql:self.createWorkpackage()")
                        .build())
                .build();
    }

    private EditTaskTool createEditTaskToolForWorkpackage() {
        return new GanttBuilders().newEditTaskTool()
                .name("Edit Workpackage")
                .body(new ChangeContextBuilder()
                        .expression("aql:self.editWorkpackage(newName, newDescription, newStartTime, newEndTime, newProgress)")
                        .build())
                .build();
    }

    private DropTaskTool createDropWorkpackageTool() {
        return new GanttBuilders().newDropTaskTool()
                .name("Drop Workpackage")
                .body(new ChangeContextBuilder()
                        .expression("aql:sourceObject.moveWorkpackageInProject(targetObject, indexInTarget)")
                        .build())
                .build();
    }
}
