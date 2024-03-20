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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.dto.DeleteTreeItemInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to delete a tree item with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class DeleteTreeItemMutationRunner implements IMutationRunner<DeleteTreeItemInput> {

    private static final String DELETE_TREE_ITEM_MUTATION = """
            mutation deleteTreeItem($input: DeleteTreeItemInput!) {
              deleteTreeItem(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DeleteTreeItemMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DeleteTreeItemInput input) {
        return this.graphQLRequestor.execute(DELETE_TREE_ITEM_MUTATION, input);
    }

}
