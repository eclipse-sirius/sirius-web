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

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.graphql.tests.AllRepresentationDescriptionsQueryRunner;
import org.eclipse.sirius.components.graphql.tests.AllRepresentationMetadataQueryRunner;
import org.eclipse.sirius.components.graphql.tests.RepresentationMetadataQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
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

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private RepresentationMetadataQueryRunner representationMetadataQueryRunner;

    @Autowired
    private AllRepresentationMetadataQueryRunner allRepresentationMetadataQueryRunner;

    @Autowired
    private AllRepresentationDescriptionsQueryRunner allRepresentationDescriptionsQueryRunner;

    @Autowired
    private IDomainEventCollector domainEventCollector;

    @BeforeEach
    public void beforeEach() {
        this.domainEventCollector.clear();
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a persistent representation id, when a query is performed, then the representation metadata are returned")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenPresistentRepresentationIdWhenQueryIsPerformedThenTheRepresentationMetadataAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                "representationId", TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()
        );
        var result = this.representationMetadataQueryRunner.run(variables);

        String representationId = JsonPath.read(result, "$.data.viewer.editingContext.representation.id");
        assertThat(representationId).isEqualTo(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());

        String kind = JsonPath.read(result, "$.data.viewer.editingContext.representation.kind");
        assertThat(kind).isEqualTo("siriusComponents://representation?type=Portal");

        String label = JsonPath.read(result, "$.data.viewer.editingContext.representation.label");
        assertThat(label).isEqualTo("Portal");
    }

    @Test
    @DisplayName("Given a transient representation id, when a query is performed, then the representation metadata are returned")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenTransientRepresentationIdWhenQueryIsPerformedThenTheRepresentationMetadataAreReturned() {
        String initialExplorerId = "explorer://?expandedIds=[]&activeFilterIds=[]";
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                "representationId", initialExplorerId
        );
        var result = this.representationMetadataQueryRunner.run(variables);

        String representationId = JsonPath.read(result, "$.data.viewer.editingContext.representation.id");
        assertThat(representationId).isEqualTo(initialExplorerId);

        String kind = JsonPath.read(result, "$.data.viewer.editingContext.representation.kind");
        assertThat(kind).isEqualTo("siriusComponents://representation?type=Tree");

        String label = JsonPath.read(result, "$.data.viewer.editingContext.representation.label");
        assertThat(label).isEqualTo("Explorer");
    }

    @Test
    @DisplayName("Given an editing context id, when a query is performed, then all the representation metadata are returned")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenEditingContextIdWhenQueryIsPerformedThenAllTheRepresentationMetadataAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString()
        );
        var result = this.allRepresentationMetadataQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.count");
        assertThat(count).isPositive();

        List<String> representationIds = JsonPath.read(result, "$.data.viewer.editingContext.representations.edges[*].node.id");
        assertThat(representationIds)
            .contains(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString())
            .contains(TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION.toString());
    }

    @Test
    @DisplayName("Given an object id, when a query is performed, then all the representation descriptions are returned")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenObjectIdWhenQueryIsPerformedThenAllTheRepresentationDescriptionsAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                "objectId", TestIdentifiers.EPACKAGE_OBJECT.toString()
        );
        var result = this.allRepresentationDescriptionsQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.count");
        assertThat(count).isEqualTo(3);

        List<String> representationIds = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.edges[*].node.id");
        assertThat(representationIds).hasSize(3);
    }
}
