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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.core.domain.results.Failure;
import org.eclipse.sirius.web.core.domain.results.Success;
import org.eclipse.sirius.web.data.MultiEditingContextFlowIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataNameUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataUpdateService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the project semantic data update service.
 *
 * @author frouene
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectSemanticDataUpdateServiceIntegrationTests extends AbstractIntegrationTests {

    private static final String MAIN_NAME = "main";

    private static final String SECONDARY_NAME = "main2";

    private static final String RENAMED_NAME = "renamed";

    @Autowired
    private IProjectSemanticDataUpdateService projectSemanticDataUpdateService;

    @Autowired
    private IProjectSemanticDataSearchService projectSemanticDataSearchService;

    @Autowired
    private IDomainEventCollector domainEventCollector;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
        this.domainEventCollector.clear();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given project semantic data, when it is renamed, then its sanitized name is persisted and a domain event is published")
    public void givenProjectSemanticDataWhenItIsRenamedThenItsSanitizedNameIsPersistedAndDomainEventIsPublished() {
        AggregateReference<Project, String> project = AggregateReference.to(MultiEditingContextFlowIdentifiers.PROJECT_ID);
        var projectSemanticData = this.projectSemanticDataSearchService.findByProjectIdAndName(project, MAIN_NAME).orElseThrow();
        var cause = new ICause.NoOp();

        var result = this.projectSemanticDataUpdateService.renameProjectSemanticData(
                cause,
                MultiEditingContextFlowIdentifiers.PROJECT_ID,
                MAIN_NAME,
                " renamed "
        );

        assertThat(result).isInstanceOf(Success.class);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        assertThat(this.projectSemanticDataSearchService.findByProjectIdAndName(project, MAIN_NAME)).isEmpty();
        assertThat(this.projectSemanticDataSearchService.findByProjectIdAndName(project, SECONDARY_NAME)).isPresent();
        assertThat(this.projectSemanticDataSearchService.findByProjectIdAndName(project, RENAMED_NAME))
                .isPresent()
                .get()
                .satisfies(renamedProjectSemanticData -> {
                    assertThat(renamedProjectSemanticData.getId()).isEqualTo(projectSemanticData.getId());
                    assertThat(renamedProjectSemanticData.getSemanticData()).isEqualTo(projectSemanticData.getSemanticData());
                });

        assertThat(this.domainEventCollector.getDomainEvents().stream()
                .filter(ProjectSemanticDataNameUpdatedEvent.class::isInstance)
                .map(ProjectSemanticDataNameUpdatedEvent.class::cast)
                .toList())
                .singleElement()
                .satisfies(event -> {
                    assertThat(event.causedBy()).isSameAs(cause);
                    assertThat(event.createdOn()).isEqualTo(event.projectSemanticData().getLastModifiedOn());
                    assertThat(event.projectSemanticData().getName()).isEqualTo(RENAMED_NAME);
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given project semantic data, when it is renamed with the same name, then the operation succeeds without publishing a domain event")
    public void givenProjectSemanticDataWhenItIsRenamedWithSameNameThenOperationSucceedsWithoutPublishingDomainEvent() {
        AggregateReference<Project, String> project = AggregateReference.to(MultiEditingContextFlowIdentifiers.PROJECT_ID);
        var lastModifiedOn = this.projectSemanticDataSearchService.findByProjectIdAndName(project, MAIN_NAME)
                .orElseThrow()
                .getLastModifiedOn();

        var result = this.projectSemanticDataUpdateService.renameProjectSemanticData(
                new ICause.NoOp(),
                MultiEditingContextFlowIdentifiers.PROJECT_ID,
                MAIN_NAME,
                " main "
        );

        assertThat(result).isInstanceOf(Success.class);
        
        assertThat(this.projectSemanticDataSearchService.findByProjectIdAndName(project, MAIN_NAME))
                .isPresent()
                .get()
                .extracting(ProjectSemanticData::getLastModifiedOn)
                .isEqualTo(lastModifiedOn);
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given project semantic data, when it is renamed with a blank name, then an invalid name failure is returned")
    public void givenProjectSemanticDataWhenItIsRenamedWithBlankNameThenInvalidNameFailureIsReturned() {
        var result = this.projectSemanticDataUpdateService.renameProjectSemanticData(
                new ICause.NoOp(),
                MultiEditingContextFlowIdentifiers.PROJECT_ID,
                MAIN_NAME,
                "  "
        );

        assertThat(result).isEqualTo(new Failure<>(this.messageService.invalidName()));
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given project semantic data with the requested name, when another project semantic data is renamed, then a rename failure is returned")
    public void givenProjectSemanticDataWithRequestedNameWhenAnotherProjectSemanticDataIsRenamedThenRenameFailureIsReturned() {
        AggregateReference<Project, String> project = AggregateReference.to(MultiEditingContextFlowIdentifiers.PROJECT_ID);

        var result = this.projectSemanticDataUpdateService.renameProjectSemanticData(
                new ICause.NoOp(),
                MultiEditingContextFlowIdentifiers.PROJECT_ID,
                MAIN_NAME,
                SECONDARY_NAME
        );

        assertThat(result).isEqualTo(new Failure<>(this.messageService.failedToRename()));
        assertThat(this.projectSemanticDataSearchService.findByProjectIdAndName(project, MAIN_NAME)).isPresent();
        assertThat(this.projectSemanticDataSearchService.findByProjectIdAndName(project, SECONDARY_NAME)).isPresent();
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given no matching project semantic data, when it is renamed, then a not found failure is returned")
    public void givenNoMatchingProjectSemanticDataWhenItIsRenamedThenNotFoundFailureIsReturned() {
        var result = this.projectSemanticDataUpdateService.renameProjectSemanticData(
                new ICause.NoOp(),
                MultiEditingContextFlowIdentifiers.PROJECT_ID,
                "unknown",
                RENAMED_NAME
        );

        assertThat(result).isEqualTo(new Failure<>(this.messageService.notFound()));
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }
}
