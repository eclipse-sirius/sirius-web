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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve registered style customizations from the GraphQL API.
 *
 * @author gcoutable
 */
@Service
public class ProjectStyleCustomizationsQueryRunner implements IQueryRunner {

    public static final String PROJECT_STYLE_CUSTOMIZATIONS_QUERY = """
            query projectStyleCustomizations($projectId: ID!, $first: Int, $after: String, $last: Int, $before: String) {
              viewer {
                project(projectId: $projectId) {
                  styleCustomizations(first: $first, after: $after, last: $last, before: $before) {
                    edges {
                      node {
                        id
                        name
                        description
                        enabled
                      }
                      cursor
                    }
                    pageInfo {
                      hasPreviousPage
                      hasNextPage
                      startCursor
                      endCursor
                      count
                    }
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ProjectStyleCustomizationsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(PROJECT_STYLE_CUSTOMIZATIONS_QUERY, variables);
    }
}
