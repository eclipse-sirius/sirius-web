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

import java.time.Duration;
import java.util.UUID;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.application.project.dto.ProjectEventInput;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DeleteProjectExecutor;
import org.eclipse.sirius.web.tests.graphql.ProjectEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the project controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectDeletionControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private DeleteProjectExecutor deleteProjectExecutor;

    @Autowired
    private ProjectEventSubscriptionRunner projectEventSubscriptionRunner;

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
    @DisplayName("Given an existing project to delete, when the mutation is performed, then the project is deleted")
    public void givenExistingProjectToDeleteWhenMutationIsPerformedThenProjectIsDeleted(CapturedOutput capturedOutput) {
        assertThat(this.projectSearchService.existsById(TestIdentifiers.UML_SAMPLE_PROJECT)).isTrue();

        var optionalSemanticData = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(TestIdentifiers.UML_SAMPLE_PROJECT))
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .flatMap(this.semanticDataSearchService::findById);

        assertThat(optionalSemanticData).isPresent();
        var semanticData = optionalSemanticData.get();

        var input = new DeleteProjectInput(UUID.randomUUID(), TestIdentifiers.UML_SAMPLE_PROJECT);
        this.deleteProjectExecutor.execute(input, capturedOutput).isSuccess();

        assertThat(this.projectSearchService.existsById(TestIdentifiers.UML_SAMPLE_PROJECT)).isFalse();
        assertThat(this.semanticDataSearchService.findById(semanticData.getId())).isEmpty();

        assertThat(this.domainEventCollector.getDomainEvents()).anyMatch(ProjectDeletedEvent.class::isInstance);
        assertThat(this.domainEventCollector.getDomainEvents()).anyMatch(ProjectSemanticDataDeletedEvent.class::isInstance);
        assertThat(this.domainEventCollector.getDomainEvents()).anyMatch(SemanticDataDeletedEvent.class::isInstance);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an invalid project to delete, when the mutation is performed, then an error is returned")
    public void givenAnInvalidProjectToDeleteWhenMutationIsPerformedThenErrorIsReturned(CapturedOutput capturedOutput) {
        var input = new DeleteProjectInput(UUID.randomUUID(), TestIdentifiers.INVALID_PROJECT);
        this.deleteProjectExecutor.execute(input, capturedOutput).isProjectDeletionFailedError();

        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when the project is deleted, then the project event is completed")
    public void givenProjectWhenTheProjectIsDeletedThenTheProjectEventIsCompleted(CapturedOutput capturedOutput) {
        var projectEventInput = new ProjectEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT);
        var flux = this.projectEventSubscriptionRunner.run(projectEventInput).flux();

        var input = new DeleteProjectInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT);
        Runnable deleteProjectTask = () -> this.deleteProjectExecutor.execute(input, capturedOutput).isSuccess();

        StepVerifier.create(flux)
                .then(deleteProjectTask)
                .expectComplete()
                .verify(Duration.ofSeconds(5));
    }
}
