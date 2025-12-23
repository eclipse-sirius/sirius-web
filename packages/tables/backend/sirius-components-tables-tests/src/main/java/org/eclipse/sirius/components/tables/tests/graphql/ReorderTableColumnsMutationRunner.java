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

import org.eclipse.sirius.components.collaborative.tables.dto.ReorderTableColumnsInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to change the order of columns in table with the GraphQL API.
 *
 * @author Jerome Gout
 */
@Service
public class ReorderTableColumnsMutationRunner implements IMutationRunner<ReorderTableColumnsInput> {

    private static final String REORDER_COLUMNS_MUTATION = """
                mutation reorderTableColumns($input: ReorderTableColumnsInput!) {
                  reorderTableColumns(input: $input) {
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

    public ReorderTableColumnsMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(ReorderTableColumnsInput input) {
        return this.graphQLRequestor.execute(REORDER_COLUMNS_MUTATION, input);
    }
}
