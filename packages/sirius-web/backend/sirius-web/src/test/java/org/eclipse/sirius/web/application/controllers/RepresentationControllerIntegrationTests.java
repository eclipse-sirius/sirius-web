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

import java.util.Map;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.TestIdentifiers;
import org.eclipse.sirius.web.services.api.IGraphQLRequestor;
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

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

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
}
