/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.project.api.ICreateProjectInput;
import org.springframework.stereotype.Service;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

/**
 * Used to create a project with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class CreateProjectMutationRunner implements IMutationRunner<ICreateProjectInput> {

    public static final String CREATE_PROJECT = """
            mutation createProject($input: CreateProjectInput!) {
              createProject(input: $input) {
                __typename
                ... on CreateProjectSuccessPayload {
                  project {
                    id
                  }
                }
               ... on ErrorPayload {
                    messages {
                      level
                      body
                    }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    private final JsonMapper objectMapper;

    public CreateProjectMutationRunner(IGraphQLRequestor graphQLRequestor, JsonMapper objectMapper) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public GraphQLResult run(ICreateProjectInput input) {
        // This runner accepts any implementation of ICreateProjectInput, so we translate it into a map, regardless of its actual content, and we only update the libraryIds,
        // which need to be serialized into namespace:name@version.
        Map<String, Object> inputMap = this.objectMapper.convertValue(input, new TypeReference<>() { });
        inputMap.put("libraryIds", input.libraryIds().stream()
                .map(libraryId -> libraryId.namespace() + ":" + libraryId.name() + "@" + libraryId.version())
                .toList());
        Map<String, Object> variables = Map.of("input", inputMap);
        return this.graphQLRequestor.execute(CREATE_PROJECT, variables);
    }

}
