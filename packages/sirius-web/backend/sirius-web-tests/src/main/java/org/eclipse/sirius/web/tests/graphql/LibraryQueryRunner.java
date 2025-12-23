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

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get the library from the GraphQL API.
 *
 * @author gcoutable
 */
@Service
public class LibraryQueryRunner implements IQueryRunner {

    private static final String LIBRARY_QUERY = """
            query getLibrary($namespace: String!, $name: String!, $version: String!) {
                viewer {
                  library(namespace: $namespace, name: $name, version: $version) {
                    namespace
                    name
                    version
                    description
                    createdOn
                    currentEditingContext {
                      id
                    }
                  }
                }
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public LibraryQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(LIBRARY_QUERY, variables);
    }
}
