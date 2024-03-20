/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get projects from the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class ProjectsQueryRunner implements IQueryRunner {

    private static final String PROJECTS_QUERY = """
            query getProjects($page: Int!, $limit: Int!) {
              viewer {
                projects(page: $page, limit: $limit) {
                  edges {
                    node {
                      id
                    }
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
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ProjectsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(PROJECTS_QUERY, variables);
    }

}
