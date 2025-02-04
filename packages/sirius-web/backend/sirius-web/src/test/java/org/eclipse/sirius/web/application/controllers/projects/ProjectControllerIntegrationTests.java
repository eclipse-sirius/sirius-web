/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.application.project.dto.ProjectEventInput;
import org.eclipse.sirius.web.application.project.dto.ProjectRenamedEventPayload;
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.eclipse.sirius.web.application.project.dto.RenameProjectSuccessPayload;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateProjectMutationRunner;
import org.eclipse.sirius.web.tests.graphql.DeleteProjectMutationRunner;
import org.eclipse.sirius.web.tests.graphql.ProjectEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.graphql.ProjectQueryRunner;
import org.eclipse.sirius.web.tests.graphql.ProjectsQueryRunner;
import org.eclipse.sirius.web.tests.graphql.RenameProjectMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import graphql.relay.Relay;
import reactor.test.StepVerifier;

/**
 * Integration tests of the project controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private ProjectQueryRunner projectQueryRunner;

    @Autowired
    private ProjectsQueryRunner projectsQueryRunner;

    @Autowired
    private CreateProjectMutationRunner createProjectMutationRunner;

    @Autowired
    private RenameProjectMutationRunner renameProjectMutationRunner;

    @Autowired
    private DeleteProjectMutationRunner deleteProjectMutationRunner;

    @Autowired
    private ProjectEventSubscriptionRunner projectEventSubscriptionRunner;

    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private IDomainEventCollector domainEventCollector;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @BeforeEach
    public void beforeEach() {
        this.domainEventCollector.clear();
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when a query is performed, then the project is returned")
    public void givenProjectWhenQueryIsPerformedThenTheProjectIsReturned() {
        Map<String, Object> variables = Map.of("projectId", TestIdentifiers.SYSML_SAMPLE_PROJECT.toString());
        var result = this.projectQueryRunner.run(variables);

        String projectId = JsonPath.read(result, "$.data.viewer.project.id");
        assertThat(projectId).isEqualTo(TestIdentifiers.SYSML_SAMPLE_PROJECT.toString());

        String projectName = JsonPath.read(result, "$.data.viewer.project.name");
        assertThat(projectName).isEqualTo("SysML Sample");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an invalid project, when a query is performed, then null is returned")
    public void givenAnInvalidProjectWhenQueryIsPerformedThenNullIsReturned() {
        Map<String, Object> variables = Map.of("projectId", TestIdentifiers.INVALID_PROJECT.toString());
        var result = this.projectQueryRunner.run(variables);

        Object value = JsonPath.read(result, "$.data.viewer.project");
        assertThat(value).isEqualTo(null);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of projects, when a valid first query is performed, then the projects are returned")
    public void givenSetOfProjectsWhenValidFirstQueryIsPerformedThenTheProjectsAreReturned() {
        Map<String, Object> variables = Map.of("first", 2);
        var result = this.projectsQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasNextPage");
        assertThat(hasNextPage).isTrue();

        String startCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.projects.pageInfo.count");
        assertThat(count).isEqualTo(2);

        List<String> projectIds = JsonPath.read(result, "$.data.viewer.projects.edges[*].node.id");
        assertThat(projectIds).hasSize(2);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of projects, when a valid first query is performed, then the projects are returned")
    public void givenSetOfProjectsWhenValidLastQueryIsPerformedThenTheProjectsAreReturned() {
        Map<String, Object> variables = Map.of("last", 2);
        var result = this.projectsQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasNextPage");
        assertThat(hasNextPage).isTrue();

        String startCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.projects.pageInfo.count");
        assertThat(count).isEqualTo(2);

        List<String> projectIds = JsonPath.read(result, "$.data.viewer.projects.edges[*].node.id");
        assertThat(projectIds).hasSize(2);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of projects, when a 0 first query is performed, then the projects are returned")
    public void givenSetOfProjectsWhenA0FirstProjectsQueryIsPerformedThenTheProjectsAreReturned() {
        Map<String, Object> variables = Map.of("first", 0);
        var result = this.projectsQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.startCursor");
        assertThat(startCursor).isBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.endCursor");
        assertThat(endCursor).isBlank();

        int count = JsonPath.read(result, "$.data.viewer.projects.pageInfo.count");
        assertThat(count).isEqualTo(0);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of projects, when a 0 last query is performed, then the projects are returned")
    public void givenSetOfProjectsWhenA0LastProjectsQueryIsPerformedThenTheProjectsAreReturned() {
        Map<String, Object> variables = Map.of("last", 0);
        var result = this.projectsQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.startCursor");
        assertThat(startCursor).isBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.endCursor");
        assertThat(endCursor).isBlank();

        int count = JsonPath.read(result, "$.data.viewer.projects.pageInfo.count");
        assertThat(count).isEqualTo(0);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of projects, when a valid after query is performed, then the projects are returned")
    public void givenSetOfProjectsWhenValidAfterQueryIsPerformedThenTheProjectsAreReturned() {
        var cursorProjectId = new Relay().toGlobalId("Project", TestIdentifiers.UML_SAMPLE_PROJECT.toString());
        Map<String, Object> variables = Map.of("after", cursorProjectId);
        var result = this.projectsQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isTrue();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.projects.pageInfo.count");
        assertThat(count).isGreaterThan(0);

        List<String> projectIds = JsonPath.read(result, "$.data.viewer.projects.edges[*].node.id");
        assertThat(projectIds.size()).isGreaterThan(0);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of projects, when a valid before query is performed, then the projects are returned")
    public void givenSetOfProjectsWhenValidBeforeQueryIsPerformedThenTheProjectsAreReturned() {
        var cursorProjectId = new Relay().toGlobalId("Project", TestIdentifiers.UML_SAMPLE_PROJECT.toString());
        Map<String, Object> variables = Map.of("before", cursorProjectId);
        var result = this.projectsQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projects.pageInfo.hasNextPage");
        assertThat(hasNextPage).isTrue();

        String startCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projects.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.projects.pageInfo.count");
        assertThat(count).isGreaterThan(0);

        List<String> projectIds = JsonPath.read(result, "$.data.viewer.projects.edges[*].node.id");
        assertThat(projectIds.size()).isGreaterThan(0);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a valid project to create, when the mutation is performed, then the project is created")
    public void givenValidProjectToCreateWhenMutationIsPerformedThenProjectIsCreated() {
        var input = new CreateProjectInput(UUID.randomUUID(), "New Project", List.of());
        var result = this.createProjectMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result, "$.data.createProject.__typename");
        assertThat(typename).isEqualTo(CreateProjectSuccessPayload.class.getSimpleName());

        String projectId = JsonPath.read(result, "$.data.createProject.project.id");

        var optionalProject = this.projectSearchService.findById(projectId);
        assertThat(optionalProject).isPresent();
        optionalProject.ifPresent(project -> assertThat(project.getName()).isEqualTo(input.name()));

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(3);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event).isInstanceOf(ProjectCreatedEvent.class);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a valid input, when a forward findAll is performed, then the returned window contains the projects after the input project")
    public void givenAValidInputWhenAForwardFindallIsPerformedThenTheReturnedWindowContainsTheProjectsAfterTheInputProject() {
        var keyset = ScrollPosition.forward(Map.of("id", TestIdentifiers.UML_SAMPLE_PROJECT.toString()));
        var window = this.projectSearchService.findAll(keyset, 1);
        assertThat(window).isNotNull();
        assertThat(window.size()).isOne();
        assertThat(window.getContent().get(0).getId()).isEqualTo(TestIdentifiers.ECORE_SAMPLE_PROJECT);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a valid input, when a forward findAll is performed, then the returned window contains the projects after the input project")
    public void givenAValidInputWhenABackwardFindallIsPerformedThenTheReturnedWindowContainsTheProjectsAfterTheInputProject() {
        var keyset = ScrollPosition.backward(Map.of("id", TestIdentifiers.UML_SAMPLE_PROJECT.toString()));
        var window = this.projectSearchService.findAll(keyset, 1);
        assertThat(window).isNotNull();
        assertThat(window.size()).isOne();
        assertThat(window.getContent().get(0).getId()).isEqualTo(TestIdentifiers.ECORE_SAMPLE_PROJECT);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an invalid project id, when findAll is performed, then the returned window is empty")
    public void givenAnInvalidProjectIdWhenFindallIsPerformedThenTheReturnedWindowIsNull() {
        var keyset = ScrollPosition.forward(Map.of("id", "invalid-id"));
        var window = this.projectSearchService.findAll(keyset, 1);
        assertThat(window).isNotNull();
        assertThat(window.size()).isZero();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an invalid limit, when findAll is performed, then the returned window is empty")
    public void givenAnInvalidLimitWhenFindallIsPerformedThenTheReturnedWindowIsNull() {
        var window = this.projectSearchService.findAll(ScrollPosition.keyset(), 0);
        assertThat(window).isNotNull();
        assertThat(window.size()).isZero();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a valid project to create, when the mutation is performed, then the semantic data are created")
    public void givenValidProjectToCreateWhenMutationIsPerformedThenTheSemanticDataAreCreated() {
        var input = new CreateProjectInput(UUID.randomUUID(), "New Project", List.of());
        var result = this.createProjectMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(3);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event).isInstanceOf(ProjectCreatedEvent.class);

        String typename = JsonPath.read(result, "$.data.createProject.__typename");
        assertThat(typename).isEqualTo(CreateProjectSuccessPayload.class.getSimpleName());

        String projectId = JsonPath.read(result, "$.data.createProject.project.id");

        var exists = this.projectSearchService.existsById(projectId);
        assertThat(exists).isTrue();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an existing project to delete, when the mutation is performed, then the project is deleted")
    public void givenExistingProjectToDeleteWhenMutationIsPerformedThenProjectIsDeleted() {
        assertThat(this.projectSearchService.existsById(TestIdentifiers.UML_SAMPLE_PROJECT)).isTrue();

        var input = new DeleteProjectInput(UUID.randomUUID(), TestIdentifiers.UML_SAMPLE_PROJECT);
        var result = this.deleteProjectMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result, "$.data.deleteProject.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

        assertThat(this.projectSearchService.existsById(TestIdentifiers.UML_SAMPLE_PROJECT)).isFalse();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event).isInstanceOf(ProjectDeletedEvent.class);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an existing project to rename, when the mutation is performed, then the project is renamed")
    public void givenExistingProjectToRenameWhenMutationIsPerformedThenProjectIsRenamed() {
        assertThat(this.projectSearchService.existsById(TestIdentifiers.UML_SAMPLE_PROJECT)).isTrue();

        var input = new RenameProjectInput(UUID.randomUUID(), TestIdentifiers.UML_SAMPLE_PROJECT, "New Name");
        var result = this.renameProjectMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result, "$.data.renameProject.__typename");
        assertThat(typename).isEqualTo(RenameProjectSuccessPayload.class.getSimpleName());

        var optionalProject = this.projectSearchService.findById(TestIdentifiers.UML_SAMPLE_PROJECT);
        assertThat(optionalProject).isPresent();

        var project = optionalProject.get();
        assertThat(project.getName()).isEqualTo(input.newName());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an existing project to rename, when the mutation is performed with an invalid name, then an error is returned")
    public void givenExistingProjectToRenameWhenMutationIsPerformedWithInvalidNameThenAnErrorIsReturned() {
        assertThat(this.projectSearchService.existsById(TestIdentifiers.UML_SAMPLE_PROJECT)).isTrue();

        var input = new RenameProjectInput(UUID.randomUUID(), TestIdentifiers.UML_SAMPLE_PROJECT, "");
        var result = this.renameProjectMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result, "$.data.renameProject.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an invalid project to rename, when the mutation is performed, then an error is returned")
    public void givenInvalidProjectToRenameWhenMutationIsPerformedThenAnErrorIsReturned() {
        assertThat(this.projectSearchService.existsById(TestIdentifiers.INVALID_PROJECT)).isFalse();

        var input = new RenameProjectInput(UUID.randomUUID(), TestIdentifiers.INVALID_PROJECT, "New Name");
        var result = this.renameProjectMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result, "$.data.renameProject.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an invalid project to delete, when the mutation is performed, then an error is returned")
    public void givenAnInvalidProjectToDeleteWhenMutationIsPerformedThenErrorIsReturned() {
        var input = new DeleteProjectInput(UUID.randomUUID(), TestIdentifiers.INVALID_PROJECT);
        var result = this.deleteProjectMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result, "$.data.deleteProject.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());

        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when the project is renamed, then a project event is emitted")
    public void givenProjectWhenTheProjectIsRenamedThenProjectEventIsEmitted() {
        var projectEventInput = new ProjectEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT);
        var flux = this.projectEventSubscriptionRunner.run(projectEventInput);

        var input = new RenameProjectInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT, "New Name");
        Runnable renameProjectTask = () -> {
            var result = this.renameProjectMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.renameProject.__typename");
            assertThat(typename).isEqualTo(RenameProjectSuccessPayload.class.getSimpleName());

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        };
        StepVerifier.create(flux)
                .then(renameProjectTask)
                .expectNextMatches(payload -> {
                    boolean isValid = payload instanceof ProjectRenamedEventPayload;
                    isValid = isValid && ((ProjectRenamedEventPayload) payload).newName().equals(input.newName());
                    return isValid;
                })
                .thenCancel()
                .verify(Duration.ofSeconds(5));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when the project is deleted, then the project event is completed")
    public void givenProjectWhenTheProjectIsDeletedThenTheProjectEventIsCompleted() {
        var projectEventInput = new ProjectEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT);
        var flux = this.projectEventSubscriptionRunner.run(projectEventInput);

        var input = new DeleteProjectInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT);
        Runnable deleteProjectTask = () -> {
            var result = this.deleteProjectMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.deleteProject.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        };
        StepVerifier.create(flux)
                .then(deleteProjectTask)
                .expectComplete()
                .verify(Duration.ofSeconds(5));
    }
}
