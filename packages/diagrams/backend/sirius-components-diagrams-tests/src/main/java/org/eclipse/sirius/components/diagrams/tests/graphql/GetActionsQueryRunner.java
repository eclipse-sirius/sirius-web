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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve the list of actions that can be executed using the GraphQL API.
 *
 * @author arichard
 */
@Service
public class GetActionsQueryRunner implements IQueryRunner {

    private static final String GET_ACTIONS_QUERY = """
            query getActions(
              $editingContextId: ID!
              $diagramId: ID!,
              $diagramElementId: ID!
            ) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $diagramId) {
                    description {
                      ... on DiagramDescription {
                        actions(diagramElementId: $diagramElementId) {
                          id
                          tooltip
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

    public GetActionsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(GET_ACTIONS_QUERY, variables);
    }
}
