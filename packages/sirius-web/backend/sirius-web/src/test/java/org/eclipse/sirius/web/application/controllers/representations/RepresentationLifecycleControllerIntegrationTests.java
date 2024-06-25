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
package org.eclipse.sirius.web.application.controllers.representations;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalEventInput;
import org.eclipse.sirius.components.graphql.tests.CreateRepresentationMutationRunner;
import org.eclipse.sirius.components.graphql.tests.DeleteRepresentationMutationRunner;
import org.eclipse.sirius.components.graphql.tests.RenameRepresentationMutationRunner;
import org.eclipse.sirius.components.portals.tests.graphql.PortalEventSubscriptionRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationDataContentUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationDataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.eclipse.sirius.web.services.TestRepresentationDescription;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the lifeycle of representations.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepresentationLifecycleControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private CreateRepresentationMutationRunner createRepresentationMutationRunner;

    @Autowired
    private RenameRepresentationMutationRunner renameRepresentationMutationRunner;

    @Autowired
    private DeleteRepresentationMutationRunner deleteRepresentationMutationRunner;

    @Autowired
    private IRepresentationDataSearchService representationDataSearchService;

    @Autowired
    private PortalEventSubscriptionRunner portalEventSubscriptionRunner;

    @Autowired
    private IDomainEventCollector domainEventCollector;

    @BeforeEach
    public void beforeEach() {
        this.domainEventCollector.clear();
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a representation to create, when the mutation is performed, then the representation has been created")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRepresentationToCreateWhenMutationIsPerformedThenTheRepresentationHasBeenCreated() {
        this.givenCommittedTransaction.commit();

        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                new TestRepresentationDescription().getId(),
                TestIdentifiers.EPACKAGE_OBJECT.toString(),
                "Test representation"
        );
        var result = this.createRepresentationMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result, "$.data.createRepresentation.__typename");
        assertThat(typename).isEqualTo(CreateRepresentationSuccessPayload.class.getSimpleName());

        String representationId = JsonPath.read(result, "$.data.createRepresentation.representation.id");
        assertThat(representationId).isNotNull();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event).isInstanceOf(RepresentationDataCreatedEvent.class);

        assertThat(this.representationDataSearchService.existsById(UUID.fromString(representationId))).isTrue();
    }

    @Test
    @DisplayName("Given a representation to rename, when the mutation is performed, then the representation has been rename")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRepresentationToRenameWhenMutationIsPerformedThenTheRepresentationHasBeenRenamed() {
        this.givenCommittedTransaction.commit();

        var flux = this.portalEventSubscriptionRunner.run(new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()));

        Runnable renameRepresentation = () -> {
            var input = new RenameRepresentationInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString(), "new name");
            var result = this.renameRepresentationMutationRunner.run(input);

            TestTransaction.flagForCommit();
            TestTransaction.end();

            String typename = JsonPath.read(result, "$.data.renameRepresentation.__typename");
            assertThat(typename).isEqualTo(RenameRepresentationSuccessPayload.class.getSimpleName());

            assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
            assertThat(this.domainEventCollector.getDomainEvents()).anyMatch(RepresentationDataContentUpdatedEvent.class::isInstance);
        };

        StepVerifier.create(flux)
                .then(renameRepresentation)
                .thenCancel()
                .verify();
    }

    @Test
    @DisplayName("Given a representation to delete, when the mutation is performed, then the representation has been deleted")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRepresentationToDeleteWhenMutationIsPerformedThenTheRepresentationHasBeenDeleted() {
        this.givenCommittedTransaction.commit();

        assertThat(this.representationDataSearchService.existsById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION)).isTrue();

        var input = new DeleteRepresentationInput(UUID.randomUUID(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var result = this.deleteRepresentationMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result, "$.data.deleteRepresentation.__typename");
        assertThat(typename).isEqualTo(DeleteRepresentationSuccessPayload.class.getSimpleName());

        assertThat(this.representationDataSearchService.existsById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION)).isFalse();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event).isInstanceOf(RepresentationDataDeletedEvent.class);
    }
}
