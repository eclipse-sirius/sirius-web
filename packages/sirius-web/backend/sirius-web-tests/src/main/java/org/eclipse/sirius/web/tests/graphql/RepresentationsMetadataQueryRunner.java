/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * Used to get the many representation metadata with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class RepresentationsMetadataQueryRunner implements IQueryRunner {

    private static final String REPRESENTATIONS_METADATA_QUERY = """
            query getRepresentationMetadata($editingContextId: ID!, $representationIds: [ID!], $after: String, $before: String, $first: Int, $last: Int) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representations(representationIds: $representationIds, after: $after, before: $before, first: $first, last: $last) {
                    edges {
                      node {
                        id
                        label
                        kind
                        iconURLs
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

    public RepresentationsMetadataQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(REPRESENTATIONS_METADATA_QUERY, variables);
    }

}
