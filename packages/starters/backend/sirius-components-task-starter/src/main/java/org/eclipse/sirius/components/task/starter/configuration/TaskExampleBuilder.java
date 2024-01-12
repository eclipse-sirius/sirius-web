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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.task.Company;
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

    private static final String DATE_2023_12_18T08_30_00Z = "2023-12-18T08:30:00Z";
    private static final String DATE_2023_12_16T17_30_00Z = "2023-12-16T17:30:00Z";
    private static final String DATE_2023_12_13T08_30_00Z = "2023-12-13T08:30:00Z";

    public EObject getContent() {
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
        company.getOwnedProjects().addAll(List.of(devProject, dailyProject));

        return company;
    }


    private Project createDevProject(Person paul, Person peter) {
        Project devProject = TaskFactory.eINSTANCE.createProject();
        devProject.setName("Project Dev");

        Task idea = TaskFactory.eINSTANCE.createTask();
        idea.setName("Idea");
        idea.setStartTime(Instant.parse("2023-12-10T08:30:00Z"));
        idea.setEndTime(Instant.parse("2023-12-11T17:30:00Z"));
        idea.setProgress(50);
        Task spec = TaskFactory.eINSTANCE.createTask();
        spec.setName("Specification");
        spec.setStartTime(Instant.parse("2023-12-11T08:30:00Z"));
        spec.setEndTime(Instant.parse("2023-12-12T17:30:00Z"));
        spec.setProgress(50);
        spec.getDependencies().add(idea);

        Task development = TaskFactory.eINSTANCE.createTask();
        development.setName("Development");
        development.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        development.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getDependencies().add(spec);

        Task codeDev = TaskFactory.eINSTANCE.createTask();
        codeDev.setName("Code Development");
        codeDev.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        codeDev.setEndTime(Instant.parse("2023-12-15T17:30:00Z"));
        codeDev.getAssignedPersons().add(peter);
        Task review = TaskFactory.eINSTANCE.createTask();
        review.setName("Review");
        review.setStartTime(Instant.parse("2023-12-16T08:30:00Z"));
        review.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);

        Task release = TaskFactory.eINSTANCE.createTask();
        release.setName("Release");
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
        idea.setName("Idea");
        idea.setStartTime(Instant.parse("2023-12-10T08:30:00Z"));
        idea.setEndTime(Instant.parse("2023-12-11T17:30:00Z"));
        idea.setProgress(50);
        idea.getTags().add(dailyTags.get(0));
        Task spec = TaskFactory.eINSTANCE.createTask();
        spec.setName("Specification");
        spec.setStartTime(Instant.parse("2023-12-11T08:30:00Z"));
        spec.setEndTime(Instant.parse("2023-12-12T17:30:00Z"));
        spec.setProgress(50);
        spec.getDependencies().add(idea);
        spec.getTags().add(dailyTags.get(0));

        Task development = TaskFactory.eINSTANCE.createTask();
        development.setName("Development");
        development.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        development.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getDependencies().add(spec);
        development.getTags().add(dailyTags.get(1));

        Task codeDev = TaskFactory.eINSTANCE.createTask();
        codeDev.setName("Code Development");
        codeDev.setStartTime(Instant.parse(DATE_2023_12_13T08_30_00Z));
        codeDev.setEndTime(Instant.parse("2023-12-15T17:30:00Z"));
        codeDev.getAssignedPersons().add(peter);
        codeDev.getTags().add(dailyTags.get(2));

        Task review = TaskFactory.eINSTANCE.createTask();
        review.setName("Review");
        review.setStartTime(Instant.parse("2023-12-16T08:30:00Z"));
        review.setEndTime(Instant.parse(DATE_2023_12_16T17_30_00Z));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);
        review.getTags().add(dailyTags.get(3));

        Task release = TaskFactory.eINSTANCE.createTask();
        release.setName("Release");
        release.setStartTime(Instant.parse(DATE_2023_12_18T08_30_00Z));
        release.setEndTime(Instant.parse(DATE_2023_12_18T08_30_00Z));
        release.getTags().add(dailyTags.get(4));


        dailyProject.getOwnedTasks().addAll(List.of(idea, spec, development, release));
        return dailyProject;
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
}
