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
 * Used to get all the tree items to expand from another tree item.
 *
 * @author sbegaudeau
 */
@Service
public class ExpandAllTreePathQueryRunner implements IQueryRunner {

    private static final String EXPAND_ALL_TREE_PATH_QUERY = """
            query getExpandAllTreePath($editingContextId: ID!, $treeId: ID!, $treeItemId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  expandAllTreePath(treeId: $treeId, treeItemId: $treeItemId) {
                    treeItemIdsToExpand
                    maxDepth
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ExpandAllTreePathQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(EXPAND_ALL_TREE_PATH_QUERY, variables);
    }
}
