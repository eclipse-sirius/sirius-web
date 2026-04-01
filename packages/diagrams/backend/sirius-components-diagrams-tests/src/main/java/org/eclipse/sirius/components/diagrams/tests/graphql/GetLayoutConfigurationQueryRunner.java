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

package org.eclipse.sirius.components.diagrams.tests.graphql;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Objects;

/**
 * Used to retrieve the list of layout configurations that can be executed using the GraphQL API.
 *
 * @author ocailleau
 */
@Service
public class GetLayoutConfigurationQueryRunner implements IQueryRunner {

    private static final String GET_LAYOUT_CONFIGURATIONS_QUERY = """
            query getLayoutConfigurations(
              $editingContextId: ID!
              $diagramId: ID!
            ) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $diagramId) {
                    description {
                      ... on DiagramDescription {
                        layoutConfigurations {
                          id
                          label
                          iconURL
                          layoutOptions {
                            key
                            value
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

    public GetLayoutConfigurationQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(GET_LAYOUT_CONFIGURATIONS_QUERY, variables);
    }
}