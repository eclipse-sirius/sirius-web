/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import pepper.peppermm.AbstractTask;
import pepper.peppermm.PepperFactory;
import pepper.peppermm.Task;
import pepper.peppermm.TaskTag;
import pepper.peppermm.TagFolder;
import pepper.peppermm.Project;
import pepper.peppermm.Workpackage;
import pepper.peppermm.Organization;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.eclipse.sirius.components.core.api.IFeedbackMessageService;


import org.eclipse.sirius.components.task.starter.services.TaskExampleBuilder;
import org.eclipse.sirius.components.task.starter.services.view.TaskJavaService;
import org.junit.jupiter.api.Test;

/**
 * Test used to validate the service for the task related views.
 *
 * @author frouene
 */
public class TaskJavaServiceTests {

    private static final String NEW_NAME = "newName";
    private static final String NEW_DESCRIPTION = "newDescription";

    @Test
    public void editTask() {
        AbstractTask task = PepperFactory.eINSTANCE.createTask();
        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());
        service.editTask(task, NEW_NAME, NEW_DESCRIPTION, Instant.ofEpochSecond(1704067200), Instant.ofEpochSecond(1704070800), 10);
        assertThat(task.getName()).isEqualTo(NEW_NAME);
        assertThat(task.getDescription()).isEqualTo(NEW_DESCRIPTION);
        assertThat(task.getStartTime().getEpochSecond()).isEqualTo(1704067200);
        assertThat(task.getEndTime().getEpochSecond()).isEqualTo(1704070800);
        assertThat(task.getProgress()).isEqualTo(10);
    }

    @Test
    public void computeTaskDurationDays() {
        Task task = PepperFactory.eINSTANCE.createTask();
        task.setStartTime(Instant.ofEpochSecond(1704067200));
        task.setEndTime(Instant.ofEpochSecond(1704157260));
        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());
        var result = service.computeTaskDurationDays(task);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("01d01h01m");
    }

    @Test
    public void editCard() {
        AbstractTask card = PepperFactory.eINSTANCE.createTask();
        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());
        service.editCard(card, NEW_NAME, NEW_DESCRIPTION, null);
        assertThat(card.getName()).isEqualTo(NEW_NAME);
        assertThat(card.getDescription()).isEqualTo(NEW_DESCRIPTION);
    }

    @Test
    public void createCard() {
        TaskTag tag = PepperFactory.eINSTANCE.createTaskTag();
        TagFolder tagFolder = PepperFactory.eINSTANCE.createTagFolder();
        Project project = PepperFactory.eINSTANCE.createProject();
        Workpackage workpackage = PepperFactory.eINSTANCE.createWorkpackage();
        project.getOwnedWorkpackages().add(workpackage);
        project.getOwnedTagFolders().add(tagFolder);
        project.getOwnedTagFolders().get(0).getOwnedTags().add(tag);
        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());
        service.createCard(tag);
        assertThat(project.getOwnedWorkpackages().get(0).getOwnedTasks()).hasSize(1);
        assertThat(project.getOwnedWorkpackages().get(0).getOwnedTasks().get(0).getName()).isEqualTo("New Task");
        assertThat(project.getOwnedWorkpackages().get(0).getOwnedTasks().get(0).getDescription()).isEqualTo("new description");
        assertThat(project.getOwnedWorkpackages().get(0).getOwnedTasks().get(0).getTags()).hasSize(1);
    }

    @Test
    public void createTask() {
        Task task11 = PepperFactory.eINSTANCE.createTask();
        task11.setStartTime(Instant.ofEpochSecond(1704067200));
        task11.setEndTime(Instant.ofEpochSecond(1704157260));

        Task task1 = PepperFactory.eINSTANCE.createTask();
        task1.setStartTime(Instant.ofEpochSecond(1704067200));
        task1.setEndTime(Instant.ofEpochSecond(1704157260));
        task1.getSubTasks().add(task11);

        Workpackage workpackage = PepperFactory.eINSTANCE.createWorkpackage();
        workpackage.getOwnedTasks().add(task1);
        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());

        service.createTask(workpackage);
        assertThat(workpackage.getOwnedTasks()).hasSize(2);

        service.createTask(task1);
        assertThat(workpackage.getOwnedTasks()).hasSize(3);
        assertThat(workpackage.getOwnedTasks().get(1).getStartTime()).isEqualTo(Instant.ofEpochSecond(1704157260));
        assertThat(workpackage.getOwnedTasks().get(1).getEndTime()).isEqualTo(Instant.ofEpochSecond(2L * 1704157260 - 1704067200));

        service.createTask(task11);
        assertThat(task1.getSubTasks()).hasSize(2);
        assertThat(task1.getSubTasks().get(1).getStartTime()).isEqualTo(Instant.ofEpochSecond(1704157260));
        assertThat(task1.getSubTasks().get(1).getEndTime()).isEqualTo(Instant.ofEpochSecond(2L * 1704157260 - 1704067200));
    }


    @Test
    public void checkTags() {
        Organization organization = new TaskExampleBuilder().getSampleContent();
        Project projectDaily = organization.getOwnedProjects().get(1);
        TagFolder tagFolder = projectDaily.getOwnedTagFolders().get(0);
        TaskTag tagDailyMonday = tagFolder.getOwnedTags().get(0);
        Workpackage workpackage = projectDaily.getOwnedWorkpackages().get(0);

        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());
        List<Task> tasksWithTag = service.getTasksWithTag(tagDailyMonday, workpackage);
        assertThat(tasksWithTag).hasSize(2);
        Task ideaTask = tasksWithTag.get(0);
        Task specificationTask = tasksWithTag.get(1);

        service.moveTagAtIndex(tagDailyMonday, 2);
        assertThat(tagDailyMonday).isEqualTo(tagFolder.getOwnedTags().get(2));

        assertThat(tasksWithTag.indexOf(ideaTask)).isEqualTo(0);
        service.moveTaskInTag(ideaTask, 1, tagDailyMonday);
        assertThat(workpackage.getOwnedTasks().indexOf(ideaTask)).isEqualTo(1);
        assertThat(workpackage.getOwnedTasks().indexOf(specificationTask)).isEqualTo(0);

        Task developmentTask = workpackage.getOwnedTasks().get(2);
        Task codeDev = developmentTask.getSubTasks().get(0);
        Task review = developmentTask.getSubTasks().get(1);
        codeDev.getTags().clear();
        codeDev.getTags().add(review.getTags().get(0));
        service.moveTaskInTag(codeDev, 1, review.getTags().get(0));
        assertThat(developmentTask.getSubTasks().indexOf(codeDev)).isEqualTo(1);
        assertThat(developmentTask.getSubTasks().indexOf(review)).isEqualTo(0);
    }
}
