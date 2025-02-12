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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get the libraries from the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class LibrariesQueryRunner implements IQueryRunner {

    private static final String LIBRARIES_QUERY = """
            query getLibraries($page: Int!, $limit: Int!) {
              viewer {
                libraries(page: $page, limit: $limit) {
                  edges {
                    node {
                      id
                      namespace
                      name
                      version
                      description
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

    public LibrariesQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(LIBRARIES_QUERY, variables);
    }
}
