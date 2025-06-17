/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

package org.eclipse.sirius.web.papaya.projecttemplates;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.papaya.Contribution;
import org.eclipse.sirius.components.papaya.Folder;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.Task;
import org.eclipse.sirius.web.papaya.factories.services.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.services.api.IObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Used to create the objects of the planning.
 *
 * @author sbegaudeau
 */
public class PlanningObjectFactory implements IObjectFactory {

    private final ObjectMapper objectMapper;

    private final Project project;

    private final Logger logger = LoggerFactory.getLogger(PlanningObjectFactory.class);

    public PlanningObjectFactory(ObjectMapper objectMapper, Project project) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.project = Objects.requireNonNull(project);
    }

    @Override
    public void create(IEMFEditingContext editingContext) {
        var iterationsFolder = this.createIterationsFolder();
        var tasksFolder = this.createTasksFolder();
        var contributionsFolder = this.createContributionsFolder();

        project.getFolders().addAll(List.of(
                iterationsFolder,
                tasksFolder,
                contributionsFolder
        ));
    }

    private Folder createIterationsFolder() {
        Folder iterationsFolder = PapayaFactory.eINSTANCE.createFolder();
        iterationsFolder.setName("Iterations");

        var milestonesResource = new ClassPathResource("data/milestones.json");
        try (var inputStream = milestonesResource.getInputStream()) {
            JsonNode jsonNode = this.objectMapper.readTree(inputStream);
            JsonNode milestonesJsonNode = jsonNode.at("/data/repository/milestones/nodes");
            if (milestonesJsonNode.isArray()) {
                var milestones = this.objectMapper.treeToValue(milestonesJsonNode, new TypeReference<List<MilestoneDTO>>() { });
                milestones.forEach(milestone -> this.createMilestone(iterationsFolder, milestone));
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return iterationsFolder;
    }

    private void createMilestone(Folder iterationsFolder, MilestoneDTO milestone) {
        Iteration iteration = PapayaFactory.eINSTANCE.createIteration();
        iteration.setName(milestone.title());
        iteration.setDescription(milestone.description());

        Folder iterationFolder = PapayaFactory.eINSTANCE.createFolder();
        iterationFolder.setName(milestone.title());

        iterationsFolder.getFolders().add(iterationFolder);
        iterationsFolder.getElements().add(iteration);
    }

    private Folder createTasksFolder() {
        Folder tasksFolder = PapayaFactory.eINSTANCE.createFolder();
        tasksFolder.setName("Tasks");

        var issuesResource = new ClassPathResource("data/issues.json");
        try (var inputStream = issuesResource.getInputStream()) {
            JsonNode jsonNode = this.objectMapper.readTree(inputStream);
            JsonNode issuesJsonNode = jsonNode.at("/data/repository/issues/nodes");
            if (issuesJsonNode.isArray()) {
                var issues = this.objectMapper.treeToValue(issuesJsonNode, new TypeReference<List<IssueDTO>>() { });
                issues.forEach(issue -> this.createTask(tasksFolder, issue));
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return tasksFolder;
    }

    private void createTask(Folder tasksFolder, IssueDTO issue) {
        Task task = PapayaFactory.eINSTANCE.createTask();
        task.setName(issue.title());

        tasksFolder.getElements().add(task);
    }

    private Folder createContributionsFolder() {
        Folder contributionsFolder = PapayaFactory.eINSTANCE.createFolder();
        contributionsFolder.setName("Contributions");

        var pullRequestsResource = new ClassPathResource("data/pull-requests.json");
        try (var inputStream = pullRequestsResource.getInputStream()) {
            JsonNode jsonNode = this.objectMapper.readTree(inputStream);
            JsonNode pullRequestsJsonNode = jsonNode.at("/data/repository/pullRequests/nodes");
            if (pullRequestsJsonNode.isArray()) {
                var pullRequests = this.objectMapper.treeToValue(pullRequestsJsonNode, new TypeReference<List<PullRequestDTO>>() { });
                pullRequests.forEach(pullRequest -> this.createContribution(contributionsFolder, pullRequest));
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return contributionsFolder;
    }

    private void createContribution(Folder contributionsFolder, PullRequestDTO pullRequest) {
        Contribution contribution = PapayaFactory.eINSTANCE.createContribution();
        contribution.setName(pullRequest.title());

        contributionsFolder.getElements().add(contribution);
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {

    }
}
