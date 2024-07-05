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
package org.eclipse.sirius.components.forms.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.dto.DeleteListItemInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to delete list items with the GraphQL API.
 *
 * @author sbegaideai
 */
@Service
public class DeleteListItemMutationRunner implements IMutationRunner<DeleteListItemInput> {

    private static final String DELETE_LIST_ITEM_MUTATION = """
            mutation deleteListItem($input: DeleteListItemInput!) {
              deleteListItem(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DeleteListItemMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DeleteListItemInput input) {
        return this.graphQLRequestor.execute(DELETE_LIST_ITEM_MUTATION, input);
    }
}
