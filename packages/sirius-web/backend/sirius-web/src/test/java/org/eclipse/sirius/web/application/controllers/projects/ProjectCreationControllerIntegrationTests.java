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
package org.eclipse.sirius.web.application.controllers.projects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.services.BlankProjectTemplateProvider;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateProjectExecutor;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the project controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectCreationControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private CreateProjectExecutor createProjectExecutor;

    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private IProjectSemanticDataSearchService projectSemanticDataSearchService;

    @Autowired
    private ISemanticDataSearchService semanticDataSearchService;

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
    @DisplayName("Given a valid project to create, when the mutation is performed, then the project is created")
    public void givenValidProjectToCreateWhenMutationIsPerformedThenProjectIsCreated(CapturedOutput capturedOutput) {
        var input = new CreateProjectInput(UUID.randomUUID(), "New Project", BlankProjectTemplateProvider.BLANK_PROJECT_TEMPLATE_ID, List.of());
        var projectId = this.createProjectExecutor.execute(input, capturedOutput)
                .isSuccess()
                .getProjectId();

        var optionalProject = this.projectSearchService.findById(projectId);
        assertThat(optionalProject).isPresent();
        optionalProject.ifPresent(project -> assertThat(project.getName()).isEqualTo(input.name()));

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(3);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event).isInstanceOf(ProjectCreatedEvent.class);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a valid project to create, when the mutation is performed, then the semantic data are created")
    public void givenValidProjectToCreateWhenMutationIsPerformedThenTheSemanticDataAreCreated(CapturedOutput capturedOutput) {
        var input = new CreateProjectInput(UUID.randomUUID(), "New Project", BlankProjectTemplateProvider.BLANK_PROJECT_TEMPLATE_ID, List.of());
        var projectId = this.createProjectExecutor.execute(input, capturedOutput)
                .isSuccess()
                .getProjectId();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(3);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event).isInstanceOf(ProjectCreatedEvent.class);

        var exists = this.projectSearchService.existsById(projectId);
        assertThat(exists).isTrue();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project to create with libraries, when mutation is performed, then the libraries are present")
    public void givenProjectToCreateWithLibrariesWhenMutationIsPerformedThenTheLibrariesArePresent(CapturedOutput capturedOutput) {
        var input = new CreateProjectInput(UUID.randomUUID(), "New Project", BlankProjectTemplateProvider.BLANK_PROJECT_TEMPLATE_ID, List.of(PapayaIdentifiers.PAPAYA_JAVA_LIBRARY_ID.toString()));
        var projectId = this.createProjectExecutor.execute(input, capturedOutput)
                .isSuccess()
                .getProjectId();

        List<Class<?>> expectedEvent = List.of(CreateProjectInput.class, SemanticDataCreatedEvent.class, SemanticDataUpdatedEvent.class);
        assertThat(this.domainEventCollector.getDomainEvents()).hasSizeGreaterThan(0);
        assertThat(this.domainEventCollector.getDomainEvents()).anyMatch(event -> expectedEvent.contains(event.getClass()));

        var projectDependencies = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(projectId))
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .flatMap(this.semanticDataSearchService::findById)
                .stream()
                .flatMap(semanticData -> semanticData.getDependencies().stream())
                .map(SemanticDataDependency::dependencySemanticDataId)
                .map(AggregateReference::getId)
                .toList();
        assertThat(projectDependencies).anyMatch(PapayaIdentifiers.PAPAYA_LIBRARY_EDITING_CONTEXT_ID::equals);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project to create, when an invalid template id is provided, then an error is returned")
    public void givenProjectToCreateWhenAnInvalidTemplateIdIsProvidedThenAnErrorIsReturned(CapturedOutput capturedOutput) {
        var input = new CreateProjectInput(UUID.randomUUID(), "New Project", "INVALID", List.of());
        this.createProjectExecutor.execute(input, capturedOutput).isProjectTemplateMissingError();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project to create, when an invalid name is provided, then an error is returned")
    public void givenProjectToCreateWhenAnInvalidNameIsProvidedThenAnErrorIsReturned(CapturedOutput capturedOutput) {
        var input = new CreateProjectInput(UUID.randomUUID(), "", BlankProjectTemplateProvider.BLANK_PROJECT_TEMPLATE_ID, List.of());
        this.createProjectExecutor.execute(input, capturedOutput).isProjectCreationFailedError();
    }
}
