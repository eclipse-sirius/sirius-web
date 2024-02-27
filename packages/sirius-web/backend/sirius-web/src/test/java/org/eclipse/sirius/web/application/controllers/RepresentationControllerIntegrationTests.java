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
package org.eclipse.sirius.web.application.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationSuccessPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationDataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.eclipse.sirius.web.services.TestRepresentation;
import org.eclipse.sirius.web.services.TestRepresentationDescription;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.services.api.IGraphQLRequestor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the representation controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepresentationControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String GET_REPRESENTATION_METADATA_QUERY = """
            query getRepresentationMetadata($editingContextId: ID!, $representationId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    id
                    kind
                    label
                  }
                }
              }
            }
            """;

    private static final String GET_ALL_REPRESENTATION_METADATA_QUERY = """
            query getAllRepresentationMetadata($editingContextId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representations {
                    edges {
                      node {
                        id
                        label
                        kind
                      }
                    }
                    pageInfo {
                      hasPreviousPage
                      hasNextPage
                      startCursor
                      endCursor
                      count
                    }
                  }
                }
              }
            }
            """;

    private static final String GET_ALL_REPRESENTATION_DESCRIPTIONS_QUERY = """
            query getAllRepresentationMetadata($editingContextId: ID!, $objectId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representationDescriptions(objectId: $objectId) {
                    edges {
                      node {
                        id
                        label
                      }
                    }
                    pageInfo {
                      hasPreviousPage
                      hasNextPage
                      startCursor
                      endCursor
                      count
                    }
                  }
                }
              }
            }
            """;

    private static final String CREATE_REPRESENTATION_MUTATION = """
            mutation createRepresentation($input: CreateRepresentationInput!) {
              createRepresentation(input: $input) {
                __typename
                ... on CreateRepresentationSuccessPayload {
                  representation {
                    id
                  }
                }
              }
            }
            """;

    private static final String DELETE_REPRESENTATION_MUTATION = """
            mutation deleteRepresentation($input: DeleteRepresentationInput!) {
              deleteRepresentation(input: $input) {
                __typename
              }
            }
            """;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @Autowired
    private IRepresentationDataSearchService representationDataSearchService;

    @Autowired
    private IDomainEventCollector domainEventCollector;

    @BeforeEach
    public void beforeEach() {
        this.domainEventCollector.clear();
    }

    @Test
    @DisplayName("Given a representation id, when a query is performed, then the representation metadata are returned")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRepresentationIdWhenQueryIsPerformedThenTheRepresentationMetadataAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                "representationId", TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()
        );
        var result = this.graphQLRequestor.execute(GET_REPRESENTATION_METADATA_QUERY, variables);

        String representationId = JsonPath.read(result, "$.data.viewer.editingContext.representation.id");
        assertThat(representationId).isEqualTo(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());

        String kind = JsonPath.read(result, "$.data.viewer.editingContext.representation.kind");
        assertThat(kind).isEqualTo("siriusComponents://representation?type=Portal");

        String label = JsonPath.read(result, "$.data.viewer.editingContext.representation.label");
        assertThat(label).isEqualTo("Portal");
    }

    @Test
    @DisplayName("Given an editing context id, when a query is performed, then all the representation metadata are returned")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenEditingContextIdWhenQueryIsPerformedThenAllTheRepresentationMetadataAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString()
        );
        var result = this.graphQLRequestor.execute(GET_ALL_REPRESENTATION_METADATA_QUERY, variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.count");
        assertThat(count).isEqualTo(1);

        List<String> representationIds = JsonPath.read(result, "$.data.viewer.editingContext.representations.edges[*].node.id");
        assertThat(representationIds).hasSize(1);

        var firstRepresentationId = representationIds.get(0);
        assertThat(firstRepresentationId).isEqualTo(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
    }

    @Test
    @DisplayName("Given an object id, when a query is performed, then all the representation descriptions are returned")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenObjectIdWhenQueryIsPerformedThenAllTheRepresentationDescriptionsAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                "objectId", TestIdentifiers.EPACKAGE_OBJECT.toString()
        );
        var result = this.graphQLRequestor.execute(GET_ALL_REPRESENTATION_DESCRIPTIONS_QUERY, variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.count");
        assertThat(count).isEqualTo(2);

        List<String> representationIds = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.edges[*].node.id");
        assertThat(representationIds).hasSize(2);
    }

    @Test
    @DisplayName("Given a representation to create, when the mutation is performed, then the representation has been created")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRepresentationToCreateWhenMutationIsPerformedThenTheRepresentationHasBeenCreated() {
        this.commitInitializeStateBeforeThreadSwitching();

        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                new TestRepresentationDescription().getId(),
                TestIdentifiers.EPACKAGE_OBJECT.toString(),
                "Test representation"
        );
        var result = this.graphQLRequestor.execute(CREATE_REPRESENTATION_MUTATION, input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result, "$.data.createRepresentation.__typename");
        assertThat(typename).isEqualTo(CreateRepresentationSuccessPayload.class.getSimpleName());

        String representationId = JsonPath.read(result, "$.data.createRepresentation.representation.id");
        assertThat(representationId).isNotNull();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event).isInstanceOf(RepresentationDataCreatedEvent.class);

        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                "representationId", representationId
        );
        var getRepresentationMetadataResult = this.graphQLRequestor.execute(GET_REPRESENTATION_METADATA_QUERY, variables);
        String kind = JsonPath.read(getRepresentationMetadataResult, "$.data.viewer.editingContext.representation.kind");
        assertThat(kind).isEqualTo(new TestRepresentation().getKind());
    }

    @Test
    @DisplayName("Given a representation to delete, when the mutation is performed, then the representation has been deleted")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRepresentationToDeleteWhenMutationIsPerformedThenTheRepresentationHasBeenDeleted() {
        this.commitInitializeStateBeforeThreadSwitching();

        assertThat(this.representationDataSearchService.findById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION)).isPresent();

        var input = new DeleteRepresentationInput(UUID.randomUUID(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var result = this.graphQLRequestor.execute(DELETE_REPRESENTATION_MUTATION, input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result, "$.data.deleteRepresentation.__typename");
        assertThat(typename).isEqualTo(DeleteRepresentationSuccessPayload.class.getSimpleName());

        assertThat(this.representationDataSearchService.findById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION)).isEmpty();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event).isInstanceOf(RepresentationDataDeletedEvent.class);
    }

    /**
     * Used to commit the state of the transaction after its initialization by the @Sql annotation
     * in order to make the state persisted in the database. Without this, the initialized state
     * will not be visible by the various repositories when the test will switch threads to use
     * the thread of the editing context.
     *
     * This should not be used every single time but only in the couple integrations tests that are
     * required to interact with repositories while inside an editing context event handler or a
     * representation event handler for example.
     */
    private void commitInitializeStateBeforeThreadSwitching() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }
}
