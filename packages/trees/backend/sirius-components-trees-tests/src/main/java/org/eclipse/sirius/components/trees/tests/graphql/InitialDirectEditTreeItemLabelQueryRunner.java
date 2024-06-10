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
 * Used to get the initial direct edit tree item label with the graphql API.
 *
 * @author gcoutable
 */
@Service
public class InitialDirectEditTreeItemLabelQueryRunner implements IQueryRunner {

    private static final String INITIAL_DIRECT_EDIT_TREE_ITEM_LABEL_QUERY = """
            query initialDirectEditElementLabel($editingContextId: ID!, $representationId: ID!, $treeItemId: ID!) {
                viewer {
                  editingContext(editingContextId: $editingContextId) {
                    representation(representationId: $representationId) {
                      description {
                        ... on TreeDescription {
                          initialDirectEditTreeItemLabel(treeItemId: $treeItemId)
                        }
                      }
                    }
                  }
                }
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public InitialDirectEditTreeItemLabelQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(INITIAL_DIRECT_EDIT_TREE_ITEM_LABEL_QUERY, variables);
    }
}
