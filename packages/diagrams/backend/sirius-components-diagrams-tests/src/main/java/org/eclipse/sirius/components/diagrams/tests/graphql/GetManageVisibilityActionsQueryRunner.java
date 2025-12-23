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
 * Used to retrieve the list of manage visibility actions that can be executed using the GraphQL API.
 *
 * @author mcharfadi
 */
@Service
public class GetManageVisibilityActionsQueryRunner implements IQueryRunner {

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
                        manageVisibilityActions(diagramElementId: $diagramElementId) {
                          id
                          label
                        }
                      }
                    }
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public GetManageVisibilityActionsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(GET_ACTIONS_QUERY, variables);
    }
}
