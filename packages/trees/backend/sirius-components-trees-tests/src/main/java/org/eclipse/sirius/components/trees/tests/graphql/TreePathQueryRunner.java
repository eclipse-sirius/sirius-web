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
package org.eclipse.sirius.components.trees.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get the tree path with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class TreePathQueryRunner implements IQueryRunner {

    private static final String TREE_PATH_QUERY = """
            query getTreePath($editingContextId: ID!, $treeId: ID!, $selectionEntryIds: [ID!]!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  treePath(treeId: $treeId, selectionEntryIds: $selectionEntryIds) {
                    treeItemIdsToExpand
                    maxDepth
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public TreePathQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(TREE_PATH_QUERY, variables);
    }
}
