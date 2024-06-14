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
package org.eclipse.sirius.components.task.starter.configuration;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.sirius.components.task.Company;
import org.eclipse.sirius.components.task.KeyResult;
import org.eclipse.sirius.components.task.Objective;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskFactory;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.components.task.Team;

/**
 * Builder for an example of task model.
 *
 * @author lfasani
 */
public class TaskExampleBuilder {

    private static final String KANBAN = "kanban";
    private static final String REVIEW = "Review";
    private static final String DEVELOPMENT = "Development";
    private static final String CODE_DEVELOPMENT = "Code Development";
    private static final String RELEASE = "Release";
    private static final String SPECIFICATION = "Specification";
    private static final String IDEA = "Idea";
    private static final String DATE_2023_12_10T08_30_00Z = "2023-12-10T08:30:00Z";
    private static final String DATE_2023_12_16T08_30_00Z = "2023-12-16T08:30:00Z";
    private static final String DATE_2023_12_11T08_30_00Z = "2023-12-11T08:30:00Z";
    private static final String DATE_2023_12_12T17_30_00Z = "2023-12-12T17:30:00Z";
    private static final String DATE_2023_12_15T17_30_00Z = "2023-12-15T17:30:00Z";
    private static final String DATE_2023_12_11T17_30_00Z = "2023-12-11T17:30:00Z";
    private static final String DATE_2023_12_13T08_30_00Z = "2023-12-13T08:30:00Z";
    private static final String DATE_2023_12_16T17_30_00Z = "2023-12-16T17:30:00Z";
    private static final String DATE_2023_12_18T08_30_00Z = "2023-12-18T08:30:00Z";

    public Company getContent() {
        Company company = TaskFactory.eINSTANCE.createCompany();

        Person paul = TaskFactory.eINSTANCE.createPerson();
        paul.setName("Paul");
        Person peter = TaskFactory.eINSTANCE.createPerson();
        peter.setName("Peter");
        company.getOwnedPersons().addAll(List.of(paul, peter));

        Team devTeam = TaskFactory.eINSTANCE.createTeam();
        devTeam.setName("Development Team");
        company.getOwnedTeams().add(devTeam);
        devTeam.getMembers().addAll(List.of(paul, peter));

        Project devProject = this.createDevProject(paul, peter);
        Project dailyProject = this.createDailyProject(paul, peter);
        Project okrProject = this.createOKRProject(paul, peter);
        Project kanbanProject = this.createKanbanProject(paul, peter);
        company.getOwnedProjects().addAll(List.of(devProject, dailyProject, okrProject, kanbanProject));

        return company;
    }


    private Project createDevProject(Person paul, Person peter) {
        Project devProject = TaskFactory.eINSTANCE.createProject();
        devProject.setName("Project Dev");

        Task idea = TaskFactory.eINSTANCE.createTask();
        idea.setName(IDEA);
        idea.setDescription("Description of the Idea");
        idea.setStartTime(Instant.parse("2023-12-10T08:30:00Z"));
        idea.setEndTime(Instant.parse("2023-12-11T17:30:00Z"));
        idea.setProgress(50);
        Task spec = TaskFactory.eINSTANCE.createTask();
        spec.setName(SPECIFICATION);
        spec.setDescription("Description of the Specification");
        spec.setStartTime(Instant.parse("2023-12-11T08:30:00Z"));
        spec.setEndTime(Instant.parse("2023-12-12T17:30:00Z"));
        spec.setProgress(50);
        spec.getDependencies().add(idea);

        Task development = TaskFactory.eINSTANCE.createTask();
        development.setName(DEVELOPMENT);
        development.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        development.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getDependencies().add(spec);
        development.setComputeStartEndDynamically(true);

        Task codeDev = TaskFactory.eINSTANCE.createTask();
        codeDev.setName(CODE_DEVELOPMENT);
        codeDev.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        codeDev.setEndTime(Instant.parse(DATE_2023_12_15T17_30_00Z));
        codeDev.getAssignedPersons().add(peter);
        codeDev.setComputeStartEndDynamically(false);
        codeDev.setProgress(40);
        Task frontDev = TaskFactory.eINSTANCE.createTask();
        frontDev.setName("Front");
        frontDev.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        frontDev.setEndTime(Instant.parse("2023-12-14T17:30:00Z"));
        frontDev.setProgress(30);
        frontDev.getAssignedPersons().add(peter);
        Task backDev = TaskFactory.eINSTANCE.createTask();
        backDev.setName("Back");
        backDev.setStartTime(Instant.parse("2023-12-14T14:00:00Z"));
        backDev.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        backDev.setProgress(40);
        backDev.getAssignedPersons().add(paul);
        codeDev.getSubTasks().addAll(List.of(frontDev, backDev));

        Task review = TaskFactory.eINSTANCE.createTask();
        review.setName(REVIEW);
        review.setStartTime(Instant.parse(DATE_2023_12_16T08_30_00Z));
        review.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);

        Task release = TaskFactory.eINSTANCE.createTask();
        release.setName(RELEASE);
        release.setStartTime(Instant.parse(DATE_2023_12_18T08_30_00Z));
        release.setEndTime(Instant.parse(DATE_2023_12_18T08_30_00Z));

        devProject.getOwnedTasks().addAll(List.of(idea, spec, development, release));
        return devProject;
    }

    private Project createDailyProject(Person paul, Person peter) {
        Project dailyProject = TaskFactory.eINSTANCE.createProject();
        dailyProject.setName("Daily Project Dev");

        List<TaskTag> dailyTags = this.createDailyTags();
        dailyProject.getOwnedTags().addAll(dailyTags);

        Task idea = TaskFactory.eINSTANCE.createTask();
        idea.setName(IDEA);
        idea.setStartTime(Instant.parse(DATE_2023_12_10T08_30_00Z));
        idea.setEndTime(Instant.parse(DATE_2023_12_11T17_30_00Z));
        idea.setProgress(50);
        idea.getTags().add(dailyTags.get(0));
        Task spec = TaskFactory.eINSTANCE.createTask();
        spec.setName(SPECIFICATION);
        spec.setStartTime(Instant.parse(DATE_2023_12_11T08_30_00Z));
        spec.setEndTime(Instant.parse(DATE_2023_12_12T17_30_00Z));
        spec.setProgress(50);
        spec.getDependencies().add(idea);
        spec.getTags().add(dailyTags.get(0));

        Task development = TaskFactory.eINSTANCE.createTask();
        development.setName(DEVELOPMENT);
        development.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        development.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getDependencies().add(spec);
        development.getTags().add(dailyTags.get(1));

        Task codeDev = TaskFactory.eINSTANCE.createTask();
        codeDev.setName(CODE_DEVELOPMENT);
        codeDev.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        codeDev.setEndTime(Instant.parse(DATE_2023_12_15T17_30_00Z));
        codeDev.getAssignedPersons().add(peter);
        codeDev.getTags().add(dailyTags.get(2));

        Task review = TaskFactory.eINSTANCE.createTask();
        review.setName(REVIEW);
        review.setStartTime(Instant.parse(DATE_2023_12_16T08_30_00Z));
        review.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);
        review.getTags().add(dailyTags.get(3));

        Task release = TaskFactory.eINSTANCE.createTask();
        release.setName(RELEASE);
        release.setStartTime(Instant.parse(DATE_2023_12_18T08_30_00Z));
        release.setEndTime(Instant.parse(DATE_2023_12_18T08_30_00Z));
        release.getTags().add(dailyTags.get(4));


        dailyProject.getOwnedTasks().addAll(List.of(idea, spec, development, release));
        return dailyProject;
    }

    private Project createKanbanProject(Person paul, Person peter) {
        Project kanbanProject = TaskFactory.eINSTANCE.createProject();
        kanbanProject.setName("Kanban Project Dev");

        List<TaskTag> kanbanTags = this.createKanbanTags();
        kanbanProject.getOwnedTags().addAll(kanbanTags);

        Task idea = TaskFactory.eINSTANCE.createTask();
        idea.setName(IDEA);
        idea.setStartTime(Instant.parse(DATE_2023_12_10T08_30_00Z));
        idea.setEndTime(Instant.parse(DATE_2023_12_11T17_30_00Z));
        idea.setProgress(50);
        //We add it in Done tag
        idea.getTags().add(kanbanTags.get(2));
        Task spec = TaskFactory.eINSTANCE.createTask();
        spec.setName(SPECIFICATION);
        spec.setStartTime(Instant.parse(DATE_2023_12_11T08_30_00Z));
        spec.setEndTime(Instant.parse(DATE_2023_12_12T17_30_00Z));
        spec.setProgress(50);
        spec.getDependencies().add(idea);
        //We add it in Done tag
        spec.getTags().add(kanbanTags.get(2));

        Task development = TaskFactory.eINSTANCE.createTask();
        development.setName(DEVELOPMENT);
        development.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        development.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getDependencies().add(spec);
        //We add it in Doing tag
        development.getTags().add(kanbanTags.get(1));

        Task codeDev = TaskFactory.eINSTANCE.createTask();
        codeDev.setName(CODE_DEVELOPMENT);
        codeDev.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        codeDev.setEndTime(Instant.parse(DATE_2023_12_15T17_30_00Z));
        codeDev.getAssignedPersons().add(peter);
        //We add it in Doing tag
        codeDev.getTags().add(kanbanTags.get(1));

        Task review = TaskFactory.eINSTANCE.createTask();
        review.setName(REVIEW);
        review.setStartTime(Instant.parse(DATE_2023_12_16T08_30_00Z));
        review.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);
        //We add it in To Do tag
        review.getTags().add(kanbanTags.get(0));

        Task release = TaskFactory.eINSTANCE.createTask();
        release.setName(RELEASE);
        release.setStartTime(Instant.parse(DATE_2023_12_18T08_30_00Z));
        release.setEndTime(Instant.parse(DATE_2023_12_18T08_30_00Z));
        //We add it in To Do tag
        release.getTags().add(kanbanTags.get(0));


        kanbanProject.getOwnedTasks().addAll(List.of(idea, spec, development, release));
        return kanbanProject;
    }

    private Project createOKRProject(Person paul, Person peter) {
        Project okrProject = TaskFactory.eINSTANCE.createProject();
        okrProject.setName("OKR Project Dev");

        Objective objectiveAppicationRunning = TaskFactory.eINSTANCE.createObjective();
        objectiveAppicationRunning.setName("Have the application running");

        KeyResult keyResultDevCompleted = TaskFactory.eINSTANCE.createKeyResult();
        keyResultDevCompleted.setName("Dev completed");
        keyResultDevCompleted.setDescription("The development is completed");

        Task idea = TaskFactory.eINSTANCE.createTask();
        idea.setName(IDEA);
        idea.setStartTime(Instant.parse(DATE_2023_12_10T08_30_00Z));
        idea.setEndTime(Instant.parse(DATE_2023_12_11T17_30_00Z));
        idea.setProgress(50);
        Task spec = TaskFactory.eINSTANCE.createTask();
        spec.setName(SPECIFICATION);
        spec.setStartTime(Instant.parse(DATE_2023_12_11T08_30_00Z));
        spec.setEndTime(Instant.parse(DATE_2023_12_12T17_30_00Z));
        spec.setProgress(50);
        spec.getDependencies().add(idea);

        Task development = TaskFactory.eINSTANCE.createTask();
        development.setName(DEVELOPMENT);
        development.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        development.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getDependencies().add(spec);

        Task codeDev = TaskFactory.eINSTANCE.createTask();
        codeDev.setName(CODE_DEVELOPMENT);
        codeDev.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        codeDev.setEndTime(Instant.parse(DATE_2023_12_15T17_30_00Z));
        codeDev.getAssignedPersons().add(peter);

        Task review = TaskFactory.eINSTANCE.createTask();
        review.setName(REVIEW);
        review.setStartTime(Instant.parse(DATE_2023_12_16T08_30_00Z));
        review.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);

        keyResultDevCompleted.getSubTasks().addAll(List.of(idea, spec, development));

        KeyResult keyResultTestsOK = TaskFactory.eINSTANCE.createKeyResult();
        keyResultTestsOK.setName("Tests passed");
        keyResultTestsOK.setDescription("The tests are all passed");

        Task manualsTest = TaskFactory.eINSTANCE.createTask();
        manualsTest.setName("Manual Test Campaign");
        Task automaticTests = TaskFactory.eINSTANCE.createTask();
        manualsTest.setName("Automatic Test Checks");

        keyResultTestsOK.getSubTasks().addAll(List.of(manualsTest, automaticTests));

        objectiveAppicationRunning.getOwnedKeyResults().addAll(List.of(keyResultDevCompleted, keyResultTestsOK));
        okrProject.getOwnedObjectives().add(objectiveAppicationRunning);

        return okrProject;
    }

    private List<TaskTag> createDailyTags() {
        List<TaskTag> tags = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            TaskTag tag = TaskFactory.eINSTANCE.createTaskTag();
            tag.setPrefix("daily");
            tag.setSuffix(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            tags.add(tag);
        }
        return tags;
    }

    private List<TaskTag> createKanbanTags() {
        List<TaskTag> tags = new ArrayList<>();
        TaskTag tag = TaskFactory.eINSTANCE.createTaskTag();
        tag.setPrefix(KANBAN);
        tag.setSuffix("To Do");
        tags.add(tag);

        tag = TaskFactory.eINSTANCE.createTaskTag();
        tag.setPrefix(KANBAN);
        tag.setSuffix("Doing");
        tags.add(tag);

        tag = TaskFactory.eINSTANCE.createTaskTag();
        tag.setPrefix(KANBAN);
        tag.setSuffix("Done");
        tags.add(tag);

        return tags;
    }
}
