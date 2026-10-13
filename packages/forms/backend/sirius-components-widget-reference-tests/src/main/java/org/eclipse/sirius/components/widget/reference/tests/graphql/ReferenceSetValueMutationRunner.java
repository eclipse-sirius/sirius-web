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

import org.eclipse.sirius.components.collaborative.widget.reference.dto.SetReferenceValueInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Sets a reference value with the GraphQL API.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceSetValueMutationRunner implements IMutationRunner<SetReferenceValueInput> {

    public static final String SET_REFERENCE_VALUE_MUTATION = """
            mutation setReferenceValue($input: SetReferenceValueInput!) {
              setReferenceValue(input: $input) {
                __typename
                ... on ErrorPayload {
                  messages {
                    body
                    level
                  }
                }
                ... on SuccessPayload {
                  messages {
                    body
                    level
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ReferenceSetValueMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(SetReferenceValueInput input) {
        return this.graphQLRequestor.execute(SET_REFERENCE_VALUE_MUTATION, input);
    }
}
