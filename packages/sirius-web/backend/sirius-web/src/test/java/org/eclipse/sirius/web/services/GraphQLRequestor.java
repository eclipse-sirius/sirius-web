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
package org.eclipse.sirius.web.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.services.api.IGraphQLRequestor;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.execution.reactive.SubscriptionPublisher;
import reactor.core.publisher.Flux;

/**
 * Used to execute GraphQL requests.
 *
 * @author sbegaudeau
 */
@Service
public class GraphQLRequestor implements IGraphQLRequestor {

    private final ObjectMapper objectMapper;

    private final GraphQL graphQL;

    public GraphQLRequestor(ObjectMapper objectMapper, GraphQL graphQL) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.graphQL = Objects.requireNonNull(graphQL);
    }

    @Override
    public String execute(String query, Map<String, Object> variables) {
        var executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .build();

        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();
        try {
            return this.objectMapper.writeValueAsString(executionResult.toSpecification());
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }
        return null;
    }

    @Override
    public String execute(String query, IInput input) {
        Map<String, Object> variables = Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() { }));
        return this.execute(query, variables);
    }

    @Override
    public Flux<IPayload> subscribe(String query, IInput input) {
        Map<String, Object> variables = Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() { }));

        var executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .build();

        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        SubscriptionPublisher result = executionResult.getData();
        return Flux.from(result.getUpstreamPublisher())
                .filter(IPayload.class::isInstance)
                .map(IPayload.class::cast);
    }
}
