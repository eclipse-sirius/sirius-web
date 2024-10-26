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

import org.eclipse.sirius.components.collaborative.trees.dto.DropTreeItemInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to drop a tree item with the GraphQL API.
 *
 * @author frouene
 */
@Service
public class DropTreeItemMutationRunner implements IMutationRunner<DropTreeItemInput> {

    private static final String DROP_TREE_ITEM_MUTATION = """
            mutation deleteTreeItem($input: DropTreeItemInput!) {
              dropTreeItem(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DropTreeItemMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DropTreeItemInput input) {
        return this.graphQLRequestor.execute(DROP_TREE_ITEM_MUTATION, input);
    }

}
