/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
 *  Used to get the list of actions in the context menu of a tree item from the GraphQL API.
 *
 * @author Jerome Gout
 */
@Service
public class TreeItemContextMenuQueryRunner implements IQueryRunner {

    private static final String TREE_ITEM_CONTEXT_MENU_QUERY = """
            query getAllContextMenuActions($editingContextId: ID!, $representationId: ID!, $treeItemId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on TreeDescription {
                        contextMenu(treeItemId: $treeItemId) {
                          __typename
                          id
                          label
                          iconURL
                          keyBindings {
                            isCtrl
                            isMeta
                            isAlt
                            key
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

    public TreeItemContextMenuQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(TREE_ITEM_CONTEXT_MENU_QUERY, variables);
    }
}
