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
 * Used to call a table configuration graphQL query for testing purpose.
 *
 * @author frouene
 */
@Service
public class TableConfigurationQueryRunner implements IQueryRunner {

    private static final String TABLE_CONFIGURATION_QUERY = """
              query getTableConfiguration($editingContextId: ID!, $representationId: ID!) {
                viewer {
                  editingContext(editingContextId: $editingContextId) {
                    representation(representationId: $representationId) {
                      configuration {
                        globalFilter
                        columnFilters {
                          id
                          value
                        }
                        columnSort {
                          id
                          desc
                        }
                        defaultPageSize
                      }
                    }
                  }
                }
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public TableConfigurationQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(TABLE_CONFIGURATION_QUERY, variables);
    }
}
