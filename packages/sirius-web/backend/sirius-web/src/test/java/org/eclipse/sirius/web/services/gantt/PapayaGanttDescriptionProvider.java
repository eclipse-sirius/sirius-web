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
package org.eclipse.sirius.web.services.gantt;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.CreateInstanceBuilder;
import org.eclipse.sirius.components.view.builder.generated.DeleteElementBuilder;
import org.eclipse.sirius.components.view.builder.generated.GanttBuilders;
import org.eclipse.sirius.components.view.builder.generated.GanttDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.emf.gantt.IGanttIdProvider;
import org.eclipse.sirius.components.view.gantt.CreateTaskTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskTool;
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
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
public class PapayaGanttDescriptionProvider implements IEditingContextProcessor {

    private final IGanttIdProvider ganttIdProvider;

    private final View view;

    private GanttDescription ganttDescription;

    public PapayaGanttDescriptionProvider(IGanttIdProvider ganttIdProvider) {
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
        ganttView.getDescriptions().add(this.createGanttDescription());

        ganttView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("GanttDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("GanttDescription"));
        resource.getContents().add(ganttView);

        return ganttView;
    }

    private GanttDescription createGanttDescription() {
        CreateTaskTool createTaskTool = this.createCreateTaskTool();
        DeleteTaskTool deleteTaskTool = this.createDeleteTaskTool();

        this.ganttDescription = new GanttDescriptionBuilder()
                .name("Gantt")
                .titleExpression("aql:'Gantt'")
                .domainType("papaya:Project")
                .taskElementDescriptions(this.createTaskDescriptionInProject())
                .createTool(createTaskTool)
                .deleteTool(deleteTaskTool)
                .build();

        return this.ganttDescription;
    }

    private TaskDescription createTaskDescriptionInProject() {
        TaskDescription taskDescriptionInTask = this.createTaskDescriptionInTask();

        return new GanttBuilders().newTaskDescription()
                .name("Iterations In Project")
                .domainType("papaya::Iteration")
                .semanticCandidatesExpression("aql:self.iterations")
                .nameExpression("aql:self.name")
                .startTimeExpression("aql:self.startTime")
                .endTimeExpression("aql:self.endTime")
                .subTaskElementDescriptions(taskDescriptionInTask)
                .build();
    }

    private TaskDescription createTaskDescriptionInTask() {
        TaskDescription taskDescription = new GanttBuilders().newTaskDescription()
                .name("Sub Tasks")
                .domainType("papaya::Task")
                .semanticCandidatesExpression("aql:self.tasks")
                .nameExpression("aql:self.name")
                .descriptionExpression("aql:self.description")
                .startTimeExpression("aql:self.startTime")
                .endTimeExpression("aql:self.endTime")
                .progressExpression("aql:self.progress")
                .build();

        taskDescription.getReusedTaskElementDescriptions().add(taskDescription);

        return taskDescription;
    }

    private DeleteTaskTool createDeleteTaskTool() {
        return new GanttBuilders().newDeleteTaskTool()
                .name("Delete Task")
                .body(new DeleteElementBuilder()
                        .build())
                .build();
    }

    private CreateTaskTool createCreateTaskTool() {
        ViewBuilders viewBuilders = new ViewBuilders();

        SetValue setNameValue = viewBuilders.newSetValue()
                .featureName("name")
                .valueExpression("New task")
                .build();

        ChangeContext newInstanceChangeContext = viewBuilders.newChangeContext()
                .expression("aql:newInstance")
                .children(setNameValue)
                .build();

        return new GanttBuilders().newCreateTaskTool()
                .name("Create Task")
                .body(new CreateInstanceBuilder()
                        .typeName("papaya::Task")
                        .referenceName("tasks")
                        .children(newInstanceChangeContext)
                        .build())
                .build();
    }
}
