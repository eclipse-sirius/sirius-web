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
package org.eclipse.sirius.components.tables.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get the tool menu entries with the GraphQL API.
 *
 * @author frouene
 */
@Service
public class ToolMenuEntriesQueryRunner implements IQueryRunner {

    private static final String TOOL_MENU_ENTRIES_QUERY = """
              query getAllToolMenuEntries($editingContextId: ID!, $representationId: ID!, $tableId: ID!) {
                viewer {
                  editingContext(editingContextId: $editingContextId) {
                    representation(representationId: $representationId) {
                      description {
                        ... on TableDescription {
                          toolMenuEntries(tableId: $tableId) {
                            ... on ToolMenuEntry {
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
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ToolMenuEntriesQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(TOOL_MENU_ENTRIES_QUERY, variables);
    }
}
