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
 * Used to search for elements inside an editing context.
 *
 * @author pcdavid
 */
@Service
public class SearchQueryRunner implements IQueryRunner {

    private static final String SEARCH_QUERY = """
              query search($editingContextId: ID!, $query: SearchQuery!) {
                viewer {
                  editingContext(editingContextId: $editingContextId) {
                    search(query: $query) {
                      __typename
                      ... on SearchSuccessPayload {
                        result {
                          __typename
                          matches {
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

    public SearchQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(SEARCH_QUERY, variables);
    }
}
