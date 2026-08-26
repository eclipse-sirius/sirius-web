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
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataDeletionService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
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
 * Integration tests of the project semantic data deletion service.
 *
 * @author frouene
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectSemanticDataDeletionServiceIntegrationTests extends AbstractIntegrationTests {

    private static final String MAIN_NAME = "main";

    private static final String SECONDARY_NAME = "main2";

    @Autowired
    private IProjectSemanticDataDeletionService projectSemanticDataDeletionService;

    @Autowired
    private IProjectSemanticDataSearchService projectSemanticDataSearchService;

    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private ISemanticDataSearchService semanticDataSearchService;

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
    @DisplayName("Given a project with two semantic data, when one project semantic data is deleted by name, then only the matching project semantic data is deleted")
    public void givenProjectWithTwoSemanticDataWhenOneProjectSemanticDataIsDeletedByNameThenOnlyMatchingProjectSemanticDataIsDeleted() {
        AggregateReference<Project, String> project = AggregateReference.to(MultiEditingContextFlowIdentifiers.PROJECT_ID);
        var mainSemanticDataId = this.projectSemanticDataSearchService.findByProjectIdAndName(project, MAIN_NAME)
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .orElseThrow();
        var secondarySemanticDataId = this.projectSemanticDataSearchService.findByProjectIdAndName(project, SECONDARY_NAME)
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .orElseThrow();

        var cause = new ICause.NoOp();
        var result = this.projectSemanticDataDeletionService.deleteProjectSemanticDataByProjectIdAndName(cause, MultiEditingContextFlowIdentifiers.PROJECT_ID, SECONDARY_NAME);

        assertThat(result).isInstanceOf(Success.class);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        assertThat(this.projectSearchService.existsById(MultiEditingContextFlowIdentifiers.PROJECT_ID)).isTrue();
        assertThat(this.projectSemanticDataSearchService.findByProjectIdAndName(project, MAIN_NAME)).isPresent();
        assertThat(this.projectSemanticDataSearchService.findByProjectIdAndName(project, SECONDARY_NAME)).isEmpty();
        assertThat(this.semanticDataSearchService.findById(mainSemanticDataId)).isPresent();
        assertThat(this.semanticDataSearchService.findById(secondarySemanticDataId)).isEmpty();

        var projectSemanticDataDeletedEvents = this.domainEventCollector.getDomainEvents().stream()
                .filter(ProjectSemanticDataDeletedEvent.class::isInstance)
                .map(ProjectSemanticDataDeletedEvent.class::cast)
                .toList();
        assertThat(projectSemanticDataDeletedEvents)
                .singleElement()
                .satisfies(event -> {
                    assertThat(event.causedBy()).isEqualTo(cause);
                    assertThat(event.projectSemanticData().getProject().getId()).isEqualTo(MultiEditingContextFlowIdentifiers.PROJECT_ID);
                    assertThat(event.projectSemanticData().getName()).isEqualTo(SECONDARY_NAME);
                });

        var semanticDataDeletedEvents = this.domainEventCollector.getDomainEvents().stream()
                .filter(SemanticDataDeletedEvent.class::isInstance)
                .map(SemanticDataDeletedEvent.class::cast)
                .toList();
        assertThat(semanticDataDeletedEvents)
                .singleElement()
                .satisfies(event -> {
                    assertThat(event.causedBy()).isEqualTo(projectSemanticDataDeletedEvents.getFirst());
                    assertThat(event.semanticData().getId()).isEqualTo(secondarySemanticDataId);
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project without matching semantic data, when project semantic data is deleted by name, then a not found failure is returned")
    public void givenProjectWithoutMatchingSemanticDataWhenProjectSemanticDataIsDeletedByNameThenNotFoundFailureIsReturned() {
        var result = this.projectSemanticDataDeletionService.deleteProjectSemanticDataByProjectIdAndName(
                new ICause.NoOp(),
                MultiEditingContextFlowIdentifiers.PROJECT_ID,
                "unknown"
        );

        assertThat(result).isEqualTo(new Failure<>(this.messageService.notFound()));
    }
}
