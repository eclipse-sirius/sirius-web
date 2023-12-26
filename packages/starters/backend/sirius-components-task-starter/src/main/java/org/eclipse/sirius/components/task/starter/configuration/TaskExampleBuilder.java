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

import java.time.Instant;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.task.Company;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskFactory;
import org.eclipse.sirius.components.task.Team;

/**
 * Builder for an example of task model.
 *
 * @author lfasani
 */
public class TaskExampleBuilder {

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

        Project devProject = TaskFactory.eINSTANCE.createProject();
        devProject.setName("Project Dev");
        company.getOwnedProjects().add(devProject);

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
        development.setStartTime(Instant.parse("2023-12-13T08:30:00Z"));
        development.setEndTime(Instant.parse("2023-12-16T17:30:00Z"));
        development.getDependencies().add(spec);

        Task codeDev = TaskFactory.eINSTANCE.createTask();
        codeDev.setName("Code Development");
        codeDev.setStartTime(Instant.parse("2023-12-13T08:30:00Z"));
        codeDev.setEndTime(Instant.parse("2023-12-15T17:30:00Z"));
        codeDev.getAssignedPersons().add(peter);
        Task review = TaskFactory.eINSTANCE.createTask();
        review.setName("Review");
        review.setStartTime(Instant.parse("2023-12-16T08:30:00Z"));
        review.setEndTime(Instant.parse("2023-12-16T17:30:00Z"));
        development.getSubTasks().addAll(List.of(codeDev, review));
        codeDev.getAssignedPersons().add(paul);

        Task release = TaskFactory.eINSTANCE.createTask();
        release.setName("Release");
        release.setStartTime(Instant.parse("2023-12-18T08:30:00Z"));
        release.setEndTime(Instant.parse("2023-12-18T08:30:00Z"));

        devProject.getOwnedTasks().addAll(List.of(idea, spec, development, release));

        return company;
    }
}
