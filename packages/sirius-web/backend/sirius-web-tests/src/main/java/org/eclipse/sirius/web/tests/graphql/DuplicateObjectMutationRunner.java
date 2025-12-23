/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectInput;
import org.springframework.stereotype.Service;

/**
 * Used to duplicate an object with the GraphQL API.
 *
 * @author frouene
 */
@Service
public class DuplicateObjectMutationRunner implements IMutationRunner<DuplicateObjectInput> {

    private static final String DUPLICATE_OBJECT = """
            mutation duplicateObject($input: DuplicateObjectInput!) {
              duplicateObject(input: $input) {
                __typename
                ... on DuplicateObjectSuccessPayload {
                  object {
                    id
                    label
                    kind
                  }
                }
               ... on ErrorPayload {
                    message
                    messages {
                      level
                      body
                    }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DuplicateObjectMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(DuplicateObjectInput input) {
        return this.graphQLRequestor.execute(DUPLICATE_OBJECT, input);
    }
}
