/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import static org.assertj.core.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.eclipse.sirius.web.application.services.ExceptionInjectionTransformer;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.RenameProjectMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import graphql.ExceptionWhileDataFetching;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.GraphQLError;
import reactor.core.publisher.Flux;

/**
 * Test the behavior when a data fetcher throws an exception.
 *
 * @author pcdavid
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@Import(ExceptionInjectionTransformer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerFailureIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GraphQL graphQL;

    @Autowired
    private IProjectSearchService projectSearchService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a data fetcher which throws an exception, when it is inboked, then a proper error is returned")
    public void givenDataFetcherThrowingExceptionWhenInvokedThenProperErrorReturned() {
        assertThat(this.projectSearchService.existsById(TestIdentifiers.UML_SAMPLE_PROJECT)).isTrue();

        List<GraphQLError> errors = new ArrayList<>();
        var input = new RenameProjectInput(UUID.randomUUID(), TestIdentifiers.UML_SAMPLE_PROJECT, "New Name");
        RenameProjectMutationRunner renameProjectMutationRunner = new RenameProjectMutationRunner(new IGraphQLRequestor() {

            @Override
            public Flux<String> subscribeToSpecification(String query, IInput input) {
                return Flux.empty();
            }

            @Override
            public Flux<Object> subscribe(String query, IInput input) {
                return Flux.empty();
            }

            @Override
            public String execute(String query, IInput input) {
                Map<String, Object> variables = Map.of("input", ProjectControllerFailureIntegrationTests.this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() { }));
                return this.execute(query, variables);
            }

            @Override
            public String execute(String query, Map<String, Object> variables) {
                var executionInput = ExecutionInput.newExecutionInput()
                        .query(query)
                        .variables(variables)
                        .build();

                var executionResult = ProjectControllerFailureIntegrationTests.this.graphQL.execute(executionInput);
                errors.addAll(executionResult.getErrors());
                try {
                    return ProjectControllerFailureIntegrationTests.this.objectMapper.writeValueAsString(executionResult.toSpecification());
                } catch (JsonProcessingException exception) {
                    fail(exception.getMessage());
                }
                return null;
            }
        });
        var jsonResult = renameProjectMutationRunner.run(input);
        var data = JsonPath.read(jsonResult, "$.data");
        assertThat(data).isNull();
        List<String> errorMessages = JsonPath.read(jsonResult, "$.errors[*].message");
        assertThat(errorMessages).isEqualTo(List.of("Exception while fetching data (/renameProject) : injected fault"));
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0)).isInstanceOf(ExceptionWhileDataFetching.class);
        assertThat(((ExceptionWhileDataFetching) errors.get(0)).getMessage()).isEqualTo("Exception while fetching data (/renameProject) : injected fault");
    }
}
