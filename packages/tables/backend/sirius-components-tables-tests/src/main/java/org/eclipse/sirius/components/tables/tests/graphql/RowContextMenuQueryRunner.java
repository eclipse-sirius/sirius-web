/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to call a row context menu graphQL query for testing purpose.
 *
 * @author Jerome Gout
 */
@Service
public class RowContextMenuQueryRunner implements IQueryRunner {

    private static final String ROW_CONTEXT_MENU_QUERY = """
              query rowContextMenuQuery($editingContextId: ID!, $representationId: ID!, $tableId: ID!, $rowId: ID!) {
                viewer {
                  editingContext(editingContextId: $editingContextId) {
                    representation(representationId: $representationId) {
                      description {
                        ... on TableDescription {
                          rowContextMenuEntries(tableId: $tableId, rowId: $rowId) {
                            __typename
                            id
                            label
                            iconURLs
                          }
                        }
                      }
                    }
                  }
                }
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public RowContextMenuQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(ROW_CONTEXT_MENU_QUERY, variables);

    }
}
