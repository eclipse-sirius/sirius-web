/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.services.gantt;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.gantt.GanttBuilders;
import org.eclipse.sirius.components.view.builder.generated.gantt.GanttDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.emf.gantt.IGanttIdProvider;
import org.eclipse.sirius.components.view.gantt.CreateTaskTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskTool;
import org.eclipse.sirius.components.view.gantt.EditTaskTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool;
import org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.components.view.gantt.TaskDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based gantt description for tests.
 *
 * @author ncouvert
 */
@Service
@Conditional(OnStudioTests.class)
public class PepperGanttDescriptionProvider implements IEditingContextProcessor {

    private final IGanttIdProvider ganttIdProvider;

    private final View view;

    private GanttDescription ganttDescription;

    private static final String AQL_SELF_DEPENDENCIES = "aql:self.dependencies->select(dep | dep.source.toString() = sourceStartOrEnd.toString())->select(dep | dep.target.toString() = targetStartOrEnd.toString())->collect(dep | dep.dependency)";


    public PepperGanttDescriptionProvider(IGanttIdProvider ganttIdProvider) {
        this.ganttIdProvider = Objects.requireNonNull(ganttIdProvider);
        this.view = this.createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    public String getRepresentationDescriptionId() {
        return this.ganttIdProvider.getId(this.ganttDescription);
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View ganttView = viewBuilder.build();
        ganttView.getDescriptions().add(this.createWorkpackageGanttDescription());

        ganttView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("GanttDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("GanttDescription"));
        resource.getContents().add(ganttView);

        return ganttView;
    }

    private GanttDescription createWorkpackageGanttDescription() {
        TaskDescription tasksDescription = this.createTaskDescriptionInWorkpackage();

        CreateTaskTool createTaskTool = this.createCreateTaskTool();
        EditTaskTool editTaskTool = this.createEditTaskTool();
        DeleteTaskTool deleteTaskTool = this.createDeleteTaskTool();
        CreateTaskDependencyTool createTaskDependencyTool = this.createCreateTaskDependencyTool();
        DeleteTaskDependencyTool deleteTaskDependencyTool = this.createDeleteTaskDependencyTool();

        this.ganttDescription = new GanttDescriptionBuilder()
                .name("Gantt")
                .titleExpression("aql:'Gantt'")
                .domainType("peppermm::Project")
                .taskElementDescriptions(tasksDescription)
                .createTool(createTaskTool)
                .editTool(editTaskTool)
                .deleteTool(deleteTaskTool)
                .createTaskDependencyTool(createTaskDependencyTool)
                .deleteTaskDependencyTool(deleteTaskDependencyTool)
                .build();

        return this.ganttDescription;
    }

    private TaskDescription createTaskDescriptionInWorkpackage() {
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
                .taskDependenciesExpression(AQL_SELF_DEPENDENCIES)
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
                .taskDependenciesExpression("aql:self.dependencies")
                .build();

        taskDescription.getReusedTaskElementDescriptions().add(taskDescription);

        return taskDescription;
    }

    private DeleteTaskTool createDeleteTaskTool() {
        return new GanttBuilders().newDeleteTaskTool()
                .name("Delete Task")
                .body(new ChangeContextBuilder()
                        .expression("aql:self.deleteTask()")
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

    private EditTaskTool createEditTaskTool() {
        return new GanttBuilders().newEditTaskTool()
                .name("Edit Task")
                .body(new ChangeContextBuilder()
                        .expression("aql:self.editTask(newName, newDescription, newStartTime, newEndTime, newProgress)")
                        .build())
                .build();
    }

    private CreateTaskDependencyTool createCreateTaskDependencyTool() {
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
}
