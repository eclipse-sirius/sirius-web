/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.sample.tests.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.projects.CreateProjectInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import graphql.ExecutionInput;
import graphql.GraphQL;

/**
 * Integration tests of the project controller.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerIntegrationTests extends AbstractIntegrationTests {
    @Autowired
    private GraphQL graphQL;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IProjectRepository projectRepository;

    @Test
    @DisplayName("Given a valid project to create, when the mutation is performed, then the project is created")
    public void givenValidProjectToCreateWhenMutationIsPerformedThenProjectIsCreated() {
        assertThat(this.projectRepository.count()).isEqualTo(0);

        var query = """
            mutation createProject($input: CreateProjectInput!) {
              createProject(input: $input) {
                __typename
                ... on CreateProjectSuccessPayload {
                  project {
                    id
                  }
                }
              }
            }
            """;

        var input = new CreateProjectInput(UUID.randomUUID(), "New Project");

        var executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() { })))
                .build();
        var executionResult = this.graphQL.execute(executionInput);
        try {
            var jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            String projectId = JsonPath.read(jsonResult, "$.data.createProject.project.id");

            assertThat(executionResult.getErrors()).isEmpty();

            var optionalProject = this.projectRepository.findById(UUID.fromString(projectId));
            assertThat(optionalProject).isPresent();
            optionalProject.ifPresent(project -> {
                assertThat(project.getName()).isEqualTo(input.name());
            });
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }
    }
}
