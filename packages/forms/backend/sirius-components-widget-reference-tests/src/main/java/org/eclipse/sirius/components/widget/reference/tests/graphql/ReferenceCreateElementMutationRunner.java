/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.widget.reference.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.widget.reference.dto.CreateElementInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to create elements in references with the GraphQL API.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceCreateElementMutationRunner implements IMutationRunner<CreateElementInput> {

    public static final String CREATE_ELEMENT_IN_REFERENCE_MUTATION = """
            mutation createElementInReference($input: CreateElementInReferenceInput!) {
              createElementInReference(input: $input) {
                __typename
                ... on SuccessPayload {
                  messages {
                    body
                    level
                  }
                }
                ... on ErrorPayload {
                  messages {
                    body
                    level
                  }
                }
                ... on CreateElementInReferenceSuccessPayload {
                  object {
                    id
                    label
                    kind
                  }
                  messages {
                    body
                    level
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ReferenceCreateElementMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(CreateElementInput input) {
        return this.graphQLRequestor.execute(CREATE_ELEMENT_IN_REFERENCE_MUTATION, input);
    }

}
