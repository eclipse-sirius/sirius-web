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
 * Integration tests of the editing context controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EditingContextControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String GET_EDITING_CONTEXT = """
            query getEditingContext($editingContextId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  id
                }
              }
            }
            """;

    private static final String GET_CURRENT_EDITING_CONTEXT = """
            query getCurrentEditingContextId($projectId: ID!) {
              viewer {
                project(projectId: $projectId) {
                  currentEditingContext {
                    id
                  }
                }
              }
            }
            """;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @Test
    @DisplayName("Given an editing context id, when a query is performed, then the editing context is returned")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenEditingContextIdWhenQueryIsPerformedThenTheEditingContextIsReturned() {
        Map<String, Object> variables = Map.of("editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
        var result = this.graphQLRequestor.execute(GET_EDITING_CONTEXT, variables);

        String editingContextId = JsonPath.read(result, "$.data.viewer.editingContext.id");
        assertThat(editingContextId).isEqualTo(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
    }

    @Test
    @DisplayName("Given a project, when a query is performed, then the editing context id is returned")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenQueryIsPerformedThenTheEditingContextIdIsReturned() {
        Map<String, Object> variables = Map.of("projectId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
        var result = this.graphQLRequestor.execute(GET_CURRENT_EDITING_CONTEXT, variables);

        String editingContextId = JsonPath.read(result, "$.data.viewer.project.currentEditingContext.id");
        assertThat(editingContextId).isEqualTo(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
    }
}
