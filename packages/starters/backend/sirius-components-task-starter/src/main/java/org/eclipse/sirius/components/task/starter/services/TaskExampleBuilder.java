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
package org.eclipse.sirius.components.task.starter.services;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pepper.peppermm.DependencyLink;
import pepper.peppermm.ExternalStakeholder;
import pepper.peppermm.InternalStakeholder;
import pepper.peppermm.KeyResult;
import pepper.peppermm.Objective;
import pepper.peppermm.Organization;
import pepper.peppermm.PepperFactory;
import pepper.peppermm.Person;
import pepper.peppermm.Project;
import pepper.peppermm.ResourceFolder;
import pepper.peppermm.TagFolder;
import pepper.peppermm.Task;
import pepper.peppermm.TaskTag;
import pepper.peppermm.TaskTimeBoundariesConstraint;
import pepper.peppermm.Team;
import pepper.peppermm.Workpackage;
import pepper.peppermm.WorkpackageArtefact;

/**
 * Builder for an example of task model.
 *
 * @author lfasani
 */
public class TaskExampleBuilder {

    private static final ZonedDateTime ZONED_DATE_TIME = LocalDateTime
            .of(2023, 12, 10, 0, 0)
            .atZone(ZoneId.systemDefault());
    private static final String ZONE = ZONED_DATE_TIME.getOffset().toString();

    private static final String KANBAN = "kanban";
    private static final String REVIEW = "Review";
    private static final String DEVELOPMENT = "Development";
    private static final String CODE_DEVELOPMENT = "Code Development";
    private static final String RELEASE = "Release";
    private static final String SPECIFICATION = "Specification";
    private static final String IDEA = "Idea";

    private static final String DATE_2023_12_10T00_00_00 = "2023-12-10T00:00:00" + ZONE;
    private static final String DATE_2023_12_11T00_00_00 = "2023-12-11T00:00:00" + ZONE;
    private static final String DATE_2023_12_11T23_59_00 = "2023-12-11T23:59:00" + ZONE;
    private static final String DATE_2023_12_12T23_59_00 = "2023-12-12T23:59:00" + ZONE;
    private static final String DATE_2023_12_13T00_00_00 = "2023-12-13T00:00:00" + ZONE;
    private static final String DATE_2023_12_14T00_00_00 = "2023-12-14T12:00:00" + ZONE;
    private static final String DATE_2023_12_14T23_59_00 = "2023-12-14T23:59:00" + ZONE;
    private static final String DATE_2023_12_15T23_59_00 = "2023-12-15T23:59:00" + ZONE;
    private static final String DATE_2023_12_16T00_00_00 = "2023-12-16T00:00:00" + ZONE;
    private static final String DATE_2023_12_16T23_59_00 = "2023-12-16T23:59:00" + ZONE;
    private static final String DATE_2023_12_18T00_00_00 = "2023-12-18T00:00:00" + ZONE;
    private static final String DATE_2023_12_10 = "2023-12-10";
    private static final String DATE_2023_30_10 = "2023-12-30";

    private static final String MAIN_WORKPACKAGE = "Main workpackage";

    public Organization getSampleContent() {
        Organization organization = PepperFactory.eINSTANCE.createOrganization();
        ResourceFolder resourceFolder = PepperFactory.eINSTANCE.createResourceFolder();
        resourceFolder.setName("Resources");
        organization.getOwnedResourceFolders().add(resourceFolder);

        Person paul = PepperFactory.eINSTANCE.createPerson();
        paul.setName("Paul");
        Person peter = PepperFactory.eINSTANCE.createPerson();
        peter.setName("Peter");
        resourceFolder.getOwnedResources().addAll(List.of(paul, peter));

        Team devTeam = PepperFactory.eINSTANCE.createTeam();
        devTeam.setName("Development Team");
        resourceFolder.getOwnedResources().add(devTeam);
        devTeam.getMembers().addAll(List.of(paul, peter));

        InternalStakeholder internalStakeholder1 = PepperFactory.eINSTANCE.createInternalStakeholder();
        internalStakeholder1.setName("Indus Department");
        InternalStakeholder internalStakeholder2 = PepperFactory.eINSTANCE.createInternalStakeholder();
        internalStakeholder2.setName("Dev Department");
        ExternalStakeholder externalStakeholder = PepperFactory.eINSTANCE.createExternalStakeholder();
        externalStakeholder.setName("Customer 1");
        ExternalStakeholder externalStakeholder2 = PepperFactory.eINSTANCE.createExternalStakeholder();
        externalStakeholder2.setName("Customer 2");
        resourceFolder.getOwnedResources().addAll(List.of(internalStakeholder1, internalStakeholder2, externalStakeholder, externalStakeholder2));

        Project devProject = this.createDevProject(paul, peter);
        Project dailyProject = this.createDailyProject(paul, peter);
        Project okrProject = this.createOKRProject(paul, peter);
        Project kanbanProject = this.createKanbanProject(paul, peter);
        organization.getOwnedProjects().addAll(List.of(devProject, dailyProject, okrProject, kanbanProject));

        return organization;
    }


    private Task createCodeDev(Person peter) {
        Task codeDev = PepperFactory.eINSTANCE.createTask();
        codeDev.setName(CODE_DEVELOPMENT);
        codeDev.setStartTime(Instant.parse(DATE_2023_12_13T00_00_00));
        codeDev.setEndTime(Instant.parse(DATE_2023_12_15T23_59_00));
        codeDev.getAssignedPersons().add(peter);
        codeDev.setComputeStartEndDynamically(false);
        codeDev.setProgress(40);
        return codeDev;
    }

    private Project createDevProject(Person paul, Person peter) {
        Project devProject = PepperFactory.eINSTANCE.createProject();
        devProject.setName("Project Dev");
        Workpackage workpackage = PepperFactory.eINSTANCE.createWorkpackage();
        workpackage.setName(MAIN_WORKPACKAGE);
        workpackage.setStartDate(LocalDate.parse(DATE_2023_12_10));
        workpackage.setEndDate(LocalDate.parse(DATE_2023_30_10));
        workpackage.setCalculationOption(TaskTimeBoundariesConstraint.START_END);
        devProject.getOwnedWorkpackages().add(workpackage);

        Task idea = PepperFactory.eINSTANCE.createTask();
        idea.setName(IDEA);
        idea.setDescription("Description of the Idea");
        idea.setStartTime(Instant.parse(DATE_2023_12_10T00_00_00));
        idea.setEndTime(Instant.parse(DATE_2023_12_11T23_59_00));
        idea.setProgress(50);
        Task spec = PepperFactory.eINSTANCE.createTask();
        spec.setName(SPECIFICATION);
        spec.setDescription("Description of the Specification");
        spec.setStartTime(Instant.parse(DATE_2023_12_11T00_00_00));
        spec.setEndTime(Instant.parse(DATE_2023_12_12T23_59_00));
        spec.setProgress(50);

        DependencyLink depSpecToIdea = PepperFactory.eINSTANCE.createDependencyLink();
        depSpecToIdea.setSource(idea);
        spec.getDependencies().add(depSpecToIdea);

        Task development = PepperFactory.eINSTANCE.createTask();
        development.setName(DEVELOPMENT);
        development.setStartTime(Instant.parse(DATE_2023_12_13T00_00_00));
        development.setEndTime(Instant.parse(DATE_2023_12_16T23_59_00));
        development.setComputeStartEndDynamically(true);

        DependencyLink depDevelopmentToSpec = PepperFactory.eINSTANCE.createDependencyLink();
        depDevelopmentToSpec.setSource(spec);
        development.getDependencies().add(depDevelopmentToSpec);

        Task codeDev = createCodeDev(peter);
        Task frontDev = PepperFactory.eINSTANCE.createTask();
        frontDev.setName("Front");
        frontDev.setStartTime(Instant.parse(DATE_2023_12_13T00_00_00));
        frontDev.setEndTime(Instant.parse(DATE_2023_12_14T23_59_00));
        frontDev.setProgress(30);
        frontDev.getAssignedPersons().add(peter);
        Task backDev = PepperFactory.eINSTANCE.createTask();
        backDev.setName("Back");
        backDev.setStartTime(Instant.parse(DATE_2023_12_14T00_00_00));
        backDev.setEndTime(Instant.parse(DATE_2023_12_16T23_59_00));
        backDev.setProgress(40);
        backDev.getAssignedPersons().add(paul);
        codeDev.getSubTasks().addAll(List.of(frontDev, backDev));

        Task review = PepperFactory.eINSTANCE.createTask();
        review.setName(REVIEW);
        review.setStartTime(Instant.parse(DATE_2023_12_16T00_00_00));
        review.setEndTime(Instant.parse(DATE_2023_12_16T23_59_00));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);

        Task release = PepperFactory.eINSTANCE.createTask();
        release.setName(RELEASE);
        release.setStartTime(Instant.parse(DATE_2023_12_18T00_00_00));
        release.setEndTime(Instant.parse(DATE_2023_12_18T00_00_00));

        workpackage.getOwnedTasks().addAll(List.of(idea, spec, development, release));
        return devProject;
    }

    private Project createDailyProject(Person paul, Person peter) {
        Project dailyProject = PepperFactory.eINSTANCE.createProject();
        dailyProject.setName("Daily Project Dev");
        Workpackage workpackage = PepperFactory.eINSTANCE.createWorkpackage();
        workpackage.setName(MAIN_WORKPACKAGE);
        workpackage.setStartDate(LocalDate.parse(DATE_2023_12_10));
        workpackage.setEndDate(LocalDate.parse(DATE_2023_30_10));
        dailyProject.getOwnedWorkpackages().add(workpackage);
        TagFolder tagFolder = PepperFactory.eINSTANCE.createTagFolder();
        tagFolder.setName("Tags");
        dailyProject.getOwnedTagFolders().add(tagFolder);

        List<TaskTag> dailyTags = this.createDailyTags();
        tagFolder.getOwnedTags().addAll(dailyTags);

        Task idea = PepperFactory.eINSTANCE.createTask();
        idea.setName(IDEA);
        idea.setStartTime(Instant.parse(DATE_2023_12_10T00_00_00));
        idea.setEndTime(Instant.parse(DATE_2023_12_11T23_59_00));
        idea.setProgress(50);
        idea.getTags().add(dailyTags.get(0));
        Task spec = PepperFactory.eINSTANCE.createTask();
        spec.setName(SPECIFICATION);
        spec.setStartTime(Instant.parse(DATE_2023_12_11T00_00_00));
        spec.setEndTime(Instant.parse(DATE_2023_12_12T23_59_00));
        spec.setProgress(50);
        spec.getTags().add(dailyTags.get(0));

        DependencyLink depSpecToIdea = PepperFactory.eINSTANCE.createDependencyLink();
        depSpecToIdea.setSource(idea);
        spec.getDependencies().add(depSpecToIdea);

        Task development = PepperFactory.eINSTANCE.createTask();
        development.setName(DEVELOPMENT);
        development.setStartTime(Instant.parse(DATE_2023_12_13T00_00_00));
        development.setEndTime(Instant.parse(DATE_2023_12_16T23_59_00));
        DependencyLink depDevelopmentToSpec = PepperFactory.eINSTANCE.createDependencyLink();
        depDevelopmentToSpec.setSource(spec);
        development.getDependencies().add(depDevelopmentToSpec);
        development.getTags().add(dailyTags.get(1));

        Task codeDev = PepperFactory.eINSTANCE.createTask();
        codeDev.setName(CODE_DEVELOPMENT);
        codeDev.setStartTime(Instant.parse(DATE_2023_12_13T00_00_00));
        codeDev.setEndTime(Instant.parse(DATE_2023_12_15T23_59_00));
        codeDev.getAssignedPersons().add(peter);
        codeDev.getTags().add(dailyTags.get(2));

        Task review = PepperFactory.eINSTANCE.createTask();
        review.setName(REVIEW);
        review.setStartTime(Instant.parse(DATE_2023_12_16T00_00_00));
        review.setEndTime(Instant.parse(DATE_2023_12_16T23_59_00));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);
        review.getTags().add(dailyTags.get(3));

        Task release = PepperFactory.eINSTANCE.createTask();
        release.setName(RELEASE);
        release.setStartTime(Instant.parse(DATE_2023_12_18T00_00_00));
        release.setEndTime(Instant.parse(DATE_2023_12_18T00_00_00));
        release.getTags().add(dailyTags.get(4));


        workpackage.getOwnedTasks().addAll(List.of(idea, spec, development, release));
        return dailyProject;
    }

    private Project createKanbanProject(Person paul, Person peter) {
        Project kanbanProject = PepperFactory.eINSTANCE.createProject();
        kanbanProject.setName("Kanban Project Dev");
        Workpackage workpackage = PepperFactory.eINSTANCE.createWorkpackage();
        workpackage.setName(MAIN_WORKPACKAGE);
        workpackage.setStartDate(LocalDate.parse(DATE_2023_12_10));
        workpackage.setEndDate(LocalDate.parse(DATE_2023_30_10));
        kanbanProject.getOwnedWorkpackages().add(workpackage);

        List<TaskTag> kanbanTags = this.createKanbanTags();
        TagFolder tagFolder = PepperFactory.eINSTANCE.createTagFolder();
        tagFolder.setName("Tags");
        kanbanProject.getOwnedTagFolders().add(tagFolder);
        tagFolder.getOwnedTags().addAll(kanbanTags);

        Task idea = PepperFactory.eINSTANCE.createTask();
        idea.setName(IDEA);
        idea.setStartTime(Instant.parse(DATE_2023_12_10T00_00_00));
        idea.setEndTime(Instant.parse(DATE_2023_12_11T23_59_00));
        idea.setProgress(50);
        //We add it in Done tag
        idea.getTags().add(kanbanTags.get(2));
        Task spec = PepperFactory.eINSTANCE.createTask();
        spec.setName(SPECIFICATION);
        spec.setStartTime(Instant.parse(DATE_2023_12_11T00_00_00));
        spec.setEndTime(Instant.parse(DATE_2023_12_12T23_59_00));
        spec.setProgress(50);
        DependencyLink depSpecToIdea = PepperFactory.eINSTANCE.createDependencyLink();
        depSpecToIdea.setSource(idea);
        spec.getDependencies().add(depSpecToIdea);        //We add it in Done tag
        spec.getTags().add(kanbanTags.get(2));

        Task development = PepperFactory.eINSTANCE.createTask();
        development.setName(DEVELOPMENT);
        development.setStartTime(Instant.parse(DATE_2023_12_13T00_00_00));
        development.setEndTime(Instant.parse(DATE_2023_12_16T23_59_00));
        DependencyLink depDevelopmentToSpec = PepperFactory.eINSTANCE.createDependencyLink();
        depDevelopmentToSpec.setSource(spec);
        development.getDependencies().add(depDevelopmentToSpec);
        //We add it in Doing tag
        development.getTags().add(kanbanTags.get(1));

        Task codeDev = PepperFactory.eINSTANCE.createTask();
        codeDev.setName(CODE_DEVELOPMENT);
        codeDev.setStartTime(Instant.parse(DATE_2023_12_13T00_00_00));
        codeDev.setEndTime(Instant.parse(DATE_2023_12_15T23_59_00));
        codeDev.getAssignedPersons().add(peter);
        //We add it in Doing tag
        codeDev.getTags().add(kanbanTags.get(1));

        Task review = PepperFactory.eINSTANCE.createTask();
        review.setName(REVIEW);
        review.setStartTime(Instant.parse(DATE_2023_12_16T00_00_00));
        review.setEndTime(Instant.parse(DATE_2023_12_16T23_59_00));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);
        //We add it in To Do tag
        review.getTags().add(kanbanTags.get(0));

        Task release = PepperFactory.eINSTANCE.createTask();
        release.setName(RELEASE);
        release.setStartTime(Instant.parse(DATE_2023_12_18T00_00_00));
        release.setEndTime(Instant.parse(DATE_2023_12_18T00_00_00));
        //We add it in To Do tag
        release.getTags().add(kanbanTags.get(0));


        workpackage.getOwnedTasks().addAll(List.of(idea, spec, development, release));
        return kanbanProject;
    }

    private Project createOKRProject(Person paul, Person peter) {
        Project okrProject = PepperFactory.eINSTANCE.createProject();
        okrProject.setName("OKR Project Dev");
        Workpackage workpackage = PepperFactory.eINSTANCE.createWorkpackage();
        workpackage.setName(MAIN_WORKPACKAGE);
        workpackage.setStartDate(LocalDate.parse(DATE_2023_12_10));
        workpackage.setEndDate(LocalDate.parse(DATE_2023_30_10));
        okrProject.getOwnedWorkpackages().add(workpackage);

        Objective objectiveApplicationRunning = PepperFactory.eINSTANCE.createObjective();
        objectiveApplicationRunning.setName("Have the application running");

        KeyResult keyResultDevCompleted = PepperFactory.eINSTANCE.createKeyResult();
        keyResultDevCompleted.setName("Dev completed");
        keyResultDevCompleted.setDescription("The development is completed");

        Task idea = PepperFactory.eINSTANCE.createTask();
        idea.setName(IDEA);
        idea.setStartTime(Instant.parse(DATE_2023_12_10T00_00_00));
        idea.setEndTime(Instant.parse(DATE_2023_12_11T23_59_00));
        idea.setProgress(50);
        Task spec = PepperFactory.eINSTANCE.createTask();
        spec.setName(SPECIFICATION);
        spec.setStartTime(Instant.parse(DATE_2023_12_11T00_00_00));
        spec.setEndTime(Instant.parse(DATE_2023_12_12T23_59_00));
        spec.setProgress(50);
        DependencyLink depSpecToIdea = PepperFactory.eINSTANCE.createDependencyLink();
        depSpecToIdea.setSource(idea);
        spec.getDependencies().add(depSpecToIdea);

        Task development = PepperFactory.eINSTANCE.createTask();
        development.setName(DEVELOPMENT);
        development.setStartTime(Instant.parse(DATE_2023_12_13T00_00_00));
        development.setEndTime(Instant.parse(DATE_2023_12_16T23_59_00));

        DependencyLink depDevelopmentToSpec = PepperFactory.eINSTANCE.createDependencyLink();
        depDevelopmentToSpec.setSource(spec);
        development.getDependencies().add(depDevelopmentToSpec);

        Task codeDev = PepperFactory.eINSTANCE.createTask();
        codeDev.setName(CODE_DEVELOPMENT);
        codeDev.setStartTime(Instant.parse(DATE_2023_12_13T00_00_00));
        codeDev.setEndTime(Instant.parse(DATE_2023_12_15T23_59_00));
        codeDev.getAssignedPersons().add(peter);

        Task review = PepperFactory.eINSTANCE.createTask();
        review.setName(REVIEW);
        review.setStartTime(Instant.parse(DATE_2023_12_16T00_00_00));
        review.setEndTime(Instant.parse(DATE_2023_12_16T23_59_00));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);

        keyResultDevCompleted.getSubTasks().addAll(List.of(idea, spec, development));

        KeyResult keyResultTestsOK = PepperFactory.eINSTANCE.createKeyResult();
        keyResultTestsOK.setName("Tests passed");
        keyResultTestsOK.setDescription("The tests are all passed");

        Task manualsTest = PepperFactory.eINSTANCE.createTask();
        manualsTest.setName("Manual Test Campaign");
        Task automaticTests = PepperFactory.eINSTANCE.createTask();
        manualsTest.setName("Automatic Test Checks");

        keyResultTestsOK.getSubTasks().addAll(List.of(manualsTest, automaticTests));

        objectiveApplicationRunning.getOwnedKeyResults().addAll(List.of(keyResultDevCompleted, keyResultTestsOK));
        okrProject.getOwnedObjectives().add(objectiveApplicationRunning);

        return okrProject;
    }

    private List<TaskTag> createDailyTags() {
        List<TaskTag> tags = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            TaskTag tag = PepperFactory.eINSTANCE.createTaskTag();
            tag.setPrefix("daily");
            tag.setSuffix(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            tags.add(tag);
        }
        return tags;
    }

    private List<TaskTag> createKanbanTags() {
        List<TaskTag> tags = new ArrayList<>();
        TaskTag tag = PepperFactory.eINSTANCE.createTaskTag();
        tag.setPrefix(KANBAN);
        tag.setSuffix("To Do");
        tags.add(tag);

        tag = PepperFactory.eINSTANCE.createTaskTag();
        tag.setPrefix(KANBAN);
        tag.setSuffix("Doing");
        tags.add(tag);

        tag = PepperFactory.eINSTANCE.createTaskTag();
        tag.setPrefix(KANBAN);
        tag.setSuffix("Done");
        tags.add(tag);

        return tags;
    }

    public Organization getEmptySampleContent() {
        Organization organization = PepperFactory.eINSTANCE.createOrganization();
        ResourceFolder resourceFolder = PepperFactory.eINSTANCE.createResourceFolder();
        resourceFolder.setName("Resources");
        organization.getOwnedResourceFolders().add(resourceFolder);

        Person person = PepperFactory.eINSTANCE.createPerson();
        person.setName("Peter");
        InternalStakeholder internalStakeholder = PepperFactory.eINSTANCE.createInternalStakeholder();
        internalStakeholder.setName("Department 1");
        ExternalStakeholder externalStakeholder = PepperFactory.eINSTANCE.createExternalStakeholder();
        externalStakeholder.setName("Client 1");
        resourceFolder.getOwnedResources().addAll(List.of(person, internalStakeholder, externalStakeholder));

        Project project = PepperFactory.eINSTANCE.createProject();
        project.setName("My project");
        Workpackage workpackage = PepperFactory.eINSTANCE.createWorkpackage();
        workpackage.setName(MAIN_WORKPACKAGE);
        WorkpackageArtefact workpackageArtefact = PepperFactory.eINSTANCE.createWorkpackageArtefact();
        workpackageArtefact.setName("New Workpackage Artefact");
        workpackage.getOutputs().add(workpackageArtefact);
        project.getOwnedWorkpackages().add(workpackage);


        organization.getOwnedProjects().add(project);

        return organization;
    }
}
