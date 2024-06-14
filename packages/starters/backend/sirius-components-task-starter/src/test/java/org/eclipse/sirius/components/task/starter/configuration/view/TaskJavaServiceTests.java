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
package org.eclipse.sirius.components.task.starter.configuration.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.task.AbstractTask;
import org.eclipse.sirius.components.task.Company;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskFactory;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.components.task.starter.configuration.TaskExampleBuilder;
import org.junit.jupiter.api.Test;

/**
 * Test used to validate the service for the task related views.
 *
 * @author frouene
 */
class TaskJavaServiceTests {

    private static final String NEW_NAME = "newName";
    private static final String NEW_DESCRIPTION = "newDescription";

    @Test
    void editTask() {
        AbstractTask task = TaskFactory.eINSTANCE.createTask();
        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());
        service.editTask(task, NEW_NAME, NEW_DESCRIPTION, Instant.ofEpochSecond(1704067200), Instant.ofEpochSecond(1704070800), 10);
        assertThat(task.getName()).isEqualTo(NEW_NAME);
        assertThat(task.getDescription()).isEqualTo(NEW_DESCRIPTION);
        assertThat(task.getStartTime().getEpochSecond()).isEqualTo(1704067200);
        assertThat(task.getEndTime().getEpochSecond()).isEqualTo(1704070800);
        assertThat(task.getProgress()).isEqualTo(10);
    }

    @Test
    void computeTaskDurationDays() {
        Task task = TaskFactory.eINSTANCE.createTask();
        task.setStartTime(Instant.ofEpochSecond(1704067200));
        task.setEndTime(Instant.ofEpochSecond(1704157260));
        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());
        var result = service.computeTaskDurationDays(task);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("01d01h01m");
    }

    @Test
    void editCard() {
        AbstractTask card = TaskFactory.eINSTANCE.createTask();
        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());
        service.editCard(card, NEW_NAME, NEW_DESCRIPTION, null);
        assertThat(card.getName()).isEqualTo(NEW_NAME);
        assertThat(card.getDescription()).isEqualTo(NEW_DESCRIPTION);
    }

    @Test
    void createCard() {
        TaskTag tag = TaskFactory.eINSTANCE.createTaskTag();
        Project project = TaskFactory.eINSTANCE.createProject();
        project.getOwnedTags().add(tag);
        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());
        service.createCard(tag);
        assertThat(project.getOwnedTasks()).hasSize(1);
        assertThat(project.getOwnedTasks().get(0).getName()).isEqualTo("New Task");
        assertThat(project.getOwnedTasks().get(0).getDescription()).isEqualTo("new description");
        assertThat(project.getOwnedTasks().get(0).getTags()).hasSize(1);
    }

    @Test
    void createTask() {
        Task task11 = TaskFactory.eINSTANCE.createTask();
        task11.setStartTime(Instant.ofEpochSecond(1704067200));
        task11.setEndTime(Instant.ofEpochSecond(1704157260));

        Task task1 = TaskFactory.eINSTANCE.createTask();
        task1.setStartTime(Instant.ofEpochSecond(1704067200));
        task1.setEndTime(Instant.ofEpochSecond(1704157260));
        task1.getSubTasks().add(task11);

        Project project = TaskFactory.eINSTANCE.createProject();
        project.getOwnedTasks().add(task1);
        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());

        service.createTask(project);
        assertThat(project.getOwnedTasks()).hasSize(2);

        service.createTask(task1);
        assertThat(project.getOwnedTasks()).hasSize(3);
        assertThat(project.getOwnedTasks().get(1).getStartTime()).isEqualTo(Instant.ofEpochSecond(1704157260));
        assertThat(project.getOwnedTasks().get(1).getEndTime()).isEqualTo(Instant.ofEpochSecond(2L * 1704157260 - 1704067200));

        service.createTask(task11);
        assertThat(task1.getSubTasks()).hasSize(2);
        assertThat(task1.getSubTasks().get(1).getStartTime()).isEqualTo(Instant.ofEpochSecond(1704157260));
        assertThat(task1.getSubTasks().get(1).getEndTime()).isEqualTo(Instant.ofEpochSecond(2L * 1704157260 - 1704067200));
    }


    @Test
    void checkTags() {
        Company company = new TaskExampleBuilder().getContent();
        Project projectDaily = company.getOwnedProjects().get(1);
        TaskTag tagDailyMonday = projectDaily.getOwnedTags().get(0);

        var service = new TaskJavaService(new IFeedbackMessageService.NoOp());
        List<Task> tasksWithTag = service.getTasksWithTag(tagDailyMonday);
        assertThat(tasksWithTag).hasSize(2);
        Task ideaTask = tasksWithTag.get(0);
        Task specificationTask = tasksWithTag.get(1);

        service.moveTagAtIndex(tagDailyMonday, 2);
        assertThat(tagDailyMonday).isEqualTo(projectDaily.getOwnedTags().get(2));

        assertThat(tasksWithTag.indexOf(ideaTask)).isEqualTo(0);
        service.moveTaskInTag(ideaTask, 1, tagDailyMonday);
        assertThat(projectDaily.getOwnedTasks().indexOf(ideaTask)).isEqualTo(1);
        assertThat(projectDaily.getOwnedTasks().indexOf(specificationTask)).isEqualTo(0);

        Task developmentTask = projectDaily.getOwnedTasks().get(2);
        Task codeDev = developmentTask.getSubTasks().get(0);
        Task review = developmentTask.getSubTasks().get(1);
        codeDev.getTags().clear();
        codeDev.getTags().add(review.getTags().get(0));
        service.moveTaskInTag(codeDev, 1, review.getTags().get(0));
        assertThat(developmentTask.getSubTasks().indexOf(codeDev)).isEqualTo(1);
        assertThat(developmentTask.getSubTasks().indexOf(review)).isEqualTo(0);
    }
}
