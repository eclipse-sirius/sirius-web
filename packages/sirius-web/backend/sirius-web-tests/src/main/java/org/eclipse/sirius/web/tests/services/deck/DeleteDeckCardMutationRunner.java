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
package org.eclipse.sirius.web.tests.services.deck;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.deck.dto.input.DeleteDeckCardInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to delete a card in a deck representation.
 *
 * @author sbegaudeau
 */
@Service
public class DeleteDeckCardMutationRunner implements IMutationRunner<DeleteDeckCardInput> {

    private static final String DELETE_DECK_CARD_MUTATION = """
            mutation deleteDeckCard($input: DeleteDeckCardInput!) {
              deleteDeckCard(input: $input) {
                __typename
                ... on SuccessPayload {
                  id
                }
                ... on ErrorPayload {
                  message
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DeleteDeckCardMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DeleteDeckCardInput input) {
        return this.graphQLRequestor.execute(DELETE_DECK_CARD_MUTATION, input);
    }
}
