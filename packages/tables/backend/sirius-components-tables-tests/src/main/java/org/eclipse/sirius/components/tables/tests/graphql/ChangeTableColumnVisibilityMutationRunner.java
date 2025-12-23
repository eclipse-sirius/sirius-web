/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.components.tables.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.tables.dto.ChangeTableColumnVisibilityInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to change the column visibility in table with the GraphQL API.
 *
 * @author frouene
 */
@Service
public class ChangeTableColumnVisibilityMutationRunner implements IMutationRunner<ChangeTableColumnVisibilityInput> {

    private static final String CHANGE_COLUMN_VISIBILITY_MUTATION = """
                mutation changeTableColumnVisibility($input: ChangeTableColumnVisibilityInput!) {
                  changeTableColumnVisibility(input: $input) {
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

    public ChangeTableColumnVisibilityMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(ChangeTableColumnVisibilityInput input) {
        return this.graphQLRequestor.execute(CHANGE_COLUMN_VISIBILITY_MUTATION, input);
    }
}
