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
package org.eclipse.sirius.components.graphql.tests;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * The query runner to perform a search from the omnibox.
 *
 * @author gdaniel
 */
@Service
public class OmniboxSearchQueryRunner implements IQueryRunner {

    private static final String OMNIBOX_SEARCH = """
            query getOmniboxSearchResults($editingContextId: ID!, $selectedObjectIds: [ID!]!, $query: String!) {
              viewer {
                omniboxSearch(editingContextId: $editingContextId, selectedObjectIds: $selectedObjectIds, query: $query) {
                  id
                  kind
                  label
                  iconURLs
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public OmniboxSearchQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(OMNIBOX_SEARCH, variables);
    }
}
